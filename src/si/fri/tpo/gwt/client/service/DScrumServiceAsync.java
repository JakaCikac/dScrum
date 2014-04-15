package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;

import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */
public interface DScrumServiceAsync {

    void performUserLogin(String username, String passwordHash, AsyncCallback<Pair<UserDTO, String>> async);

    void dummyCharacterTrigger(Character b, AsyncCallback<Character> callback);

    void dummyUserTrigger(UserDTO userDTO, AsyncCallback<UserDTO> callback3);

    void validateUserData(String emailValue, AsyncCallback<Pair<Boolean, String>> validationCallback);

    void saveUser(UserDTO userDTO, Boolean value, AsyncCallback<Pair<Boolean, String>> saveUser);

    void findUserByUsername(String username, AsyncCallback<UserDTO> callback);

    void findAllUsers(AsyncCallback<List<UserDTO>> callback);

    void saveTeam(TeamDTO teamDTO, String projectName, boolean withProject, AsyncCallback<Pair<Boolean, Integer>> saveTeam);

    void saveProject(ProjectDTO projectDTO, AsyncCallback<Pair<Boolean,String>> saveProject);

    void updateUser(UserDTO userDTO, boolean b, AsyncCallback<Pair<Boolean, String>> updateUser);

    void findAllProjects(AsyncCallback<List<ProjectDTO>> callback);
}

