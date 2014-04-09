package si.fri.tpo.gwt.client.dto;

import com.sencha.gxt.legacy.client.data.BaseModelData;

import java.util.List;

/**
 * Created by t13db on 4.4.2014.
 */
public class PriorityDTO extends BaseModelData {

    //private Integer priorityId;
    //private String name;
    //private List<UserStory> userStoryList;

    public PriorityDTO() {
    }

    public Integer getPriorityId() {
        return get("priorityId");
    }

    public void setPriorityId(Integer priorityId) {
        set("priorityId", priorityId);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public List<UserStoryDTO> getUserStoryList() {
        return get("userStoryList");
    }

    public void setUserStoryList(List<UserStoryDTO> userStoryList) {
        set("userStoryList", userStoryList);
    }
}
