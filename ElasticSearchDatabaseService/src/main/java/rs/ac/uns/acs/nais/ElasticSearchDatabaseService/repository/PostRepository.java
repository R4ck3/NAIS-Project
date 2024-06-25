package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {
}
