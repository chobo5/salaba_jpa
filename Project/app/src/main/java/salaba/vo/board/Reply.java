package salaba.vo.board;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.vo.Member;

@Builder
@Data // 클래스 내의 모든 필드에 대한 Getter와 Setter 메서드를 생성
@NoArgsConstructor
@AllArgsConstructor
public class Reply implements Serializable { // 답글
  private static final long serialVersionUID = 100L;

  private long replyNo;  // 답글 번호
  private long commentNo; // 댓글 번호
  private String content;  // 내용
  private Member writer;  // 답글 작성자
  private LocalDateTime createdDate; // 작성일
  private char state; // 상태

}
