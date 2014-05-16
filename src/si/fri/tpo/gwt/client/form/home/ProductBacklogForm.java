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
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.addedit.UserStoryEditDialog;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nanorax on 01/05/14.
 */


public class ProductBacklogForm implements IsWidget {

    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private ListStore<UserStoryDTO> store;
    private ListStore<UserStoryDTO> ufSprintStore;
    private Grid<UserStoryDTO> grid;
    private Grid<UserStoryDTO> ufSprintGrid;
    private TabPanel tabPane;

    public ProductBacklogForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    @Override
    public Widget asWidget() {
        if (tabPane == null) {

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
                        sb.appendHtmlConstant("<p style='margin: 5px 5px 2px'>" + value.getComment() + "</p>");
                    }
                }
            });

            RowExpander<UserStoryDTO> ufExpander = new RowExpander<UserStoryDTO>(new AbstractCell<UserStoryDTO>() {
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
                        sb.appendHtmlConstant("<p style='margin: 5px 5px 2px'>" + value.getComment() + "</p>");
                    }
                }
            });

            ColumnConfig<UserStoryDTO, String> nameCol = new ColumnConfig<UserStoryDTO, String>(getNameValue(), 200, "Name");
            ColumnConfig<UserStoryDTO, String> priorityCol = new ColumnConfig<UserStoryDTO, String>(getPriorityValue(), 70, "Priority");
            ColumnConfig<UserStoryDTO, String> estimatedTimeCol = new ColumnConfig<UserStoryDTO, String>(getEstimatedTimeValue(), 120, "Estimated Time (Pt)");
            ColumnConfig<UserStoryDTO, Integer> businessValueCol = new ColumnConfig<UserStoryDTO, Integer>(getBusinessValue(), 100, "Business Value");
            ColumnConfig<UserStoryDTO, String> ufEditColumn = new ColumnConfig<UserStoryDTO, String>(getEditValue(), 90, "Edit");
            ColumnConfig<UserStoryDTO, String> ufDeleteColumn = new ColumnConfig<UserStoryDTO, String>(getDeleteValue(), 130, "Delete");
            ColumnConfig<UserStoryDTO, String> ufNoteColumn = new ColumnConfig<UserStoryDTO, String>(getNoteValue(), 100, "Note");
            ColumnConfig<UserStoryDTO, String> sprintColumn = new ColumnConfig<UserStoryDTO, String>(getSprintValue(), 50, "Sprint");

            TextButtonCell ufEditButton = new TextButtonCell();
            ufEditButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = ufSprintStore.get(row);
                    if ( p.getSprint().getStartDate().equals(new Date()) || p.getSprint().getEndDate().equals(new Date()) ||
                    p.getSprint().getStartDate().before(new Date()) && p.getSprint().getEndDate().after(new Date())) {
                        Info.display("User story", "The user story " + p.getName() + " cannot be edited because it is in active sprint.");
                    } else {
                        UserStoryEditDialog sed = new UserStoryEditDialog(service, center, west, east, north, south, p);
                        sed.show();
                    }
                }
            });
            ufEditColumn.setCell(ufEditButton);

            TextButtonCell ufDeleteButton = new TextButtonCell();
            ufDeleteButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = ufSprintStore.get(row);
                    if ( p.getSprint().getStartDate().equals(new Date()) || p.getSprint().getEndDate().equals(new Date()) ||
                            p.getSprint().getStartDate().before(new Date()) && p.getSprint().getEndDate().after(new Date())) {
                        Info.display("User story", "The user story " + p.getName() + " cannot be deleted because it is in active sprint.");
                    } else {
                        performDeleteUserStory(p);
                    }
                }
            });
            ufDeleteColumn.setCell(ufDeleteButton);

            TextButtonCell ufNoteButton = new TextButtonCell();
            ufNoteButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    // TODO: Add dialog to insert comment
                }
            });
            ufNoteColumn.setCell(ufNoteButton);

            List<ColumnConfig<UserStoryDTO, ?>> l = new ArrayList<ColumnConfig<UserStoryDTO, ?>>();
            l.add(expander);
            l.add(nameCol);
            l.add(priorityCol);
            l.add(estimatedTimeCol);
            l.add(businessValueCol);
            l.add(sprintColumn);
            ColumnModel<UserStoryDTO> cm = new ColumnModel<UserStoryDTO>(l);

            List<ColumnConfig<UserStoryDTO, ?>> ufSl = new ArrayList<ColumnConfig<UserStoryDTO, ?>>();
            ufSl.add(ufExpander);
            ufSl.add(nameCol);
            ufSl.add(priorityCol);
            ufSl.add(sprintColumn);
            ufSl.add(estimatedTimeCol);
            ufSl.add(businessValueCol);
            ufSl.add(ufNoteColumn);
            if(isProductOwner() || isScrumMaster()) {
                ufSl.add(ufEditColumn);
                ufSl.add(ufDeleteColumn);
            }
            ColumnModel<UserStoryDTO> ufScm = new ColumnModel<UserStoryDTO>(ufSl);

            store= new ListStore<UserStoryDTO>(getModelKeyProvider());
            ufSprintStore = new ListStore<UserStoryDTO>(getModelKeyProvider());
            getStoryList();

            tabPane = new TabPanel();

            ContentPanel cp = new ContentPanel();
            cp.setHeaderVisible(false);
            cp.setPixelSize(850, 460);
            cp.addStyleName("margin-10");

            final GroupingView<UserStoryDTO> viewSprint = new GroupingView<UserStoryDTO>();
            viewSprint.setForceFit(true);
            ContentPanel lol = new ContentPanel();
            ufSprintGrid = new Grid<UserStoryDTO>(ufSprintStore, ufScm);
            ufSprintGrid.setView(viewSprint);
            ufSprintGrid.getView().setAutoExpandColumn(nameCol);
            viewSprint.groupBy(sprintColumn);
            viewSprint.setEnableGroupingMenu(false);
            ufSprintGrid.setBorders(true);
            ufSprintGrid.getView().setStripeRows(true);
            ufSprintGrid.getView().setColumnLines(true);
            ufSprintGrid.getView().setForceFit(true);
            ufExpander.initPlugin(ufSprintGrid);
            lol.setWidget(ufSprintGrid);
            cp.add(lol);
            tabPane.add(cp, "Unfinished User Stories");


            cp = new ContentPanel();
            cp.setHeaderVisible(false);
            cp.setPixelSize(850, 460);
            cp.addStyleName("margin-10");

            final GroupingView<UserStoryDTO> view = new GroupingView<UserStoryDTO>();
            view.setForceFit(true);
            grid = new Grid<UserStoryDTO>(store, cm);
            grid.setView(view);
            grid.getView().setAutoExpandColumn(nameCol);
            view.groupBy(sprintColumn);
            view.setEnableGroupingMenu(false);
            grid.setBorders(true);
            grid.getView().setStripeRows(true);
            grid.getView().setColumnLines(true);
            expander.initPlugin(grid);
            cp.setWidget(grid);
            tabPane.add(cp, "Finished User Stories");
        }
        return tabPane;
    }

    private boolean isProductOwner() {
        if (SessionInfo.projectDTO.getTeamTeamId().getProductOwnerId() == SessionInfo.userDTO.getUserId()){
            return true;
        }
        return false;
    }

    private boolean isScrumMaster() {
        if (SessionInfo.projectDTO.getTeamTeamId().getScrumMasterId() == SessionInfo.userDTO.getUserId()){
            return true;
        }
        return false;
    }

    private void performDeleteUserStory(UserStoryDTO userStoryDTO) {
        AsyncCallback<Pair<Boolean, String>> deleteUserStory = new AsyncCallback<Pair<Boolean, String>>() {
            @Override
            public void onSuccess(Pair<Boolean, String> result) {
                if (result == null) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error!", "Error while performing user story deleting!");
                    amb2.show();
                }
                else if (!result.getFirst()) {
                    AlertMessageBox amb2 = new AlertMessageBox("Error deleting user story!", result.getSecond());
                    amb2.show();
                }
                else {
                    MessageBox amb3 = new MessageBox("Message delete User Story", result.getSecond());
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
        service.deleteUserStory(userStoryDTO, deleteUserStory);
    }

    private void getStoryList() {
        AsyncCallback<List<UserStoryDTO>> callback = new AsyncCallback<List<UserStoryDTO>>() {
            @Override
            public void onSuccess(List<UserStoryDTO> result) {
                store.clear();
                ufSprintStore.clear();
                for (UserStoryDTO usDTO : result) {
                    if (usDTO.getStatus().equals("Finished")) {
                        store.add(usDTO);
                    } else if (usDTO.getStatus().equals("Unfinished")){
                        ufSprintStore.add(usDTO);
                    }
                }
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findAllStoriesByProject(SessionInfo.projectDTO, callback);
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

    private ValueProvider<UserStoryDTO, String> getSprintValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                if(object.getSprint() != null){
                    if(object.getSprint().getSprintPK() != null){
                        int id = object.getSprint().getSprintPK().getSprintId();
                        return "Sprint "+id;
                    }
                }
                return "/";
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

    private ValueProvider<UserStoryDTO, String> getDeleteValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return "Delete";
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

    private ValueProvider<UserStoryDTO, String> getEstimatedTimeValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                if(object.getEstimateTime() != null)
                    return object.getEstimateTime().toString();
                else return "/";
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

    private ValueProvider<UserStoryDTO, String> getEditValue() {
        ValueProvider<UserStoryDTO, String> vpn = new ValueProvider<UserStoryDTO, String>() {
            @Override
            public String getValue(UserStoryDTO object) {
                return "Edit";
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
