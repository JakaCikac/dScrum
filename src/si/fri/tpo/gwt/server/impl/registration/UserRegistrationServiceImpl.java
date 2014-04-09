package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.user.UserImpl;

import java.util.List;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserRegistrationServiceImpl {

    public static Pair<Boolean, String> saveUser(UserDTO userDTO, Boolean newUser) {
        boolean alsoAddedNewUser = false;

        // check for duplicates!
        if (newUser) {
            Pair<Boolean, String> duplPair = duplicateCheck(userDTO);

            if (duplPair == null || !duplPair.getFirst().booleanValue())
                return duplPair;
        }

        Pair<Boolean, String> aBoolean = UserImpl.saveNewUser(newUser, userDTO);
        if (aBoolean == null)
            return Pair.of(false, "User DB insertion failed!");
        if (aBoolean.getFirst())
            alsoAddedNewUser = true;

        final String userID = userDTO.getUsername();
        return Pair.of(true, alsoAddedNewUser ? "The user " + userID + " was successfully inserted to the system!"
                : "Creating new user " + userID + " was successfully added to the system!");
    }

    private static Pair<Boolean, String> duplicateCheck(UserDTO esDTO) {

            List<UserDTO> userDTOs = UserImpl.getAllUsers();
            if(userDTOs != null) {
                System.out.println("Retrieved All users list.");
            }
            for (UserDTO dto : userDTOs) {
                //TODO: in case required, check for more equals with &&
                if (dto.getUsername().equals(esDTO.getUsername())) {
                    System.out.println("Can't add: existing user!");
                    return Pair.of(false, "Can't add: existing user!");
                }
            }
        return Pair.of(true, "all good :)");
    }
}
