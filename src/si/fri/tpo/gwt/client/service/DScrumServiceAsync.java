package si.fri.tpo.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;

/**
 * Created by nanorax on 04/04/14.
 */
public interface DScrumServiceAsync {

    void performUserLogin(String username, String passwordHash, AsyncCallback<Pair<UserDTO, String>> async);

    void dummyCharacterTrigger(Character b, AsyncCallback<Character> callback);

    void dummyUserTrigger(UserDTO userDTO, AsyncCallback<UserDTO> callback3);

    void validateUserData(String emailValue, AsyncCallback<Pair<Boolean, String>> validationCallback);

    void saveUser(UserDTO userDTO, Boolean value, AsyncCallback<Pair<Boolean, String>> saveUser);

    void findUserByUsername(String username, AsyncCallback<UserDTO> callback);
}

