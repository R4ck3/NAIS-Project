package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.User;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.UserRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findByFullNameOrUsername(String fullName, String username) {
        return userRepository.findByFullNameOrUsername(fullName, username);
    }

    public List<User> findByFullNameContainingOrUsernameContaining(String fullName, String username) {
        return userRepository.findByFullNameContainingOrUsernameContaining(fullName, username);
    }

    public List<User> findByCustomQuery(String query) {
        return userRepository.findByCustomQuery(query);
    }

    public List<User> searchByAddressPhrase(String phrase) {
        return userRepository.searchByAddressPhrase(phrase);
    }

    public List<User> searchByFullNameOrUsernameFuzzy(String searchTerm) {
        return userRepository.searchByFullNameOrUsernameFuzzy(searchTerm);
    }

    public List<User> findByFunctionScore(String searchTerm, String boostTerms) {
        return userRepository.findByFunctionScore(searchTerm, boostTerms);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public String getFullNameById(String id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? user.getFullName() : null;
    }
}
