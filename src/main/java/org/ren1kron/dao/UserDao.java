package org.ren1kron.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.ren1kron.models.User;

@ApplicationScoped
public class UserDao {
    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public User findByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void saveUser(User user) {
        em.persist(user);
    }
}
