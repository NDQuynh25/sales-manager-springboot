package com.example.sales_manager.entity;


import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "feedback_reviews", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class FeedbackReview extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ n-1 với bảng Review
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    // Quan hệ n-1 với bảng User
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "comment_time")
    private Instant commentTime;

    @OneToMany(mappedBy = "feedbackReview")
    private List<FeedbackReviewImage> feedbackReviewImages;

    public FeedbackReview() {
    }

    public FeedbackReview(Review review, User user, String content, Instant commentTime, List<FeedbackReviewImage> feedbackReviewImages) {
        this.review = review;
        this.user = user;
        this.content = content;
        this.commentTime = commentTime;
        this.feedbackReviewImages = feedbackReviewImages;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Review getReview() {
        return this.review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCommentTime() {
        return this.commentTime;
    }

    public void setCommentTime(Instant commentTime) {
        this.commentTime = commentTime;
    }

    public List<FeedbackReviewImage> getFeedbackReviewImages() {
        return this.feedbackReviewImages;
    }

    public void setFeedbackReviewImages(List<FeedbackReviewImage> feedbackReviewImages) {
        this.feedbackReviewImages = feedbackReviewImages;
    }
    

}
