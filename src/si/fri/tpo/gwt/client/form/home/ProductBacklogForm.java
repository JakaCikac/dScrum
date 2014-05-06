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
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.form.addedit.UserStoryEditDialog;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 01/05/14.
 */


public class ProductBacklogForm implements IsWidget {

    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private ListStore<UserStoryDTO> store;
    private ListStore<UserStoryDTO> ufStore;
    private Grid<UserStoryDTO> grid;
    private Grid<UserStoryDTO> ufGrid;
    private TabPanel panel;

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
        if (panel == null) {

            RowExpander<UserStoryDTO> expander = new RowExpander<UserStoryDTO>(new AbstractCell<UserStoryDTO>() {
                @Override
                public void render(Context context, UserStoryDTO value, SafeHtmlBuilder sb) {
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Content:</b> " + value.getContent() + "</p>");
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 5px'><b>Acceptance Tests:</b>" + "</p>");
                    for (AcceptanceTestDTO atDTO : value.getAcceptanceTestList()) {
                        sb.appendHtmlConstant("<p style='margin: 5px 5px 3px'> <b> # </b> " + atDTO.getContent() + "</p>");
                    }
                }
            });

            RowExpander<UserStoryDTO> ufExpander = new RowExpander<UserStoryDTO>(new AbstractCell<UserStoryDTO>() {
                @Override
                public void render(Context context, UserStoryDTO value, SafeHtmlBuilder sb) {
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Content:</b> " + value.getContent() + "</p>");
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 5px'><b>Acceptance Tests:</b>" + "</p>");
                    for (AcceptanceTestDTO atDTO : value.getAcceptanceTestList()) {
                        sb.appendHtmlConstant("<p style='margin: 5px 5px 3px'> <b> # </b> " + atDTO.getContent() + "</p>");
                    }
                }
            });

            ColumnConfig<UserStoryDTO, String> nameCol = new ColumnConfig<UserStoryDTO, String>(getNameValue(), 200, "Name");
            ColumnConfig<UserStoryDTO, String> priorityCol = new ColumnConfig<UserStoryDTO, String>(getPriorityValue(), 100, "Priority");
            ColumnConfig<UserStoryDTO, Double> estimatedTimeCol = new ColumnConfig<UserStoryDTO, Double>(getEstimatedTimeValue(), 125, "Estimated Time (Pt)");
            ColumnConfig<UserStoryDTO, Integer> businessValueCol = new ColumnConfig<UserStoryDTO, Integer>(getBusinessValue(), 30, "Business Value");
            ColumnConfig<UserStoryDTO, String> ufEditColumn = new ColumnConfig<UserStoryDTO, String>(getEditValue(), 80, "Edit");
            ColumnConfig<UserStoryDTO, String> ufDeleteColumn = new ColumnConfig<UserStoryDTO, String>(getDeleteValue(), 80, "Delete");

            TextButtonCell ufEditButton = new TextButtonCell();
            ufEditButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();
                    UserStoryDTO p = ufStore.get(row);
                    if ( p.getSprint() == null) {
                        UserStoryEditDialog sed = new UserStoryEditDialog(service, center, west, east, north, south, p);
                        sed.show();
                    } else {
                        Info.display("User story", "The user story " + p.getName() + " cannot be edited because it is in active sprint.");
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
                    UserStoryDTO p = ufStore.get(row);
                    if ( p.getSprint() == null ) {
                        performDeleteUserStory(p);
                    } else {
                        Info.display("User story", "The user story " + p.getName() + " cannot be deleted because it is in active sprint.");
                    }
                }
            });
            ufDeleteColumn.setCell(ufDeleteButton);

            List<ColumnConfig<UserStoryDTO, ?>> l = new ArrayList<ColumnConfig<UserStoryDTO, ?>>();
            l.add(expander);
            l.add(nameCol);
            l.add(priorityCol);
            l.add(estimatedTimeCol);
            l.add(businessValueCol);
            ColumnModel<UserStoryDTO> cm = new ColumnModel<UserStoryDTO>(l);

            List<ColumnConfig<UserStoryDTO, ?>> ufl = new ArrayList<ColumnConfig<UserStoryDTO, ?>>();
            ufl.add(expander);
            ufl.add(nameCol);
            ufl.add(priorityCol);
            ufl.add(estimatedTimeCol);
            ufl.add(businessValueCol);
            if (isProductOwner() || isScrumMaster()){
                ufl.add(ufEditColumn);
                ufl.add(ufDeleteColumn);
            }
            ColumnModel<UserStoryDTO> ufcm = new ColumnModel<UserStoryDTO>(ufl);


            store= new ListStore<UserStoryDTO>(getModelKeyProvider());
            ufStore= new ListStore<UserStoryDTO>(getModelKeyProvider());
            getStoryList();

            panel = new TabPanel();

            ContentPanel cp = new ContentPanel();
            cp.setHeaderVisible(false);
            cp.setPixelSize(850, 460);
            cp.addStyleName("margin-10");
            ufGrid = new Grid<UserStoryDTO>(ufStore, ufcm);
            ufGrid.getView().setAutoExpandColumn(nameCol);
            ufGrid.setBorders(true);
            ufGrid.getView().setStripeRows(true);
            ufGrid.getView().setColumnLines(true);
            ufGrid.getView().setForceFit(true);
            ufExpander.initPlugin(ufGrid);
            cp.setWidget(ufGrid);
            panel.add(cp, "Unfinished User Stories");

            cp = new ContentPanel();
            cp.setHeaderVisible(false);
            cp.setPixelSize(850, 460);
            cp.addStyleName("margin-10");

            grid = new Grid<UserStoryDTO>(store, cm);
            grid.getView().setAutoExpandColumn(nameCol);
            grid.setBorders(true);
            grid.getView().setStripeRows(true);
            grid.getView().setColumnLines(true);
            grid.getView().setForceFit(true);
            expander.initPlugin(grid);
            cp.setWidget(grid);
            panel.add(cp, "Finished User Stories");

        }
        return panel;
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
                    south.clear();
                    SessionInfo.projectDTO = null;
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

    private void getStoryList() {
        AsyncCallback<List<UserStoryDTO>> callback = new AsyncCallback<List<UserStoryDTO>>() {
            @Override
            public void onSuccess(List<UserStoryDTO> result) {
                for (UserStoryDTO usDTO : result) {
                    if (usDTO.getStatus().equals("Finished")) {
                        store.add(usDTO);
                    } else if (usDTO.getStatus().equals("Unfinished")){
                        ufStore.add(usDTO);
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
