package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.server.impl.userStory.UserStoryImpl;

import java.util.List;

/**
 * Created by anze on 27. 04. 14.
 */
public class UserStoryServiceImpl {
    public static Pair<Boolean, String> saveUserStory(UserStoryDTO userStoryDTO, ProjectDTO projectDTO) {
        // check for duplicates!
        Pair<Boolean, String> duplPair = duplicateCheck(userStoryDTO, projectDTO);

        if (duplPair == null || !duplPair.getFirst().booleanValue())
            return duplPair;


        Pair<Boolean, String> aBoolean = UserStoryImpl.saveUserStory(userStoryDTO);
        if (aBoolean == null)
            return Pair.of(false, "User story database insertion failed miserably!");
        if (!aBoolean.getFirst())
            return Pair.of(false, aBoolean.getSecond());

        final String projectName = userStoryDTO.getName();
        return Pair.of(true, aBoolean.getSecond());
    }


    private static Pair<Boolean, String> duplicateCheck(UserStoryDTO usDTO, ProjectDTO projectDTO) {

        String usDTOLo = usDTO.getName().toLowerCase();
        List<UserStoryDTO> userStoryDTOs = UserStoryImpl.getAllStoryOfProject(projectDTO);
        if(userStoryDTOs != null) {
            System.out.println("Retrieved All user story list.");
        }
        for (UserStoryDTO dto : userStoryDTOs) {
            //TODO: in case required, check for more equals with &&

            if (dto.getName().toLowerCase().equals(usDTOLo)) {
                return Pair.of(false, "Can't add: existing project!");
            }
        }
        return Pair.of(true, "");
    }
}
