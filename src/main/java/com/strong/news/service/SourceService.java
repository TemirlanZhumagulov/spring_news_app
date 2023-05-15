package com.strong.news.service;

import com.strong.news.model.Source;
import com.strong.news.repository.SourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SourceService {
    private final SourceRepo sourceRepo;

    @Autowired
    public SourceService(SourceRepo sourceRepo) {
        this.sourceRepo = sourceRepo;
    }

    public List<Source> getAllNewsSources() {
        return sourceRepo.findAll();
    }

    public Optional<Source> getNewsSourceById(Long id) {
        return sourceRepo.findById(id);
    }

    public Source addNewsSource(Source newsSource) {
        return sourceRepo.save(newsSource);
    }

    public Optional<Source> updateNewsSource(Long id, Source updatedSource) {
        Optional<Source> existingSource = sourceRepo.findById(id);
        existingSource.ifPresent(source -> {
            source.setName(updatedSource.getName());
            sourceRepo.save(source);
        });
        return existingSource;
    }

    public void deleteNewsSource(Long id) {
        sourceRepo.deleteById(id);
    }

}
