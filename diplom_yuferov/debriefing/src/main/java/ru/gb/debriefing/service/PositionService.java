package ru.gb.debriefing.service;

import org.springframework.stereotype.Service;
import ru.gb.debriefing.model.Positions;
import ru.gb.debriefing.repository.PositionRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository repository) {
        this.positionRepository = repository;
    }

    public List<Positions> findAll() {
        return positionRepository.findAll();
    }

    public Optional<Positions> findById(Long id) {
        return positionRepository.findById(id);
    }

    public Optional<Positions> create(Positions position) {
        return Optional.of(positionRepository.save(position));
    }

    public void delete(Long id) {
        positionRepository.deleteById(id);
    }

    public Optional<Positions> update(Long id, Positions pos) {
        Optional<Positions> position = positionRepository.findById(id);
        if (position.isPresent()) {
            position.get().setPosition(pos.getPosition());
        }
        return position;
    }

}