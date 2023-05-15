package com.strong.news.repository;

import com.strong.news.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    Page<News> findAllByOrderByCreatedDateDesc(Pageable pageable);
    Page<News> findBySourceIdOrderByCreatedDateDesc(Long sourceId, Pageable pageable);
    Page<News> findByTopicIdOrderByCreatedDateDesc(Long topicId, Pageable pageable);

    int countBySourceId(Long sourceId);
}
