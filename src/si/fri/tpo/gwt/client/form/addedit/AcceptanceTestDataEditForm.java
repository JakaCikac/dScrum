package si.fri.tpo.gwt.client.form.addedit;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridRowEditing;
import si.fri.tpo.gwt.client.dto.AcceptanceTestDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 26/04/14.
 */
public class AcceptanceTestDataEditForm extends AcceptanceTestDataEditAbstractForm {
    public AcceptanceTestDataEditForm(DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east) {
        super(service, center, west, east);
    }

    @Override
    protected GridEditing<AcceptanceTestDTO> createGridEditing(Grid<AcceptanceTestDTO> editableGrid) {
        return new GridRowEditing<AcceptanceTestDTO>(editableGrid);
    }
}
