package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // 자동생성, GeneratedValue를 세팅하면 id 값이 항상 생성이 보장
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입 사용할 때 어노테이션
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member") // 주인 아닌 것
    private List<Order> orders = new ArrayList<>();
}
