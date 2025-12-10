package com.cpsc464.retreat_meal_manager.presentation.session;

import com.cpsc464.retreat_meal_manager.application.session.SessionService;
import com.cpsc464.retreat_meal_manager.domain.session.Group;
import com.cpsc464.retreat_meal_manager.domain.session.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<Session> createSession(@RequestBody CreateSessionRequest request) {
        Session session = sessionService.createSession(
                request.name(),
                request.startDate(),
                request.endDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getSession(@PathVariable Long id) {
        Session session = sessionService.getSessionById(id);
        return ResponseEntity.ok(session);
    }

    @GetMapping
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> updateSession(
            @PathVariable Long id,
            @RequestBody CreateSessionRequest request) {
        Session session = sessionService.updateSession(
                id,
                request.name(),
                request.startDate(),
                request.endDate()
        );
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/groups")
    public ResponseEntity<Group> addGroup(
            @PathVariable Long id,
            @RequestBody Group group) {
        Group savedGroup = sessionService.addGroupToSession(id, group);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGroup);
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<List<Group>> getGroups(@PathVariable Long id) {
        List<Group> groups = sessionService.getGroupsBySession(id);
        return ResponseEntity.ok(groups);
    }

    record CreateSessionRequest(String name, LocalDate startDate, LocalDate endDate) {}
}
