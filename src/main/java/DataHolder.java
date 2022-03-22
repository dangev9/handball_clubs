import lombok.Getter;
import models.Club;
import models.Country;
import models.Ground;
import org.apache.jena.query.*;
import org.springframework.stereotype.Component;
import repository.ClubRepository;
import repository.CountryRepository;
import repository.GroundRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
public class DataHolder {
    public List<Country> countries = new ArrayList<>();
    public static final List<Club> hcGermany = new ArrayList<>();
    public static final List<Club> hcFrance = new ArrayList<>();
    public static final List<Club> hcHungary = new ArrayList<>();
    public static final List<Club> hcSpain = new ArrayList<>();
    public List<String> clubs = new ArrayList<>();

    public final CountryRepository countryRepository;
    public final ClubRepository clubRepository;
    public final GroundRepository groundRepository;


    public DataHolder(CountryRepository countryRepository, ClubRepository clubRepository, GroundRepository groundRepository) {
        this.countryRepository = countryRepository;
        this.clubRepository = clubRepository;
        this.groundRepository = groundRepository;
    }


    public List<String> getAllClubs(String country) {
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        clubs.clear();
        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX db: <http://dbpedia.org/resource/>" +
                        "PREFIX dct: <http://purl.org/dc/terms/>" +
                        "SELECT distinct ?club WHERE { " +
                        "db:Category:" + country + "_handball_clubs" + " ^dct:subject ?club. " +
                        "}" +
                        "Limit 150";

        //TODO AKO NE RABOTI QUERTIO DA GO PROMENIME SO OVA DOLE
//        SELECT distinct ?club WHERE {
//?club  dct:subject  dbc:French_handball_clubs .
//        } LIMIT 150


        Query sparqlQuery = QueryFactory.create(query);
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String[] arr = (solution.get("club").toString()).split("/");
                String club = arr[arr.length - 1];
                clubs.add(club);
            }
        }
        return clubs;
    }


    public void getClubDetails(String club, String country) {
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX db: <http://dbpedia.org/resource/>" +
                        "PREFIX dbp:  <http://dbpedia.org/property/>" +
                        "SELECT ?abstract ?chairman ?manager ?ground ?winners WHERE { " +
                        "OPTIONAL {db:" + club + " dbo:abstract ?abstract.}" +
                        "OPTIONAL {db:" + club + " dbo:chairman ?chairman.}" +
                        "OPTIONAL {db:" + club + " dbo:manager ?manager.}" +
                        "OPTIONAL {db:" + club + " dbo:ground ?ground.}" +
                        "OPTIONAL {db:" + club + " ^dbp:winners ?winners.}" +
                        "FILTER (lang(?abstract) = 'en')" +
                        "}" +
                        "LIMIT 1";
        Club newClub = new Club();
        Query sparqlQuery = QueryFactory.create(query);

        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();

                String[] arr = (solution.get("abstract").toString()).split("/");
                String abstracts = arr[arr.length - 1];
                newClub.setDescription(abstracts);
                if (solution.contains("chairman")) {
                    arr = (solution.get("chairman").toString()).split("/");
                    String chairman = arr[arr.length - 1];
                    newClub.setChairman(chairman);
                }
                if (solution.contains("manager")) {
                    arr = (solution.get("manager").toString()).split("/");
                    String manager = arr[arr.length - 1];
                    newClub.setManager(manager);
                }
                if (solution.contains("ground")) {
                    arr = (solution.get("ground").toString()).split("/");
                    String ground = arr[arr.length - 1];
                    newClub.setGround(new Ground(0, ground));
                }
                if (solution.contains("champions")) {
                    arr = (solution.get("champions").toString()).split("/");
                    String champions = arr[arr.length - 1];
                    newClub.setWinners(champions);
                }
                newClub.setClubName(club);
                newClub.setId(0);
                if (country.equals("French")) hcFrance.add(newClub);
                if (country.equals("Spanish")) hcSpain.add(newClub);
                if (country.equals("German")) hcGermany.add(newClub);
                if (country.equals("Hungarian")) hcHungary.add(newClub);
                break;
            }
        }
    }

    public void getGroundDetails(String stadium, String country) {
        String SPARQLEndpoint = "https://dbpedia.org/sparql";

        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "PREFIX db: <http://dbpedia.org/resource/>" +
                        "PREFIX dbp:  <http://dbpedia.org/property/>" +
                        "SELECT ?description ?renovated ?opened ?capacity ?location WHERE { " +
                        "OPTIONAL {db:" + stadium + " dbo:description ?description.}" +
                        "OPTIONAL {db:" + stadium + " dbo:renovated ?renovated.}" +
                        "OPTIONAL {db:" + stadium + " dbp:opened ?opened.}" +
                        "OPTIONAL {db:" + stadium + " dbp:capacity ?capacity.}" +
                        "OPTIONAL {db:" + stadium + " ^dbo:location ?location.}" +
                        "FILTER (lang(?abstract) = 'en')" +
                        "}" +
                        "Limit 1";

        Ground newGround = new Ground();

        Query sparqlQuery = QueryFactory.create(query);

        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String[] arr = (solution.get("abstract").toString()).split("/");
                String abstracts = arr[arr.length - 1];
                newGround.setDescription(abstracts);
                if (solution.contains("renovated")) {
                    arr = (solution.get("renovated").toString()).split("/");
                    String renovated = arr[arr.length - 1];
                    newGround.setRenovated(renovated);
                }
                if (solution.contains("opened")) {
                    arr = (solution.get("opened").toString()).split("/");
                    String opened = arr[arr.length - 1];
                    newGround.setOpened(opened);
                }
                if (solution.contains("capacity")) {
                    arr = (solution.get("capacity").toString()).split("/");
                    String capacity = arr[arr.length - 1];
                    newGround.setCapacity(capacity);
                }
                if (solution.contains("location")) {
                    arr = (solution.get("location").toString()).split("/");
                    String location = arr[arr.length - 1];
                    newGround.setLocation(location);
                }
                newGround.setName(newGround.getName());
                newGround.setId(0);
                if (country.equals("French")) {
                    for (Club club : hcFrance) {
                        if (club.getGround() != null && club.getGround().getName().equals(stadium)) {
                            club.setGround(newGround);
                        }
                    }
                }
                if (country.equals("Spanish")) {
                    for (Club club : hcSpain) {
                        if (club.getGround() != null && club.getGround().getName().equals(stadium)) {
                            club.setGround(newGround);
                        }
                    }
                }
                if (country.equals("German")) {
                    for (Club club : hcGermany) {
                        if (club.getGround() != null && club.getGround().getName().equals(stadium)) {
                            club.setGround(newGround);
                        }
                    }
                }
                if (country.equals("Hungarian")) {
                    for (Club club : hcHungary) {
                        if (club.getGround() != null && club.getGround().getName().equals(stadium)) {
                            club.setGround(newGround);
                        }
                    }
                }

                break;
            }
        }
    }


    @PostConstruct
    public void init() {

        List<String> frenchClubs = new ArrayList<>(this.getAllClubs("French"));
        List<String> germanClubs = new ArrayList<>(this.getAllClubs("German"));
        List<String> spanishClubs = new ArrayList<>(this.getAllClubs("Spanish"));
        List<String> hungarianClubs = new ArrayList<>(this.getAllClubs("Hungarian"));


        //TODO DA GI PROVERIME SITE FOROVI STO ZNACAT TIE
        for (String club : frenchClubs) {
            if (!club.contains("'") && !club.contains("&") && !club.contains("2013")) {
                if (club.endsWith(".")) {
                    String c = club.substring(0, club.length() - 1);
                    c = c + "\\.";
                    club = c;
                }
                this.getClubDetails(club, "French");
            }

        }
        for (String club : spanishClubs) {
            if (!club.contains("'") && !club.contains("&") && !club.contains(")")) {
                if (club.endsWith(".")) {
                    String c = club.substring(0, club.length() - 1);
                    c = c + "\\.";
                    club = c;
                }
                this.getClubDetails(club, "Spanish");
            }

        }
        for (String club : germanClubs) {
            if (!club.contains("'") && !club.contains("&") && !club.contains(")")) {
                if (club.endsWith(".")) {
                    String c = club.substring(0, club.length() - 1);
                    c = c + "\\.";
                    club = c;
                }
                this.getClubDetails(club, "German");
            }

        }
        for (String club : hungarianClubs) {
            if (!club.contains("'") && !club.contains("&") && !club.contains(")")) {
                if (club.endsWith(".")) {
                    String c = club.substring(0, club.length() - 1);
                    c = c + "\\.";
                    club = c;
                }
                this.getClubDetails(club, "Hungarian");
            }

        }


        for (Club club : hcFrance) {
            if (club.getGround() != null && !club.getGround().getName().contains(",") && !club.getGround().getName().contains("'")) {
                this.getGroundDetails(club.getGround().getName(), "French");
                if (club.getGround() != null) {
                    groundRepository.save(club.getGround());
                }
            }
        }

        for (Club club : hcSpain) {
            if (club.getGround() != null && !club.getGround().getName().contains(",") && !club.getGround().getName().contains("'") && !club.getGround().getName().contains(")")) {
                this.getGroundDetails(club.getGround().getName(), "Spanish");
                if (club.getGround() != null) {
                    groundRepository.save(club.getGround());
                }
            }
        }

        for (Club club : hcGermany) {
            if (club.getGround() != null && !club.getGround().getName().contains(",") && !club.getGround().getName().contains(")")) {
                this.getGroundDetails(club.getGround().getName(), "Germany");
                if (club.getGround() != null) {
                  groundRepository.save(club.getGround());
                }
            }
        }
        for (Club club : hcHungary) {
            if (club.getGround() != null && !club.getGround().getName().contains(",")
                    && !club.getGround().getName().contains("=")
                    && !club.getGround().getName().contains(")")
                    && !club.getGround().getName().contains("_")) {
                this.getGroundDetails(club.getGround().getName(), "England");
                if (club.getGround() != null) {
                    groundRepository.save(club.getGround());
                }
            }
        }

        countries = Arrays.asList(
                new Country(0, "French", hcFrance),
                new Country(0, "Spanish", hcSpain),
                new Country(0, "German", hcGermany),
                new Country(0, "Hungarian", hcHungary)
        );

        this.clubRepository.saveAll(hcFrance);
        this.clubRepository.saveAll(hcSpain);
        this.clubRepository.saveAll(hcGermany);
        this.clubRepository.saveAll(hcHungary);
        this.countryRepository.saveAll(countries);


    }




}
