package com.kazfintracker.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KazFinTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KazFinTrackerApplication.class, args);
	}


//	@Bean
//	public CommandLineRunner initData(NewsService newsService, SourceService sourceService, TopicService topicService) {
//		return args -> {
//			Source source1 = new Source("Source 1");
//			Source source2 = new Source("Source 2");
//			sourceService.addNewsSource(source1);
//			sourceService.addNewsSource(source2);
//
//			Topic topic1 = new Topic("Topic 1");
//			Topic topic2 = new Topic("Topic 2");
//			topicService.addNewsTopic(topic1);
//			topicService.addNewsTopic(topic2);
//
//			News news1 = new News("Title 1", "Content 1", source1, topic1);
//			News news2 = new News("Title 2", "Content 2", source2, topic2);
//			newsService.addNews(news1);
//			newsService.addNews(news2);
//		};
//	}
}
