package salaba.vo.board;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data // 클래스 내의 모든 필드에 대한 Getter와 Setter 메서드를 생성
@NoArgsConstructor
@AllArgsConstructor
public class BoardFile implements Serializable { // 게시판, 신고 첨부파일

  private static final long serialVersionUID = 100L;

  private long fileNo; // 파일 번호
  private long boardNo; // 게시판 번호
  private String oriFileName; // 원본 파일명
  private String uuidFileName; // UUID 파일명
  private int order; // 사진 순서

}
