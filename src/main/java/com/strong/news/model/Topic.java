package com.strong.news.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id", updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 255, message = "Topic name must be lesser than 255 characters")
    private String name;

    public Topic() {
    }

    public Topic(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
