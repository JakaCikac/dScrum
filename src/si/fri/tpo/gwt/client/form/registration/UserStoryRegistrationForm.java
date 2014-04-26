package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.TeamDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.addedit.AcceptanceTestDataEditAbstractForm;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchCallback;
import si.fri.tpo.gwt.client.form.search.SingleUserSearchDialog;
import si.fri.tpo.gwt.client.form.select.ProjectSelectForm;
import si.fri.tpo.gwt.client.form.select.TeamSelectForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;

/**
 * Created by nanorax on 26/04/14.
 */
public class UserStoryRegistrationForm implements IsWidget {

    private DScrumServiceAsync service;
    private ContentPanel center, west, east;
    private VerticalPanel vp;
    private HorizontalPanel hp;

    private TextField userStoryName;
    private TextArea content;
    private IntegerField businessValue;

    private Radio couldHave;
    private Radio shouldHave;
    private Radio mustHave;

    private ProjectDTO projectDTO;

    private TextButton submitButton;

    public Widget asWidget() {
        if (vp == null) {
            vp = new VerticalPanel();
            vp.setSpacing(10);
            createUserStoryForm();
        }
        return vp;
    }

    public UserStoryRegistrationForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
    }

    private void createUserStoryForm() {
        FramedPanel panel = new FramedPanel();
        panel.setHeadingText("User Story Creation Form");
        panel.setWidth(450);
        panel.setBodyStyle("background: none; padding: 15px");

        VerticalLayoutContainer p = new VerticalLayoutContainer();
        panel.add(p);

        userStoryName = new TextField();
        userStoryName.setAllowBlank(false);
        userStoryName.setEmptyText("GoodNight story");
        p.add(new FieldLabel(userStoryName, "User Story Name *"), new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        content = new TextArea();
        content.setAllowBlank(false);
        content.addValidator(new MinLengthValidator(10));
        p.add(new FieldLabel(content, "Content *"), new VerticalLayoutContainer.VerticalLayoutData(1, 100));

        businessValue = new IntegerField();
        businessValue.setAllowBlank(false);
        p.add(new FieldLabel(businessValue, "Business Value *"), new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        mustHave = new Radio();
        mustHave.setBoxLabel("Must Have");
        shouldHave = new Radio();
        shouldHave.setBoxLabel("Should Have");
        couldHave = new Radio();
        couldHave.setBoxLabel("Could Have");

        hp = new HorizontalPanel();
        hp.add(mustHave);
        hp.add(shouldHave);
        hp.add(couldHave);
        p.add(new FieldLabel(hp, "Priority *"));

        ToggleGroup toggle = new ToggleGroup();
        toggle.add(mustHave);
        toggle.add(shouldHave);
        toggle.add(couldHave);
        toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio mustHave = (Radio) group.getValue();
            }
        });

        AcceptanceTestDataEditAbstractForm atdeaf = new AcceptanceTestDataEditAbstractForm(service, center, west, east) {
            @Override
            protected GridEditing<AcceptanceTestDTO> createGridEditing(Grid<AcceptanceTestDTO> grid) {
                return new GridRowEditing <AcceptanceTestDTO>(grid);
            }
        };
        p.add(atdeaf.asWidget());

        submitButton = new TextButton("Create");
        submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {

            }
        });
        panel.addButton(submitButton);
        vp.add(panel);

    }

}
