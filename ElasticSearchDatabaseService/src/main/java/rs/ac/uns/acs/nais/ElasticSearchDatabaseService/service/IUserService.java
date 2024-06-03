package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.User;

import java.util.List;

public interface IUserService {

    List<User> findByFullNameOrUsername(String fullName, String username);

    List<User> findByFullNameContainingOrUsernameContaining(String fullName, String username);

    List<User> findByCustomQuery(String query);

    List<User> searchByAddressPhrase(String phrase);

    List<User> searchByFullNameOrUsernameFuzzy(String searchTerm);

    /**
     * Pronalazi korisnike koji tačno odgovaraju zadatom imenu, ali ne sadrže određene reči u adresi.
     * Takođe, omogućava pronalaženje korisnika koji mogu odgovarati drugim opcionalnim uslovima.
     *
     * @param fullName ime korisnika koji se traži
     * @param mustNotTerms reči koje ne smeju biti prisutne u adresi korisnika
     * @param shouldTerms opcionalni uslovi koji korisnike čine dodatno relevantnim
     * @return lista korisnika koji zadovoljavaju dati kriterijum
     */
    List<User> findByFullNameAndAddressNotAndOptional(String fullName, String mustNotTerms, String shouldTerms);

    /**
     * Pronalazi korisnike na osnovu specifičnih atributa i vrši agregaciju kako bi dobio statistiku o broju korisnika po svakom atributu.
     *
     * @param attributeName ime atributa po kojem se vrši pretraga
     * @param attributeValue vrednost atributa koja se traži
     * @return lista korisnika koji zadovoljavaju dati kriterijum
     */
    List<User> findByNestedAttributeAndAggregate(String attributeName, String attributeValue);

    /**
     * Rangira korisnike na osnovu različitih kriterijuma, kao što su relevantnost prema pretraživanom pojmu ili pojava određenih reči u adresi.
     *
     * @param searchTerm pojam pretrage koji se koristi za rangiranje
     * @param boostTerms reči koje, kada se pojave u adresi, daju dodatni skor korisniku
     * @return lista korisnika rangiranih na osnovu datih kriterijuma
     */
    List<User> findByFunctionScore(String searchTerm, String boostTerms);

    /**
     * Pronalazi sve korisnike.
     *
     * @return lista svih korisnika
     */
    List<User> findAll();
}
