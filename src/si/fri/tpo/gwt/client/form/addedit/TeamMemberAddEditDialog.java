package si.fri.tpo.gwt.client.form.addedit;

//import com.extjs.gxt.ui.client.widget.form.TextField;

/**
 * Created by nanorax on 07/04/14.
 */

public class TeamMemberAddEditDialog extends com.sencha.gxt.widget.core.client.Dialog {
   /* public TeamMemberAddEditDialog(boolean addMode, AbstractAddEditForm parentForm, DScrumServiceAsync service, UserDTO dto) {
        super(addMode, service, parentForm);

        TextField<String> codeTF = new TextField<String>();
        codeTF.setFieldLabel("TEAAM");
        codeTF.setAllowBlank(false);


        initComponents(codeTF);
    }

    @Override
    protected void fillSaveData() {

        AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

            @Override
            public void onSuccess(Boolean success) {
                System.out.println("Team member was added: " + success);
                getParentForm().reconfigureData();
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };

       // getService().addOrEditTeamMembersDto(userDTO, isAddMode(), callback);

    }*/
}
