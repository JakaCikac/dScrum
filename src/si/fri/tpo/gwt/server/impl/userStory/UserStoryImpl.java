package si.fri.tpo.gwt.server.impl.userStory;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anze on 27. 04. 14.
 */
public class UserStoryImpl {

    public static List<UserStoryDTO> getAllStoryOfProject(ProjectDTO projectDTO) {
        List<UserStory> userStoryList = ProxyManager.getUserStoryProxy().getUserStoryList(projectDTO.getProjectId());

        List<UserStoryDTO> userStoryDTOList = new ArrayList<UserStoryDTO>();
        for (UserStory userStory : userStoryList){
            UserStoryDTO userStoryDTO = new UserStoryDTO();
            userStoryDTO.setStoryId(userStory.getStoryId());
            userStoryDTO.setName(userStory.getName());
            userStoryDTO.setContent(userStory.getContent());
            userStoryDTO.setStatus(userStory.getStatus());
            if (userStory.getEstimateTime() != null) {
                userStoryDTO.setEstimateTime(userStory.getEstimateTime().doubleValue());
            } else userStoryDTO.setEstimateTime(null);
            userStoryDTO.setBusinessValue(userStory.getBusinessValue());

            PriorityDTO priorityDTO = new PriorityDTO();
            priorityDTO.setPriorityId(userStory.getPriorityPriorityId().getPriorityId());
            priorityDTO.setName(userStory.getPriorityPriorityId().getName());
            userStoryDTO.setPriorityPriorityId(priorityDTO);

            userStoryDTO.setProjectProjectId(projectDTO);

            List<AcceptanceTestDTO> acceptanceTestDTOList = new ArrayList<AcceptanceTestDTO>();
            List<AcceptanceTest> acceptanceTestList = userStory.getAcceptanceTestList();
            for (AcceptanceTest acceptanceTest : acceptanceTestList){
                AcceptanceTestDTO acceptanceTestDTO = new AcceptanceTestDTO();
                acceptanceTestDTO.setAcceptanceTestId(acceptanceTest.getAcceptanceTestId());
                acceptanceTestDTO.setContent(acceptanceTest.getContent());
                acceptanceTestDTO.setUserStoryStoryId(userStoryDTO);
                acceptanceTestDTOList.add(acceptanceTestDTO);
            }
            userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList);
            //TODO: Possible error, later, when we need all userStoryDTO data from DB
            userStoryDTOList.add(userStoryDTO);
        }
        return userStoryDTOList;
    }

    public static Pair<Boolean, Integer> saveUserStory(UserStoryDTO userStoryDTO) {

        UserStory userStory = new UserStory();
        try {
            userStory.setName(userStoryDTO.getName());
            userStory.setContent(userStoryDTO.getContent());
            userStory.setBusinessValue(userStoryDTO.getBusinessValue());
            userStory.setStatus(userStoryDTO.getStatus());

            Priority priority = new Priority();
            priority.setPriorityId(userStoryDTO.getPriorityPriorityId().getPriorityId());
            priority.setName(userStoryDTO.getPriorityPriorityId().getName());
            userStory.setPriorityPriorityId(priority);

            Project project = ProxyManager.getProjectProxy().findProjectByName(userStoryDTO.getProjectProjectId().getName());
            if (project == null){
                return Pair.of(false, -1);
            } else {
                userStory.setProjectProjectId(project);
            }

            List<AcceptanceTest> acceptanceTestList = new ArrayList<AcceptanceTest>();
            if (userStoryDTO.getAcceptanceTestList() != null) {
                for (AcceptanceTestDTO acceptanceTestDTO : userStoryDTO.getAcceptanceTestList()) {
                    AcceptanceTest acceptanceTest = ProxyManager.getAcceptanceTestProxy().findAcceptanceTest(acceptanceTestDTO.getAcceptanceTestId());
                    acceptanceTestList.add(acceptanceTest);
                }
                userStory.setAcceptanceTestList(acceptanceTestList);
            }

            try {
                if (userStory == null)
                    return Pair.of(false, -2);

                ProxyManager.getUserStoryProxy().create(userStory);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, -3);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -4);
        }
        return Pair.of(true, 0);
    }

    public static Pair<Boolean, String> updateUserStory(UserStoryDTO userStoryDTO) {
        try {
            UserStory userStory = ProxyManager.getUserStoryProxy().findUserStory(userStoryDTO.getStoryId());
            userStory.setName(userStoryDTO.getName());
            userStory.setContent(userStoryDTO.getContent());
            userStory.setBusinessValue(userStoryDTO.getBusinessValue());
            userStory.setStatus(userStoryDTO.getStatus());
            userStory.setEstimateTime(userStoryDTO.getEstimateTime());

            Priority priority = ProxyManager.getPriorityProxy().findPriority(userStoryDTO.getPriorityPriorityId().getPriorityId());
            userStory.setPriorityPriorityId(priority);

            Project project = ProxyManager.getProjectProxy().findProjectByName(userStoryDTO.getProjectProjectId().getName());
            if (project == null){
                return Pair.of(false, "Data error project!");
            } else {
                userStory.setProjectProjectId(project);
            }

            List<AcceptanceTest> acceptanceTestList = new ArrayList<AcceptanceTest>();
            for (AcceptanceTestDTO acceptanceTestDTO : userStoryDTO.getAcceptanceTestList()) {
                AcceptanceTest acceptanceTest = ProxyManager.getAcceptanceTestProxy().findAcceptanceTest(acceptanceTestDTO.getAcceptanceTestId());
                acceptanceTestList.add(acceptanceTest);
            }
            userStory.setAcceptanceTestList(acceptanceTestList);

            //SprintDTO sprintDTO = userStoryDTO.getSprint();
            //SprintPKDTO sprintPKDTO = sprintDTO.getSprintPK();
            SprintPK sprintPK = userStory.getSprint().getSprintPK();
            //sprintPK.setSprintId(sprintPKDTO.getSprintId());
            //sprintPK.setProjectProjectId(sprintPKDTO.getProjectProjectId());
            Sprint sprint = ProxyManager.getSprintProxy().findSprint(sprintPK);
            userStory.setSprint(sprint);

            try {
                sprintPKDTO = sprintDTO.getSprintPK();
                sprintPK.setSprintId(sprintPKDTO.getSprintId());
                sprintPK.setProjectProjectId(sprintPKDTO.getProjectProjectId());
                Sprint sprint = ProxyManager.getSprintProxy().findSprint(sprintPK);
                userStory.setSprint(sprint);
            } catch(NullPointerException ex) {
                sprintPKDTO = null;
            }
            try {
                if (userStory == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getUserStoryProxy().edit(userStory);

            } catch (Exception e) {
                System.err.println("Error while editing user story with message: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "User story should be updated.");
    }
}
