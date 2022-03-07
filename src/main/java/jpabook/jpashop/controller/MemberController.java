package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // Member를 바로 넣지 않는 이유는 컬럼이 안맞고 validation 지정이 다를수 있기 때문에
        // @valid - javax validation 기능을 쓴다고 인지
        // BindingResult - 오류가 담겨서 코드가 실행 (스프링부트에서 제공)

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 이렇게 하면 첫 번째 페이지로 넘어가게 된다.
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers(); // *** API를 만들때는 절대 엔티티를 반환하면 안된다 *** -> 필드 수정하면 API 스펙이 변한다.
        model.addAttribute("members", members);
        // 모델에 데이터를 담을 때
        // (String name, Object value):value 객체를 name 이름으로 추가.
        // 뷰 코드에서는 name을 지정한 이름을 통해서 value를 사용
        return "members/memberList";
    }
}
