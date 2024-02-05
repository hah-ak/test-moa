package my.application.api.services.study.jqpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.entities.study.jpql.JpqlMember;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpqlService {
    @Qualifier("studyLocalContainerEntityManagerFactoryBean")
    private final EntityManagerFactory entityManagerFactory;

    @AllArgsConstructor
    @Getter
    public static class JpqlMemberDTO {
        private String username;
        private Integer age;
    }

    public void querying() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

//            JpqlMember jpqlMember = new JpqlMember();
//            JpqlTeam jpqlTeam = new JpqlTeam();
//            jpqlTeam.setName("teamName");
//            jpqlMember.setAge(10);
//            jpqlMember.setUsername("userName");
//            jpqlMember.setTeam(jpqlTeam);
//
//            entityManager.persist(jpqlTeam);
//            entityManager.persist(jpqlMember);

//            TypedQuery<JpqlMember> selectMFromJpqlMemberM = entityManager.createQuery("select m from JpqlMember m", JpqlMember.class);
//            List<JpqlMember> resultList = selectMFromJpqlMemberM.getResultList();
//            for (JpqlMember member : resultList) {
//                System.out.println("member = " + member.getUsername());
//            }
//
//
//            Query query = entityManager.createQuery("select m.age, m.username from JpqlMember m");
//            List resultList1 = query.getResultList();
//            for (Object o : resultList1) {
//                System.out.println("o = " + o);
//            }
//
//
//            TypedQuery<JpqlMember> query1 = entityManager.createQuery("select m from JpqlMember m where m.username = :nameparam", JpqlMember.class);
//            TypedQuery<JpqlMember> jpqlMemberTypedQuery = query1.setParameter("nameparam", "userName");
//            List<JpqlMember> resultList2 = jpqlMemberTypedQuery.getResultList();
//            for (JpqlMember member : resultList2) {
//                System.out.println("member = " + member.getUsername());
//            }

            // 프로젝션 ( 조회대상 조회방식, 대상에는 임베디드 엔티티 스칼라 jpa에서 사용할수 있는 모든 값타입 사용가능.
            // 조회결과로 엔티티가 나오면 jpa에서 관리되는 객체가 만들어지는 거임.
//            List<JpqlTeam> resultList3 = entityManager.createQuery("select m.team from JpqlMember m", JpqlTeam.class)
//                    .getResultList();
//            for (JpqlTeam team : resultList3) {
//                team.setName("projectentitytest");
//            }
            //이너클래스는 되지도 않음;
//            List<JpqlMemberDTO> resultList = entityManager.createQuery("select new my.application.api.services.study.jqpl.JpqlService.JpqlMemberDTO(m.username, m.age) from JpqlMember m where m.age = 10", JpqlMemberDTO.class).getResultList();

            // 페이징
//            List<JpqlMember> resultList = entityManager.createQuery("select m from JpqlMember m order by m.age", JpqlMember.class)
//                    .setFirstResult(5)
//                    .setMaxResults(10)
//                    .getResultList();

//            JpqlMember jpqlMember = entityManager.find(JpqlMember.class, 1);
//            JpqlProduct jpqlProduct = new JpqlProduct();
//            JpqlOrder jpqlOrder = new JpqlOrder();
//
//            jpqlProduct.setName("prodName");
//            jpqlProduct.setStockAmount(1);
//            jpqlProduct.setPrice(100);
//
//            jpqlOrder.setMember(jpqlMember);
//            jpqlOrder.setOrderAmount(1);
//            jpqlOrder.setAddress(new JpqlAddress("city","street","zipcode"));
//            jpqlOrder.setJpqlProduct(jpqlProduct);
//
//            entityManager.persist(jpqlProduct);
//            entityManager.persist(jpqlOrder);
//
//            List resultList = entityManager.createQuery("select m, o from JpqlMember m left join JpqlOrder o")
//                    .getResultList();
//
//            for (Object o : resultList) {
//                Object[] oo = (Object[]) o;
//                JpqlMember jpqlMember1 = (JpqlMember) oo[0];
//                JpqlOrder jpqlOrder1 = (JpqlOrder) oo[1];
//
//                System.out.println("jpqlMember1.getUsername() = " + jpqlMember1.getUsername());
//                System.out.println("jpqlOrder1.getMember().getUsername() = " + jpqlOrder1.getMember().getUsername());
//            }


            // jpql에서 서브쿼리는 from 에서 못씀.
//            List<JpqlMember> resultList1 = entityManager.createQuery("select m from JpqlMember m where m.age > (select avg(mm.age) from JpqlMember mm)", JpqlMember.class)
//                    .getResultList();

//            MySQLDialect 이런 dialect 클래스에 각각의 dialect를 포함한 문법들이 들어가 있고 상속받아서 추가후 커스텀해서 사용도 가능

//            List<JpqlMember> resultList = entityManager.createQuery("select m.team from JpqlMember m", JpqlMember.class).getResultList();
//            엔티티의 엔티티를 탐색하기 위해 실제 db 내부에서 조인이 일어나서 가져와야함.
//            조인이 내부에서 얼마나 일어날지 커지면 모르니 최대한 막는게 좋음.
//            위에서 나온 조인처럼 내부에서 묵시적으로 생기면 상당히 유지보수 차원에서 어려워짐.
//            명시적으로 조인을 해줘야함.

//            fetch join
//            지속적으로 셀렉트 쿼리문이 생기는 걸 막고 한번에 가져옴. (lazy여도 가져옴)
            List<JpqlMember> resultList = entityManager.createQuery("select m from JpqlMember m inner join fetch m.team", JpqlMember.class).getResultList();

            transaction.commit();
        } catch (Exception e) {
            log.error("{}", e);
            transaction.rollback();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

    }
}
