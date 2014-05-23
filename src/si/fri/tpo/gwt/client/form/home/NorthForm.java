package si.fri.tpo.gwt.client.form.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import si.fri.tpo.gwt.client.dto.SprintDTO;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;
import si.fri.tpo.gwt.client.session.SessionInfo;

/**
 * Created by anze on 23. 05. 14.
 */
public class NorthForm implements IsWidget{

    private ContentPanel panel;
    private ContentPanel center, west, east, north, south;
    private DScrumServiceAsync service;
    private SimpleContainer con;

    public NorthForm(DScrumServiceAsync service, ContentPanel center, ContentPanel north, ContentPanel south, ContentPanel east, ContentPanel west) {
        this.service = service;
        this.center = center;
        this.west = west;
        this.east = east;
        this.north = north;
        this.south = south;
    }

    @Override
    public Widget asWidget() {
        panel = new ContentPanel();
        panel.setHeaderVisible(false);
        panel.setPixelSize(north.getOffsetWidth(), north.getOffsetHeight());
        panel.setBorders(false);
        panel.setBodyBorder(false);

        HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
        panel.setWidget(hlc);

        String right = new String();
        if(SessionInfo.userDTO != null){
            right += "Logged in as: " + SessionInfo.userDTO.getUsername() + "<br/>";
        }

        String left = new String();
        if(SessionInfo.projectDTO != null){
            left += "Current Project:<br/>" + SessionInfo.projectDTO.getName() + "<br/>";

            right += "as Team member";

            if(SessionInfo.userDTO.isAdmin()){
                right += ", Admin";
            }
            if(SessionInfo.projectDTO.getTeamTeamId().getProductOwnerId() == SessionInfo.userDTO.getUserId()){
                right += ", Product Owner";
            }
            if(SessionInfo.projectDTO.getTeamTeamId().getScrumMasterId() == SessionInfo.userDTO.getUserId()){
                right += ", Scrum Master";
            }

            for(SprintDTO sprintDTO : SessionInfo.projectDTO.getSprintList()){
                if(sprintDTO.getStatus().equals("In progress")){
                    left += "<br/>Project status:<br/>Sprint" + sprintDTO.getSeqNumber() + " in progress, ";
                    left += "ends on " + DateTimeFormat.getShortDateFormat().format(sprintDTO.getEndDate());
                }
            }

        }

        HTML label2 = new HTML("<h1 style='margin-left:auto; margin-right:auto; font-size:70px;'>dScrum</h1>");

        hlc.add(new HTML(left), new HorizontalLayoutContainer.HorizontalLayoutData(0.15, 1, new Margins(4)));
        hlc.add(label2, new HorizontalLayoutContainer.HorizontalLayoutData(0.7, 1, new Margins(4, 0, 4, 0)));
        hlc.add(new HTML(right), new HorizontalLayoutContainer.HorizontalLayoutData(0.15, 1, new Margins(4)));
        return panel;

    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        return label;
    }
}
