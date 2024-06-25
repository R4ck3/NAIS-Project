package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import java.util.List;
import java.util.Optional;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;

public interface IPostService {
    Post save(Post post);
    Optional<Post> findById(String id);
    List<Post> findByContent(String content);
    void deleteById(String id);
    void populateDatabase();
}
