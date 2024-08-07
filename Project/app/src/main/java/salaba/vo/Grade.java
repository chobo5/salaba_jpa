package salaba.vo;

import java.io.Serializable;

import lombok.Data;


@Data
public class Grade implements Serializable {

  private static final long serialVersionUID = 100L;

  private int gradeNo; // 등급번호
  private String gradeName; // 등급명

}
