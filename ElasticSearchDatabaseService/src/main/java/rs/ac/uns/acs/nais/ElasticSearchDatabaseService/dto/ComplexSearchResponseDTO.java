package com.example.postservice.dto;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;
import java.util.List;
import java.util.Map;

public class ComplexSearchResponseDTO {
    private List<Post> posts;
    private Map<String, Long> authorAggregations;
    private Map<String, Long> blogIdAggregations;
    private Map<String, Double> averageLikesPerAuthor;

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

    public Map<String, Double> getAverageLikesPerAuthor() {
        return averageLikesPerAuthor;
    }

    public void setAverageLikesPerAuthor(Map<String, Double> averageLikesPerAuthor) {
        this.averageLikesPerAuthor = averageLikesPerAuthor;
    }
}
