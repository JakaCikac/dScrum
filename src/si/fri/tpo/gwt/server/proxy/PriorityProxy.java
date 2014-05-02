package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.PriorityJpaController;
import si.fri.tpo.gwt.server.jpa.Priority;

import javax.persistence.EntityManagerFactory;

/**
 * Created by anze on 2. 05. 14.
 */
public class PriorityProxy extends PriorityJpaController{

    public PriorityProxy(EntityManagerFactory emf) {super(emf);}
}
