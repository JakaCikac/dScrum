package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
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
import si.fri.tpo.gwt.client.dto.*;
import com.google.gwt.editor.client.Editor;
import si.fri.tpo.gwt.client.form.home.NorthForm;
import si.fri.tpo.gwt.client.form.home.UserHomeForm;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.server.jpa.TaskPK;
import si.fri.tpo.gwt.server.jpa.Workblock;
import si.fri.tpo.gwt.server.jpa.Workload;

import java.util.Date;
import java.util.*;

/**
 * Created by Administrator on 5/18/2014.
 */
public class WorkHistoryForm implements IsWidget  {

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
    private List<WorkloadDTO> workloadDTOList, workloadDTOListNEW;
    private Comparator<WorkloadDTO> wbSortByDate;

    private DoubleField workSpent;
    private DoubleField workRemaining;

    private SubmitButton submitButton;
    private SubmitButton startButton;

    private Date today = new Date();
    private Date lastDay;
    private long forDay;
    private int dateDifference = 0;
    private String timeRem="";
    private int countClicks = 0;

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
        taskCreationDateCol.setSortable(true);
        ColumnConfig<WorkloadDTO, String> hoursSpenCol = new ColumnConfig<WorkloadDTO, String>(getHoursSpent(), 60, "Work spent (h)");
        ColumnConfig<WorkloadDTO, String> hoursRemainingCol = new ColumnConfig<WorkloadDTO, String>(getHoursRemaining(), 60, "Remaining (h)");

        List<ColumnConfig<WorkloadDTO, ?>> l = new ArrayList<ColumnConfig<WorkloadDTO, ?>>();
        l.add(taskCreationDateCol);
        l.add(hoursSpenCol);
        l.add(hoursRemainingCol);

        wbSortByDate = new WBSortByDate();

        cm = new ColumnModel<WorkloadDTO>(l);
        store = new ListStore<WorkloadDTO>(getModelKeyProvider());

        //list for sorting date in a workload
        workloadDTOList = selectedTaskDTO.getWorkloadList();
        Collections.sort(workloadDTOList, wbSortByDate);
        store.addAll(workloadDTOList);
        final WorkloadDTO lastElement;

        if (workloadDTOList.isEmpty()){
            lastElement = workloadDTO;
            lastDay = selectedTaskDTO.getAssignedDate();
            timeRem = (String.valueOf(selectedTaskDTO.getEstimatedTime()));
        }

        else {
            lastElement =  workloadDTOList.get(workloadDTOList.size() - 1);
            lastDay = lastElement.getDay();
            timeRem = lastElement.getTimeRemaining();
        }

        dateDifference = (int)(today.getTime() - lastDay.getTime())/(1000*60*60*24);
        forDay = lastDay.getTime()+(1000*60*60*24);

        workloadDTOListNEW = new ArrayList<WorkloadDTO>();
        for (int i=0; i<dateDifference; i++){
            WorkloadDTO addWL = new WorkloadDTO();
            WorkloadPKDTO workloadPKDTO = new WorkloadPKDTO();
            workloadPKDTO.setUserUserId(selectedTaskDTO.getUserUserId().getUserId());
            workloadPKDTO.setTaskTaskId(selectedTaskDTO.getTaskPK().getTaskId());
            workloadPKDTO.setTaskUserStoryStoryId(selectedTaskDTO.getUserStory().getStoryId());
            addWL.setWorkloadPK(workloadPKDTO);

            addWL.setUser(selectedTaskDTO.getUserUserId());
            addWL.setTask(selectedTaskDTO);
            addWL.setTimeSpent("0");
            addWL.setDay(new Date(forDay));
            addWL.setTimeRemaining(timeRem);

            forDay = forDay +(1000*60*60*24);

            workloadDTOListNEW.add(addWL);
        }
        performSaveWorkload(workloadDTOListNEW);

        lastDay = null;
        long forDay = 0;
        int dateDifference = 0;

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
                double est = (double)selectedTaskDTO.getEstimatedTime();
                double rem = workRemaining.getValue();

                //if workRemaining==0 -> you have finished your work! :)
                if (selectedTaskDTO.getEstimatedTime()!=0 && (workRemaining.getValue())==0){
                    //System.out.println("ID taska:" + selectedTaskDTO.getTaskPK().getTaskId());
                    selectedTaskDTO.setStatus("Completed");
                    selectedTaskDTO.setTimeRemaining(0);
                    MessageBox d = new MessageBox("Congratz!", "You just finished your task!");
                    performUpdateTask(selectedTaskDTO);
                    d.show();
                }

                //if workRemaining!=0 -> taskStatus == Assigned
                if (selectedTaskDTO.getEstimatedTime()!=0 && (workRemaining.getValue())!=0){
                    //System.out.println("ID taska:" + selectedTaskDTO.getTaskPK().getTaskId());
                    selectedTaskDTO.setStatus("Assigned");
                    performUpdateTask(selectedTaskDTO);
                }

                /* ----------------------------- END VALIDATORS ------------------------------- */

                //update remaining time @ task - Zaključevanje nalog
                int lastTimeRemaining;
                double doubleLastTimeRemaining;
                doubleLastTimeRemaining = Double.parseDouble(lastElement.getTimeRemaining());
                lastTimeRemaining = (int)doubleLastTimeRemaining;

                //round to 1decimal number and save to base
                double wSpent = workSpent.getValue()*10;
                wSpent = Math.round(wSpent);
                wSpent = wSpent/10;

                double wRemaining = workRemaining.getValue()*10;
                wRemaining = Math.round(wRemaining);
                wRemaining = wRemaining/10;

                if (workloadDTO.getDay().compareTo(lastElement.getDay())==0){
                    //System.out.println("----------zadnji dan!--------------");
                    lastTimeRemaining = (int)wRemaining;
                }

