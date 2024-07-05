package hellojpa;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        // resources/META-INF/persistence.xml 에 있는 <persistence-unit name="hello"> 을 참조
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

//         EntityManagerFactory 는 애플리케이션 로딩 시점에 하나만 만들어져야 한다.
//         Transaction 을 한 번 실행할 때마다 EntityManager 를 만들어줘야 한다.
//         EntityManager 는 쓰레드 간에도 공유하면 안 된다. 그러면 장애가 발생한다.
//         EntityManager 는 사용하고 즉시 버려야 한다.
//
//         JPA 의 정석적인 사용 방식: try-catch-finally 를 통해 트랜잭션 관리를 한다.
        try {
            // try 문 내부에는 em.persist(), em.find(), em.remove() 등의 메서드를 호출할 수 있다.
            // 수정은 member.필드세터() 만 호출하면 된다.
            Member member1 = new Member();
            member1.setName("A");

            Member member2 = new Member();
            member2.setName("B");

            Member member3 = new Member();
            member3.setName("C");

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        // WAS 가 내려갈 때 emf 를 닫아주어야 한다.
        emf.close();

    }
    
    // 실제로는 Spring 이 위의 코드를 다 작성해주기 때문에 이런 코드를 볼 일이 별로 없다.
    
}
