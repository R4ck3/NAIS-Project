package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.dto;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;

import java.util.List;

public class BlogDTO3 {
    private String userId;
    private String fullName;
    private String username;
    private String email;
    private List<Blog> blogs;


    public BlogDTO3() {
    }

    public BlogDTO3(String userId, String fullName, String username, String email, List<Blog> blogs) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.blogs = blogs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
