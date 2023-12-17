package com.strong.news.controller;

import com.strong.news.model.News;
import com.strong.news.model.Source;
import com.strong.news.model.Topic;
import com.strong.news.service.NewsService;
import com.strong.news.service.SourceService;
import com.strong.news.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;
    private final TopicService newsTopicService;
    private final SourceService newsSourceService;

    @Autowired
    public NewsController(NewsService newsService, TopicService newsTopicService, SourceService newsSourceService) {
        this.newsService = newsService;
        this.newsTopicService = newsTopicService;
        this.newsSourceService = newsSourceService;
    }

    // News API methods

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        Optional<News> news = newsService.getNewsById(id);
        return news.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<News> addNews(@RequestBody News news) {
        News addedNews = newsService.addNews(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedNews);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News updatedNews) {
        Optional<News> news = newsService.updateNews(id, updatedNews);
        return news.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<News>> getAllNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<News> newsList = newsService.getAllNews(page, size);
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/source/{sourceId}")
    public ResponseEntity<Page<News>> getNewsBySourceId(
            @PathVariable Long sourceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<News> newsList = newsService.getNewsBySourceId(sourceId, page, size);
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<Page<News>> getNewsByTopicId(
            @PathVariable Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<News> newsList = newsService.getNewsByTopicId(topicId, page, size);
        return ResponseEntity.ok(newsList);
    }

    // News Source API methods

    @GetMapping("/sources")
    public ResponseEntity<List<Source>> getAllNewsSources() {
        List<Source> newsSources = newsSourceService.getAllNewsSources();
        return ResponseEntity.ok(newsSources);
    }

    @GetMapping("/sources/{id}")
    public ResponseEntity<Source> getNewsSourceById(@PathVariable Long id) {
        Optional<Source> newsSource = newsSourceService.getNewsSourceById(id);
        return newsSource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/sources")
    public ResponseEntity<Source> addNewsSource(@RequestBody Source newsSource) {
        Source addedSource = newsSourceService.addNewsSource(newsSource);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSource);
    }

    @PutMapping("/sources/{id}")
    public ResponseEntity<Source> updateNewsSource(@PathVariable Long id, @RequestBody Source updatedSource) {
        Optional<Source> newsSource = newsSourceService.updateNewsSource(id, updatedSource);
        return newsSource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/sources/{id}")
    public ResponseEntity<Void> deleteNewsSource(@PathVariable Long id) {
        newsSourceService.deleteNewsSource(id);
        return ResponseEntity.noContent().build();
    }

// News Topic API methods

    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllNewsTopics() {
        List<Topic> newsTopics = newsTopicService.getAllNewsTopics();
        return ResponseEntity.ok(newsTopics);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> getNewsTopicById(@PathVariable Long id) {
        Optional<Topic> newsTopic = newsTopicService.getNewsTopicById(id);
        return newsTopic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/topics")
    public ResponseEntity<Topic> addNewsTopic(@RequestBody Topic newsTopic) {
        Topic addedTopic = newsTopicService.addNewsTopic(newsTopic);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTopic);
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> updateNewsTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        Optional<Topic> newsTopic = newsTopicService.updateNewsTopic(id, updatedTopic);
        return newsTopic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteNewsTopic(@PathVariable Long id) {
        newsTopicService.deleteNewsTopic(id);
        return ResponseEntity.noContent().build();
    }

}
