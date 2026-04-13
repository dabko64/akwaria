package data;

import dao.FishDao;
import dao.RatingDao;
import exceptions.OceanariumException;
import model.Aquarium;
import model.Fish;
import model.FishCondition;
import model.OceanariumManager;
import model.Rating;

import java.time.LocalDate;

public class DataGenerator {
    private static DataGenerator instance;

    private DataGenerator() {
    }

    public static DataGenerator getInstance() {
        if (instance == null) {
            instance = new DataGenerator();
        }
        return instance;
    }

    public OceanariumManager createSampleData() throws OceanariumException {
        OceanariumManager manager = new OceanariumManager();
        FishDao fishDao = new FishDao();
        RatingDao ratingDao = new RatingDao();

        if (!manager.getAllAquariums().isEmpty()) {
            return manager;
        }

        manager.addAquarium("tropikalne", 5);
        manager.addAquarium("morskie", 4);
        manager.addAquarium("kwarantanna", 3);

        Aquarium tropikalne = manager.getAquarium("tropikalne");
        Aquarium morskie = manager.getAquarium("morskie");

        Fish f1 = new Fish("Jadwiga", "Tilapia", FishCondition.ZDROWA, 2, 0.5, "Australia");
        f1.setAquarium(tropikalne);
        fishDao.save(f1);

        Fish f2 = new Fish("Krzysztof", "Morszczuk", FishCondition.CHORA, 3, 0.8, "Ocean Spokojny");
        f2.setAquarium(tropikalne);
        fishDao.save(f2);

        Fish f3 = new Fish("Halina", "Śledź", FishCondition.KWARANTANNA, 4, 0.4, "Norwegia");
        f3.setAquarium(morskie);
        fishDao.save(f3);

        Rating r1 = new Rating(5, LocalDate.now(), "super akwarium");
        r1.setAquarium(tropikalne);
        ratingDao.save(r1);

        Rating r2 = new Rating(4, LocalDate.now(), "bardzo ladne");
        r2.setAquarium(tropikalne);
        ratingDao.save(r2);

        Rating r3 = new Rating(3, LocalDate.now(), "moze byc");
        r3.setAquarium(morskie);
        ratingDao.save(r3);

        return manager;
    }
}