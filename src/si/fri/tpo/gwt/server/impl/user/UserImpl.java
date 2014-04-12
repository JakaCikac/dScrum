package si.fri.tpo.gwt.server.impl.user;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.fill.FillDTO;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserImpl {


    public static Pair<Boolean, String> saveNewUser(Boolean newUser, UserDTO dto) {
        System.out.println("OWARAT sAVAVA userereere");
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
            userDTO = FillDTO.fillUserDTO(u);
            resList.add(userDTO);
        }
        return resList;
    }

    public static UserDTO findUserByUsername(String username) {
        User user;
        user = ProxyManager.getUserProxy().findUserByUsername(username);
        UserDTO userDTO = FillDTO.fillUserDTO(user);
        return userDTO;
    }

    public static Pair<Boolean, String> updateUser(UserDTO userDTO, boolean changedUsername) {
        System.out.println("OMGGMGMGMGM update userereere");
        try {

            // Additional duplication control

            // check for such username in database
            System.out.println("Username to check in base: " + userDTO.getUsername() + " with changed flag " + changedUsername);
            User existingUser = null;
            if (changedUsername) {
                existingUser = ProxyManager.getUserProxy().findUserByUsername(userDTO.getUsername());
            } else existingUser = null;


            if (existingUser != null && changedUsername) {
                System.out.println("Existing user exists!");
                return Pair.of(false, "User with this username already exists!");
            } else System.out.println("User check passed, no existing user");

            User u = ProxyManager.getUserProxy().findUserById(userDTO.getUserId());
            //System.out.println("DTO getUsername = " + userDTO.getUsername());
            //u.setUserId(new Integer(userDTO.getUserId()));
            System.out.println("\n\n u getUserId = " + u.getUserId());
            u.setLastName(userDTO.getLastName());
            System.out.println("u getLastName = " + u.getLastName());
            u.setFirstName(userDTO.getFirstName());
            System.out.println("u getFirstName = " + u.getFirstName());
            u.setEmail(userDTO.getEmail());
            System.out.println("u getEmail = " + u.getEmail());
            u.setTimeCreated(userDTO.getTimeCreated());
            System.out.println("u getTimeCreated = " + u.getTimeCreated());
            u.setUsername(userDTO.getUsername());
            System.out.println("u getUsername = " + u.getUsername());
            u.setSalt(userDTO.getSalt());
            System.out.println("u getSalt = " + u.getSalt());
            u.setIsActive(userDTO.isActive());
            System.out.println("u getIsActive = " + u.getIsActive());
            u.setIsAdmin(userDTO.isAdmin());
            System.out.println("u getIsAdmin = " + u.getIsAdmin());
            u.setPassword(userDTO.getPassword());
            System.out.println("u getPassword = " + u.getPassword());

            System.out.println("userFill completed");
            try {
                if (u == null)
                    return Pair.of(false, "Data error!");
                System.out.println("Calling JPA controller update user, with username " + u.getUsername());
                // TODO: test
                ProxyManager.getUserProxy().edit(u);
                // LALAALAL
                System.out.println("NO UPDATE.");

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "User should be updated, all good..");

    }
}