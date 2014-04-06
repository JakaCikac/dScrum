package si.fri.tpo.gwt.server.impl.fill;

import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.server.jpa.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 05/04/14.
 */
public class FillDTO {

   /* public static AcceptanceTestDTO fillAcceptanceTestDTO(AcceptanceTest acceptanceTest) {
        AcceptanceTestDTO acceptanceTestDTO = new AcceptanceTestDTO();

        acceptanceTestDTO.setAcceptanceTestId(acceptanceTest.getAcceptanceTestId());
        acceptanceTestDTO.setContent(acceptanceTest.getContent());
        acceptanceTestDTO.setUserStoryStoryId(acceptanceTest.getUserStoryStoryId());

        return acceptanceTestDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
   /* public static CommentDTO fillCommentDTO(Comment comment) {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentPK(comment.getCommentPK());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatetime(comment.getCreatetime());
        commentDTO.setUser(comment.getUser());
        commentDTO.setDiscussion(comment.getDiscussion());

        return commentDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
   /* private static DailyScrumEntryDTO fillDailyScrumEntryDTO(DailyScrumEntry dailyScrumEntry) {
        DailyScrumEntryDTO dailyScrumEntryDTO = new DailyScrumEntryDTO();

        dailyScrumEntryDTO.setDailyScrumEntryPK(dailyScrumEntry.getDailyScrumEntryPK());
        dailyScrumEntryDTO.setDate(dailyScrumEntry.getDate());
        dailyScrumEntryDTO.setPastWork(dailyScrumEntry.getPastWork());
        dailyScrumEntryDTO.setFutureWork(dailyScrumEntry.getFutureWork());
        dailyScrumEntryDTO.setProblemsAndIssues(dailyScrumEntry.getProblemsAndIssues());
        dailyScrumEntryDTO.setProject(dailyScrumEntry.getProject());
        dailyScrumEntryDTO.setUser(dailyScrumEntry.getUser());

        return dailyScrumEntryDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
   /* private static DiscussionDTO fillDiscussionDTO(Discussion discussion) {
        DiscussionDTO discussionDTO = new DiscussionDTO();

        discussionDTO.setDiscussionPK(discussion.getDiscussionPK());
        discussionDTO.setContent(discussion.getContent());
        discussionDTO.setCreatetime(discussion.getCreatetime());
        discussionDTO.setProject(discussion.getProject());
        discussionDTO.setUser(discussion.getUser());
        discussionDTO.setCommentList(discussion.getCommentList());

        return discussionDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    /*public static ProjectDTO fillProjectData(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStatus(project.getStatus());
        projectDTO.setTeamTeamId(project.getTeamTeamId());
        projectDTO.setDiscussionList(project.getDiscussionList());
        projectDTO.setDailyScrumEntryList(project.getDailyScrumEntryList());
        projectDTO.setSprintList(project.getSprintList());
        projectDTO.setUserStoryList(project.getUserStoryList());

        return projectDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    /*private static TaskDTO fillTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setTaskPK(task.getTaskPK());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setTimeRemaining(task.getTimeRemaining());
        taskDTO.setEstimatedTime(task.getEstimatedTime());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setWorkloadList(task.getWorkloadList());
        taskDTO.setUserStory(task.getUserStory());
        taskDTO.setUserUserId(task.getUserUserId());

        return taskDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    /*public static TeamDTO fillTeamData(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.setTeamId(team.getTeamId());
        teamDTO.setScrumMasterId(team.getScrumMasterId());
        teamDTO.setProductOwnerId(team.getProductOwnerId());
        teamDTO.setUserList(team.getUserList());
        teamDTO.setProjectList(team.getProjectList());

        return teamDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
   /* public static TeamDTO fillTeamDTO(Team team) {
        TeamDTO teamDTO = new TeamDTO();

        teamDTO.setTeamId(team.getTeamId());
        teamDTO.setScrumMasterId(team.getScrumMasterId());
        teamDTO.setProductOwnerId(team.getProductOwnerId());
        teamDTO.setUserList(team.getUserList());
        teamDTO.setProjectList(team.getProjectList());

        return teamDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    public static UserDTO fillUserData(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAdmin(user.getIsAdmin());
        userDTO.setSalt(user.getSalt());
        userDTO.setActive(user.getIsActive());
        userDTO.setTimeCreated(user.getTimeCreated());

        /* if (user.getTeamList() != null && user.getTeamList().size() > 0)
            userDTO.setTeamList(fillTeamDTOs(user.getTeamList()));

        if (user.getWorkloadList() != null && user.getWorkloadList().size() > 0)
            userDTO.setWorkloadList(fillWorkloadListDTOs(user.getWorkloadList()));

        if (user.getDiscussionList() != null && user.getDiscussionList().size() > 0)
            userDTO.setDiscussionList(fillDiscussionListDTOs(user.getDiscussionList()));

        if (user.getTaskList() != null && user.getTaskList().size() > 0)
            userDTO.setTaskList(fillTaskListDTOs(user.getTaskList()));

        if (user.getDailyScrumEntryList() != null && user.getDailyScrumEntryList().size() > 0)
            userDTO.setDailyScrumEntryList(fillDailyScrumEntryDTOs(user.getDailyScrumEntryList()));

        if (user.getCommentList() != null && user.getCommentList().size() > 0)
            userDTO.setCommentList(fillCommentListDTOs(user.getCommentList()));*/

