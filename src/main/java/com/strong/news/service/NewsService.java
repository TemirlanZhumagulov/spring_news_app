package com.strong.news.service;

import com.strong.news.exception.NullEntityReferenceException;
import com.strong.news.exception.UnacceptableParameterValueException;
import com.strong.news.model.News;
import com.strong.news.model.Source;
import com.strong.news.model.Topic;
import com.strong.news.repository.NewsRepo;
import com.strong.news.repository.SourceRepo;
import com.strong.news.repository.TopicRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class NewsService {
    private static final String NEWS_NOT_FOUND_MESSAGE = "News (id=%d) was not found";
    private static final String NEWS_NULL_ENTITY_MESSAGE = "News cannot be 'null'";
    private static final String NEWS_DELETED_MESSAGE = "News (id=%d) was deleted";
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
        if(news == null){
            log.error(NEWS_NULL_ENTITY_MESSAGE);
            throw new NullEntityReferenceException(NEWS_NULL_ENTITY_MESSAGE);
        }
        news.setCreatedDate(LocalDateTime.now());
        setDependentTables(news, news);
        return newsRepo.save(news);
    }
    public Optional<News> updateNews(Long id, News updatedNews) {
        if(updatedNews == null){
            log.error(NEWS_NULL_ENTITY_MESSAGE);
            throw new NullEntityReferenceException(NEWS_NULL_ENTITY_MESSAGE);
        }
        Optional<News> existingNews = newsRepo.findById(id);
        existingNews.ifPresent(news -> {
            news.setTitle(updatedNews.getTitle());
            news.setContent(updatedNews.getContent());
            setDependentTables(updatedNews, news);
            newsRepo.save(news);
        });
        return existingNews;
    }

    private void setDependentTables(News updatedNews, News news) {
        Source source = updatedNews.getSource();
        if (source != null && source.getId() != null && source.getName() != null &&
                source.getId() >= 0 && !source.getName().isEmpty()) {

            sourceRepo.findById(source.getId()).ifPresentOrElse(
                    news::setSource,
                    () -> news.setSource(sourceRepo.save(source))
            );
        } else {
            throw new UnacceptableParameterValueException("Source name is null or empty or id is -1 or below");
        }

        Topic topic = updatedNews.getTopic();
        if (topic != null && topic.getId() != null && topic.getName() != null &&
                topic.getId() >= 0 && !topic.getName().isEmpty()) {

            topicRepo.findById(topic.getId()).ifPresentOrElse(
                    news::setTopic,
                    () -> news.setTopic(topicRepo.save(topic))
            );
        } else {
            throw new UnacceptableParameterValueException("Topic name is null or empty or id is -1 or below");
        }
    }

    public void deleteNews(Long id) {
        newsRepo.findById(id).ifPresentOrElse(
                r ->{
                    newsRepo.delete(r);
                    log.info(NEWS_DELETED_MESSAGE.formatted(id));
                },() -> {
                    log.error(NEWS_NOT_FOUND_MESSAGE.formatted(id));
                    throw new EntityNotFoundException(NEWS_NOT_FOUND_MESSAGE.formatted(id));
                }
        );
    }


    public int getNewsCountBySource(Long sourceId) {
        return newsRepo.countBySourceId(sourceId);
    }
}

