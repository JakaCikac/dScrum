package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.server.impl.project.ProjectImpl;
import si.fri.tpo.gwt.server.impl.team.TeamImpl;

import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class TeamRegistrationServiceImpl {
    public static Pair<Boolean, Integer> saveTeamWithProject(TeamDTO teamDTO, String projectName) {

        // check for duplicates!
        Pair<Boolean, Integer> duplPair = duplicateCheck(projectName);

        if (duplPair == null || !duplPair.getFirst().booleanValue())
            return duplPair;


        Pair<Boolean, Integer> aBoolean = TeamImpl.saveTeam(teamDTO);
        if (aBoolean == null)
            return Pair.of(false, -1);


        return Pair.of(true, aBoolean.getSecond());
    }

    private static Pair<Boolean, Integer> duplicateCheck(String projectName) {

        List<ProjectDTO> projectDTOs = ProjectImpl.getAllProject();
        if(projectDTOs != null) {
            System.out.println("Retrieved All projects list.");
        }
        for (ProjectDTO dto : projectDTOs) {
            if (dto.getName().equals(projectName)) {
                System.out.println("Can't add: existing team!");
                return Pair.of(false, -1);
            }
        }
        return Pair.of(true, 0);
    }
}
