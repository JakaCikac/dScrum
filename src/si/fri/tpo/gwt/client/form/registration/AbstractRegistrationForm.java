package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.CheckBox;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;



/**
 * Created by nanorax on 06/04/14.
 */
public abstract class AbstractRegistrationForm implements IsWidget {

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

    public AbstractRegistrationForm(DScrumServiceAsync service) {
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
        }); */

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

        getVp().add(getSimple()); */
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


}
