package si.fri.tpo.gwt.client.form.search;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 12/04/14.
 */
public class SingleUserSearchDialog extends Dialog {

    private DScrumServiceAsync service;

    public SingleUserSearchDialog(DScrumServiceAsync service) {
        this.service = service;


            setBodyBorder(false);
            setHeadingText("BorderLayout Dialog");
            setWidth(400);
            setHeight(225);
            setHideOnButtonClick(true);

            BorderLayoutContainer layout = new BorderLayoutContainer();
            add(layout);

            // Layout - west
            ContentPanel panel = new ContentPanel();
            panel.setHeadingText("West");
            BorderLayoutContainer.BorderLayoutData data = new BorderLayoutContainer.BorderLayoutData(150);
            data.setMargins(new Margins(0, 5, 0, 0));
            panel.setLayoutData(data);
            layout.setWestWidget(panel);

            // Layout - center
            panel = new ContentPanel();
            panel.setHeadingText("Center");
            SingleUserSearch sus = new SingleUserSearch(service);
            panel.add(sus.asWidget());
            layout.setCenterWidget(panel);
        }
}

