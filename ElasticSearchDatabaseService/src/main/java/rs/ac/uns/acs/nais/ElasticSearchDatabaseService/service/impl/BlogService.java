package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.BlogRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IBlogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.Collections; 
import java.util.Comparator;


@Service
public class BlogService implements IBlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public void deleteBlogById(String id) {
        blogRepository.deleteById(id);
    }

    public Optional<Blog> findBlogById(String id) {
        return blogRepository.findById(id);
    }

    public List<Blog> findByTitleOrAuthorId(String title, String authorId) {
        return blogRepository.findByTitleOrAuthorId(title, authorId);
    }

    public List<Blog> findByTitleContainingOrAuthorIdContaining(String title, String authorId) {
        return blogRepository.findByTitleContainingOrAuthorIdContaining(title, authorId);
    }

    public List<Blog> findByCustomQuery(String query) {
            return blogRepository.findByCustomQuery(query);
    }

    public List<Blog> searchByDescriptionPhrase(String phrase) {
        return blogRepository.searchByDescriptionPhrase(phrase);
    }

    public List<Blog> searchByTitleOrDescriptionFuzzy(String searchTerm) {
        return blogRepository.searchByTitleOrDescriptionFuzzy(searchTerm);
    }

    public List<Blog> findByAuthorId(String authorId) {
        return blogRepository.findByAuthorId(authorId);
    }

    public List<Blog> findByAuthorIdAndCategoryAndTitle(String authorId, String category, String title) {
        return blogRepository.findByAuthorIdAndCategoryAndTitle(authorId, category, title);
    }

    public ResponseEntity<Blog> updateBlogDescription(String blogId, String newDescription) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blog.setDescription(newDescription);
            blogRepository.save(blog);
            return ResponseEntity.ok(blog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Blog> findAllOrderByCreatedAtDesc() {
        List<Blog> blogs = blogRepository.findAll();
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }

    public List<Blog> findByAuthorIdAndCategoryAndTitleNoEM(String authorId, String category, String title) {
        return blogRepository.findByAuthorIdAndCategoryAndTitleNoEM(authorId, category, title);
    }

    public List<Blog> findByCategoryAndDateRange(String category, String startDate, String endDate) {
        List<Blog> blogs = blogRepository.findByCategoryAndDateRange(category, startDate, endDate);
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }

    public List<Blog> findByDynamicQuery(String title, String category, String description, String country, String authorId, String startDate, String endDate) {
        List<Blog> blogs = blogRepository.findByDynamicQuery(title, category, description, country, authorId, startDate, endDate);
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }

    public List<Blog> findByDynamicQuery2(String title, String category, String description, String country, String authorId, String startDate, String endDate) {
        List<Blog> blogs = blogRepository.findByDynamicQuery2(title, category, description, country, authorId, startDate, endDate);
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }
}
