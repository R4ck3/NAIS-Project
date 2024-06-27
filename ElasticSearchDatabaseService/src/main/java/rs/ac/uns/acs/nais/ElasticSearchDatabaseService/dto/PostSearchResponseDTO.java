package com.example.postservice.dto;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Post;

import java.util.List;
import java.util.Map;

public class PostSearchResponseDTO {
    private List<Post> posts;
    private Map<String, Long> languageAggregations;
    private Map<String, Long> categoryAggregations;

    // Getters and Setters

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Map<String, Long> getLanguageAggregations() {
        return languageAggregations;
    }

    public void setLanguageAggregations(Map<String, Long> languageAggregations) {
        this.languageAggregations = languageAggregations;
    }

    public Map<String, Long> getCategoryAggregations() {
        return categoryAggregations;
    }

    public void setCategoryAggregations(Map<String, Long> categoryAggregations) {
        this.categoryAggregations = categoryAggregations;
    }
}
