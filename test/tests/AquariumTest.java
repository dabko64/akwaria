package tests;

import exceptions.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AquariumTest {
    private Aquarium aquarium;
    private Fish fish1;

    @BeforeEach
    void setUp() throws InvalidDataException {
        aquarium = new Aquarium("Testowe", 2);
        fish1 = new Fish("Nemo", "Clownfish", FishCondition.ZDROWA, 1, 0.2, "Australia");
    }

    @Test
    void shouldAddFish() throws Exception {
        Aquarium a = new Aquarium("test", 2);
        Fish f = new Fish("Nemo", "clownfish", FishCondition.ZDROWA, 1, 0.5, "ocean");

        a.addFish(f);

        assertEquals(1, a.getFishList().size());
    }

    @Test
    void shouldThrowExceptionWhenCapacityExceeded() throws OceanariumException {
        aquarium.addFish(fish1);
        aquarium.addFish(new Fish("Dory", "Blue Tang", FishCondition.ZDROWA, 2, 0.3, "Ocean"));

        assertThrows(AquariumCapacityExceededException.class, () ->
                aquarium.addFish(new Fish("Marlin", "Clownfish", FishCondition.CHORA, 4, 0.4, "Ocean")));
    }

    @Test
    void shouldThrowExceptionWhenDuplicateFish() throws OceanariumException {
        aquarium.addFish(fish1);

        assertThrows(FishAlreadyExistsException.class, () ->
                aquarium.addFish(new Fish("Nemo", "Clownfish", FishCondition.ZDROWA, 1, 0.2, "Australia")));
    }

    @Test
    void shouldSearchFishByName() throws OceanariumException {
        aquarium.addFish(fish1);
        Fish result = aquarium.search("nemo");

        assertNotNull(result);
        assertEquals("Nemo", result.getName());
    }

    @Test
    void shouldFilterByCondition() throws OceanariumException {
        aquarium.addFish(fish1);
        aquarium.addFish(new Fish("Dory", "Blue Tang", FishCondition.CHORA, 2, 0.3, "Ocean"));

        assertEquals(1, aquarium.filterByCondition(FishCondition.CHORA).size());
    }

    @Test
    void shouldRemoveFish() throws Exception {
        Aquarium a = new Aquarium("test", 2);
        Fish f = new Fish("Nemo", "clownfish", FishCondition.ZDROWA, 1, 0.5, "ocean");

        a.addFish(f);
        a.removeFish(f);

        assertTrue(a.getFishList().isEmpty());
    }

    @Test
    void shouldFindFish() throws Exception {
        Aquarium a = new Aquarium("test", 2);
        Fish f = new Fish("Nemo", "clownfish", FishCondition.ZDROWA, 1, 0.5, "ocean");

        a.addFish(f);

        assertNotNull(a.search("Nemo"));
    }

    @Test
    void shouldReturnNullWhenSearchFails() throws Exception {
        Aquarium a = new Aquarium("test", 2);

        assertNull(a.search("Brak"));
    }

    @Test
    void shouldCountByCondition() throws Exception {
        Aquarium a = new Aquarium("test", 3);
        a.addFish(new Fish("Nemo", "clownfish", FishCondition.ZDROWA, 1, 0.5, "ocean"));
        a.addFish(new Fish("Dory", "blue tang", FishCondition.CHORA, 2, 0.4, "ocean"));
        a.addFish(new Fish("Borys", "beta", FishCondition.CHORA, 1, 0.2, "river"));

        assertEquals(2, a.countByCondition(FishCondition.CHORA));
    }

    @Test
    void shouldSortByName() throws Exception {
        Aquarium a = new Aquarium("test", 3);
        a.addFish(new Fish("Zenek", "a", FishCondition.ZDROWA, 1, 0.5, "ocean"));
        a.addFish(new Fish("Adam", "b", FishCondition.ZDROWA, 1, 0.4, "ocean"));

        List<Fish> sorted = a.sortByName();

        assertEquals("Adam", sorted.get(0).getName());
        assertEquals("Zenek", sorted.get(1).getName());
    }

    @Test
    void shouldSortByWeight() throws Exception {
        Aquarium a = new Aquarium("test", 3);
        a.addFish(new Fish("Nemo", "a", FishCondition.ZDROWA, 1, 0.9, "ocean"));
        a.addFish(new Fish("Dory", "b", FishCondition.ZDROWA, 1, 0.3, "ocean"));

        List<Fish> sorted = a.sortByWeight();

        assertEquals("Dory", sorted.get(0).getName());
        assertEquals("Nemo", sorted.get(1).getName());
    }

    @Test
    void shouldReturnHeaviestFish() throws Exception {
        Aquarium a = new Aquarium("test", 3);
        a.addFish(new Fish("Nemo", "a", FishCondition.ZDROWA, 1, 0.9, "ocean"));
        a.addFish(new Fish("Dory", "b", FishCondition.ZDROWA, 1, 0.3, "ocean"));

        Fish maxFish = a.max();

        assertNotNull(maxFish);
        assertEquals("Nemo", maxFish.getName());
    }

    @Test
    void shouldReturnNullWhenNoFishForMax() throws Exception {
        Aquarium a = new Aquarium("test", 3);

        assertNull(a.max());
    }
}