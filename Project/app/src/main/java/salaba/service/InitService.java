package salaba.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import salaba.entity.Nation;
import salaba.entity.board.*;
import salaba.entity.member.Member;
import salaba.entity.member.Role;
import salaba.entity.member.RoleName;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Component
public class InitService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init() {
//        Nation korea = new Nation(82, "kr");
//        Nation japan = new Nation(81, "jp");
//        Nation usa = new Nation(1, "us");
//        Nation china = new Nation(86, "ch");
//        Nation germany = new Nation(49, "ger");
//        Nation uk = new Nation(44, "uk");
//        Nation russia = new Nation(7, "rus");
//        em.persist(korea);
//        em.persist(japan);
//        em.persist(usa);
//        em.persist(china);
//        em.persist(germany);
//        em.persist(uk);
//        em.persist(russia);
//
//        Role member = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
//        Role manager = new Role(RoleName.MANAGER.getId(), RoleName.MANAGER);
//        Role admin = new Role(RoleName.ADMIN.getId(), RoleName.ADMIN);
//        em.persist(member);
//        em.persist(manager);
//        em.persist(admin);

        for (int i = 4; i <= 9; i++) {
            Member member = Member.createMember("test" + i + "@test.com", "Tt12241509!@", "test" + i, "test" + i, LocalDate.of(1999, 1, 1));
            em.persist(member);
            for (int j = 0; j < 4; j++) {
                Board board = Board.createBoard("board" + member.getId() + i, "board" + member.getId() + i, BoardCategory.FREE, BoardScope.ALL, member);
                em.persist(board);

                BoardLike boardLike = BoardLike.createBoardLike(board, member);
                em.persist(boardLike);

                for (int k = 0; k < 4; k++) {
                    Comment comment = Comment.createComment(board, "comment" + board.getId() + member.getId(), member);
                    em.persist(comment);
                }
            }
        }
    }

}
