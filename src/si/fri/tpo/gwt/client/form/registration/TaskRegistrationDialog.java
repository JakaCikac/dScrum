package si.fri.tpo.gwt.client.form.registration;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.addedit.UserStoryEditForm;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by nanorax on 02/05/14.
 */
public class TaskRegistrationDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private UserStoryDTO usDTO;

    public TaskRegistrationDialog(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, UserStoryDTO usDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
        this.usDTO = usDTO;

        // Layout
        setBodyBorder(false);
        setHeadingText("Task registration form");

        setWidth(350);
        setHeight(350);
        setHideOnButtonClick(true);

        ClearPanels();

        FlowLayoutContainer layout = new FlowLayoutContainer();
        add(layout);
        TaskRegistrationForm trf = new TaskRegistrationForm(this.service, this.center, this.west, this.east, this.usDTO);
        layout.add(trf.asWidget());


    }

    private void ClearPanels(){
        addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
            @Override
            public void onDialogHide(DialogHideEvent event) {
                UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                center.add(userHomeForm.asWidget());
                west.clear();
                east.clear();
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
        });
    }
}