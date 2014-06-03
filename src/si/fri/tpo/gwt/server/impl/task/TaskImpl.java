package si.fri.tpo.gwt.server.impl.task;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.TaskDTO;
import si.fri.tpo.gwt.client.dto.UserStoryDTO;
import si.fri.tpo.gwt.client.dto.WorkloadDTO;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 03/05/14.
 */
public class TaskImpl {

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

            User u;
            if (taskDTO.getPreassignedUserName() != null) {
                u = ProxyManager.getUserProxy().findUserByUsername(taskDTO.getPreassignedUserName());
                if (u == null) {
                    return Pair.of(false, "Data error (user doesn't exist!");
                }
                t.setPreassignedUserName(taskDTO.getPreassignedUserName());
            } else {
                t.setPreassignedUserName(null);
            }

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
        return Pair.of(true, "Task saved successfully.");
    }

    public static Pair<Boolean, String> updateTask(TaskDTO taskDTO) {
        try {
            TaskPK taskPK = new TaskPK();
            taskPK.setTaskId(taskDTO.getTaskPK().getTaskId());
            taskPK.setUserStoryStoryId(taskDTO.getTaskPK().getUserStoryStoryId());
            Task t = ProxyManager.getTaskProxy().findTask(taskPK);
            t.setDescription(taskDTO.getDescription());
            t.setTimeRemaining(taskDTO.getTimeRemaining());
            t.setEstimatedTime(taskDTO.getEstimatedTime());
            t.setStatus(taskDTO.getStatus());
            t.setAssignedDate(taskDTO.getAssignedDate());
            t.setPreassignedUserName(taskDTO.getPreassignedUserName());

            List<Workload> workloadList = new ArrayList<Workload>();
            for(WorkloadDTO workloadDTO : taskDTO.getWorkloadList()){
                WorkloadPK workloadPK = new WorkloadPK();
                workloadPK.setWorkloadId(workloadDTO.getWorkloadPK().getWorkloadId());
                workloadPK.setTaskTaskId(workloadDTO.getWorkloadPK().getTaskTaskId());
                workloadPK.setTaskUserStoryStoryId(workloadDTO.getWorkloadPK().getTaskUserStoryStoryId());
                workloadPK.setUserUserId(workloadDTO.getWorkloadPK().getUserUserId());
                Workload workload = ProxyManager.getWorkloadProxy().findWorkload(workloadPK);

                workloadList.add(workload);
            }
            t.setWorkloadList(workloadList);

            t.setUserUserId(ProxyManager.getUserProxy().findUserById(taskDTO.getUserUserId().getUserId()));

            User u;
            if (taskDTO.getPreassignedUserName() != null) {
                u = ProxyManager.getUserProxy().findUserByUsername(taskDTO.getPreassignedUserName());
                if (u == null) {
                    return Pair.of(false, "Data error (user doesn't exist!");
                }
                t.setPreassignedUserName(u.getUsername());
            } else {
                t.setPreassignedUserName(null);
            }

            if (taskDTO.getUserUserId() != null) {
                u = ProxyManager.getUserProxy().findUserByUsername(taskDTO.getUserUserId().getUsername());
                if (u == null) {
                    return Pair.of(false, "Data error (user doesn't exist!");
                }
                t.setUserUserId(u);
            } else {
                t.setUserUserId(null);
            }

            try {
                if (t == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getTaskProxy().edit(t);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Task updated successfully.");
    }

    public static Pair<Boolean, String> deleteTask(TaskDTO taskDTO) {
        try {
            TaskPK taskPK = new TaskPK();
            taskPK.setTaskId(taskDTO.getTaskPK().getTaskId());
            taskPK.setUserStoryStoryId(taskDTO.getTaskPK().getUserStoryStoryId());
            Task t = ProxyManager.getTaskProxy().findTask(taskPK);
            try {
                if (t == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getTaskProxy().destroy(t.getTaskPK());

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Task deleted successfully.");
    }
}
