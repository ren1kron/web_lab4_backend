package org.ren1kron.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.ren1kron.models.Point;
import org.ren1kron.models.User;

import java.util.List;

@NoArgsConstructor

@ApplicationScoped
public class PointDao {

    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;

    @Transactional
    public void addPoint(Point point) {
        entityManager.persist(point);
    }

    public List<Point> getPointsByUser(User user) {
        return entityManager.createQuery("SELECT p FROM Point p where p.user = :user", Point.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Transactional
    public int clearUserPoints(User user) {
        return entityManager.createQuery("DELETE FROM Point p where p.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }
}
