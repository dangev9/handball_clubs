package service;

import models.Club;

import java.util.List;
import java.util.Optional;

public interface ClubService {

    public Club save(Club club);
    public List<Club> findAll();
    public Optional<Club> findById(Integer id);
    public Optional<Club> findByClubName(String Name);
    public Club getClubDetailsByLink(String clubLink);
    public Club getClubDetailsByName(String Name);

}
