package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.*;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.CommentDTO;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.form.addedit.DiscussionCommentDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

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
    private ContentPanel vp;
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

            vp = new ContentPanel();
            vp.setHeaderVisible(false);

            VerticalLayoutContainer fpp = new VerticalLayoutContainer();
            ta = new TextArea();
            ta.setPixelSize(400, 75);
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

            RowExpander<DiscussionDTO> expander = new RowExpander<DiscussionDTO>(new AbstractCell<DiscussionDTO>() {
                @Override
                public void render(Context context, DiscussionDTO value, SafeHtmlBuilder sb) {
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 3px'><b>Content:</b></p>");
                    sb.appendHtmlConstant("<p style='margin: 5px 5px 2px'>" + value.getContent().replaceAll("\n", "</br>") + "</p>");
                    sb.appendHtmlConstant("<p style='margin: 15px 5px 3px'><b>Comments:</b></p>");
                    List<CommentDTO> commentDTOList = value.getCommentList();
                    sb.appendHtmlConstant("<table><tr><th>Date</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Author</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Content</th></tr>");
                    for (CommentDTO commentDTO : commentDTOList) {
                        sb.appendHtmlConstant("<tr>");
                        sb.appendHtmlConstant("<td>" + commentDTO.getCreatetime().toString().substring(4, 16) + "</td><td></td><td>" + commentDTO.getUser().getUsername() + "</td><td></td><td>" + commentDTO.getContent() + "</td>");
                        sb.appendHtmlConstant("</tr>");
                    }
                    sb.appendHtmlConstant("</table>");
                }
            });

            ColumnConfig<DiscussionDTO, String> timestampCol = new ColumnConfig<DiscussionDTO, String>(getTimestampValue(), 150, "Date/Time");
            ColumnConfig<DiscussionDTO, String> contentCol = new ColumnConfig<DiscussionDTO, String>(getContentValue(), 250, "Content");
            ColumnConfig<DiscussionDTO, String> authorCol = new ColumnConfig<DiscussionDTO, String>(getAuthorValue(), 160, "Author");
            ColumnConfig<DiscussionDTO, String> addCommentButtonCol = new ColumnConfig<DiscussionDTO, String>(getAddCommentValue(), 160, "Comment");

            List<ColumnConfig<DiscussionDTO, ?>> l = new ArrayList<ColumnConfig<DiscussionDTO, ?>>();
            l.add(expander);
            l.add(timestampCol);
            l.add(authorCol);
            l.add(contentCol);
            l.add(addCommentButtonCol);

            TextButtonCell addCommentButton = new TextButtonCell();
            addCommentButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    Cell.Context c = event.getContext();
                    int row = c.getIndex();

                }
            });
            addCommentButtonCol.setCell(addCommentButton);

            ColumnModel<DiscussionDTO> cm = new ColumnModel<DiscussionDTO>(l);

            store = new ListStore<DiscussionDTO>(getModelKeyProvider());
            getDiscussionList();

            tabPane = new TabPanel();

            ContentPanel cp = new ContentPanel();
            cp.setHeaderVisible(false);
            cp.setPixelSize(center.getOffsetWidth(), center.getOffsetHeight()-150);
            cp.addStyleName("margin-10");

            final GroupingView<DiscussionDTO> viewDiscussion = new GroupingView<DiscussionDTO>();
            viewDiscussion.setForceFit(true);
            grid = new Grid<DiscussionDTO>(store, cm);
            grid.setView(viewDiscussion);
            grid.getView().setAutoExpandColumn(timestampCol);
            //viewDiscussion.groupBy(contentCol);
            viewDiscussion.setEnableGroupingMenu(false);
            grid.setBorders(true);
            grid.getView().setStripeRows(true);
            grid.getView().setColumnLines(true);
            grid.getView().setForceFit(true);
            expander.initPlugin(grid);
            cp.add(grid);
            tabPane.add(cp, "Discussion");
            fpp.add(tabPane);
            vp.add(fpp);
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
                return DateTimeFormat.getMediumDateTimeFormat().format(object.getCreatetime());
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

    private ValueProvider<DiscussionDTO, String> getAddCommentValue() {
        ValueProvider<DiscussionDTO, String> vpn = new ValueProvider<DiscussionDTO, String>() {
            @Override
            public String getValue(DiscussionDTO object) {
                return "Comment";
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
