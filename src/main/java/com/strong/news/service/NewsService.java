package com.strong.news.service;

import com.strong.news.model.News;
import com.strong.news.model.Source;
import com.strong.news.model.Topic;
import com.strong.news.repository.NewsRepo;
import com.strong.news.repository.SourceRepo;
import com.strong.news.repository.TopicRepo;
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
    private final TopicRepo topicRepo;
    private final SourceRepo sourceRepo;

    @Autowired
    public NewsService(NewsRepo newsRepo, TopicRepo topicRepo, SourceRepo sourceRepo) {
        this.newsRepo = newsRepo;
        this.topicRepo = topicRepo;
        this.sourceRepo = sourceRepo;
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

        Source source = news.getSource();
        if (source != null && source.getId() == null) {
            source = sourceRepo.save(source);
            news.setSource(source);
        }

        Topic topic = news.getTopic();
        if (topic != null && topic.getId() == null) {
            topic = topicRepo.save(topic);
            news.setTopic(topic);
        }
        return newsRepo.save(news);
    }
    public Optional<News> updateNews(Long id, News updatedNews) {
        Optional<News> existingNews = newsRepo.findById(id);
        existingNews.ifPresent(news -> {
            news.setTitle(updatedNews.getTitle());
            news.setContent(updatedNews.getContent());


            Source source = updatedNews.getSource();
            if (source != null && source.getId() == null) {
                source = sourceRepo.save(source);
                news.setSource(source);
            }


            Topic topic = updatedNews.getTopic();
            if (topic != null && topic.getId() == null) {
                topic = topicRepo.save(topic);
                news.setTopic(topic);
            }
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

