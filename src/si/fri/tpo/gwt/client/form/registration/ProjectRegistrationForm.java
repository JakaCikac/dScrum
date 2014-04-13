package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchCallback;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchDialog;
import si.fri.tpo.gwt.client.form.select.TeamSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

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

    private ProjectDTO projectDTO;
    private UserDTO scrumMasterDTO;
    private UserDTO productOwnerDTO;


    private TextButton submitButton;
    private TextButton userSearchButton;

    // TODO: Add team members
    private TextButton selectScrumMasterB;
    private TextButton selectProductOwnerB;

    private FieldLabel scrumMasterLabel;
    private FieldLabel productOwnerLabel;
    private TextField selScrumMaster;
    private TextField selProductOwner;
    private TeamSelectForm tsf;
    private int teamId;


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

    private void createProjectForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("Project Creation Form");
        panel.setWidth(320);
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

        selProductOwner = new TextField();
        selScrumMaster = new TextField();
        selScrumMaster.setAllowBlank(false);
        selProductOwner.setAllowBlank(false);
        productOwnerLabel = new FieldLabel();
        scrumMasterLabel = new FieldLabel();

        HorizontalPanel userSelectionPanel = new HorizontalPanel();
        selectScrumMasterB = new TextButton("Select SM");
        selectScrumMasterB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                SingleUserSearchDialog sus = new SingleUserSearchDialog(service, new SingleUserSearchCallback() {
                    @Override
                    public void userSearchCallback(UserDTO userDTO) {
                        if ((productOwnerDTO == null) || (!(productOwnerDTO == null) && !userDTO.getUsername().equals(productOwnerDTO.getUsername()))) {
                            setScrumMasterDTO(userDTO);
                            selScrumMaster.setText(scrumMasterDTO.getUsername());
                        } else Info.display("Warning", "Scrum Master can not be the same person as Product Owner!");
                    }
                });
                sus.show();
            }
        });
        selectProductOwnerB = new TextButton("Select PO");
        selectProductOwnerB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                SingleUserSearchDialog sus = new SingleUserSearchDialog(service, new SingleUserSearchCallback() {
                    @Override
                    public void userSearchCallback(UserDTO userDTO) {
                        if ((scrumMasterDTO == null) || (!(scrumMasterDTO == null) && !userDTO.getUsername().equals(scrumMasterDTO.getUsername()))) {
                            setProductOwnerDTO(userDTO);
                            selProductOwner.setText(productOwnerDTO.getUsername());
                        } else Info.display("Warning", "Product Owner can not be the same person as Scrum Master!");
                    }
                });
                sus.show();
            }
        });
        userSelectionPanel.add(selectProductOwnerB);
        userSelectionPanel.add(selectScrumMasterB);
        p.add(userSelectionPanel);

        VerticalPanel selectedUserPanel = new VerticalPanel();
        selProductOwner.setReadOnly(true);
        selScrumMaster.setReadOnly(true);
        productOwnerLabel.setText("Product Owner");
        scrumMasterLabel.setText("Scrum Master");
        HorizontalPanel po = new HorizontalPanel();
        po.add(productOwnerLabel);
        po.add(selProductOwner);
        selectedUserPanel.add(po);
        po = new HorizontalPanel();
        po.add(scrumMasterLabel);
        po.add(selScrumMaster);
        selectedUserPanel.add(po);
        p.add(selectedUserPanel);

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


        FieldLabel lol = new FieldLabel();
        lol.setText("Team members");
        p.add(lol);
        tsf = new TeamSelectForm(service);
        p.add(tsf.asWidget(), new VerticalLayoutContainer.VerticalLayoutData(-1, -1));


        submitButton = new TextButton("Submit");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                // Get Project Name
                // Get Project Description
                // Get Project Status
                final ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setName(projectName.getText());
                projectDTO.setDescription(description.getText());
                if (assigned.getValue())
                    projectDTO.setStatus("assigned");
                else projectDTO.setStatus("waiting");

                // Get Product Owner
                // Get Scrum Master
                // Get Team Members
                final TeamDTO teamDTO = new TeamDTO();
                teamDTO.setProductOwnerId(productOwnerDTO.getUserId());
                teamDTO.setScrumMasterId(scrumMasterDTO.getUserId());
                teamDTO.setUserList(tsf.getMembers());
                // Save team to database
                performSaveTeam(teamDTO);
                teamDTO.setTeamId(teamId);
                projectDTO.setTeamTeamId(teamDTO);
                // Save project to database
                performSaveProject(projectDTO);

            }
        });
    }

    private void performSaveProject(ProjectDTO projectDTO) {

        AsyncCallback<Pair<Boolean, String>> saveProject = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing project saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving project!", result.getSecond());
                    amb2.show();
                }
                else {
                    AlertMessageBox amb3 = new AlertMessageBox("Message save Project", result.getSecond());
                    amb3.show();
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        System.out.println("Calling saveProject");
        // TODO: project name duplication
        service.saveProject(projectDTO, saveProject);
    }

    private void performSaveTeam(TeamDTO teamDTO) {

        AsyncCallback<Pair<Boolean, Integer>> saveTeam = new AsyncCallback<Pair<Boolean, Integer>>() {
            @Override
            public void onSuccess(Pair<Boolean, Integer> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing team saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving team!", result.getSecond().toString());
                    amb2.show();
                }
                else {
                    AlertMessageBox amb3 = new AlertMessageBox("Message at save Team", result.getSecond().toString());
                    amb3.show();
                    /// set team Id to stored Team
                    setTeamId(result.getSecond());
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        System.out.println("Calling saveTeam");
        service.saveTeam(teamDTO, saveTeam);
    }


    private void setProjectDTO(ProjectDTO dto) {
        this.projectDTO = dto;
    }

    public void setProductOwnerDTO(UserDTO productOwnerDTO) {
        this.productOwnerDTO = productOwnerDTO;
    }

    public void setScrumMasterDTO(UserDTO scrumMasterDTO) {
        this.scrumMasterDTO = scrumMasterDTO;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

}
