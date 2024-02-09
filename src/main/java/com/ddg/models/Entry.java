package com.ddg.models;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "entries")
public class Entry {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    @CreationTimestamp
    private Instant timestamp;

    @Column(name = "userId")
    private UUID userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "isPositive")
    private Boolean isPositive;
}