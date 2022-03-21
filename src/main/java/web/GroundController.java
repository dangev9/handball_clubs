package web;


import models.Ground;
import models.exceptions.GroundException;
import org.springframework.web.bind.annotation.*;
import service.GroundService;

import java.util.List;

@RestController
@RequestMapping("/ground")
public class GroundController {

    private final GroundService groundService;

    public GroundController(GroundService groundService) {
        this.groundService = groundService;
    }

    @PostMapping("/save")
    public Ground saveGround(@RequestParam Ground stadium){
        return groundService.save(stadium);
    }

    @GetMapping("/get-all")
    public List<Ground> getAll(){
        return groundService.findAll();
    }

    @GetMapping("/{groundId}")
    public Ground getById(@PathVariable Integer groundId){
        return groundService.findById(groundId).orElseThrow(GroundException::new);
    }
}
