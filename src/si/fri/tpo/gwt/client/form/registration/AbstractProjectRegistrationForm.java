package si.fri.tpo.gwt.client.form.registration;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Element;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.addedit.TeamMemberAddEditForm;
import si.fri.tpo.gwt.client.form.search.UserSearchCallback;
import si.fri.tpo.gwt.client.form.search.UserSearchDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 07/04/14.
 */
public abstract class AbstractProjectRegistrationForm extends LayoutContainer {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private VerticalPanel vp2;
    private FormData formData;
    private RadioGroup typeOfProjectRG = new RadioGroup();
    private TextField<String> searchedUserTF = new TextField<String>();
    private FormPanel simple = new FormPanel();
    private Radio newProjectRB = new Radio();
    private UserDTO dto = new UserDTO();
    private ListBox lb;
    private ArrayList<UserDTO> al;
    // textfields
    private TextField<String> projectName = new TextField<String>();

    // fieldsets
    private FieldSet setBasicData = new FieldSet();

    // buttons
    private Button submitButton = new Button("Create");
    private Button userSearchButton = new Button("Select User");

    // TODO: Add team members
    private Button addTeamMemberB = new Button("Add member");
    private Button selectScrumMasterB = new Button("Select SM");
    private Button selectProductOwnerB = new Button("Select PO");

    private Label selectProductOwnerLabel = new Label("Select Product Owner: ");
    private Label selectScrumMasterLabel = new Label("Select Scrum Master: ");
    private Label selectedScrumMasterLabel = new Label("Selected Scrum Master: ");
    private Label selectedProductOwnerLabel = new Label("Selected Product Owner: ");
    private Label selectedScrumMasterUserLabel = new Label("SM not selected.");
    private Label selectedProductOwnerUserLabel = new Label("PO not selected.");


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
        // SM and PO label field
        setBasicData.add(selectedScrumMasterLabel);
        setBasicData.add(selectProductOwnerLabel);
        // SM and PO user label field
        setBasicData.add(selectedScrumMasterUserLabel);
        setBasicData.add(selectedProductOwnerUserLabel);

        ;
        lb = new ListBox();
        setBasicData.add(lb);
        al = new ArrayList<UserDTO>();

        Button addItem = new Button("Add Member", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                new UserSearchDialog(getService(), new UserSearchCallback() {
                    @Override
                public void userSearchCallback (UserDTO dto){
                    // return selected user and change label to his username
                    setDTO(dto);
                    al.add(dto);
                    System.out.println("Adding to listbox: " + dto.getUsername());
                    lb.addItem(dto.getUsername());
                    }
                });
            }
        });

        setBasicData.add(addItem);
        getSimple().add(setBasicData);

        submitButton.setEnabled(true);
        getSimple().addButton(submitButton);
        getSimple().addButton(new Button("Reset form"));

        FormButtonBinding binding = new FormButtonBinding(getSimple());
        binding.addButton(submitButton);

        getVp().add(getSimple());
    }

    private void setDTO(UserDTO dto) {
        this.dto = dto;
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

    public Button getAddTeamMemberB() {
        return addTeamMemberB;
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

    public ArrayList<UserDTO> getUsersArrayList() {
        return al;
    }

    public Label getSelectedScrumMasterLabel() {
        return selectedScrumMasterLabel;
    }

    public Label getSelectedProductOwnerLabel() {
        return selectedProductOwnerLabel;
    }

    public Label getSelectedProductOwnerUserLabel() {
        return selectedProductOwnerUserLabel;
    }

    public void setSelectedProductOwnerUserLabel(Label selectedProductOwnerUserLabel) {
        this.selectedProductOwnerUserLabel = selectedProductOwnerUserLabel;
    }

    public Label getSelectedScrumMasterUserLabel() {
        return selectedScrumMasterUserLabel;
    }

    public void setSelectedScrumMasterUserLabel(Label selectedScrumMasterUserLabel) {
        this.selectedScrumMasterUserLabel = selectedScrumMasterUserLabel;
    }

}
