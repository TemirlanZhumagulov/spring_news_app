package com.strong.news;

import com.strong.news.model.News;
import com.strong.news.model.Source;
import com.strong.news.model.Topic;
import com.strong.news.service.NewsService;
import com.strong.news.service.SourceService;
import com.strong.news.service.TopicService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class NewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(NewsService newsService, SourceService sourceService, TopicService topicService) {
		return args -> {
			Source source1 = new Source(1L, "Source 1");
			Source source2 = new Source(2L, "Source 2");
			sourceService.addNewsSource(source1);
			sourceService.addNewsSource(source2);

			Topic topic1 = new Topic(1L, "Topic 1");
			Topic topic2 = new Topic(2L, "Topic 2");
			topicService.addNewsTopic(topic1);
			topicService.addNewsTopic(topic2);

			News news1 = new News(1L, "Title 1", "Content 1", source1, topic1, LocalDateTime.now());
			News news2 = new News(2L, "Title 2", "Content 2", source2, topic2, LocalDateTime.now());
			newsService.addNews(news1);
			newsService.addNews(news2);
		};
	}
}
