package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("hallo");
            member.setTeam(team);
            em.persist(member);
            team.getMembers().add(member);

            // 연관관계에서 가장 많이 하는 실수 => 연관관계의 주인에 값을 입력하지 않음!!
//            team.getMembers().add(member);
            // member에서 setTeam()을 하면 정상적으로 TEAM_ID가 UPDATE된다.
            // 현재 member 객체가 주인이기에 가능!

            // 내부에서 캐싱을 하기에 해당 설정을 해주지 않으면 SELECT 쿼리가 보이지 않는다.
            em.flush(); // DB 쿼리를 보낸다.
            em.clear(); // 캐시를 날린다.

            // 객체를 테이블에 맞추어 모델링을 하면(각각의 외래키들을 그냥 만들어준다면)
            //  => 멤버를 불러오고, 멤버에 teamId를 이용해서 다시한 번 Team을 조회해야한다.
            // ** 객체 지향적인 방법이 아니다!! **
            // 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다.

            Member findMember = em.find(Member.class, member.getId());

            // FetchType.LAZY일 때 em.close()를 하고 LazyInitializationException이 발생해야 하는데
            // 현재는 발생하지 않음..
            em.close();

            Team findTeam = findMember.getTeam();
            findTeam.getName();
            System.out.println("findTeam = " + findTeam);

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }
}
