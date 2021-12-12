## JPA Tutorial

### 1강 JPA THRO


### 2강 JPA 기초와 매핑


### 3강 필드와 컬럼 매핑


### 4강 연관간계 매핑


### 5강 양방향 매핑


### 6강 JPA 내부구조
1. 영속성 컨텍스트
2. 프록시와 즉시로딩, 지연로딩

#### 비영속과 영속이란
영속 컨텍스트(EntityManager)

- 비영속 : 객체를 생성만 하고 EntityManager.persist()를 하지 않은 상태
- 영속   : 객체를 생성하고 EntityManager.persist() 즉 객체를 저장한 상태(영속)
- 준영속 : 들어왔다가 삭제하는 것? => EntityManager.detach() 
- 삭제   : EntityManger.remove() 

#### 영속성 컨텍스트의 이점
1. 1차 캐시 
   - 영속 엔티티의 동일성 보장 => sout(a == b) 와 같이 같은 캐시에서 나오기에 레퍼런스 값이 같다.
2. 동일성(identity) 보장
3. 엔티티 등록 - 트랜잭션을 지원하는 쓰기 지연(transactional write-behind) 
4. 변경감지(Dirty Checking) 
   - 스냅샵을 두고 이전의 Entity와 변경사항이 존재하면 UPDATE를 한다.
5. 지연 로딩(Lazy Loading)
   - 지연 로딩 LAZY을 사용해서 프록시로 조회
   - 프록시와 즉시로딩 주의
      - 가급적 지연 로딩을 사용
      - 즉시 로딩을 적용하면 예상하지 못한 SQL 발생
      - 즉시 로딩은 JPQL에서 N + 1 문제를 일으킨다.

 






