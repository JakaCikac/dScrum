package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.addedit.WorkHistoryDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anze on 12. 05. 14.
 */

/*
Taski, grupirani po user storyju (vendar le od prijavljenega uporabnika -> SessionInfo.userDTO).
Izpisuj status in description. Prek taska dobis tudi ime user storyja (za ime grupe).
 */

public class MyTasksForm implements IsWidget{
    private ContentPanel panel, center, west, east, north, south;
    private DScrumServiceAsync service;
    private ListStore<TaskDTO> store;
    private Grid<TaskDTO> grid;
    private SprintDTO sprintDTO;

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

            ColumnConfig<TaskDTO, String> statusCol = new ColumnConfig<TaskDTO, String>(getStatusValue(), 200, "Status");
            ColumnConfig<TaskDTO, String> descriptionCol = new ColumnConfig<TaskDTO, String>(getDescriptionValue(), 200, "Description");
            ColumnConfig<TaskDTO, String> userStoryCol = new ColumnConfig<TaskDTO, String>(getUserStoryValue(), 200, "User Story");
            ColumnConfig<TaskDTO, String> workHistoryCol = new ColumnConfig<TaskDTO, String>(getWorkHistoryValue(), 100, "Work History");

            List<ColumnConfig<TaskDTO, ?>> l = new ArrayList<ColumnConfig<TaskDTO, ?>>();
            l.add(descriptionCol);
            l.add(statusCol);
            l.add(userStoryCol);
            l.add(workHistoryCol);
            ColumnModel<TaskDTO> cm = new ColumnModel<TaskDTO>(l);

            TextButtonCell workHistoryButton = new TextButtonCell();
            workHistoryButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    TaskDTO t = store.get(row);
                    WorkHistoryDialog whd = new WorkHistoryDialog(service, center, west, east, north, south, t);
                    whd.show();
                }
            });
            workHistoryCol.setCell(workHistoryButton);

            store = new ListStore<TaskDTO>(getModelKeyProvider());
            getTaskList();

            panel = new ContentPanel();
            panel.setHeadingText("User's Tasklist");
            panel .setHeaderVisible(false);
            panel.setPixelSize(850, 460);
            panel.addStyleName("margin-10");

            final GroupingView<TaskDTO> viewUserStory = new GroupingView<TaskDTO>();
            viewUserStory.setForceFit(true);
            grid = new Grid<TaskDTO>(store, cm);
            grid.setView(viewUserStory);
            grid.getView().setAutoExpandColumn(descriptionCol);
            viewUserStory.groupBy(userStoryCol);
            viewUserStory.setEnableGroupingMenu(false);
            grid.setBorders(true);
            grid.getView().setStripeRows(true);
            grid.getView().setColumnLines(true);
            grid.getView().setForceFit(true);
            panel.setWidget(grid);
        }
        return panel;
    }

    private void getTaskList() {
        for (UserStoryDTO usDTO : sprintDTO.getUserStoryList()) {
            for (TaskDTO tDTO : usDTO.getTaskList()) {
                if (tDTO.getUserUserId() != null && tDTO.getUserUserId().getUserId().equals(SessionInfo.userDTO.getUserId())) {
                    store.add(tDTO);
                }
            }
        }
    }

    private ValueProvider<TaskDTO, String> getUserStoryValue() {
        ValueProvider<TaskDTO, String> vpn = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return object.getUserStory().getName();
            }
            @Override
            public void setValue(TaskDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<TaskDTO, String> getDescriptionValue() {
        ValueProvider<TaskDTO, String> vpn = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return object.getDescription();
            }
            @Override
            public void setValue(TaskDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<TaskDTO, String> getStatusValue() {
        ValueProvider<TaskDTO, String> vpn = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return object.getStatus();
            }
            @Override
            public void setValue(TaskDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    // return the model key provider for the list store
    private ModelKeyProvider<TaskDTO> getModelKeyProvider() {
        ModelKeyProvider<TaskDTO> mkp = new ModelKeyProvider<TaskDTO>() {
            @Override
            public String getKey(TaskDTO item) {
                return item.getTaskPK().getTaskId() + "";
            }
        };
        return mkp;
    }

    //TO DO !!!!
    private ValueProvider<TaskDTO, String> getWorkHistoryValue() {
        ValueProvider<TaskDTO, String> vpn = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                return "Work History";
            }
            @Override
            public void setValue(TaskDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }
}
