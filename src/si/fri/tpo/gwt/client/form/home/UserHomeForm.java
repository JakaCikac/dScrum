package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserHomeForm implements IsWidget {

    private VerticalPanel vp;
    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;

    public UserHomeForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            //String txt = TestData.DUMMY_TEXT_SHORT;

            /* SelectionHandler<Widget> handler = new SelectionHandler<Widget>() {
                @Override
                public void onSelection(SelectionEvent<Widget> event) {
                    TabPanel panel = (TabPanel) event.getSource();
                    Widget w = event.getSelectedItem();
                    TabItemConfig config = panel.getConfig(w);
                    Info.display("Message", "'" + config.getText() + "' Selected");
                }
            }; */

            TabPanel folder = new TabPanel();
            //folder.addSelectionHandler(handler);
            folder.setWidth(450);

            ContentPanel cp = new ContentPanel();
            cp.add(new Label("Project waaaalz"));
            folder.add(cp, "Project wall");

            if (SessionInfo.projectDTO == null) {
                cp = new ContentPanel();
                cp.add(new Label("No project selected :)"));
                folder.add(cp, "Product backlog");
            }
            else {
                folder.add(new ProductBacklogForm(service, center, west, east, north, south).asWidget(), "Product Backlog");
            }

            vp.add(folder);
        }

        return vp;
    }

}