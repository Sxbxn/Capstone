# Cellification🔬 (세포 계수 측정을 위한 딥러닝 기반 객체 탐지 시스템 연구 개발)
## 🔍 개요
- "인공지능을 활용한 세포 계수 측정 시스템"의 주제를 가지고, 기업 [(주)솔](https://sol.re.kr/main)과 산학협력 프로젝트를 진행하게 되었음 (2022년 경기대학교 캡스톤)
- 기존의 방식들은 세포 계수를 알아내는데 많은 시간과 비용과 인력이 필요하여 비효율적임  
  => 세포 계수를 인공지능으로 분석하는 앱 애플리케이션을 활용하여 세포 계수를 검출한다면, 적은 시간과 비용으로 간편하게 세포 계수를 알아낼 수 있음
- 인공지능을 활용해 사용자가 직접 계산하지 않아도 __Live Cell__ 과 __Dead Cell__ 을 탐지하고 __Viability__ 비율을 계산하여 사용자에게 빠르게 제공해줌 
---

## 🙌 팀원
| 팀원 | 역할 | 개발환경 
|-----|-----|--------
|조수빈(PM), 김태강| 백엔드 | Spring frameworks, AWS s3, Naver Cloud Platform, Flask
|김민종, 박준후| 안드로이드 | Android(Kotlin)
|한동현| AI(Object Detection) | YOLO, PyTorch
---

## 📚 관련 자료
#### 📍 [포스터](https://github.com/Sxbxn/Capstone/blob/be5f5ac1b1f2f99c4ba343aa96434680b5a1531f/%EA%B2%B0%EA%B3%BC%EB%AC%BC%20%EB%AA%A8%EC%9D%8C/%ED%8F%AC%EC%8A%A4%ED%84%B0.JPG)
#### 📍 [BackEnd](https://github.com/Sxbxn/Capstone/blob/be5f5ac1b1f2f99c4ba343aa96434680b5a1531f/%EA%B2%B0%EA%B3%BC%EB%AC%BC%20%EB%AA%A8%EC%9D%8C/%EB%B0%B1%EC%97%94%EB%93%9C/%EB%B0%B1%EC%97%94%EB%93%9C%20%EC%88%98%ED%96%89%EB%82%B4%EC%9A%A9.md)
#### 📍 [Android](https://github.com/Sxbxn/Capstone/blob/be5f5ac1b1f2f99c4ba343aa96434680b5a1531f/%EA%B2%B0%EA%B3%BC%EB%AC%BC%20%EB%AA%A8%EC%9D%8C/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%20%EC%88%98%ED%96%89%EB%82%B4%EC%9A%A9.md)
#### 📍 [AI](https://github.com/Sxbxn/Capstone/tree/main/%EA%B2%B0%EA%B3%BC%EB%AC%BC%20%EB%AA%A8%EC%9D%8C/AI/Development_log)
---

## ✨ App Icon
![image](https://user-images.githubusercontent.com/79958455/174990062-e530faae-c849-41ba-a939-bc8ad9a12a7f.png)

---
## 🚀 Technology
__Language__: __JAVA__, __Kotlin__, __Python__  
__Tech Stack__: `Spring`, `AWS S3`, `MySQL`, `PyTorch`, `OpenCV`, `AAC`, `Jetpack`, `LiveData`, `ViewModel`, `Dependency Injection(Hilt)`, `Glide`, `Room`, `Retrofit`, `Coroutine` ...  
__Architecture__: Backend - 마이크로서비스 아키텍처(MSA) &nbsp; &nbsp; Android - MVVM  

---
## 📷 Result Screen

<img src="https://user-images.githubusercontent.com/63226023/174482009-0350d5c4-3d72-4228-ad09-c7a2fd33d09c.png" width="20%">
<img src="https://user-images.githubusercontent.com/63226023/174482071-9e5ae217-b2d6-4e03-8fa7-74ffa4673d09.png" width="20%">
<img src="https://user-images.githubusercontent.com/63226023/174482073-b815a53d-e72d-4fcf-9702-e0528a95b8a7.png" width="20%">
<img src="https://user-images.githubusercontent.com/63226023/174482090-a02624cc-5660-40de-8e1d-fd323a8b5070.png" width="20%">
<img src="https://user-images.githubusercontent.com/63226023/174482116-1d59fc3e-b428-43cb-bfa5-c9c5fcd31069.png" width="20%">

---
## ✅ 커밋 메시지 규칙
1. 문장의 끝에 . 를 붙이지 말기
2. 이슈 번호를 커밋 메시지 끝에 붙이기
3. 형식
   > [타입]: [내용] [이슈 번호]
4. 예시
   > DOCS: OO메소드 관련 설명 주석 [#3]
   >
   > FEAT: 시스템의 기능 add() [#6]

> \- FEAT : 새로운 기능의 추가
> 
> \- FIX: 버그 수정
> 
> \- DOCS: 문서 수정
> 
> \- STYLE: 스타일 관련 기능(코드 포맷팅, 세미콜론 누락, 코드 자체의 변경이 없는 경우)
> 
> \- REFACTOR: 코드 리펙토링
> 
> \- TEST: 테스트 코트, 리펙토링 테스트 코드 추가
> 
> \- CHORE: 간단한 수정, 빌드 업무 수정, 패키지 매니저 수정(ex .gitignore 수정 같은 경우)