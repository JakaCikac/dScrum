package si.fri.tpo.gwt.client.form.addedit;

/*import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;*/
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 07/04/14.
 */
public abstract class AbstractAddEditForm<X extends BaseModelData> {// extends LayoutContainer {
   /* private DScrumServiceAsync service;
    ListStore<X> listStore = new ListStore<X>();
    private List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
    private ColumnModel cm;
    private ContentPanel cp;
    private Grid<X> grid;
    private Button addButton;
    private Button editButton;
    private Button deleteButton;
    private Label countLabel;

    private final int WIDTH = 500;
    private final int HEIGHT = 300;


    public AbstractAddEditForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setSize(WIDTH, HEIGHT+100);
        initForm();
        show();
    }

    private void initForm() {
        fillColumnConfigs();

        countLabel = new Label("# of entries: 0");
        cm = new ColumnModel(configs);
        cp = new ContentPanel();
        cp.setBodyBorder(true);
        cp.setHeading(setFormTitle());
        cp.setButtonAlign(Style.HorizontalAlignment.CENTER);
        cp.setLayout(new FitLayout());
        cp.setSize(WIDTH, HEIGHT);
        cp.setExpanded(true);
        grid = new Grid<X>(listStore, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setBorders(true);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.getAriaSupport().setLabelledBy(cp.getHeader().getId() + "-label");
        grid.setAutoExpandColumn(getConfigs().get(getConfigs().size() - 1).getId());
        cp.add(grid);

        AsyncCallback<List<X>> callback = new AsyncCallback<List<X>>() {

            @Override
            public void onSuccess(List<X> result) {
                listStore.add(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        performCallService(callback);
        //setCountValue(listStore.getCount());
        add(cp);

        new QuickTip(grid);
        //add(countLabel);

        addButton = new Button("Add", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                setAddEntryAction();
            }
        });

        add(addButton);

        editButton = new Button("Edit", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                setEditEntryAction();
            }
        });
        editButton.setEnabled(false);
        add(editButton);

        deleteButton = new Button("Delete", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                MessageBox.confirm("Deleting entries", "Are you sure?", new Listener<MessageBoxEvent>() {
                    public void handleEvent(MessageBoxEvent be) {
                        if (be.getButtonClicked().getItemId().equals("yes")) {
                            AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

                                @Override
                                public void onSuccess(Boolean success) {
                                    reconfigureData();
                                }

                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert(caught.getMessage());
                                }
                            };
                            setDeleteEntryAction(callback);
                        }
                    }
                });

            }
        });
        deleteButton.setEnabled(false);
        add(deleteButton);

        grid.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<X>() {
            @Override
            public void selectionChanged(SelectionChangedEvent<X> se) {
                X x = se.getSelectedItem();
                editButton.setEnabled(x != null);
                deleteButton.setEnabled(x != null);

            }
        });

        preprareButtonsState();
    }

    public void setCountValue(int count) {
        countLabel.setText("# of entries: " + count);
    }

    public DScrumServiceAsync getService() {
        return service;
    }

    public ListStore<X> getListStore() {
        return listStore;
    }

    public List<ColumnConfig> getConfigs() {
        return configs;
    }

    public Grid<X> getGrid() {
        return grid;
    }

    public void reconfigureData() {
        listStore.removeAll();

        AsyncCallback<List<X>> callback = new AsyncCallback<List<X>>() {

            @Override
            public void onSuccess(List<X> result) {
                listStore.add(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        performCallService(callback);
        setCountValue(listStore.getCount());
        grid.reconfigure(listStore, cm);
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    protected abstract void preprareButtonsState();

    public abstract void fillColumnConfigs();

    protected abstract String setFormTitle();

    protected abstract void performCallService(AsyncCallback<List<X>> callback);

    protected abstract void setDeleteEntryAction(AsyncCallback<Boolean> callback);

    protected abstract void setEditEntryAction();

    protected abstract void setAddEntryAction();
*/
}
