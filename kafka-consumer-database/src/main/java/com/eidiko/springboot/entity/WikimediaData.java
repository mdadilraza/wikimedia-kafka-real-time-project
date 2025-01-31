package com.eidiko.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "wikimedia_recentChange")
public class WikimediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private String wikiEventData;
}
