package com.example.postservice.dto;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import java.util.List;
import java.util.Map;

public class PostTermSearchResponseDTO {
    private List<Post> posts;
    private Map<String, Long> authorAggregations;
    private Map<String, Long> blogIdAggregations;

    // Getters and Setters

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Map<String, Long> getAuthorAggregations() {
        return authorAggregations;
    }

    public void setAuthorAggregations(Map<String, Long> authorAggregations) {
        this.authorAggregations = authorAggregations;
    }

    public Map<String, Long> getBlogIdAggregations() {
        return blogIdAggregations;
    }

    public void setBlogIdAggregations(Map<String, Long> blogIdAggregations) {
        this.blogIdAggregations = blogIdAggregations;
    }
}
