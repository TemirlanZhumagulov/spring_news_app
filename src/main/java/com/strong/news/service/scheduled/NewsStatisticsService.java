package com.strong.news.service.scheduled;

import com.strong.news.model.Source;
import com.strong.news.service.NewsService;
import com.strong.news.service.SourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
public class NewsStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsStatisticsService.class);
    private final SourceService sourceService;
    private final NewsService newsService;
    @Autowired
    public NewsStatisticsService(SourceService sourceService, NewsService newsService) {
        this.sourceService = sourceService;
        this.newsService = newsService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight every day
    public void generateStatisticsFile() {
        logger.info("Scheduled task started. Current time: {}", new Date());
        List<Source> newsSources = sourceService.getAllNewsSources();

        ExecutorService executorService = Executors.newFixedThreadPool(newsSources.size());
        List<Future<String>> results = new ArrayList<>();

        for (Source newsSource : newsSources) {
            Callable<String> task = () -> {
                int newsCount = newsService.getNewsCountBySource(newsSource.getId());
                return newsSource.getName() + ": " + newsCount;
            };
            Future<String> result = executorService.submit(task);
            results.add(result);
        }

        executorService.shutdown();

        // Writes statistics to the file statistics.txt, which will be created in the src folder of this project
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("statistics.txt"));
            logger.info("File created");
            for (Future<String> result : results) {
                try {
                    writer.write(result.get());
                    writer.newLine();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            logger.info("File filled out");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
