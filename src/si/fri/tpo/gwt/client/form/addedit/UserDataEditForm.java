package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.client.verification.PassHash;

/**
 * Created by nanorax on 07/04/14.
 */
public class UserDataEditForm implements IsWidget{

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
    private ContentPanel center, north, south, east, west;

    public UserDataEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel north, ContentPanel south, ContentPanel east, ContentPanel west) {
        this.service = service;
        this.center = center;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
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
        p.add(new FieldLabel(username, "Username *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        firstName = new TextField();
        firstName.setAllowBlank(true);
        p.add(new FieldLabel(firstName, "First name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        lastName = new TextField();
        lastName.setAllowBlank(true);
        p.add(new FieldLabel(lastName, "Last name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        email = new TextField();
        email.setAllowBlank(false);
        email.addValidator(new RegExValidator("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", "chuck@norris.com"));
        p.add(new FieldLabel(email, "E-mail *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

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
                if (password.getText().equals("") && !repassword.getText().equals("")) {
                    Info.display("Confirm Password enetered", "No password to confirm, please fill out both Password and Confirm password fields.");
                    return;
                }
                if (!password.getText().equals("") && repassword.getText().equals("")) {
                    Info.display("Password enetered", "No confirmation password, please fill out both Password and Confirm password fields.");
                    return;
                }
                if (!password.getText().equals("")) {
                    String hashPass = PassHash.getMD5Password(password.getValue());
                    String confirmPass = PassHash.getMD5Password(repassword.getValue());
                    if (!hashPass.equals(confirmPass)) {
                        AlertMessageBox amb = new AlertMessageBox("Password confirmation", "Password and Confirm password fields don't match!");
                        amb.show();
                        repassword.setText("");
                        return;
                    }   // if confirm password entered and password empty block data change
                }

                changedUsername = false;

                AsyncCallback<Pair<Boolean, String>> validationCallback = new AsyncCallback<Pair<Boolean, String>>() {
                    @Override
                    public void onSuccess(Pair<Boolean, String> result) {
                        AlertMessageBox amb;
                        if (result.getFirst()) {
                            final UserDTO saveUserDTO = new UserDTO();
                            saveUserDTO.setUserId(userDTO.getUserId());
                            //System.out.println("UserDTO id :" + userDTO.getUserId());
                            if (username.getText().equals("")) {
                                amb = new AlertMessageBox("Empty Username", "Please enter username!");
                                amb.show();
                                return;
                            }
                            if (!username.getText().equals(userDTO.getUsername())) {
                                changedUsername = true;
                                saveUserDTO.setUsername(username.getText());
                            } else {
                                changedUsername = false;
                                saveUserDTO.setUsername(userDTO.getUsername());
                            }
                            //System.out.println("Changed username from " + userDTO.getUsername() + " to " + username.getText() + " and flag is " + changedUsername);

                            if (email.getText().equals("")) {
                                amb = new AlertMessageBox("Empty Email", "Please enter your email!");
                                amb.show();
                                return;
                            } else saveUserDTO.setEmail(email.getText());

                            saveUserDTO.setAdmin(userDTO.isAdmin());
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
                            // Update user
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
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while updating user!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message", result.getSecond());
                    amb3.show();
                    UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                    center.add(userHomeForm.asWidget());
                    west.clear();
                    east.clear();
                    SessionInfo.projectDTO = null;
                    if (SessionInfo.userDTO.isAdmin()){
                        AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                        east.add(adminNavPanel.asWidget());
                    } else {
                        UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                        east.add(userNavPanel.asWidget());
                    }
                    ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                    west.add(psf.asWidget());
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        // true, ker vedno dodamo novega userja / admina
        //System.out.println("Calling updateUser with changed username flag " + changedUsername);
        service.updateUser(userDTO, changedUsername, updateUser);

    }
}
