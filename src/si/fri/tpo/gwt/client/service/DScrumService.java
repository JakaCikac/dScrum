package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;

import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */

@RemoteServiceRelativePath("dscrum")
public interface DScrumService extends RemoteService {

    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash);

    public Character dummyCharacterTrigger(Character b);

    public UserDTO dummyUserTrigger(UserDTO userDTO);

    public Pair<Boolean , String> validateUserData(String emailValue);

    public Pair<Boolean, String>  saveUser(UserDTO userDTO, Boolean isNew);

    public UserDTO findUserByUsername(String username);

    public List<UserDTO> findAllUsers();

    public Pair<Boolean, Integer> saveTeam(TeamDTO teamDTO, String projectName, boolean withProject);

    public Pair<Boolean, String> saveProject(ProjectDTO projectDTO);

    public Pair<Boolean, String> updateUser(UserDTO userDTO, boolean b);

    public Pair<Boolean, String> saveSprint(SprintDTO sprintDTO);
}
