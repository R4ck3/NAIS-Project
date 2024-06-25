package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<Post> getAllPosts();
    Optional<Post> getPostById(String id);
    Post createPost(Post post);
    void deletePost(String id);
    Post updatePost(String id, Post post);
}
