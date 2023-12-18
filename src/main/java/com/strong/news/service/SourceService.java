package com.strong.news.service;

import com.strong.news.exception.NullEntityReferenceException;
import com.strong.news.model.Source;
import com.strong.news.repository.SourceRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SourceService {
    private static final String SOURCE_NOT_FOUND_MESSAGE = "Source (id=%d) was not found";
    private static final String SOURCE_NULL_ENTITY_MESSAGE = "Source cannot be 'null'";
    private static final String SOURCE_DELETED_MESSAGE = "Source (id=%d) was deleted";
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
        if (newsSource == null) {
            log.error(SOURCE_NULL_ENTITY_MESSAGE);
            throw new NullEntityReferenceException(SOURCE_NULL_ENTITY_MESSAGE);
        }
        return sourceRepo.save(newsSource);
    }

    public Optional<Source> updateNewsSource(Long id, Source updatedSource) {
        if (updatedSource == null) {
            log.error(SOURCE_NULL_ENTITY_MESSAGE);
            throw new NullEntityReferenceException(SOURCE_NULL_ENTITY_MESSAGE);
        }

        Optional<Source> existingSource = sourceRepo.findById(id);
        existingSource.ifPresent(source -> {
            source.setName(updatedSource.getName());
            sourceRepo.save(source);
        });
        return existingSource;
    }

    public void deleteNewsSource(Long id) {
        sourceRepo.findById(id).ifPresentOrElse(
                r -> {
                    sourceRepo.delete(r);
                    log.info(SOURCE_DELETED_MESSAGE.formatted(id));
                }, () -> {
                    log.error(SOURCE_NOT_FOUND_MESSAGE.formatted(id));
                    throw new EntityNotFoundException(SOURCE_NOT_FOUND_MESSAGE.formatted(id));
                });
    }

}
