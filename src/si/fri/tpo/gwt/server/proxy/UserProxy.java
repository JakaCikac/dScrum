package si.fri.tpo.gwt.server.proxy;


import si.fri.tpo.gwt.server.controllers.UserJpaController;
import si.fri.tpo.gwt.server.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by nanorax on 05/04/14.
 */
public class UserProxy extends UserJpaController {


    //private static final long serialVersionUID = -2306268549496326663L;

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
                //System.out.println("User proxy findUserByUsername error: " + e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }

    public List<User> getUsersList() {
        return findUserEntities();
    }

    public User findUserById(Integer userId) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            User u;
            try {
                u = em.createNamedQuery("User.findByUserId", User.class).setParameter("userId", userId).getSingleResult();
                return u;
            } catch (Exception e) {
                //System.out.println("User proxy findUserById error: " + e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }
}
