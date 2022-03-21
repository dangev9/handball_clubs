package service;

import models.Ground;

import java.util.List;
import java.util.Optional;

public interface GroundService {

    public Ground save(Ground ground);
    public List<Ground> findAll();
    public Optional<Ground> findById(Integer id);
    public Ground getStadiumDetailsByLink(String stadiumLink);
}
