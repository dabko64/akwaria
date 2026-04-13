package dto;

public class AquariumTableRowDto {
    private final String aquariumName;
    private final int maxCapacity;
    private final int currentLoad;
    private final double fillPercentage;
    private final long ratingsCount;
    private final double averageRating;

    public AquariumTableRowDto(String aquariumName,
                               int maxCapacity,
                               int currentLoad,
                               double fillPercentage,
                               long ratingsCount,
                               double averageRating) {
        this.aquariumName = aquariumName;
        this.maxCapacity = maxCapacity;
        this.currentLoad = currentLoad;
        this.fillPercentage = fillPercentage;
        this.ratingsCount = ratingsCount;
        this.averageRating = averageRating;
    }

    public String getAquariumName() {
        return aquariumName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public double getFillPercentage() {
        return fillPercentage;
    }

    public long getRatingsCount() {
        return ratingsCount;
    }

    public double getAverageRating() {
        return averageRating;
    }
}