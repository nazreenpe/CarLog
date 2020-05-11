package com.nasreen.carlog.db;

import com.nasreen.carlog.model.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class UserRepository {
    private static final String DELETE_ALL = "DELETE FROM users";
    public static final String INSERT_QUERY = "INSERT INTO users(id, name, email_id, is_admin, encrypted_password)" +
            "  VALUES(:id, :name, :email_id, :is_admin, :encrypted_password)";
    public static final String SELECT = "SELECT * FROM users where email_id = :email_id";
    private Jdbi jdbi;

    @Autowired
    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Optional<User> save(User user) {
        return jdbi.withHandle(handle -> {
            try {
                handle.createUpdate(INSERT_QUERY)
                        .bind("id", user.getId())
                        .bind("name", user.getName())
                        .bind("email_id", user.getEmailId())
                        .bind("encrypted_password", user.getEncryptedPassword())
                        .bind("is_admin", user.getIsAdmin())
                        .execute();
            } catch (Exception ignore) {
                return Optional.empty();
            }

            return Optional.of(user);
        });
    }

    public void deleteAll() {
        jdbi.withHandle(handle -> handle.createUpdate(DELETE_ALL).execute());
    }

    public Optional<User> findByEmail(String email) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT)
                .bind("email_id", email)
                .map((rs, ctxt) -> new User(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("email_id"),
                        rs.getString("encrypted_password")
                        ))
                .findFirst());
    }
}
