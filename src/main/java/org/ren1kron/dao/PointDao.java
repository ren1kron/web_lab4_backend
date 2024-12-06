package org.ren1kron.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.ren1kron.models.Point;

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

    public List<Point> getPoints() {
        return entityManager.createQuery("SELECT p FROM Point p", Point.class).getResultList();
    }

    @Transactional
    public int clear() {
        int deletedCount = entityManager.createQuery("DELETE FROM Point p").executeUpdate();
        return deletedCount;
    }
}
