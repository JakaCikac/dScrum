package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by anze on 12. 05. 14.
 */
public class MyTasksForm implements IsWidget{
    private ContentPanel panel, center, west, east, north, south;
    private DScrumServiceAsync service;
    private SprintDTO sprintDTO;
    private ListStore<UserStoryDTO> store;
    private Grid<UserStoryDTO> grid;

    public MyTasksForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, SprintDTO sprintDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
        this.sprintDTO = sprintDTO;
    }

    @Override
    public Widget asWidget() {
        if (panel == null) {
            panel = new ContentPanel();
            panel.setHeadingText("User Story list");
            panel.setPixelSize(850, 460);
            panel.addStyleName("margin-10");
        }
        return panel;
    }

}
