package salaba.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import salaba.entity.Nation;
import salaba.entity.Region;
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
//        Nation korea = em.find(Nation.class, 82);
//        Region seoul = new Region("seoul", korea);
//        Region suwon = new Region("suwon", korea);
//        Region hwaseong = new Region("hwaseong", korea);
//        Region yongin = new Region("yongin", korea);
//        em.persist(seoul);
//        em.persist(suwon);
//        em.persist(hwaseong);
//        em.persist(yongin);

//
//        Role member = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
//        Role manager = new Role(RoleName.MANAGER.getId(), RoleName.MANAGER);
//        Role admin = new Role(RoleName.ADMIN.getId(), RoleName.ADMIN);
//        em.persist(member);
//        em.persist(manager);
//        em.persist(admin);

//        for (int i = 4; i <= 9; i++) {
//            Member newMember = Member.createMember("test" + i + "@test.com", "Tt12241509!@", "test" + i, "test" + i, LocalDate.of(1999, 1, 1));
//            em.persist(newMember);
//            for (int j = 0; j < 4; j++) {
//                Board board = Board.createBoard("board" + newMember.getId() + i, "board" + newMember.getId() + i, BoardCategory.FREE, BoardScope.ALL, newMember);
//                em.persist(board);
//
//                BoardLike boardLike = BoardLike.createBoardLike(board, newMember);
//                em.persist(boardLike);
//
//                for (int k = 0; k < 4; k++) {
//                    Reply reply = Reply.createReply(board, "reply" + board.getId() + newMember.getId(), newMember);
//                    em.persist(reply);
//                    for (int l = 0; l < 3; l++) {
//                        Reply reReply = Reply.createReplyToReply(reply, "reReply" + board.getId() + newMember.getId(), newMember);
//                        em.persist(reReply);
//                    }
//                }
//            }
//        }
    }

}
