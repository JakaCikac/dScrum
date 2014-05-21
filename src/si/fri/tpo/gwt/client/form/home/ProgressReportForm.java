package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
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

        private int seqNumber;
        private int hrsRemaining;
        private int hrsSpent;

        public BurndownData(int seqNumber, int hrsRemaining, int hrsSpent) {
            this.seqNumber = seqNumber;
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
        store.addAll(fillBurndownData());

        final Chart<BurndownData> chart = new Chart<BurndownData>();
        chart.setStore(store);
        chart.setShadowChart(false);

        NumericAxis<BurndownData> axis = new NumericAxis<BurndownData>();
        axis.setPosition(Chart.Position.LEFT);
        // TODO: CONTINUE HERE!!!!
    }

    private ModelKeyProvider<BurndownData> getModelKeyProvider() {
        ModelKeyProvider<BurndownData> mkp = new ModelKeyProvider<BurndownData>() {
            @Override
            public String getKey(BurndownData item) {
                return item.seqNumber + "";
            }
        };
        return mkp;
    }

    private List<BurndownData> fillBurndownData() {

        List<BurndownData> burndownDataList = new ArrayList<BurndownData>();
        TreeMap<Date, BurndownData> temp = new TreeMap<Date, BurndownData>();

        /* za najti zacetni datum projekta, se izkazalo kot nepotrebno ker na grafu ni datumov ampak cifro
        Date startDate = null;
        Date endDate = new Date();

        // get/find start date
        for (SprintDTO sprintDTO : SessionInfo.projectDTO.getSprintList()) {
            if (startDate.after(sprintDTO.getStartDate()) || startDate == null) {
                startDate = sprintDTO.getStartDate();
            }
        }
        */

        // fetch/accumulate data
        for (UserStoryDTO userStoryDTO : SessionInfo.projectDTO.getUserStoryList()) {
            for (TaskDTO taskDTO : userStoryDTO.getTaskList()) {
                for (WorkloadDTO workloadDTO : taskDTO.getWorkloadList()) {

                    Date key = workloadDTO.getDay();
                    int timeRemaining = Integer.parseInt(workloadDTO.getTimeRemaining());
                    int timeSpent = Integer.parseInt(workloadDTO.getTimeSpent());
                    BurndownData value = temp.get(key);

                    if (value != null) {
                        value.hrsRemaining += timeRemaining;
                        value.hrsSpent += timeSpent;
                    } else {
                        value = new BurndownData(0, timeRemaining, timeSpent);
                    }

                    temp.put(key, value);
                }
            }
        }

        int i = 1;
        for (BurndownData bd : temp.values()) {
            bd.seqNumber = i;
            burndownDataList.add(bd);
            i++;
        }

        return burndownDataList;
    }
}
