package com.nasreen.carlog.db;

import com.nasreen.carlog.model.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepository {
    private Jdbi jdbi;

    @Autowired
    public UserRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public User save(User user) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate("INSERT INTO users(id, username, email_id, is_admin, encrypted_password)" +
                    "  VALUES(:id, :username, :email_id, :is_admin, :encrypted_password)")
                    .bind("id", user.getId())
                    .bind("username", user.getUsername())
                    .bind("email_id", user.getEmailId())
                    .bind("encrypted_password", user.getEncryptedPassword())
                    .bind("is_admin", user.getIsAdmin())
                    .execute();
            return user;
        });
    }
}
