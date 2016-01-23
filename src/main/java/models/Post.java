package models;

/**
 * Created by akarthik10 on 11/14/2015.
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import config.SevenElevenConstants;
import org.hibernate.validator.constraints.Email;


import javax.persistence.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "POST")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;



    public Post() {
    }

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    @OneToOne
    private Category category;

    @OneToOne
    private Subcategory subcategory;

    @Column(name = "RATE")
    private String rate;

    @Column(name = "CREATED")
    private Date created;

    @Column(name = "EXPIRY")
    @JsonIgnore
    private Date expiry;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private User user;

    @Column(name = "REQUEST_ACCEPTED")
    private boolean request_accepted = false;

    @OneToOne
    private User acceptedUser;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<PostRequest> PostRequests;

    public Post(String title, String description, double latitude, double longitude, Date created, Date expiry, User user, Category category, Subcategory subcategory, String rate, boolean request_accepted) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created = created;
        this.expiry = expiry;
        this.user = user;
        this.category = category;
        this.subcategory = subcategory;
        this.rate = rate;
        this.request_accepted = request_accepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public boolean isRequest_accepted() {
        return request_accepted;
    }

    public void setRequest_accepted(boolean request_accepted) {
        this.request_accepted = request_accepted;
    }

    public User getAcceptedUser() {
        return acceptedUser;
    }

    public void setAcceptedUser(User acceptedUser) {
        this.acceptedUser = acceptedUser;
    }

    public Set<PostRequest> getPostRequests() {
        return PostRequests;
    }

    public void setPostRequests(Set<PostRequest> postRequests) {
        PostRequests = postRequests;
    }
}
