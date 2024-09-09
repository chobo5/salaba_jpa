package salaba.domain.rentalHome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchRentalHomeDto {
    private List<Long> ids;
    private Long totalCount;

}
