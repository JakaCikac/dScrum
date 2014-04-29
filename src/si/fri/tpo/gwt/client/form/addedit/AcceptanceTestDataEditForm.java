package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 26/04/14.
 */
public class AcceptanceTestDataEditForm implements IsWidget {



    interface Driver extends SimpleBeanEditorDriver<UserStoryDTO, AcceptanceTestDataEditor> {}

    private Driver driver = GWT.create(Driver.class);
    private FramedPanel panel;
    private ContentPanel center, west, east;
    private DScrumServiceAsync service;
    private List<AcceptanceTestDTO> acceptanceTests;


    public AcceptanceTestDataEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
    }

    @Override
    public Widget asWidget() {
        if (panel == null) {
            panel = new FramedPanel();
            panel.setHeaderVisible(false);
            panel.setBodyBorder(false);
            panel.setWidth(400);
            panel.addStyleName("margin-10");

            AcceptanceTestDataEditor accTestEditor = new AcceptanceTestDataEditor(service, center, west, east);
            driver.initialize(accTestEditor);

            panel.setWidget(accTestEditor);
            panel.addButton(new TextButton("Save", new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    UserStoryDTO us = driver.flush();
                    if (!driver.hasErrors()) {
                        new MessageBox("Driver failed completely with numerous errors.").show();
                    }
                }
            }));

            // TODO: naredi user story
            UserStoryDTO userStoryDTO = new UserStoryDTO();
            // TODO: set user story data

            // = new ArrayList<AcceptanceTestDTO>();
            //accTest.setKids(acceptanceTests);

            driver.edit(userStoryDTO);
        }
        return panel;
    }

    public List<AcceptanceTestDTO> getAcceptanceTestList() {
        return acceptanceTests;
    }

}
