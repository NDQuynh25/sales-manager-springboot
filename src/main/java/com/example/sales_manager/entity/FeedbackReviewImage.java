package com.example.sales_manager.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "feedback_review_images", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class FeedbackReviewImage extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // quan hệ n-1 với FeedbackReview
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_review_id")
    private FeedbackReview feedbackReview;

    private String imageUrl;

    public FeedbackReviewImage() {
    }

    public FeedbackReviewImage(FeedbackReview feedbackReview, String imageUrl) {
        this.feedbackReview = feedbackReview;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeedbackReview getFeedbackReview() {
        return this.feedbackReview;
    }

    public void setFeedbackReview(FeedbackReview feedbackReview) {
        this.feedbackReview = feedbackReview;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
}
