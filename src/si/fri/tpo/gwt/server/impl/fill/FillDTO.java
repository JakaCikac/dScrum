package si.fri.tpo.gwt.server.impl.fill;

import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.jpa.User;

/**
 * Created by nanorax on 05/04/14.
 */
public class FillDTO {
    
    public static UserDTO fillUserData(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        // TODO: complete UserDTO fill
        return userDTO;
    }

}


