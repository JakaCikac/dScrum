package si.fri.tpo.gwt.client.form.addedit;

import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.TextArea;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 07/04/14.
 */
public abstract class AbstractAddEditDialog<X> extends Dialog {
   /* private DScrumServiceAsync service;
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
    protected void onButtonPressed(TextButton button) {
        if (button.getItemId().equals("yes")) {
            fillSaveData();
        }
        super.onButtonPressed(button);
    }

    protected void initComponents(Field... textFields) {
        int textAreaLength = 0;
        fieldSet = new FieldSet();
        fieldSet.setBorders(false);

        for (Field field : textFields) {

            if (field instanceof TextArea) {
                field.setSize("100px", "200px");
                textAreaLength++;
            } else {
                field.setSize("100px", "20px");
            }
            fieldSet.add(field);
        }

        setSize("350px", ((textFields.length - textAreaLength) * 25 + 120) + (textAreaLength * 200) + "px");
        add(fieldSet);
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

    protected abstract void fillSaveData();*/
}
