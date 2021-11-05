## 2번째 프로젝트 - EcoDiary(8.20~9.?)

### 설명

* 일반 : 하루하루 환경에 대한 미션을 주고 해당 미션수행에 대한 사소한 질문을 제공, 질문에 대한 답변을 하고 자신의 별점을 매기면 해당 미션에 대한 환경정보들과 각 미션에 맞는 스탬프를 제공한다. 
* 교육 : 환경일기라는 교육방법은 이미 많이 쓰이고 있으므로 이 방식을 앱에 적용. 선생님들을 위한 관리자모드가 웹으로 제공되며 자신의 학생 앱ID를 입력하면 관리목록에 넣을 수 있다. 선생님들이 직접 미션과 질문, 정보들을 입력할 수 있게 했다. 
* 관련영상,PPT는 처음 디렉토리에서 발견가능





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

##### Mustache(탬플릿 엔진)

------

### 전체구조

![image-20211029023834016](https://user-images.githubusercontent.com/70104259/140189001-2c83d347-800d-49da-90aa-75f2c4410f14.png)

![image-20211029023954819](https://user-images.githubusercontent.com/70104259/140189201-b05472a4-a505-425c-b500-60b4c9f8f70b.png)

![주석 2021-11-05 160659](https://user-images.githubusercontent.com/70104259/140471808-2f46adf9-8640-46ee-9d39-3981c97aed1d.png)

(test부분구조는..... 우선 밑에서 얘기할게요....)

#### DB구조

![image-20211103043602436](https://user-images.githubusercontent.com/70104259/140189250-664bd45f-b980-4301-9a92-75a7756af5b8.png)


* daily
  * id BIGINT / Primary Key / Auto_Increment / Not Null: daily 테이블 아이디
  * mission VARCHAR(100) / Not Null  : 미션
  * question VARCHAR(100) / Not Null : 질문
  * info VARCHAR(200)  : 정보
  * imgurl VARCHAR(100) : 스탬프 이미지 URL
* edu_daily
  * id BIGINT / Primary Key / Auto_Increment / Not Null : edu_daily 테이블 아이디
  * admin_id BIGINT / Not Null : 관리자 아이디 (daily 테이블과 다른 점)
  * num BIGINT / Not Null : 미션 순서 (daily 테이블과 다른 점)
  * mission VARCHAR(100) / Not Null : 미션
  * question VARCHAR(100) / Not Null : 질문
  * info VARCHAR(200) : 정보
  * imgurl VARCHAR(100) : 스탬프 이미지 URL
* user
  * id BIGINT / Primary Key / Auto_Increment / Not Null : user 테이블 아이디
  * mission_id BIGINT / Not Null : 미션 순서
  * mission_date DATE : 미션 받은 날짜
  * admin_id BIGINT : 관리자 아이디(null 일 경우 일반사용자, 아닐경우 교육용 사용자)
* manager
  * id BIGINT / Primary Key / Not Null: 관리자 아이디



------

### 고민한 점

1. 패키지 구조

   그 전 프로젝트부터 고민있었던 부분. 처음에 시작했을 때는 대부분의 자료에서 계층형 구조를 사용하는 것을 잘 이해하지 못했다. 도메인형으로 하는게 오히려 더 깔끔해보였다. 하지만 이 프로젝트를 진행하면서 처음엔 도메인형으로 시작했지만, 점점 도메인간의 서로 참조(?)가 많아지면서(연관관계는 없었다) 이게 Posts Service인지 User Service인지 딱 구분하기가 애매해지는 부분이 가끔 있었다.  결국에는 계층형으로 바꾸진 못했지만, 이번에 구조에 대해서 확실히 생각해 볼 수 있었다. (어떤 구조가 정답인지에 대해 매우 고민할 때 발견한 것 : https://www.inflearn.com/questions/16046)

2. DB(MySql vs MongoDB)

   첫 번째 프로그램을 할 때도 굉장히 고민했던 부분. 첫번째 프로그램에서는 MongoDB를 사용했었다. 그때 몽고디비를 사용했던 이유는 RDBS와 NoSql의 차이를 많이 반영해서 했다기보다는 Mysql은 사용해본적이 없지만 몽고디비는 사용해본적이 없어 경험상 사용했었다. 확실히 규격이 없어서 그런지 좀 더 간편했고 기본적으로 Json형식이라 넘겨줄 때 간편하게 넘겨줬던것같다. 하지만 관계가 중요한 데이터나 확실한 규격이 있는 데이터에선 rdbs를 쓰는게 더 낫다고 느꼈다. 

   이번엔 MySql을 썼는데 위에서 데이터 구조를 보면 알겠지만 관계를 해놓지 않았다. 구조가 처음에는 daily만 있어서 관계가 가능했지만 후반부에 프로그램의 방향성을 바꾸느라 daily지만 성향이 다른 daily 테이블인 edu_daily를 만들게 되었다. daily와 연결되어 있는 user의 admin_id가  edu_daily와도 관계가 있게되어서 우선 내 수준에서는 조금 비효율적이지만 user의 admin_id를 select해 따로 해당 테이블에서 검색하는 방법을 썼다. 이로 인해 관계는 없지만, 데이터 구조가 명확하고 id와 같이 중복되면 안되는 데이터들을 사용하기 때문에 MySql이 더 낫다고 느껴 사용하게 되었다.  보통 중복불가가 한 데이터를 많이 쓰기에 앞으로 많이 쓸 것 같다.

3. REST API URI 결정

   rest api uri를 작성할 때, 내가 그냥 마음대로 붙여도 되는건가? 하는 의문이 들어 검색을 해보았더니 REST API URI 규칙이 존재했다. (https://sas-study.tistory.com/265 ~~검색해도 바로 나오는거지만~~) 이러한 것들을 지키면서 작성하려고 노력했다. 맞게 작성한 지는 모르겠지만 앞으로의 프로젝트에서도 꾸준히 지키려고 노력할 것 이다. 

4. Mustache

   갑작스레 웹페이지를 개발하게 되었다. JSP 를 사용하는 걸 많이 봤었고 Thymeleaf 또한 꽤 봤다. 그래서 둘 중 하나로 하고 싶었지만, 내가 참고를 많이 하는 책에선 Mustache 를 추천해 하루하루 빨리 개발해야하는 나에게는 확실한 자료가 있는 것을 선택하는 것이 낫겠다 라고 생각해 Mustache로 개발하게 되었다. 

------

### 개선할 점(~~너무 많지만~~)

1. 테스트 코드

   정말 아쉬운 마음이 큰 부분이다. 테스트의 중요성을 알고 프로젝트를 시작했으나 막판에 프로젝트가 갑작스레 진행되게 되고 개발을 하는데 급급해져서 test코드를 거의 작성하지 못했다. Conroller의 test코드 작성방법을 좀 더 공부해야할 것같다. test코드가 굉장히 중요한 것임을 알고있으므로 다음 프로젝트에서는 꼭 test line coverage 80% 을 충족하며 개발하고 싶다. 

2. CI/CD, Nginx

   Travis를 통해 CI를 진행하였고 

  ![image-20211104050328213](https://user-images.githubusercontent.com/70104259/140189280-ee9489bc-d9b3-476b-955f-b805812d15f7.png)
)

   그 다음에 AWS S3 와 AWS CodeDepoly를 연결하고 나아가 NginX까지 진행했어야하는데 그러지 못했다. 

   이 부분도 너무너무 아쉬운부분..

3. QueryDSL

   querydsl을 사용해 정적 쿼리가 아닌 동적 쿼리를 구현해보고 싶다. sql 자체로는 자바에선 그저 문자열이기 뿐이라 실행 전까지 오류를 발견할 수 없다. querydsl은 문자가 아닌 코드로 작성하며 실행이 아닌 컴파일 시점에 문법 오류를 발견할 수 있으며 마지막으로 동적쿼리를 사용한다. 

------

### 마지막 할 말

처음으로 이렇게 프로젝트 후기?나 정리를 써보는데 써보면 써볼수록 내가 부족한게 뭐고 어떤걸 보완해야하는지가 명확하게 들어나는 것같다. 다음 후기에서는 위에서 언급한 개선할 점들은 많이 보완이 되었는지를 꼭 확인해야겠다. 늘 느끼는건데 하나하나 생각하면서 하는 개발을 하고 싶지만 결국엔 개발자체에 조급해져 빨리빨리가 되고 만다. 프로젝트가 끝난 후에는 무조건 프로젝트 보완점을 개선하는 시간을 가져야 할 것 같다. 이번에는 프로젝트가 끝나자마자 시험이고 시험끝나자마자 다른 프로젝트를 갑자기 호로록 하게 되어 보완점을 개선하고 넘어가진 못해서 아쉬움이 많이 남았지만 첫번째 했던 프로젝트에 비해 성장한게 보여서 다행이라고 생각한다. 





