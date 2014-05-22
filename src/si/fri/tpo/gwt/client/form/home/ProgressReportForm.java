package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
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
            verticalPanel.add(createBurnDownForm());
        }
        return verticalPanel;
    }

    private Chart createBurnDownForm() {

        final ListStore<BurndownData> store = new ListStore<BurndownData>(getModelKeyProvider());
        List<BurndownData> data = fillBurndownData();
        store.addAll(data);

        final Chart<BurndownData> chart = new Chart<BurndownData>();
        chart.setStore(store);
        chart.setShadowChart(false);

        NumericAxis<BurndownData> axis = new NumericAxis<BurndownData>();
        axis.setPosition(Chart.Position.LEFT);
        axis.addField(getHrsRemainingValue());
        axis.addField(getHrsSpentValue());
        TextSprite title = new TextSprite("Hours of work");
        title.setFontSize(18);
        axis.setTitleConfig(title);
        axis.setMinorTickSteps(1);
        axis.setDisplayGrid(true);
        PathSprite odd = new PathSprite();
        odd.setOpacity(1);
        odd.setFill(new Color("#ddd"));
        odd.setStroke(new Color("#bbb"));
        odd.setStrokeWidth(0.5);
        axis.setGridOddConfig(odd);
        axis.setMinimum(0);
        axis.setMaximum(100);
        chart.addAxis(axis);

        CategoryAxis<BurndownData, Integer> catAxis = new CategoryAxis<BurndownData, Integer>();
        catAxis.setPosition(Chart.Position.BOTTOM);
        catAxis.setField(getSeqNumberValue());
        title = new TextSprite("Days since project start");
        title.setFontSize(18);
        catAxis.setTitleConfig(title);
        /*catAxis.setLabelProvider(new LabelProvider<BurndownData>() {
            @Override
            public Integer getLabel(BurndownData item) {
                return item.seqNumber;
            }
        });*/
        chart.addAxis(catAxis);

        // PREOSTALI CAS
        final LineSeries<BurndownData> series = new LineSeries<BurndownData>();
        series.setYAxisPosition(Chart.Position.LEFT);
        series.setYField(getHrsRemainingValue());
        series.setStroke(new RGB(194, 0, 36));
        series.setShowMarkers(true);
        Sprite marker = Primitives.square(0, 0, 6);
        marker.setFill(new RGB(194, 0, 36));
        series.setMarkerConfig(marker);
        series.setHighlighting(true);
        chart.addSeries(series);

        // PORABLJEN CAS
        final LineSeries<BurndownData> series2 = new LineSeries<BurndownData>();
        series2.setYAxisPosition(Chart.Position.LEFT);
        series2.setYField(getHrsSpentValue());
        series2.setStroke(new RGB(240, 165, 10));
        series2.setShowMarkers(true);
        series2.setSmooth(true);
        marker = Primitives.circle(0, 0, 6);
        marker.setFill(new RGB(240, 165, 10));
        series2.setMarkerConfig(marker);
        series2.setHighlighting(true);
        chart.addSeries(series2);

        final Legend<BurndownData> legend = new Legend<BurndownData>();
        legend.setItemHighlighting(true);
        legend.setItemHiding(true);
        legend.getBorderConfig().setStrokeWidth(0);
        chart.setLegend(legend);

        chart.setLayoutData(new VerticalLayoutContainer.VerticalLayoutData(1, 1));

        return chart;
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

    private ValueProvider<BurndownData, Integer> getSeqNumberValue() {
        ValueProvider<BurndownData, Integer> vpc = new ValueProvider<BurndownData, Integer>() {
            @Override
            public Integer getValue(BurndownData object) {
                return object.seqNumber;
            }
            @Override
            public void setValue(BurndownData object, Integer value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<BurndownData, Integer> getHrsRemainingValue() {
        ValueProvider<BurndownData, Integer> vpc = new ValueProvider<BurndownData, Integer>() {
            @Override
            public Integer getValue(BurndownData object) {
                return object.hrsRemaining;
            }
            @Override
            public void setValue(BurndownData object, Integer value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }

    private ValueProvider<BurndownData, Integer> getHrsSpentValue() {
        ValueProvider<BurndownData, Integer> vpc = new ValueProvider<BurndownData, Integer>() {
            @Override
            public Integer getValue(BurndownData object) {
                return object.hrsSpent;
            }
            @Override
            public void setValue(BurndownData object, Integer value) {
            }
            @Override
            public String getPath() {
                return null;
            }
        };
        return vpc;
    }
}