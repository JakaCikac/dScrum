package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;

/**
 * Created by nanorax on 04/04/14.
 */

@RemoteServiceRelativePath("dscrum")
public interface DScrumService extends RemoteService {

    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash);

    public Character dummyCharacterTrigger(Character b);

    //public SprintDTO dummySprintTrigger(SprintDTO sprintDTO);

    public UserDTO dummyUserTrigger(UserDTO userDTO);

    public Pair<Boolean , String> validateUserData(String emailValue);

    public Pair<Boolean, String>  saveUser(UserDTO userDTO, Boolean isNew);

    //public DiscussionDTO dummyDiscussionTrigger(DiscussionDTO discussionDTO);

}
