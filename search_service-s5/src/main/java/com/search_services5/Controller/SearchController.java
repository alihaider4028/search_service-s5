package com.anushka.search_services5.Controller;

import com.anushka.search_services5.Repository.EventRepository;
import com.anushka.search_services5.Repository.EventTypeRepository;
import com.anushka.search_services5.Repository.LocationRepository;
import com.anushka.search_services5.Services.SearchService;
import com.anushka.search_services5.entity.SearchCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("search")
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    EventTypeRepository eventTypeRepository;



    @GetMapping(value = "OrderBy/location/{locationId}", produces = "application/json")
    public ResponseEntity<?> searchBYLocation(@PathVariable("locationId") int locationId) {
        return ResponseEntity.ok(this.searchService.searchByLocation(locationId));
    }

    @GetMapping(value = "OrderBy/capacityLessThen/{capacity}", produces = "application/json")
    public ResponseEntity<?> searchByCapacityLessThen(@PathVariable("capacity") int capacity) {
        return ResponseEntity.ok(this.searchService.searchEventByCapacityLessThen(capacity));
    }

    @GetMapping(value = "OrderBy/title/{title}", produces = "application/json")
    public ResponseEntity<?> searchByTitle(@PathVariable("title") String title) {
        return ResponseEntity.ok(this.searchService.searchEventByTitle(title));
    }

    @GetMapping(value = "OrderBy/capacityGreaterThen/{capacity}", produces = "application/json")
    public ResponseEntity<?> searchByCapacityGreaterThen(@PathVariable("capacity") int capacity) {
        return ResponseEntity.ok(this.searchService.searchEventByCapacityGreaterThen(capacity));
    }


    @GetMapping(value = "OrderBy/date/{date}", produces = "application/json")
    public ResponseEntity<?> searchByDate(@PathVariable("date") String date) {
        return ResponseEntity.ok(this.searchService.searchEventByDate(date));
    }

    @GetMapping(value = "OrderBy/type/{type}", produces = "application/json")
    public ResponseEntity<?> searchByType(@PathVariable("type") int type) {
        return ResponseEntity.ok(this.searchService.searchByEventType(type));
    }

    @GetMapping(value = "/{str}", produces = "application/json")
    public ResponseEntity<?> search(@PathVariable("str") String str) {
        return ResponseEntity.ok(this.searchService.search(str));
    }

    @GetMapping(value = "/searchCriteria/", produces = "application/json")
    public ResponseEntity<?> searchEvents( @RequestParam(value = "searchBy") String searchBy,
                                         @RequestParam(value = "value") String value) {
        return ResponseEntity.ok(this.searchService.searchEvents(searchBy, value));
    }
}
