package salaba.domain.board.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.member.entity.Member;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class BoardTest {

    Member member;
    @BeforeEach
    public void 회원생성() {
        //given
        final String email = "test@test.com";
        final String password = "Aa123456@";
        final String nickname = "test_nickname";
        final String name = "name";
        final LocalDate birthday = LocalDate.of(1996, 10, 8);

        member = Member.createMember(email, password, name, nickname, birthday);
    }

    @Test
    public void 게시물생성() {
        //given
        String title = "테스트 제목";
        String content = "테스트 내용";
        BoardScope scope = BoardScope.ALL;

        //when
        Board board = Board.createBoard(title, content,scope, member);

        //then
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getBoardScope()).isEqualTo(scope);
        assertThat(board.getWriter()).isEqualTo(member);
    }



}