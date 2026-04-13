package dao;

import config.HibernateUtil;
import model.Fish;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FishDao {

    public void save(Fish fish) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(fish);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Fish update(Fish fish) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Fish merged = (Fish) session.merge(fish);
            transaction.commit();
            return merged;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Fish fish) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.contains(fish) ? fish : session.merge(fish));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Fish findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Fish.class, id);
        }
    }



    public List<Fish> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Fish", Fish.class).list();
        }
    }

    public List<Fish> findByAquariumName(String aquariumName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Fish f where f.aquarium.aquariumName = :name",
                            Fish.class
                    )
                    .setParameter("name", aquariumName)
                    .list();
        }
    }

    public List<Fish> findByConditionInAquarium(String aquariumName, String condition) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Fish f where f.aquarium.aquariumName = :name and f.condition = :condition",
                            Fish.class
                    )
                    .setParameter("name", aquariumName)
                    .setParameter("condition", Enum.valueOf(model.FishCondition.class, condition))
                    .list();
        }
    }
}
