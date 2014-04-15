package si.fri.tpo.gwt.client.form.select;

import com.google.gwt.core.client.GWT;
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
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;


import java.util.List;

/**
 * Created by nanorax on 15/04/14.
 */
public class ProjectSelectForm implements IsWidget {

    private DScrumServiceAsync service;
    private FramedPanel panel;
    private ListStore<ProjectDTO> projectList;

    private VerticalPanel vp;

    public ProjectSelectForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);

            FramedPanel panel = new FramedPanel();
            panel.setHeadingText("Project list view");
            panel.setPixelSize(500, 225);

            HorizontalLayoutContainer con = new HorizontalLayoutContainer();

            projectList = new ListStore<ProjectDTO>(getModelKeyProvider());
            projectList.addSortInfo(new Store.StoreSortInfo<ProjectDTO>(getNameValue(), SortDir.ASC));
            getProjectList();
            ListView<ProjectDTO, String> list1 = new ListView<ProjectDTO, String>(projectList, getNameValue());

            projectList = new ListStore<ProjectDTO>(getModelKeyProvider());
            projectList.addSortInfo(new Store.StoreSortInfo<ProjectDTO>(getNameValue(), SortDir.ASC));

            ListView<ProjectDTO, String> list2 = new ListView<ProjectDTO, String>(projectList, getNameValue());
            list2.getSelectionModel().setSelectionMode(Style.SelectionMode.MULTI);

            new ListViewDragSource<ProjectDTO>(list1).setGroup("top");

            new ListViewDropTarget<ProjectDTO>(list1).setGroup("top");

            con.add(list1, new HorizontalLayoutContainer.HorizontalLayoutData(.5, 1, new Margins(5)));

            panel.add(con);
            vp.add(panel);

            panel = new FramedPanel();
            panel.setHeadingText("ListView Insert");
            panel.setPixelSize(500, 225);

            con = new HorizontalLayoutContainer();

            projectList = new ListStore<ProjectDTO>(getModelKeyProvider());

            list1 = new ListView<ProjectDTO, String>(projectList, getNameValue());

            new ListViewDragSource<ProjectDTO>(list1).setGroup("bottom");

            ListViewDropTarget<ProjectDTO> target1 = new ListViewDropTarget<ProjectDTO>(list1);
            target1.setFeedback(DND.Feedback.INSERT);
            target1.setGroup("bottom");

            con.add(list1, new HorizontalLayoutContainer.HorizontalLayoutData(.5, 1, new Margins(5)));

            panel.add(con);
            vp.add(panel);
        }

        return vp;
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
        //TODO: write service
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