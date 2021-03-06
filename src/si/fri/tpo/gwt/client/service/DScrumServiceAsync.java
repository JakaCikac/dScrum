package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;

import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */
public interface DScrumServiceAsync {

    // DUMMY //
    void dummyCharacterTrigger(Character b, AsyncCallback<Character> callback);

    void dummyUserTrigger(UserDTO userDTO, AsyncCallback<UserDTO> callback3);

    void dummyTeamTrigger(TeamDTO teamDTO, AsyncCallback<TeamDTO> callback5);

    void dummyProjectTrigger(ProjectDTO projectDTO, AsyncCallback<ProjectDTO> callback6);

    void dummySprintTrigger(SprintDTO sprintDTO, AsyncCallback<SprintDTO> callback2);

    void dummySprintPKTrigger(SprintPKDTO sprintPKDTO, AsyncCallback<SprintPKDTO> callbackPKDTO);

    void dummyWorkloadTrigger(WorkloadDTO workloadDTO, AsyncCallback<WorkloadDTO> workloadCallback);

    void dummyWorkloadPKTrigger(WorkloadPKDTO workloadPKDTO, AsyncCallback<WorkloadPKDTO> workloadPKCallback);

    void dummyDiscussionTrigger(DiscussionDTO discussionDTO, AsyncCallback<DiscussionDTO> discussionCallback);

    void dummyDiscussionPKTrigger(DiscussionPKDTO discussionPKDTO, AsyncCallback<DiscussionPKDTO> discussionPKCallback);

    void dummyAcceptanceTestTrigger(AcceptanceTestDTO acceptanceTestDTO, AsyncCallback<AcceptanceTestDTO> acceptanceTestCallback);

    void dummyCommentTrigger(CommentDTO commentDTO, AsyncCallback<CommentDTO> commentCallback);

    void dummyCommentPKTrigger(CommentPKDTO commentPKDTO, AsyncCallback<CommentPKDTO> commentPKCallback);

    void dummyDailyScrumEntryTrigger(DailyScrumEntryDTO dailyScrumEntryDTO, AsyncCallback<DailyScrumEntryDTO> dailyScrumEntryCallback);

    void dummyDailyScrumEntryPKTrigger(DailyScrumEntryPKDTO dailyScrumEntryPKDTO, AsyncCallback<DailyScrumEntryPKDTO> dailyScrumEntryPKCallback);

    void dummyPriorityTrigger(PriorityDTO priorityDTO, AsyncCallback<PriorityDTO> priorityCallback);

    void dummyWorkblockTrigger(WorkblockDTO workblockDTO, AsyncCallback<WorkblockDTO> workblockCallback);

    void dummyWorkblockPKTrigger(WorkblockPKDTO workblockPKDTO, AsyncCallback<WorkblockPKDTO> workblockPKCallback);

    void dummyTaskTrigger(TaskDTO taskDTO, AsyncCallback<TaskDTO> taskCallback);

    void dummyTaskPKTrigger(TaskPKDTO taskPKDTO, AsyncCallback<TaskPKDTO> taskPKCallback);

    void dummyUserStoryTrigger(UserStoryDTO userStoryDTO, AsyncCallback<UserStoryDTO> userStoryCallback);
    // DUMMY //

    // PROJECT //
    void saveProject(ProjectDTO projectDTO, AsyncCallback<Pair<Boolean,String>> saveProject);

    void updateProject(ProjectDTO projectDTO, boolean changedProjectName, String originalProjectName, AsyncCallback<Pair<Boolean,String>> updateProject);

    void findAllProjects(AsyncCallback<List<ProjectDTO>> callback);

    void findUserProjects(UserDTO userDTO, AsyncCallback<List<ProjectDTO>> callback);

    void findProjectByName(String name, AsyncCallback<ProjectDTO> callback);
    // PROJECT //

    // USER //
    void saveUser(UserDTO userDTO, Boolean value, AsyncCallback<Pair<Boolean, String>> saveUser);

    void updateUser(UserDTO userDTO, boolean b, AsyncCallback<Pair<Boolean, String>> updateUser);

    void validateUserData(String emailValue, AsyncCallback<Pair<Boolean, String>> validationCallback);

    void performUserLogin(String username, String passwordHash, AsyncCallback<Pair<UserDTO, String>> async);

