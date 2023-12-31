package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(of = {"id", "username", "age"})
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
@NamedEntityGraph(
        name = "Member.all",
        attributeNodes = @NamedAttributeNode("team")
)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    Long id;
    String username;
    int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    Team team;
    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
