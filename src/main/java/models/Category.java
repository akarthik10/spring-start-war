package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by akarthik10 on 12/30/2015.
 */
@Entity
@Table(name = "CATEGORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category implements Serializable {


    @Column(name = "NAME")
    private String name;

    @Id
    @Column(name = "ID", unique = true)
    private String key;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Subcategory> subcategories;

    public Category(String name, String key, Set<Subcategory> subcategories) {
        this.name = name;
        this.key = key;
        this.subcategories = subcategories;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
}