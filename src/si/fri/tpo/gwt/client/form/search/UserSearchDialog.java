package si.fri.tpo.gwt.client.form.search;

import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserSearchDialog extends Dialog {
    private UserSearchForm userSearch;
    private UserSearchCallback callback;

    public UserSearchDialog(DScrumServiceAsync service, UserSearchCallback callback) {
        this.callback = callback;
        userSearch = new UserSearchForm(service);
        add(userSearch);

        setButtons(YESNO);
        setWidth(445);
        setHeading("User search");
        getButtonById("yes").setText("Confirm");
        getButtonById("yes").setEnabled(false);
        getButtonById("no").setText("Cancel");

        userSearch.getGrid().getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<UserDTO>() {
            @Override
            public void selectionChanged(SelectionChangedEvent<UserDTO> selectionEvent) {
                getButtonById("yes").setEnabled(selectionEvent.getSelectedItem() != null);
            }
        });

        show();
    }

    @Override
    protected void onButtonPressed(Button button) {
        if (button.getItemId().equals("yes")) {
            final UserDTO userDTO = userSearch.getGrid().getSelectionModel().getSelectedItem();
            callback.userSearchCallback(userDTO);
        }
        super.onButtonPressed(button);
        hide();
    }
}
