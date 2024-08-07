package salaba.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Qna implements Serializable {

  private static final long serialVersionUID = 100L;

  private long questionNo;
  private String answer; // 답변
  private LocalDateTime answerDate; // 답변 날짜
  
}
