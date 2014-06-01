package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.ContentPanel;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
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
            folder.setWidth(center.getOffsetWidth());
            folder.setHeight(center.getOffsetHeight());

            ContentPanel cp;
            if (SessionInfo.projectDTO == null) {
                String noProject = "<h1 style='margin-left:auto; margin-right:auto; margin-top:30px; " +
                        "font-size:15px;'>No project selected!</h1><br/><h2 style='margin-left:auto; margin-right:auto; " +
                        "font-size:15px;'>Select project from Project list.</h2>";

                cp = new ContentPanel();
                cp.add(new HTML(noProject));
                folder.add(cp, "Project wall");

                cp = new ContentPanel();
                cp.add(new HTML(noProject));
                folder.add(cp, "Product backlog");

                cp = new ContentPanel();
                cp.add(new HTML(noProject));
                folder.add(cp, "Sprint backlog");

                cp = new ContentPanel();
                cp.add(new HTML(noProject));
                folder.add(cp, "My tasks");

                cp = new ContentPanel();
                cp.add(new HTML(noProject));
                folder.add(cp, "Progress report");

            } else {

                folder.add(new DiscussionForm(service, center, west, east, north, south).asWidget(), "Project wall");
                folder.add(new ProductBacklogForm(service, center, west, east, north, south).asWidget(), "Product backlog");

                boolean inProgress = false;
                for (SprintDTO sprintDTO : SessionInfo.projectDTO.getSprintList()){
                    if ((sprintDTO.getStartDate().before(new Date()) && sprintDTO.getEndDate().after(new Date())) ||
                            sprintDTO.getStartDate().equals(new Date()) || sprintDTO.getEndDate().equals(new Date())){
                        folder.add(new SprintBacklogForm(service, center, west, east, north, south, sprintDTO).asWidget(), "Sprint backlog");
                        folder.add(new MyTasksForm(service, center, west, east, north, south, sprintDTO).asWidget(), "My tasks");
                        inProgress = true;
                    }
                }

                if (!inProgress) {
                    String noSprint = "<h1 style='margin-left:auto; margin-right:auto; margin-top:30px; font-size:15px;'>No sprint in progress</h1>";

                    cp = new ContentPanel();
                    cp.add(new HTML(noSprint));
                    folder.add(cp, "Sprint backlog");

                    cp = new ContentPanel();
                    cp.add(new HTML(noSprint));
                    folder.add(cp, "My tasks");
                }

                folder.add(new ProgressReportForm(service, center, west, east, north, south).asWidget(), "Progress report");
            }
        }
        return folder;
    }

}