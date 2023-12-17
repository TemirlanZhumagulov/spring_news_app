package com.strong.news.service;

import com.strong.news.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Scheduled(cron = "0 0/1 * * * ?") // Runs every 1 minutes
    public void generateStatisticsFile() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);

        logger.info("Scheduled task started. Current time: {}", formattedDateTime);
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("statistics.txt"))) {
            writer.write("Statistics generated at: " + formattedDateTime);
            writer.newLine();
            writer.newLine(); // Add an empty line for better readability

            for (Future<String> result : results) {
                try {
                    writer.write(result.get());
                    writer.newLine();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            logger.info("File created and filled out");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
