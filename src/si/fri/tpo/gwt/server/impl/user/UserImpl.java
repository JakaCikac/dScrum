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


    public static Pair<Boolean, String> saveNewUser(Boolean newUser, UserDTO dto) {
        try {

            // Additional duplication control
            if (newUser) {
                // check for such username in database
                System.out.println("Username to check in base: " + dto.getUsername());
                User existingUser = ProxyManager.getUserProxy().findUserByUsername(dto.getUsername());;

                if (existingUser != null) {
                    System.out.println("Existing user exists!");
                    return Pair.of(false, "User with this username already exists!");
                } else System.out.println("User check passed, no existing user");

                User u = new User();
                System.out.println("DTO username = " + dto.getUsername());
                u.setLastName(dto.getLastName());
                u.setFirstName(dto.getFirstName());
                u.setEmail(dto.getEmail());
                u.setTimeCreated(dto.getTimeCreated());
                u.setUsername(dto.getUsername());
                u.setSalt(dto.getSalt());
                u.setIsActive(dto.isActive());
                u.setIsAdmin(dto.isAdmin());
                u.setPassword(dto.getPassword());
                try {
                    if (u == null)
                        return Pair.of(false, "Data error!");

                    ProxyManager.getUserProxy().create(u);

                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    return Pair.of(false, e.getMessage());
                }
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