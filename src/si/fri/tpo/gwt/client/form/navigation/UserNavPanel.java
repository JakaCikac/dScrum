package si.fri.tpo.gwt.client.form.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.legacy.client.data.BaseModelData;
import com.sencha.gxt.legacy.client.data.ModelData;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.form.addedit.UserDataEditForm;
import si.fri.tpo.gwt.client.form.registration.SprintRegistrationForm;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by nanorax on 06/04/14.
 * Modified by t13as
 */
public class UserNavPanel implements IsWidget{
    private DScrumServiceAsync service;
    private ContentPanel center;
    private FlowLayoutContainer con;
    private ContentPanel west;

    public UserNavPanel(DScrumServiceAsync service, ContentPanel center, ContentPanel west) {
        this.center = center;
        this.service = service;
        this.west = west;
    }

    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        con = new FlowLayoutContainer();
        panel.add(con);
        panel.setHeadingText("User menu");
        panel.setBodyBorder(false);
        createUserNavPanel();
        return panel;
    }

    private void createUserNavPanel() {

        ContentPanel cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        TextButton userDataEditB = new TextButton("Edit user");
        userDataEditB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                UserDataEditForm udef = new UserDataEditForm(service);
                center.clear();
                center.add(udef.asWidget());
            }
        });
        cp.add(userDataEditB);
        con.add(cp);

        cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setBodyStyleName("pad-text");

        TextButton sprintRegistrationB = new TextButton("Sprint Management");
        sprintRegistrationB.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (SessionInfo.projectDTO == null) {
                    Info.display("No project selected", "Please select project from the list on the left.");
                } else {
                    SprintRegistrationForm srf = new SprintRegistrationForm(service);
                    center.clear();
                    center.add(srf.asWidget());
                }
            }
        });
        cp.add(sprintRegistrationB);
        con.add(cp);

        ProjectSelectForm psf = new ProjectSelectForm(service, center);
        west.setHeadingText("Project list");
        west.add(psf.asWidget());

        panel.setWidth(270);
    }

}