package com.example.impressiondaily.jpa;

import com.example.impressiondaily.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserJPA extends
        JpaRepository<User,Long>,
        JpaSpecificationExecutor<User> {
    User findByUsername(String username);
}