        return userDTO;
    }

   /*  private static List<CommentDTO> fillCommentListDTOs(List<Comment> commentList) {
        return null;
    }

    private static List<DailyScrumEntryDTO> fillDailyScrumEntryDTOs(List<DailyScrumEntry> dailyScrumEntryList) {
        List<DailyScrumEntryDTO> dailyScrumEntryListDTOs = new ArrayList<DailyScrumEntryDTO>();
        for (DailyScrumEntry dailyScrumEntry : dailyScrumEntryList)
            dailyScrumEntryListDTOs.add(fillDailyScrumEntryDTO(dailyScrumEntry));

        return dailyScrumEntryListDTOs;
    }

    private static List<TaskDTO> fillTaskListDTOs(List<Task> taskList) {
        List<TaskDTO> taskListDTOs = new ArrayList<TaskDTO>();
        for (Task task : taskList)
            taskListDTOs.add(fillTaskDTO(task));

        return taskListDTOs;
    }

    private static List<DiscussionDTO> fillDiscussionListDTOs(List<Discussion> discussionList) {
        List<DiscussionDTO> discussionDTOs = new ArrayList<DiscussionDTO>();
        for (Discussion discussion : discussionList)
            discussionDTOs.add(fillDiscussionDTO(discussion));

        return discussionDTOs;
    }

    private static List<WorkloadDTO> fillWorkloadListDTOs(List<Workload> workloadList) {
        List<WorkloadDTO> workloadDTOs = new ArrayList<WorkloadDTO>();
        for (Workload workload : workloadList)
            workloadDTOs.add(fillWorkloadDTO(workload));

        return workloadDTOs;
    }

    private static List<TeamDTO> fillTeamDTOs(List<Team> teams) {
        List<TeamDTO> teamsDTOs = new ArrayList<TeamDTO>();
        for (Team team : teams)
            teamsDTOs.add(fillTeamDTO(team));

        return teamsDTOs;
    } */
    /* -------------------------------------------------------------------------------------------------------------- */
   /* private static WorkloadDTO fillWorkloadDTO(Workload workload) {
        WorkloadDTO workloadDTO = new WorkloadDTO();

        workloadDTO.setWorkloadPK(workload.getWorkloadPK());
        workloadDTO.setTimeSpent(workload.getTimeSpent());
        workloadDTO.setUser(workload.getUser());
        workloadDTO.setTask(workload.getTask());
        workloadDTO.setWorkblockList(workload.getWorkblockList());

        return workloadDTO;
    } */
}
