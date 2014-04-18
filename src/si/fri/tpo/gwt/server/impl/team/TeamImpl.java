package si.fri.tpo.gwt.server.impl.team;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.jpa.Team;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class TeamImpl {

    public static Pair<Boolean, Integer> saveTeam(TeamDTO dto) {
        int insertedTeamID = -1;
        try {
            Team team = new Team();
            team.setScrumMasterId(dto.getScrumMasterId());
            team.setProductOwnerId(dto.getProductOwnerId());
            List<User> userList = new ArrayList<User>();
            for (UserDTO udto : dto.getUserList()) {
                User u = new User();
                u.setPassword(udto.getPassword());
                u.setSalt(udto.getSalt());
                u.setLastName(udto.getLastName());
                u.setFirstName(udto.getFirstName());
                u.setEmail(udto.getEmail());
                u.setUserId(udto.getUserId());
                u.setIsAdmin(udto.isAdmin());
                u.setIsActive(udto.isActive());
                u.setTimeCreated(udto.getTimeCreated());
                u.setUsername(udto.getUsername());
                userList.add(u);
            }
            team.setUserList(userList);

            try {
                if (team == null)
                    return Pair.of(false, -1);
                insertedTeamID = ProxyManager.getTeamProxy().create(team);
                if (insertedTeamID == -1) {
                    System.out.println("ob vstavljanju s kontrolerjem je id ... -1 :(");
                }

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -1);
        }
        return Pair.of(true, insertedTeamID);
    }

    public static Pair<Boolean, Integer> updateTeamWithProject(TeamDTO dto) {
        int insertedTeamID = -1;
        try {
            if (dto.getTeamId() != null) {
                System.out.println("team ID: " + dto.getTeamId());
            }
            Team team = ProxyManager.getTeamProxy().findTeam(dto.getTeamId());
            team.setScrumMasterId(dto.getScrumMasterId());
            team.setProductOwnerId(dto.getProductOwnerId());
            List<User> userList = new ArrayList<User>();
            for (UserDTO udto : dto.getUserList()) {
                User u = new User();
                u.setPassword(udto.getPassword());
                u.setSalt(udto.getSalt());
                u.setLastName(udto.getLastName());
                u.setFirstName(udto.getFirstName());
                u.setEmail(udto.getEmail());
                u.setUserId(udto.getUserId());
                u.setIsAdmin(udto.isAdmin());
                u.setIsActive(udto.isActive());
                u.setTimeCreated(udto.getTimeCreated());
                u.setUsername(udto.getUsername());
                userList.add(u);
            }
            team.setUserList(userList);

            try {
                if (team == null)
                    return Pair.of(false, -1);

                ProxyManager.getTeamProxy().edit(team);
                insertedTeamID = dto.getTeamId();
                if (insertedTeamID == -1) {
                    System.out.println("ob vstavljanju s kontrolerjem je id ... -1 :(");
                }

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -1);
        }
        return Pair.of(true, insertedTeamID);
    }
}
