package com.strong.news.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    @Size(min = 1, max = 255, message = "Title name must be lesser than 255 characters")
    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Source source;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
