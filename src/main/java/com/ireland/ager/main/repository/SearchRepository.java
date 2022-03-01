package com.ireland.ager.main.repository;

import com.ireland.ager.main.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, Long>, SearchRepositoryCustom {
}
