package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.client.verification.PassHash;

/**
 * Created by nanorax on 07/04/14.
 */
public class UserDataEditForm implements IsWidget{
//TODO: Something :)
    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private TextField username;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private PasswordField password;
    private PasswordField repassword;
    private UserDTO userDTO;
    private boolean changedUsername;

    public UserDataEditForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    public Widget asWidget() {
        if ( vp == null){
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createUserEditForm();
        }
        return vp;
    }


    private void createUserEditForm() {
        this.userDTO = SessionInfo.userDTO;
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("User Edit Form");
        panel.setWidth(350);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        username = new TextField();
        username.setAllowBlank(false);
        p.add(new FieldLabel(username, "Username"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        firstName = new TextField();
        firstName.setAllowBlank(true);
        p.add(new FieldLabel(firstName, "First name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        lastName = new TextField();
        lastName.setAllowBlank(true);
        p.add(new FieldLabel(lastName, "Last name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        email = new TextField();
        email.setAllowBlank(false);
        p.add(new FieldLabel(email, "E-mail"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        password = new PasswordField();
        password.setAllowBlank(true);
        p.add(new FieldLabel(password, "Change Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        repassword = new PasswordField();
        p.add(new FieldLabel(repassword, "Repeat Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        final TextButton saveButton = new TextButton("Save changes");
        saveButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

                boolean save = true;
                // check if password and repassword match
                if (!password.getText().equals("")) {
                    String hashPass = PassHash.getMD5Password(password.getValue());
                    String confirmPass = PassHash.getMD5Password(repassword.getValue());
                    if (!hashPass.equals(confirmPass)) {
                        AlertMessageBox amb = new AlertMessageBox("Password confirmation", "Password and Confirm password fields don't match!");
                        amb.show();
                        repassword.setText("");
                        save = false;
                    }   // if confirm password entered and password empty block data change
                }
                if (password.getText().equals("") && !repassword.getText().equals("")) {
                   save = false;
                   Info.display("Confirm Password enetered", "No password to confirm, please fill out both Password and Confirm password fields.");
                }
                if (!password.getText().equals("") && repassword.getText().equals("")) {
                    save = false;
                    Info.display("Password enetered", "No confirmation password, please fill out both Password and Confirm password fields.");
                }

                changedUsername = false;
                if(save) {
                    AsyncCallback<Pair<Boolean, String>> validationCallback = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result.getFirst()) {
                                final UserDTO saveUserDTO = new UserDTO();
                                saveUserDTO.setUserId(userDTO.getUserId());
                                System.out.println("UserDTO id :" + userDTO.getUserId());
                                if (!username.getText().equals(userDTO.getUsername())) {
                                    changedUsername = true;
                                    saveUserDTO.setUsername(username.getText());
                                } else {
                                    changedUsername = false;
                                    saveUserDTO.setUsername(userDTO.getUsername());
                                }
                        System.out.println("Changed username from " + userDTO.getUsername() + " to " + username.getText() + " and flag is " + changedUsername);
                        saveUserDTO.setEmail(email.getText());
                        if (userDTO.isAdmin())
                                saveUserDTO.setAdmin(true);
                        else saveUserDTO.setAdmin(false);
                        saveUserDTO.setFirstName(firstName.getText());
                        saveUserDTO.setLastName(lastName.getText());
                        saveUserDTO.setActive(true);
                        saveUserDTO.setSalt("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                        saveUserDTO.setTimeCreated(userDTO.getTimeCreated());
                        // hash retrieved password and set it
                        if (password.getText().equals("") && repassword.getText().equals("")) {
                            saveUserDTO.setPassword(userDTO.getPassword());
                        } else {
                            saveUserDTO.setPassword(PassHash.getMD5Password(password.getText()));
                        }
                        // Save user
                        performUpdateUser(saveUserDTO);

                    } else {
                        MessageBox box = new MessageBox("Confirm", "Are you sure you want to do that?");
                        box.show();
                    }
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
                    };
                    //TODO: validate user data
                    service.validateUserData(email.getText(), validationCallback);
                }
            }
        });
        panel.addButton(saveButton);
        vp.add(panel);
        fillUserForm();
    }

    private void fillUserForm(){
        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO result) {
                username.setText(result.getUsername());
                firstName.setText(result.getFirstName());
                lastName.setText(result.getLastName());
                email.setText(result.getEmail());
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findUserByUsername(userDTO.getUsername(), callback);
    }

    private void performUpdateUser(UserDTO userDTO) {

        AsyncCallback<Pair<Boolean, String>> updateUser = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", result.getSecond());
                    amb2.show();
                }
                else {
                    AlertMessageBox amb3 = new AlertMessageBox("Message", result.getSecond());
                    amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        // true, ker vedno dodamo novega userja / admina
        System.out.println("Calling updateUser with changed username flag " + changedUsername);
        service.updateUser(userDTO, changedUsername, updateUser);

    }
}
