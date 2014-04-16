package si.fri.tpo.gwt.server.impl.project;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.fill.FillDTO;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.Team;
import si.fri.tpo.gwt.server.jpa.User;
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
        return Pair.of(true, "");
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
}