package com.anushka.search_services5.entity;

import com.anushka.search_services5.entity.EventType;
import com.anushka.search_services5.entity.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class SearchCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int SearchCriteriaId;

    String keywords;
    @ManyToMany
    List<Event> eventType;

    @ManyToMany
    List <Event> location;
    @ManyToMany
    List<Event> date;
}
