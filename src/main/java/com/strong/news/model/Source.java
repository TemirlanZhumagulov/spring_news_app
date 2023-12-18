package com.strong.news.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id", updatable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 255, message = "Source name must be lesser than 255 characters")
    private String name;

}
