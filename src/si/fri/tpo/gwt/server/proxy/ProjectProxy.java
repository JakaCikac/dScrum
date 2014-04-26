package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.ProjectJpaController;
import si.fri.tpo.gwt.server.jpa.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class ProjectProxy extends ProjectJpaController {

    public ProjectProxy(EntityManagerFactory emf) {
        super(emf);
    }

    public ProjectProxy() {}

    public List<Project> getProjectsList() {
        return findProjectEntities();
    }

    public Project findProjectByName(String name) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Project p;
            try {
                p = em.createNamedQuery("Project.findByName", Project.class).setParameter("name", name).getSingleResult();
                return p;
            } catch (Exception e) {
                System.out.println("Project proxy findProjectByName error: " + e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }

    public List<Project> getUserProjectsList(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            List<Project> p;
            try {
                Query query =  em.createNativeQuery("SELECT * FROM t13_2014.project WHERE team_team_id IN (SELECT team_team_id FROM t13_2014.team_has_user WHERE user_user_id = ?)", Project.class);
                query.setParameter(1, id);
                p = query.getResultList();
                return p;
            } catch (Exception e) {
                System.out.println("Project proxy getUserProjectsList error: " + e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }
}
