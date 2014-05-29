package si.fri.tpo.gwt.client.form.select;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.dnd.core.client.ListViewDragSource;
import com.sencha.gxt.dnd.core.client.ListViewDropTarget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.addedit.ProjectDataEditForm;
import si.fri.tpo.gwt.client.form.home.NorthForm;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.GodNavPanel;
import si.fri.tpo.gwt.client.form.navigation.ScrumMasterNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nanorax on 15/04/14.
 */
public class ProjectSelectForm implements IsWidget {

    // TODO: get user role and display next to project

    private DScrumServiceAsync service;
    private ListStore<ProjectDTO> projectList;
    private ProjectDTO selectedProject;
    private ProjectDataEditForm pdef;
    private ContentPanel center;
    private ContentPanel west;
    private ContentPanel east;
    private ContentPanel north;
    private ContentPanel south;

    private VerticalPanel vp;

    
    public ProjectSelectForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(1);
            vp.setBorderWidth(0);

            FramedPanel panel = new FramedPanel();
            panel.setPixelSize(west.getOffsetWidth(), west.getOffsetHeight());
            panel.setHeaderVisible(true);
            panel.setHeadingText("Project list");
            panel.setBorders(false);

            HorizontalLayoutContainer con = new HorizontalLayoutContainer();

            projectList = new ListStore<ProjectDTO>(getModelKeyProvider());
            projectList.addSortInfo(new Store.StoreSortInfo<ProjectDTO>(getNameValue(), SortDir.ASC));

            // Check user role and display project list accordingly.
            if (SessionInfo.userDTO.isAdmin()) {
                getAdminProjectList();
            } else getUserProjectList();

            ListView<ProjectDTO, String> list1 = new ListView<ProjectDTO, String>(projectList, getNameValue());
            list1.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

            new ListViewDragSource<ProjectDTO>(list1).setGroup("top");
            new ListViewDropTarget<ProjectDTO>(list1).setGroup("top");

            panel.add(list1, new HorizontalLayoutContainer.HorizontalLayoutData(1, 1, new Margins(0)));

