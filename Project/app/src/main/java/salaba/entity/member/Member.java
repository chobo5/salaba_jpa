package salaba.entity.member;

import lombok.*;
import salaba.entity.*;
import salaba.entity.board.Board;
import salaba.entity.board.Comment;
import salaba.entity.board.Reply;
import salaba.entity.board.WritingReport;
import salaba.entity.rental.Reservation;

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



}
