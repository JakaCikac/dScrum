package si.fri.tpo.gwt.client.form.select;

import com.google.gwt.core.client.GWT;
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
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.dnd.core.client.DND;
import com.sencha.gxt.dnd.core.client.ListViewDragSource;
import com.sencha.gxt.dnd.core.client.ListViewDropTarget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewSelectionModel;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.client.form.addedit.ProjecDataEditForm;


import java.util.List;

/**
 * Created by nanorax on 15/04/14.
 */
public class ProjectSelectForm implements IsWidget {

    // TODO: get user role and display next to project

    private DScrumServiceAsync service;
    private ListStore<ProjectDTO> projectList;
    private ProjectDTO selectedProject;
    private ProjecDataEditForm pdef;
    private ContentPanel center;

    private VerticalPanel vp;

    
    public ProjectSelectForm(DScrumServiceAsync service, ContentPanel center) {
        this.service = service;
        this.center = center;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(2);
            vp.setBorderWidth(0);

            FramedPanel panel = new FramedPanel();
            panel.setPixelSize(230, 400);
            panel.setHeaderVisible(false);

            HorizontalLayoutContainer con = new HorizontalLayoutContainer();

            projectList = new ListStore<ProjectDTO>(getModelKeyProvider());
            projectList.addSortInfo(new Store.StoreSortInfo<ProjectDTO>(getNameValue(), SortDir.ASC));

            // Check user role and display project list accordingly.
            if (SessionInfo.userDTO.isAdmin()) {
                getProjectList();
            } else getUserProjectList();

            ListView<ProjectDTO, String> list1 = new ListView<ProjectDTO, String>(projectList, getNameValue());
            list1.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

            new ListViewDragSource<ProjectDTO>(list1).setGroup("top");
            new ListViewDropTarget<ProjectDTO>(list1).setGroup("top");

            con.add(list1, new HorizontalLayoutContainer.HorizontalLayoutData(.9, 0.8, new Margins(0)));

            list1.getSelectionModel().addSelectionHandler(new SelectionHandler<ProjectDTO>() {
                @Override
                public void onSelection(SelectionEvent<ProjectDTO> event) {
                    getProjectDTO(event.getSelectedItem().getName());
                    Info.display("Selected project", "Project " + event.getSelectedItem().getName() + " selected.");
                    // TODO: WHen you have a project wall, redirect to project wall
                    center.clear();
                }
            });
            panel.add(con);
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
                System.out.println("SelectedProject ID : " + selectedProject.getProjectId());
                System.out.println("SessionProjectDTO ID: " + selectedProject.getSprintList().size() );
                // TODO: uh oh, tole ne bo slo tako, moras pogruntat kako osvzit data edit forme, ce se project zamenja med urejanjem
                //pdef.fillFormData();
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findProjectByName(name, callback);
    }

    private void getProjectList() {
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
                projectList.addAll(result);
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findUserProjects(SessionInfo.userDTO, callback);
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