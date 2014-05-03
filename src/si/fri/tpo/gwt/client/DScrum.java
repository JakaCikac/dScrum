package si.fri.tpo.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 2/4/14.
 */

public class DScrum implements IsWidget, EntryPoint {
    private DScrumServiceAsync service = GWT.create(DScrumService.class);

    public void onModuleLoad() {
        // Call dummy calls to trick the system about serialization
        startupConnectCallback();
        Viewport viewport = new Viewport();
        viewport.add(asWidget());
        RootPanel.get().add(viewport);

    }

    private SimpleContainer simpleContainer;
    private ContentPanel center;
    private ContentPanel east;
    private ContentPanel south;
    private ContentPanel north;
    private ContentPanel west;

    private static final int BORDER_LAYOUT_HEIGHT = 100;
    private static final int BORDER_LAYOUT_WEIGHT = 234;
    private static final int MARGIN_SIZE_OUTSIDE = 10;
    private static final int MARGIN_SIZE_INSIDE = 5;

    private BorderLayoutData northData = new BorderLayoutData(BORDER_LAYOUT_HEIGHT);
    private BorderLayoutData westData = new BorderLayoutData(BORDER_LAYOUT_WEIGHT);
    private BorderLayoutData eastData = new BorderLayoutData(BORDER_LAYOUT_WEIGHT);
    private BorderLayoutData southData = new BorderLayoutData(BORDER_LAYOUT_HEIGHT);

    public int getRootSize = (int) RootPanel.get().getOffsetWidth();
    public int getWestSize = (int) westData.getSize();
    public int getEastSize = (int) eastData.getSize();

    public Widget asWidget() {
        if (simpleContainer == null) {
            simpleContainer = new SimpleContainer();

            final BorderLayoutContainer con = new BorderLayoutContainer();
            simpleContainer.add(con, new MarginData(MARGIN_SIZE_OUTSIDE));
            con.setBorders(false);

            center = new ContentPanel();
            east = new ContentPanel();
            south = new ContentPanel();
            north = new ContentPanel();
            west = new ContentPanel();

            FlowLayoutContainer fl = new FlowLayoutContainer();
            fl.getScrollSupport().setScrollMode(ScrollSupport.ScrollMode.AUTO);
            center.add(fl);
            /* TODO: ok, tole ne bo slo skoz, je treba cez v loginPanel in naprej poslat fl
                ne center, in potem dodajat stvari na fl ... drugace je scroll no go
             */

            west.setCollapsible(false);
            west.setResize(false);

            center.setHeadingText("DScrum application");
            LoginPanel lp = new LoginPanel(this, center, north, south, east, west,  service);
            center.add(lp.asWidget());
            center.setResize(false);
            MarginData centerData = new MarginData();

            northData.setMargins(new Margins(MARGIN_SIZE_OUTSIDE));
            northData.setSplit(false);
            //northData.setCollapsible(false);
            north.setHeaderVisible(false);

            westData.setCollapsible(false);
            westData.setSplit(false);
            //westData.setCollapseMini(false); //omogoča resizanje
            west.setHeaderVisible(false);
            westData.setMargins(new Margins(0, MARGIN_SIZE_INSIDE, 0, MARGIN_SIZE_OUTSIDE));

            eastData.setMargins(new Margins(0, MARGIN_SIZE_OUTSIDE, 0, MARGIN_SIZE_INSIDE));
            eastData.setSplit(false);
            //eastData.setCollapsible(false); //resize ON
            east.setHeaderVisible(false);

            southData.setMargins(new Margins(MARGIN_SIZE_OUTSIDE));
            southData.setCollapsible(false);
            //southData.setCollapseMini(false);
            south.setHeaderVisible(false);

            con.setNorthWidget(north, northData);
            con.setWestWidget(west, westData);
            con.setCenterWidget(center, centerData);
            con.setEastWidget(east, eastData);
            con.setSouthWidget(south, southData);
        }

        return simpleContainer;
    }

