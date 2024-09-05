package salaba.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public static MemberRole createMemberRole(Member member, Role role) {
        MemberRole memberRole = new MemberRole();
        memberRole.member = member;
        memberRole.role = role;
        member.getRoles().add(memberRole);
        return memberRole;
    }
}
