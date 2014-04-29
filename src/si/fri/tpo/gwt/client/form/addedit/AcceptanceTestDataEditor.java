package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 26/04/14.
 */
public class AcceptanceTestDataEditor implements IsWidget, Editor<UserStoryDTO> {

    protected Grid<AcceptanceTestDTO> grid;

    private List<AcceptanceTestDTO> acceptanceTestList;
    private ContentPanel center, west, east;
    private DScrumServiceAsync service;
    private UserStoryDTO storyDTO;
    static private int acceptanceTestCount;
    private ListStore<AcceptanceTestDTO> store;
    private ListStoreEditor<AcceptanceTestDTO> editStore;
    private AcceptanceTestDTO tempDTO;
    private boolean firstTimeEdit = true;

    public AcceptanceTestDataEditor(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.acceptanceTestCount = 0;
    }

    private FlowPanel container;

    @Override
    public Widget asWidget() {
        if (container == null) {
            container = new FlowPanel();

            // should be layout based
            RowNumberer<AcceptanceTestDTO> numberer = new RowNumberer<AcceptanceTestDTO>();
            //numberer.setWidth(200);
            ColumnConfig<AcceptanceTestDTO, String> content = new ColumnConfig<AcceptanceTestDTO, String>(getContentValue(), 300, "Content");
            List<ColumnConfig<AcceptanceTestDTO, ?>> l = new ArrayList<ColumnConfig<AcceptanceTestDTO, ?>>();
            l.add(numberer);
            l.add(content);

            ColumnModel<AcceptanceTestDTO> cm = new ColumnModel<AcceptanceTestDTO>(l);
            store = new ListStore<AcceptanceTestDTO>(getModelKeyProvider());
            editStore = new ListStoreEditor<AcceptanceTestDTO>(store);

            grid = new Grid<AcceptanceTestDTO>(store, cm);
            grid.getView().setAutoExpandColumn(content);
            grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
            grid.setBorders(true);
            grid.getView().setForceFit(true);

            GridInlineEditing<AcceptanceTestDTO> inlineEditor = new GridInlineEditing<AcceptanceTestDTO>(grid);
            inlineEditor.addEditor(content, new TextField());

            grid.setWidth(382);
            grid.setHeight(200);

            FieldLabel accTestContainer = new FieldLabel();
            accTestContainer.setText("Acceptance tests");
            accTestContainer.setLabelAlign(FormPanel.LabelAlign.TOP);
            accTestContainer.setWidget(grid);
            container.add(accTestContainer);

            TextButton deleteBtn = new TextButton("Delete selected row", new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                        store.remove(grid.getSelectionModel().getSelectedItem());
                }
            });

            TextButton createBtn = new TextButton("Create new test", new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    AcceptanceTestDTO newRow = new AcceptanceTestDTO();
                    newRow.setAcceptanceTestId(acceptanceTestCount);
                    newRow.setContent("Woop woop");
                    store.add(newRow);
                    acceptanceTestCount++;
                }
            });

            ButtonBar buttons = new ButtonBar();
            buttons.add(deleteBtn);
            buttons.add(createBtn);
            container.add(buttons);
            }

        return container;

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

    public void setTempDTO(AcceptanceTestDTO tempDTO) {
        this.tempDTO = tempDTO;
    }
}
