package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by akarthik10 on 12/30/2015.
 */
@Entity
@Table(name = "SKILL")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillPref implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToOne(fetch = FetchType.EAGER)
    private Subcategory subcategory;

    @Column(name = "STARTTIME")
    private String startTime;

    @Column(name = "ENDTIME")
    private String endTime;


    public SkillPref() {
    }

    public SkillPref(Category category, Subcategory subcategory, String startTime, String endTime) {
        this.category = category;
        this.subcategory = subcategory;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}