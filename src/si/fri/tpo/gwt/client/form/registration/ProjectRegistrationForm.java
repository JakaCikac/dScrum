package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.search.UserSearchCallback;
import si.fri.tpo.gwt.client.form.search.UserSearchDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.verification.PassHash;

import java.util.ArrayList;


/**
 * Created by nanorax on 07/04/14.
 */
public class ProjectRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private VerticalPanel vp;
    private ListBox lb;
    private ArrayList<UserDTO> al;

    private TextField projectName;
    private TextArea description;
    private Radio completed;
    private Radio assigned;
    private Radio waiting;

    private TextButton submitButton;
    private TextButton userSearchButton;

    // TODO: Add team members
    private TextButton addTeamMemberB;
    private TextButton selectScrumMasterB;
    private TextButton selectProductOwnerB;

     public Widget asWidget() {
         if (vp == null) {
             vp = new VerticalPanel();
             vp.setSpacing(10);
             createProjectForm();
         }
         return vp;
    }

    public ProjectRegistrationForm(DScrumServiceAsync service) {
        this.service = service;
    }

    private ProjectDTO dto;


    private void createProjectForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Project Creation Form");
        panel.setWidth(350);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        projectName = new TextField();
        projectName.setAllowBlank(false);
        projectName.setEmptyText("Chuck's project");
        p.add(new FieldLabel(projectName, "Project Name"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        description = new TextArea();
        description.setAllowBlank(false);
        description.addValidator(new MinLengthValidator(10));
        p.add(new FieldLabel(description, "Description"), new VerticalLayoutContainer.VerticalLayoutData(1, 100));


        VerticalPanel rbp;

        assigned = new Radio();
        assigned.setBoxLabel("Assigned");
        assigned.setValue(true);

        completed = new Radio();
        completed.setBoxLabel("Completed");

        waiting = new Radio();
        waiting.setBoxLabel("Waiting");

        rbp = new VerticalPanel();
        rbp.add(assigned);
        //rbp.add(completed);
        //rbp.add(waiting);

        p.add(new FieldLabel(rbp, "Project status"));

        VerticalPanel userSelectionPanel = new VerticalPanel();
        addTeamMemberB = new TextButton("Add Team");
        selectScrumMasterB = new TextButton("Select SM");
        selectProductOwnerB = new TextButton("SelectPO");
        userSelectionPanel.add(addTeamMemberB);
        userSelectionPanel.add(selectProductOwnerB);
        userSelectionPanel.add(selectScrumMasterB);
        p.add(userSelectionPanel);

        // we can set name on radios or use toggle group
        ToggleGroup toggle = new ToggleGroup();
        toggle.add(assigned);
        toggle.add(completed);
        toggle.add(waiting);
        toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio assigned = (Radio) group.getValue();
            }
        });

        submitButton = new TextButton("Submit");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // TODO: add project to database
            }
        });
        panel.addButton(submitButton);


        vp.add(panel);
    }
}


 /*   private SelectionListener poSelectionListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    // return selected user and change label to his username
                    getSelectedProductOwnerUserLabel().setText(dto.getUsername());
                    setDTO(dto);
                }
            });
        }
    };

    private SelectionListener smSelectionListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    // return selected user and change label to his username
                    getSelectedScrumMasterUserLabel().setText(dto.getUsername());
                    setDTO(dto);
                }
            });
        }
    };

    private SelectionListener addTeamListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    // return selected user and change label to his username
                    // TODO: I dont know what but incomplete
                    setDTO(dto);
                }
            });
        }
    };

    private void setDTO(UserDTO dto) {
        this.dto = dto;
    }


    private SelectionListener submitListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            performSaveProject(new ProjectDTO());
        }
    };

    private void performSaveProject(ProjectDTO projectDTO) {
        System.out.println(getUsersArrayList().size());
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        initNewRegistrationForm();
        initComponentsDataFill();
    }

}
*/

/*

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

} */
