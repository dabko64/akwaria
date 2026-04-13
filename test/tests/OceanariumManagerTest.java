package tests;

import exceptions.*;
import model.Aquarium;
import model.OceanariumManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OceanariumManagerTest {
    private OceanariumManager manager;

    @BeforeEach
    void setUp() {
        manager = new OceanariumManager();
    }

    @Test
    void shouldAddAquarium() throws Exception {
        OceanariumManager manager = new OceanariumManager();

        manager.addAquarium("test", 2);

        assertNotNull(manager.getAquarium("test"));
    }

    @Test
    void shouldThrowWhenAquariumAlreadyExists() throws OceanariumException {
        manager.addAquarium("A1", 3);
        assertThrows(AquariumAlreadyExistsException.class, () -> manager.addAquarium("A1", 2));
    }

    @Test
    void shouldRemoveAquarium() throws Exception {
        OceanariumManager manager = new OceanariumManager();
        manager.addAquarium("test", 2);

        manager.removeAquarium("test");

        assertThrows(AquariumNotFoundException.class, () -> manager.getAquarium("test"));
    }

    @Test
    void shouldFindEmptyAquariums() throws Exception {
        manager.addAquarium("A1", 2);
        manager.addAquarium("A2", 3);

        List<Aquarium> empty = manager.findEmpty();

        assertEquals(2, empty.size());
    }

    @Test
    void shouldGetAllAquariums() throws Exception {
        manager.addAquarium("A1", 2);
        manager.addAquarium("A2", 3);

        assertEquals(2, manager.getAllAquariums().size());
    }
}