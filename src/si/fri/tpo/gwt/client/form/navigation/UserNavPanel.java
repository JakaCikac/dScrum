package si.fri.tpo.gwt.client.form.navigation;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserNavPanel extends LayoutContainer {
    private RootPanel mainContainer;
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

        cp.setAutoHeight(true);
        cp.setAutoWidth(true);
        panel.add(cp);

        panel.setWidth(270);
        panel.setAutoHeight(true);
        this.add(panel);

        //TODO: Implement user nav panel
    }
}
