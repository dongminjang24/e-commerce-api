### Background

---
배경 : 인증에 메일 서비스를 연동하기로 하여 리서치 후 mailgun 사용하기로 하였다.

### Change

---
기존 회원가입 로직에 이메일 인증을 동해 가입해야 하는 한 1step을 더 추가하였다.

### Test

---
이메일 발송을통한 실 테스트 확인, 테스트 코드 함께 작성

### Analytics

---
성능 테스트 결과, 비용
이메일 발송에 한건당 38원 필요

### Discuss

---
for은 여러가로 작성 하였지만, 좋은 방법이라고 생각하지는 않습니다.
혹시나 더 좋은 방법이 있으면 말씀해 주시면 적극 반영해 보겠습니다.