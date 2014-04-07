package si.fri.tpo.gwt.client.form.registration;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.verification.PassHash;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserRegistrationForm extends AbstractRegistrationForm {

    public UserRegistrationForm(DScrumServiceAsync service) {
        super(service);
        //getUserSearchButton().addSelectionListener(userSearchListener);
        getSubmitButton().addSelectionListener(submitListener);
    }

    private UserDTO dto;

   /* private SelectionListener userSearchListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            new UserSearchDialog(getService(), new UserSearchCallback() {
                @Override
                public void userSearchCallback(UserDTO dto) {
                    getSearchedUserTF().setValue(dto.getFirstName() + " " + dto.getLastName());
                    //TODO: get index
                    //fillExistingUserData(dto);
                    setDTO(dto);
                }
            });
        }
    }; */

    private void setDTO(UserDTO dto) {
        this.dto = dto;
    }

   /* private void fillExistingUserData(UserDTO dto) {
        getFirstName().setValue(dto.getFirstName());
        getLastName().setValue(dto.getLastName());
        getEmail().setValue(dto.getEmail());
    } */

    private SelectionListener submitListener = new SelectionListener<ButtonEvent>() {
        @Override
        public void componentSelected(ButtonEvent ce) {
            AsyncCallback<Pair<Boolean, String>> validationCallback = new AsyncCallback<Pair<Boolean, String>>() {
                @Override
                public void onSuccess(Pair<Boolean, String> result) {
                    if (result.getFirst()) {
                        final UserDTO userDTO = new UserDTO();
                        userDTO.setUsername(getUsername().getValue());
                        System.out.println("UserDTO username = " + userDTO.getUsername());
                        userDTO.setEmail(getEmail().getValue());
                        userDTO.setAdmin(getNewAdminRB().getValue());
                        userDTO.setFirstName(getFirstName().getValue());
                        userDTO.setLastName(getLastName().getValue());
                        userDTO.setActive(true);
                        userDTO.setSalt("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                        // create time created = now()
                        java.util.Date utilDate = new java.util.Date();
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                        userDTO.setTimeCreated(sqlDate);

                        // hash retrieved password and set it
                        userDTO.setPassword(PassHash.getMD5Password(getPassword().getValue()));

                        // Save user
                        performSaveUser(userDTO);

                } else
                        MessageBox.alert("Warning", result.getSecond(), null);
                }
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
            };
            //TODO: validate user data
            getService().validateUserData(getEmail().getValue(), validationCallback);
        }
    };


    private void performSaveUser(UserDTO userDTO) {

            AsyncCallback<Pair<Boolean, String>> saveUser = new AsyncCallback<Pair<Boolean, String>>() {
                @Override
                public void onSuccess(Pair<Boolean, String> result) {
                    if (result == null)
                        MessageBox.alert("Error!", "Error while saving!", null);
                    else if (!result.getFirst())
                        MessageBox.alert("Error!", result.getSecond(), null);
                    else
                        MessageBox.info("Message", result.getSecond(), null);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
            };
            getService().saveUser(userDTO, getNewUserRB().getValue(), saveUser);
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        initNewRegistrationForm();
        initComponentsDataFill();
    }
}