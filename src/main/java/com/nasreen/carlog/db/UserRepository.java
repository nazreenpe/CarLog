package com.nasreen.carlog.db;

import com.nasreen.carlog.model.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepository {
    private static final String DELETE_ALL = "DELETE FROM users";
    public static final String INSERT_QUERY = "INSERT INTO users(id, username, email_id, is_admin, encrypted_password)" +
            "  VALUES(:id, :username, :email_id, :is_admin, :encrypted_password)";
    private Jdbi jdbi;

    @Autowired
    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public User save(User user) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate(INSERT_QUERY)
                    .bind("id", user.getId())
                    .bind("username", user.getUsername())
                    .bind("email_id", user.getEmailId())
                    .bind("encrypted_password", user.getEncryptedPassword())
                    .bind("is_admin", user.getIsAdmin())
                    .execute();
            return user;
        });
    }

    public void deleteAll() {
        jdbi.withHandle(handle -> handle.createUpdate(DELETE_ALL).execute());
    }
}
