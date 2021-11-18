package com.mia.dProject.dmaker.repository;

import com.mia.dProject.dmaker.entity.Developer;
import com.mia.dProject.dmaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
