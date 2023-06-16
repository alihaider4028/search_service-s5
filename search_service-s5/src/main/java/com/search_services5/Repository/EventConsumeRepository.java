package com.anushka.search_services5.Repository;

import com.anushka.search_services5.entity.Event;
import com.anushka.search_services5.entity.EventConsume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventConsumeRepository extends JpaRepository<EventConsume,Integer> {

}
