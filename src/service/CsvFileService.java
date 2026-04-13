package service;

import config.HibernateUtil;
import dao.AquariumDao;
import dao.FishDao;
import exceptions.InvalidDataException;
import model.Aquarium;
import model.Fish;
import model.FishCondition;
import org.hibernate.Session;

import annotations.CsvColumn;

import java.lang.reflect.Field;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileService {

    private final AquariumDao aquariumDao;
    private final FishDao fishDao;

    public CsvFileService() {
        this.aquariumDao = new AquariumDao();
        this.fishDao = new FishDao();
    }

    public void exportAquariumToCsv(String aquariumName, String filePath) throws IOException {
        Aquarium aquarium = getAquariumWithFishUsingHql(aquariumName);

        if (aquarium == null) {
            throw new FileNotFoundException("Nie znaleziono akwarium: " + aquariumName);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("aquariumName,maxCapacity,fishName,species,condition,age,weight,origin");
            writer.newLine();

            if (aquarium.getFishList().isEmpty()) {
                writer.write(escape(aquarium.getAquariumName()) + "," + aquarium.getMaxCapacity() + ",,,,,,");
                writer.newLine();
                return;
            }

            for (Fish fish : aquarium.getFishList()) {
                writer.write(
                        escape(aquarium.getAquariumName()) + "," +
                                aquarium.getMaxCapacity() + "," +
                                escape(fish.getName()) + "," +
                                escape(fish.getSpecies()) + "," +
                                fish.getCondition() + "," +
                                fish.getAge() + "," +
                                fish.getWeight() + "," +
                                escape(fish.getOrigin())
                );
                writer.newLine();
            }
        }
    }

    public void importAquariumFromCsv(String filePath) throws IOException, InvalidDataException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            if (header == null) {
                throw new IOException("Plik CSV jest pusty.");
            }

            List<String[]> rows = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    rows.add(parseCsvLine(line));
                }
            }

            if (rows.isEmpty()) {
                throw new IOException("Brak danych do importu.");
            }

            String aquariumName = rows.get(0)[0];
            int maxCapacity = Integer.parseInt(rows.get(0)[1]);

            Aquarium existing = aquariumDao.findByName(aquariumName);
            if (existing != null) {
                throw new InvalidDataException("Akwarium o nazwie '" + aquariumName + "' już istnieje w bazie.");
            }

            Aquarium aquarium = new Aquarium(aquariumName, maxCapacity);
            aquariumDao.save(aquarium);

            Aquarium savedAquarium = aquariumDao.findByNameWithFish(aquariumName);

            for (String[] row : rows) {
                if (row.length < 8) {
                    continue;
                }

                String fishName = row[2];
                String species = row[3];
                String conditionText = row[4];
                String ageText = row[5];
                String weightText = row[6];
                String origin = row[7];

                if (fishName == null || fishName.isBlank()) {
                    continue;
                }

                Fish fish = new Fish(
                        fishName,
                        species,
                        FishCondition.valueOf(conditionText),
                        Integer.parseInt(ageText),
                        Double.parseDouble(weightText),
                        origin
                );
                fish.setAquarium(savedAquarium);
                fishDao.save(fish);
            }
        }
    }

    private Aquarium getAquariumWithFishUsingHql(String aquariumName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select distinct a from Aquarium a left join fetch a.fishList where a.aquariumName = :name",
                            Aquarium.class
                    )
                    .setParameter("name", aquariumName)
                    .uniqueResult();
        }
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    private String[] parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        values.add(current.toString());
        return values.toArray(new String[0]);
    }
    public List<String> getAnnotatedFishFields() {
        List<String> result = new ArrayList<>();

        Field[] fields = Fish.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CsvColumn.class)) {
                CsvColumn annotation = field.getAnnotation(CsvColumn.class);
                result.add(annotation.name());
            }
        }

        return result;
    }

}