package service;

import config.HibernateUtil;
import dto.AquariumRatingStatsDto;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import model.Aquarium;
import model.Rating;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class StatisticsService {

    public List<AquariumRatingStatsDto> getAquariumRatingStats() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Tuple> cq = cb.createTupleQuery();

            Root<Aquarium> aquariumRoot = cq.from(Aquarium.class);
            Join<Aquarium, Rating> ratingJoin = aquariumRoot.join("ratings", JoinType.LEFT);

            cq.multiselect(
                    aquariumRoot.get("aquariumName").alias("aquariumName"),
                    cb.count(ratingJoin.get("id")).alias("ratingsCount"),
                    cb.coalesce(cb.avg(ratingJoin.get("ratingValue")), 0.0).alias("averageRating")
            );

            cq.groupBy(aquariumRoot.get("aquariumName"));
            cq.orderBy(cb.asc(aquariumRoot.get("aquariumName")));

            List<Tuple> result = session.createQuery(cq).getResultList();
            List<AquariumRatingStatsDto> stats = new ArrayList<>();

            for (Tuple tuple : result) {
                String aquariumName = tuple.get("aquariumName", String.class);
                Long ratingsCount = tuple.get("ratingsCount", Long.class);
                Double averageRating = tuple.get("averageRating", Double.class);

                stats.add(new AquariumRatingStatsDto(
                        aquariumName,
                        ratingsCount != null ? ratingsCount : 0L,
                        averageRating != null ? averageRating : 0.0
                ));
            }

            return stats;
        }
    }
}