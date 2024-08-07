package salaba.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import salaba.vo.rental_home.RentalHomePhoto;

@Data
public class Reservation implements Serializable {

  private static final long serialVersionUID = 100L;

  private long reservationNo; // 예약 번호
  private long rentalHomeNo; // 숙소 번호
  private long memberNo; // 회원 번호
  private String name; // 숙소명
  private LocalDateTime startDate; // 체크인
  private LocalDateTime endDate; // 체크아웃
  private int numberOfPeople; // 인원수
  private char state; // 예약상태
  private String lat;
  private String lon;
  private List<RentalHomePhoto> photoList; //숙소사진파일 리스트
  private String nickname; // 닉네임
  private String telNo; // 전화번호
  private String photo; // 숙소 주인 사진
  private String rentalHomeRule; // 이용규칙
  private String address; // 주소
  private String chatFileName; // 채팅파일명
  private Payment payment; // 결제정보
}
