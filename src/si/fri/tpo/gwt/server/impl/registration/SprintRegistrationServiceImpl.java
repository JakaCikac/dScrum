package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.server.impl.project.ProjectImpl;
import si.fri.tpo.gwt.server.impl.sprint.SprintImpl;

import java.util.List;

/**
 * Created by Administrator on 15-Apr-14.
 */
public class SprintRegistrationServiceImpl {

    public static Pair<Boolean, String> saveSprint(SprintDTO sprintDTO) {
        boolean alsoAddedNewUser = false;

        // check for duplicates!
        Pair<Boolean, String> duplPair = duplicateCheck(sprintDTO);

        if (duplPair == null || !duplPair.getFirst().booleanValue())
            return duplPair;


        Pair<Boolean, String> aBoolean = SprintImpl.saveNewSprint(sprintDTO);
        if (aBoolean == null)
            return Pair.of(false, "User DB insertion failed!");
        if (aBoolean.getFirst())
            alsoAddedNewUser = true;

        final int sprintID = sprintDTO.getSprintPK().getSprintId();
        return Pair.of(true, alsoAddedNewUser ? "The project " + sprintID + " was successfully inserted to the system!"
                : "Creating new project " + sprintID + " was successfully added to the system!");
    }



    private static Pair<Boolean, String> duplicateCheck(SprintDTO epDTO) {

        /*List<SprintDTO> sprintDTOs = SprintImpl.getAllSprint();
        if(sprintDTOs != null) {
            System.out.println("Retrieved All project list.");
        }
        for (SprintDTO dto : sprintDTOs) {
            //TODO: in case required, check for more equals with &&
            if (dto.getName().equals(epDTO.getName())) {
                System.out.println("Can't add: existing project!");
                return Pair.of(false, "Can't add: existing project!");
            }
        }*/
        return Pair.of(true, "project all good :)");
    }
}
