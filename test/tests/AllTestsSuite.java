package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AquariumTest.class,
        OceanariumManagerTest.class,
        OceanariumFacadeTest.class,
        DataGeneratorTest.class
})
public class AllTestsSuite {
}