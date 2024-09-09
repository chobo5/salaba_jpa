package salaba.domain.rentalHome.es;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHomeESRepository extends ElasticsearchRepository<RentalHomeDocument, Long> {
    SearchHits<RentalHomeDocument> findByRegionName(String regionName);
    SearchHits<RentalHomeDocument> findByThemes(List<String> themes);
    SearchHits<RentalHomeDocument> findByName(String name);
}