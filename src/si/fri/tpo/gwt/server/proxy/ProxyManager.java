package si.fri.tpo.gwt.server.proxy;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by nanorax on 05/04/14.
 */
public class ProxyManager {



    private static EntityManagerFactory emf = null;
    private static UserProxy userProxy = null;
    private static TeamProxy teamProxy = null;
    private static ProjectProxy projectProxy = null;

    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("DScrum");
        }

        return emf;
    }

    public static UserProxy getUserProxy() {
        if (userProxy == null) {
            userProxy = new UserProxy(getEmf());
        }

        return userProxy;
    }

    public static ProjectProxy getProjectProxy() {
        if (projectProxy == null) {
            projectProxy = new ProjectProxy(getEmf());
        }

        return projectProxy;
    }

    public static TeamProxy getTeamProxy() {
        if (teamProxy == null) {
            teamProxy = new TeamProxy(getEmf());
        }
        return teamProxy;
    }
}
