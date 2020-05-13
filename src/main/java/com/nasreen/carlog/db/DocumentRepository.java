package com.nasreen.carlog.db;

import com.nasreen.carlog.model.Document;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DocumentRepository {
    public static final String SELECT_QUERY = "SELECT * FROM documents WHERE record_id = :recordId AND id = :id";
    public static final String INSERT_QUERY = "INSERT INTO documents(id, path, description, record_id, filename)  " +
        "VALUES(:id, :path, :description, :recordId, :filename)";
    public static final String SELECT_ALL_BY_ID = "SELECT * FROM documents WHERE record_id = :recordId";
    public static final String UPDATE = "UPDATE documents SET description = :description WHERE id = :id";
    public static final String DELETE = "DELETE FROM documents WHERE id = :id";
    private Jdbi jdbi;

    @Autowired
    public DocumentRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Document save(Document document) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate(INSERT_QUERY)
                .bind("id", document.getId().toString())
                .bind("path", document.getPath())
                .bind("filename", document.getFilename())
                .bind("description", document.getDescription())
                .bind("recordId", document.getRecordId().toString())
                .execute();
            return document;
        });
    }

    public Optional<Document> findById(UUID recordId, UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_QUERY)
            .bind("recordId", recordId.toString())
            .bind("id", id.toString())
            .map(DocumentRowMapper())
            .findFirst());
    }

    public List<Document> list(UUID recordId) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_ALL_BY_ID)
            .bind("recordId", recordId.toString())
            .map(DocumentRowMapper())
            .list()
        );
    }

    public Optional<Document> update(Document document) {
        jdbi.withHandle(handle -> handle.createUpdate(UPDATE)
            .bind("description", document.getDescription())
            .bind("path", document.getPath())
            .bind("id", document.getId())
            .execute());
        return Optional.of(document);
    }

    public Optional<UUID> delete(UUID id) {
        jdbi.withHandle(handle -> handle.createUpdate(DELETE)
            .bind("id", id.toString())
            .execute());
        return Optional.ofNullable(id);
    }

    private RowMapper<Document> DocumentRowMapper() {
        return (rs, ctx) -> new Document(UUID.fromString(rs.getString("id")),
            UUID.fromString(rs.getString("record_id")),
            rs.getString("description"),
            rs.getString("path"),
            rs.getString("filename"));
    }
}
