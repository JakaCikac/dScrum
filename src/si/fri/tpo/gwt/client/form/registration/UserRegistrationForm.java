package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;


/**
 * Created by nanorax on 06/04/14.
 */

public class UserRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private UserDTO dto = new UserDTO();
    private ListBox lb;
    private ArrayList<UserDTO> al;

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

        TextField username = new TextField();
        username.setAllowBlank(false);
        username.setEmptyText("ChuckNorris");
        p.add(new FieldLabel(username, "Username"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        TextField firstName = new TextField();
        firstName.setAllowBlank(true);
        firstName.setEmptyText("Enter your name...");
            /* firstName.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    Info.display("Value Changed", "First name changed to " + event.getValue() == null ? "blank" : event.getValue());
                }
            }); */

        p.add(new FieldLabel(firstName, "Name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        TextField lastName = new TextField();
        lastName.setAllowBlank(false);
        lastName.setEmptyText("Norris");

        p.add(new FieldLabel(lastName, "Surname"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        TextField email = new TextField();
        email.setAllowBlank(false);
        email.setEmptyText("chuck@norris.com");
        p.add(new FieldLabel(email, "Email"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        PasswordField password = new PasswordField();
        p.add(new FieldLabel(password, "Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        PasswordField repassword = new PasswordField();
        p.add(new FieldLabel(repassword, "Confirm Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));


        HorizontalPanel hp = new HorizontalPanel();

        Radio newUserRB = new Radio();
        newUserRB.setBoxLabel("New User");

        Radio newAdminRB = new Radio();
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
                //Info.display(" ", newUserRB.getBoxLabel());
            }
        });

        panel.addButton(new TextButton("Submit"));

        vp.add(panel);
    }
}

/*
    public UserRegistrationForm(DScrumServiceAsync service) {
        RootPanel.get().add(asWidget());
        //getUserSearchButton().addSelectionListener(userSearchListener);
        //getSubmitButton().addSelectionListener(submitListener);
    }

    public Widget asWidget() {
        initNewRegistrationForm();
        initComponentsDataFill();
        return getVp();
    }

    private UserDTO dto;

   /* private SelectionListener userSearchListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    getSearchedUserTF().setValue(dto.getFirstName() + " " + dto.getLastName());
                    //TODO: get index
                    //fillExistingUserData(dto);
                    setDTO(dto);
                }
            });
        }
    }; */


    // TODO: uncomment
  //  private void setDTO(UserDTO dto) {
   //     this.dto = dto;
   // }

   /* private void fillExistingUserData(UserDTO dto) {
        getFirstName().setValue(dto.getFirstName());
        getLastName().setValue(dto.getLastName());
        getEmail().setValue(dto.getEmail());
    } */

  /*  private SelectionListener submitListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            AsyncCallback<Pair<Boolean, String>> validationCallback = new AsyncCallback<Pair<Boolean, String>>() {
                @Override
                public void onSuccess(Pair<Boolean, String> result) {
                    if (result.getFirst()) {
                        final UserDTO userDTO = new UserDTO();
                        userDTO.setUsername(getUsername().getValue());
                        userDTO.setEmail(getEmail().getValue());
                        userDTO.setAdmin(getNewAdminRB().getValue());
                        userDTO.setFirstName(getFirstName().getValue());
                        userDTO.setLastName(getLastName().getValue());
                        userDTO.setActive(true);
                        userDTO.setSalt("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                        // create time created = now()
                        java.util.Date utilDate = new java.util.Date();
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                        userDTO.setTimeCreated(sqlDate);

                        // hash retrieved password and set it
                        userDTO.setPassword(PassHash.getMD5Password(getPassword().getValue()));

                        // Save user
                        performSaveUser(userDTO);

                } else
                        MessageBox.alert("Warning", result.getSecond(), null);
                }
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
            };
            //TODO: validate user data
            getService().validateUserData(getEmail().getValue(), validationCallback);
        }
    };


    private void performSaveUser(UserDTO userDTO) {

            AsyncCallback<Pair<Boolean, String>> saveUser = new AsyncCallback<Pair<Boolean, String>>() {
                @Override
                public void onSuccess(Pair<Boolean, String> result) {
                    if (result == null)
                        MessageBox.alert("Error!", "Error while saving!", null);
                    else if (!result.getFirst())
                        MessageBox.alert("Error!", result.getSecond(), null);
                    else
                        MessageBox.info("Message", result.getSecond(), null);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
            };
            // TODO: true, ker vedno dodamo novega userja..
            getService().saveUser(userDTO, true, saveUser);
    } */



/* public abstract class UserRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private ToggleGroup typeOfUserRG = new ToggleGroup();
    private TextField searchedUserTF = new TextField();
    private Radio newUserRB = new Radio();
    private Radio newAdminRB = new Radio();

    // Basic info components
    private CheckBox checkbox = new CheckBox();
    private TextField textField = new TextField();
    private ToggleGroup group = new ToggleGroup();
    private Radio item1 = new Radio();
    private Radio item2 = new Radio();

    // textfeilds
    private TextField username = new TextField();
    private TextField email = new TextField();
    private TextField lastName = new TextField();
    private TextField firstName = new TextField();
    private TextField password = new TextField();
    private TextField repassword = new TextField();

    // fieldsets
    private FieldSet setBasicData = new FieldSet();

    // buttons
    private TextButton submitButton = new TextButton("Register");

    public UserRegistrationForm(DScrumServiceAsync service) {
        this.service = service;
        RootPanel.get().add(asWidget());
    }


    public Widget asWidget() {
        vp = new VerticalPanel();
        vp.setSpacing(10);
        initMainRegistrationForm();
        return vp;
    }

    // TODO: zakaj se to ne klicari?
    private void initMainRegistrationForm() {
        System.out.println("New main reg form");
        newUserRB.setName("new_user");
        newUserRB.setValue(true);
        newUserRB.setBoxLabel("New user");

        newAdminRB.setName("new admin");
        newAdminRB.setValue(false);
        newAdminRB.setBoxLabel("New Admin");


        HorizontalPanel hp = new HorizontalPanel();
        hp.add(newUserRB);
        hp.add(newAdminRB);
        vp.add(new FieldLabel(hp, "User type"));

        typeOfUserRG.add(newUserRB);
        typeOfUserRG.add(newAdminRB);
        /* typeOfUserRG.addListener(Events.Change, new Listener<FieldEvent>() {
            @Override
            public void handleEvent(FieldEvent fe) {
                if (newUserRB.getValue()) {
                    clearAllData();
                }
            }
        });

    }

    private void disableSingleComponent(Component component) {
        if (component instanceof TextField)
            ((TextField) component).setValue(null);
        else if (component instanceof ComboBox)
            ((ComboBox) component).setValue(null);
    }

    protected void initNewRegistrationForm() {
        System.out.println("New reg form lalala");
        username.setText("lala");
        vp.add(username);

       /* getSimple().setHeading("Registration form");
        getSimple().setFrame(true);

        getSimple().setButtonAlign(Style.HorizontalAlignment.CENTER);

        setBasicData.setHeading("User information");
        layout.setLabelWidth(100);
        setBasicData.setLayout(layout);

        firstName.setFieldLabel("Name");
        firstName.setAllowBlank(true);
        setBasicData.add(firstName, getFormData());

        lastName.setFieldLabel("Surname");
        lastName.setAllowBlank(true);
        setBasicData.add(lastName, getFormData());

        username.setFieldLabel("Username");
        username.setAllowBlank(false);
        setBasicData.add(username, getFormData());

        password.setMinLength(4);
        password.setPassword(true);
        password.setAllowBlank(false);
        password.setFieldLabel("Password");
        setBasicData.add(password, getFormData());

        getSimple().add(setBasicData);


        email.setFieldLabel("Email address");
        email.setAllowBlank(false);
        setBasicData.add(email, getFormData());

        submitButton.setEnabled(true);
        getSimple().addButton(submitButton);
        getSimple().addButton(new Button("Reset form"));

        FormButtonBinding binding = new FormButtonBinding(getSimple());
        binding.addButton(submitButton);

        getVp().add(getSimple());
    }

    protected void initComponentsDataFill() {}

    public VerticalPanel getVp() {
        return vp;
    }

    public DScrumServiceAsync getService() {
        return service;
    }

    public TextButton getSubmitButton() {
        return submitButton;
    }

    public TextField getLastName() {
        return lastName;
    }

    public TextField getFirstName() {
        return firstName;
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public TextField getEmail() {
        return email;
    }

    public FieldSet getSetBasicData() {
        return setBasicData;
    }

    public Radio getNewUserRB() {
        return newUserRB;
    }

    public Radio getNewAdminRB() {
        return newAdminRB;
    }


} */
