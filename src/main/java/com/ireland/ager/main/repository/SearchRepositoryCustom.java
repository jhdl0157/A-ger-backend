package com.ireland.ager.main.repository;
import java.util.List;

public interface SearchRepositoryCustom {
    List<String> findFirst5SearchesOrderByPopularDesc();
}
