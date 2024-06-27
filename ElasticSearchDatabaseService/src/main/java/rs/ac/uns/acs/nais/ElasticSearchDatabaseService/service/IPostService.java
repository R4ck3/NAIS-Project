package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import com.example.postservice.dto.PostTermSearchResponseDTO;
import com.example.postservice.dto.PostSearchResponseDTO;

import java.util.List;

public interface IPostService {

    List<Post> findAll();

    Post getPostById(String id);

    Post createPost(Post post);

    Post updatePost(String id, Post post);

    void deletePost(String id);

    List<Post> findByTitleOrAuthor(String title, String author); 

    List<Post> findByCategoryAndDateRange(String category, String startDate, String endDate);

    List<Post> searchPostsFuzzy(String term);

    PostSearchResponseDTO findByAuthorAndDateRangeAndLikes(String author, String startDate, String endDate, int likes);

    PostSearchResponseDTO findByTitleOrDescriptionAndDateRange(String text, String startDate, String endDate);

    PostTermSearchResponseDTO findByTitleAndCategoryAndLanguageAndDateRange(String title, String category, String language, String startDate, String endDate);
}
