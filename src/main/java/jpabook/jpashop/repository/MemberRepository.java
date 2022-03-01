package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //    @PersistenceUnit // 스프링이 엔티티 매니저를 만들어서 여기에 주입해준다.
    private final EntityManager em;

    //    @PersistenceUnit - 팩토리 매니저 주입 시
    //    private EntityManagerFactory emf;

    public void save(Member member) { // 등록
        em.persist(member);
    }

    public Member findOne(Long id) { // 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() { // 다수 조회
        return em.createQuery("select m from Member m", Member.class) // jpql - sql과 기능적으론 동일, 엔티티 대상으로 쿼리
                .getResultList();
    }

    public List<Member> findByName(String name) { // 이름으로 검색해서 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
