package si.fri.tpo.gwt.client.form.navigation;

/* import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
*/
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserNavPanel { //extends LayoutContainer {
    /* private RootPanel mainContainer;
    private DScrumServiceAsync service;

    public UserNavPanel(RootPanel mainContainer, DScrumServiceAsync service) {
        this.mainContainer = mainContainer;
        this.service = service;
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setLayout(new FlowLayout(10));

        final ContentPanel panel = new ContentPanel();
        panel.setHeading("User menu");
        panel.setBodyBorder(false);
        panel.setLayout(new AccordionLayout());

        ContentPanel cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setExpanded(true);
        cp.setBodyStyleName("pad-text");

        /* Button projectManagement = new Button("User Data Management", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new UserDataEditForm(service));
            }
        });
        projectManagement.setWidth("100%");
        cp.add(projectManagement);

        cp.setAutoHeight(true);
        cp.setAutoWidth(true);
        panel.add(cp);

        panel.setWidth(270);
        panel.setAutoHeight(true);

        this.add(panel);
    }

    private ModelData newItem(String text, String iconStyle) {
        ModelData m = new BaseModelData();
        m.set("name", text);
        m.set("icon", iconStyle);
        return m;
    }
    */
}