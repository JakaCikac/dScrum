package si.fri.tpo.gwt.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.server.impl.login.LoginServiceImpl;
import si.fri.tpo.gwt.server.impl.project.ProjectImpl;
import si.fri.tpo.gwt.server.impl.registration.ProjectRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.registration.TeamRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.registration.UserRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.team.TeamImpl;
import si.fri.tpo.gwt.server.impl.user.UserImpl;

import java.util.List;

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

    @Override
    public UserDTO dummyUserTrigger(UserDTO userDTO) {
        return new UserDTO();
    }

    @Override
    public TeamDTO dummyTeamTrigger(TeamDTO teamDTO) {
        return new TeamDTO();
    }

    @Override
    public Pair<Boolean, String> validateUserData(String emailValue) {

        //if (!Validate.isEmail(emailValue)) {
            // getEmail().focus();
        //    return Pair.of(false, "Email not valid!");
        //}

        return Pair.of(true, "");
    }

    @Override
    public Pair<Boolean, String> saveUser(UserDTO userDTO, Boolean isNew) {
        return UserRegistrationServiceImpl.saveUser(userDTO, isNew);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return UserImpl.findUserByUsername(username);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return UserImpl.getAllUsers();
    }

    // with project if team gets stored with project, so you can validate
    // if the project exists
    @Override
    public Pair<Boolean, Integer> saveTeam(TeamDTO teamDTO, String projectName, boolean withProject) {
        if (withProject) {
            return TeamRegistrationServiceImpl.saveTeamWithProject(teamDTO, projectName);
        } else
            return TeamImpl.saveTeam(teamDTO);
    }

    @Override
    public Pair<Boolean, String> saveProject(ProjectDTO projectDTO) {
        return ProjectRegistrationServiceImpl.saveProject(projectDTO);
    }

    @Override
    public Pair<Boolean, String> updateUser(UserDTO userDTO, boolean b) {
        return UserImpl.updateUser(userDTO, b);
    }

    @Override
    public List<ProjectDTO> findAllProjects() {
        return ProjectImpl.getAllProject();
    }

    @Override
    public ProjectDTO dummyProjectTrigger(ProjectDTO projectDTO) {
        return new ProjectDTO();
    }

}
