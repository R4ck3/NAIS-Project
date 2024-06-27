package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {

    @Query("{\"match_all\": {}}")
    List<Post> findAll();

    List<Post> findByTitleOrAuthor(String title, String author);

    @Query("{\"bool\": {\"must\": [" +
        "{\"term\": {\"category\": \"?0\"}}," +
        "{\"range\": {\"createdAt\": {\"gte\": \"?1\", \"lte\": \"?2\"}}}" +
        "]}}")
    List<Post> findByCategoryAndDateRange(String category, String startDate, String endDate);

    @Query("{\"bool\": {\"should\": [" +
        "{\"fuzzy\": {\"content\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
        "{\"fuzzy\": {\"title\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}" +
        "]}}")
    List<Post> searchPostsFuzzy(String term);

    @Query("{\"bool\": {\"must\": [" +
        "{\"term\": {\"author\": \"?0\"}}," +
        "{\"range\": {\"createdAt\": {\"gte\": \"?1\", \"lte\": \"?2\"}}}," +
        "{\"range\": {\"likes\": {\"gt\": \"?3\"}}}" +
        "]}}")
    List<Post> findByAuthorAndDateRangeAndLikes(String author, String startDate, String endDate, int likes);
}
