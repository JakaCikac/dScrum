package si.fri.tpo.gwt.client.form.addedit;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.form.home.NorthForm;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by anze on 18. 05. 14.
 */
public class TaskEditDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private TaskDTO taskDTO;
    private AcceptEditTasksDialog aetd;

    public TaskEditDialog(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, TaskDTO taskDTO, AcceptEditTasksDialog aetd) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
        this.taskDTO = taskDTO;
        this.aetd = aetd;

        // Layout
        setBodyBorder(false);
        setHeadingText("Edit task");

        setWidth(350);
        setHeight(350);
        setHideOnButtonClick(true);

        ClearPanels();

        FlowLayoutContainer layout = new FlowLayoutContainer();
        add(layout);
        TaskEditForm tef = new TaskEditForm(this.service, this.center, this.west, this.east, this.taskDTO);
        layout.add(tef.asWidget());
        aetd.disable();
    }

    private void ClearPanels(){
        getButton(this.getPredefinedButtons().get(0)).addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                SessionInfo.projectDTO = null;
                north.clear();
                west.clear();
                east.clear();
                center.clear();
                NorthForm nf = new NorthForm(service, center, north, south, east, west);
                north.add(nf.asWidget());
                if (SessionInfo.userDTO.isAdmin()) {
                    AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                    east.add(adminNavPanel.asWidget());
                } else {
                    UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                    east.add(userNavPanel.asWidget());
                }
                ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                west.add(psf.asWidget());
                UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                center.add(userHomeForm.asWidget());
            }
        });
        addHideHandler(new HideEvent.HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                aetd.hide();
                center.enable();
                east.enable();
                west.enable();
                if(north!=null)north.enable();
                if(south!=null)south.enable();
            }
        });
    }
}
