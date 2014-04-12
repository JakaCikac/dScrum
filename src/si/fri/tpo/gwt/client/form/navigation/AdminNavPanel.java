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
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.form.registration.ProjectRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.SprintRegistrationForm;
import si.fri.tpo.gwt.client.form.registration.UserRegistrationForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

//TODO: merge cnavpanel into admin nav panel!
    private DScrumServiceAsync service;
    private ContentPanel center;
    private FlowLayoutContainer con;

    public AdminNavPanel(DScrumServiceAsync service) {        this.service = service;
        this.center = center;
    }


    private ContentPanel panel;
    public Widget asWidget() {
        panel = new ContentPanel();
        con = new FlowLayoutContainer();
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
        cp.setBodyStyleName("pad-text");


        final TextButton users = new TextButton("User Management");
        users.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                System.out.println("Registered click on user management");                UserRegistrationForm rgf = new UserRegistrationForm(service);
                center.clear();
                center.add(rgf.asWidget());
            }
        });

        cp.add(users);
        con.add(cp);

        final TextButton projectManagement = new TextButton("Project Management");        projectManagement.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override

            public void onSelect(SelectEvent event) {
                ProjectRegistrationForm pgf = new ProjectRegistrationForm(service);
                center.clear();
                center.add(pgf.asWidget());
            }
        });
        cp.add(projectManagement);

        panel.setWidth(160);

        panel.add(cp);

    }

    private ModelData newItem(String text, String iconStyle) {
        ModelData m = new BaseModelData();
        m.set("name", text);
        m.set("icon", iconStyle);
        return m;
    }
