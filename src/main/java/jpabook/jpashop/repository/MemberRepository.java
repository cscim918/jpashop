package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // 스프링이 엔티티 매니저를 만들어서 여기에 주입해준다.
    private EntityManager em;

    public void save(Member member){ // 등록
        em.persist(member);
    }
ㄴ
    public Member findOne(Long id){ // 조회
        return em.find(Member.class, id);
    }
}
