package com.cpsc464.retreat_meal_manager.infrastructure.persistence.session;

import com.cpsc464.retreat_meal_manager.domain.session.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findBySessionSessionId(Long sessionId);
}
