package si.fri.tpo.gwt.client.form.addedit;

/* import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox; */
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import si.fri.tpo.gwt.client.DScrum;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 07/04/14.
 */
public class AddEditForm { //extends FormPanel {
 /*   private DScrumServiceAsync service;
    private SimpleComboBox<String> entriesCB;

    public AddEditForm(RootPanel mainContainer, DScrumServiceAsync service) {
        this.service = service;
        setHeading("Managing entries");
        setFrame(true);
        mainContainer.setSize(DScrum.WIDTH_NO_SCROLL + "px", DScrum.HEIGHT_NO_SCROLL + "px");
        setWidth("100%");
        setDeferHeight(true);
        initCodeRegisterComboBox();
        initComboBoxListeners();
    }

    private void initCodeRegisterComboBox() {
        entriesCB = new SimpleComboBox<String>();
        entriesCB.add("Users");

        entriesCB.setEmptyText("Choose category...");
        entriesCB.setTypeAhead(true);
        entriesCB.setForceSelection(true);
        entriesCB.setWidth(200);
        entriesCB.setTriggerAction(SimpleComboBox.TriggerAction.ALL);
        entriesCB.setFieldLabel("Categories");

        add(entriesCB);
    }

    private void initComboBoxListeners() {
        entriesCB.addListener(Events.Change, new Listener<FieldEvent>() {
            @Override
            public void handleEvent(FieldEvent be) {
                openCRForm(entriesCB.getSimpleValue());
            }
        });

    }

    private void openCRForm(String value) {
        while (getItemCount() > 1)
            remove(getItem(1));

        if (value.equals("Users")) {
            this.add(new TeamMemberAddEditForm(service));
        } else {
            add(new Label("Display category form.."));
        }
        this.layout();
    } */
}
