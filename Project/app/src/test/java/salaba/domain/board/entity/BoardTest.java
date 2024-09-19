package salaba.domain.board.entity;

import org.junit.jupiter.api.Test;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.common.constants.WritingStatus;
import salaba.domain.member.entity.Member;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class BoardTest {


    @Test
    public void 게시물생성() {
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

        //when
        Board board = Board.create(title, content,scope, member);

        //then
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getBoardScope()).isEqualTo(scope);
        assertThat(board.getWriter()).isEqualTo(member);
    }

    @Test
    public void 게시물삭제() {
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
        board.delete();

        //then
        assertThat(board.getWritingStatus()).isEqualTo(WritingStatus.DELETED);
    }


    @Test
    public void 게시물수정() {
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
        board.modify("modifiedTitle", "modifiedContent", null);

        //then
        assertThat(board.getTitle()).isEqualTo("modifiedTitle");
        assertThat(board.getContent()).isEqualTo("modifiedContent");
        assertThat(board.getBoardScope()).isEqualTo(BoardScope.ALL);
    }


}