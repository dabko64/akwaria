package model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "fish")
public class Fish implements Comparable<Fish>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String species;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FishCondition condition;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String origin;

    @ManyToOne
    @JoinColumn(name = "aquarium_id")
    private Aquarium aquarium;

    public Fish() {
    }

    public Fish(String name, String species, FishCondition condition, int age, double weight, String origin) {
        this.name = name;
        this.species = species;
        this.condition = condition;
        this.age = age;
        this.weight = weight;
        this.origin = origin;
    }

    @Override
    public int compareTo(Fish other) {
        int byName = this.name.compareToIgnoreCase(other.name);
        if (byName != 0) return byName;

        int bySpecies = this.species.compareToIgnoreCase(other.species);
        if (bySpecies != 0) return bySpecies;

        return Integer.compare(this.age, other.age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fish fish)) return false;
        return age == fish.age &&
                Objects.equals(name, fish.name) &&
                Objects.equals(species, fish.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, species, age);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public FishCondition getCondition() {
        return condition;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public String getOrigin() {
        return origin;
    }

    public Aquarium getAquarium() {
        return aquarium;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setCondition(FishCondition condition) {
        this.condition = condition;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setAquarium(Aquarium aquarium) {
        this.aquarium = aquarium;
    }
}