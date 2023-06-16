package com.anushka.search_services5.Services.impl;

import com.anushka.search_services5.Repository.EventRepository;
import com.anushka.search_services5.Repository.SearchCriteriaRepository;
import com.anushka.search_services5.Response.ApiResponse;
import com.anushka.search_services5.Services.SearchService;
import com.anushka.search_services5.entity.Event;
import com.anushka.search_services5.entity.SearchCriteria;
import com.anushka.search_services5.publisher.RabbitMQEventListProducer;
import com.anushka.search_services5.publisher.RabbitMQEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    RabbitMQEventListProducer rabbitMQEventListProducer;
    @Autowired

    RabbitMQEventProducer rabbitMQEventProducer;
    @Autowired
    SearchCriteriaRepository searchCriteriaRepository;


    @Override
    public ApiResponse<?> searchByLocation(int locationId) {
        Optional<String> resp = "NO EVENT FOUND ON THIS lOCATION".describeConstable();
        ApiResponse<?> apiResponse;
        if (this.eventRepository.findByLocationId(locationId).isPresent()) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "location found", this.eventRepository.findByLocationId(locationId));
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "location not found", resp);
            this.rabbitMQEventListProducer.sendJsonMessage((Optional<List<Event>>) apiResponse.getResult());

        }
        return apiResponse;
    }

    @Override
    public ApiResponse<?> searchEventByCapacityLessThen(int capacity) {
        Optional<String> resp = ("NO EVENT FOUND WITH Capacity LESS THAN" + capacity + ".").describeConstable();
        ApiResponse<?> apiResponse;
        if (this.eventRepository.findByCapacityLessThan(capacity).get().isEmpty()) {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No event found", resp);
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event found", this.eventRepository.findByCapacityLessThan(capacity));


        }

        return apiResponse;
    }

    @Override
    public ApiResponse<?> searchEventByDate(String date) {

        Optional<String> resp = ("NO EVENT FOUND WITH THIS DATE " + date).describeConstable();
        ApiResponse<?> apiResponse;
        Optional<List<Event>> event = Optional.of(this.eventRepository.findAll().stream().filter(event1 -> event1.getDate().toString().contains(date)).collect(Collectors.toList()));
        if (event.isEmpty()) {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Event not found", resp);
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event  found", event);
            this.rabbitMQEventListProducer.sendJsonMessage((Optional<List<Event>>) apiResponse.getResult());

        }
        return apiResponse;
    }

    @Override
    public ApiResponse<?> searchEventByTitle(String title) {
        Optional<String> resp = ("NO EVENT FOUND with THIS Title " + title).describeConstable();
        ApiResponse<?> apiResponse;
        if (this.eventRepository.findByTitle(title).isPresent()) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event found", this.eventRepository.findByTitle(title));
            this.rabbitMQEventProducer.sendJsonMessage((Optional<Event>) apiResponse.getResult());

        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Event not found", resp);

        }
        return apiResponse;
    }

    @Override
    public ApiResponse<?> searchEventByCapacityGreaterThen(int capacity) {
        Optional<String> resp = ("NO EVENT FOUND WITH  CAPACITY GREATER THEN " + capacity + ".").describeConstable();
        ApiResponse<?> apiResponse;
        if (this.eventRepository.findByCapacityGreaterThan(capacity).get().isEmpty()) {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No event found", resp);
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event found", this.eventRepository.findByCapacityGreaterThan(capacity));


        }

        return apiResponse;
    }

    @Override
    public ApiResponse<?> searchByEventType(int type) {
        Optional<String> resp = ("NO EVENT FOUND with This TypeId " + type).describeConstable();
        ApiResponse<?> apiResponse;
        if (!this.eventRepository.findByTypeId(type).get().isEmpty()) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event found", this.eventRepository.findByTypeId(type));
        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Event not found", resp);

        }
        return apiResponse;
    }

    @Override
    public ApiResponse<?> search(String str) {
        Optional<String> resp = ("NO EVENT FOUND with following keyword " + str).describeConstable();

        Optional<List<Event>> events = Optional.of(this.eventRepository.findAll().stream().filter(event -> event.toString().contains(str)).collect(Collectors.toList()));
        ApiResponse<?> apiResponse;
        if (!events.isEmpty()) {
            apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event found", events);
            this.rabbitMQEventListProducer.sendJsonMessage((Optional<List<Event>>) apiResponse.getResult());

        } else {
            apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Event not found", resp);

        }
        return apiResponse;
    }

    @Override
    public ApiResponse<?> searchEvents(String searchBy, String value) {
        ApiResponse<?> apiResponse;
        Optional<String> resp = ("NO EVENT FOUND ").describeConstable();
        SearchCriteria searchCriteria= new SearchCriteria();
try{
        if (searchBy.equalsIgnoreCase("keywords")) {
            searchCriteria.setKeywords(value);
            this.searchCriteriaRepository.save(searchCriteria);
            return search(value);

        } else if (searchBy.equalsIgnoreCase("location")) {
            searchCriteria.setLocation(this.eventRepository.findByLocationId(Integer.parseInt(value)).get());
            return searchByLocation(Integer.parseInt(value));
        } else if (searchBy.equalsIgnoreCase("TypeId")) {
            return searchByEventType(Integer.parseInt(value));
        } else if (searchBy.equalsIgnoreCase("date")) {

          return   searchEventByDate(value);

        } else {
            return apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event not found", resp);

        }}catch (Exception ex) {
    System.out.println(ex.getMessage().toString());
    return apiResponse = new ApiResponse<>(HttpStatus.OK.value(), "Event not found", resp);


}


    }
}