    private void startupConnectCallback() {

        AsyncCallback<Character> callback = new AsyncCallback<Character>() {

            @Override
            public void onSuccess(Character result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyCharacterTrigger(new Character('B'), callback);

        AsyncCallback<SprintDTO> sprintCallback = new AsyncCallback<SprintDTO>() {

            @Override
            public void onSuccess(SprintDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummySprintTrigger(new SprintDTO(), sprintCallback);

        AsyncCallback<UserDTO> userCallback = new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyUserTrigger(new UserDTO(), userCallback);


        AsyncCallback<TeamDTO> teamCallback = new AsyncCallback<TeamDTO>() {

            @Override
            public void onSuccess(TeamDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyTeamTrigger(new TeamDTO(), teamCallback);

        AsyncCallback<ProjectDTO> projectCallback = new AsyncCallback<ProjectDTO>() {

            @Override
            public void onSuccess(ProjectDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyProjectTrigger(new ProjectDTO(), projectCallback);

        AsyncCallback<DiscussionDTO> discussionCallback = new AsyncCallback<DiscussionDTO>() {

            @Override
            public void onSuccess(DiscussionDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyDiscussionTrigger(new DiscussionDTO(), discussionCallback);

        AsyncCallback<WorkloadDTO> workloadCallback = new AsyncCallback<WorkloadDTO>() {

            @Override
            public void onSuccess(WorkloadDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyWorkloadTrigger(new WorkloadDTO(), workloadCallback);

        AsyncCallback<WorkloadPKDTO> workloadPKCallback = new AsyncCallback<WorkloadPKDTO>() {

            @Override
            public void onSuccess(WorkloadPKDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyWorkloadPKTrigger(new WorkloadPKDTO(), workloadPKCallback);

        AsyncCallback<DiscussionPKDTO> discussionPKCallback = new AsyncCallback<DiscussionPKDTO>() {

            @Override
            public void onSuccess(DiscussionPKDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyDiscussionPKTrigger(new DiscussionPKDTO(), discussionPKCallback);

        AsyncCallback<AcceptanceTestDTO> acceptanceTestCallback = new AsyncCallback<AcceptanceTestDTO>() {

            @Override
            public void onSuccess(AcceptanceTestDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyAcceptanceTestTrigger(new AcceptanceTestDTO(), acceptanceTestCallback);

        AsyncCallback<CommentDTO> commentCallback = new AsyncCallback<CommentDTO>() {

            @Override
            public void onSuccess(CommentDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyCommentTrigger(new CommentDTO(), commentCallback);

        AsyncCallback<CommentPKDTO> commentPKCallback = new AsyncCallback<CommentPKDTO>() {

            @Override
            public void onSuccess(CommentPKDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyCommentPKTrigger(new CommentPKDTO(), commentPKCallback);

        AsyncCallback<DailyScrumEntryDTO> dailyScrumEntryCallback = new AsyncCallback<DailyScrumEntryDTO>() {

            @Override
            public void onSuccess(DailyScrumEntryDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyDailyScrumEntryTrigger(new DailyScrumEntryDTO(), dailyScrumEntryCallback);

        AsyncCallback<DailyScrumEntryPKDTO> dailyScrumEntryPKCallback = new AsyncCallback<DailyScrumEntryPKDTO>() {

            @Override
            public void onSuccess(DailyScrumEntryPKDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyDailyScrumEntryPKTrigger(new DailyScrumEntryPKDTO(), dailyScrumEntryPKCallback);

        AsyncCallback<PriorityDTO> priorityCallback = new AsyncCallback<PriorityDTO>() {

            @Override
            public void onSuccess(PriorityDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyPriorityTrigger(new PriorityDTO(), priorityCallback);

        AsyncCallback<WorkblockDTO> workblockCallback = new AsyncCallback<WorkblockDTO>() {

            @Override
            public void onSuccess(WorkblockDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyWorkblockTrigger(new WorkblockDTO(), workblockCallback);

        AsyncCallback<WorkblockPKDTO> workblockPKCallback = new AsyncCallback<WorkblockPKDTO>() {

            @Override
            public void onSuccess(WorkblockPKDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyWorkblockPKTrigger(new WorkblockPKDTO(), workblockPKCallback);

        AsyncCallback<SprintPKDTO> sprintPKCallback = new AsyncCallback<SprintPKDTO>() {

            @Override
            public void onSuccess(SprintPKDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummySprintPKTrigger(new SprintPKDTO(), sprintPKCallback);

        AsyncCallback<TaskDTO> taskCallback = new AsyncCallback<TaskDTO>() {

            @Override
            public void onSuccess(TaskDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyTaskTrigger(new TaskDTO(), taskCallback);

        AsyncCallback<TaskPKDTO> taskPKCallback = new AsyncCallback<TaskPKDTO>() {

            @Override
            public void onSuccess(TaskPKDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyTaskPKTrigger(new TaskPKDTO(), taskPKCallback);

        AsyncCallback<UserStoryDTO> userStoryCallback = new AsyncCallback<UserStoryDTO>() {

            @Override
            public void onSuccess(UserStoryDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyUserStoryTrigger(new UserStoryDTO(), userStoryCallback);
    }

    public ContentPanel getCenter() {
        return this.center;
    }

    public ContentPanel getSouth() {
        return this.south;
    }

    public ContentPanel getWest() {
        return this.west;
    }

    public ContentPanel getEast() {
        return this.east;
    }

    public ContentPanel getNorth() {
        return this.north;
    }

    public int getRootSize (){ return this.getRootSize; }

    public int getWestSize (){ return this.getWestSize; }

    public int getEastSize (){ return this.getEastSize; }
}
