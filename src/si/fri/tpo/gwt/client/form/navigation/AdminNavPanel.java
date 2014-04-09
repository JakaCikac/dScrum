package si.fri.tpo.gwt.client.form.navigation;

/**
 * Created by nanorax on 04/04/14.
 */

import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.legacy.client.data.ModelData;
import com.sencha.gxt.legacy.client.data.BaseModelData;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.ContentPanel;
/* import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
*/
import com.google.gwt.user.client.Element;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
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
        //users.addSelectHandler()

        /* TextButton users = new TextButton("User management", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new UserRegistrationForm(service));
            }
        });
        users.setWidth("100%");
        final ToggleButton b1 = new ToggleButton("Toggle Size");
        b1.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                if (b1.getValue()) {
                    con.setPixelSize(600, 400);
                } else {
                    con.setPixelSize(400, 300);
                }

            }
        });
        cp.add(users);

        TextButton projectManagement = new TextButton("Project Management", new CustomEventHandler() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                //mainContainer.add(new AddEditForm(mainContainer,service));
                mainContainer.add(new ProjectRegistrationForm(service));
            }
        });
        projectManagement.setWidth("100%");
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


