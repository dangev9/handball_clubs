package web;


import models.Country;
import models.exceptions.CountryException;
import org.springframework.web.bind.annotation.*;
import service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/save")
    public Country saveCountry(@RequestParam Country country){
        return countryService.save(country);
    }

    @GetMapping("/get-all")
    public List<Country> getAll(){
        return countryService.findAll();
    }

    @GetMapping("/{countryId}")
    public Country getById(@PathVariable Integer countryId){
        return countryService.findById(countryId).orElseThrow(CountryException::new);
    }
}
