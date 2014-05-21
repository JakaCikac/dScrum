package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.WorkloadDTO;
import com.google.gwt.editor.client.Editor;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 5/18/2014.
 */
public class WorkHistoryForm implements IsWidget, Editor<WorkloadDTO>  {

    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private WorkHistoryDialog whd;
    private ColumnModel<WorkloadDTO> cm;

    private VerticalPanel verticalPanel;
    private FlowPanel container;
    private TaskDTO selectedTaskDTO;
    private WorkloadDTO selectedWorkloadDTO;
    private ListStore<WorkloadDTO> store;
    private Grid<WorkloadDTO> grid;

    private IntegerField workSpent;
    private IntegerField workRemaining;

    public WorkHistoryForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, TaskDTO tDTO, WorkHistoryDialog whd) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.selectedTaskDTO = tDTO;
        this.whd = whd;
    }

    @Override
    public Widget asWidget() {
        if (verticalPanel == null) {
            verticalPanel = new VerticalPanel();
            verticalPanel.setSpacing(10);
            createWorkHistory();
        }
        return verticalPanel;
    }

    public void createWorkHistory() {
        FramedPanel panel = new FramedPanel();
        panel.setHeaderVisible(false);
        panel.setWidth(660);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        // create store and grid for acceptance tests
        container = new FlowPanel();

        // create store and grid for acceptance tests
        RowNumberer<WorkloadDTO> numberer = new RowNumberer<WorkloadDTO>();
        @Deprecated
        ColumnConfig<WorkloadDTO, String> taskCreationDateCol = new ColumnConfig<WorkloadDTO, String>(getTaskCreationDate(), 100, "Date");
        ColumnConfig<WorkloadDTO, String> hoursSpenCol = new ColumnConfig<WorkloadDTO, String>(getHoursSpent(), 60, "Work spent (h)");
        ColumnConfig<WorkloadDTO, Integer> hoursRemainingCol = new ColumnConfig<WorkloadDTO, Integer>(getHoursRemaining(), 60, "Remaining (h)");

        List<ColumnConfig<WorkloadDTO, ?>> l = new ArrayList<ColumnConfig<WorkloadDTO, ?>>();
        l.add(taskCreationDateCol);
        l.add(hoursSpenCol);
        l.add(hoursRemainingCol);

        cm = new ColumnModel<WorkloadDTO>(l);
        store = new ListStore<WorkloadDTO>(getModelKeyProvider());
        store.addAll(selectedTaskDTO.getWorkloadList());
        System.out.println("Test---------------------------Test");

        grid = new Grid<WorkloadDTO>(store, cm);
        grid.getView().setAutoExpandColumn(taskCreationDateCol);
        grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        grid.setBorders(true);
        grid.getView().setForceFit(true);

        grid.setWidth(650);
        grid.setHeight(150);

        FieldLabel taskContainer = new FieldLabel();
        taskContainer.setText("Workload");
        taskContainer.setLabelAlign(FormPanel.LabelAlign.TOP);
        taskContainer.setWidget(grid);

        container.add(taskContainer);
        p.add(container);

        workSpent = new IntegerField();
        workSpent.setEnabled(false);
        workSpent.setAllowBlank(false);
        workSpent.setAllowNegative(false);
        p.add(new FieldLabel(workSpent, "Time spent"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        workRemaining = new IntegerField();
        workRemaining.setEnabled(false);
        workRemaining.setAllowBlank(false);
        workRemaining.setAllowNegative(false);
        p.add(new FieldLabel(workRemaining, "Time remaining"), new VerticalLayoutContainer.VerticalLayoutData(1, 1));

        verticalPanel.add(panel);
    }

    // return the model key provider for the list store
    private ModelKeyProvider<WorkloadDTO> getModelKeyProvider() {
        ModelKeyProvider<WorkloadDTO> mkp = new ModelKeyProvider<WorkloadDTO>() {
            @Override
            public String getKey(WorkloadDTO item) {
                System.out.println("itemWokrloadPK: " +item.getWorkloadPK().getWorkloadId());
                return item.getWorkloadPK().getWorkloadId() + "";
            }
        };
        return mkp;
    }

    @Deprecated
    private ValueProvider<WorkloadDTO, String> getTaskCreationDate() {
        ValueProvider<WorkloadDTO, String> vpcd = new ValueProvider<WorkloadDTO, String>() {
            @Override
            public String getValue(WorkloadDTO object) {
                System.out.println("DateTimFormat-assignedDate: "+DateTimeFormat.getShortDateFormat().format(object.getTask().getAssignedDate()));
                return DateTimeFormat.getShortDateFormat().format(object.getTask().getAssignedDate());
            }
            @Override
            public void setValue(WorkloadDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpcd;
    }

    private ValueProvider<WorkloadDTO, String> getHoursSpent() {
        ValueProvider<WorkloadDTO, String> vphs = new ValueProvider<WorkloadDTO, String>() {
            @Override
            public String getValue(WorkloadDTO object) {
                System.out.println("Time spent: "+object.getTimeSpent());
                return object.getTimeSpent();
            }
            @Override
            public void setValue(WorkloadDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vphs;
    }

    private ValueProvider<WorkloadDTO, Integer> getHoursRemaining() {
        ValueProvider<WorkloadDTO, Integer> vphr = new ValueProvider<WorkloadDTO, Integer>() {
            @Override
            public Integer getValue(WorkloadDTO object) {
                //NAROBE! WORKLOAD! in ne task!!!
                System.out.println("Time remaining: "+object.getTask().getTimeRemaining());
                return object.getTask().getTimeRemaining();
            }
            @Override
            public void setValue(WorkloadDTO object, Integer value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vphr;
    }
}
