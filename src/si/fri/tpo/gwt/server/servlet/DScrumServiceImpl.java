package si.fri.tpo.gwt.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.server.impl.login.LoginServiceImpl;
import si.fri.tpo.gwt.server.impl.registration.UserRegistrationServiceImpl;
import si.fri.tpo.gwt.server.impl.user.UserImpl;

import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */
public class DScrumServiceImpl extends RemoteServiceServlet implements DScrumService {

    @Override
    public Pair<UserDTO, String> performUserLogin(String username, String passwordHash) {
        return LoginServiceImpl.performUserLogin(username, passwordHash);
    }

    @Override
    public Character dummyCharacterTrigger(Character b) {
        return new Character('A');
    }

    @Override
    public UserDTO dummyUserTrigger(UserDTO userDTO) {
        return new UserDTO();
    }

    @Override
    public Pair<Boolean, String> validateUserData(String emailValue) {

        //if (!Validate.isEmail(emailValue)) {
            // getEmail().focus();
        //    return Pair.of(false, "Email not valid!");
        //}

        return Pair.of(true, "");
    }

    @Override
    public Pair<Boolean, String> saveUser(UserDTO userDTO, Boolean isNew) {
        return UserRegistrationServiceImpl.saveUser(userDTO, isNew);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return UserImpl.findUserByUsername(username);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return UserImpl.getAllUsers();
    }

    @Override
    public Pair<Boolean, String> updateUser(UserDTO userDTO, boolean b) {
        return UserImpl.updateUser(userDTO, b);
    }

}
