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
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class TeamSelectForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel panel;
    private ListStore<UserDTO> usersStore;
    private ListStore<UserDTO> members;

    public TeamSelectForm(DScrumServiceAsync service) {
        this.service = service;
    }

    public Widget asWidget() {
        if (panel == null) {
            panel = new ContentPanel();
            panel.setWidth(280);
            panel.setHeaderVisible(false);

            VerticalLayoutContainer con = new VerticalLayoutContainer();
            panel.add(con, new MarginData(10));

            usersStore = new ListStore<UserDTO>(getModelKeyProvider());
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
        List<UserDTO> membersList = members.getAll();
        return membersList;
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

}
