package facade;

import dao.FishDao;
import dao.RatingDao;
import dto.AquariumRatingStatsDto;
import dto.AquariumTableRowDto;
import exceptions.*;
import model.*;
import service.BinaryFileService;
import service.CsvFileService;
import service.StatisticsService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OceanariumFacade {
    private final BinaryFileService binaryFileService;
    private final CsvFileService csvFileService;
    private final OceanariumManager manager;
    private final FishDao fishDao;
    private final RatingDao ratingDao;
    private final StatisticsService statisticsService;

    public OceanariumFacade(OceanariumManager manager) {
        this.manager = manager;
        this.fishDao = new FishDao();
        this.ratingDao = new RatingDao();
        this.statisticsService = new StatisticsService();
        this.binaryFileService = new BinaryFileService();
        this.csvFileService = new CsvFileService();
    }

    public List<Aquarium> getAquariums() {
        return manager.getAllAquariums();
    }

    public List<AquariumTableRowDto> getAquariumTableRows() {
        List<Aquarium> aquariums = manager.getAllAquariums();
        List<AquariumRatingStatsDto> stats = statisticsService.getAquariumRatingStats();
        List<AquariumTableRowDto> rows = new ArrayList<>();

        for (Aquarium aquarium : aquariums) {
            long ratingsCount = 0;
            double averageRating = 0.0;

            for (AquariumRatingStatsDto stat : stats) {
                if (stat.getAquariumName().equals(aquarium.getAquariumName())) {
                    ratingsCount = stat.getRatingsCount();
                    averageRating = stat.getAverageRating();
                    break;
                }
            }

            rows.add(new AquariumTableRowDto(
                    aquarium.getAquariumName(),
                    aquarium.getMaxCapacity(),
                    aquarium.getCurrentLoad(),
                    aquarium.getFillPercentage(),
                    ratingsCount,
                    averageRating
            ));
        }

        return rows;
    }

    public List<Fish> getFishInAquarium(String aquariumName) throws AquariumNotFoundException {
        return fishDao.findByAquariumName(aquariumName);
    }

    public void addAquarium(String name, int capacity) throws OceanariumException {
        manager.addAquarium(name, capacity);
    }

    public void removeAquarium(String name) throws OceanariumException {
        manager.removeAquarium(name);
    }

    public void addFish(String aquariumName, Fish fish) throws OceanariumException {
        Aquarium aquarium = manager.getAquarium(aquariumName);

        if (aquarium.getCurrentLoad() >= aquarium.getMaxCapacity()) {
            throw new AquariumCapacityExceededException(aquariumName);
        }

        fish.setAquarium(aquarium);
        fishDao.save(fish);
    }

    public void removeFish(String aquariumName, Fish fish) throws OceanariumException {
        fishDao.delete(fish);
    }

    public void updateFishCondition(String aquariumName, Fish fish, FishCondition condition) throws OceanariumException {
        fish.setCondition(condition);
        fishDao.update(fish);
    }

    public void sortAquariumsByCurrentLoad(List<AquariumTableRowDto> aquariums) {
        aquariums.sort((a, b) -> Integer.compare(a.getCurrentLoad(), b.getCurrentLoad()));
    }

    public List<Fish> filterFishByText(String aquariumName, String text) throws OceanariumException {
        String pattern = text.toLowerCase();

        return fishDao.findByAquariumName(aquariumName).stream()
                .filter(f -> f.getName().toLowerCase().contains(pattern)
                        || f.getSpecies().toLowerCase().contains(pattern))
                .toList();
    }

    public List<Fish> filterFishByState(String aquariumName, FishCondition condition) throws OceanariumException {
        return fishDao.findByAquariumName(aquariumName).stream()
                .filter(f -> f.getCondition() == condition)
                .toList();
    }

    public List<Fish> getAllFishForSelectedAquarium(String aquariumName) throws OceanariumException {
        return fishDao.findByAquariumName(aquariumName);
    }

    public void addRating(String aquariumName, int ratingValue, String comment) throws OceanariumException {
        if (ratingValue < 0 || ratingValue > 5) {
            throw new InvalidDataException("ocena musi byc z zakresu 0-5");
        }

        Aquarium aquarium = manager.getAquarium(aquariumName);

        Rating rating = new Rating(ratingValue, LocalDate.now(), comment);
        rating.setAquarium(aquarium);

        ratingDao.save(rating);
    }

    public void exportSelectedAquariumToCsv(String aquariumName, String filePath) throws IOException {
        csvFileService.exportAquariumToCsv(aquariumName, filePath);
    }

    public void importAquariumFromCsv(String filePath) throws Exception {
        csvFileService.importAquariumFromCsv(filePath);
    }

    public void saveAquariumsToBinary(String filePath) throws IOException {
        binaryFileService.saveAquariumsToBinary(manager.getAllAquariums(), filePath);
    }

    public List<Aquarium> loadAquariumsFromBinary(String filePath) throws IOException, ClassNotFoundException {
        return binaryFileService.loadAquariumsFromBinary(filePath);
    }
}