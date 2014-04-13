package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.button.TextButton;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.verification.PassHash;

import java.util.ArrayList;


/**
 * Created by nanorax on 06/04/14.
 */

public class UserRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private UserDTO dto;
    private ListBox lb;
    private ArrayList<UserDTO> al;
    private PasswordField password;
    private PasswordField repassword;
    private TextField email;
    private TextField username;
    private TextField lastName;
    private TextField firstName;
    private Radio newUserRB;
    private Radio newAdminRB;


    public UserRegistrationForm(DScrumServiceAsync service) {
        this.service = service;
    }

    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createForm1();
        }
        return vp;
    }

    private void createForm1() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("User Registration Form");
        panel.setWidth(350);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        username = new TextField();
        username.setAllowBlank(false);
        username.setEmptyText("ChuckNorris");
        p.add(new FieldLabel(username, "Username"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        firstName = new TextField();
        firstName.setAllowBlank(true);
        firstName.setEmptyText("Enter your name...");
            /* firstName.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    Info.display("Value Changed", "First name changed to " + event.getValue() == null ? "blank" : event.getValue());
                }
            }); */

        p.add(new FieldLabel(firstName, "Name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        lastName = new TextField();
        lastName.setAllowBlank(false);
        lastName.setEmptyText("Norris");

        p.add(new FieldLabel(lastName, "Surname"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        email = new TextField();
        email.setAllowBlank(false);
        email.setEmptyText("chuck@norris.com");
        p.add(new FieldLabel(email, "Email"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        password = new PasswordField();
        p.add(new FieldLabel(password, "Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        repassword = new PasswordField();
        repassword.setAllowBlank(false);
        p.add(new FieldLabel(repassword, "Confirm Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));


        HorizontalPanel hp;

        newUserRB = new Radio();
        newUserRB.setBoxLabel("New User");

        newAdminRB = new Radio();
        newAdminRB.setBoxLabel("New Admin");
        newAdminRB.setValue(true);

        hp = new HorizontalPanel();
        hp.add(newUserRB);
        hp.add(newAdminRB);

        p.add(new FieldLabel(hp, "User Type"));

        // we can set name on radios or use toggle group
        ToggleGroup toggle = new ToggleGroup();
        toggle.add(newUserRB);
        toggle.add(newAdminRB);
        toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio newUserRB = (Radio) group.getValue();
            }
        });

        final TextButton submitButton = new TextButton("Submit");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // check if password and repassword match
                String hashPass = PassHash.getMD5Password(password.getValue());
                String confirmPass = PassHash.getMD5Password(repassword.getValue());
                if (!hashPass.equals(confirmPass)) {
                    AlertMessageBox amb = new AlertMessageBox("Password confirmation", "Password and Confirm password fields don't match!");
                    amb.show();
                    repassword.setText("");
                } else {
                    AsyncCallback<Pair<Boolean, String>> validationCallback = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result.getFirst()) {
                                final UserDTO userDTO = new UserDTO();
                                userDTO.setUsername(username.getValue());
                                userDTO.setEmail(email.getValue());
                                userDTO.setAdmin(newAdminRB.getValue());
                                userDTO.setFirstName(firstName.getValue());
                                userDTO.setLastName(lastName.getValue());
                                userDTO.setActive(true);
                                userDTO.setSalt("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                                // create time created = now()
                                java.util.Date utilDate = new java.util.Date();
                                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                                userDTO.setTimeCreated(sqlDate);
                                // hash retrieved password and set it
                                userDTO.setPassword(PassHash.getMD5Password(password.getValue()));

                                // Save user
                                performSaveUser(userDTO);

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
                    service.validateUserData(email.getValue(), validationCallback);
                }
            }
        });
        panel.addButton(submitButton);
        vp.add(panel);
    }

    private void setDTO(UserDTO dto) {
         this.dto = dto;
    }

    private void performSaveUser(UserDTO userDTO) {

        AsyncCallback<Pair<Boolean, String>> saveUser = new AsyncCallback<Pair<Boolean, String>>() {
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
        System.out.println("Calling saveUser");
        service.saveUser(userDTO, true, saveUser);
    }
}