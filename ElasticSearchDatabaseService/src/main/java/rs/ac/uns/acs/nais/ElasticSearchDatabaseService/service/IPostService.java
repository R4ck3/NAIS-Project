package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;

import java.util.List;

public interface IPostService {

    List<Post> findAll();

    Post getPostById(String id);

    Post createPost(Post post);

    Post updatePost(String id, Post post);

    void deletePost(String id);

    List<Post> findByTitleOrAuthor(String title, String author); 

    List<Post> findByCategoryAndDateRange(String category, String startDate, String endDate);
}
