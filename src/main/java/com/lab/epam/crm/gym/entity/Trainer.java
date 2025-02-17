package com.lab.epam.crm.gym.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trainers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trainer extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "specialization_id")
    TrainingType specialization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "trainers")
    Set<Trainee> trainees = new HashSet<>();

    @OneToMany(mappedBy = "trainer")
    Set<Training> trainings = new HashSet<>();
}