package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by akarthik10 on 12/30/2015.
 */
@Entity
@Table(name = "POSTREQUEST")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequest implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private User byUser;

    @Column(name = "REQUESTED")
    private Date when;

    public PostRequest() {
    }

    public PostRequest(Long id, User byUser) {
        this.id = id;
        this.byUser = byUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }
}