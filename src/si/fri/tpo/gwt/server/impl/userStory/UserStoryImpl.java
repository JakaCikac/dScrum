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
        for (UserStory userStory : userStoryList) {
            userStory = ProxyManager.getUserStoryProxy().findUserStory(userStory.getStoryId());
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
            for (AcceptanceTest acceptanceTest : acceptanceTestList) {
                acceptanceTest = ProxyManager.getAcceptanceTestProxy().findAcceptanceTest(acceptanceTest.getAcceptanceTestId());
                AcceptanceTestDTO acceptanceTestDTO = new AcceptanceTestDTO();
                acceptanceTestDTO.setAcceptanceTestId(acceptanceTest.getAcceptanceTestId());
                acceptanceTestDTO.setContent(acceptanceTest.getContent());
                acceptanceTestDTO.setUserStoryStoryId(userStoryDTO);
                acceptanceTestDTOList.add(acceptanceTestDTO);
            }
            userStoryDTO.setAcceptanceTestList(acceptanceTestDTOList);

            if (userStory.getSprint() != null){
                Sprint sprint = ProxyManager.getSprintProxy().findSprint(userStory.getSprint().getSprintPK());
                SprintDTO sprintDTO = new SprintDTO();
                SprintPKDTO sprintPKDTO = new SprintPKDTO();
                sprintPKDTO.setSprintId(sprint.getSprintPK().getSprintId());
                sprintPKDTO.setProjectProjectId(sprint.getSprintPK().getProjectProjectId());
                sprintDTO.setSprintPK(sprintPKDTO);
                sprintDTO.setSeqNumber(sprint.getSeqNumber());
                sprintDTO.setEndDate(sprint.getEndDate());
                sprintDTO.setStartDate(sprint.getStartDate());
                sprintDTO.setStatus(sprint.getStatus());
                sprintDTO.setVelocity(sprint.getVelocity());
                userStoryDTO.setSprint(sprintDTO);
            }
            //TODO: Possible error, later, when we need all userStoryDTO data from DB
            userStoryDTOList.add(userStoryDTO);
        }
        return userStoryDTOList;
    }

    public static Pair<Boolean, String> saveUserStory(UserStoryDTO userStoryDTO) {

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
                return Pair.of(false, "Project for this user story does not exist!");
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
                    return Pair.of(false, "Data error!");

                ProxyManager.getUserStoryProxy().create(userStory);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, "Error creating user story!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, "Unknown error!");
        }
        return Pair.of(true, "User story was saved successfully! :)");
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

            if (userStoryDTO.getSprint() != null && userStoryDTO.getSprint().getSprintPK() != null) {
                SprintDTO sprintDTO = userStoryDTO.getSprint();
                SprintPKDTO sprintPKDTO = sprintDTO.getSprintPK();
                SprintPK sprintPK = new SprintPK();
                sprintPK.setSprintId(sprintPKDTO.getSprintId());
                sprintPK.setProjectProjectId(sprintPKDTO.getProjectProjectId());
                Sprint sprint = ProxyManager.getSprintProxy().findSprint(sprintPK);
                userStory.setSprint(sprint);
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

    public static Pair<Boolean, String> deleteUserStory(UserStoryDTO userStoryDTO) {
        try {
            UserStory userStory = ProxyManager.getUserStoryProxy().findUserStory(userStoryDTO.getStoryId());
            try {
                if (userStory == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getUserStoryProxy().destroy(userStory.getStoryId());

            } catch (Exception e) {
                System.err.println("Error while deleting user story with message: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "User story should be deleted.");
    }
}
