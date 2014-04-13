package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.ProjectJpaController;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
                System.out.println(e.getMessage());
                return null;
            }
        } finally {
            em.close();
        }
    }
}
