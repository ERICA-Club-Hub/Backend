Analyze staged changes and commit with an appropriate message.

## 절차
1. `git diff --staged` 로 변경사항 파악
2. 적절한 type과 메시지 제안
3. 사용자 확인 후 커밋 실행

## 커밋 메시지 규칙
- 형식: 'type: 영어 설명'
- type 종류: feat, fix, refactor, chore, docs, test
- 설명은 간결하고 명령형으로 (e.g. implement user login)
- 소문자 시작, 마침표 없음

## 여러 변경사항 처리
- 타입이 섞인 경우 가장 중요한 타입 사용
- 커밋이 분리 가능한 경우 분리하여 커밋 메시지 제안
