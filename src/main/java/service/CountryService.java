package service;


import models.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    public Country save(Country country);
    public List<Country> findAll();
    public Optional<Country> findById(Integer id);
    public Optional<Country> findByCountryName(String Name);
    public Country getCountryDetailsByLink(String countryLink);
    public Country getCountryDetailsByName(String Name);
}
