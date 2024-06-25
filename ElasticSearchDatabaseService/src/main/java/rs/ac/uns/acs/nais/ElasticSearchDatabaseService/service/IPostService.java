package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import java.util.List;

public interface IPostService {
    List<Post> getAllPosts();
    Post createPost(Post post);
    Post getPostById(String id);
    void deletePostById(String id);
    Post updatePost(String id, Post updatedPost);
}
