package si.fri.tpo.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import si.fri.tpo.gwt.client.resources.MyResources;
import si.fri.tpo.gwt.client.service.DScrumService;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 2/4/14.
 */
public class DScrum implements EntryPoint {
    private DScrumServiceAsync service = GWT.create(DScrumService.class);
    public static int HEIGHT_NO_SCROLL;
    public static int WIDTH_NO_SCROLL;

    public void onModuleLoad() {

        startupConnectCallback();

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
        //RootPanel navigationContainer = RootPanel.get("navigationContainer");
        //navigationContainer.getElement().getStyle().setBackgroundColor("#FFF");
        //Image myImage = new Image(myResources.logoFri());
        //navigationContainer.add(myImage);
        //return navigationContainer;
        return null;
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
    }
}
