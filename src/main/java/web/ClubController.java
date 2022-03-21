package web;


import models.Club;
import models.exceptions.ClubException;
import org.springframework.web.bind.annotation.*;
import service.ClubService;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping("/save")
    public Club saveClub(@RequestParam Club club){
        return clubService.save(club);
    }

    @GetMapping("/get-all")
    public List<Club> getAll(){
        return clubService.findAll();
    }

    @GetMapping("/{clubId}")
    public Club getById(@PathVariable Integer clubId){
        return clubService.findById(clubId).orElseThrow(ClubException::new);
    }
}
