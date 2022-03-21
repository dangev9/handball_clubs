package service.impl;

import models.Country;
import org.springframework.stereotype.Service;
import repository.CountryRepository;
import service.CountryService;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public Country save(Country country) {
        return this.countryRepository.save(country);
    }

    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Integer id) {
        return this.countryRepository.findById(id);
    }

    @Override
    public Optional<Country> findByCountryName(String name) {
        return this.countryRepository.findByCountryName(name);
    }

    @Override
    public Country getCountryDetailsByLink(String countryLink) {
        return null;
    }

    @Override
    public Country getCountryDetailsByName(String Name) {
        return null;
    }
}
