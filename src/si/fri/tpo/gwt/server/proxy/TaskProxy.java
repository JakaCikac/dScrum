package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.TaskJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by anze on 3. 05. 14.
 */
public class TaskProxy extends TaskJpaController{

    public TaskProxy(EntityManagerFactory emf) {
        super(emf);
    }
}
