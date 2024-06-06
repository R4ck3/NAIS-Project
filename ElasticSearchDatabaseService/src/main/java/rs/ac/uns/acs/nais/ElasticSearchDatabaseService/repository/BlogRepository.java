package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends ElasticsearchRepository<Blog, String> {

    @Query("{\"match_all\": {}}")
    List<Blog> findAll();

    void deleteById(String id);

    @Query("{\"term\": {\"blogId\": \"?0\"}}")
    Optional<Blog> findById(String id);

    List<Blog> findByTitleOrAuthorId(String title, String authorId);

    List<Blog> findByTitleContainingOrAuthorIdContaining(String title, String authorId);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"description\": \"?0\"}}]}}")
    List<Blog> findByCustomQuery(String query);

    @Query("{\"match_phrase\":{\"description\":\"?0\"}}")
    List<Blog> searchByDescriptionPhrase(String phrase);

    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"title^3\",\"description\"],\"fuzziness\":\"AUTO\"}}")
    List<Blog> searchByTitleOrDescriptionFuzzy(String searchTerm);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authorId\": \"?0\"}}]}}")
    List<Blog> findByAuthorId(String authorId);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authorId\": \"?0\"}}, {\"match\": {\"category\": \"?1\"}}, {\"match\": {\"title\": \"?2\"}}]}}")
    List<Blog> findByAuthorIdAndCategoryAndTitle(String authorId, String category, String title);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authorId\": \"?0\"}}, {\"query_string\": {\"query\": \"*?1*\", \"fields\": [\"category\"]}}, {\"query_string\": {\"query\": \"*?2*\", \"fields\": [\"title\"]}}]}}")
    List<Blog> findByAuthorIdAndCategoryAndTitleNoEM(String authorId, String category, String title);

    @Query("{\"bool\": {\"must\": [{\"term\": {\"category\": \"?0\"}}, {\"range\": {\"createdAt\": {\"gte\": \"?1\", \"lte\": \"?2\"}}}]}}")
    List<Blog> findByCategoryAndDateRange(String category, String startDate, String endDate);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\" }}," +
           " {\"term\": {\"category\": \"?1\" }}," +
           " {\"match_phrase\": {\"description\": \"?2\" }}," +
           " {\"term\": {\"country\": \"?3\" }}," +
           " {\"term\": {\"authorId\": \"?4\" }}," +
           " {\"range\": {\"createdAt\": {\"gte\": \"?5\", \"lte\": \"?6\" }}}]}}")
    List<Blog> findByDynamicQuery(String title, String category, String description, String country, String authorId, String startDate, String endDate);

    @Query("{\"bool\": {\"must\": [" +
           "{\"multi_match\": {\"query\": \"?0*\", \"fields\": [\"title\"], \"fuzziness\": \"AUTO\"}}," +
           "{\"term\": {\"category\": \"?1\"}}," +
           "{\"match_phrase\": {\"description\": \"?2\"}}," +
           "{\"multi_match\": {\"query\": \"?3*\", \"fields\": [\"country\"], \"fuzziness\": \"AUTO\"}}," +
           "{\"term\": {\"authorId\": \"?4\"}}," +
           "{\"range\": {\"createdAt\": {\"gte\": \"?5\", \"lte\": \"?6\"}}}" +
         "]}}")
    List<Blog> findByDynamicQuery2(String title, String category, String description, String country, String authorId, String startDate, String endDate);


    @Query("{\"bool\": {\"must\": [{\"range\": {\"createdAt\": {\"gte\": \"2022-01-01\", \"lte\": \"2023-12-31\"}}}," +
           " {\"match_phrase\": {\"description\": \"?0\"}}]}}")
    List<Blog> searchByDescriptionPhrasePDF(String phrase);

    
}
