package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;

import java.util.List;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);
}
