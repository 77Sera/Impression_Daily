package com.example.impressiondaily.jpa;

import com.example.impressiondaily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DailyJPA extends
        JpaRepository<Daily,Long>,
        JpaSpecificationExecutor<Daily> {
    List<Daily> findByUserid(String userid);
}
