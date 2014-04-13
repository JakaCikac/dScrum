package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.TeamJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by nanorax on 13/04/14.
 */
public class TeamProxy extends TeamJpaController {

    public TeamProxy(EntityManagerFactory emf) {
        super(emf);
    }

    public TeamProxy() {}

}
