package exceptions;

public class FishAlreadyExistsException extends OceanariumException {
    public FishAlreadyExistsException(String name) {
        super("ryba '" + name + "' już istnieje w akwarium");
    }
}