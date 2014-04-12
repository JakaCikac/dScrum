package si.fri.tpo.gwt.client.form.search;

/**
 * Created by nanorax on 12/04/14.
 */

import java.util.*;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

public class SingleUserSearch implements IsWidget {

    private DScrumServiceAsync service;
    private VerticalPanel verticalPanel;
    private ListStore<UserDTO> store;
    private UserDTO userDTO;
    private Grid<UserDTO> grid;

    public SingleUserSearch(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    public Widget asWidget() {
        if (verticalPanel == null) {
            verticalPanel = new VerticalPanel();

            VerticalLayoutContainer p = new VerticalLayoutContainer();
            verticalPanel.add(p);

            // Create search by username
            HorizontalPanel hp = new HorizontalPanel();
            ContentPanel panel;

            TextField searchField = new TextField();
            panel = new ContentPanel();
            panel.setHeaderVisible(false);
            panel.add(searchField);
            hp.add(panel);

            TextButton searchButton = new TextButton("Search");
            panel = new ContentPanel();
            panel.setHeaderVisible(false);
            panel.add(searchButton);
            hp.add(panel);

            p.add(hp);

            // TODO: add search button and field
            final NumberFormat number = NumberFormat.getFormat("0.00");

            RowNumberer<UserDTO> numberer = new RowNumberer<UserDTO>();
            ColumnConfig<UserDTO, String> usernameCol = new ColumnConfig<UserDTO, String>(getUsernameValue(), 100, "Username");
            ColumnConfig<UserDTO, String> firstNameCol = new ColumnConfig<UserDTO, String>(getFirstNameValue(), 100, "First Name");
            ColumnConfig<UserDTO, String> lastNameCol = new ColumnConfig<UserDTO, String>(getLastNameValue(), 100, "Last Name");
            ColumnConfig<UserDTO, Boolean> isAdminCol = new ColumnConfig<UserDTO, Boolean>(getIsAdminValue(), 80, "Is Admin?");
            isAdminCol.setCell(new AbstractCell<Boolean>() {

                @Override
                public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
                    String style = "style='color: " + (value == false ? "red" : "green") + "'";
                    sb.appendHtmlConstant("<span " + style + " qtitle='Change' qtip='" + value + "'>" + value + "</span>");
                }
            });

            List<ColumnConfig<UserDTO, ?>> l = new ArrayList<ColumnConfig<UserDTO, ?>>();
            l.add(numberer);
            l.add(usernameCol);
            l.add(firstNameCol);
            l.add(lastNameCol);
            l.add(isAdminCol);
            ColumnModel<UserDTO> cm = new ColumnModel<UserDTO>(l);

            // initialize list store with model key provider (username)
            store = new ListStore<UserDTO>(getModelKeyProvider());
            // retrieve user List and add data to list store
            getUserList();

            grid = new Grid<UserDTO>(store, cm);
            grid.getView().setAutoExpandColumn(usernameCol);
            grid.setBorders(false);
            grid.getView().setStripeRows(true);
            grid.getView().setColumnLines(true);
            grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

            numberer.initPlugin(grid);

            panel = new ContentPanel();
            panel.setHeadingText("User selection form");
            panel.setPixelSize(600, 320);
            panel.addStyleName("margin-10");

            panel.setWidget(grid);
            p.add(panel);
        }

        return verticalPanel;
    }

    private void setDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getDTO() {
        setDTO(grid.getSelectionModel().getSelectedItem());
        return this.userDTO;
    }

    private void getUserList() {
        AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onSuccess(List<UserDTO> result) {
                // add list to ListStore
                store.addAll(result);
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.findAllUsers(callback);
    }

    // return the model key provider for the list store
    private  ModelKeyProvider<UserDTO> getModelKeyProvider() {
        ModelKeyProvider<UserDTO> mkp = new ModelKeyProvider<UserDTO>() {
            @Override
            public String getKey(UserDTO item) {
                return item.getUsername();
            }
        };
        return mkp;
    }

    private ValueProvider<UserDTO, String> getUsernameValue() {
        ValueProvider<UserDTO, String> vpu = new ValueProvider<UserDTO, String>() {
            @Override
            public String getValue(UserDTO object) {
                return object.getUsername();
            }
            @Override
            public void setValue(UserDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpu;
    }

    private ValueProvider<UserDTO, String> getFirstNameValue() {
        ValueProvider<UserDTO, String> vpfn = new ValueProvider<UserDTO, String>() {
            @Override
            public String getValue(UserDTO object) {
                return object.getFirstName();
            }
            @Override
            public void setValue(UserDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpfn;
    }
    private ValueProvider<UserDTO, String> getLastNameValue() {
        ValueProvider<UserDTO, String> vpln = new ValueProvider<UserDTO, String>() {
            @Override
            public String getValue(UserDTO object) {
                return object.getLastName();
            }
            @Override
            public void setValue(UserDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpln;
    }

    private ValueProvider<UserDTO, Boolean> getIsAdminValue() {
       ValueProvider<UserDTO, Boolean> vpia = new ValueProvider<UserDTO, Boolean>() {
           @Override
           public Boolean getValue(UserDTO object) {
               return object.isAdmin();
           }
           @Override
           public void setValue(UserDTO object, Boolean value) {

           }
           @Override
           public String getPath() {
               return null;
           }
       };
        return vpia;
    }
}