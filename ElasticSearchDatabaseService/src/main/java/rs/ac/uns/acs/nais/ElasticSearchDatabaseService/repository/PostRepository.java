package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
    List<Post> findByContent(String content);
}
