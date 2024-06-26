package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import com.example.postservice.dto.ComplexSearchResponseDTO;
import com.example.postservice.dto.PostTermSearchResponseDTO;
import com.example.postservice.dto.PostSearchResponseDTO;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.PostRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IPostService;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;
import java.util.Collections; 
import java.util.Comparator;
import java.util.Map;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post getPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(String id, Post post) {
        if (postRepository.existsById(id)) {
            post.setId(id);
            return postRepository.save(post);
        } else {
            return null;
        }
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public List<Post> findByTitleOrAuthor(String title, String author) {
        return postRepository.findByTitleOrAuthor(title, author);
    }
    
    public List<Post> findByCategoryAndDateRange(String category, String startDate, String endDate) {
        return postRepository.findByCategoryAndDateRange(category, startDate, endDate);
    }

    public List<Post> searchPostsFuzzy(String term) {
        return postRepository.searchPostsFuzzy(term);
    }

    private Map<String, Long> countByLanguage(List<Post> posts) {
        return posts.stream()
                .collect(Collectors.groupingBy(Post::getLanguage, Collectors.counting()));
    }

    private Map<String, Long> countByCategory(List<Post> posts) {
        return posts.stream()
                .collect(Collectors.groupingBy(Post::getCategory, Collectors.counting()));
    }

    private Map<String, Long> countByAuthor(List<Post> posts) {
        return posts.stream()
                .collect(Collectors.groupingBy(Post::getAuthor, Collectors.counting()));
    }

    private Map<String, Long> countByBlogId(List<Post> posts) {
        return posts.stream()
                .collect(Collectors.groupingBy(Post::getBlogId, Collectors.counting()));
    }
    
    private Map<String, Double> calculateAverageLikesPerAuthor(List<Post> posts) {
        return posts.stream()
                .collect(Collectors.groupingBy(Post::getAuthor, 
                        Collectors.averagingInt(post -> Integer.parseInt(post.getLikes()))));
    }

    public PostSearchResponseDTO findByAuthorAndDateRangeAndLikes(String author, String startDate, String endDate, int likes) {
        List<Post> posts = postRepository.findByAuthorAndDateRangeAndLikes(author, startDate, endDate, likes);

        Map<String, Long> languageAggregations = countByLanguage(posts);

        Map<String, Long> categoryCounts = countByCategory(posts);

        PostSearchResponseDTO ptsd = new PostSearchResponseDTO();
        ptsd.setPosts(posts);
        ptsd.setLanguageAggregations(languageAggregations);
        ptsd.setCategoryAggregations(categoryCounts);

        return ptsd;
    }

    @Override
    public PostSearchResponseDTO findByTitleOrDescriptionAndDateRange(String text, String startDate, String endDate) {
        List<Post> posts = postRepository.findByTitleOrDescriptionAndDateRange(text, startDate, endDate);

        posts.sort(Comparator.comparing(Post::getLikes).reversed());

        Map<String, Long> languageAggregations = countByLanguage(posts);

        Map<String, Long> categoryCounts = countByCategory(posts);

        PostSearchResponseDTO ptsd = new PostSearchResponseDTO();
        ptsd.setPosts(posts);
        ptsd.setLanguageAggregations(languageAggregations);
        ptsd.setCategoryAggregations(categoryCounts);

        return ptsd;
    }

    @Override
    public PostTermSearchResponseDTO findByTitleAndCategoryAndLanguageAndDateRange(String title, String category, String language, String startDate, String endDate) {
        List<Post> posts = postRepository.findByTitleAndCategoryAndLanguageAndDateRange(title, category, language, startDate, endDate);

        Map<String, Long> authorAggregations = countByAuthor(posts);
        Map<String, Long> blogIdAggregations = countByBlogId(posts);

        PostTermSearchResponseDTO ptsd = new PostTermSearchResponseDTO();
        ptsd.setPosts(posts);
        ptsd.setAuthorAggregations(authorAggregations);
        ptsd.setBlogIdAggregations(blogIdAggregations);

        return ptsd;
    }

    @Override
    public ComplexSearchResponseDTO findByTitleAndCategoryAndDateRange(String title, String category, String startDate, String endDate) {
        List<Post> posts = postRepository.findByTitleAndCategoryAndDateRange(title, category, startDate, endDate);

        // Perform aggregations
        Map<String, Long> authorAggregations = countByAuthor(posts);
        Map<String, Long> blogIdAggregations = countByBlogId(posts);
        Map<String, Double> averageLikesPerAuthor = calculateAverageLikesPerAuthor(posts);

        ComplexSearchResponseDTO responseDTO = new ComplexSearchResponseDTO();
        responseDTO.setPosts(posts);
        responseDTO.setAuthorAggregations(authorAggregations);
        responseDTO.setBlogIdAggregations(blogIdAggregations);
        responseDTO.setAverageLikesPerAuthor(averageLikesPerAuthor);

        return responseDTO;
    }
}
