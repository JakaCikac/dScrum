package si.fri.tpo.gwt.server.impl.sprint;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.SprintPKDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-Apr-14.
 */
public class SprintImpl {
    public static Pair<Boolean, Integer> saveNewSprint(SprintDTO sprintDTO) {
        int sprintID = -1;
        try {
            Sprint s = new Sprint();
            s.setSeqNumber(sprintDTO.getSeqNumber());
            s.setStatus(sprintDTO.getStatus());
            s.setEndDate(sprintDTO.getEndDate());
            s.setStartDate(sprintDTO.getStartDate());
            s.setVelocity(sprintDTO.getVelocity());

            Project project = ProxyManager.getProjectProxy().findProjectByName(sprintDTO.getProject().getName());
            s.setProject(project);

            SprintPK sprintPK = new SprintPK();
            sprintPK.setProjectProjectId(project.getProjectId());
            s.setSprintPK(sprintPK);

            //List<Sprint> sprintList = project.getSprintList();
            //sprintList.add(s);
            //project.setSprintList(sprintList);

            try {
                if (s == null)
                    return Pair.of(false, -1);

                sprintID = ProxyManager.getSprintProxy().create(s);
                System.out.println("Shranjen Sprint " + sprintID);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, -2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -3);
        }
        return Pair.of(true, sprintID);
    }

    public static Pair<Boolean, String> updateSprint(SprintDTO sprintDTO) {
        try {
            SprintPKDTO sprintPKDTO = sprintDTO.getSprintPK();
            SprintPK sprintPK = new SprintPK();
            sprintPK.setSprintId(sprintPKDTO.getSprintId());
            sprintPK.setProjectProjectId(sprintPKDTO.getProjectProjectId());
            System.out.println("1. updateSprint: " + sprintPK.getSprintId());

            Sprint s = null;
            try {
                s = ProxyManager.getSprintProxy().findSprint(sprintPK);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
            System.out.println("2. updateSprint: " + s.getSprintPK().getSprintId());
            s.setSprintPK(sprintPK);
            System.out.println("3. updateSprint: " + s.getSprintPK().getSprintId());
            s.setSeqNumber(sprintDTO.getSeqNumber());
            s.setStatus(sprintDTO.getStatus());
            s.setEndDate(sprintDTO.getEndDate());
            s.setStartDate(sprintDTO.getStartDate());
            s.setVelocity(sprintDTO.getVelocity());

            ProjectDTO projectDTO = sprintDTO.getProject();
            Project project = ProxyManager.getProjectProxy().findProjectByName(projectDTO.getName());
            s.setProject(project);
            try {
                if (s == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getSprintProxy().edit(s);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Sprint was updated successfully!");
    }

    public static Pair<Boolean, String> deleteSprint(SprintDTO sprintDTO) {
        try {
            SprintPKDTO sprintPKDTO = sprintDTO.getSprintPK();
            SprintPK sprintPK = new SprintPK();
            sprintPK.setSprintId(sprintPKDTO.getSprintId());
            sprintPK.setProjectProjectId(sprintPKDTO.getProjectProjectId());
            Sprint s = ProxyManager.getSprintProxy().findSprint(sprintPK);

            s.setSprintPK(sprintPK);
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
            //System.out.println("Team's userList:" + projectDTO.getTeamTeamId().getUserList().size());
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

                ProxyManager.getSprintProxy().destroy(sprintPK);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Sprint was deleted successfully!");
    }
}
