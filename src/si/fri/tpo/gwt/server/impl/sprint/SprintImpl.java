package si.fri.tpo.gwt.server.impl.sprint;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.Sprint;
import si.fri.tpo.gwt.server.jpa.Team;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-Apr-14.
 */
public class SprintImpl {
    public static Pair<Boolean, String> saveNewSprint(SprintDTO sprintDTO) {
        try {
            Sprint s = new Sprint();
            s.setSeqNumber(sprintDTO.getSeqNumber());
            s.setStatus(sprintDTO.getStatus());
            s.setEndDate(sprintDTO.getEndDate());
            s.setStartDate(sprintDTO.getStartDate());
            s.setVelocity(sprintDTO.getVelocity());

            ProjectDTO projectDTO = sprintDTO.getProject();
            Project project = new Project();
            project.setProjectId(projectDTO.getProjectId());
            project.setStatus(projectDTO.getStatus());
            project.setDescription(projectDTO.getDescription());
            project.setName(projectDTO.getName());
            Team team = new Team();
            team.setTeamId(projectDTO.getTeamTeamId().getTeamId());
            team.setScrumMasterId(projectDTO.getTeamTeamId().getScrumMasterId());
            team.setProductOwnerId(projectDTO.getTeamTeamId().getProductOwnerId());

            List<User> userList = new ArrayList<User>();
            System.out.println("Team's userList:" + projectDTO.getTeamTeamId().getUserList().size());
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
            project.setTeamTeamId(team);

            s.setProject(project);
            try {
                if (s == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getSprintProxy().create(s);

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

}
