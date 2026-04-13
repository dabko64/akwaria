package service;

import model.Aquarium;

import java.io.*;
import java.util.List;

public class BinaryFileService {

    public void saveAquariumsToBinary(List<Aquarium> aquariums, String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(aquariums);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Aquarium> loadAquariumsFromBinary(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Aquarium>) in.readObject();
        }
    }
}