package si.fri.tpo.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import si.fri.tpo.jpa.UserStory;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class PriorityDTO extends BaseModelData {

    //private int priorityId;
    //private String name;
    //private List<UserStory> userStories;

    public PriorityDTO() {
    }

    public int getPriorityId() {
        return get("priorityId");
    }

    public void setPriorityId(int priorityId) {
        set("priorityId", priorityId);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public List<UserStoryDTO> getUserStories() {
        return get("userStories");
    }

    public void setUserStories(List<UserStoryDTO> userStories) {
        set("userStories", userStories);
    }
}
