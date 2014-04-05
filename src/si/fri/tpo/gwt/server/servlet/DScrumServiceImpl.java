package si.fri.tpo.gwt.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.server.impl.login.LoginServiceImpl;

/**
 * Created by nanorax on 04/04/14.
 */
public class DScrumServiceImpl extends RemoteServiceServlet implements DScrumService {

    // TODO: ker ne returna pravilne zadeve -> crash
   /* @Override
    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash) {
        return LoginServiceImpl.performUserLogin(username, passwordHash);
    } */
}
