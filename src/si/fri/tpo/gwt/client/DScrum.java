package si.fri.tpo.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import si.fri.tpo.gwt.client.dto.DiscussionDTO;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.client.resources.MyResources;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 2/4/14.
 */
public class DScrum implements IsWidget, EntryPoint {
    private DScrumServiceAsync service = GWT.create(DScrumService.class);
    public static int HEIGHT_NO_SCROLL;
    public static int WIDTH_NO_SCROLL;

    @Override
    public void onModuleLoad() {
        // Call dummy calls to trick the system about serialization
        startupConnectCallback();
        RootPanel.get().add(asWidget());
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
}
