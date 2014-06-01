package si.fri.tpo.gwt.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.server.impl.acceptanceTest.AcceptanceTestImpl;
import si.fri.tpo.gwt.server.impl.discussion.DiscussionImpl;
import si.fri.tpo.gwt.server.impl.login.LoginServiceImpl;
import si.fri.tpo.gwt.server.impl.project.ProjectImpl;
import si.fri.tpo.gwt.server.impl.registration.*;
import si.fri.tpo.gwt.server.impl.sprint.SprintImpl;
import si.fri.tpo.gwt.server.impl.task.TaskImpl;
import si.fri.tpo.gwt.server.impl.team.TeamImpl;
import si.fri.tpo.gwt.server.impl.user.UserImpl;
import si.fri.tpo.gwt.server.impl.userStory.UserStoryImpl;
import si.fri.tpo.gwt.server.impl.workblock.WorkblockImpl;
import si.fri.tpo.gwt.server.impl.workload.WorkloadImpl;

import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */
public class DScrumServiceImpl extends RemoteServiceServlet implements DScrumService {

    // DUMMY //
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
    public ProjectDTO dummyProjectTrigger(ProjectDTO projectDTO) {
        return new ProjectDTO();
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
    // DUMMY //

    // PROJECT //
    @Override
    public Pair<Boolean, String> saveProject(ProjectDTO projectDTO) {
        return ProjectRegistrationServiceImpl.saveProject(projectDTO);
    }

    @Override
    public Pair<Boolean, String> updateProject(ProjectDTO projectDTO, boolean changedProjectName, String originalProjectName) {
        return ProjectImpl.updateProject(projectDTO,changedProjectName, originalProjectName);
    }

    @Override
    public List<ProjectDTO> findAllProjects() {
        return ProjectImpl.getAllProject();
    }

    @Override
    public List<ProjectDTO> findUserProjects(UserDTO userDTO) {
        return ProjectImpl.getUserProject(userDTO);
    }

    @Override
    public ProjectDTO findProjectByName(String name) {
        return ProjectImpl.getProjectByName(name);
    }
    // PROJECT //

    // USER //
    @Override
    public Pair<Boolean, String> saveUser(UserDTO userDTO, Boolean isNew) {
        return UserRegistrationServiceImpl.saveUser(userDTO, isNew);
    }

    @Override
    public Pair<Boolean, String> updateUser(UserDTO userDTO, boolean b) {
        return UserImpl.updateUser(userDTO, b);
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
    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash) {
        return LoginServiceImpl.performUserLogin(username, passwordHash);
    }

    @Override
    public UserDTO findUserById(int userId) {
        return UserImpl.findUserById(userId);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return UserImpl.findUserByUsername(username);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return UserImpl.getAllUsers();
    }
    // USER //

    // TEAM //
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
    // TEAM //

    // SPRINT //
    @Override
    public Pair<Boolean, Integer> saveSprint(SprintDTO sprintDTO) {
        return SprintImpl.saveNewSprint(sprintDTO);
    }

    @Override
    public Pair<Boolean, String> updateSprint(SprintDTO sprintDTO) {
        return SprintImpl.updateSprint(sprintDTO);
    }

    @Override
    public Pair<Boolean, String> deleteSprint(SprintDTO sprintDTO) {
        return SprintImpl.deleteSprint(sprintDTO);
    }
    // SPRINT //

    // ACCEPTANCE TEST //
    @Override
    public Pair<Boolean, List<Integer>> saveAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList) {
        return AcceptanceTestImpl.saveAcceptanceTestList(acceptanceTestDTOList);
    }

    @Override
    public Pair<Boolean, String> updateAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList) {
        return AcceptanceTestImpl.updateAcceptanceTestList(acceptanceTestDTOList);
    }

    @Override
    public Pair<Boolean, String> deleteAcceptanceTest(AcceptanceTestDTO acceptanceTestDTO) {
        return AcceptanceTestImpl.deleteAcceptanceTest(acceptanceTestDTO);
    }
    // ACCEPTANCE TEST //

    // USER STORY //
    @Override
    public Pair<Boolean, String> saveUserStory(UserStoryDTO userStoryDTO, ProjectDTO projectDTO) {
        return UserStoryServiceImpl.saveUserStory(userStoryDTO, projectDTO);
    }

    @Override
    public List<UserStoryDTO> findAllStoriesByProject(ProjectDTO projectDTO) {
        return UserStoryImpl.getAllStoryOfProject(projectDTO);
    }

    @Override
    public Pair<Boolean, String> updateUserStory(UserStoryDTO userStoryDTO) {
        return UserStoryImpl.updateUserStory(userStoryDTO);
    }

    @Override
    public Pair<Boolean, String> deleteUserStory(UserStoryDTO userStoryDTO) {
        return UserStoryImpl.deleteUserStory(userStoryDTO);
    }
    // USER STORY //

    // TASK //
    @Override
    public Pair<Boolean, String> saveTask(TaskDTO taskDTO, UserStoryDTO userStoryDTO) {
        return TaskImpl.saveTask(taskDTO, userStoryDTO);
    }

    @Override
    public Pair<Boolean, String> updateTask(TaskDTO p) {
        return TaskImpl.updateTask(p);
    }

    @Override
    public Pair<Boolean, String> deleteTask(TaskDTO taskDTO) {
        return TaskImpl.deleteTask(taskDTO);
    }
    // TASK //

    // USER_STORY_COMMENT //
    @Override
    public Pair<Boolean, String> saveComment(UserStoryDTO userStoryDTO) {
        return UserStoryImpl.saveComment(userStoryDTO);
    }

    @Override
    public Pair<Boolean, String> updateComment(UserStoryDTO userStoryDTO) {
        return UserStoryImpl.saveComment(userStoryDTO);
    }
    // USER_STORY_COMMENT //

    // DISCUSSION //
    @Override
    public List<DiscussionDTO> findAllDiscussionsByProject(ProjectDTO projectDTO) {
        return DiscussionImpl.getAllDiscussionOfProject(projectDTO);
    }

    @Override
    public Pair<Boolean, String> saveDiscussion(DiscussionDTO discussionDTO, ProjectDTO projectDTO) {
        return DiscussionImpl.saveDiscussion(discussionDTO, projectDTO);
    }

    @Override
    public Pair<Boolean, String> updateDiscussion(DiscussionDTO discussionDTO) {
        return DiscussionImpl.updateDiscussion(discussionDTO);
    }

    @Override
    public Pair<Boolean, Integer> saveDiscussionComment(CommentDTO commentDTO) {
        return DiscussionImpl.saveDiscussionComment(commentDTO);
    }
    // DISCUSION //

    // WORKLOAD //
    @Override
    public Pair<Boolean, String> updateWorkload(WorkloadDTO workloadDTO) {
        return WorkloadImpl.updateWorkload(workloadDTO);
    }

    @Override
    public Pair<Boolean, List<Integer>> saveWorkload(List<WorkloadDTO> workloadDTO) {
        return WorkloadImpl.saveWorkload(workloadDTO);
    }
    // WORKLOAD //

    //WORKBLOCK //
    @Override
    public Pair<Boolean, Integer> saveWorkblock(WorkblockDTO workblockDTO) {
        return WorkblockImpl.saveWorkblock(workblockDTO);
    }
    // WORKLOAD //

}
