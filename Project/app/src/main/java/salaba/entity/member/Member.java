package salaba.entity.member;

import lombok.*;
import salaba.entity.*;
import salaba.entity.board.Board;
import salaba.entity.board.Reply;
import salaba.entity.rental.Reservation;
import salaba.util.Validator;

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

    @OneToMany(mappedBy = "writer")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "targetMember")
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Point> pointHistories = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<BookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private Set<MemberRole> roles = new HashSet<>();

    public void changePassword(String password) {
        Validator.isValidPassword(password);
        this.password = password;
    }

    public void changeEmail(String email) {
        Validator.isValidEmail(email);
        this.email = email;
    }

    public void changeTelNo(String telNo) {
        this.telNo = telNo;
    }

    public void changeNickname(String nickname) {
        Validator.isValidNickname(nickname);
        this.nickname = nickname;
    }

    public static Member createMember(String email, String password, String name, String nickname, LocalDate birthday) {
        Member newMember = new Member();
        Validator.isValidEmail(email);
        newMember.email = email;
        Validator.isValidPassword(password);
        newMember.password = password;
        newMember.name = name;
        Validator.isValidNickname(nickname);
        newMember.nickname = nickname;
        newMember.birthday = birthday;
        newMember.status = MemberStatus.NORMAL;
        newMember.lastLoginDate = LocalDateTime.now();
        newMember.warningCount = 0;
        newMember.grade = Grade.BRONZE;
        return newMember;
    }

    public void changeProfile(String name, Gender gender, Nation nation, Address address) {
        this.name = name;
        this.gender = gender;
        this.nation = nation;
        this.address = address;
    }

    public void resign() {
        this.status = MemberStatus.RESIGN;
    }

}
