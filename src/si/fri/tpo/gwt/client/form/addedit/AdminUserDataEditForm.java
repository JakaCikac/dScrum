package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.home.NorthForm;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchCallback;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchDialog;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.client.verification.PassHash;

/**
 * Created by nanorax on 07/04/14.
 */
public class AdminUserDataEditForm implements IsWidget{
//TODO: Something :)
    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private TextField username;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private PasswordField password;
    private PasswordField repassword;
    private TextField selectedUser;
    private TextButton deleteUserButton;
    private Radio userRB;
    private Radio adminRB;
    private Radio activeRB;
    private Radio inactiveRB;

    private UserDTO userDTO;
    private boolean changedUsername;
    private TextButton selectUser;
    private ContentPanel center, north, south, east, west;

    public AdminUserDataEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel north, ContentPanel south, ContentPanel east, ContentPanel west) {
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
            createAdminUserEditForm();
        }
        return vp;
    }

    private void createAdminUserEditForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("User Edit Form");
        panel.setWidth(350);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        VerticalPanel userControl;
        HorizontalPanel selectUserPanel = new HorizontalPanel();

        selectUser = new TextButton("Select User");
        selectUser.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                SingleUserSearchDialog sus = new SingleUserSearchDialog(service, new SingleUserSearchCallback() {
                    @Override
                    public void userSearchCallback(UserDTO userDTO) {
                        setUserDTO(userDTO);
                        selectedUser.setText(userDTO.getUsername());
                        fillUserForm(userDTO);
                    }
                }, center, west, east, north, south);
                sus.show();
            }
        });
        selectUserPanel.add(selectUser);
        p.add(selectUserPanel);

        selectedUser = new TextField();
        selectedUser.setAllowBlank(false);
        selectedUser.setReadOnly(true);
        selectUserPanel.add(selectedUser);
        p.add(selectUserPanel);

        userControl = new VerticalPanel();
        deleteUserButton = new TextButton("Delete user");
        deleteUserButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (userDTO != null) {
                    Info.display("Delete notice", "The user will not be deleted from the database, but his status will be set to inactive.");
                    activeRB.setValue(false);
                    inactiveRB.setValue(true);
                    updateUserCall();
                    Info.display("Resetting form.", "The form has been reset.");
                } else Info.display("No selected user", "There is no user select, please select one.");
            }
        });
        userControl.add(deleteUserButton);
        p.add(userControl);

        userControl = new VerticalPanel();
        HorizontalPanel hp;

        userRB = new Radio();
        userRB.setBoxLabel("Is User");
        userRB.setValue(true);

        adminRB = new Radio();
        adminRB.setBoxLabel("Is Admin");

        hp = new HorizontalPanel();
        hp.add(userRB);
        hp.add(adminRB);
        userControl.add(hp);
        userControl.add(new FieldLabel(hp, "User Type"));
        p.add(userControl);

        // we can set name on radios or use toggle group
        ToggleGroup toggle = new ToggleGroup();
        toggle.add(userRB);
        toggle.add(adminRB);
        toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio adminRBG = (Radio) group.getValue();
            }
        });

        activeRB = new Radio();
        activeRB.setBoxLabel("Is Active");
        activeRB.setValue(true);

        inactiveRB = new Radio();
        inactiveRB.setBoxLabel("Is Inactive");

        hp = new HorizontalPanel();
        hp.add(activeRB);
        hp.add(inactiveRB);
        userControl.add(hp);
        userControl.add(new FieldLabel(hp, "Is user active?"));
        p.add(userControl);

        // we can set name on radios or use toggle group
        ToggleGroup toggle1 = new ToggleGroup();
        toggle1.add(activeRB);
        toggle1.add(inactiveRB);
        toggle1.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio activeRBG = (Radio) group.getValue();
            }
        });

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
                updateUserCall();
            }
        });
        panel.addButton(saveButton);
        vp.add(panel);

    }

    private void fillUserForm(UserDTO userDTO){
        AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
            @Override
            public void onSuccess(UserDTO result) {
                username.setText(result.getUsername());
                firstName.setText(result.getFirstName());
                lastName.setText(result.getLastName());
                email.setText(result.getEmail());
                if (result.isAdmin()) {
                    adminRB.setValue(true);
                    userRB.setValue(false);
                } else {
                    userRB.setValue(true);
                    adminRB.setValue(false);
                }
                if (result.isActive()) {
                    activeRB.setValue(true);
                    inactiveRB.setValue(false);
                } else {
                    inactiveRB.setValue(true);
                    activeRB.setValue(false);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findUserByUsername(userDTO.getUsername(), callback);
    }

    private void emptyForm() {
        username.setValue("");
        password.setValue("");
        repassword.setValue("");
        selectedUser.setValue("");
        firstName.setValue("");
        lastName.setValue("");
        email.setValue("");
    }

    private void performUpdateUser(UserDTO userDTO) {

        AsyncCallback<Pair<Boolean, String>> updateUser = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while user updating!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating user!", result.getSecond());
                    amb2.show();
                }
                else {
                    SessionInfo.projectDTO = null;
                    north.clear();
                    west.clear();
                    east.clear();
                    center.clear();
                    NorthForm nf = new NorthForm(service, center, north, south, east, west);
                    north.add(nf.asWidget());
                    if (SessionInfo.userDTO.isAdmin()) {
                        AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                        east.add(adminNavPanel.asWidget());
                    } else {
                        UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                        east.add(userNavPanel.asWidget());
                    }
                    ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                    west.add(psf.asWidget());
                    UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                    center.add(userHomeForm.asWidget());
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

    private void updateUserCall() {
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
        if(getUserDTO() != null) {
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
                        //System.out.println("Changed username from " + username.getText() + " to " + username.getText());
                        if (email.getText().equals("")) {
                            amb = new AlertMessageBox("Empty Email", "Please enter your email!");
                            amb.show();
                            return;
                        } else saveUserDTO.setEmail(email.getText());
                        saveUserDTO.setFirstName(firstName.getText());
                        saveUserDTO.setLastName(lastName.getText());
                        saveUserDTO.setActive(activeRB.getValue());
                        saveUserDTO.setAdmin(adminRB.getValue());
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
                        center.clear(); //TODO: when home page (wall, sprint backlog etc) create as widget on center, till then just clear.

                    } else {
                        MessageBox box = new MessageBox("Select user", "Maybe select a user first, yes?");
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
        } else {
            Info.display("No user selected", "Please Select User.");
        }
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
