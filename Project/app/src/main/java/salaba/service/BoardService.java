package salaba.service;

import salaba.dto.PageRequestDto;
import salaba.dto.PageResultDto;
import salaba.dto.board.BoardDetailDto;
import salaba.dto.board.BoardDto;
import salaba.entity.*;

import java.util.List;

public interface BoardService {
    Long register(BoardDetailDto boardDetailDto);

    PageResultDto<BoardDto, Object[]> getBoardList(PageRequestDto requestDto, int categoryNo);

    BoardDetailDto getBoard(Long boardNo);

    default Board dtoToEntity(BoardDetailDto dto) { //등록, 수정시 사용
        Member member = Member.builder()
                .memberNo(dto.getWriterNo())
                .build();

        BoardHead boardHead = BoardHead
                .builder()
                .headNo(dto.getHeadNo())
                .build();
        BoardCategory boardCategory = BoardCategory
                .builder()
                .categoryNo(dto.getCategoryNo())
                .build();

        return Board
                .builder()
                .boardHead(boardHead)
                .boardCategory(boardCategory)
                .title(dto.getTitle())
                .content(dto.getTitle())
                .writer(member)
                .build();
    }

    //목록 조회시 사용
    default BoardDto entityToDto(Board board, Member writer, Long commentCount, Long likeCount) {
        return BoardDto
                .builder()
                .boardNo(board.getBoardNo())
                .title(board.getTitle())
                .content(board.getContent())
                .writerNickname(writer.getNickname())
                .headName(board.getBoardHead().getHeadName())
                .categoryNo(board.getBoardCategory().getCategoryNo())
                .createdDate(board.getCreatedDate())
                .likeCount(likeCount)
                .viewCount(board.getViewCount())
                .commentCount(commentCount)
                .build();
    }

    //상세 조회시 사용
    default BoardDetailDto entityToDto(Board board, List<Comment> comments, List<Reply> replies) {
        return BoardDetailDto
                .builder()
                .boardNo(board.getBoardNo())
                .title(board.getTitle())
                .content(board.getContent())
                .writerNickname(board.getWriter().getNickname())
                .headNo(board.getBoardHead().getHeadNo())
                .headName(board.getBoardHead().getHeadName())
                .categoryNo(board.getBoardCategory().getCategoryNo())
                .categoryName(board.getBoardCategory().getCategoryName())
                .createdDate(board.getCreatedDate())
                .build();
    }


}
