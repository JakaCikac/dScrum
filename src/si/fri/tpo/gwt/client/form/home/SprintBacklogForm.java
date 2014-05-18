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
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.form.addedit.AcceptEditTasksDialog;
import si.fri.tpo.gwt.client.form.addedit.UserStoryCommentDialog;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.registration.TaskRegistrationDialog;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
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
    private UserDTO user;

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
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 3px'><b>Content:</b></p>");
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 2px'>" + value.getContent().replaceAll("\n", "</br>") + "</p>");
                    sb.appendHtmlConstant("<p style='margin: 15px 5px 3px'><b>Acceptance Tests:</b></p>");
                    for (AcceptanceTestDTO atDTO : value.getAcceptanceTestList()) {
                        sb.appendHtmlConstant("<p style='margin: 5px 5px 2px'> <b> # </b> " + atDTO.getContent() + "</p>");
                    }
                    if(value.getComment() != null && value.getComment().length()!=0) {
                        sb.appendHtmlConstant("<p style='margin: 15px 5px 3px'><b>Comment:</b></p>");
                        sb.appendHtmlConstant("<p style='margin: 5px 5px 2px'>" + value.getComment().replaceAll("\n", "</br>") + "</p>");
                    }
                    // TODO: add "Edit" button
                    if (value.getTaskList() != null) {
                        sb.appendHtmlConstant("<table style='margin: 15px 5px 3px'><thead><th><b>Task:</b></th><th><b>Status/action</b></th><th><b>Member</b></th><th><b>Remaining</b></th><tbody>");
                        int time = 0;
                        for (TaskDTO taskDTO : value.getTaskList()) {
                            sb.appendHtmlConstant("<tr>");
                            sb.appendHtmlConstant("<td>" + taskDTO.getDescription() + "</td>");
                            sb.appendHtmlConstant("<td>" + taskDTO.getStatus() + "</td>");
                            if(taskDTO.getUserUserId()!=null) {
                                sb.appendHtmlConstant("<td>" + taskDTO.getUserUserId().getUsername() + "</td>");
                            } else if(taskDTO.getPreassignedUserName()!=null) {
                                //TODO: get preassignet user from DB
                                sb.appendHtmlConstant("<td>" + taskDTO.getPreassignedUserName() + "</td>");
                                //sb.appendHtmlConstant("<td></td>");
                            } else sb.appendHtmlConstant("<td>/</td>");
                            sb.appendHtmlConstant("<td>" + taskDTO.getTimeRemaining() + " h</td>");
                            time += taskDTO.getTimeRemaining();
                            sb.appendHtmlConstant("</tr>");
                        }
                        sb.appendHtmlConstant("</tbody>");
                        sb.appendHtmlConstant("<tfoot><td></td><td></td><td>Sum:</td><td>" + time + " h</td></tfoot>");
                        sb.appendHtmlConstant("</table>");
                    }
                }
            });

            ColumnConfig<UserStoryDTO, String> nameCol = new ColumnConfig<UserStoryDTO, String>(getNameValue(), 100, "Name");
            ColumnConfig<UserStoryDTO, String> priorityCol = new ColumnConfig<UserStoryDTO, String>(getPriorityValue(), 100, "Priority");
            ColumnConfig<UserStoryDTO, Double> estimatedTimeCol = new ColumnConfig<UserStoryDTO, Double>(getEstimatedTimeValue(), 50, "Estimated Time (Pt)");
            ColumnConfig<UserStoryDTO, Integer> businessValueCol = new ColumnConfig<UserStoryDTO, Integer>(getBusinessValue(), 40, "Business Value");
            ColumnConfig<UserStoryDTO, String> addTaskColumn = new ColumnConfig<UserStoryDTO, String>(getTaskValue(), 60, "Add Task");
            ColumnConfig<UserStoryDTO, String> confirmColumn = new ColumnConfig<UserStoryDTO, String>(getConfirmValue(), 60, "Confirm");
            ColumnConfig<UserStoryDTO, String> rejectColumn = new ColumnConfig<UserStoryDTO, String>(getRejectValue(), 60, "Reject");
            ColumnConfig<UserStoryDTO, String> ufNoteColumn = new ColumnConfig<UserStoryDTO, String>(getNoteValue(), 40, "Note");
            ColumnConfig<UserStoryDTO, String> acceptEditTasksColumn = new ColumnConfig<UserStoryDTO, String>(getAcceptEditTasksValue(), 70, "Accept/Edit tasks");

            TextButtonCell addTaskButton = new TextButtonCell();
            addTaskButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = store.get(row);
                    TaskRegistrationDialog trd = new TaskRegistrationDialog(service, center, west, east, north, south, p);
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

            TextButtonCell rejectButton = new TextButtonCell();
            rejectButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = store.get(row);
                    p.setSprint(null); // Odvzemi zgodbo iz sprinta.
                    performUpdateUserStory(p);
                }
            });
            rejectColumn.setCell(rejectButton);

            TextButtonCell ufNoteButton = new TextButtonCell();
            ufNoteButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = store.get(row);
                    UserStoryCommentDialog uscd = new UserStoryCommentDialog(service, center, west, east, north, south, p);
                    uscd.show();
                }
            });
            ufNoteColumn.setCell(ufNoteButton);

            TextButtonCell acceptEditTasksButton = new TextButtonCell();
            acceptEditTasksButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = store.get(row);
                    AcceptEditTasksDialog eatd = new AcceptEditTasksDialog(service, center, west, east, north, south, p);
                    eatd.show();
                    //UserStoryCommentDialog uscd = new UserStoryCommentDialog(service, center, west, east, north, south, p);
                    //uscd.show();
                }
            });
            acceptEditTasksColumn.setCell(acceptEditTasksButton);

            List<ColumnConfig<UserStoryDTO, ?>> l = new ArrayList<ColumnConfig<UserStoryDTO, ?>>();
            l.add(expander);
            l.add(nameCol);
            l.add(priorityCol);
            l.add(estimatedTimeCol);
            l.add(businessValueCol);
            l.add(addTaskColumn);
            l.add(ufNoteColumn);
            l.add(acceptEditTasksColumn);
            if(productOwner) {
                l.add(confirmColumn);
                l.add(rejectColumn);
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
                    if (userStoryDTO.getSprint() == null){
                        MessageBox amb3 = new MessageBox("Message update User Story", "User story " + userStoryDTO.getName() +"is rejected.");
                        amb3.show();
                        UserStoryCommentDialog uscd = new UserStoryCommentDialog(service, center, west, east, north, south, userStoryDTO); // Omogoci PO da doda komentar pri rejectu.
                        uscd.show();
                    } else {
                        MessageBox amb3 = new MessageBox("Message update User Story", "User story " + userStoryDTO.getName() +"is finished.");
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

    private ValueProvider<UserStoryDTO, String> getNoteValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return "Note";
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

    private ValueProvider<UserStoryDTO, String> getAcceptEditTasksValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return "Accept/Edit tasks";
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

    private ValueProvider<UserStoryDTO, String> getRejectValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return "Reject";
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

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
