package si.fri.tpo.gwt.client.form.registration;


import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

import java.util.Date;


/**
 * Created by t13as on 07-Apr-14.
 */
public abstract class AbstractSprintRegistrationForm {//extends LayoutContainer{

    /*private DScrumServiceAsync service;
    private VerticalPanel vp;
    private FormData formData;
    private RadioGroup typeSprintRG = new RadioGroup();
    private FormPanel simple = new FormPanel();
    private Radio newSprintRB = new Radio();

    // Basic info components
    private DateBox startDatePicker = new DateBox();
    private DateBox endDatePicker = new DateBox();

    // textfeilds
    private NumberField velocity = new NumberField();

    // fieldsets
    private FieldSet setBasicData = new FieldSet();

    // labels
    private Label startDate = new Label();
    private Label endDate = new Label();

    // buttons
    private Button submitButton = new Button("Create sprint");

    public AbstractSprintRegistrationForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        formData = new FormData("-20");
        vp = new VerticalPanel();
        vp.setSpacing(10);
        initMainRegistrationForm();
        add(vp);
    }

    private void initMainRegistrationForm() {
        newSprintRB.setName("new_sprint");
        newSprintRB.setValue(true);
        newSprintRB.setBoxLabel("New sprint");

        typeSprintRG.setFieldLabel("Sprint type");
        typeSprintRG.setAutoWidth(true);
        typeSprintRG.add(newSprintRB);
        typeSprintRG.addListener(Events.Change, new Listener<FieldEvent>() {
            @Override
            public void handleEvent(FieldEvent fe) {
                if (newSprintRB.getValue()) {
                    clearAllData();
                }
            }
        });
        simple.add(typeSprintRG);

    }

    protected void clearAllData() {
        for (Component component : setBasicData.getItems()) {
            disableSingleComponent(component);
        }
    }

    private void disableSingleComponent(Component component) {
        if (component instanceof TextField)
            ((TextField) component).setValue(null);
        else if (component instanceof ComboBox)
            ((ComboBox) component).setValue(null);
    }

    protected void initNewRegistrationForm() {
        getSimple().setHeading("Sprint Creation form");
        getSimple().setFrame(true);

        FormLayout layout = new FormLayout();

        getSimple().setButtonAlign(Style.HorizontalAlignment.CENTER);

        setBasicData.setHeading("Sprint information");
        layout.setLabelWidth(100);
        setBasicData.setLayout(layout);

        startDate.setText("Start date: ");
        setBasicData.add(startDate, getFormData());

        DateTimeFormat dateFormat = DateTimeFormat.getLongDateFormat();
        startDatePicker.setValue(new Date());
        startDatePicker.setFormat(new DateBox.DefaultFormat(dateFormat));
        setBasicData.add(startDatePicker, getFormData());

        endDate.setText("<br/>"+"Finish date: ");
        setBasicData.add(endDate, getFormData());

        endDatePicker.setValue(new Date());
        endDatePicker.setFormat(new DateBox.DefaultFormat(dateFormat));
        setBasicData.add(endDatePicker, getFormData());

        velocity.setFieldLabel("Sprint velocity");
        velocity.setAllowBlank(false);
        setBasicData.add(velocity, getFormData());

        getSimple().add(setBasicData);

        submitButton.setEnabled(true);
        getSimple().addButton(submitButton);

        FormButtonBinding binding = new FormButtonBinding(getSimple());
        binding.addButton(submitButton);

        getVp().add(getSimple());
    }

    protected void initComponentsDataFill() {

    }

    public VerticalPanel getVp() {
        return vp;
    }

    public FormData getFormData() {
        return formData;
    }

    public DScrumServiceAsync getService() {
        return service;
    }

    public FormPanel getSimple() {
        return simple;
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public DateBox getStartDatePicker() {
        return startDatePicker;
    }

    public DateBox getEndDatePicker() {
        return endDatePicker;
    }

    public NumberField getVelocity() {
        return velocity;
    }

    public FieldSet getSetBasicData() {
        return setBasicData;
    }

    public Radio getNewSprintRB() {
        return newSprintRB;
    }*/
}
