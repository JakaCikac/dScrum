package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;

import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */

@RemoteServiceRelativePath("dscrum")
public interface DScrumService extends RemoteService {

    // DUMMY //
    public Character dummyCharacterTrigger(Character b);

    public UserDTO dummyUserTrigger(UserDTO userDTO);

    public TeamDTO dummyTeamTrigger(TeamDTO teamDTO);

    public ProjectDTO dummyProjectTrigger(ProjectDTO projectDTO);

    public SprintDTO dummySprintTrigger(SprintDTO sprintDTO);

    public SprintPKDTO dummySprintPKTrigger(SprintPKDTO sprintPKDTO);

    public WorkloadDTO dummyWorkloadTrigger(WorkloadDTO workloadDTO);

    public WorkloadPKDTO dummyWorkloadPKTrigger(WorkloadPKDTO workloadPKDTO);

    public DiscussionDTO dummyDiscussionTrigger(DiscussionDTO discussionDTO);

    public DiscussionPKDTO dummyDiscussionPKTrigger(DiscussionPKDTO discussionPKDTO);

    public AcceptanceTestDTO dummyAcceptanceTestTrigger(AcceptanceTestDTO acceptanceTestDTO);

    public CommentDTO dummyCommentTrigger(CommentDTO commentDTO);

    public CommentPKDTO dummyCommentPKTrigger(CommentPKDTO commentPKDTO);

    public DailyScrumEntryDTO dummyDailyScrumEntryTrigger(DailyScrumEntryDTO dailyScrumEntryDTO);

    public DailyScrumEntryPKDTO dummyDailyScrumEntryPKTrigger(DailyScrumEntryPKDTO dailyScrumEntryPKDTO);

    public PriorityDTO dummyPriorityTrigger(PriorityDTO priorityDTO);

    public WorkblockDTO dummyWorkblockTrigger(WorkblockDTO workblockDTO);

    public WorkblockPKDTO dummyWorkblockPKTrigger(WorkblockPKDTO workblockPKDTO);

    public TaskDTO dummyTaskTrigger(TaskDTO taskDTO);

    public TaskPKDTO dummyTaskPKTrigger(TaskPKDTO taskPKDTO);

    public UserStoryDTO dummyUserStoryTrigger(UserStoryDTO userStoryDTO);
    // DUMMY //

    // PROJECT //
    public Pair<Boolean, String> saveProject(ProjectDTO projectDTO);

    public Pair<Boolean, String> updateProject(ProjectDTO projectDTO, boolean changedProjectName, String originalProjectName);

    public ProjectDTO findProjectByName(String name);

    public List<ProjectDTO> findAllProjects();

    public List<ProjectDTO> findUserProjects(UserDTO userDTO);
    // PROJECT //

    // USER //
    public Pair<Boolean, String>  saveUser(UserDTO userDTO, Boolean isNew);

    public Pair<Boolean, String> updateUser(UserDTO userDTO, boolean b);

    public Pair<Boolean , String> validateUserData(String emailValue);

    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash);

    public List<UserDTO> findAllUsers();

    public UserDTO findUserByUsername(String username);

    public UserDTO findUserById(int userId);
    // USER //

    // TEAM //
    public Pair<Boolean, Integer> saveTeam(TeamDTO teamDTO, String projectName, boolean withProject);

    public Pair<Boolean, Integer> updateTeam(TeamDTO teamDTO, String name, boolean withProject);
    // TEAM //

    // SPRINT //
    public Pair<Boolean, Integer> saveSprint(SprintDTO sprintDTO);

    public Pair<Boolean, String> updateSprint(SprintDTO sprintDTO);

    public Pair<Boolean, String> deleteSprint(SprintDTO sprintDTO);
    // SPRINT //

    // ACCEPTANCE TEST //
    public Pair<Boolean, List<Integer>> saveAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList);

    public Pair<Boolean, String> updateAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList);

    public Pair<Boolean, String> deleteAcceptanceTest(AcceptanceTestDTO selectedItem);
    // ACCEPTANCE TEST //

    // USER STORY //
    public Pair<Boolean, String> saveUserStory(UserStoryDTO userStoryDTO, ProjectDTO projectDTO);

    public List<UserStoryDTO> findAllStoriesByProject(ProjectDTO projectDTO);

    public Pair<Boolean, String> updateUserStory(UserStoryDTO userStoryDTO);

    public  Pair<Boolean, String> deleteUserStory(UserStoryDTO userStoryDTO);
    // USER STORY //

    // TASK //
    public Pair<Boolean, String> saveTask(TaskDTO taskDTO, UserStoryDTO userStoryDTO);

    // TASK //

}
