package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;

import java.util.List;
import java.util.Optional;

public interface IBlogService {

    Blog createBlog(Blog blog);

    List<Blog> findAll();

    void deleteBlogById(String id);

}
