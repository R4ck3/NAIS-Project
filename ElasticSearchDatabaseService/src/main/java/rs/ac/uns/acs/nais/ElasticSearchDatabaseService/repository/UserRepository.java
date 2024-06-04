package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    List<User> findByFullNameOrUsername(String fullName, String username);

    List<User> findByFullNameContainingOrUsernameContaining(String fullName, String username);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"fullName\": \"?0\"}}, {\"match\": {\"username\": \"?0\"}}]}}")
    List<User> findByCustomQuery(String query);

    @Query("{\"match_phrase\":{\"address\":\"?0\"}}")
    List<User> searchByAddressPhrase(String phrase);

    @Query("{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"fullName^3\",\"username\"],\"fuzziness\":\"AUTO\"}}")
    List<User> searchByFullNameOrUsernameFuzzy(String searchTerm);

    @Query("{\"function_score\":{\"query\":{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"full_name^3\",\"username\"]}},\"functions\":[{\"filter\":{\"match\":{\"address\":\"?1\"}},\"weight\":2}],\"score_mode\":\"sum\",\"boost_mode\":\"multiply\"}}")
    List<User> findByFunctionScore(String searchTerm, String boostTerms);

    @Query("{\"match_all\": {}}")
    List<User> findAll();

    void deleteById(String id);

    @Query("{\"term\": {\"_id\": \"?0\"}}")
    Optional<User> findById(String id);
}
