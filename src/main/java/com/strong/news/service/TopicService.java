package com.strong.news.service;

import com.strong.news.model.Topic;
import com.strong.news.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
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
        return topicRepo.save(newsTopic);
    }

    public Optional<Topic> updateNewsTopic(Long id, Topic updatedTopic) {
        Optional<Topic> existingTopic = topicRepo.findById(id);
        existingTopic.ifPresent(topic -> {
            topic.setName(updatedTopic.getName());
            topicRepo.save(topic);
        });
        return existingTopic;
    }

    public void deleteNewsTopic(Long id) {
        topicRepo.deleteById(id);
    }
}
