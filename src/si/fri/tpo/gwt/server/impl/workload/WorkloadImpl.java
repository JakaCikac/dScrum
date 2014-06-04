package si.fri.tpo.gwt.server.impl.workload;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 22/05/14.
 */
public class WorkloadImpl {

    public static Pair<Boolean,String> updateWorkload(WorkloadDTO workloadDTO) {

        try {
            WorkloadPK workloadPK = new WorkloadPK();
            workloadPK.setWorkloadId(workloadDTO.getWorkloadPK().getWorkloadId());
            workloadPK.setTaskTaskId(workloadDTO.getWorkloadPK().getTaskTaskId());
            workloadPK.setTaskUserStoryStoryId(workloadDTO.getWorkloadPK().getTaskUserStoryStoryId());
            workloadPK.setUserUserId(workloadDTO.getWorkloadPK().getUserUserId());
            Workload workload = ProxyManager.getWorkloadProxy().findWorkload(workloadPK);

            workload.setTimeRemaining(workloadDTO.getTimeRemaining());
            workload.setTimeSpent(workloadDTO.getTimeSpent());

            workload.setStarted(workloadDTO.getStarted());
            workload.setStartTime(workloadDTO.getStartTime());
            workload.setStopTime(workloadDTO.getStopTime());

            try {
                if (workload == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getWorkloadProxy().edit(workload);

            } catch (Exception e) {
                System.err.println("Error while editing workload with message: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Workload updated successfully.");

    }

    public static Pair<Boolean,List<Integer>> saveWorkload(List<WorkloadDTO> workloadDTOList) {
        List<Integer> insertedWorkloadID = new ArrayList<Integer>();
        for(WorkloadDTO workloadDTO : workloadDTOList) {
            try {
                WorkloadPK workloadPK = new WorkloadPK();
                workloadPK.setTaskTaskId(workloadDTO.getWorkloadPK().getTaskTaskId());
                workloadPK.setTaskUserStoryStoryId(workloadDTO.getWorkloadPK().getTaskUserStoryStoryId());
                workloadPK.setUserUserId(workloadDTO.getWorkloadPK().getUserUserId());
                Workload workload = new Workload();
                workload.setWorkloadPK(workloadPK);
                workload.setTimeSpent("0");
                workload.setTimeRemaining(workloadDTO.getTimeRemaining());
                workload.setDay(workloadDTO.getDay());

                TaskPK taskPK = new TaskPK();
                taskPK.setTaskId(workloadDTO.getTask().getTaskPK().getTaskId());
                taskPK.setUserStoryStoryId(workloadDTO.getTask().getTaskPK().getUserStoryStoryId());
                workload.setTask(ProxyManager.getTaskProxy().findTask(taskPK));

                workload.setUser(ProxyManager.getUserProxy().findUserById(workloadDTO.getUser().getUserId()));

                workload.setStarted(workloadDTO.getStarted());
                workload.setStartTime(workloadDTO.getStartTime());
                workload.setStopTime(workloadDTO.getStopTime());
                try {
                    if (workload == null)
                        return Pair.of(false, null);

                    int id = ProxyManager.getWorkloadProxy().create(workload);
                    if (id == -1) {
                        System.out.println("ob vstavljanju s kontrolerjem je Workload id ... -1 :(");
                    } else {
                        insertedWorkloadID.add(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error while editing workload with message: " + e.getMessage());
                    return Pair.of(false, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Pair.of(false, null);
            }
        }
        return Pair.of(true, insertedWorkloadID);
    }

}
