Git push and create a Pull Request for the current branch.

## Steps
1. `git status` 및 `git log --oneline develop..HEAD`로 현재 branch 상태 파악 후 사용자에게 표시
   - 커밋되지 않은 변경사항이 있으면 중단
2. `git fetch` 후 `git log --oneline HEAD..origin/develop`으로 diverge 여부 확인 후 사용자에게 표시
   - 결과가 없으면 rebase 불필요, 다음 단계로 진행
   - 결과가 있으면 `git rebase origin/develop`으로 rebase
3. rebase 충돌 발생 시 사용자에게 충돌 파일 표시 후 중단
4. remote branch로 push
   - default: `git push -u origin <branch>`
   - rebase 후 force push가 필요한 경우: `git push -u origin <branch> --force-with-lease`
5. PR title 및 body 제안
6. 사용자 확인 후 GitHub MCP(`mcp__github__create_pull_request`)로 PR 생성

## PR Title Rules
- 형식: `[TYPE]: 한글 설명` (e.g. `[FEAT]: 로그인 기능 구현`)
- type 종류: `FEAT, FIX, REFACTOR, CHORE, DOCS, TEST`
- type은 대문자, 마침표 없음, 명령형
- 70자 이하

## PR Body Format
```
## Summary
- <변경사항 핵심 요약>

## Changes
- <구체적인 변경 내용>

## Related Issue
Closes #<issue number>
```

## PR Body Guidelines
- `git log --oneline develop..HEAD` 로 커밋 목록 파악
- `git diff develop..HEAD` 로 실제 변경 내용 파악 후 body에 반영
- Summary, Changes 항목은 bullet point로 작성
- branch명에 이슈 번호가 포함된 경우(e.g. `feat/180`) 자동으로 Related Issue에 반영

## Base Branch
- 기본 base branch: `develop`
- 사용자가 명시한 경우 해당 branch 사용

## Notes
- `main`, `develop` branch로의 force push는 절대 금지
- 커밋되지 않은 변경사항이 있으면 PR 생성 전 사용자에게 알림
- PR 생성 후 URL을 출력하여 사용자가 확인할 수 있도록 함
