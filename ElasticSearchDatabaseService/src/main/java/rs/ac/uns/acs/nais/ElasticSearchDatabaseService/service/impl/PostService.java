package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.PostRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IPostService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository repository;

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        repository.findAll().forEach(posts::add);
        return posts;
    }

    @Override
    public Post createPost(Post post) {
        return repository.save(post);
    }

    @Override
    public Post getPostById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deletePostById(String id) {
        repository.deleteById(id);
    }

    @Override
    public Post updatePost(String id, Post updatedPost) {
        if (repository.existsById(id)) {
            updatedPost.setId(id);
            return repository.save(updatedPost);
        }
        return null;
    }
}
