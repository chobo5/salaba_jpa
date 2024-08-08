package salaba.entity.member;

import lombok.*;
import salaba.entity.*;
import salaba.entity.board.Board;
import salaba.entity.board.Comment;
import salaba.entity.board.Reply;
import salaba.entity.board.WritingReport;
import salaba.entity.rental.Reservation;
import salaba.util.PasswordValidator;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
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
    private Address address;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @OneToMany(mappedBy = "writer")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Alarm> alarmList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Point> pointHistory = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<BookMark> bookMarkList = new ArrayList<>();

    @OneToMany(mappedBy = "reporter")
    private List<WritingReport> writingReportList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String password) throws ValidationException {
        if (PasswordValidator.isValidPassword(password)) {
            this.password = password;
        } else {
            throw new ValidationException("비밀번호는 최소 8자 이상, 하나 이상의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
        }
    }

    public Member Join(String email, String password, String name, String nickname) throws ValidationException {
        if (!PasswordValidator.isValidPassword(password)) {
            throw new ValidationException("비밀번호는 최소 8자 이상, 하나 이상의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
        }

        Member newMember = new Member();
        newMember.email = email;
        newMember.password = password;
        newMember.name = name;
        newMember.nickname = nickname;
        newMember.status = MemberStatus.NORMAL;
        newMember.lastLoginDate = LocalDateTime.now();
        newMember.warningCount = 0;
        newMember.grade = Grade.BRONZE;
        return newMember;
    }
    
    public Member updateProfile()
}
