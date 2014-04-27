package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.UserStoryJpaController;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.UserStory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by anze on 27. 04. 14.
 */
public class UserStoryProxy extends UserStoryJpaController{

    public UserStoryProxy(EntityManagerFactory emf){
        super(emf);
    }

    public List<UserStory> getUserStoryList(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<UserStory> p;
            try {
                Query query =  em.createNativeQuery("SELECT * FROM t13_2014.user_story WHERE project_project_id IN (SELECT project_project_id FROM t13_2014.project WHERE project_id = ?)", UserStory.class);
                query.setParameter(1, id);
                p = query.getResultList();
                return p;
            } catch (Exception e) {
                System.out.println("UserStory proxy getUserStoryList error: " + e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }
}
