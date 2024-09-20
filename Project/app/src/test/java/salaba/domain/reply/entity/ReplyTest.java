package salaba.domain.reply.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.board.entity.Board;
import salaba.domain.common.constants.WritingStatus;
import salaba.domain.member.entity.Member;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReplyTest {

    @Test
    public void 댓글작성() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.create(email, password, name, nickname, birthday);
        String title = "테스트 제목";
        String content = "테스트 내용";
        BoardScope scope = BoardScope.ALL;

        Board board = Board.create(title, content,scope, member);

        //when
        Reply re = Reply.createReply(board, "댓글", member);
        Reply reRe = Reply.createReplyToReply(re, "대댓글", member);

        //then
        assertThat(board.getReplies().get(0)).isEqualTo(re);
        assertThat(re.getRepliesToReply().get(0)).isEqualTo(reRe);
    }

    public void 댓글수정() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.create(email, password, name, nickname, birthday);
        String title = "테스트 제목";
        String content = "테스트 내용";
        BoardScope scope = BoardScope.ALL;

        Board board = Board.create(title, content,scope, member);
        Reply re = Reply.createReply(board, "댓글", member);
        Reply reRe = Reply.createReplyToReply(re, "대댓글", member);

        //when
        re.modify("댓글 수정");
        reRe.modify("대댓글 수정");

        //then
        assertThat(board.getReplies().get(0).getContent()).isEqualTo("댓글 수정");
        assertThat(re.getRepliesToReply().get(0).getContent()).isEqualTo("대댓글 수정");
    }

    public void 댓글삭제() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.create(email, password, name, nickname, birthday);
        String title = "테스트 제목";
        String content = "테스트 내용";
        BoardScope scope = BoardScope.ALL;

        Board board = Board.create(title, content,scope, member);
        Reply re = Reply.createReply(board, "댓글", member);

        //when
        re.deleteReply();

        assertThat(re.getWritingStatus()).isEqualTo(WritingStatus.DELETED);
        assertThat(board.getReplies().size()).isEqualTo(1);

    }

    public void 대댓글삭제() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        Member member = Member.create(email, password, name, nickname, birthday);
        String title = "테스트 제목";
        String content = "테스트 내용";
        BoardScope scope = BoardScope.ALL;

        Board board = Board.create(title, content,scope, member);
        Reply re = Reply.createReply(board, "댓글", member);
        Reply reRe = Reply.createReplyToReply(re, "대댓글", member);

        //when
        reRe.deleteReply();

        assertThat(reRe.getWritingStatus()).isEqualTo(WritingStatus.DELETED);
        assertThat(re.getRepliesToReply().size()).isEqualTo(0);

    }


}