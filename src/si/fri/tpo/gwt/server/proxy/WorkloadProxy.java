package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.WorkloadJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by Administrator on 5/21/2014.
 */
public class WorkloadProxy extends WorkloadJpaController{

    public WorkloadProxy(EntityManagerFactory emf) {
        super(emf);
    }
}
