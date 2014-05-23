package si.fri.tpo.gwt.client.form.addedit;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by t13db on 22.5.2014.
 */
public class DiscussionCommentDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;
    private DiscussionDTO discussionDTO;

    public DiscussionCommentDialog(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, DiscussionDTO discussionDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
        this.discussionDTO = discussionDTO;

        // Layout
        setBodyBorder(false);
        setHeadingText("Comment form");

        setWidth(350);
        setHeight(350);
        setHideOnButtonClick(true);

        ClearPanels();

        FlowLayoutContainer layout = new FlowLayoutContainer();
        add(layout);
        DiscussionCommentAddForm caf = new DiscussionCommentAddForm(this.service, this.center, this.west, this.east, this.discussionDTO);
        center.disable();
        east.disable();
        west.disable();
        if (north != null) {
            north.disable();
        }
        south.disable();
        layout.add(caf.asWidget());
    }

    private void ClearPanels(){
        getButton(this.getPredefinedButtons().get(0)).addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
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
        addHideHandler(new HideEvent.HideHandler() {
            @Override
            public void onHide(HideEvent event) {
                center.enable();
                east.enable();
                west.enable();
                north.enable();
                south.enable();
            }
        });
    }
}
