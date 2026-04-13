package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ratings")
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating_value", nullable = false)
    private int ratingValue;

    @Column(nullable = false)
    private LocalDate ratingDate;

    @Column
    private String comment;

    @ManyToOne
    @JoinColumn(name = "aquarium_id", nullable = false)
    private Aquarium aquarium;

    public Rating() {
    }

    public Rating(int ratingValue, LocalDate ratingDate, String comment) {
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public LocalDate getRatingDate() {
        return ratingDate;
    }

    public String getComment() {
        return comment;
    }

    public Aquarium getAquarium() {
        return aquarium;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public void setRatingDate(LocalDate ratingDate) {
        this.ratingDate = ratingDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAquarium(Aquarium aquarium) {
        this.aquarium = aquarium;
    }
}