    void findUserById(int userId, AsyncCallback<UserDTO> getOrigScrumMasterDTO);

    void findUserByUsername(String username, AsyncCallback<UserDTO> callback);

    void findAllUsers(AsyncCallback<List<UserDTO>> callback);
    // USER //

    // TEAM //
    void saveTeam(TeamDTO teamDTO, String projectName, boolean withProject, AsyncCallback<Pair<Boolean, Integer>> saveTeam);

    void updateTeam(TeamDTO teamDTO, String name, boolean withProject, AsyncCallback<Pair<Boolean, Integer>> updateTeam);
    // TEAM //

    // SPRINT //
    void saveSprint(SprintDTO sprintDTO, AsyncCallback<Pair<Boolean,Integer>> saveSprint);

    void updateSprint(SprintDTO sprintDTO, AsyncCallback<Pair<Boolean,String>> updateSprint);

    void deleteSprint(SprintDTO sprintDTO, AsyncCallback<Pair<Boolean,String>> deleteSprint);
    // SPRINT //

    // ACCEPTANCE TEST //
    void saveAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList, AsyncCallback<Pair<Boolean,List<Integer>>> saveAcceptanceTestList);

    void updateAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestDTOList, AsyncCallback<Pair<Boolean, String>> saveAcceptanceTestList);

    void deleteAcceptanceTest(AcceptanceTestDTO selectedItem, AsyncCallback<Pair<Boolean,String>> deleteAcceptanceTest);
    // ACCEPTANCE TEST //

    // USER STORY //
    void saveUserStory(UserStoryDTO userStoryDTO, ProjectDTO projectDTO, AsyncCallback<Pair<Boolean,String>> saveUserStory);

    void findAllStoriesByProject(ProjectDTO projectDTO, AsyncCallback<List<UserStoryDTO>> callback);

    void updateUserStory(UserStoryDTO userStoryDTO, AsyncCallback<Pair<Boolean,String>> updateUserStory);

    void deleteUserStory(UserStoryDTO userStoryDTO, AsyncCallback<Pair<Boolean,String>> deleteUserStory);
    // USER STORY //

    // TASK //
    void saveTask(TaskDTO taskDTO, UserStoryDTO userStoryDTO, AsyncCallback<Pair<Boolean, String>> saveTask);

    void updateTask(TaskDTO p, AsyncCallback<Pair<Boolean,String>> updateTask);

    void deleteTask(TaskDTO taskDTO, AsyncCallback<Pair<Boolean, String>> deleteTask);
    // TASK //

    // USER_STORY_COMMENT //
    void saveComment(UserStoryDTO userStoryDTO, AsyncCallback<Pair<Boolean,String>> saveComment);

    void updateComment(UserStoryDTO userStoryDTO, AsyncCallback<Pair<Boolean,String>> updateComment);

    // USER_STORY_COMMENT //

    // DISCUSSION //
    void findAllDiscussionsByProject(ProjectDTO projectDTO, AsyncCallback<List<DiscussionDTO>> callback);

    void saveDiscussion(DiscussionDTO discussionDTO, ProjectDTO projectDTO, AsyncCallback<Pair<Boolean,String>> callback);

    void updateDiscussion(DiscussionDTO discussionDTO, AsyncCallback<Pair<Boolean,String>> updateDiscussion);

    void saveDiscussionComment(CommentDTO commentDTO, AsyncCallback<Pair<Boolean,Integer>> saveDiscussionComment);

    void deleteDiscussion(DiscussionDTO discussionDTO, AsyncCallback<Pair<Boolean,String>> deleteDiscussion);

    // DISCUSSION //

    // WORKLOAD //
    void updateWorkload(WorkloadDTO workloadDTO, AsyncCallback<Pair<Boolean,String>> updateWorkload);

    void saveWorkload(List<WorkloadDTO> workloadDTO, AsyncCallback<Pair<Boolean,List<Integer>>> saveWorkload);

    // WORKLOAD //

    // WORKBLOCK //

    void saveWorkblock(WorkblockDTO workblockDTO, AsyncCallback<Pair<Boolean,Integer>> saveWorkblock);

    void updateWorkblock(WorkblockDTO wbDTO, AsyncCallback<Pair<Boolean, Integer>> updateWorkblock);

    // WORKBLOCK //
}

