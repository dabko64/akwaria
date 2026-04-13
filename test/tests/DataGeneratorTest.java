package tests;

import data.DataGenerator;
import model.OceanariumManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorTest {

    @Test
    void shouldGenerateData() throws Exception {
        DataGenerator generator = DataGenerator.getInstance();

        assertNotNull(generator);

        OceanariumManager manager = generator.createSampleData();
        assertNotNull(manager);

        assertNotNull(manager.getAquarium("tropikalne"));
        assertNotNull(manager.getAquarium("morskie"));
        assertNotNull(manager.getAquarium("kwarantanna"));
    }
}