package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.server.impl.project.ProjectImpl;
import si.fri.tpo.gwt.server.impl.userStory.UserStoryImpl;

import java.util.List;

/**
 * Created by anze on 27. 04. 14.
 */
public class UserStoryServiceImpl {
    public static Pair<Boolean, Integer> saveUserStory(UserStoryDTO userStoryDTO, ProjectDTO projectDTO) {
        // check for duplicates!
        Pair<Boolean, Integer> duplPair = duplicateCheck(userStoryDTO, projectDTO);

        if (duplPair == null || !duplPair.getFirst().booleanValue())
            return duplPair;


        Pair<Boolean, Integer> aBoolean = UserStoryImpl.saveUserStory(userStoryDTO);
        if (aBoolean == null)
            return Pair.of(false, -1);
        if (!aBoolean.getFirst())
            return Pair.of(false, aBoolean.getSecond());

        final String projectName = userStoryDTO.getName();
        return Pair.of(true, aBoolean.getSecond());
    }


    private static Pair<Boolean, Integer> duplicateCheck(UserStoryDTO usDTO, ProjectDTO projectDTO) {

        String usDTOLo = usDTO.getName().toLowerCase();
        List<UserStoryDTO> userStoryDTOs = UserStoryImpl.getAllStoryOfProject(projectDTO);
        if(userStoryDTOs != null) {
            System.out.println("Retrieved All user story list.");
        }
        for (UserStoryDTO dto : userStoryDTOs) {
            //TODO: in case required, check for more equals with &&

            if (dto.getName().toLowerCase().equals(usDTOLo)) {
                System.out.println("Can't add: existing project!");
                return Pair.of(false, -1);
            }
        }
        return Pair.of(true, 0);
    }
}
