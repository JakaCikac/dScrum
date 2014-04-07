package si.fri.tpo.gwt.client.form.addedit;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import si.fri.tpo.gwt.client.form.addedit.AbstractAddEditForm;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 07/04/14.
 */
public abstract class AbstractAddEditDialog<X> extends Dialog {
    private FormData formData;
    private DScrumServiceAsync service;
    private boolean addMode;
    private FieldSet fieldSet;
    private long dtoID = -1;
    private AbstractAddEditForm parentForm;

    // if not add mode == EDIT_MODE
    public AbstractAddEditDialog(boolean addMode, DScrumServiceAsync service, AbstractAddEditForm parentForm) {
        this.service = service;
        this.addMode = addMode;
        this.parentForm = parentForm;
        initDialog(addMode);
        show();
    }

    private void initDialog(boolean addMode) {
        setButtons(YESNO);
        setHeading(addMode ? "Add Team Members" : "Edit Team Members");
        getButtonById("yes").setText("Confirm");
        getButtonById("no").setText("Cancel");
        setBodyStyleName("pad-text");
        setScrollMode(Style.Scroll.AUTO);
        setHideOnButtonClick(true);
    }

    @Override
    protected void onButtonPressed(Button button) {
        if (button.getItemId().equals("yes")) {
            fillSaveData();
        }
        super.onButtonPressed(button);
    }

    protected void initComponents(Field... textFields) {
        FormLayout layout = new FormLayout();
        int textAreaLength = 0;
        fieldSet = new FieldSet();
        fieldSet.setBorders(false);
        layout.setLabelWidth(100);
        fieldSet.setLayout(layout);

        for (Field field : textFields) {

            if (field instanceof TextArea) {
                field.setSize("100px", "200px");
                textAreaLength++;
            } else {
                field.setSize("100px", "20px");
            }
            fieldSet.add(field, formData);
        }

        setSize("350px", ((textFields.length - textAreaLength) * 25 + 120) + (textAreaLength * 200) + "px");
        add(fieldSet);
        layout();
    }

    public DScrumServiceAsync getService() {
        return service;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public FieldSet getFieldSet() {
        return fieldSet;
    }

    public long getDtoID() {
        return dtoID;
    }

    public AbstractAddEditForm getParentForm() {
        return parentForm;
    }

    public void setDtoID(long dtoID) {
        this.dtoID = dtoID;
    }

    protected abstract void fillSaveData();
}
