# Title: Adopt Redis-based atomic increment with periodic DB sync for view count

## Status
accepted

## Context
`GET /api/clubs/{clubId}` includes logic to increment the view count on each call.
The existing implementation used JPA dirty checking, causing race conditions under concurrent requests.

A load test (VU 20, 20s) on dev showed only 195 increments out of 1,647 requests — **88.2% lost updates**.

## Decision
Increment view count atomically via Redis INCR, and sync to DB every minute using a scheduler with `getAndDelete` + bulk UPDATE.

- View count increment: `redisTemplate.opsForValue().increment("club:viewCount:{clubId}")`
- DB sync: scheduler collects Redis keys every minute and performs bulk UPDATE
- The 1-minute interval is chosen to balance acceptable staleness against DB write frequency.

Redis INCR is a single-threaded atomic operation that guarantees concurrency without DB locks, achieving the highest throughput among all five strategies (226/s on dev).

## Alternatives Considered

| Strategy | Lost Update | Error Rate | Throughput (dev) | Note |
|----------|-------------|------------|------------------|------|
| Dirty Check (existing) | ❌ 88.2% lost | ✅ 0% | 82/s | Cannot resolve lost updates |
| Pessimistic Lock | ✅ 0% | ✅ 0% | 75/s | Lowest throughput due to lock contention |
| Optimistic Lock | ✅ 0% | ❌ ~80% | 55/s | Most requests fail under high contention |
| Atomic Operation | ✅ 0% | ✅ 0% | 137/s | Balanced accuracy, stability, and performance |
| **Redis (adopted)** | ✅ 0% | ✅ 0% | **226/s** | Highest throughput; requires DB sync |

## Consequences

**Improvements**
- Lost updates eliminated (88.2% → 0%)
- Throughput improved (82/s → 226/s)
- Concurrency guaranteed without DB locks

**Known Issues**
1. **Up to 1-minute staleness**: `viewCount` in API responses is DB-based; unsynced Redis increments are not reflected for up to 1 minute
2. **Data loss on server restart**: Redis increments not yet synced to DB are lost on restart; no Graceful Shutdown hook for forced sync
3. **`KEYS` command performance**: `KEYS club:viewCount:*` is O(N) blocking; should be replaced with `SCAN`
4. **Data loss on Redis failure**: View counts recorded during Redis downtime are unrecoverable; no fallback to direct DB increment
5. **Cold start**: Idle Redis connection pool causes first requests to stall on connection setup (0–14s outage observed on dev)