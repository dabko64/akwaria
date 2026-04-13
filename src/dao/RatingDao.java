package dao;

import config.HibernateUtil;
import model.Rating;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RatingDao {

    public void save(Rating rating) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(rating);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Rating rating) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.contains(rating) ? rating : session.merge(rating));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Rating> findByAquariumName(String aquariumName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Rating r where r.aquarium.aquariumName = :name",
                            Rating.class
                    )
                    .setParameter("name", aquariumName)
                    .list();
        }
    }

    public Long countByAquariumName(String aquariumName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select count(r) from Rating r where r.aquarium.aquariumName = :name",
                            Long.class
                    )
                    .setParameter("name", aquariumName)
                    .uniqueResult();
        }
    }

    public Double averageByAquariumName(String aquariumName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select avg(r.ratingValue) from Rating r where r.aquarium.aquariumName = :name",
                            Double.class
                    )
                    .setParameter("name", aquariumName)
                    .uniqueResult();
        }
    }
}