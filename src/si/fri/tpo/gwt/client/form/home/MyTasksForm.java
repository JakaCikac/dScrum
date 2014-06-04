package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import org.apache.commons.lang3.time.DateUtils;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.form.addedit.WorkHistoryDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

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
    private WorkloadDTO workloadDTO;

    public MyTasksForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, SprintDTO sprintDTO) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
        this.sprintDTO = sprintDTO;
        createMissedWorkload();
    }

    @Override
    public Widget asWidget() {
        if (panel == null) {

            ColumnConfig<TaskDTO, String> statusCol = new ColumnConfig<TaskDTO, String>(getStatusValue(), 200, "Status");
            ColumnConfig<TaskDTO, String> descriptionCol = new ColumnConfig<TaskDTO, String>(getDescriptionValue(), 200, "Description");
            ColumnConfig<TaskDTO, String> userStoryCol = new ColumnConfig<TaskDTO, String>(getUserStoryValue(), 200, "User Story");
            ColumnConfig<TaskDTO, String> workHistoryCol = new ColumnConfig<TaskDTO, String>(getWorkHistoryValue(), 100, "Work History");
            ColumnConfig<TaskDTO, String> startStopCol = new ColumnConfig<TaskDTO, String>(getStartStopValue(), 100, "Start");

            List<ColumnConfig<TaskDTO, ?>> l = new ArrayList<ColumnConfig<TaskDTO, ?>>();
            l.add(descriptionCol);
            l.add(statusCol);
            l.add(userStoryCol);
            l.add(startStopCol);
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

            TextButtonCell startStopButton = new TextButtonCell();
            startStopButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    TaskDTO t = store.get(row);

                    Date today = new Date();
                    for (WorkloadDTO workloadDTO : t.getWorkloadList()) {
                        if (CalendarUtil.isSameDate(workloadDTO.getDay(), today)) {
                            setWorkloadDTO(workloadDTO);
                        }
                    }
                    if(!workloadDTO.getStarted()){
                        //Start
                        workloadDTO.setStarted(true);
                        workloadDTO.setStartTime(today);
                    } else {
                        //Stop
                        workloadDTO.setStarted(false);
                        workloadDTO.setStopTime(today);
                        long start = workloadDTO.getStartTime().getTime();
                        long stop = workloadDTO.getStopTime().getTime();
                        stop -= start;
                        double ure = Math.round(stop/(1000*60*60));
                        int m = (int)(stop-(stop/(1000*60*60))*(1000*60*60))/(1000*60);
                        double minute = (double)m/60.0;
                        minute = minute*10;
                        minute = Math.round(minute);
                        minute = minute/10;
                        double skupaj = ure + minute;
                        double timeSpant = Double.parseDouble(workloadDTO.getTimeSpent());
                        double timeRemaining = Double.parseDouble(workloadDTO.getTimeRemaining());
                        timeSpant += skupaj;
                        timeSpant = timeSpant*10;
                        timeSpant = Math.round(timeSpant);
                        timeSpant = timeSpant/10;
                        timeRemaining -= skupaj;
                        timeRemaining = timeRemaining*10;
                        timeRemaining = Math.round(timeRemaining);
                        timeRemaining = timeRemaining/10;
                        if(timeRemaining <= 0.0){
                            timeRemaining = 0.0;
                            t.setStatus("Completed");
                        }
                        t.setTimeRemaining((int)timeRemaining);

                        workloadDTO.setTimeSpent(String.valueOf(timeSpant));
                        workloadDTO.setTimeRemaining(String.valueOf(timeRemaining));
                    }
                    performUpdateWorkload(workloadDTO, t);
                }
            });
            startStopCol.setCell(startStopButton);

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

    private void performUpdateWorkload(WorkloadDTO workloadDTO, final TaskDTO taskDTO){
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
                    MessageBox amb3 = new MessageBox("Message update Work History", result.getSecond());
                    amb3.show();

                    AsyncCallback<Pair<Boolean, String>> updateTask = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result == null) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing task updating!");
                                amb2.show();
                            } else if (!result.getFirst()) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error updating Task!", result.getSecond());
                                amb2.show();
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    service.updateTask(taskDTO, updateTask);
                    store.update(taskDTO);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.updateWorkload(workloadDTO, updateWorkload);
    }

    private ValueProvider<TaskDTO, String> getStartStopValue() {
        ValueProvider<TaskDTO, String> vpn = new ValueProvider<TaskDTO, String>() {
            @Override
            public String getValue(TaskDTO object) {
                for(WorkloadDTO workloadDTO : object.getWorkloadList()){
                    if(workloadDTO.getStarted()){
                        return "Stop work";
                    }
                }
                return "Start work";
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

    private void createMissedWorkload() {
        for(UserStoryDTO userStoryDTO : sprintDTO.getUserStoryList()){
            for(TaskDTO taskDTO : userStoryDTO.getTaskList()){
                if(taskDTO.getUserUserId() != null && taskDTO.getUserUserId().getUserId() == SessionInfo.userDTO.getUserId()) {
                    boolean end = true, newWorkload = false;
                    String timeRem = String.valueOf(taskDTO.getEstimatedTime());

                    Date assDate = CalendarUtil.copyDate(taskDTO.getAssignedDate()), today = new Date();
                    UserDTO userDTO = taskDTO.getUserUserId();

                    for (WorkloadDTO workloadDTO : taskDTO.getWorkloadList()) {
                        if (workloadDTO.getDay().after(assDate)) {
                            assDate = CalendarUtil.copyDate(workloadDTO.getDay());
                            timeRem = workloadDTO.getTimeRemaining();
                            userDTO = workloadDTO.getUser();
                        }
                    }
                    final Date lastDate = assDate;

                    if(taskDTO.getWorkloadList().size() != 0) {
                        CalendarUtil.addDaysToDate(lastDate, 1);
                    }

                    if (lastDate.equals(today) || lastDate.after(today)) end = false;
                    List<WorkloadDTO> workloadDTOList = new ArrayList<WorkloadDTO>();
                    while (end) {
                        WorkloadDTO workloadDTO = new WorkloadDTO();
                        WorkloadPKDTO workloadPKDTO = new WorkloadPKDTO();
                        workloadPKDTO.setUserUserId(userDTO.getUserId());
                        workloadPKDTO.setTaskTaskId(taskDTO.getTaskPK().getTaskId());
                        workloadPKDTO.setTaskUserStoryStoryId(taskDTO.getUserStory().getStoryId());
                        workloadDTO.setWorkloadPK(workloadPKDTO);

                        workloadDTO.setUser(userDTO);
                        workloadDTO.setTask(taskDTO);
                        workloadDTO.setTimeSpent("0");
                        workloadDTO.setDay(CalendarUtil.copyDate(lastDate));
                        workloadDTO.setTimeRemaining(timeRem);
                        workloadDTO.setStartTime(new Date(0));
                        workloadDTO.setStopTime(new Date(0));
                        workloadDTO.setStarted(false);

                        CalendarUtil.addDaysToDate(lastDate, 1);
                        if (lastDate.equals(today) || lastDate.after(today)) end = false;
                        workloadDTOList.add(workloadDTO);
                        newWorkload = true;
                    }
                    CalendarUtil.addDaysToDate(lastDate, -1);
                    if(newWorkload) {
                        performSaveWorkloadList(workloadDTOList, taskDTO);
                    }
                }
            }
        }
    }

    private void performSaveWorkloadList(final List<WorkloadDTO> workloadDTOList, final TaskDTO taskDTO){
        final AsyncCallback<Pair<Boolean, List<Integer>>> saveWorkload = new AsyncCallback<Pair<Boolean, List<Integer>>>() {
            @Override
            public void onSuccess(Pair<Boolean, List<Integer>> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing work updating!");
                    amb2.show();
                } else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating work!", result.getSecond()+"");
                    amb2.show();
                } else {
                    List<WorkloadDTO> workloadDTOListNew = taskDTO.getWorkloadList();

                    ListIterator litr = result.getSecond().listIterator();
                    for (WorkloadDTO workloadDTO : workloadDTOList){
                        try {
                            if (litr.hasNext()) {
                                WorkloadPKDTO workloadPKDTO = workloadDTO.getWorkloadPK();
                                workloadPKDTO.setWorkloadId((Integer) litr.next());
                                workloadDTO.setWorkloadPK(workloadPKDTO);
                                workloadDTOListNew.add(workloadDTO);
                            } else {
                                AlertMessageBox amb2 = new AlertMessageBox("Error saving workload!", "There was an error while performing workload saving!");
                                amb2.show();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    taskDTO.setWorkloadList(workloadDTOListNew);
                    AsyncCallback<Pair<Boolean, String>> updateTask = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result == null) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing task saving!");
                                amb2.show();
                            }
                            else if (!result.getFirst()) {
                                AlertMessageBox amb2 = new AlertMessageBox("Error updating task!", result.getSecond());
                                amb2.show();
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    service.updateTask(taskDTO, updateTask);
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.saveWorkload(workloadDTOList, saveWorkload);
    }

    public void setWorkloadDTO(WorkloadDTO workloadDTO) {
        this.workloadDTO = workloadDTO;
    }
}
