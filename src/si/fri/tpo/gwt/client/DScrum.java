package si.fri.tpo.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;

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

    public Widget asWidget() {
        if (simpleContainer == null) {
            simpleContainer = new SimpleContainer();

            final BorderLayoutContainer con = new BorderLayoutContainer();
            simpleContainer.add(con, new MarginData(10));
            con.setBorders(false);

            ContentPanel center = new ContentPanel();
            ContentPanel east = new ContentPanel();
            ContentPanel south = new ContentPanel();
            ContentPanel north = new ContentPanel();
            ContentPanel west = new ContentPanel();
            west.setCollapsible(false);
            west.setResize(false);

            center.setHeadingText("DScrum application");
            LoginPanel lp = new LoginPanel(this, center, north, south, east, west,  service);
            center.add(lp.asWidget());
            center.setResize(false);

            BorderLayoutData northData = new BorderLayoutData(100);
            northData.setMargins(new Margins(8));
            northData.setCollapsible(false);
            northData.setSplit(false);

            BorderLayoutData westData = new BorderLayoutData(150);
            westData.setCollapsible(false);
            westData.setSplit(false);
            westData.setCollapseMini(false);
            westData.setMargins(new Margins(0, 8, 0, 5));

            MarginData centerData = new MarginData();

            BorderLayoutData eastData = new BorderLayoutData(150);
            eastData.setMargins(new Margins(0, 5, 0, 8));
            eastData.setCollapsible(false);
            eastData.setSplit(false);

            BorderLayoutData southData = new BorderLayoutData(100);
            southData.setMargins(new Margins(8));
            southData.setCollapsible(false);
            southData.setCollapseMini(false);

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

        AsyncCallback<SprintDTO> callback2 = new AsyncCallback<SprintDTO>() {

            @Override
            public void onSuccess(SprintDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        //service.dummySprintTrigger(new SprintDTO(), callback2);

        AsyncCallback<UserDTO> callback3 = new AsyncCallback<UserDTO>() {

            @Override
            public void onSuccess(UserDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyUserTrigger(new UserDTO(), callback3);


        AsyncCallback<TeamDTO> callback5 = new AsyncCallback<TeamDTO>() {

            @Override
            public void onSuccess(TeamDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyTeamTrigger(new TeamDTO(), callback5);

        AsyncCallback<ProjectDTO> callback6 = new AsyncCallback<ProjectDTO>() {

            @Override
            public void onSuccess(ProjectDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        };
        service.dummyProjectTrigger(new ProjectDTO(), callback6);

        AsyncCallback<DiscussionDTO> callback4 = new AsyncCallback<DiscussionDTO>() {

            @Override
            public void onSuccess(DiscussionDTO result) {
                //Window.alert(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());

            }
        };
        //service.dummyDiscussionTrigger(new DiscussionDTO(), callback4);

    }
}
