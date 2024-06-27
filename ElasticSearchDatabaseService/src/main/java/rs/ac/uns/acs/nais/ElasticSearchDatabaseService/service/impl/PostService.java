package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.PostRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
