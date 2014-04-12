package si.fri.tpo.gwt.client.form.registration;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by Administrator on 07-Apr-14.
 */
public class SprintRegistrationForm implements IsWidget {

    public SprintRegistrationForm(DScrumServiceAsync service) {
        super(service);
        //getUserSearchButton().addSelectionListener(userSearchListener);
        //getSubmitButton().addSelectionListener(submitListener);
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        initNewRegistrationForm();
        initComponentsDataFill();
    }

    @Override
    public Widget asWidget() {
        return null;
    }
}
