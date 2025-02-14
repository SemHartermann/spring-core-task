package com.lab.epam.crm.gym.repository;

import com.lab.epam.crm.gym.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {
    List<Training> findAllByTraineeUserUsernameAndTrainingDateBetween(String username, Date fromDate, Date toDate);

    List<Training> findAllByTrainerUserUsernameAndTrainingDateBetween(String username, Date fromDate, Date toDate);
}