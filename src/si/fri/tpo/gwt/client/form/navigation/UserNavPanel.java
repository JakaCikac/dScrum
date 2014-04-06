package si.fri.tpo.gwt.client.form.navigation;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import si.fri.tpo.gwt.client.service.DScrumServiceAsync;

/**
 * Created by nanorax on 06/04/14.
 */
public class UserNavPanel extends LayoutContainer {
    private RootPanel mainContainer;
    private DScrumServiceAsync service;

    public UserNavPanel(RootPanel mainContainer, DScrumServiceAsync service) {
        this.mainContainer = mainContainer;
        this.service = service;
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setLayout(new FlowLayout(10));

        final ContentPanel panel = new ContentPanel();
        panel.setHeading("User menu");
        panel.setBodyBorder(false);
        panel.setLayout(new AccordionLayout());

        ContentPanel cp = new ContentPanel();
        cp.setHeaderVisible(false);
        cp.setAnimCollapse(false);
        cp.setExpanded(true);
        cp.setBodyStyleName("pad-text");

        /*Button urejanjeSifrantov = new Button("Urejanje šifrantov", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new MainEditCodeRegisterForm(mainContainer, service));
            }
        });
        urejanjeSifrantov.setWidth("100%");
        cp.add(urejanjeSifrantov);

        Button iskalnikStudentov = new Button("Iskalnik študentov", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new StudentSearchEngineForm(service));
            }
        });
        iskalnikStudentov.setWidth("100%");
        cp.add(iskalnikStudentov);

        Button vpisniList = new Button("Vpisni list", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new StudentEnrollmentForm(service));

            }
        });
        vpisniList.setWidth("100%");
        cp.add(vpisniList);

        Button osebniPodatkiStudenta = new Button("Osebni podatki študenta", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new StudentPersonalDataForm(service));
            }
        });
        osebniPodatkiStudenta.setWidth("100%");
        cp.add(osebniPodatkiStudenta);

      //copy this link to student meni
        Long studentId = SessionInfo.personDTO.getPersonId();
        String url = GWT.getModuleBaseURL();
        cp.add(new Html("<a href=\""+url+"fileupload?sifrant=pregledRazpisanihRokov&year=2011&dtoId="+studentId+"\" target=\"_blank\">Pregled razpisanih rokov 2011</a>"));
        System.out.println("PersonId: "+studentId);

        cp.add(new Html("<a href=\""+url+"fileupload?sifrant=vpisniList&dtoId=1\" target=\"_blank\">PDF vpisni list 20012</a>"));

        panel.setSize(270, 500);*/

        /* Button pregledRazpisanihRokov = new Button("Pregled razpisanih rokov", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new StudentExamsEnrollingForm(mainContainer, service));

            }
        });
        pregledRazpisanihRokov.setWidth("100%");
        cp.add(pregledRazpisanihRokov);

        Button prijavaOdjava = new Button("Prijava na izpit", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new ExamSignInForm(service));
            }
        });
        prijavaOdjava.setWidth("100%");
        cp.add(prijavaOdjava);

        Button izpitOdjava = new Button("Odjava od izpita", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new ExamSignoffForm(service));
            }
        });
        izpitOdjava.setWidth("100%");
        cp.add(izpitOdjava);

        Button kartotecniList = new Button("Kartotečni list", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                mainContainer.clear();
                mainContainer.add(new StudentGradeRecordForm(service));
            }
        });
        kartotecniList.setWidth("100%");
        cp.add(kartotecniList); */

        cp.setAutoHeight(true);
        cp.setAutoWidth(true);
        panel.add(cp);

        panel.setWidth(270);
        panel.setAutoHeight(true);
        this.add(panel);
    }
}
