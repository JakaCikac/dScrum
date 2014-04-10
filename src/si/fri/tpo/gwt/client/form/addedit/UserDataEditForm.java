package si.fri.tpo.gwt.client.form.addedit;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 07/04/14.
 */
public class UserDataEditForm implements IsWidget{
//TODO: Something :)
    private DScrumServiceAsync service;

    public UserDataEditForm(DScrumServiceAsync service) {
        this.service = service;
    }

    @Override
    public Widget asWidget() {

        return null;
    }

}
