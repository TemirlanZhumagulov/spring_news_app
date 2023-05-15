package com.strong.news;

import com.strong.news.model.News;
import com.strong.news.model.Source;
import com.strong.news.repository.NewsRepo;
import com.strong.news.repository.SourceRepo;
import com.strong.news.service.NewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NewsServiceTest {
    //@Autowired
    private NewsService newsService;
    //@Autowired
    private SourceRepo sourceRepo;
    //@Autowired
    private NewsRepo newsRepo;

    //@Test
    public void testGetNewsCountBySource() {
        // Create a test source and save it to the database
        Source testSource = new Source();
        testSource.setName("Test Source");
        Source savedSource = sourceRepo.save(testSource);

        // Create some test news entries associated with the test source
        News news1 = new News();
        news1.setTitle("News 1");
        news1.setSource(savedSource);
        newsRepo.save(news1);

        News news2 = new News();
        news2.setTitle("News 2");
        news2.setSource(savedSource);
        newsRepo.save(news2);

        // Call the getNewsCountBySource method
        long count = newsService.getNewsCountBySource(savedSource.getId());

        // Assert that the count is equal to the number of news entries for the test source
        assertEquals(2, count);
    }

}
