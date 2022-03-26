package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // javax 보다 spring이 쓸 수 있는 옵션이 더 많다.
@RequiredArgsConstructor // final에 있는 필드만 가지고 생성자를 만든다.
public class MemberService {

    //    @Autowired // 스프링이 스프링 빈에 들어있는 Repository를 인젝션
    private final MemberRepository memberRepository; // 변경할 일 없기 때문에 final로

//    장점: 테스트코드 작성할 때 mock을 직접 주입할 수 있다 / 단점: 런타임 중에 누군가가 바꿀 수 있다.
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

//    // 요즘에 권장하는 방법, 중간에 변경 불가, 테스트케이스 작성시 MemberService를 값을 직접 주입 가능
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional // 기본적으로 readOnly가 false
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 개인 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 회원 수정
    @Transactional
    public void update(Long id, String name) { // id 정도까지만 반환
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}