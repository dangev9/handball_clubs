package service.impl;

import models.Ground;
import org.springframework.stereotype.Service;
import repository.GroundRepository;
import service.GroundService;

import java.util.List;
import java.util.Optional;

@Service
public class GroundServiceImpl implements GroundService {

    private final GroundRepository groundRepository;

    public GroundServiceImpl(GroundRepository groundRepository) {
        this.groundRepository = groundRepository;
    }

    @Override
    public Ground save(Ground ground) {
        return this.groundRepository.save(ground);
    }

    @Override
    public List<Ground> findAll() {
        return this.groundRepository.findAll();
    }

    @Override
    public Optional<Ground> findById(Integer id) {
        return this.groundRepository.findById(id);
    }

    @Override
    public Ground getStadiumDetailsByLink(String stadiumLink) {
        return null;
    }
}
