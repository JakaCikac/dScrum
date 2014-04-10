package si.fri.tpo.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.form.TextField;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.resources.MyResources;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 2/4/14.
 */


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

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
            con.setBorders(true);

            //ContentPanel north = new ContentPanel();
            //ContentPanel west = new ContentPanel();
            ContentPanel center = new ContentPanel();
            center.setHeadingText("DScrum application");
            LoginPanel lp = new LoginPanel(this, service);
            center.add(lp.asWidget());
            center.setResize(false);
            //center.add(table);

            ContentPanel east = new ContentPanel();
            ContentPanel south = new ContentPanel();

            BorderLayoutData northData = new BorderLayoutData(100);
            northData.setMargins(new Margins(8));
            northData.setCollapsible(false);
            northData.setSplit(true);

            BorderLayoutData westData = new BorderLayoutData(150);
            westData.setCollapsible(false);
            westData.setSplit(true);
            westData.setCollapseMini(true);
            westData.setMargins(new Margins(0, 8, 0, 5));

            MarginData centerData = new MarginData();

            BorderLayoutData eastData = new BorderLayoutData(150);
            eastData.setMargins(new Margins(0, 5, 0, 8));
            eastData.setCollapsible(true);
            eastData.setSplit(true);

            BorderLayoutData southData = new BorderLayoutData(100);
            southData.setMargins(new Margins(8));
            southData.setCollapsible(false);
            southData.setCollapseMini(true);

            //con.setNorthWidget(north, northData);
            //con.setWestWidget(west, westData);
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



/* public class DScrum implements IsWidget, EntryPoint {
    private DScrumServiceAsync service = GWT.create(DScrumService.class);
    public static int HEIGHT_NO_SCROLL;
    public static int WIDTH_NO_SCROLL;

    @Override
    public void onModuleLoad() {
        // Call dummy calls to trick the system about serialization
        startupConnectCallback();
        RootPanel.get().add(asWidget());
    }

    @Override
    public Widget asWidget() {

        // NAVIGATION CONTAINER
        RootPanel navigationContainer = initNavigationContainer();

        //HEADER CONTAINER
        RootPanel headerContainer = initHeaderContainer();
        headerContainer.getElement().getStyle().setBackgroundColor("#E0E0E0");
        headerContainer.add(new Label("DScrum orodje"));

        // MAIN CONTAINER
        RootPanel mainContainer = initMainContainer(navigationContainer, headerContainer);

        // FOOTER CONTAINER
        RootPanel footerContainer = RootPanel.get("footerContainer");
        footerContainer.getElement().getStyle().setBackgroundColor("#E0E0E0");
        footerContainer.add(new Label("Avtorji: Anže, Denis, Jaka, Matej"));
        footerContainer.add(new Label("Skupina: TPO13, 2014"));

        return mainContainer;
    }

    protected RootPanel initMainContainer(RootPanel navigationContainer, RootPanel headerContainer) {
        RootPanel mainContainer = RootPanel.get("mainContainer");
        mainContainer.setSize("100%", "100%");
        HEIGHT_NO_SCROLL = mainContainer.getOffsetHeight();
        WIDTH_NO_SCROLL = mainContainer.getOffsetWidth();
        mainContainer.getElement().getStyle().setBackgroundColor("#E0E0E0");
        mainContainer.add(new LoginPanel(this, navigationContainer, mainContainer, headerContainer, service));

        return mainContainer;
    }

    protected RootPanel initHeaderContainer() {
        RootPanel headerContainer = RootPanel.get("headerContainer");
        RootPanel.get("headerContainer");
        headerContainer.getElement().getStyle().setBackgroundColor("#E0E0E0");
        return headerContainer;
    }

    protected RootPanel initNavigationContainer() {
        MyResources myResources = GWT.create(MyResources.class);
        RootPanel navigationContainer = RootPanel.get("navigationContainer");
        navigationContainer.getElement().getStyle().setBackgroundColor("#FFF");
        return navigationContainer;
    }


} */
