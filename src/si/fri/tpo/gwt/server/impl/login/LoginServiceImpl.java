package si.fri.tpo.gwt.server.impl.login;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.fill.FillDTO;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

/**
 * Created by nanorax on 04/04/14.
 */
public class LoginServiceImpl {

    public static Pair<UserDTO, String> performUserLogin(String username, String passwordHash) {
         User user = ProxyManager.getUserProxy().findUserByUsername(username);

        if (user == null)
            return Pair.of(null, "Username with this username does not exist!");

        // verify password
        final boolean attempt = user.getPassword().equals(passwordHash);

        if (!attempt)
            return Pair.of(null, "Wrong password!");

        return Pair.of(FillDTO.fillUserDTO(user), "success");
    }

}
