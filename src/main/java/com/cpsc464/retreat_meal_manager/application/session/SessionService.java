package com.cpsc464.retreat_meal_manager.application.session;

import com.cpsc464.retreat_meal_manager.domain.mealperiod.MealPeriod;
import com.cpsc464.retreat_meal_manager.domain.mealperiod.MealPeriodType;
import com.cpsc464.retreat_meal_manager.domain.session.Day;
import com.cpsc464.retreat_meal_manager.domain.session.Group;
import com.cpsc464.retreat_meal_manager.domain.session.Session;
import com.cpsc464.retreat_meal_manager.infrastructure.persistence.session.DayRepository;
import com.cpsc464.retreat_meal_manager.infrastructure.persistence.session.GroupRepository;
import com.cpsc464.retreat_meal_manager.infrastructure.persistence.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionService {

    private final SessionRepository sessionRepository;
    private final GroupRepository groupRepository;
    private final DayRepository dayRepository;

    public Session createSession(String name, LocalDate startDate, LocalDate endDate) {
        Session session = Session.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        session = sessionRepository.save(session);

        // Initialize days for the session
        initializeDaysForSession(session);

        return sessionRepository.save(session);
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session updateSession(Long id, String name, LocalDate startDate, LocalDate endDate) {
        Session session = getSessionById(id);
        session.setName(name);
        session.setStartDate(startDate);
        session.setEndDate(endDate);
        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public Group addGroupToSession(Long sessionId, Group group) {
        Session session = getSessionById(sessionId);
        group.setSession(session);
        return groupRepository.save(group);
    }

    public List<Group> getGroupsBySession(Long sessionId) {
        return groupRepository.findBySessionSessionId(sessionId);
    }

    private void initializeDaysForSession(Session session) {
        LocalDate currentDate = session.getStartDate();
        LocalDate endDate = session.getEndDate();

        while (!currentDate.isAfter(endDate)) {
            Day day = Day.builder()
                    .date(currentDate)
                    .session(session)
                    .adultCount(0)
                    .youthCount(0)
                    .kidCount(0)
                    .build();

            // Initialize 3 meal periods for each day
            List<MealPeriod> mealPeriods = new ArrayList<>();
            for (MealPeriodType type : MealPeriodType.values()) {
                MealPeriod mealPeriod = MealPeriod.builder()
                        .mealPeriodType(type)
                        .day(day)
                        .build();
                mealPeriods.add(mealPeriod);
            }
            day.setMealPeriods(mealPeriods);

            session.getDays().add(day);
            currentDate = currentDate.plusDays(1);
        }
    }
}
