package si.fri.tpo.gwt.client.form.registration;

/* import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
*/

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.search.UserSearchCallback;
import si.fri.tpo.gwt.client.form.search.UserSearchDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.verification.PassHash;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nanorax on 07/04/14.
 */
public abstract class AbstractProjectRegistrationForm implements IsWidget {// extends LayoutContainer {

    private DScrumServiceAsync service;

    /* private Widget asWidget() {

        return null;
    } */

    private VerticalPanel vp;
    private ToggleGroup typeOfProjectRG = new ToggleGroup();
    private TextField searchedUserTF = new TextField();
    private FormPanel simple = new FormPanel();
    private Radio newProjectRB = new Radio();
    private UserDTO dto = new UserDTO();
    private ListBox lb;
    private ArrayList<UserDTO> al;


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
            panel.setHeadingText("Simple Form");
            panel.setWidth(350);
            panel.setBodyStyle("background: none; padding: 15px");

            VerticalLayoutContainer p = new VerticalLayoutContainer();
            panel.add(p);

            TextField firstName = new TextField();
            firstName.setAllowBlank(false);
            firstName.setEmptyText("Enter your name...");
            /* firstName.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    Info.display("Value Changed", "First name changed to " + event.getValue() == null ? "blank" : event.getValue());
                }
            }); */

            p.add(new FieldLabel(firstName, "Name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

            TextField email = new TextField();
            email.setAllowBlank(false);
            p.add(new FieldLabel(email, "Email"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

            PasswordField password = new PasswordField();
            p.add(new FieldLabel(password, "Password"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));





            ValueChangeHandler<Boolean> musicHandler = new ValueChangeHandler<Boolean>() {

                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    CheckBox check = (CheckBox)event.getSource();
                    Info.display("Music Changed", check.getBoxLabel() + " " + (event.getValue() ? "selected" : "not selected"));
                }
            };

            CheckBox check1 = new CheckBox();
            check1.setEnabled(false);
            check1.setBoxLabel("Classical");
            check1.addValueChangeHandler(musicHandler);

            CheckBox check2 = new CheckBox();
            check2.setBoxLabel("Rock");
            check2.addValueChangeHandler(musicHandler);
            check2.setValue(true);

            CheckBox check3 = new CheckBox();
            check3.setBoxLabel("Blues");
            check3.addValueChangeHandler(musicHandler);

            HorizontalPanel hp = new HorizontalPanel();
            hp.add(check1);
            hp.add(check2);
            hp.add(check3);

            p.add(new FieldLabel(hp, "User type"));

            Radio radio = new Radio();
            radio.setBoxLabel("Red");

            Radio radio2 = new Radio();
            radio2.setBoxLabel("Blue");
            radio2.setValue(true);

            hp = new HorizontalPanel();
            hp.add(radio);
            hp.add(radio2);

            p.add(new FieldLabel(hp, "Color"));

            // we can set name on radios or use toggle group
            ToggleGroup toggle = new ToggleGroup();
            toggle.add(radio);
            toggle.add(radio2);
            toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {

                @Override
                public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                    ToggleGroup group = (ToggleGroup)event.getSource();
                    Radio radio = (Radio)group.getValue();
                    Info.display("Color Changed", "You selected " + radio.getBoxLabel());
                }
            });

            panel.addButton(new TextButton("Save"));
            panel.addButton(new TextButton("Cancel"));

            vp.add(panel);
        }

 /*   // textfields
    private TextField projectName = new TextField();

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

        getSimple().setButtonAlign(Style.HorizontalAlignment.CENTER);

        setBasicData.setHeading("Project information");
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


        lb = new ListBox();
        setBasicData.add(lb);
        al = new ArrayList<UserDTO>();

        final TextButton addItem = new TextButton("Add Member");
        addItem.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
               /* new UserSearchDialog(getService(), new UserSearchCallback() {
                    @Override
                    public void userSearchCallback (UserDTO dto){
                        // return selected user and change label to his username
                        setDTO(dto);
                        al.add(dto);
                        System.out.println("Adding to listbox: " + dto.getUsername());
                        lb.addItem(dto.getUsername());
            }
        });
        addItem.addButton(loginButton);
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

    public TextField getProjectName() {
        return projectName;
    }

    public FieldSet getSetBasicData() {
        return setBasicData;
    }

    public TextField getSearchedUserTF() {
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
    } */
}
