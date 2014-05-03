package si.fri.tpo.gwt.server.impl.registration;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

/**
 * Created by nanorax on 03/05/14.
 */
public class TaskRegistrationServiceImpl {

    public static Pair<Boolean, String> saveTask(TaskDTO taskDTO, UserStoryDTO userStoryDTO) {
        try {

            Task t = new Task();
            t.setStatus(taskDTO.getStatus());
            t.setDescription(taskDTO.getDescription());
            t.setTimeRemaining(taskDTO.getTimeRemaining());
            t.setEstimatedTime(taskDTO.getEstimatedTime());

            UserStory us;
            us = ProxyManager.getUserStoryProxy().findUserStory(userStoryDTO.getStoryId());
            if (us == null) {
                return Pair.of(false, "Data error (user story doesn't exist!");
            }
            t.setUserStory(us);

            //User u;
            //u = ProxyManager.getUserProxy().findUserById(taskDTO.getUserUserId().getUserId());
            //if (u == null) {
            //    return Pair.of(false, "Data error (user doesn't exist!");
            //}
            //t.setUserUserId(u);

            TaskPK tpk = new TaskPK();
            tpk.setUserStoryStoryId(us.getStoryId());
            t.setTaskPK(tpk);

            try {
                if (t == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getTaskProxy().create(t);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "");
    }

}
