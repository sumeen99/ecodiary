## 2번째 프로젝트 - EcoDiary(8.20~)

### 설명

* 일반 : 하루하루 환경에 대한 미션을 주고 해당 미션수행에 대한 사소한 질문을 제공, 질문에 대한 답변을 하고 자신의 별점을 매기면 해당 미션에 대한 환경정보들과 각 미션에 맞는 스탬프를 제공한다. 
* 교육 : 환경일기라는 교육방법은 이미 많이 쓰이고 있으므로 이 방식을 앱에 적용. 선생님들을 위한 관리자모드가 웹으로 제공되며 자신의 학생 앱ID를 입력하면 관리목록에 넣을 수 있다. 선생님들이 직접 미션과 질문, 정보들을 입력할 수 있게 했다. 





### 기술 스택(~~이라고 할것도 없지만...~~)

##### spring

* Gradle Project
* Java 11
* Spring boot - 2.5.4
* Dependencies
  * Spring Web
  * Lombok
  * Spring Boot DevTools
  * Spring Data JPA
  * MySQL Driver
  * Mustache

##### MySQL(AWS RDS)

##### Travis-Ci

##### AWS EC2

------

### 구조

<img src="C:\Users\leesumeen\AppData\Roaming\Typora\typora-user-images\image-20211029023834016.png" alt="image-20211029023834016" style="zoom:150%;" />

<img src="C:\Users\leesumeen\AppData\Roaming\Typora\typora-user-images\image-20211029023954819.png" alt="image-20211029023954819" style="zoom:150%;" />

(test부분구조는..... 우선 밑에서 얘기할게요....)

------

### 고민한 점

1. 패키지 구조

   그 전 프로젝트부터 고민있었던 부분. 처음에 시작했을 때는 대부분의 자료에서 계층형 구조를 사용하는 것을 잘 이해하지 못했다. 도메인형으로 하는게 오히려 더 깔끔해보였다. 하지만 이 프로젝트를 진행하면서 처음엔 도메인형으로 시작했지만, 점점 도메인간의 서로 참조(?)가 많아지면서(연관관계는 없었다) 이게 Posts Service인지 User Service인지 딱 구분하기가 애매해졌다. 나중에 가서는 계층형이 오히려 더 잘맞을 수 있게다 라고 생각했다. 결국에는 계층형으로 바꾸진 못했지만, 이번에 구조에 대해서 확실히 생각해 볼 수 있었다. (어떤 구조가 정답인지에 대해 매우 고민할 때 발견한 것 : https://www.inflearn.com/questions/16046)

2. 보안이란, db(설계,연결,어떤db를 사용할지), apiurl, test, mustache, 수치화해서 보여줄수있는것.....?,카프카,문자열정리,queryDSL,aws,nginx,Codedeploy(aws)





