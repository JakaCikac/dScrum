package si.fri.tpo.gwt.client.form.search;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */
public class UserSearchForm extends VerticalPanel {

        private ColumnModel cm;
        private ContentPanel cp;
        private Grid<UserDTO> grid;
        //private EditorGrid<UserDTO> grid;
        private DScrumServiceAsync service;
        private FormData formData;
        ListStore<UserDTO> listStore = new ListStore<UserDTO>();
        private List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        private TextField<String> name;
        NumberField numberExample;

        private TextField<String> surname;

        public UserSearchForm(DScrumServiceAsync service) {
            this.service = service;
            initComponents();
        }

        private void initComponents() {

            listStore.setDefaultSort("surname", Style.SortDir.ASC);

            FieldSet fieldSet = new FieldSet();
            FormLayout layout = new FormLayout();
            layout.setLabelWidth(100);
            fieldSet.setLayout(layout);
            formData = new FormData("-20");

            name = new TextField<String>();
            name.setFieldLabel("Name");
            name.setAllowBlank(true);
            fieldSet.add(name, formData);

            surname = new TextField<String>();
            surname.setFieldLabel("Surname");
            surname.setAllowBlank(true);
            fieldSet.add(surname, formData);

            /* number example
            number = new NumberField();
            number.setPropertyEditorType(Integer.class);
            number.setFieldLabel("Field name");
            number.setAllowBlank(true);
            fieldSet.add(enrolmentNumber, formData);
            */

            FormPanel simple = new FormPanel();
            simple.setHeading("Search parameters");
            simple.setBorders(false);
            simple.setFrame(true);
            simple.setWidth(438);

            simple.add(fieldSet);
            simple.add(new Button("Search", new SelectionListener<ButtonEvent>() {
                public void componentSelected(ButtonEvent ce) {
                    reconfigureData();
                }
            }));
            add(simple);
            fillColumnConfigs();

            cm = new ColumnModel(configs);
            cp = new ContentPanel();
            cp.setBodyBorder(true);
            cp.setHeading("List of found users");
            cp.setButtonAlign(Style.HorizontalAlignment.CENTER);
            cp.setLayout(new FitLayout());
            cp.setSize(438, 300);
            cp.setExpanded(true);
            grid = new Grid<UserDTO>(listStore, cm);
            //grid = new EditorGrid<UserDTO>(listStore, cm);
            grid.setStyleAttribute("borderTop", "none");
            grid.setBorders(true);
            grid.setStripeRows(true);
            grid.setColumnLines(true);
            grid.setColumnReordering(true);
            grid.getAriaSupport().setLabelledBy(cp.getHeader().getId() + "-label");
            grid.setAutoExpandColumn(configs.get(configs.size() - 1).getId());
            cp.add(grid);

            add(cp);
            new QuickTip(grid);
        }

        public void reconfigureData() {
            listStore.removeAll();

            AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {

                @Override
                public void onSuccess(List<UserDTO> result) {
                    listStore.add(result);
                    listStore.sort("surname", Style.SortDir.ASC);
                    grid.reconfigure(listStore, cm);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
            };
            //service.findUserByFirstAndLastName(name.getValue(), surname.getValue(), callback);
        }


        private void fillColumnConfigs() {
            RowNumberer numberer = new RowNumberer();
            configs.add(numberer);

            ColumnConfig column = new ColumnConfig();
            column.setId("userDTO.user_id");
            column.setAlignment(Style.HorizontalAlignment.LEFT);
            column.setHeader("User ID");
            column.setWidth(70);
            configs.add(column);

            column = new ColumnConfig();
            column.setId("userDTO.username");
            column.setAlignment(Style.HorizontalAlignment.LEFT);
            column.setHeader("Username");
            column.setWidth(80);
            column.setRowHeader(true);
            configs.add(column);

            column = new ColumnConfig();
            column.setId("name");
            column.setAlignment(Style.HorizontalAlignment.LEFT);
            column.setHeader("Name");
            column.setWidth(80);
            column.setRowHeader(true);
            configs.add(column);

            column = new ColumnConfig();
            column.setId("surname");
            column.setAlignment(Style.HorizontalAlignment.LEFT);
            column.setHeader("Surname");
            column.setWidth(80);
            column.setRowHeader(true);
            configs.add(column);

            column = new ColumnConfig();
            column.setId("isAdmin");
            column.setAlignment(Style.HorizontalAlignment.LEFT);
            column.setHeader("Is Admin?");
            column.setWidth(20);
            column.setRowHeader(true);
            configs.add(column);
        }

        public Grid<UserDTO> getGrid() {
            return grid;
        }
}
