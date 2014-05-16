package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.Date;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserHomeForm implements IsWidget {

    private VerticalPanel vp;
    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private TabPanel folder;

    public UserHomeForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    @Override
    public Widget asWidget() {
        if (folder == null) {

            folder = new TabPanel();
            folder.setWidth(800);

            ContentPanel cp = new ContentPanel();
            cp.add(new Label("Project wall."));
            folder.add(cp, "Project wall");

            if (SessionInfo.projectDTO == null) {
                cp = new ContentPanel();
                cp.add(new Label("No project selected :)"));
                folder.add(cp, "Product backlog");
            }
            else {
                folder.add(new ProductBacklogForm(service, center, west, east, north, south).asWidget(), "Product backlog");
            }

            boolean inProgress = false;
            if (SessionInfo.projectDTO != null){
                for (SprintDTO sprintDTO : SessionInfo.projectDTO.getSprintList()){
                    if ((sprintDTO.getStartDate().before(new Date()) && sprintDTO.getEndDate().after(new Date())) ||
                            sprintDTO.getStartDate().equals(new Date()) || sprintDTO.getEndDate().equals(new Date())){
                        folder.add(new SprintBacklogForm(service, center, west, east, north, south, sprintDTO).asWidget(), "Sprint backlog");
                        folder.add(new MyTasksForm(service, center, west, east, north, south, sprintDTO).asWidget(), "My tasks");
                        inProgress = true;
                    }
                }
            }
            if (!inProgress) {
                cp = new ContentPanel();
                cp.add(new Label("No sprint in progress."));
                folder.add(cp, "Sprint backlog");
                cp = new ContentPanel();
                cp.add(new Label("No sprint in progress."));
                folder.add(cp, "My tasks");
            }
        }
        return folder;
    }

}