package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.UserStory;

/**
 * Created by t13db on 4.4.2014.
 */
public class AcceptanceTestDTO extends BaseModelData {

    //private Integer acceptanceTestId;
    //private String content;
    //private UserStory userStoryStoryId;

    public AcceptanceTestDTO() {
    }

    public Integer getAcceptanceTestId() {
        return get("acceptanceTestId");
    }

    public void setAcceptanceTestId(Integer acceptanceTestId) {
        set("acceptanceTestId", acceptanceTestId);
    }

    public String getContent() {
        return get("content");
    }

    public void setContent(String content) {
        set("content", content);
    }

    public UserStory getUserStoryStoryId() {
        return get("userStoryStoryId");
    }

    public void setUserStoryStoryId(UserStory userStoryStoryId) {
        set("userStoryStoryId", userStoryStoryId);
    }
}
