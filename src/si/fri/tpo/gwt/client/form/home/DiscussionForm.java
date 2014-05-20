package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.TextArea;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.form.addedit.UserStoryCommentDialog;
import si.fri.tpo.gwt.client.form.addedit.UserStoryEditDialog;
import si.fri.tpo.gwt.client.form.navigation.AdminNavPanel;
import si.fri.tpo.gwt.client.form.navigation.UserNavPanel;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.server.jpa.Comment;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nanorax on 20/05/14.
 */
public class DiscussionForm implements IsWidget {

    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private ListStore<DiscussionDTO> store;
    private Grid<DiscussionDTO> grid;
    private VerticalPanel vp;
    private TabPanel tabPane;
    private com.google.gwt.user.client.ui.TextArea ta;

    public DiscussionForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    @Override
    public Widget asWidget() {
        if (vp == null) {

            vp = new VerticalPanel();

            VerticalLayoutContainer p = new VerticalLayoutContainer();
            vp.add(p);

            FramedPanel fp = new FramedPanel();
            VerticalLayoutContainer fpp = new VerticalLayoutContainer();
            fp.add(fpp);
            fp.setHeaderVisible(false);
            ta = new TextArea();
            ta.setWidth("750");
            ta.setHeight("350");
            fpp.add(ta);

            TextButton addButton = new TextButton("Add");
            addButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    DiscussionDTO dDTO = new DiscussionDTO();

                    dDTO.setCreatetime(new Date());
                    dDTO.setContent(ta.getValue());
                    dDTO.setUser(SessionInfo.userDTO);

                    AsyncCallback<Pair<Boolean, String>> callback = new AsyncCallback<Pair<Boolean, String>>() {
                        @Override
                        public void onSuccess(Pair<Boolean, String> result) {
                            if (result.getFirst()) {
                                getDiscussionList();
                                grid.getView().refresh(true);
                            } else if (!result.getFirst()) {
                                Window.alert("Error saving discussion!");
                            }
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
                    };
                    service.saveDiscussion(dDTO, SessionInfo.projectDTO, callback);
                }
            });

            fpp.add(addButton);
            fp.add(fpp);
            vp.add(fp);

            ColumnConfig<DiscussionDTO, String> timestampCol = new ColumnConfig<DiscussionDTO, String>(getTimestampValue(), 150, "Date/Time");
            ColumnConfig<DiscussionDTO, String> contentCol = new ColumnConfig<DiscussionDTO, String>(getContentValue(), 250, "Content");
            ColumnConfig<DiscussionDTO, String> authorCol = new ColumnConfig<DiscussionDTO, String>(getAuthorValue(), 160, "Author");

            List<ColumnConfig<DiscussionDTO, ?>> l = new ArrayList<ColumnConfig<DiscussionDTO, ?>>();
            l.add(timestampCol);
            l.add(authorCol);
            l.add(contentCol);

            ColumnModel<DiscussionDTO> cm = new ColumnModel<DiscussionDTO>(l);

            store = new ListStore<DiscussionDTO>(getModelKeyProvider());
            getDiscussionList();

            tabPane = new TabPanel();

            ContentPanel cp = new ContentPanel();
            cp.setHeaderVisible(false);
            cp.setPixelSize(850, 460);
            cp.addStyleName("margin-10");

            final GroupingView<DiscussionDTO> viewSprint = new GroupingView<DiscussionDTO>();
            viewSprint.setForceFit(true);
            ContentPanel lol = new ContentPanel();
            grid = new Grid<DiscussionDTO>(store, cm);
            grid.setView(viewSprint);
            //grid.getView().setAutoExpandColumn(nameCol);
            //viewSprint.groupBy(sprintColumn);
            //viewSprint.setEnableGroupingMenu(false);
            grid.setBorders(true);
            grid.getView().setStripeRows(true);
            grid.getView().setColumnLines(true);
            grid.getView().setForceFit(true);
            lol.setWidget(grid);
            cp.add(lol);
            tabPane.add(cp, "Discussion");
            vp.add(tabPane);
        }
        return vp;
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

    private void getDiscussionList() {
        AsyncCallback<List<DiscussionDTO>> callback = new AsyncCallback<List<DiscussionDTO>>() {
            @Override
            public void onSuccess(List<DiscussionDTO> result) {
                store.clear();
                store.addAll(result);
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findAllDiscussionsByProject(SessionInfo.projectDTO, callback);
    }

    // return the model key provider for the list store
    private ModelKeyProvider<DiscussionDTO> getModelKeyProvider() {
        ModelKeyProvider<DiscussionDTO> mkp = new ModelKeyProvider<DiscussionDTO>() {
            @Override
            public String getKey(DiscussionDTO item) {
                return item.getDiscussionPK().getDiscussionId() + "";
            }
        };
        return mkp;
    }

    private ValueProvider<DiscussionDTO, String> getContentValue() {
        ValueProvider<DiscussionDTO, String> vpn = new ValueProvider<DiscussionDTO, String>() {
            @Override
            public String getValue(DiscussionDTO object) {
                return object.getContent();
            }
            @Override
            public void setValue(DiscussionDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<DiscussionDTO, String> getAuthorValue() {
        ValueProvider<DiscussionDTO, String> vpn = new ValueProvider<DiscussionDTO, String>() {
            @Override
            public String getValue(DiscussionDTO object) {
                return object.getUser().getUsername();
            }
            @Override
            public void setValue(DiscussionDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

    private ValueProvider<DiscussionDTO, String> getTimestampValue() {
        ValueProvider<DiscussionDTO, String> vpn = new ValueProvider<DiscussionDTO, String>() {
            @Override
            public String getValue(DiscussionDTO object) {
                return object.getCreatetime().toString();
            }
            @Override
            public void setValue(DiscussionDTO object, String value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpn;
    }

}
