package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchCallback;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchDialog;
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
                        if ((productOwnerDTO == null) || (!(productOwnerDTO == null) && !userDTO.getUsername().equals(productOwnerDTO.getUsername()))){
                            setScrumMasterDTO(userDTO);
                            selScrumMaster.setText(scrumMasterDTO.getUsername());
                            System.out.println("Zomgz1");
                        }
                        else Info.display("Warning", "Scrum Master can not be the same person as Product Owner!");
                        System.out.println("Zomgz");
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
                            System.out.println("Zomgz2");
                        } else Info.display("Warning", "Product Owner can not be the same person as Scrum Master!");
                        System.out.println("Zomgz");
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


        //TODO: sm in po ne sme biti ista oseba!

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

    private void setProjectDTO(ProjectDTO dto) {
        this.projectDTO = dto;
    }

    public void setProductOwnerDTO(UserDTO productOwnerDTO) {
        this.productOwnerDTO = productOwnerDTO;
    }

    public void setScrumMasterDTO(UserDTO scrumMasterDTO) {
        this.scrumMasterDTO = scrumMasterDTO;
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


    private SelectionListener submitListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            performSaveProject(new ProjectDTO());
        }
    };

    private void performSaveProject(ProjectDTO projectDTO) {
        System.out.println(getUsersArrayList().size());
    }

}
*/

/*



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



} */
