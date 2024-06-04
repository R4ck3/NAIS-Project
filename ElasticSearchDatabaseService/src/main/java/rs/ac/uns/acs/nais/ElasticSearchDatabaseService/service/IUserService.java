package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service;

import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findByFullNameOrUsername(String fullName, String username);

    List<User> findByFullNameContainingOrUsernameContaining(String fullName, String username);

    List<User> findByCustomQuery(String query);

    List<User> searchByAddressPhrase(String phrase);

    List<User> searchByFullNameOrUsernameFuzzy(String searchTerm);

    List<User> findByFunctionScore(String searchTerm, String boostTerms);

    List<User> findAll();

    User createUser(User user);

    void deleteUserById(String id);

    Optional<User> findUserById(String id);  
}
