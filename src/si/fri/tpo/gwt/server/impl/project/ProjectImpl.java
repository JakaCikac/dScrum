package si.fri.tpo.gwt.server.impl.project;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class ProjectImpl {


    public static List<ProjectDTO> getAllProject() {
        //Get project list
        List<Project> projectList;
        // retrieve project list from JPA controller
        projectList = ProxyManager.getProjectProxy().getProjectsList();

        ArrayList<ProjectDTO> resList = new ArrayList<ProjectDTO>();
        ProjectDTO projectDTO;
        for (Project p : projectList) {
            projectDTO = new ProjectDTO();
            projectDTO.setName(p.getName());
            projectDTO.setDescription(p.getDescription());
            projectDTO.setStatus(p.getStatus());
            Team team = p.getTeamTeamId();
            if (team != null) {
                TeamDTO teamDTO = new TeamDTO();
                teamDTO.setTeamId(team.getTeamId());
                teamDTO.setProductOwnerId(team.getProductOwnerId());
                teamDTO.setScrumMasterId(team.getScrumMasterId());
                ArrayList<UserDTO> userDTOList = new ArrayList<UserDTO>();
                for (User user : team.getUserList()){
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
                    userDTOList.add(userDTO);
                }
                teamDTO.setUserList(userDTOList);
                projectDTO.setTeamTeamId(teamDTO);
            }
            else projectDTO.setTeamTeamId(null); //TODO: possible break
            resList.add(projectDTO);
        }
        return resList;
    }

    public static Pair<Boolean, String> saveNewProject(ProjectDTO projectDTO) {

        // check for project in database
        System.out.println("Project to check in base: " + projectDTO.getName());
        Project existingProject = ProxyManager.getProjectProxy().findProjectByName(projectDTO.getName());
        try {
            if (existingProject != null) {
                System.out.println("Existing project exists!");
                return Pair.of(false, "Project with this name already exists!");
            } else System.out.println("Project check passed, no existing project.");

            Project p = new Project();
            p.setName(projectDTO.getName());
            p.setDescription(projectDTO.getDescription());
            p.setStatus(projectDTO.getStatus());
            Team team = new Team();
            team.setTeamId(projectDTO.getTeamTeamId().getTeamId());
            team.setScrumMasterId(projectDTO.getTeamTeamId().getScrumMasterId());
            team.setProductOwnerId(projectDTO.getTeamTeamId().getProductOwnerId());

            List<User> userList = new ArrayList<User>();
            if (projectDTO.getTeamTeamId().getUserList() != null) {
                for (UserDTO userDTO : projectDTO.getTeamTeamId().getUserList()) {
                    User user = new User();
                    user.setUserId(userDTO.getUserId());
                    user.setUsername(userDTO.getUsername());
                    user.setPassword(userDTO.getPassword());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setIsAdmin(userDTO.isAdmin());
                    user.setSalt(userDTO.getSalt());
                    user.setIsActive(userDTO.isActive());
                    user.setTimeCreated(userDTO.getTimeCreated());
                    userList.add(user);
                }
                team.setUserList(userList);
            } else return Pair.of(false, "No user list when saving team.");
            p.setTeamTeamId(team);
            try {
                if (p == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getProjectProxy().create(p);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Project saved successfully.");
    }

    public static List<ProjectDTO> getUserProject(UserDTO dto) {
        //Get project list
        List<Project> projectList;
        // retrieve project list from JPA controller
        projectList = ProxyManager.getProjectProxy().getUserProjectsList(dto.getUserId());

        ArrayList<ProjectDTO> resList = new ArrayList<ProjectDTO>();
        ProjectDTO projectDTO;
        for (Project p : projectList) {
            projectDTO = new ProjectDTO();
            projectDTO.setName(p.getName());
            projectDTO.setDescription(p.getDescription());
            projectDTO.setStatus(p.getStatus());
            Team team = p.getTeamTeamId();
            if (team != null) {
                TeamDTO teamDTO = new TeamDTO();
                teamDTO.setTeamId(team.getTeamId());
                teamDTO.setProductOwnerId(team.getProductOwnerId());
                teamDTO.setScrumMasterId(team.getScrumMasterId());
                ArrayList<UserDTO> userDTOList = new ArrayList<UserDTO>();
                for (User user : team.getUserList()){
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
                    userDTOList.add(userDTO);
                }
                teamDTO.setUserList(userDTOList);
                projectDTO.setTeamTeamId(teamDTO);
            }
            else projectDTO.setTeamTeamId(null); //TODO: possible break
            resList.add(projectDTO);
        }
        return resList;
    }

    public static ProjectDTO getProjectByName(String name) {
        Project p;
        p = ProxyManager.getProjectProxy().findProjectByName(name);
        if (p != null) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setProjectId(p.getProjectId());
            projectDTO.setName(p.getName());
            projectDTO.setDescription(p.getDescription());
            projectDTO.setStatus(p.getStatus());
            Team team = p.getTeamTeamId();
            if (team != null) {
                TeamDTO teamDTO = new TeamDTO();
                teamDTO.setTeamId(team.getTeamId());
                teamDTO.setProductOwnerId(team.getProductOwnerId());
                teamDTO.setScrumMasterId(team.getScrumMasterId());
                ArrayList<UserDTO> userDTOList = new ArrayList<UserDTO>();
                for (User user : team.getUserList()){
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
                    userDTOList.add(userDTO);
                }
                teamDTO.setUserList(userDTOList);
                projectDTO.setTeamTeamId(teamDTO);
            } else projectDTO.setTeamTeamId(null);

            List<SprintDTO> sprintDTOList = new ArrayList<SprintDTO>();
            if (p.getSprintList() != null) {
                for (Sprint s : p.getSprintList()) {
                    s = ProxyManager.getSprintProxy().findSprint(s.getSprintPK());
                    SprintDTO sprintDTO = new SprintDTO();
                    SprintPKDTO sprintPKDTO = new SprintPKDTO();
                    sprintPKDTO.setSprintId(s.getSprintPK().getSprintId());
                    sprintPKDTO.setProjectProjectId(s.getSprintPK().getProjectProjectId());
                    sprintDTO.setSprintPK(sprintPKDTO);
                    sprintDTO.setSeqNumber(s.getSeqNumber());
                    sprintDTO.setStatus(s.getStatus());
                    sprintDTO.setStartDate(s.getStartDate());
                    sprintDTO.setEndDate(s.getEndDate());
                    sprintDTO.setVelocity(s.getVelocity());
                    if (s.getUserStoryList() != null) {
                        List<UserStoryDTO> userStoryDTOList = new ArrayList<UserStoryDTO>();
                        for (UserStory userStory : s.getUserStoryList()) {
                            userStory = ProxyManager.getUserStoryProxy().findUserStory(userStory.getStoryId());
                            UserStoryDTO userStoryDTO = new UserStoryDTO();
                            userStoryDTO.setStoryId(userStory.getStoryId());
                            userStoryDTO.setName(userStory.getName());
                            userStoryDTO.setContent(userStory.getContent());
                            userStoryDTO.setBusinessValue(userStory.getBusinessValue());
                            userStoryDTO.setStatus(userStory.getStatus());
                            if (userStory.getEstimateTime() != null) {
                                userStoryDTO.setEstimateTime(userStory.getEstimateTime().doubleValue());
                            } else userStoryDTO.setEstimateTime(null);
                            userStoryDTO.setStoryId(userStory.getStoryId());
                            userStoryDTO.setProjectProjectId(projectDTO);
                            userStoryDTO.setComment(userStory.getComment());
                            userStoryDTO.setSprint(sprintDTO);

                            PriorityDTO priorityDTO = new PriorityDTO();
                            priorityDTO.setPriorityId(userStory.getPriorityPriorityId().getPriorityId());
                            priorityDTO.setName(userStory.getPriorityPriorityId().getName());
                            userStoryDTO.setPriorityPriorityId(priorityDTO);

                            List<AcceptanceTestDTO> acceptanceTestDTOList = new ArrayList<AcceptanceTestDTO>();
                            for (AcceptanceTest at : userStory.getAcceptanceTestList()) {
                                AcceptanceTestDTO acceptanceTestDTO = new AcceptanceTestDTO();
                                acceptanceTestDTO.setAcceptanceTestId(at.getAcceptanceTestId());
                                acceptanceTestDTO.setContent(at.getContent());
                                acceptanceTestDTOList.add(acceptanceTestDTO);
                            }
                            userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList);

                            if (userStory.getTaskList() != null) {
                                List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();
                                for (Task task : userStory.getTaskList()) {
                                    task = ProxyManager.getTaskProxy().findTask(task.getTaskPK());
                                    TaskDTO taskDTO = new TaskDTO();
                                    taskDTO.setDescription(task.getDescription());
                                    taskDTO.setTimeRemaining(task.getTimeRemaining());
                                    taskDTO.setEstimatedTime(task.getEstimatedTime());
                                    taskDTO.setStatus(task.getStatus());
                                    taskDTO.setPreassignedUserName(task.getPreassignedUserName());
                                    taskDTO.setAssignedDate(task.getAssignedDate());

                                    taskDTO.setUserStory(userStoryDTO);

                                    TaskPKDTO taskPKDTO = new TaskPKDTO();
                                    taskPKDTO.setTaskId(task.getTaskPK().getTaskId());
                                    taskPKDTO.setUserStoryStoryId(task.getTaskPK().getUserStoryStoryId());
                                    taskDTO.setTaskPK(taskPKDTO);

                                    if (task.getUserUserId() != null) {
                                        User user = ProxyManager.getUserProxy().findUser(task.getUserUserId().getUserId());
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
                                        taskDTO.setUserUserId(userDTO);
                                    }

                                    List<WorkloadDTO> workloadDTOList = new ArrayList<WorkloadDTO>();
                                    for(Workload workload : task.getWorkloadList()){
                                        workload = ProxyManager.getWorkloadProxy().findWorkload(workload.getWorkloadPK());
                                        WorkloadDTO workloadDTO = new WorkloadDTO();
                                        workloadDTO.setDay(workload.getDay());
                                        workloadDTO.setTimeRemaining(workload.getTimeRemaining());
                                        workloadDTO.setTimeSpent(workload.getTimeSpent());
                                        workloadDTO.setStarted(workload.getStarted());
                                        workloadDTO.setStartTime(workload.getStartTime());
                                        workloadDTO.setStopTime(workload.getStopTime());

                                        User user = ProxyManager.getUserProxy().findUser(workload.getUser().getUserId());
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
                                        workloadDTO.setUser(userDTO);

                                        workloadDTO.setTask(taskDTO);

                                        WorkloadPKDTO workloadPKDTO = new WorkloadPKDTO();
                                        WorkloadPK workloadPK = workload.getWorkloadPK();
                                        workloadPKDTO.setTaskTaskId(workloadPK.getTaskTaskId());
                                        workloadPKDTO.setTaskUserStoryStoryId(workloadPK.getTaskUserStoryStoryId());
                                        workloadPKDTO.setUserUserId(workloadPK.getUserUserId());
                                        workloadPKDTO.setWorkloadId(workloadPK.getWorkloadId());
                                        workloadDTO.setWorkloadPK(workloadPKDTO);

                                        List<WorkblockDTO> workblockDTOList = new ArrayList<WorkblockDTO>();
                                        for(Workblock workblock : workload.getWorkblockList()){
                                            workblock = ProxyManager.getWorkblockProxy().findWorkblock(workblock.getWorkblockPK());
                                            WorkblockDTO workblockDTO = new WorkblockDTO();
                                            workblockDTO.setTimeStart(workblock.getTimeStart());
                                            workblockDTO.setTimeStop(workblock.getTimeStop());

                                            workblockDTO.setWorkload(workloadDTO);

                                            WorkblockPKDTO workblockPKDTO = new WorkblockPKDTO();
                                            WorkblockPK workblockPK = workblock.getWorkblockPK();
                                            workloadPKDTO.setWorkloadId(workblockPK.getWorkloadWorkloadId());
                                            workblockPKDTO.setWorkloadTaskTaskId(workloadPK.getTaskTaskId());
                                            workblockPKDTO.setWorkloadTaskUserStoryStoryId(workblockPK.getWorkloadTaskUserStoryStoryId());
                                            workblockPKDTO.setWorkloadUserUserId(workblockPK.getWorkloadUserUserId());
                                            workblockPKDTO.setWorkloadWorkloadId(workblockPK.getWorkloadWorkloadId());
                                            workblockDTO.setWorkblockPK(workblockPKDTO);

                                            workblockDTOList.add(workblockDTO);
                                        }
                                        workloadDTO.setWorkblockList(workblockDTOList);

                                        workloadDTOList.add(workloadDTO);
                                    }
                                    taskDTO.setWorkloadList(workloadDTOList);

                                    taskDTOList.add(taskDTO);
                                }
                                userStoryDTO.setTaskList(taskDTOList);
                            }
                            userStoryDTOList.add(userStoryDTO);
                        }
                        sprintDTO.setUserStoryList(userStoryDTOList);
                    }
                    sprintDTOList.add(sprintDTO);
                }
                projectDTO.setSprintList(sprintDTOList);

            } else projectDTO.setSprintList(null);

            List<UserStoryDTO> userStoryDTOList = new ArrayList<UserStoryDTO>();
            if (p.getUserStoryList() != null) {
                for (UserStory us : p.getUserStoryList()) {
                    UserStoryDTO userStoryDTO = new UserStoryDTO();
                    userStoryDTO.setStoryId(us.getStoryId());
                    userStoryDTO.setName(us.getName());
                    userStoryDTO.setContent(us.getContent());
                    userStoryDTO.setBusinessValue(us.getBusinessValue());
                    userStoryDTO.setStatus(us.getStatus());
                    if (us.getEstimateTime() != null) {
                        userStoryDTO.setEstimateTime(us.getEstimateTime().doubleValue());
                    } else userStoryDTO.setEstimateTime(null);
                    userStoryDTO.setStoryId(us.getStoryId());
                    userStoryDTO.setProjectProjectId(projectDTO);

                    PriorityDTO priorityDTO = new PriorityDTO();
                    priorityDTO.setPriorityId(us.getPriorityPriorityId().getPriorityId());
                    priorityDTO.setName(us.getPriorityPriorityId().getName());
                    userStoryDTO.setPriorityPriorityId(priorityDTO);

                    List<AcceptanceTestDTO> acceptanceTestDTOList = new ArrayList<AcceptanceTestDTO>();
                    for (AcceptanceTest at : us.getAcceptanceTestList()) {
                        AcceptanceTestDTO acceptanceTestDTO = new AcceptanceTestDTO();
                        acceptanceTestDTO.setAcceptanceTestId(at.getAcceptanceTestId());
                        acceptanceTestDTO.setContent(at.getContent());
                        acceptanceTestDTOList.add(acceptanceTestDTO);
                    }
                    userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList);

                    //TODO: Set taskList to userStoryDTO
                    if (us.getTaskList() != null) {
                        List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();
                        for (Task task : us.getTaskList()) {
                            task = ProxyManager.getTaskProxy().findTask(task.getTaskPK());
                            TaskDTO taskDTO = new TaskDTO();
                            taskDTO.setDescription(task.getDescription());
                            taskDTO.setTimeRemaining(task.getTimeRemaining());
                            taskDTO.setEstimatedTime(task.getEstimatedTime());
                            taskDTO.setStatus(task.getStatus());
                            taskDTO.setPreassignedUserName(task.getPreassignedUserName());
                            taskDTO.setAssignedDate(task.getAssignedDate());

                            taskDTO.setUserStory(userStoryDTO);

                            TaskPKDTO taskPKDTO = new TaskPKDTO();
                            taskPKDTO.setTaskId(task.getTaskPK().getTaskId());
                            taskPKDTO.setUserStoryStoryId(task.getTaskPK().getUserStoryStoryId());
                            taskDTO.setTaskPK(taskPKDTO);

                            if (task.getUserUserId() != null) {
                                User user = ProxyManager.getUserProxy().findUser(task.getUserUserId().getUserId());
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
                                taskDTO.setUserUserId(userDTO);
                            }

                            List<WorkloadDTO> workloadDTOList = new ArrayList<WorkloadDTO>();
                            for(Workload workload : task.getWorkloadList()){
                                workload = ProxyManager.getWorkloadProxy().findWorkload(workload.getWorkloadPK());
                                WorkloadDTO workloadDTO = new WorkloadDTO();
                                workloadDTO.setDay(workload.getDay());
                                workloadDTO.setTimeRemaining(workload.getTimeRemaining());
                                workloadDTO.setTimeSpent(workload.getTimeSpent());

                                User user = ProxyManager.getUserProxy().findUser(workload.getUser().getUserId());
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
                                workloadDTO.setUser(userDTO);

                                workloadDTO.setTask(taskDTO);

                                WorkloadPKDTO workloadPKDTO = new WorkloadPKDTO();
                                WorkloadPK workloadPK = workload.getWorkloadPK();
                                workloadPKDTO.setTaskTaskId(workloadPK.getTaskTaskId());
                                workloadPKDTO.setTaskUserStoryStoryId(workloadPK.getTaskUserStoryStoryId());
                                workloadPKDTO.setUserUserId(workloadPK.getUserUserId());
                                workloadPKDTO.setWorkloadId(workloadPK.getWorkloadId());
                                workloadDTO.setWorkloadPK(workloadPKDTO);

                                List<WorkblockDTO> workblockDTOList = new ArrayList<WorkblockDTO>();
                                for(Workblock workblock : workload.getWorkblockList()){
                                    workblock = ProxyManager.getWorkblockProxy().findWorkblock(workblock.getWorkblockPK());
                                    WorkblockDTO workblockDTO = new WorkblockDTO();
                                    workblockDTO.setTimeStart(workblock.getTimeStart());
                                    workblockDTO.setTimeStop(workblock.getTimeStop());

                                    workblockDTO.setWorkload(workloadDTO);

                                    WorkblockPKDTO workblockPKDTO = new WorkblockPKDTO();
                                    WorkblockPK workblockPK = workblock.getWorkblockPK();
                                    workloadPKDTO.setWorkloadId(workblockPK.getWorkloadWorkloadId());
                                    workblockPKDTO.setWorkloadTaskTaskId(workloadPK.getTaskTaskId());
                                    workblockPKDTO.setWorkloadTaskUserStoryStoryId(workblockPK.getWorkloadTaskUserStoryStoryId());
                                    workblockPKDTO.setWorkloadUserUserId(workblockPK.getWorkloadUserUserId());
                                    workblockPKDTO.setWorkloadWorkloadId(workblockPK.getWorkloadWorkloadId());
                                    workblockDTO.setWorkblockPK(workblockPKDTO);

                                    workblockDTOList.add(workblockDTO);
                                }
                                workloadDTO.setWorkblockList(workblockDTOList);

                                workloadDTOList.add(workloadDTO);
                            }
                            taskDTO.setWorkloadList(workloadDTOList);

                            taskDTOList.add(taskDTO);
                        }
                        userStoryDTO.setTaskList(taskDTOList);
                    } else userStoryDTO.setTaskList(null);

                    SprintDTO sprintDTO = new SprintDTO();
                    if (us.getSprint() != null) {
                        SprintPKDTO sprintPKDTO = new SprintPKDTO();
                        sprintPKDTO.setSprintId(us.getSprint().getSprintPK().getSprintId());
                        sprintPKDTO.setProjectProjectId(us.getSprint().getSprintPK().getProjectProjectId());
                        sprintDTO.setSprintPK(sprintPKDTO);
                        sprintDTO.setSeqNumber(us.getSprint().getSeqNumber());
                        sprintDTO.setStatus(us.getSprint().getStatus());
                        sprintDTO.setStartDate(us.getSprint().getStartDate());
                        sprintDTO.setEndDate(us.getSprint().getEndDate());
                        sprintDTO.setVelocity(us.getSprint().getVelocity());
                    } else sprintDTO = null;
                    userStoryDTO.setSprint(sprintDTO);
                    userStoryDTOList.add(userStoryDTO);
                }
                projectDTO.setUserStoryList(userStoryDTOList);
            }
            return projectDTO;
        }
        else return null;
    }

    public static Pair<Boolean, String> updateProject(ProjectDTO projectDTO, boolean changedProjectName, String originalProjectName) {
        try {
            // Additional duplication control
            Project existingProject;
            if (changedProjectName) {
                existingProject = ProxyManager.getProjectProxy().findProjectByName(projectDTO.getName());
            } else existingProject = null;


            if (existingProject != null && changedProjectName) {
                System.out.println("Existing project exists!");
                return Pair.of(false, "Project with this name already exists!");
            }

            Project p = ProxyManager.getProjectProxy().findProjectByName(originalProjectName);
            p.setProjectId(projectDTO.getProjectId());
            p.setName(projectDTO.getName());
            p.setDescription(projectDTO.getDescription());
            p.setStatus(projectDTO.getStatus());
            Team team = new Team();
            team.setTeamId(projectDTO.getTeamTeamId().getTeamId());
            team.setScrumMasterId(projectDTO.getTeamTeamId().getScrumMasterId());
            team.setProductOwnerId(projectDTO.getTeamTeamId().getProductOwnerId());

            List<User> userList = new ArrayList<User>();
            if (projectDTO.getTeamTeamId().getUserList() != null) {
                for (UserDTO userDTO : projectDTO.getTeamTeamId().getUserList()) {
                    User user = new User();
                    user.setUserId(userDTO.getUserId());
                    user.setUsername(userDTO.getUsername());
                    user.setPassword(userDTO.getPassword());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setIsAdmin(userDTO.isAdmin());
                    user.setSalt(userDTO.getSalt());
                    user.setIsActive(userDTO.isActive());
                    user.setTimeCreated(userDTO.getTimeCreated());
                    userList.add(user);
                }
                team.setUserList(userList);
            } else return Pair.of(false, "No project list when saving team.");
            p.setTeamTeamId(team);
            try {
                if (p == null)
                    return Pair.of(false, "Data error!");
                ProxyManager.getProjectProxy().edit(p);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Project updated successfully.");
    }
}