package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.Date;

/**
 * Created by nanorax on 02/05/14.
 */
public class UserStoryEditDialog extends Dialog {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east, north, south;

    public UserStoryEditDialog(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
            this.service = service;
            this.center = center;
            this.west = west;
            this.east = east;
            this.north = north;
            this.south = south;

            // Layout
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

            layout.setCenterWidget(new UserStoryEditForm(this.service, this.center, this.west, this.east).asWidget());

        }
}


