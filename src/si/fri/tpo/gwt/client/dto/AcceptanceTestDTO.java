package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.UserStory;

/**
 * Created by t13db on 4.4.2014.
 */
public class AcceptanceTestDTO extends BaseModelData {

    //private int acceptanceTestId;
    //private String content;
    //private UserStory userStory;

    public AcceptanceTestDTO() {
    }

    public int getAcceptanceTestId() {
        return get("acceptanceTestId");
    }

    public void setAcceptanceTestId(int acceptanceTestId) {
        set("acceptanceTestId", acceptanceTestId);
    }

    public String getContent() {
        return get("content");
    }

    public void setContent(String content) {
        set("content", content);
    }

    public UserStory getUserStory() {
        return get("userStory");
    }

    public void setUserStory(UserStory userStory) {
        set("userStory", userStory);
    }
}
