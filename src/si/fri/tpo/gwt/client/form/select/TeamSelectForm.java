package si.fri.tpo.gwt.client.form.select;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class TeamSelectForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel panel;
    private ListStore<UserDTO> usersStore;
    private ListStore<UserDTO> members;
    private boolean fillData;
    private List<UserDTO> projecUserList;

    public TeamSelectForm(DScrumServiceAsync service, boolean fillData) {
        this.service = service;
        this.fillData = fillData;
    }

    public Widget asWidget() {
        if (panel == null) {
            panel = new ContentPanel();
            panel.setWidth(280);
            panel.setHeaderVisible(false);

            VerticalLayoutContainer con = new VerticalLayoutContainer();
            panel.add(con, new MarginData(10));

            usersStore = new ListStore<UserDTO>(getModelKeyProvider());

            if (fillData)
            getUserList();

             members = new ListStore<UserDTO>(getModelKeyProvider());

            final DualListField<UserDTO, String> field = new DualListField<UserDTO, String>(usersStore, members, getUsernameValue(),
                    new TextCell());
            field.addValidator(new EmptyValidator<List<UserDTO>>());
            field.setEnableDnd(true);
            field.setMode(DualListField.Mode.INSERT);

            con.add(field);
        }
        return panel;
    }

    public ListStore<UserDTO> getUsersStore() {
        return usersStore;
    }

    public List<UserDTO> getMembers() {
        List<UserDTO> returnUsers = new ArrayList<UserDTO>();
        for (UserDTO userDTO : members.getAll()) {
            UserDTO tempDTO = new UserDTO();
            tempDTO.setAdmin(userDTO.isAdmin());
            tempDTO.setActive(userDTO.isActive());
            tempDTO.setUsername(userDTO.getUsername());
            tempDTO.setLastName(userDTO.getLastName());
            tempDTO.setFirstName(userDTO.getFirstName());
            tempDTO.setSalt(userDTO.getSalt());
            tempDTO.setEmail(userDTO.getEmail());
            tempDTO.setPassword(userDTO.getPassword());
            tempDTO.setTimeCreated(userDTO.getTimeCreated());
            tempDTO.setUserId(userDTO.getUserId());
            returnUsers.add(tempDTO);
        }
        return returnUsers;
    }

    public void setMembers(List<UserDTO> userList) {
        members.replaceAll(userList);
    }

    public void removeUsersFromUserList(List<UserDTO> userListDTO, List<UserDTO> oldList) {
        List<UserDTO> newList = new ArrayList<UserDTO>();
        for (UserDTO dto : usersStore.getAll()) {
            UserDTO tempDTO = new UserDTO();
            tempDTO.setAdmin(dto.isAdmin());
            tempDTO.setActive(dto.isActive());
            tempDTO.setUsername(dto.getUsername());
            tempDTO.setLastName(dto.getLastName());
            tempDTO.setFirstName(dto.getFirstName());
            tempDTO.setSalt(dto.getSalt());
            tempDTO.setEmail(dto.getEmail());
            tempDTO.setPassword(dto.getPassword());
            tempDTO.setTimeCreated(dto.getTimeCreated());
            tempDTO.setUserId(dto.getUserId());
            oldList.add(tempDTO);
            }
        for (int i = 0; i < oldList.size(); i++) {
            boolean addToList = true;
            for(int j = 0; j < userListDTO.size(); j++) {
                if ((oldList.get(i).getUsername()).equals(userListDTO.get(j).getUsername())) {
                    addToList = false;
                }
            }
            if (addToList)
                newList.add(oldList.get(i));
        }
        usersStore.addAll(newList);
    }

    public void displayAllUsers() {
        usersStore.clear();
        getUserList();
    }

    private void getUserList() {
        AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onSuccess(List<UserDTO> result) {
                // add list to ListStore
                usersStore.addAll(result);
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findAllUsers(callback);
    }

    public void setUserList(List<UserDTO> userListDTO) {
        setProjecUserList(userListDTO);
        AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onSuccess(List<UserDTO> result) {
                // add list to ListStore
                removeUsersFromUserList(getProjecUserList(), result);
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findAllUsers(callback);
    }

    // return the model key provider for the list store
    private ModelKeyProvider<UserDTO> getModelKeyProvider() {
        ModelKeyProvider<UserDTO> mkp = new ModelKeyProvider<UserDTO>() {
            @Override
            public String getKey(UserDTO item) {
                return item.getUsername();
            }
        };
        return mkp;
    }

    private ValueProvider<UserDTO, String> getUsernameValue() {
        ValueProvider<UserDTO, String> vpu = new ValueProvider<UserDTO, String>() {
            @Override
            public String getValue(UserDTO object) {
                return object.getUsername();
            }
            @Override
            public void setValue(UserDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpu;
    }

    private ValueProvider<UserDTO, String> getFirstNameValue() {
        ValueProvider<UserDTO, String> vpfn = new ValueProvider<UserDTO, String>() {
            @Override
            public String getValue(UserDTO object) {
                return object.getFirstName();
            }
            @Override
            public void setValue(UserDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpfn;
    }
    private ValueProvider<UserDTO, String> getLastNameValue() {
        ValueProvider<UserDTO, String> vpln = new ValueProvider<UserDTO, String>() {
            @Override
            public String getValue(UserDTO object) {
                return object.getLastName();
            }
            @Override
            public void setValue(UserDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpln;
    }


    public List<UserDTO> getProjecUserList() {
        return projecUserList;
    }

    public void setProjecUserList(List<UserDTO> projecUserList) {
        this.projecUserList = projecUserList;
    }

}
