package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.WorkblockJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by Administrator on 5/22/2014.
 */
public class WorkblockProxy extends WorkblockJpaController {

    public WorkblockProxy(EntityManagerFactory emf) {
        super(emf);
    }
}
