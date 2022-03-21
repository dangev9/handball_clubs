package service.impl;

import models.Club;
import org.springframework.stereotype.Service;
import repository.ClubRepository;
import service.ClubService;

import java.util.List;
import java.util.Optional;


@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Club save(Club club) {
        return this.clubRepository.save(club);
    }

    @Override
    public List<Club> findAll() {
        return this.clubRepository.findAll();
    }

    @Override
    public Optional<Club> findById(Integer id) {
        return this.clubRepository.findById(id);
    }

    @Override
    public Optional<Club> findByClubName(String name) {
        return this.clubRepository.findByClubName(name);
    }

    @Override
    public Club getClubDetailsByLink(String clubLink) {
        return null;
    }

    @Override
    public Club getClubDetailsByName(String Name) {
        return null;
    }
}
