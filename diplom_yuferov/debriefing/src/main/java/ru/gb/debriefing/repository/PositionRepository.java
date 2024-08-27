package ru.gb.debriefing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.gb.debriefing.model.Positions;

public interface PositionRepository extends JpaRepository<Positions, Long> {
}
