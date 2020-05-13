package com.nasreen.carlog.service;

import com.nasreen.carlog.db.DocumentRepository;
import com.nasreen.carlog.db.DocumentRepository;
import com.nasreen.carlog.model.Document;
import com.nasreen.carlog.request.DocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
    private DocumentRepository repository;

    @Autowired
    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public Document create(UUID recordId, DocumentRequest request) {
        Document activity = new Document(request.getDescription(), recordId, request.getPath());
        repository.save(activity);
        return activity;
    }

    public List<Document> list(UUID recordId) {
        return repository.list(recordId);
    }

    public Optional<Document> get(UUID recordId, UUID id) {
        return repository.findById(recordId, id);
    }

    public Optional<Document> update(UUID recordId, UUID id, DocumentRequest request) {
        return repository.findById(recordId, id)
                .flatMap(activity -> {
                    activity.setDescription(request.getDescription());
                    activity.setPath(request.getPath());
                    return repository.update(activity);
                });
    }

    public Optional<UUID> delete(UUID recordId, UUID id) {
        return repository.findById(recordId, id)
                .flatMap(activity -> repository.delete(id));
    }
}
