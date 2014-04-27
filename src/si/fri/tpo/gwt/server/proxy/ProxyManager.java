package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.jpa.UserStory;

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
    private static SprintProxy sprintProxy = null;
    private static AcceptanceTestProxy acceptanceTestProxy = null;
    private static UserStoryProxy userStoryProxy = null;

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

    public static SprintProxy getSprintProxy() {
        if (sprintProxy == null) {
            sprintProxy = new SprintProxy(getEmf());
        }
        return sprintProxy;
    }

    public static AcceptanceTestProxy getAcceptanceTestProxy() {
        if (acceptanceTestProxy == null) {
            acceptanceTestProxy = new AcceptanceTestProxy(getEmf());
        }
        return acceptanceTestProxy;
    }

    public static UserStoryProxy getUserStoryProxy() {
        if (userStoryProxy == null) {
            userStoryProxy = new UserStoryProxy(getEmf());
        }
        return userStoryProxy;
    }
}
