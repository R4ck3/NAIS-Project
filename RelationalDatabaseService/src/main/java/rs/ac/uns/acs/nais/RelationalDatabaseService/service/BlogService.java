package rs.ac.uns.acs.nais.RelationalDatabaseService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.RelationalDatabaseService.model.Blog;
import rs.ac.uns.acs.nais.RelationalDatabaseService.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }
    
    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog updateBlog(Long id, Blog updatedBlog) {
        updatedBlog.setId(id);
        return blogRepository.save(updatedBlog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
