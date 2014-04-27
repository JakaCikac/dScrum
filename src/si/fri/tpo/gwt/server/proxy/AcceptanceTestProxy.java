package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.AcceptanceTestJpaController;
import si.fri.tpo.gwt.server.jpa.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by anze on 27. 04. 14.
 */
public class AcceptanceTestProxy extends AcceptanceTestJpaController{

    public AcceptanceTestProxy(EntityManagerFactory emf) {
        super(emf);
    }
}
