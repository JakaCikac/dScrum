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
        try {
            // Additional duplication control
            if (newUser) {
                // check for such username in database
                User existingUser = ProxyManager.getUserProxy().findUserByUsername(dto.getUsername());

                if (existingUser != null) {
                    System.out.println("Existing user exists!");
                    return Pair.of(false, "User with this username already exists!");
                }

                User u = new User();
                //System.out.println("DTO username = " + dto.getUsername());
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
        try {
            user = ProxyManager.getUserProxy().findUserByUsername(username);
            if (user != null) {
                UserDTO userDTO = FillDTO.fillUserDTO(user);
                return userDTO;
            } else return null;
        } catch (Exception e) {
                System.out.println("Exception while fetching user by username");
                System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static Pair<Boolean, String> updateUser(UserDTO userDTO, boolean changedUsername) {
        try {
            // Additional duplication control
            // check for such username in database
            User existingUser = null;
            if (changedUsername) {
                existingUser = ProxyManager.getUserProxy().findUserByUsername(userDTO.getUsername());
            } else existingUser = null;


            if (existingUser != null && changedUsername) {
                System.out.println("Existing user exists!");
                return Pair.of(false, "User with this username already exists!");
            }

            User u = ProxyManager.getUserProxy().findUserById(userDTO.getUserId());
            //System.out.println("DTO getUsername = " + userDTO.getUsername());
            //u.setUserId(new Integer(userDTO.getUserId()));
            u.setLastName(userDTO.getLastName());
            u.setFirstName(userDTO.getFirstName());
            u.setEmail(userDTO.getEmail());
            u.setTimeCreated(userDTO.getTimeCreated());
            u.setUsername(userDTO.getUsername());
            u.setSalt(userDTO.getSalt());
            u.setIsActive(userDTO.isActive());
            u.setIsAdmin(userDTO.isAdmin());
            u.setPassword(userDTO.getPassword());

            try {
                if (u == null)
                    return Pair.of(false, "Data error!");
                ProxyManager.getUserProxy().edit(u);

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

    public static UserDTO findUserById(int userId) {
        User user;
        try {
            user = ProxyManager.getUserProxy().findUserById(userId);
            if (user != null) {
                UserDTO userDTO = FillDTO.fillUserDTO(user);
                return userDTO;
            } else return null;
        } catch (Exception e) {
            System.out.println("Exception while fetching user by id");
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}