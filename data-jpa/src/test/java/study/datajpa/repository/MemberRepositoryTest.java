package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void testMember () {
        final Member member = new Member("memberA");
        final Member savedMember = memberRepository.save(member);
        final Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void test() {
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 10);
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void testNamedQuery() {
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<Member> result = memberRepository.findByUsername("AAA");
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    public void testQuery() {
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<Member> result = memberRepository.findUser("AAA", 10);
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    public void findUsernameList() {
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<String> result = memberRepository.findUsernameList();
        System.out.println("result = " + result);

    }

    @Test
    public void findMemberDto() {
        final Team team = new Team("teamA");
        teamRepository.save(team);

        final Member member1 = new Member("AAA", 10, team);
        memberRepository.save(member1);

        final List<MemberDto> memberDto = memberRepository.findMemberDto();
        System.out.println(memberDto);

    }

    @Test
    public void findByNames() {
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<Member> result = memberRepository.findByNames(List.of("AAA", "BBB"));
        System.out.println("result = " + result);

    }

    @Test
    public void paging() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        final PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "username"));

//        final Page<Member> page = memberRepository.findByAge(age, pageRequest);
//        final List<Member> content = page.getContent();
//        final long totalElements = page.getTotalElements();
//
//         assertThat(content.size()).isEqualTo(2);
//        assertThat(page.getTotalElements()).isEqualTo(5);
//        assertThat(page.getNumber()).isEqualTo(1);
//        assertThat(page.getTotalPages()).isEqualTo(3);
//        assertThat(page.isFirst()).isFalse();
//        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void slice() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        final PageRequest pageRequest = PageRequest.of(2, 2, Sort.by(Sort.Direction.DESC, "username"));

//        final Slice<Member> page = memberRepository.findByAge(age, pageRequest);
//        final List<Member> content = page.getContent();
//
//        System.out.println(content);
//        assertThat(content.size()).isEqualTo(1);
//        assertThat(page.getNumber()).isEqualTo(2);
//        assertThat(page.isFirst()).isFalse();
//        assertThat(page.hasNext()).isFalse();
    }

    @Test
    public void list() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        final PageRequest pageRequest = PageRequest.of(2, 2, Sort.by(Sort.Direction.DESC, "username"));

        final List<Member> page = memberRepository.findByAge(age, pageRequest);
        assertThat(page.size()).isEqualTo(1);
    }

    @Test
    public void countQuery() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        final PageRequest pageRequest = PageRequest.of(2, 2, Sort.by(Sort.Direction.DESC, "username"));

        final Page<Member> page = memberRepository.findByAgeCount(age, pageRequest);

        final Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        assertThat(page.getContent().size()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(5);
    }

    @Test
    public void blukUpdate() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 30));
        memberRepository.save(new Member("member4", 40));
        memberRepository.save(new Member("member5", 50));

        final int resultCount = memberRepository.bulkAgePlus(20);


        assertThat(resultCount).isEqualTo(4);
    }

    @Test
    public void findMemberLazy(@Autowired EntityManager em) {
        final Team teamA = new Team("teamA");
        final Team teamB = new Team("teamA");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        final List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("member  = " + member.getUsername());
            System.out.println("member team = " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint(@Autowired EntityManager em) {
        final Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        final Member member = memberRepository.findReadOnlyByUsername("member1");
        member.setUsername("member2");

        em.flush();

    }
}