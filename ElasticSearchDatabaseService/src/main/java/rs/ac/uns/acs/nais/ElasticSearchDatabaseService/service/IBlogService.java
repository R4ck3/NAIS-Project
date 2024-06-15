package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.dto.BlogDTO;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.dto.BlogDTO2;


import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

public interface IBlogService {

    Blog createBlog(Blog blog);

    Blog updateBlog(String id, Blog updatedBlog);

    List<Blog> findAll();

    void deleteBlogById(String id);

    Optional<Blog> findBlogById(String id); 

    List<Blog> findByTitleOrAuthorId(String title, String authorId); 

    List<Blog> findByTitleContainingOrAuthorIdContaining(String title, String authorId);

    List<Blog> findByCustomQuery(String query);

    List<Blog> searchByDescriptionPhrase(String phrase);

    BlogDTO2 searchByDescriptionPhraseWithAggs(String phrase);

    List<Blog> searchByTitleOrDescriptionFuzzy(String searchTerm);

    List<Blog> findByAuthorId(String authorId);

    List<Blog> findByAuthorIdAndCategoryAndTitle(String authorId, String category, String title);

    List<Blog> findByAuthorIdAndCategoryAndTitleNoEM(String authorId, String category, String title);

    List<Blog> findAllOrderByCreatedAtDesc();

    BlogDTO findByCategoryAndDateRange(String category, String startDate, String endDate);

    List<Blog> findByDynamicQuery(String title, String category, String description, String country, String authorId, String startDate, String endDate);

    List<Blog> findByDynamicQuery2(String title, String category, String description, String country, String authorId, String startDate, String endDate);

    List<Blog> searchByDescriptionPhrasePDF(String phrase, String startDate, String endDate);

    List<Blog> findByCountryAndDateRange(String country, String startDate, String endDate);

    List<Blog> findByAuthorIdAndDateRange(String authorId, String startDate, String endDate);

    List<Blog> findBlogsByCategoryAndDateRange(String category, String startDate, String endDate);
}
