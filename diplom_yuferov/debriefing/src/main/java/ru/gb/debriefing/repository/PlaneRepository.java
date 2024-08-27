package ru.gb.debriefing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.gb.debriefing.model.Planes;

public interface PlaneRepository extends JpaRepository<Planes, Long> {
}
