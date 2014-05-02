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
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchCallback;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchDialog;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.form.select.TeamSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;


/**
 * Created by nanorax on 07/04/14.
 */
public class ProjectRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private VerticalPanel vp;
    private ListBox lb;
    private ArrayList<UserDTO> al;

    private TextField projectName;
    private TextArea description;
    private Radio completed;
    private Radio assigned;
    private Radio waiting;

    private ProjectDTO projectDTO;
    private TeamDTO teamDTO;
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

    public ProjectRegistrationForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
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
        p.add(new FieldLabel(projectName, "Project Name *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

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
        productOwnerLabel.setText("Product Owner *");
        scrumMasterLabel.setText("Scrum Master *");
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
        tsf = new TeamSelectForm(service, true);
        p.add(tsf.asWidget(), new VerticalLayoutContainer.VerticalLayoutData(-1, -1));

        submitButton = new TextButton("Submit");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                AlertMessageBox amb;
                // Get Project Name
                // Get Project Description
                // Get Project Status
                final ProjectDTO projectDTO = new ProjectDTO();
                if (projectName.getValue() == null){
                    amb = new AlertMessageBox("Empty Project Name", "Please enter project name!");
                    amb.show();
                    return;
                } else projectDTO.setName(projectName.getText());
                projectDTO.setDescription(description.getText());
                if (assigned.getValue())
                    projectDTO.setStatus("assigned");
                else projectDTO.setStatus("waiting");
                // Get Product Owner
                // Get Scrum Master
                // Get Team Members
                TeamDTO teamDTO = new TeamDTO();
                if (selProductOwner.getText().equals("")){
                    amb = new AlertMessageBox("Empty Product Owner", "Please choose product owner!");
                    amb.show();
                    return;
                } else teamDTO.setProductOwnerId(productOwnerDTO.getUserId());
                // Enforcing ScrumMaster selection when Project is selected (suspended - not required)
                /*if (selScrumMaster.getText().equals("")){
                    amb = new AlertMessageBox("Empty Scrum Master", "Please choose scrum master!");
                    amb.show();
                    return;
                } else*/ teamDTO.setScrumMasterId(scrumMasterDTO.getUserId());
                //TODO: Ali je potrebno da projekt ima člane skupine ali ne??
                System.out.println(tsf.getMembers().size());//vrne 0 če je prazen
                teamDTO.setUserList(tsf.getMembers());

                // Save project to database with team
                performSaveTeamAndProject(teamDTO, projectDTO);
            }
        });
        panel.addButton(submitButton);
        vp.add(panel);
    }

    private void performSaveTeamAndProject(TeamDTO teamDTO, ProjectDTO projectDTO) {

        setProjectDTO(projectDTO);
        setTeamDTO(teamDTO);
        AsyncCallback<Pair<Boolean, Integer>> saveTeam = new AsyncCallback<Pair<Boolean, Integer>>() {
            @Override
            public void onSuccess(Pair<Boolean, Integer> result) {

                setTeamId(result.getSecond());
                getTeamDTO().setTeamId(teamId);
                getProjectDTO().setTeamTeamId(getTeamDTO());

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
                            MessageBox amb3 = new MessageBox("Message save Project", result.getSecond());
                            amb3.show();
                            center.clear();
                            west.clear();
                            east.clear();//TODO: when home page (wall, sprint backlog etc) create as widget on center, till then just clear.
                            SessionInfo.projectDTO = null;
                            if (SessionInfo.userDTO.isAdmin()){
                                AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                                east.add(adminNavPanel.asWidget());
                            } else {
                                UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                                east.add(userNavPanel.asWidget());
                            }
                            ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                            west.add(psf.asWidget());
                        }
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
                };
                System.out.println("Calling saveProject");
                service.saveProject(getProjectDTO(), saveProject);


                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing team saving!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error saving team!", result.getSecond().toString());
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveTeam(teamDTO, projectDTO.getName(), true, saveTeam);
    }

    private void setProjectDTO(ProjectDTO dto) {
        this.projectDTO = dto;
    }

    public ProjectDTO getProjectDTO() {
        return projectDTO;
    }

    public TeamDTO getTeamDTO() {
        return teamDTO;
    }

    public void setTeamDTO(TeamDTO teamDTO) {
        this.teamDTO = teamDTO;
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