            list1.getSelectionModel().addSelectionHandler(new SelectionHandler<ProjectDTO>() {
                @Override
                public void onSelection(SelectionEvent<ProjectDTO> event) {
                    getProjectDTO(event.getSelectedItem().getName());
                    // TODO: select selected project after refresh (nekje si verejtn rabs zapomnt kaj je bilo sel in potem sel)
                    Info.display("Selected project", "Project " + event.getSelectedItem().getName() + " selected.");
                }
            });
            //panel.add(con);
            vp.add(panel);

        }
        return vp;
    }

    private void getProjectDTO(String name) {
        AsyncCallback<ProjectDTO> callback = new AsyncCallback<ProjectDTO>() {
            @Override
            public void onSuccess(ProjectDTO result) {
                selectedProject = result;
                SessionInfo.projectDTO = selectedProject;
                List<SprintDTO> sprintDTOList = selectedProject.getSprintList();
                List<SprintDTO> newSprintDTOList = new ArrayList<SprintDTO>();
                // If sprint for the selected project is over or in progress, the status may need to be changed.
                // Go through sprints and check if status is ok, update sprints accordingly and update SessionInfo.projectDTO
                for (SprintDTO sprintDTO : sprintDTOList){
                    if ((sprintDTO.getStartDate().before(new Date()) && sprintDTO.getEndDate().after(new Date())) ||
                            sprintDTO.getStartDate().equals(new Date()) || sprintDTO.getEndDate().equals(new Date())){
                        sprintDTO.setStatus("In progress");
                    } else if (sprintDTO.getEndDate().before(new Date())){
                        sprintDTO.setStatus("Completed");
                    }
                    sprintDTO.setProject(SessionInfo.projectDTO);
                    AsyncCallback<Pair<Boolean, String>> updateSprint = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result == null) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing sprint updating!");
                                amb2.show();
                            }
                            else if (!result.getFirst()) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error updating Sprint!", result.getSecond());
                                amb2.show();
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    service.updateSprint(sprintDTO, updateSprint);
                    newSprintDTOList.add(sprintDTO);
                }
                SessionInfo.projectDTO.setSprintList(newSprintDTOList);

                // if user is scrum master for selected project and not an admin
                if (SessionInfo.projectDTO.getTeamTeamId().getScrumMasterId() == SessionInfo.userDTO.getUserId()
                        && !SessionInfo.userDTO.isAdmin()) {
                    // replace navigation panel with scrum master panel
                    east.clear();
                    ScrumMasterNavPanel smnp = new ScrumMasterNavPanel(center, west, east,north, south, service);
                    east.add(smnp.asWidget());
                }  // if user is just a user and not a scrum master
                else if (SessionInfo.projectDTO.getTeamTeamId().getScrumMasterId() != SessionInfo.userDTO.getUserId()
                        && !SessionInfo.userDTO.isAdmin()) {
                    east.clear();
                    UserNavPanel unp = new UserNavPanel(service, center, west, east, north, south);
                    east.add(unp.asWidget());
                } // if user is not scrum master but admin
                else if (SessionInfo.projectDTO.getTeamTeamId().getScrumMasterId() != SessionInfo.userDTO.getUserId()
                        && SessionInfo.userDTO.isAdmin()) {
                    east.clear();
                    AdminNavPanel anp = new AdminNavPanel(center, west, east,north, south, service);
                    east.add(anp.asWidget());
                } // if user is scrum master and admin
                else if (SessionInfo.projectDTO.getTeamTeamId().getScrumMasterId() == SessionInfo.userDTO.getUserId()
                        && SessionInfo.userDTO.isAdmin()) {
                    east.clear();
                    GodNavPanel gnp = new GodNavPanel(center, west, east,north, south, service);
                    east.add(gnp.asWidget());
                }

                UserHomeForm uhf = new UserHomeForm(service, center, west, east, north, south);
                center.clear();
                center.add(uhf.asWidget());
                //pdef.fillFormData();
                north.add(new NorthForm(service, center, north, south, east, west).asWidget());
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findProjectByName(name, callback);
    }

    private void getAdminProjectList() {
        AsyncCallback<List<ProjectDTO>> callback = new AsyncCallback<List<ProjectDTO>>() {
            @Override
            public void onSuccess(List<ProjectDTO> result) {
                projectList.addAll(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findAllProjects(callback);
    }

    private void getUserProjectList() {
        AsyncCallback<List<ProjectDTO>> callback = new AsyncCallback<List<ProjectDTO>>() {
            @Override
            public void onSuccess(List<ProjectDTO> result) {
                for(ProjectDTO projectDTO : result){
                    if(projectDTO.getTeamTeamId().getScrumMasterId() == SessionInfo.userDTO.getUserId() ||
                            projectDTO.getTeamTeamId().getProductOwnerId() == SessionInfo.userDTO.getUserId()){
                        projectList.add(projectDTO);
                    } else {
                        for(UserDTO userDTO : projectDTO.getTeamTeamId().getUserList()){
                            if(userDTO.getUserId() == SessionInfo.userDTO.getUserId()){
                                projectList.add(projectDTO);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findAllProjects(callback);
    }

    // return the model key provider for the list store
    private ModelKeyProvider<ProjectDTO> getModelKeyProvider() {
        ModelKeyProvider<ProjectDTO> mkp = new ModelKeyProvider<ProjectDTO>() {
            @Override
            public String getKey(ProjectDTO item) {
                return item.getName();
            }
        };
        return mkp;
    }

    private ValueProvider<ProjectDTO, String> getNameValue() {
        ValueProvider<ProjectDTO, String> vpn = new ValueProvider<ProjectDTO, String>() {
            @Override
            public String getValue(ProjectDTO object) {
                return object.getName();
            }
            @Override
            public void setValue(ProjectDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

}