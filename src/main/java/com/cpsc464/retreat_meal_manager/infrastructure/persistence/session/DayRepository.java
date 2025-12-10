package com.cpsc464.retreat_meal_manager.infrastructure.persistence.session;

import com.cpsc464.retreat_meal_manager.domain.session.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    List<Day> findBySessionSessionId(Long sessionId);

    Optional<Day> findBySessionSessionIdAndDate(Long sessionId, LocalDate date);
}
