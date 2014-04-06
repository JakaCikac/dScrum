package si.fri.tpo.gwt.server.proxy;


import si.fri.tpo.gwt.server.controllers.UserJpaController;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.jpa.User;

import java.util.List;

/**
 * Created by nanorax on 05/04/14.
 */
public class UserProxy extends UserJpaController {

    public UserProxy(EntityManagerFactory emf) {
        super(emf);

    }

    public UserProxy() {}

    public User findUserByUsername(String username) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User u;
            try {
                u = em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
                return u;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }

    public List<User> getUsersList() {
        return findUserEntities();
    }
}
