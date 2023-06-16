package com.anushka.search_services5.Repository;

import com.anushka.search_services5.entity.SearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchCriteriaRepository extends JpaRepository<SearchCriteria, Integer> {
}
