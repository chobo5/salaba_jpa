package salaba.domain.member.entity;

import lombok.*;
import salaba.domain.auth.entity.MemberRole;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.BaseEntity;
import salaba.domain.global.entity.Nation;
import salaba.domain.board.entity.Board;
import salaba.domain.member.constants.Gender;
import salaba.domain.auth.constant.MemberStatus;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.board.entity.Reply;
import salaba.domain.member.constants.Grade;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    private String telNo;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private LocalDateTime lastLoginDate;

    private LocalDateTime exitDate;

    private Integer warningCount;

    @Embedded
    @Column(nullable = true)
    private Address address;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeTelNo(String telNo) {
        this.telNo = telNo;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public static Member create(String email, String password, String name, String nickname, LocalDate birthday) {
        Member newMember = new Member();
        newMember.email = email;
        newMember.password = password;
        newMember.name = name;
        newMember.nickname = nickname;
        newMember.birthday = birthday;
        newMember.status = MemberStatus.NORMAL;
        newMember.lastLoginDate = LocalDateTime.now();
        newMember.warningCount = 0;
        newMember.grade = Grade.BRONZE;
        return newMember;
    }

    public void changeProfile(String name, Gender gender, Nation nation, Address address) {
        this.name = name != null ? name : this.name;
        this.gender = gender != null ? gender : this.gender;
        this.nation = nation != null ? nation : this.nation;
        this.address = address != null ? address : this.address;
    }

    public void resign() {
        exitDate = LocalDateTime.now();
        status = MemberStatus.RESIGN;
    }

    public void login() {
        lastLoginDate = LocalDateTime.now();
    }

    public void awake() {
        status = MemberStatus.NORMAL;
    }


}
