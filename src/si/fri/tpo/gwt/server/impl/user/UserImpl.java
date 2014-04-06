package si.fri.tpo.gwt.server.impl.user;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.fill.FillDTO;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserImpl {


    public static Pair<Boolean, String> saveNewUser(Boolean newStudent, UserDTO dto) {
        try {

            // Additional duplication control
            if (newStudent) {
                // check for such username in database
                User existingUser = ProxyManager.getUserProxy().findUserByUsername(dto.getUsername());

                if (existingUser != null) {
                    return Pair.of(false, "User with this username already exists!");
                }

                User u = new User();
                u.setLastName(dto.getLastName());
                u.setFirstName(dto.getFirstName());
                u.setEmail(dto.getEmail());
                //TODO: finnish filling user from DTO and create in database

                ProxyManager.getUserProxy().create(u);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "");
    }

    // Return all users by id
    public static List<UserDTO> getAllUsers() {
        //Get user list
        List<User> userList;
        // retrieve user list from JPA controller
        userList = ProxyManager.getUserProxy().getUsersList();
        ArrayList<UserDTO> resList = new ArrayList<UserDTO>();
        UserDTO userDTO;
        for (User u : userList) {
            userDTO = new UserDTO();
            userDTO = FillDTO.fillUserData(u);
            resList.add(userDTO);
        }
        // TODO: Check if this returns the right stuff :)
        return resList;
    }

}