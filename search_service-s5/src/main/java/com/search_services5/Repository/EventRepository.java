package com.anushka.search_services5.Repository;

import com.anushka.search_services5.Response.ApiResponse;
import com.anushka.search_services5.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Integer> {
    public  Optional<List<Event>> findByCapacityGreaterThan(int i);
    public Optional<List<Event>> findByCapacityLessThan(int i);
    @Query("select a from Event a join fetch a.locationId where a.locationId.Id = ?1")
    public Optional<List<Event>> findByLocationId(int i);

    public  Optional<Event> findByTitle(String title);
    @Query("select a from Event a join fetch a.typeId where a.typeId.id = ?1")

    public Optional<List<Event>> findByTypeId(int type);

    public Optional<List<Event>> findAllByDate(Date date);

}
