package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;
import java.io.IOException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/findAll")
    public List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) {
        postService.deletePost(id);
    }

    @GetMapping("/findByTitleOrAuthor")
    public List<Post> findByTitleOrAuthor(@RequestParam(value = "title") String title,
                                               @RequestParam(value = "author") String author) {
        return postService.findByTitleOrAuthor(title, author);
    }

    @GetMapping("/getPostsByCategoryAndDateRange")
    public List<Post> getPostsByCategoryAndDateRange(
        @RequestParam(value = "category") String category,
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate
    ) {
        return postService.findByCategoryAndDateRange(category, startDate, endDate);
    }
}
