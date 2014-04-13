package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.project.ProjectImpl;
import si.fri.tpo.gwt.server.impl.user.UserImpl;

import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class ProjectRegistrationServiceImpl {

    public static Pair<Boolean, String> saveProject(ProjectDTO projectDTO) {
        boolean alsoAddedNewUser = false;

        // check for duplicates!
        Pair<Boolean, String> duplPair = duplicateCheck(projectDTO);

        if (duplPair == null || !duplPair.getFirst().booleanValue())
            return duplPair;


        Pair<Boolean, String> aBoolean = ProjectImpl.saveNewProject(projectDTO);
        if (aBoolean == null)
            return Pair.of(false, "User DB insertion failed!");
        if (aBoolean.getFirst())
            alsoAddedNewUser = true;

        final String projectName = projectDTO.getName();
        return Pair.of(true, alsoAddedNewUser ? "The project " + projectName + " was successfully inserted to the system!"
                : "Creating new project " + projectName + " was successfully added to the system!");
    }



    private static Pair<Boolean, String> duplicateCheck(ProjectDTO epDTO) {

        List<ProjectDTO> projectDTOs = ProjectImpl.getAllProject();
        if(projectDTOs != null) {
            System.out.println("Retrieved All users list.");
        }
        for (ProjectDTO dto : projectDTOs) {
            //TODO: in case required, check for more equals with &&
            if (dto.getName().equals(epDTO.getName())) {
                System.out.println("Can't add: existing project!");
                return Pair.of(false, "Can't add: existing project!");
            }
        }
        return Pair.of(true, "project all good :)");
    }
}
