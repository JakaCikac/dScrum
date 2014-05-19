package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.dto.WorkblockDTO;
import si.fri.tpo.gwt.client.dto.WorkloadDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.Date;

/**
 * Created by t13db on 19.5.2014.
 */
public class ProgressReportForm implements IsWidget {

    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private VerticalPanel verticalPanel;

    public ProgressReportForm (DScrumServiceAsync service, ContentPanel center, ContentPanel west, ContentPanel east, ContentPanel north, ContentPanel south) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    @Override
    public Widget asWidget() {
        if (verticalPanel == null) {
            verticalPanel = new VerticalPanel();
            verticalPanel.setSpacing(10);
            createBurnDownForm();
        }
        return verticalPanel;
    }

    private void createBurnDownForm() {

        for (UserStoryDTO userStoryDTO : SessionInfo.projectDTO.getUserStoryList()) {
            for (TaskDTO taskDTO : userStoryDTO.getTaskList()) {
                for (WorkloadDTO workloadDTO : taskDTO.getWorkloadList()) {
                    if (workloadDTO.getUser() == SessionInfo.userDTO) {
                        for (WorkblockDTO workblockDTO : workloadDTO.getWorkblockList()) {
                            Date startDateTime = workblockDTO.getTimeStart();
                            Date endDateTime = workblockDTO.getTimeStop();
                        }
                    }
                }
            }
        }

        final ListStore<Integer> store = new ListStore<Integer>(getModelKeyProvider());
        store.addAll(TestData.getData(8, 20, 100));
    }

    private ModelKeyProvider<Integer> getModelKeyProvider() {
        ModelKeyProvider<Integer> mkp = new ModelKeyProvider<Integer>() {
            @Override
            public String getKey(WorkblockDTO item) {
                return item.getWorkblockPK().getWorkblockId() + "";
            }
        };
        return mkp;
    }
}
