package com.anushka.search_services5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class EventConsume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;
    private int capacity;
    private Date date;
    private String description;
    @ManyToOne
    private Location location;
    private int organizerId;

    @ManyToMany
    @JoinTable(
            name = "Event_tags_consume",
            joinColumns = @JoinColumn(name = "Event_conume", referencedColumnName = "eventId"),
            inverseJoinColumns = @JoinColumn(name = "Tag", referencedColumnName = "tagId")
    )    private List<Tag> tags;

    private String title;
    @ManyToOne
    private EventType type;

    // Constructors, getters, and setters
}
