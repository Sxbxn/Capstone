# Capstone

## discovery-service(마이크로 서비스들을 관리해주는 프로젝트, 서버)
- Eureka 서버 생성 완료

## apigateway-service(여러 마이크로 서비스들을 apigateway-service의 port로 관리, 연결해주는 프로젝트, 서버)
- pigateway-service 생성 완료

## user-service(사용자를 관리하는 프로젝트, 서버)
- CreateUser(유저 생성 기능 구현 완료)
- getUserByAll(모든 유저 조회 기능 구현 완료)
- getUserByUserId(특정 유저 조회 기능 구현 완료)
- deleteUserByUserId(회원 탈퇴 기능 구현 완료)
- deleteCellByUserIdAndCellId(특정 회원이 가지고있는 특정 cell 삭제 기능 구현 완료)
- updateUserInfo(특정 회원의 email로 정보를 찾아와 회원의 정보를 update하는 기능 구현 완료)

## cell-service(세포를 관리하는 프로젝트, 서버)
- CreateCell(세포 생성 기능 구현 완료 - 테스트 용)
- getCellByUserId(특정 userId가 가지고 있는 모든 세포 조회 기능 구현 완료)
- getOneCellByUserId(특정 userId가 가지고 있는 특정 cellId의 세포 조회 기능 구현 완료)
- deleteCellByUserId(회원 탈퇴시 탈퇴한 회원이 가지고있는 모든 cell 삭제 기능 구현 완료)
- deleteCellByUserIdAndCellId(특정한 회원이 가지고있는 특정한 cell 삭제 기능 구현 완료)

# 내일 해야할 일
- Docker
