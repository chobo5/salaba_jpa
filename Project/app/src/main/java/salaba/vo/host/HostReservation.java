package salaba.vo.host;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HostReservation implements Serializable { // 호스트 예약내역 (결제완료)

  private static final long serialVersionUID = 100L;

  private String rentalHomeName; //숙소 이름
  private String memberName; // 예약자명
  private long reservationNo; // 예약 번호
  private long memberNo; // 예약자번호
  private long hostNo; // 호스트번호
  private long rentalHomeNo; // 숙소 번호
  private LocalDateTime startDate; // 시작일
  private LocalDateTime endDate; // 종료일
  private char state; // 상태
  private LocalDateTime paymentDate; // 결제일
  private int amount; // 결제금액
  private int numberOfPeople; // 이용 인원수

}
