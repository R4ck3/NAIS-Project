package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.BlogService;

import java.io.IOException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/blogs.json")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/createBlog")
    public Blog createBlog(@RequestBody Blog blog) {
        return blogService.createBlog(blog);
    }

    @GetMapping("/findAll")
    public List<Blog> findAll() {
        return blogService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogById(@PathVariable String id) {
        blogService.deleteBlogById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> findBlogById(@PathVariable String id) {
        Optional<Blog> blog = blogService.findBlogById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findByTitleOrAuthorId")
    public List<Blog> findByTitleOrAuthorId(@RequestParam(value = "title") String title,
                                               @RequestParam(value = "authorId") String authorId) {
        return blogService.findByTitleOrAuthorId(title, authorId);
    }

    @GetMapping("/findByTitleContainingOrAuthorIdContaining")
    public List<Blog> findByTitleContainingOrAuthorIdContaining(@RequestParam(value = "title") String title,
                                                                   @RequestParam(value = "authorId") String authorId) {
        return blogService.findByTitleContainingOrAuthorIdContaining(title, authorId);
    }

    @GetMapping("/findByCustomQuery")
    public List<Blog> findByCustomQuery(@RequestParam(value = "query") String query) {
        return blogService.findByCustomQuery(query);
    }

    @GetMapping("/searchByDescriptionPhrase")
    public List<Blog> searchByDescriptionPhrase(@RequestParam(value = "phrase") String phrase) {
        return blogService.searchByDescriptionPhrase(phrase);
    }

    @GetMapping("/searchByTitleOrDescriptionFuzzy")
    public List<Blog> searchByTitleOrDescriptionFuzzy(@RequestParam(value = "searchTerm") String searchTerm) {
        return blogService.searchByTitleOrDescriptionFuzzy(searchTerm);
    }

}
