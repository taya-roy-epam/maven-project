package com.example.SongService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongMetadata {

    @Id
    Long id;

    String name;
    String artist;
    String album;
    String duration;
    String year;
}
