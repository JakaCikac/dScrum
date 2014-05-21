package si.fri.tpo.gwt.server.proxy;

import si.fri.tpo.gwt.server.controllers.DiscussionJpaController;
import si.fri.tpo.gwt.server.controllers.PriorityJpaController;
import si.fri.tpo.gwt.server.jpa.Discussion;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by nanorax on 20/05/14.
 */
public class DiscussionProxy extends DiscussionJpaController {

    public DiscussionProxy(EntityManagerFactory emf) {
        super(emf);
    }

    public Discussion findDiscussion(int discussionId) {
        return findDiscussion(discussionId);
    }

    public List<Discussion> findDiscussionList() {
        return findDiscussionEntities();
    }
}
