package si.fri.tpo.gwt.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Created by nanorax on 04/04/14.
 */
public interface MyResources extends ClientBundle {
    public static final MyResources INSTANCE =  GWT.create(MyResources.class);

    //@Source("my.css")
    //public CssResource css();

    //@Source("config.xml")
    //public TextResource initialConfiguration();

    //@ClientBundle.Source("manual.pdf")
    //public DataResource ownersManual();
}