                selectedTaskDTO.setTimeRemaining(lastTimeRemaining);

                workloadDTO.setTimeSpent(String.valueOf(wSpent));
                workloadDTO.setTimeRemaining(String.valueOf(wRemaining));

                performUpdateTask(selectedTaskDTO); //zakljucevanje nalog
                performUpdateWorkload(workloadDTO);
                store.update(workloadDTO);
            //end OnClick
            }
        //end addClickHandler
        });

        //Start work button
        startButton = new SubmitButton("Start work");
        startButton.setEnabled(true);
        startButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (countClicks == 0){
                    startButton.setText("Stop work");
                    countClicks = 1;
                }
                else {
                    startButton.setText("Start work");
                    countClicks = 0;
                }
                final WorkloadDTO workloadDTO = getWorkloadDTO();
                List<WorkloadDTO> workloadDTOList = selectedTaskDTO.getWorkloadList();

                /* ------------------------------- VALIDATORS --------------------------------- */

                //if workRemaining==0 -> you have finished your work! :)
                if (selectedTaskDTO.getEstimatedTime()!=0 && (workRemaining.getValue())==0){
                    //System.out.println("ID taska:" + selectedTaskDTO.getTaskPK().getTaskId());
                    selectedTaskDTO.setStatus("Completed");
                    selectedTaskDTO.setTimeRemaining(0);
                    MessageBox d = new MessageBox("Congratz!", "You just finished your task!");
                    performUpdateTask(selectedTaskDTO);
                    d.show();
                }

                //if workRemaining!=0 -> taskStatus == Assigned
                if (selectedTaskDTO.getEstimatedTime()!=0 && (workRemaining.getValue())!=0){
                    //System.out.println("ID taska:" + selectedTaskDTO.getTaskPK().getTaskId());
                    selectedTaskDTO.setStatus("Assigned");
                    performUpdateTask(selectedTaskDTO);
                }
                /* ----------------------------- END VALIDATORS ------------------------------- */


            }
            //end addClickHandler
        });

        panel.addButton(startButton);
        panel.addButton(submitButton);
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
                    SessionInfo.projectDTO = null;
                    north.clear();
                    west.clear();
                    east.clear();
                    center.clear();
                    NorthForm nf = new NorthForm(service, center, north, south, east, west);
                    north.add(nf.asWidget());
                    if (SessionInfo.userDTO.isAdmin()) {
                        AdminNavPanel adminNavPanel = new AdminNavPanel(center, west, east, north, south, service);
                        east.add(adminNavPanel.asWidget());
                    } else {
                        UserNavPanel userNavPanel = new UserNavPanel(service, center, west, east, north, south);
                        east.add(userNavPanel.asWidget());
                    }
                    ProjectSelectForm psf = new ProjectSelectForm(service, center, west, east, north, south);
                    west.add(psf.asWidget());
                    UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                    center.add(userHomeForm.asWidget());
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.updateWorkload(workloadDTO, updateWorkload);
    }

    private void performSaveWorkload(List<WorkloadDTO> workloadDTOListSave){
        AsyncCallback<Pair<Boolean, List<Integer>>> saveWorkload = new AsyncCallback<Pair<Boolean, List<Integer>>>() {
            @Override
            public void onSuccess(Pair<Boolean, List<Integer>> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing work updating!");
                    amb2.show();
                } else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating work!", result.getSecond()+"");
                    amb2.show();
                } else {
                    //List<WorkloadDTO> workloadDTOListN = new ArrayList<WorkloadDTO>();

                    if ( workloadDTOListNEW.size() == result.getSecond().size()) {
                        ListIterator litr = result.getSecond().listIterator();
                        for (WorkloadDTO workloadDTO1 : workloadDTOListNEW){
                            if(litr.hasNext()) {
                                WorkloadPKDTO workloadPKDTO = workloadDTO1.getWorkloadPK();
                                workloadPKDTO.setWorkloadId((Integer)litr.next());
                                workloadDTO1.setWorkloadPK(workloadPKDTO);
                                workloadDTOList.add(workloadDTO1);
                            } else {
                                errorMessage("Error saving acceptance test!", "There was an error while performing acceptance test saving!");
                            }
                        }
                    } else {
                        errorMessage("Error saving acceptance test!", "There was an error while performing acceptance test saving!");
                    }

                    //ponovno sortiraj
                    Collections.sort(workloadDTOList, wbSortByDate);
                    //shrani v store
                    store.addAll(workloadDTOList);

                    selectedTaskDTO.setWorkloadList(workloadDTOList);
                    AsyncCallback<Pair<Boolean, String>> updateTask = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    service.updateTask(selectedTaskDTO, updateTask);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveWorkload(workloadDTOListSave, saveWorkload);
    }

    private void performSaveWorkblock(WorkblockDTO wbdto) {
        AsyncCallback<Pair<Boolean, Integer>> saveWorkblock = new AsyncCallback<Pair<Boolean, Integer>>() {
            @Override
            public void onSuccess(Pair<Boolean, Integer> result) {
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveWorkblock(wbdto, saveWorkblock);
    }


    private void performUpdateTask(TaskDTO p) {
        AsyncCallback<Pair<Boolean, String>> updateTask = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.updateTask(p, updateTask);
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
    private void errorMessage(String s, String s1) {
        AlertMessageBox amb = new AlertMessageBox(s, s1);
        amb.show();
    }
}

//sort dates
class WBSortByDate implements Comparator<WorkloadDTO> {

    @Override
    public int compare(WorkloadDTO a, WorkloadDTO b) {

        int i = a.getDay().compareTo(b.getDay());
        return i;
    }
}
