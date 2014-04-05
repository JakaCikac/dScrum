package si.fri.tpo.gwt.server.impl.login;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.fill.FillDTO;
import si.fri.tpo.jpa.User;
import si.fri.tpo.proxy.ProxyManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

        return Pair.of(FillDTO.fillUserData(user), "success");
    }

}
