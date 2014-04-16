package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.SprintJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by Administrator on 15-Apr-14.
 */
public class SprintProxy extends SprintJpaController {

    public SprintProxy(EntityManagerFactory emf) {
        super(emf);
    }

    public SprintProxy() {}
}
