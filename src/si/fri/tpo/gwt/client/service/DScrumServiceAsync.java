package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;

/**
 * Created by nanorax on 04/04/14.
 */
public interface DScrumServiceAsync {

    void performUserLogin(String username, String passwordHash, AsyncCallback<Pair<UserDTO, String>> async);

    void dummyCharacterTrigger(Character b, AsyncCallback<Character> callback);

    //void dummySprintTrigger(SprintDTO sprintDTO, AsyncCallback<SprintDTO> callback2);

    void dummyUserTrigger(UserDTO userDTO, AsyncCallback<UserDTO> callback3);

    //void dummyDiscussionTrigger(DiscussionDTO discussionDTO, AsyncCallback<DiscussionDTO> callback4);
}

