package salaba.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "member")
@Getter
@ToString(exclude = {"roleSet", "grade", "nation"})
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long memberNo;

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

    @CreationTimestamp //현재시간이 저장될 때, 자동으로 생성
    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "tel_no")
    private String telNo;

    private Character state;

    private Character gender;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "exit_date")
    private LocalDateTime exitDate;

    @Column(name = "warning_count")
    private Integer warningCount;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "grade_no", nullable = false)
//    private Grade grade;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "nation_no")
//    private Nation nation;

//    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
////    private List<Board> boardList;

    @ManyToMany
    @JoinTable(name = "member_role",
            joinColumns = @JoinColumn(name = "member_no"),
            inverseJoinColumns = @JoinColumn(name = "role_no"))
    private Set<Role> roleSet;

}
