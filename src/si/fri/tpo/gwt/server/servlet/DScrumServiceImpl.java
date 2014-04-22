package si.fri.tpo.gwt.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.server.impl.login.LoginServiceImpl;
import si.fri.tpo.gwt.server.impl.project.ProjectImpl;
import si.fri.tpo.gwt.server.impl.registration.ProjectRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.registration.SprintRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.registration.TeamRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.registration.UserRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.sprint.SprintImpl;
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
    public Pair<Boolean, Integer> updateTeam(TeamDTO teamDTO, String name, boolean withProject) {
            return TeamImpl.updateTeamWithProject(teamDTO);
    }

    @Override
    public Pair<Boolean, String> updateProject(ProjectDTO projectDTO, boolean changedProjectName, String originalProjectName) {
        return ProjectImpl.updateProject(projectDTO,changedProjectName, originalProjectName);
    }

    @Override
    public Pair<Boolean, String> updateSprint(SprintDTO sprintDTO) {
        return SprintImpl.updateSprint(sprintDTO);
    }

    @Override
    public Pair<Boolean, String> deleteSprint(SprintDTO sprintDTO) {
        return SprintImpl.deleteSprint(sprintDTO);
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
    public Pair<Boolean, String> saveSprint(SprintDTO sprintDTO) {
        return SprintImpl.saveNewSprint(sprintDTO);
    }

    @Override
    public List<ProjectDTO> findAllProjects() {
        return ProjectImpl.getAllProject();
    }

    @Override
    public ProjectDTO dummyProjectTrigger(ProjectDTO projectDTO) {
        return new ProjectDTO();
    }

    @Override
    public List<ProjectDTO> findUserProjects(UserDTO userDTO) {
        return ProjectImpl.getUserProject(userDTO);
    }

    @Override
    public ProjectDTO findProjectByName(String name) {
        return ProjectImpl.getProjectByName(name);
    }

    @Override
    public SprintDTO dummySprintTrigger(SprintDTO sprintDTO) {
        return new SprintDTO();
    }

    @Override
    public SprintPKDTO dummySprintPKTrigger(SprintPKDTO sprintPKDTO) {
        return new SprintPKDTO();
    }

    @Override
    public UserDTO findUserById(int userId) {
        return UserImpl.findUserById(userId);
    }

    @Override
    public WorkloadDTO dummyWorkloadTrigger(WorkloadDTO workloadDTO) {
        return new WorkloadDTO();
    }

    @Override
    public WorkloadPKDTO dummyWorkloadPKTrigger(WorkloadPKDTO workloadPKDTO) {
        return new WorkloadPKDTO();
    }

    @Override
    public DiscussionDTO dummyDiscussionTrigger(DiscussionDTO discussionDTO) {
        return new DiscussionDTO();
    }

    @Override
    public DiscussionPKDTO dummyDiscussionPKTrigger(DiscussionPKDTO discussionPKDTO) {
        return new DiscussionPKDTO();
    }

    @Override
    public AcceptanceTestDTO dummyAcceptanceTestTrigger(AcceptanceTestDTO acceptanceTestDTO) {
        return new AcceptanceTestDTO();
    }

    @Override
    public CommentDTO dummyCommentTrigger(CommentDTO commentDTO) {
        return new CommentDTO();
    }

    @Override
    public CommentPKDTO dummyCommentPKTrigger(CommentPKDTO commentPKDTO) {
        return new CommentPKDTO();
    }

    @Override
    public DailyScrumEntryDTO dummyDailyScrumEntryTrigger(DailyScrumEntryDTO dailyScrumEntryDTO) {
        return new DailyScrumEntryDTO();
    }

    @Override
    public DailyScrumEntryPKDTO dummyDailyScrumEntryPKTrigger(DailyScrumEntryPKDTO dailyScrumEntryPKDTO) {
        return new DailyScrumEntryPKDTO();
    }

    @Override
    public PriorityDTO dummyPriorityTrigger(PriorityDTO priorityDTO) {
        return new PriorityDTO();
    }

    @Override
    public WorkblockDTO dummyWorkblockTrigger(WorkblockDTO workblockDTO) {
        return new WorkblockDTO();
    }

    @Override
    public WorkblockPKDTO dummyWorkblockPKTrigger(WorkblockPKDTO workblockPKDTO) {
        return new WorkblockPKDTO();
    }

    @Override
    public TaskDTO dummyTaskTrigger(TaskDTO taskDTO) {
        return new TaskDTO();
    }

    @Override
    public TaskPKDTO dummyTaskPKTrigger(TaskPKDTO taskPKDTO) {
        return new TaskPKDTO();
    }

    @Override
    public UserStoryDTO dummyUserStoryTrigger(UserStoryDTO userStoryDTO) {
        return new UserStoryDTO();
    }

}
