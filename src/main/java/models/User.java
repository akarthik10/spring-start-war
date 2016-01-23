package models;

/**
 * Created by akarthik10 on 11/14/2015.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import config.SevenElevenConstants;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Email;


import javax.persistence.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    public User(String first_name,String last_name, String email, String phone, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email.toLowerCase();
        this.phone = phone;
        this.password = password;
        this.activated = false;
        this.activationCode = generateActivationCode();
    }

    public User() {
    }

    @Column(name = "FIRSTNAME")
    private String first_name;

    @Column(name = "LASTNAME")
    private String last_name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @Column(name = "ACTIVATED")
    private boolean activated;

    @Column(name = "ACTIVATIONCODE")
    @JsonIgnore
    private String activationCode;

    @Column(name = "RESETCODE")
    @JsonIgnore
    private String resetCode;

    @Column(name = "RESETEXPIRY")
    @JsonIgnore
    private Date resetDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Post> Posts;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;

    @Column(name = "GCMID")
    @JsonIgnore
    private String gcm_id;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setFirstName(String name) {
        this.first_name= name;
    }
    public void setLastName(String name) {
        this.last_name= name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String generateActivationCode() {
        return randomString();
    }

    private String randomString() {
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
        String pw = "";
        final Random RANDOM = new SecureRandom();
        for (int i = 0; i< SevenElevenConstants.USER_ACTIVATION_CODE_LENGTH; i++)
        {
            int index = (int)(RANDOM.nextDouble()*letters.length());
            pw += letters.substring(index, index+1);
        }
        return pw;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getGcm_id() {
        return gcm_id;
    }

    public void setGcm_id(String gcm_id) {
        this.gcm_id = gcm_id;
    }
}
