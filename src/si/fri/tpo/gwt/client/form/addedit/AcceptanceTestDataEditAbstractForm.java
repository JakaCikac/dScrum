package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.event.StoreAddEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowNumberer;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;
import si.fri.tpo.gwt.server.jpa.AcceptanceTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 26/04/14.
 */
public abstract class AcceptanceTestDataEditAbstractForm implements IsWidget {

    protected Grid<AcceptanceTestDTO> grid;

    private List<AcceptanceTestDTO> acceptanceTestList;
    private FramedPanel panel;
    private ContentPanel center, west, east;
    private DScrumServiceAsync service;
    private UserStoryDTO storyDTO;
    static private int acceptanceTestCount;
    private TextField contentTF;
    private ListStore<AcceptanceTestDTO> store;
    private AcceptanceTestDTO tempDTO;
    private boolean firstTimeEdit = true;

    public AcceptanceTestDataEditAbstractForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.acceptanceTestCount = 0;

    }

    @Override
    public Widget asWidget() {
        if (panel == null) {

            RowNumberer<AcceptanceTestDTO> numberer = new RowNumberer<AcceptanceTestDTO>();
            //numberer.setWidth(200);
            ColumnConfig<AcceptanceTestDTO, String> content = new ColumnConfig<AcceptanceTestDTO, String>(getContentValue(), 300, "Content");

            List<ColumnConfig<AcceptanceTestDTO, ?>> l = new ArrayList<ColumnConfig<AcceptanceTestDTO, ?>>();
            l.add(numberer);
            l.add(content);

            ColumnModel<AcceptanceTestDTO> cm = new ColumnModel<AcceptanceTestDTO>(l);

            store = new ListStore<AcceptanceTestDTO>(getModelKeyProvider());
            //store.setAutoCommit(true);
            grid.getStore().addStoreAddHandler(new StoreAddEvent.StoreAddHandler<AcceptanceTestDTO>() {
                @Override
                public void onAdd(StoreAddEvent<AcceptanceTestDTO> event) {

                }
            });

            grid = new Grid<AcceptanceTestDTO>(store, cm);
            grid.getView().setAutoExpandColumn(content);
            grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);

            // EDITING //
            final GridEditing<AcceptanceTestDTO> editing = createGridEditing(grid);
            contentTF = new TextField();
            contentTF.setAllowBlank(false);
            editing.addEditor(content, contentTF);


            customizeGrid(grid);

            panel = new FramedPanel();
            //panel.setHeadingText("Edit acceptance tests");
            panel.setHeaderVisible(false);
            panel.setPixelSize(380, 200);
            //panel.addStyleName("margin-10");

            ToolBar toolBar = new ToolBar();

            TextButton add = new TextButton("Add Acceptance Test");
            add.addSelectHandler(new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    final AcceptanceTestDTO test = new AcceptanceTestDTO();
                    // set values
                    test.setAcceptanceTestId(acceptanceTestCount);
                    // warningzi: this id MUST NOT BE SAVED IN DB
                    test.setContent("Add content");

                    editing.cancelEditing();
                    store.add(0, test);

                    int row = store.indexOf(test);
                    editing.startEditing(new Grid.GridCell(row, 0));

                    firstTimeEdit = true;

                    // TODO: ko se editira ze ustvarjen acceptance test se tud klice ta zadeva, jup you're fucked.
                    // TODO: celica se updata sele potem ko jo spet kliknes za urejanje. ...  -. -.- .- .-
                   /* editing.addCompleteEditHandler(new CompleteEditEvent.CompleteEditHandler<AcceptanceTestDTO>() {
                        @Override
                        public void onCompleteEdit(CompleteEditEvent<AcceptanceTestDTO> event) {
                            System.out.println("Sem v edit ocomeom ojd handlerju!");
                            System.out.println(contentTF.getValue());
                            System.out.println("ACctestCou " + acceptanceTestCount );
                            System.out.println("store at acc test count: " + store.get(acceptanceTestCount));
                            System.out.println("Store at acc test count content before change: " + store.get(acceptanceTestCount).getContent());
                            store.get(acceptanceTestCount).setContent(contentTF.getValue());
                            System.out.println("Store at acc test count content after change: " + store.get(acceptanceTestCount).getContent());
                            grid.getSelectionModel().select(0, true);
                            System.out.println("Grid content before change: " + grid.getSelectionModel().getSelectedItem().getContent());
                            grid.getSelectionModel().getSelectedItem().setContent(store.get(acceptanceTestCount).getContent());
                            System.out.println("Grid content after change: " + grid.getSelectionModel().getSelectedItem().getContent());
                            if (firstTimeEdit) {
                                acceptanceTestCount++;
                                firstTimeEdit = false;
                            }
                        }
                    }); */

                    //System.out.println("STore content: " + store.get(acceptanceTestCount).getContent());
                    //grid.getSelectionModel().select(0, true);
                    //grid.getSelectionModel().getSelectedItem().setContent(store.get(acceptanceTestCount).getContent());
                    //System.out.println("Grid content: " + grid.getSelectionModel().getSelectedItem().getContent());


                }
            });

            toolBar.add(add);



            panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.END);
            final TextButton removeButton = new TextButton("Remove Selected Test");
            removeButton.setEnabled(false);
            SelectEvent.SelectHandler removeButtonHandler = new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    for(AcceptanceTestDTO test : grid.getSelectionModel().getSelectedItems()) {
                        grid.getStore().remove(test);
                    }
                    removeButton.setEnabled(false);
                }
            };
            removeButton.addSelectHandler(removeButtonHandler);

            grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<AcceptanceTestDTO>() {
                @Override
                public void onSelectionChanged(SelectionChangedEvent<AcceptanceTestDTO> event) {
                    removeButton.setEnabled( ! event.getSelection().isEmpty());
                }
            });

            toolBar.add(removeButton);

            numberer.initPlugin(grid);

            VerticalLayoutContainer con = new VerticalLayoutContainer();
            con.setBorders(true);
            con.add(toolBar, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
            con.add(grid, new VerticalLayoutContainer.VerticalLayoutData(1, 1));

            panel.setWidget(con);

            /*panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
            panel.addButton(new TextButton("Reset", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    store.rejectChanges();
                }
            }));
            */
            panel.addButton(new TextButton("Save", new SelectEvent.SelectHandler() {

                @Override
                public void onSelect(SelectEvent event) {
                    store.commitChanges();
                }
            }));
        }

        return panel;
    }

    // return the model key provider for the list store
    private ModelKeyProvider<AcceptanceTestDTO> getModelKeyProvider() {
        ModelKeyProvider<AcceptanceTestDTO> mkp = new ModelKeyProvider<AcceptanceTestDTO>() {
            @Override
            public String getKey(AcceptanceTestDTO item) {
                return item.getAcceptanceTestId().toString();
            }
        };
        return mkp;
    }

    private ValueProvider<AcceptanceTestDTO, String> getContentValue() {
        ValueProvider<AcceptanceTestDTO, String> vpc = new ValueProvider<AcceptanceTestDTO, String>() {
            @Override
            public String getValue(AcceptanceTestDTO object) {
                return object.getContent();
            }
            @Override
            public void setValue(AcceptanceTestDTO object, String value) {

            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    public void setAcceptanceTestList(List<AcceptanceTestDTO> acceptanceTestList) {
        this.acceptanceTestList = acceptanceTestList;
    }

    public List<AcceptanceTestDTO> getAcceptanceTestList() {
        List<AcceptanceTestDTO> acceptanceTestDTOList = new ArrayList<AcceptanceTestDTO>();
        for (AcceptanceTestDTO acceptanceTestDTO : store.getAll()){
            AcceptanceTestDTO temp = new AcceptanceTestDTO();
            temp.setContent(acceptanceTestDTO.getContent());
            acceptanceTestDTOList.add(temp);
        }
        return acceptanceTestDTOList;
    }

    /**
     * Abstract method to allow example subclass to build the specific editing details
     */
    protected abstract GridEditing<AcceptanceTestDTO> createGridEditing(Grid<AcceptanceTestDTO> grid);

    /**
     * Additional modifications can be made to the grid in the subclass with this method
     */
    protected void customizeGrid(Grid<AcceptanceTestDTO> grid) { }

    public AcceptanceTestDTO getTempDTO() {
        return tempDTO;
    }

    public void setTempDTO(AcceptanceTestDTO tempDTO) {
        this.tempDTO = tempDTO;
    }
}
