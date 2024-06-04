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

    @Query("{\"term\": {\"_id\": \"?0\"}}")
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

}
