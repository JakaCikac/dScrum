package si.fri.tpo.gwt.client.form.registration;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Element;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 07/04/14.
 */
public abstract class AbstractProjectRegistrationForm extends LayoutContainer {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private FormData formData;
    private RadioGroup typeOfProjectRG = new RadioGroup();
    private TextField<String> searchedUserTF = new TextField<String>();
    private FormPanel simple = new FormPanel();
    private Radio newProjectRB = new Radio();

    // Basic info components
    private CheckBox checkbox = new CheckBox();
    private TextField<String> textField = new TextField<String>();
    private NumberField number = new NumberField();
    private RadioGroup group = new RadioGroup();
    private Radio item1 = new Radio();
    private Radio item2 = new Radio();

    // textfields
    private TextField<String> projectName = new TextField<String>();

    // fieldsets
    private FieldSet setBasicData = new FieldSet();

    // buttons
    private Button submitButton = new Button("Create");
    private Button userSearchButton = new Button("Select User");
    private Button addTeamMember = new Button("Add member");
    private Button selectScrumMasterB = new Button("Select SM");
    private Button selectProductOwnerB = new Button("Select PO");

    Label selectProductOwnerLabel = new Label("Select Product Owner:");
    Label selectScrumMasterLabel = new Label("Select Scrum Master:");


    public AbstractProjectRegistrationForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        formData = new FormData("-20");
        vp = new VerticalPanel();
        vp.setSpacing(10);
        initMainRegistrationForm();
        add(vp);
    }

    private void initMainRegistrationForm() {
        newProjectRB.setName("new_project");
        newProjectRB.setValue(true);
        newProjectRB.setBoxLabel("New Project");

        typeOfProjectRG.setFieldLabel("Project type");
        typeOfProjectRG.setAutoWidth(true);
        typeOfProjectRG.add(newProjectRB);
        typeOfProjectRG.addListener(Events.Change, new Listener<FieldEvent>() {
            @Override
            public void handleEvent(FieldEvent fe) {
                if (newProjectRB.getValue()) {
                    clearAllData();
                }

            }
        });
        simple.add(typeOfProjectRG);

        //simple.add(searchedUserTF, getFormData());
        //userSearchButton.setEnabled(false);
        //simple.add(userSearchButton);

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

        // Title of the form
        getSimple().setHeading("Project Creation Form");
        getSimple().setFrame(true);

        FormLayout layout = new FormLayout();

        getSimple().setButtonAlign(Style.HorizontalAlignment.CENTER);

        setBasicData.setHeading("Project information");
        layout.setLabelWidth(100);
        setBasicData.setLayout(layout);

        projectName.setFieldLabel("Project Name");
        projectName.setAllowBlank(false);
        setBasicData.add(projectName, getFormData());

        // Product owner field
        setBasicData.add(selectProductOwnerLabel);
        setBasicData.add(selectProductOwnerB);
        // Scrum master field
        setBasicData.add(selectScrumMasterLabel);
        setBasicData.add(selectScrumMasterB);


        getSimple().add(setBasicData);

        submitButton.setEnabled(true);
        getSimple().addButton(submitButton);
        getSimple().addButton(new Button("Reset form"));

        FormButtonBinding binding = new FormButtonBinding(getSimple());
        binding.addButton(submitButton);

        getVp().add(getSimple());
    }

    protected void initComponentsDataFill() { }

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

    public Button getUserSearchButton() {
        return userSearchButton;
    }

    public Button getSelectProductOwnerB() {
        return selectProductOwnerB;
    }

    public Button getSelectScrumMasterB() {
        return selectScrumMasterB;
    }

    public TextField<String> getProjectName() {
        return projectName;
    }

    public FieldSet getSetBasicData() {
        return setBasicData;
    }

    public TextField<String> getSearchedUserTF() {
        return searchedUserTF;
    }

    public Radio getNewUserRB() {
        return newProjectRB;
    }



}
