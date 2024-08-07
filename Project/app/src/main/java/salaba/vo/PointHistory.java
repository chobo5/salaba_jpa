package salaba.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PointHistory implements Serializable {

  private static final long serialVersionUID = 100L;

  private long historyNo;
  private String saveContent; // 적립 내용
  private long savePoint; // 적립 포인트
  private LocalDateTime saveDate; // 적립 날짜
  private long totalPoint;

}
