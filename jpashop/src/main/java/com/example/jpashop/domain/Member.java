package com.example.jpashop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    Long id;

    String name;

    @Embedded
    Address address;

    // 연관 관계의 주인이 Order이기 때문에, mappedBy를 통해 매핑한다.
    // mappedBy의 member는 Order의 member 필드를 의미한다.
    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();
}