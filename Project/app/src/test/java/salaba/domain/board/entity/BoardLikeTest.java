package salaba.domain.board.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.member.entity.Member;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BoardLikeTest {

    @Test
    @DisplayName(value = "게시물 좋아요 생성")
    public void 게시물좋아요생성() {
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
        BoardLike boardLike = BoardLike.create(board, member);

        //then
        assertThat(boardLike.getBoard()).isEqualTo(board);
        assertThat(boardLike.getMember()).isEqualTo(member);
        assertThat(board.getBoardLikes().get(0)).isEqualTo(boardLike);

    }

    @Test
    @DisplayName(value = "게시물 좋아요 취소")
    public void 게시물좋아요취소() {
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

        BoardLike boardLike = BoardLike.create(board, member);

        //when
        boardLike.cancel();


        //then
        assertThat(board.getBoardLikes()).isEmpty();

    }

}