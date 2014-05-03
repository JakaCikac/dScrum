package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
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
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.form.registration.TaskRegistrationDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anze on 2. 05. 14.
 */
public class SprintBacklogForm  implements IsWidget{

    private ContentPanel panel, center, west, east, north, south;
    private DScrumServiceAsync service;
    private SprintDTO sprintDTO;
    private ListStore<UserStoryDTO> store;
    private Grid<UserStoryDTO> grid;

    public SprintBacklogForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south, SprintDTO sprintDTO) {
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
        boolean productOwner = SessionInfo.projectDTO.getTeamTeamId().getProductOwnerId()==SessionInfo.userDTO.getUserId();
        if (panel == null) {

            RowExpander<UserStoryDTO> expander = new RowExpander<UserStoryDTO>(new AbstractCell<UserStoryDTO>() {
                @Override
                public void render(Context context, UserStoryDTO value, SafeHtmlBuilder sb) {
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 3px'><b>Content:</b> " + value.getContent() + "</p>");
                    sb.appendHtmlConstant("<p style='margin: 12px 5px 5px'><b>Acceptance Tests:</b>" + "</p>");
                    // TODO: add acceptance tests lists
                    for (AcceptanceTestDTO atDTO : value.getAcceptanceTestList()) {
                        sb.appendHtmlConstant("<p style='margin: 5px 5px 3px'> <b> # </b> " + atDTO.getContent() + "</p>");
                    }
                    // TODO: add "Edit" button
                    if (value.getTaskList() != null) {
                        sb.appendHtmlConstant("<p style='margin: 12px 5px 5px'><b>Task:</b>" + "</p>");
                        for (TaskDTO taskDTO : value.getTaskList()) {
                            sb.appendHtmlConstant("<p style='margin: 5px 5px 3px'>" + taskDTO.getDescription() + "</p>");
                        }
                    }
                }
            });

            ColumnConfig<UserStoryDTO, String> nameCol = new ColumnConfig<UserStoryDTO, String>(getNameValue(), 100, "Name");
            ColumnConfig<UserStoryDTO, String> priorityCol = new ColumnConfig<UserStoryDTO, String>(getPriorityValue(), 100, "Priority");
            ColumnConfig<UserStoryDTO, Double> estimatedTimeCol = new ColumnConfig<UserStoryDTO, Double>(getEstimatedTimeValue(), 100, "Estimated Time (Pt)");
            ColumnConfig<UserStoryDTO, Integer> businessValueCol = new ColumnConfig<UserStoryDTO, Integer>(getBusinessValue(), 80, "Business Value");
            ColumnConfig<UserStoryDTO, String> addTaskColumn = new ColumnConfig<UserStoryDTO, String>(getTaskValue(), 80, "Add Task");
            ColumnConfig<UserStoryDTO, String> confirmColumn = new ColumnConfig<UserStoryDTO, String>(getConfirmValue(), 80, "Confirm");

            TextButtonCell addTaskButton = new TextButtonCell();
            addTaskButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = store.get(row);
                    TaskRegistrationDialog trd = new TaskRegistrationDialog(service, center, west, east, p);
                    trd.show();

                }
            });
            addTaskColumn.setCell(addTaskButton);

            TextButtonCell confirmButton = new TextButtonCell();
            confirmButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = store.get(row);
                    Info.display("Event", "The " + p.getName() + " was clicked.");
                    if (storyCompleted(p)){
                        p.setStatus("Finished");
                        performUpdateUserStory(p);
                    } else {
                        AlertMessageBox amb2 = new AlertMessageBox("User Story not completed!", "Tasks are not completed yet.");
                        amb2.show();
                    }
                }
            });
            confirmColumn.setCell(confirmButton);


            List<ColumnConfig<UserStoryDTO, ?>> l = new ArrayList<ColumnConfig<UserStoryDTO, ?>>();
            l.add(expander);
            l.add(nameCol);
            l.add(priorityCol);
            l.add(estimatedTimeCol);
            l.add(businessValueCol);
            l.add(addTaskColumn);
            if(productOwner) {
                l.add(confirmColumn);
            }
            ColumnModel<UserStoryDTO> cm = new ColumnModel<UserStoryDTO>(l);



            store= new ListStore<UserStoryDTO>(getModelKeyProvider());
            getStoryList();

            panel = new ContentPanel();
            panel.setHeadingText("User Story list");
            panel.setPixelSize(850, 460);
            panel.addStyleName("margin-10");

            grid = new Grid<UserStoryDTO>(store, cm);
            grid.getView().setAutoExpandColumn(nameCol);
            grid.setBorders(true);
            grid.getView().setStripeRows(true);
            grid.getView().setColumnLines(true);

            grid.getView().setForceFit(true);

            expander.initPlugin(grid);
            panel.setWidget(grid);
        }
        return panel;
    }

    private void performUpdateUserStory(final UserStoryDTO userStoryDTO) {
        AsyncCallback<Pair<Boolean, String>> updateUserStory = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing user story updating!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error updating user story!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message update User Story", "User story " + userStoryDTO.getName() +"is finished.");
                    amb3.show();
                    UserHomeForm userHomeForm = new UserHomeForm(service, center, west, east, north, south);
                    center.add(userHomeForm.asWidget());
                    west.clear();
                    east.clear();
                    SessionInfo.projectDTO = null;
                    if (SessionInfo.userDTO.isAdmin()){
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
        service.updateUserStory(userStoryDTO, updateUserStory);
    }

    private boolean storyCompleted(UserStoryDTO userStoryDTO) {
        for (TaskDTO taskDTO : userStoryDTO.getTaskList()){
            if (taskDTO.getTimeRemaining() != 0){
                return false;
            }
        }
        return true;
    }

    private void getStoryList() {
        for (UserStoryDTO userStoryDTO : sprintDTO.getUserStoryList()){
            if (!userStoryDTO.getStatus().equals("Finished")) {
                store.add(userStoryDTO);
            }
        }
    }

    private ValueProvider<UserStoryDTO, String> getTaskValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return "Add Task";
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    // return the model key provider for the list store
    private ModelKeyProvider<UserStoryDTO> getModelKeyProvider() {
        ModelKeyProvider<UserStoryDTO> mkp = new ModelKeyProvider<UserStoryDTO>() {
            @Override
            public String getKey(UserStoryDTO item) {
                return item.getName();
            }
        };
        return mkp;
    }

    private ValueProvider<UserStoryDTO, Integer> getBusinessValue() {
        ValueProvider<UserStoryDTO, Integer> vpn = new ValueProvider<UserStoryDTO, Integer>() {
            @Override
            public Integer getValue(UserStoryDTO object) {
                return object.getBusinessValue();
            }
            @Override
            public void setValue(UserStoryDTO object, Integer value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<UserStoryDTO, Double> getEstimatedTimeValue() {
        ValueProvider<UserStoryDTO, Double> vpn = new ValueProvider<UserStoryDTO, Double>() {
            @Override
            public Double getValue(UserStoryDTO object) {
                if(object.getEstimateTime() != null)
                    return object.getEstimateTime().doubleValue();
                else return 0.0;
            }
            @Override
            public void setValue(UserStoryDTO object, Double value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<UserStoryDTO, String> getNameValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return object.getName();
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<UserStoryDTO, String> getConfirmValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return "Confirm";
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<UserStoryDTO, String> getPriorityValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return object.getPriorityPriorityId().getName();
            }
            @Override
            public void setValue(UserStoryDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

}
