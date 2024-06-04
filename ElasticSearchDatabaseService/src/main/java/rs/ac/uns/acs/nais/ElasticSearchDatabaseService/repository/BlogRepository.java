package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends ElasticsearchRepository<Blog, String> {

    @Query("{\"match_all\": {}}")
    List<Blog> findAll();

    void deleteById(String id);

}
