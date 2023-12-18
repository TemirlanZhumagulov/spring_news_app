package com.strong.news.service;

import com.strong.news.exception.NullEntityReferenceException;
import com.strong.news.model.Topic;
import com.strong.news.repository.TopicRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TopicService {
    private static final String TOPIC_NOT_FOUND_MESSAGE = "Topic (id=%d) was not found";
    private static final String TOPIC_NULL_ENTITY_MESSAGE = "Topic cannot be 'null'";
    private static final String TOPIC_DELETED_MESSAGE = "Topic (id=%d) was deleted";
    private final TopicRepo topicRepo;
    @Autowired
    public TopicService(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    public List<Topic> getAllNewsTopics() {
        return topicRepo.findAll();
    }

    public Optional<Topic> getNewsTopicById(Long id) {
        return topicRepo.findById(id);
    }

    public Topic addNewsTopic(Topic newsTopic) {
        if (newsTopic == null) {
            log.error(TOPIC_NULL_ENTITY_MESSAGE);
            throw new NullEntityReferenceException(TOPIC_NULL_ENTITY_MESSAGE);
        }
        return topicRepo.save(newsTopic);
    }

    public Optional<Topic> updateNewsTopic(Long id, Topic updatedTopic) {
        if (updatedTopic == null) {
            log.error(TOPIC_NULL_ENTITY_MESSAGE);
            throw new NullEntityReferenceException(TOPIC_NULL_ENTITY_MESSAGE);
        }
        Optional<Topic> existingTopic = topicRepo.findById(id);
        existingTopic.ifPresent(topic -> {
            topic.setName(updatedTopic.getName());
            topicRepo.save(topic);
        });
        return existingTopic;
    }

    public void deleteNewsTopic(Long id) {
        topicRepo.findById(id).ifPresentOrElse(
                r -> {
                    topicRepo.delete(r);
                    log.info(TOPIC_DELETED_MESSAGE.formatted(id));
                }, () -> {
                    log.error(TOPIC_NOT_FOUND_MESSAGE.formatted(id));
                    throw new EntityNotFoundException(TOPIC_NOT_FOUND_MESSAGE.formatted(id));
                });
    }
}
