package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;

/**
 * Created by nanorax on 04/04/14.
 */

@RemoteServiceRelativePath("dscrum")
public interface DScrumService extends RemoteService {

    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash);

}