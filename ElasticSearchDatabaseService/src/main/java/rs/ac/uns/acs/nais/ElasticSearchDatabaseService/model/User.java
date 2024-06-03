package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.beans.factory.annotation.Value;

@Document(indexName = "users")
public class User {
    @Id
    private String id;
    private String full_name;
    private String username;
    private String email;
    private String phone_number;
    private String address;

    public User(String id, String full_name, String username, String email, String phone_number, String address) {
        this.id = id;
        this.full_name = full_name;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
    }

    public User(){
            
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
