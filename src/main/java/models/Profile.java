package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by akarthik10 on 12/30/2015.
 */
@Entity
@Table(name = "PROFILE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile implements Serializable {


    @Column(name = "LOCATION")
    private String location;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<SkillPref> skillPrefs;

    /*@OneToOne
    private User user;
*/


    public Profile() {
    }

    public Profile(String location, Set<SkillPref> skillPrefs) {
        this.location = location;
        this.skillPrefs = skillPrefs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<SkillPref> getSkillPrefs() {
        return skillPrefs;
    }

    public void setSkillPrefs(Set<SkillPref> skillPrefs) {
        this.skillPrefs = skillPrefs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    */
}