package tests;

import exceptions.AquariumNotFoundException;
import exceptions.OceanariumException;
import facade.OceanariumFacade;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OceanariumFacadeTest {
    private OceanariumFacade facade;

    @BeforeEach
    void setUp() throws OceanariumException {
        OceanariumManager manager = new OceanariumManager();
        manager.addAquarium("A1", 3);
        facade = new OceanariumFacade(manager);
    }

    @Test
    void shouldAddFishThroughFacade() throws OceanariumException {
        OceanariumManager manager = new OceanariumManager();
        OceanariumFacade facade = new OceanariumFacade(manager);

        facade.addAquarium("test", 2);

        Fish fish = new Fish("Nemo", "clownfish", FishCondition.ZDROWA, 1, 0.5, "ocean");

        facade.addFish("test", fish);

        assertEquals(1, facade.getAllFishForSelectedAquarium("test").size());
    }

    @Test
    void shouldFilterFishByText() throws OceanariumException {
        facade.addFish("A1", new Fish("Nemo", "Clownfish", FishCondition.ZDROWA, 1, 0.2, "Australia"));
        facade.addFish("A1", new Fish("Dory", "Blue Tang", FishCondition.CHORA, 2, 0.3, "Ocean"));

        assertEquals(1, facade.filterFishByText("A1", "Nem").size());
    }

    @Test
    void shouldFilterFishByState() throws OceanariumException {
        facade.addFish("A1", new Fish("Nemo", "Clownfish", FishCondition.ZDROWA, 1, 0.2, "Australia"));
        facade.addFish("A1", new Fish("Dory", "Blue Tang", FishCondition.CHORA, 2, 0.3, "Ocean"));

        assertEquals(1, facade.filterFishByState("A1", FishCondition.CHORA).size());
    }

    @Test
    void shouldRemoveFishThroughFacade() throws OceanariumException {
        Fish fish = new Fish("Nemo", "Clownfish", FishCondition.ZDROWA, 1, 0.2, "Australia");
        facade.addFish("A1", fish);

        facade.removeFish("A1", fish);

        assertEquals(0, facade.getAllFishForSelectedAquarium("A1").size());
    }

    @Test
    void shouldRemoveAquariumThroughFacade() throws OceanariumException {
        facade.addAquarium("A2", 2);
        facade.removeAquarium("A2");

        assertThrows(AquariumNotFoundException.class, () -> facade.getFishInAquarium("A2"));
    }

    @Test
    void shouldReturnAllAquariums() {
        assertEquals(1, facade.getAquariums().size());
    }
}