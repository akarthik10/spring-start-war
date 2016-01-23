package models;

/**
 * Created by akarthik10 on 12/30/2015.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by akarthik10 on 12/30/2015.
 */
@Entity
@Table(name = "SUBCATEGORY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subcategory implements Serializable {


    @Column(name = "NAME")
    private String name;

    @Id
    @Column(name = "ID", unique = true)
    private String key;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID")
    @JsonBackReference
    private Category category;

    public Subcategory(String name, String key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
    }

    public Subcategory() {
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


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}