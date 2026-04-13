package exceptions;

public class AquariumNotFoundException extends OceanariumException {
    public AquariumNotFoundException(String name) {
        super("nie znaleziono akwarium: " + name);
    }
}