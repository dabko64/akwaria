package exceptions;

public class AquariumAlreadyExistsException extends OceanariumException {
    public AquariumAlreadyExistsException(String name) {
        super("akwarium o nazwie '" + name + "' już istnieje");
    }
}