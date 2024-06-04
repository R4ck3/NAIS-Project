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
}
