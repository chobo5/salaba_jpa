package salaba.vo.rental_home;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RentalHomePhoto implements Serializable { // 숙소 사진
  private static final long serialVersionUID = 100L;

  private long photoNo; // 사진 번호

  private String oriPhotoName; // 사진이름(Ori)

  private String uuidPhotoName; // 사진이름(uuid)

  private String photoExplanation; // 사진 설명

  private int photoOrder; // 사진순서
}
