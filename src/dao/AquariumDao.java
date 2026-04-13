package dao;

import config.HibernateUtil;
import model.Aquarium;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AquariumDao {

    public void save(Aquarium aquarium) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(aquarium);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Aquarium aquarium) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.contains(aquarium) ? aquarium : session.merge(aquarium));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Aquarium findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select distinct a from Aquarium a left join fetch a.fishList where a.id = :id",
                            Aquarium.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public Aquarium findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Aquarium a where a.aquariumName = :name",
                            Aquarium.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public Aquarium findByNameWithFish(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select distinct a from Aquarium a left join fetch a.fishList where a.aquariumName = :name",
                            Aquarium.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public Aquarium update(Aquarium aquarium) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Aquarium merged = (Aquarium) session.merge(aquarium);
            transaction.commit();
            return merged;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public List<Aquarium> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select distinct a from Aquarium a left join fetch a.fishList",
                            Aquarium.class)
                    .list();
        }
    }

    public List<Aquarium> findEmpty() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select distinct a from Aquarium a left join fetch a.fishList where size(a.fishList) = 0",
                            Aquarium.class)
                    .list();
        }
    }



}