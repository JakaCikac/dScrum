package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.fill.FillDTO;
import si.fri.tpo.gwt.server.impl.user.UserImpl;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.List;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserRegistrationServiceImpl {

    public static Pair<Boolean, String> saveUser(UserDTO userDTO, Boolean newUser) {
        boolean alsoAddedNewUser = false;

        // check for duplicates!
        if (!newUser) {
            Pair<Boolean, String> duplPair = duplicateCheck(userDTO);

            if (duplPair == null || !duplPair.getFirst().booleanValue())
                return duplPair;
        }

        //TODO: UserImpl
        Pair<Boolean, String> aBoolean = UserImpl.saveNewUser(newUser, userDTO);
        if (aBoolean == null)
            return Pair.of(false, "User DB insertion failed!");
        if (aBoolean.getFirst())
            alsoAddedNewUser = true;

        final String userID = String.valueOf(userDTO.getUserId());
        return Pair.of(true, alsoAddedNewUser ? "The user " + userID + " was successfully inserted to the system!"
                : "Creating new user " + userID + " was successfully added to the system!");
        //return saveOnlyUser(userDTO, alsoAddedNewUser);
    }

    private static Pair<Boolean, String> duplicateCheck(UserDTO esDTO) {
        if (esDTO.getUserId() != null) {
            List<UserDTO> userDTOs = UserImpl.getAllUsers();
            for (UserDTO dto : userDTOs) {
                //TODO: in case required, check for more equals with &&
                if (dto.getUsername().equals(esDTO.getUsername()))
                    return Pair.of(false, "Can't add: existing user!");
            }
        }
        return Pair.of(true, "all good :)");
    }

    /* private static Pair<Boolean, String> saveOnlyUser(UserDTO userDTO, Boolean alsoAddedNewUser) {
        try {
            //TODO: fill entity
            User user = FillEntity.fillUserData(userDTO);
            if (user == null)
                return Pair.of(false, "Data error!");
            ProxyManager.getUserProxy().create(user);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return Pair.of(false, e.getMessage());
        }
        final String userID = String.valueOf(userDTO.getUserId());
        return Pair.of(true, alsoAddedNewUser ? "The user " + userID + " was successfully inserted to the system!"
                : "Creating new user " + userID + " was successfully added to the system!");
    } */
}
