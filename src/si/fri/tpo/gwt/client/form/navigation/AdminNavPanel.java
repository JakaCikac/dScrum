package si.fri.tpo.gwt.client.form.navigation;

/**
 * Created by nanorax on 04/04/14.
 */

import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.legacy.client.data.ModelData;
import com.sencha.gxt.legacy.client.data.BaseModelData;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.form.registration.ProjectRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserRegistrationForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

//TODO: merge cnavpanel into admin nav panel!
public class AdminNavPanel extends AccordionLayoutContainer implements IsWidget {
    private RootPanel mainContainer;
    private DScrumServiceAsync service;

    public AdminNavPanel(RootPanel mainContainer, DScrumServiceAsync service) {
        this.mainContainer = mainContainer;
        this.service = service;
        RootPanel.get().add(asWidget());
    }

    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        AccordionLayoutContainer con = new AccordionLayoutContainer();
        con.setExpandMode(AccordionLayoutContainer.ExpandMode.SINGLE_FILL);
        panel.add(con);
        panel.setHeadingText("Administrator menu");
        panel.setBodyBorder(false);
        createAdminNavPanel();
        return panel;
    }

    private void createAdminNavPanel() {

        ContentPanel cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setExpanded(true);
        cp.setBodyStyleName("pad-text");

        final TextButton users = new TextButton("User Management");
        users.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                mainContainer.clear();
                System.out.println("Registered click on user management");
                UserRegistrationForm rgf = new UserRegistrationForm(service);
                RootPanel.get().add(rgf);
            }
        });
        cp.add(users);

        /* final TextButton projectManagement = new TextButton("Project Management");
        projectManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                mainContainer.clear();
                mainContainer.add(new ProjectRegistrationForm(service));
            }
        });
        cp.add(projectManagement); */


        panel.add(cp);
        panel.setWidth(270);

    }

    private ModelData newItem(String text, String iconStyle) {
        ModelData m = new BaseModelData();
        m.set("name", text);
        m.set("icon", iconStyle);
        return m;
    }

}


