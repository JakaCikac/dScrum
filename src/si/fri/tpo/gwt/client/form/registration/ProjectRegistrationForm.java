package si.fri.tpo.gwt.client.form.registration;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.google.gwt.user.client.Element;
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
        getUserSearchButton().addSelectionListener(userSearchListener);
        getSubmitButton().addSelectionListener(submitListener);
    }

    private UserDTO dto;

    private SelectionListener userSearchListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    getSearchedUserTF().setValue(dto.getFirstName() + " " + dto.getLastName());
                    //TODO: get index

                    setDTO(dto);
                }
            });
        }
    };

    private SelectionListener poSelectionListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    getSearchedUserTF().setValue(dto.getFirstName() + " " + dto.getLastName());
                    //TODO: get index

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
                    getSearchedUserTF().setValue(dto.getFirstName() + " " + dto.getLastName());
                    //TODO: get index

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

        }
    };

    private void performSaveProject(ProjectDTO projectDTO) {

    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        initNewRegistrationForm();
        initComponentsDataFill();
    }
}
