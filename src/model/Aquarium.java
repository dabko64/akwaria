package model;

import exceptions.AquariumCapacityExceededException;
import exceptions.FishAlreadyExistsException;
import exceptions.FishNotFoundException;
import exceptions.InvalidDataException;
import exceptions.OceanariumException;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "aquariums")
public class Aquarium implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String aquariumName;

    @Column(nullable = false)
    private int maxCapacity;

    @OneToMany(mappedBy = "aquarium", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fish> fishList = new ArrayList<>();

    @OneToMany(mappedBy = "aquarium", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    public Aquarium() {
    }

    public Aquarium(String aquariumName, int maxCapacity) throws InvalidDataException {
        if (aquariumName == null || aquariumName.isBlank()) {
            throw new InvalidDataException("akwarium musi miec nazwe");
        }
        if (maxCapacity <= 0) {
            throw new InvalidDataException("podaj wieksza pojemnosc");
        }

        this.aquariumName = aquariumName;
        this.maxCapacity = maxCapacity;
    }

    public void addFish(Fish fish) throws OceanariumException {
        if (fish == null) {
            throw new InvalidDataException("podaj prawidlowa rybe");
        }
        if (fishList.size() >= maxCapacity) {
            throw new AquariumCapacityExceededException(aquariumName);
        }
        if (fishList.contains(fish)) {
            throw new FishAlreadyExistsException(fish.getName());
        }

        fishList.add(fish);
        fish.setAquarium(this);
    }

    public void removeFish(Fish fish) throws FishNotFoundException {
        if (!fishList.remove(fish)) {
            throw new FishNotFoundException(fish.getName());
        }
        fish.setAquarium(null);
    }

    public Fish moveFish(Fish fish) throws FishNotFoundException {
        if (!fishList.remove(fish)) {
            throw new FishNotFoundException(fish.getName());
        }
        fish.setAquarium(null);
        return fish;
    }

    public void changeCondition(Fish fish, FishCondition condition) throws FishNotFoundException {
        if (!fishList.contains(fish)) {
            throw new FishNotFoundException(fish.getName());
        }
        fish.setCondition(condition);
    }

    public void changeAge(Fish fish, int age) throws OceanariumException {
        if (!fishList.contains(fish)) {
            throw new FishNotFoundException(fish.getName());
        }
        if (age < 0) {
            throw new InvalidDataException("wiek zaczyna sie od 0");
        }
        fish.setAge(age);
    }

    public int countByCondition(FishCondition condition) {
        int count = 0;
        for (Fish fish : fishList) {
            if (fish.getCondition() == condition) {
                count++;
            }
        }
        return count;
    }

    public List<Fish> sortByName() {
        return fishList.stream()
                .sorted(Comparator.comparing(Fish::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<Fish> sortByWeight() {
        return fishList.stream()
                .sorted(Comparator.comparingDouble(Fish::getWeight))
                .collect(Collectors.toList());
    }

    public Fish search(String name) {
        for (Fish fish : fishList) {
            if (fish.getName().equalsIgnoreCase(name)) {
                return fish;
            }
        }
        return null;
    }

    public List<Fish> searchPartial(String text) {
        String pattern = text.toLowerCase();
        return fishList.stream()
                .filter(f -> f.getName().toLowerCase().contains(pattern)
                        || f.getSpecies().toLowerCase().contains(pattern))
                .collect(Collectors.toList());
    }

    public List<Fish> filterByCondition(FishCondition condition) {
        return fishList.stream()
                .filter(f -> f.getCondition() == condition)
                .collect(Collectors.toList());
    }

    public Fish max() {
        if (fishList.isEmpty()) {
            return null;
        }
        return Collections.max(fishList, Comparator.comparingDouble(Fish::getWeight));
    }

    public double getFillPercentage() {
        return (double) fishList.size() / maxCapacity * 100.0;
    }

    public int getCurrentLoad() {
        return fishList.size();
    }

    public boolean isEmpty() {
        return fishList.isEmpty();
    }

    public void addRating(Rating rating) {
        if (rating != null) {
            ratings.add(rating);
            rating.setAquarium(this);
        }
    }

    public void removeRating(Rating rating) {
        if (rating != null) {
            ratings.remove(rating);
            rating.setAquarium(null);
        }
    }

    public Long getId() {
        return id;
    }

    public String getAquariumName() {
        return aquariumName;
    }

    public void setAquariumName(String aquariumName) {
        this.aquariumName = aquariumName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<Fish> getFishList() {
        return fishList;
    }

    public void setFishList(List<Fish> fishList) {
        this.fishList = fishList;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}