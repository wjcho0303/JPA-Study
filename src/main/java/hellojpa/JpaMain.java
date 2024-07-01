package hellojpa;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // EntityManagerFactory 애플리케이션 로딩 시점에 하나만 만들어져야 한다.
        // Transaction 을 한 번 실행할 때마다 EntityManager 를 만들어줘야 한다.

        // JPA 의 정석적인 사용 방식: try-catch-finally 를 통해 트랜잭션 관리를 한다.
        try {
            // try 문 내부에는 persist(), find() 등의 메서드를 호출할 수 있다.
            Member member = new Member();

            member.setId(2L);
            member.setName("HelloB");

            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
    
    // 실제로는 Spring 이 위의 코드를 다 작성해주기 때문에 이런 코드를 볼 일이 별로 없다.
    
}
