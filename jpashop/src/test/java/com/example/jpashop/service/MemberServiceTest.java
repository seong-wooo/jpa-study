package com.example.jpashop.service;

import com.example.jpashop.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입(@Autowired EntityManager em) {
        // given
        final Member member = new Member();
        member.setName("kim");
        // when
        final Long joinMember = memberService.join(member);

        // then
        em.flush();
        assertEquals(member, memberService.findOne(joinMember));
    }

}