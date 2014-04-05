package si.fri.tpo.proxy;

import si.fri.tpo.controllers.UserJpaController;
import si.fri.tpo.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by nanorax on 05/04/14.
 */
public class UserProxy extends UserJpaController {

    public UserProxy(EntityManagerFactory emf) {
        super(emf);

    }

    public User findUserByUsername(String username) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User u;
            try {
                // TODO: named query for findUserByUsername
                u = em.createNamedQuery("User.findUserByUsername", User.class).setParameter("username", username).getSingleResult();
                return u;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }

}
