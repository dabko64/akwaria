package model;

import dao.AquariumDao;
import exceptions.AquariumAlreadyExistsException;
import exceptions.AquariumNotFoundException;
import exceptions.OceanariumException;

import java.util.Comparator;
import java.util.List;

public class OceanariumManager {

    private final AquariumDao aquariumDao;

    public OceanariumManager() {
        this.aquariumDao = new AquariumDao();
    }

    public void addAquarium(String name, int capacity) throws OceanariumException {
        Aquarium existingAquarium = aquariumDao.findByName(name);

        if (existingAquarium != null) {
            throw new AquariumAlreadyExistsException(name);
        }

        Aquarium newAquarium = new Aquarium(name, capacity);
        aquariumDao.save(newAquarium);
    }

    public void removeAquarium(String name) throws AquariumNotFoundException {
        Aquarium aquarium = aquariumDao.findByName(name);

        if (aquarium == null) {
            throw new AquariumNotFoundException(name);
        }

        aquariumDao.delete(aquarium);
    }

    public Aquarium getAquarium(String name) throws AquariumNotFoundException {
        Aquarium aquarium = aquariumDao.findByNameWithFish(name);

        if (aquarium == null) {
            throw new AquariumNotFoundException(name);
        }

        return aquarium;
    }

    public List<Aquarium> getAllAquariums() {
        return aquariumDao.findAll();
    }

    public List<Aquarium> findEmpty() {
        return aquariumDao.findEmpty();
    }

    public void sortAquariumsByCurrentLoad(List<Aquarium> aquariums) {
        aquariums.sort(Comparator.comparingInt(Aquarium::getCurrentLoad));
    }
}