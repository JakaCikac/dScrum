package si.fri.tpo.gwt.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.server.impl.login.LoginServiceImpl;

/**
 * Created by nanorax on 04/04/14.
 */
public class DScrumServiceImpl extends RemoteServiceServlet implements DScrumService {

    @Override
    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash) {
        return LoginServiceImpl.performUserLogin(username, passwordHash);
    }

    @Override
    public Character dummyCharacterTrigger(Character b) {
        return new Character('A');
    }

    //@Override
    //public SprintDTO dummySprintTrigger(SprintDTO sprintDTO) {
    //    return new SprintDTO();
    //}

    @Override
    public UserDTO dummyUserTrigger(UserDTO userDTO) {
        return new UserDTO();
    }

    //@Override
    //public DiscussionDTO dummyDiscussionTrigger(DiscussionDTO discussionDTO) {
    //    return new DiscussionDTO();
    //}

}
