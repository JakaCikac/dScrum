package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
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
            System.out.println("Creating home form..");
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

            HTML shortText = new HTML("Hello world.");
            //shortText.addStyleName("pad-text");
            folder.add(shortText, "Short Text");

            HTML longText = new HTML("Tomorrow a " + "<br><br>" + " great day.");
            //longText.addStyleName("pad-text");
            folder.add(longText, "Long Text");

            final PlainTabPanel panel = new PlainTabPanel();
            panel.setPixelSize(450, 250);
            //panel.addSelectionHandler(handler);

            Label normal = new Label("Just a plain old tab");
            //normal.addStyleName("pad-text");
            panel.add(normal, "Normal");

            Label iconTab = new Label("Just a plain old tab with an icon");
            //iconTab.addStyleName("pad-text");

            TabItemConfig config = new TabItemConfig("Icon Tab");
            //config.setIcon(Resources.IMAGES.table());
            panel.add(iconTab, config);

            Label disabled = new Label("This tab should be disabled");
            //disabled.addStyleName("pad-text");

            config = new TabItemConfig("Disabled");
            config.setEnabled(false);
            panel.add(disabled, config);

            vp.add(folder);
            vp.add(panel);
        }
        System.out.println("Returning home form.");

        return vp;
    }

}