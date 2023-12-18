package com.strong.news.service.scheduled;

import com.strong.news.model.Source;
import com.strong.news.service.NewsService;
import com.strong.news.service.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.*;

@Slf4j
@Service
public class NewsStatisticsService {
    private static final String STATISTICS_FILE_NAME = "statistics.txt";
    private final SourceService sourceService;
    private final NewsService newsService;
    @Autowired
    public NewsStatisticsService(SourceService sourceService, NewsService newsService) {
        this.sourceService = sourceService;
        this.newsService = newsService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void generateStatisticsFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String formatted = dateFormat.format(new Date());
        log.info("Scheduled task started. Current time: {}", formatted);

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
            BufferedWriter writer = new BufferedWriter(new FileWriter(STATISTICS_FILE_NAME, true));
            log.info("Started filling out the file: " + STATISTICS_FILE_NAME);
            writer.write("Counting news by sources for: " + formatted);
            writer.newLine();
            writer.write("------------------------------------------------");
            writer.newLine();
            for (Future<String> result : results) {
                try {
                    writer.write(result.get());
                    writer.newLine();
                } catch (InterruptedException | ExecutionException e) {
                    log.info("Exception during writing to file: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            writer.write("------------------------------------------------");
            writer.newLine();
            log.info("Finished filling out the file: " + STATISTICS_FILE_NAME);
            writer.close();
        } catch (IOException e) {
            log.info("Exception in generateStatisticsFile:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
