package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.dto;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;

import java.util.List;
import java.util.Map;

public class BlogDTO2 {
    private List<Blog> blogs;
    private Map<String, Long> authorBlogCounts;
    private Map<String, Long> countryCounts;
    private Map<String, Long> categoryCounts;

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Map<String, Long> getAuthorBlogCounts() {
        return authorBlogCounts;
    }

    public void setAuthorBlogCounts(Map<String, Long> authorBlogCounts) {
        this.authorBlogCounts = authorBlogCounts;
    }

    public Map<String, Long> getCountryCounts() {
        return countryCounts;
    }

    public void setCountryCounts(Map<String, Long> countryCounts) {
        this.countryCounts = countryCounts;
    }

    public Map<String, Long> getCategoryCounts() {
        return categoryCounts;
    }

    public void setCategoryCounts(Map<String, Long> categoryCounts) {
        this.categoryCounts = categoryCounts;
    }
}
