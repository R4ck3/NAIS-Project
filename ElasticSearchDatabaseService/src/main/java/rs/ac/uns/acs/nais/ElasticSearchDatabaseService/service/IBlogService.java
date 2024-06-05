package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;


import java.util.List;
import java.util.Optional;

public interface IBlogService {

    Blog createBlog(Blog blog);

    List<Blog> findAll();

    void deleteBlogById(String id);

    Optional<Blog> findBlogById(String id); 

    List<Blog> findByTitleOrAuthorId(String title, String authorId); 

    List<Blog> findByTitleContainingOrAuthorIdContaining(String title, String authorId);

    List<Blog> findByCustomQuery(String query);

    List<Blog> searchByDescriptionPhrase(String phrase);

    List<Blog> searchByTitleOrDescriptionFuzzy(String searchTerm);

    List<Blog> findByAuthorId(String authorId);

    List<Blog> findByAuthorIdAndCategoryAndTitle(String authorId, String category, String title);

    List<Blog> findByAuthorIdAndCategoryAndTitleNoEM(String authorId, String category, String title);

    List<Blog> findAllOrderByCreatedAtDesc();
}
