package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.CommentJpaController;

import javax.persistence.EntityManagerFactory;

/**
 * Created by nanorax on 20/05/14.
 */
public class CommentProxy extends CommentJpaController {
    public CommentProxy(EntityManagerFactory emf) {
        super(emf);
    }
}
