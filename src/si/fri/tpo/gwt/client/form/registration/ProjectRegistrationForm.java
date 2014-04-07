package si.fri.tpo.gwt.client.form.registration;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Label;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.form.search.UserSearchCallback;
import si.fri.tpo.gwt.client.form.search.UserSearchDialog;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;


/**
 * Created by nanorax on 07/04/14.
 */
public class ProjectRegistrationForm extends AbstractProjectRegistrationForm {

    public ProjectRegistrationForm(DScrumServiceAsync service) {
        super(service);
        getSelectProductOwnerB().addSelectionListener(poSelectionListener);
        getSelectScrumMasterB().addSelectionListener(smSelectionListener);
        getSubmitButton().addSelectionListener(submitListener);
        getAddTeamMemberB().addSelectionListener(addTeamListener);
    }

    private UserDTO dto;

    private SelectionListener poSelectionListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    // return selected user and change label to his username
                    getSelectedProductOwnerUserLabel().setText(dto.getUsername());
                    setDTO(dto);
                }
            });
        }
    };

    private SelectionListener smSelectionListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    // return selected user and change label to his username
                    getSelectedScrumMasterUserLabel().setText(dto.getUsername());
                    setDTO(dto);
                }
            });
        }
    };

    private SelectionListener addTeamListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    // return selected user and change label to his username
                    // TODO: I dont know what but incomplete
                    setDTO(dto);
                }
            });
        }
    };

    private void setDTO(UserDTO dto) {
        this.dto = dto;
    }


    private SelectionListener submitListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            performSaveProject(new ProjectDTO());
        }
    };

    private void performSaveProject(ProjectDTO projectDTO) {
            System.out.println(getUsersArrayList().size());
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        initNewRegistrationForm();
        initComponentsDataFill();
    }
}
