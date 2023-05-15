package com.strong.news.service;

import com.strong.news.model.News;
import com.strong.news.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class NewsService {
    private final NewsRepo newsRepo;

    @Autowired
    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public Page<News> getAllNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.findAllByOrderByCreatedDateDesc(pageable);
    }
    public Page<News> getNewsBySourceId(Long sourceId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.findBySourceIdOrderByCreatedDateDesc(sourceId, pageable);
    }

    public Page<News> getNewsByTopicId(Long topicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.findByTopicIdOrderByCreatedDateDesc(topicId, pageable);
    }
    public Optional<News> getNewsById(Long id) {
        return newsRepo.findById(id);
    }

    public News addNews(News news) {
        news.setCreatedDate(LocalDateTime.now());
        return newsRepo.save(news);
    }
    public Optional<News> updateNews(Long id, News updatedNews) {
        Optional<News> existingNews = newsRepo.findById(id);
        existingNews.ifPresent(news -> {
            news.setTitle(updatedNews.getTitle());
            news.setContent(updatedNews.getContent());
            news.setSource(updatedNews.getSource());
            news.setTopic(updatedNews.getTopic());
            newsRepo.save(news);
        });
        return existingNews;
    }

    public void deleteNews(Long id) {
        newsRepo.deleteById(id);
    }


    public int getNewsCountBySource(Long sourceId) {
        return newsRepo.countBySourceId(sourceId);
    }
}

