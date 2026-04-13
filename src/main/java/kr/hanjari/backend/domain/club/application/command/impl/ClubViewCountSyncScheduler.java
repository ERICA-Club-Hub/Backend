package kr.hanjari.backend.domain.club.application.command.impl;

import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.redis.core.ScanOptions;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubViewCountSyncScheduler {

    private static final String KEY_PATTERN = "club:viewCount:*";

    private final StringRedisTemplate redisTemplate;
    private final ClubRepository clubRepository;

    @Scheduled(fixedDelay = 60000) // 1분
    @Transactional
    public void syncViewCountToDb() {
        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions()
                .match(KEY_PATTERN)
                .count(100)
                .build();

        try (Cursor<String> cursor = redisTemplate.scan(options)) {
            cursor.forEachRemaining(keys::add);
        }
        if (keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            try {
                String value = redisTemplate.opsForValue().getAndDelete(key);
                if (value == null) continue;

                long delta = Long.parseLong(value);
                Long clubId = extractClubId(key);

                clubRepository.incrementViewCountBy(clubId, delta);
                log.info("[ViewCount Sync] clubId={}, delta={}", clubId, delta);
            } catch (Exception e) {
                log.error("[ViewCount Sync] failed for key={}", key, e);
            }
        }
    }

    private Long extractClubId(String key) {
        return Long.parseLong(key.substring(key.lastIndexOf(":") + 1));
    }
}
