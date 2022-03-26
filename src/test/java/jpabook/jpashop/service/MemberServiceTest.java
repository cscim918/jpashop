package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class) // JUnit 실행할 때 스프링이랑 같이 엮어서 실행하려면 넣어주면됨.
@SpringBootTest // 스프링부트를 띄운 상태에서 테스트(없으면 Autowired 실패)
@Transactional // 기본적으로 커밋을 안하고 롤백(@Rollback(false)로 하면 insert문 확인 가능)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
//    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("shim");

        // when
        Long savedId = memberService.join(member); // join을 통해 가입

        // then
//        em.flush(); 영속성 컨텍스트에 변경이나 등록된 내용을 데이터베이스에 반영
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("shim");

        Member member2 = new Member();
        member2.setName("shim");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 함!!!

        // then
        fail("예외가 발생해야 한다."); // 여기 오면 잘못된 거
    }
}