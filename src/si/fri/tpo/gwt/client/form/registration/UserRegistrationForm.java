package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
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
    private ContentPanel center;
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
    private HorizontalPanel hp;


    public UserRegistrationForm(DScrumServiceAsync service, ContentPanel center) {
        this.service = service;
        this.center = center;
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
        username.setEmptyText("Enter username...");
        p.add(new FieldLabel(username, "Username *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        firstName = new TextField();
        firstName.setEmptyText("Enter your name...");
            /* firstName.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    Info.display("Value Changed", "First name changed to " + event.getValue() == null ? "blank" : event.getValue());
                }
            }); */
        p.add(new FieldLabel(firstName, "Name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        lastName = new TextField();
        lastName.setEmptyText("Enter your surname...");
        p.add(new FieldLabel(lastName, "Surname"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        email = new TextField();
        email.setAllowBlank(false);
        email.setEmptyText("local-part@domain");
        email.addValidator(new RegExValidator("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", "Email format must be local-part@domain."));
        p.add(new FieldLabel(email, "Email *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        password = new PasswordField();
        password.setAllowBlank(false);
        p.add(new FieldLabel(password, "Password *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        repassword = new PasswordField();
        repassword.setAllowBlank(false);
        p.add(new FieldLabel(repassword, "Confirm Password *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

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
                AsyncCallback<Pair<Boolean, String>> validationCallback = new AsyncCallback<Pair<Boolean, String>>() {
                    @Override
                    public void onSuccess(Pair<Boolean, String> result) {
                        String hashPass = null, confirmPass = null;
                        AlertMessageBox amb;

                        // check if email address is valid
                        RegExp emailRegExp = RegExp.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                        MatchResult emailMatcher = emailRegExp.exec(email.getValue());

                        // This if is for validation, but is unused (always true).
                        if (result.getFirst()) {
                            final UserDTO userDTO = new UserDTO();
                            if (username.getValue() == null) {
                                amb = new AlertMessageBox("Empty Username", "Please enter username!");
                                amb.show();
                                return;
                            } else userDTO.setUsername(username.getValue());

                            if (email.getValue() == null) {
                                amb = new AlertMessageBox("Empty Email", "Please enter your email!");
                                amb.show();
                                return;
                            } else if (emailMatcher == null) {
                                amb = new AlertMessageBox("Invalid email format", "Please enter correct email address!");
                                amb.show();
                                return;
                            } else userDTO.setEmail(email.getValue());

                            userDTO.setAdmin(newAdminRB.getValue());
                            userDTO.setFirstName(firstName.getValue());
                            userDTO.setLastName(lastName.getValue());
                            userDTO.setActive(true);
                            userDTO.setSalt("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                            // create time created = now()
                            java.util.Date utilDate = new java.util.Date();
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                            userDTO.setTimeCreated(sqlDate);

                            // check if password and repassword match
                            if (password.getValue() == null) {
                                amb = new AlertMessageBox("Empty Password", "Please enter password!");
                                amb.show();
                                return;
                            } else hashPass = PassHash.getMD5Password(password.getText());
                            if (repassword.getValue() == null) {
                                amb = new AlertMessageBox("Empty Confirm Password", "Please enter confirm password!");
                                amb.show();
                                return;
                            } else confirmPass = PassHash.getMD5Password(repassword.getText());
                            if (!hashPass.equals(confirmPass)) {
                                amb = new AlertMessageBox("Password confirmation", "Password and Confirm password fields don't match!");
                                amb.show();
                                repassword.setText("");
                                return;
                            }
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
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing user saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving user!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message save User", result.getSecond());
                    amb3.show();
                    center.clear();
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