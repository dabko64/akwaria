package exceptions;

public class AquariumCapacityExceededException extends OceanariumException {
    public AquariumCapacityExceededException(String aquariumName) {
        super("akwarium '" + aquariumName + "' osiagnelo" +
                " max pojemnosc");
    }
}