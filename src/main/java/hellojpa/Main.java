package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
//            member.setId(100L); // @GeneratedValue(strategy = GenerationType.AUTO) 로 설정하면 DB에서 AUTO_INCREMENT
            member.setName("hallo");
            member.setTeam(team);

            em.persist(member);

            // 내부에서 캐싱을 하기에 해당 설정을 해주지 않으면 SELECT 쿼리가 보이지 않는다.
            em.flush(); // DB 쿼리를 보낸다.
            em.clear(); // 캐시를 날린다.

            // 객체를 테이블에 맞추어 모델링을 하면(각각의 외래키들을 그냥 만들어준다면)
            //  => 멤버를 불러오고, 멤버에 teamId를 이용해서 다시한 번 Team을 조회해야한다.
            // ** 객체 지향적인 방법이 아니다!! **
            // 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다.

            // 조회
            Member findMember = em.find(Member.class, member.getId());

            // 연관관계가 있기에 참조를 사용해서 Member에서 그대로 가져온다.
            Team findTeam = findMember.getTeam();
            // Member의 ManyToOne의 TYPE을 LAZY 즉 지연로딩으로 해주면 Member만 가져온다.
            // 지연로딩에서 가져오려면 Team을 터치해주면 가져온다.
            findTeam.getName();

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }
}
