package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.User;
import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    List<User> findByFull_nameOrUsername(String fullName, String username);

    List<User> findByFull_nameContainingOrUsernameContaining(String fullName, String username);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"full_name\": \"?0\"}}, {\"match\": {\"username\": \"?0\"}}]}}")
    List<User> findByCustomQuery(String query);

    @Query("{\"match_phrase\":{\"address\":\"?0\"}}")
    List<User> searchByAddressPhrase(String phrase);

    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"full_name^3\",\"username\"],\"fuzziness\":\"AUTO\"}}")
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
    @Query("{\"bool\":{\"must\":[{\"match\":{\"full_name\":\"?0\"}}],\"must_not\":[{\"match\":{\"address\":\"?1\"}}],\"should\":[{\"match\":{\"attribute\":\"?2\"}}]}}")
    List<User> findByFullNameAndAddressNotAndOptional(String fullName, String mustNotTerms, String shouldTerms);

    /**
     * Pronalazi korisnike na osnovu specifičnih atributa i vrši agregaciju kako bi dobio statistiku o broju korisnika po svakom atributu.
     *
     * @param attributeName ime atributa po kojem se vrši pretraga
     * @param attributeValue vrednost atributa koja se traži
     * @return lista korisnika koji zadovoljavaju dati kriterijum
     */
    @Query("{\"nested\":{\"path\":\"attributes\",\"query\":{\"bool\":{\"must\":[{\"match\":{\"attributes.name\":\"?0\"}},{\"match\":{\"attributes.value\":\"?1\"}}]}}},\"aggs\":{\"attribute_stats\":{\"terms\":{\"field\":\"attributes.value.keyword\"}}}}")
    List<User> findByNestedAttributeAndAggregate(String attributeName, String attributeValue);

    /**
     * Rangira korisnike na osnovu različitih kriterijuma, kao što su relevantnost prema pretraživanom pojmu ili pojava određenih reči u adresi.
     *
     * @param searchTerm pojam pretrage koji se koristi za rangiranje
     * @param boostTerms reči koje, kada se pojave u adresi, daju dodatni skor korisniku
     * @return lista korisnika rangiranih na osnovu datih kriterijuma
     */
    @Query("{\"function_score\":{\"query\":{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"full_name^3\",\"username\"]}},\"functions\":[{\"filter\":{\"match\":{\"address\":\"?1\"}},\"weight\":2}],\"score_mode\":\"sum\",\"boost_mode\":\"multiply\"}}")
    List<User> findByFunctionScore(String searchTerm, String boostTerms);

    @Query("{\"match_all\": {}}")
    List<User> findAll();
}
