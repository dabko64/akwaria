package exceptions;

public class FishNotFoundException extends OceanariumException {
    public FishNotFoundException(String name) {
        super("nie znaleziono ryby: " + name);
    }
}