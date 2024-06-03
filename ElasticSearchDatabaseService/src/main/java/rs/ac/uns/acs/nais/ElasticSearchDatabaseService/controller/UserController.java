package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.User;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.UserService;

import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/users.json")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String hello() {
        return "hello";
    }

    @PostMapping
    public void addProduct(@RequestBody User user) {
        userService.save(user);
    }

    @GetMapping("/findByFullNameOrUsername")
    public List<User> findByFullNameOrUsername(@RequestParam(value = "fullName") String fullName,
                                               @RequestParam(value = "username") String username) {
        return userService.findByFullNameOrUsername(fullName, username);
    }

    @GetMapping("/findByFullNameContainingOrUsernameContaining")
    public List<User> findByFullNameContainingOrUsernameContaining(@RequestParam(value = "fullName") String fullName,
                                                                   @RequestParam(value = "username") String username) {
        return userService.findByFullNameContainingOrUsernameContaining(fullName, username);
    }

    @GetMapping("/findByCustomQuery")
    public List<User> findByCustomQuery(@RequestParam(value = "query") String query) {
        return userService.findByCustomQuery(query);
    }

    @GetMapping("/searchByAddressPhrase")
    public List<User> searchByAddressPhrase(@RequestParam(value = "phrase") String phrase) {
        return userService.searchByAddressPhrase(phrase);
    }

    @GetMapping("/searchByFullNameOrUsernameFuzzy")
    public List<User> searchByFullNameOrUsernameFuzzy(@RequestParam(value = "searchTerm") String searchTerm) {
        return userService.searchByFullNameOrUsernameFuzzy(searchTerm);
    }

    @GetMapping("/findByFullNameAndAddressNotAndOptional")
    public List<User> findByFullNameAndAddressNotAndOptional(@RequestParam(value = "fullName") String fullName,
                                                             @RequestParam(value = "mustNotTerms") String mustNotTerms,
                                                             @RequestParam(value = "shouldTerms") String shouldTerms) {
        return userService.findByFullNameAndAddressNotAndOptional(fullName, mustNotTerms, shouldTerms);
    }

    @GetMapping("/findByFunctionScore")
    public List<User> findByFunctionScore(@RequestParam(value = "searchTerm") String searchTerm,
                                          @RequestParam(value = "boostTerms") String boostTerms) {
        return userService.findByFunctionScore(searchTerm, boostTerms);
    }
}