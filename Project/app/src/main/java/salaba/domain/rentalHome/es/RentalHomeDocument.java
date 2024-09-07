package salaba.domain.rentalHome.es;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.util.List;

@Document(indexName = "rental_homes")
@Getter
@AllArgsConstructor
public class RentalHomeDocument {
    @Id
    private Long id;

    private String name; // 숙소 이름

    private String regionName; // 지역 이름

    private List<String> themes;

    private Double price; // 가격

    private Double rating; // 평점

    private Integer reviewCount; // 리뷰 수

    private Integer salesCount; // 판매 수

    private String description; // 숙소 설명

    private double score;

    public void calculateScore(Integer minPrice, Integer maxReviewCount, Integer maxSalesCount) {
        double normalizedPrice = Math.min(minPrice / price, 1.0);
        double normalizedRating = Math.min(rating / 5.0, 1.0);
        double normalizedReviewCount = Math.min((double) reviewCount / maxReviewCount, 1.0);
        double normalizedSalesCount = Math.min((double) salesCount / maxSalesCount, 1.0);

        score = normalizedPrice * 0.2
                + normalizedReviewCount * 0.3
                + normalizedRating * 0.3
                + normalizedSalesCount * 0.3;

    }

}
