package dto;

public class AquariumRatingStatsDto {
    private final String aquariumName;
    private final long ratingsCount;
    private final double averageRating;

    public AquariumRatingStatsDto(String aquariumName, long ratingsCount, double averageRating) {
        this.aquariumName = aquariumName;
        this.ratingsCount = ratingsCount;
        this.averageRating = averageRating;
    }

    public String getAquariumName() {
        return aquariumName;
    }

    public long getRatingsCount() {
        return ratingsCount;
    }

    public double getAverageRating() {
        return averageRating;
    }
}