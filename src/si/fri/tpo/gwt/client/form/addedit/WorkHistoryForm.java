package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.dev.ModuleTabPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.WorkloadDTO;
import com.google.gwt.editor.client.Editor;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.server.jpa.Workload;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private WorkloadDTO workloadDTO;
    private ListStore<WorkloadDTO> store;
    private Grid<WorkloadDTO> grid;

    private DoubleField workSpent;
    private DoubleField workRemaining;

    private SubmitButton submitButton;

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
        ColumnConfig<WorkloadDTO, String> hoursRemainingCol = new ColumnConfig<WorkloadDTO, String>(getHoursRemaining(), 60, "Remaining (h)");

        List<ColumnConfig<WorkloadDTO, ?>> l = new ArrayList<ColumnConfig<WorkloadDTO, ?>>();
        l.add(taskCreationDateCol);
        l.add(hoursSpenCol);
        l.add(hoursRemainingCol);

        cm = new ColumnModel<WorkloadDTO>(l);
        store = new ListStore<WorkloadDTO>(getModelKeyProvider());
        store.addAll(selectedTaskDTO.getWorkloadList());

        grid = new Grid<WorkloadDTO>(store, cm);
        grid.getView().setAutoExpandColumn(taskCreationDateCol);
        grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        grid.setBorders(true);
        grid.getView().setForceFit(true);

        grid.setWidth(650);
        grid.setHeight(150);

        grid.addRowClickHandler(new RowClickEvent.RowClickHandler() {
            @Override
            public void onRowClick(RowClickEvent event) {
                workSpent.setEnabled(true);
                workRemaining.setEnabled(true);
                workloadDTO = grid.getSelectionModel().getSelectedItem();
                //System.out.println("spent: " +workloadDTO.getTimeSpent());
                //System.out.println("rem: " +workloadDTO.getTimeRemaining());

                if (workloadDTO.getTimeRemaining() == null){
                    workRemaining.setValue(Double.parseDouble(("0.0")));
                    workSpent.setValue(Double.parseDouble(workloadDTO.getTimeSpent()));
                }

                else if (workloadDTO.getTimeSpent() == null){
                    workSpent.setValue(Double.parseDouble(("0.0")));
                    workRemaining.setValue(Double.parseDouble((workloadDTO.getTimeRemaining())));
                }

                else {
                    workSpent.setValue(Double.parseDouble(workloadDTO.getTimeSpent()));
                    workRemaining.setValue(Double.parseDouble((workloadDTO.getTimeRemaining())));
                }
            }
        });

        final FieldLabel taskContainer = new FieldLabel();
        taskContainer.setText("Workload");
        taskContainer.setLabelAlign(FormPanel.LabelAlign.TOP);
        taskContainer.setWidget(grid);

        container.add(taskContainer);
        p.add(container);

        workSpent = new DoubleField();
        workSpent.setEnabled(false);
        workSpent.setAllowBlank(false);
        workSpent.setAllowNegative(false);
        p.add(new FieldLabel(workSpent, "Time spent"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        workRemaining = new DoubleField();
        workRemaining.setEnabled(false);
        workRemaining.setAllowBlank(false);
        workRemaining.setAllowNegative(false);
        p.add(new FieldLabel(workRemaining, "Time remaining"), new VerticalLayoutContainer.VerticalLayoutData(1, 1));

        //submit button
        submitButton = new SubmitButton("Update work");
        submitButton.setEnabled(true);
        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                final WorkloadDTO workloadDTO = getWorkloadDTO();
                List<WorkloadDTO> workloadDTOList = selectedTaskDTO.getWorkloadList();

                /* ------------------------------- VALIDATORS --------------------------------- */

                if(workSpent.getText().equals(null)){
                    AlertMessageBox d = new AlertMessageBox("Work you spent is empty", "Please enter work you spent!");
                    d.show();
                    return;
                }

                if(workRemaining.getText().equals(null)){
                    AlertMessageBox d = new AlertMessageBox("Work you spent is empty", "Please enter work you spent!");
                    d.show();
                    return;
                }

//                if (res > 0){
//                    AlertMessageBox d = new AlertMessageBox("Estimated time is " + estimated + ".", "Write smaller number!");
//                    d.show();
//                    return;
//                }
                /* ----------------------------- END VALIDATORS ------------------------------- */

                workloadDTO.setTimeSpent(String.valueOf(workSpent.getValue()));
                workloadDTO.setTimeRemaining(String.valueOf(workRemaining.getValue()));


                System.out.println("updated spent: " +workloadDTO.getTimeSpent());
                System.out.println("updated rem: " +workloadDTO.getTimeRemaining());


                performUpdateWorkload(workloadDTO);
                System.out.println("--------UPDATE CLICK------------------");
                store.update(workloadDTO);
            //end OnClick
            }
        //end addClickHandler
        });
        panel.addButton(submitButton);
        System.out.println("--------end_of_createWorkHistory-------------");
        verticalPanel.add(panel);
    }

    public WorkloadDTO getWorkloadDTO(){
        return workloadDTO;
    }

    private void performUpdateWorkload(WorkloadDTO workloadDTO){
        AsyncCallback<Pair<Boolean, String>> updateWorkload = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing work updating!");
                    amb2.show();
                } else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating work!", result.getSecond());
                    amb2.show();
                } else {
                    MessageBox amb3 = new MessageBox("Message update Sprint", result.getSecond());
                    amb3.show();
                    UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                    center.add(userHomeForm.asWidget());
                    west.clear();
                    east.clear();
                    SessionInfo.projectDTO = null;
                    if (SessionInfo.userDTO.isAdmin()) {
                        AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                        east.add(adminNavPanel.asWidget());
                    } else {
                        UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                        east.add(userNavPanel.asWidget());
                    }
                    ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                    west.add(psf.asWidget());
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };

    }

    public void emptyForm() {
        workSpent.setText("");
        workRemaining.setText("");
    }

    //MODELKEY PROVIDER
    // return the model key provider for the list store
    private ModelKeyProvider<WorkloadDTO> getModelKeyProvider() {
        ModelKeyProvider<WorkloadDTO> mkp = new ModelKeyProvider<WorkloadDTO>() {
            @Override
            public String getKey(WorkloadDTO item) {
                //System.out.println("itemWokrloadPK: " +item.getWorkloadPK().getWorkloadId());
                return item.getWorkloadPK().getWorkloadId() + "";
            }
        };
        return mkp;
    }

    //VALUE PROVIDERJI
    @Deprecated
    private ValueProvider<WorkloadDTO, String> getTaskCreationDate() {
        ValueProvider<WorkloadDTO, String> vpcd = new ValueProvider<WorkloadDTO, String>() {
            @Override
            public String getValue(WorkloadDTO object) {
                //System.out.println("Date_of_workload: "+DateTimeFormat.getShortDateFormat().format(object.getTask().getAssignedDate()));
                return DateTimeFormat.getShortDateFormat().format(object.getDay());
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
                //System.out.println("Time spent: "+object.getTimeSpent());
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

    private ValueProvider<WorkloadDTO, String> getHoursRemaining() {
        ValueProvider<WorkloadDTO, String> vphr = new ValueProvider<WorkloadDTO, String>() {
            @Override
            public String getValue(WorkloadDTO object) {
                //System.out.println("Time remaining: "+object.getTask().getTimeRemaining());
                return object.getTimeRemaining();
            }
            @Override
            public void setValue(WorkloadDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vphr;
    }
}
