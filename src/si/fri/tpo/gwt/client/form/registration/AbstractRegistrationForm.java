package si.fri.tpo.gwt.client.form.registration;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 06/04/14.
 */
public abstract class AbstractRegistrationForm extends LayoutContainer {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private FormData formData;
    private RadioGroup typeOfUserRG = new RadioGroup();
    private TextField<String> searchedUserTF = new TextField<String>();
    private FormPanel simple = new FormPanel();
    private Radio newUserRB = new Radio();
    private Radio existingUserRB = new Radio();

    // Basic info components
    private CheckBox checkbox = new CheckBox();
    private TextField<String> textField = new TextField<String>();
    private NumberField number = new NumberField();
    private RadioGroup group = new RadioGroup();
    private Radio item1 = new Radio();
    private Radio item2 = new Radio();

    // textfeilds
    private TextField<String> email = new TextField<String>();
    private TextField<String> lastName = new TextField<String>();
    private TextField<String> firstName = new TextField<String>();

    // fieldsets
    private FieldSet setBasicData = new FieldSet();

    // buttons
    private Button submitButton = new Button("Register");
    private Button userSearchButton = new Button("Search Users");

    public AbstractRegistrationForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        formData = new FormData("-20");
        vp = new VerticalPanel();
        vp.setSpacing(10);
        initMainRegistrationForm();
        initExistingUserForm();
        add(vp);
    }

    private void initMainRegistrationForm() {
        newUserRB.setName("new_user");
        newUserRB.setValue(true);
        newUserRB.setBoxLabel("New user");

        existingUserRB.setName("existing_user");
        existingUserRB.setBoxLabel("Existing user");

        typeOfUserRG.setFieldLabel("User type");
        typeOfUserRG.setAutoWidth(true);
        typeOfUserRG.add(newUserRB);
        typeOfUserRG.add(existingUserRB);
        typeOfUserRG.addListener(Events.Change, new Listener<FieldEvent>() {
            @Override
            public void handleEvent(FieldEvent fe) {
                if (newUserRB.getValue()) {
                    getUserSearchButton().setEnabled(false);
                    clearAllData();

                } else if (existingUserRB.getValue()) {
                    getUserSearchButton().setEnabled(true);
                }
                searchedUserTF.setValue("");
            }
        });
        simple.add(typeOfUserRG);

        simple.add(searchedUserTF, getFormData());
        userSearchButton.setEnabled(false);
        simple.add(userSearchButton);

        /*
        fillDummyData.addSelectionListener(new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                initDummyData();
            }
        });
         */

        //simple.add(fillDummyData);
    }

    protected void clearAllData() {
        for (Component component : setBasicData.getItems()) {
            disableSingleComponent(component);
        }
    }

    private void disableSingleComponent(Component component) {
        if (component instanceof TextField)
            ((TextField) component).setValue(null);
        else if (component instanceof ComboBox)
            ((ComboBox) component).setValue(null);
    }

    protected void initNewRegistrationForm() {
        getSimple().setHeading("Registration form");
        getSimple().setFrame(true);

        FormLayout layout = new FormLayout();

        getSimple().setButtonAlign(Style.HorizontalAlignment.CENTER);

        setBasicData.setHeading("User information");
        layout.setLabelWidth(100);
        setBasicData.setLayout(layout);

        lastName.setFieldLabel("surname");
        lastName.setAllowBlank(false);
        setBasicData.add(lastName, getFormData());

        firstName.setFieldLabel("Name");
        firstName.setAllowBlank(false);
        setBasicData.add(firstName, getFormData());

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

    protected void initExistingUserForm() {
        searchedUserTF.setWidth(100);
        searchedUserTF.setEnabled(false);
        searchedUserTF.setFieldLabel("Searched user");
    }

    protected void initComponentsDataFill() {

    }

    public VerticalPanel getVp() {
        return vp;
    }

    public FormData getFormData() {
        return formData;
    }

    public DScrumServiceAsync getService() {
        return service;
    }

    public FormPanel getSimple() {
        return simple;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public TextField<String> getSearchedUserTF() {
        return searchedUserTF;
    }

    public Button getUserSearchButton() {
        return userSearchButton;
    }

    public TextField<String> getLastName() {
        return lastName;
    }

    public TextField<String> getFirstName() {
        return firstName;
    }

    public TextField<String> getEmail() {
        return email;
    }

    public FieldSet getSetBasicData() {
        return setBasicData;
    }

    public Radio getNewUserRB() {
        return newUserRB;
    }

    public Radio getExistingUserRB() {
        return existingUserRB;
    }

    @Deprecated
    private void initDummyData() {
        getFirstName().setValue("Denis");
        getLastName().setValue("Bozo");
        getEmail().setValue("denis@bozo.com");
    }

}
