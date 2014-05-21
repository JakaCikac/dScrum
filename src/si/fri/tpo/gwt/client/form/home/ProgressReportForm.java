package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.dto.WorkloadDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by t13db on 19.5.2014.
 */
public class ProgressReportForm implements IsWidget {

    class BurndownData {

        private Date date;
        private int hrsRemaining;
        private int hrsSpent;

        public BurndownData(Date date, int hrsRemaining, int hrsSpent) {
            this.date = date;
            this.hrsRemaining = hrsRemaining;
            this.hrsSpent = hrsSpent;
        }
    }

    class BurndownData2 {

        private int hrsRemaining;
        private int hrsSpent;

        public BurndownData2(int hrsRemaining, int hrsSpent) {
            this.hrsRemaining = hrsRemaining;
            this.hrsSpent = hrsSpent;
        }
    }

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



        final ListStore<BurndownData> store = new ListStore<BurndownData>(getModelKeyProvider());
        //store.addAll();
    }

    private ModelKeyProvider<BurndownData> getModelKeyProvider() {
        ModelKeyProvider<BurndownData> mkp = new ModelKeyProvider<BurndownData>() {
            @Override
            public String getKey(BurndownData item) {
                return item.date.toString();
            }
        };
        return mkp;
    }

    private List<BurndownData> fillBurndownData() {

        List<BurndownData> burndownDataList = new ArrayList<BurndownData>();
        Date startDate = null;
        Date endDate = new Date();

        TreeMap<Date, BurndownData2> temp = new TreeMap<Date, BurndownData2>();

        // get/find start date
        for (SprintDTO sprintDTO : SessionInfo.projectDTO.getSprintList()) {
            if (startDate.after(sprintDTO.getStartDate()) || startDate == null) {
                startDate = sprintDTO.getStartDate();
            }
        }

        // fetch/accumulate data
        for (UserStoryDTO userStoryDTO : SessionInfo.projectDTO.getUserStoryList()) {
            for (TaskDTO taskDTO : userStoryDTO.getTaskList()) {
                for (WorkloadDTO workloadDTO : taskDTO.getWorkloadList()) {
                    int timeRemaining = Integer.parseInt(workloadDTO.getTimeRemaining());
                    int timeSpent = Integer.parseInt(workloadDTO.getTimeSpent());
                    Date date = new Date();// = workloadDTO.getDate();
                    BurndownData2 timeParams = new BurndownData2(timeRemaining, timeSpent);
                    temp.put(date, timeParams);
                }
            }
        }

        return burndownDataList;
    }
}
