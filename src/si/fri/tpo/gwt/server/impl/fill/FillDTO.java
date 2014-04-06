package si.fri.tpo.gwt.server.impl.fill;

import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.server.jpa.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 05/04/14.
 */
public class FillDTO {

    public static AcceptanceTestDTO fillAcceptanceTestDTO(AcceptanceTest acceptanceTest) {
        AcceptanceTestDTO acceptanceTestDTO = new AcceptanceTestDTO();
        acceptanceTestDTO.setAcceptanceTestId(acceptanceTest.getAcceptanceTestId());
        acceptanceTestDTO.setContent(acceptanceTest.getContent());
        acceptanceTestDTO.setUserStoryStoryId(acceptanceTest.getUserStoryStoryId());
        return acceptanceTestDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    public static CommentDTO fillCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentPK(commentDTO.getCommentPK());
        commentDTO.setContent(commentDTO.getContent());
        commentDTO.setCreatetime(commentDTO.getCreatetime());
        commentDTO.setUser(commentDTO.getUser());
        commentDTO.setDiscussion(commentDTO.getDiscussion());
        return commentDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    public static ProjectDTO fillProjectData(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStatus(project.getStatus());
        projectDTO.setTeamTeamId(project.getTeamTeamId());
        projectDTO.setDiscussionList(project.getDiscussionList());
        projectDTO.setDailyScrumEntryList(project.getDailyScrumEntryList());
        projectDTO.setSprintList(project.getSprintList());
        projectDTO.setUserStoryList(project.getUserStoryList());
        return projectDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    public static TeamDTO fillTeamData(Team team) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(team.getTeamId());
        teamDTO.setScrumMasterId(team.getScrumMasterId());
        teamDTO.setProductOwnerId(team.getProductOwnerId());
        teamDTO.setUserList(team.getUserList());
        teamDTO.setProjectList(team.getProjectList());
        return teamDTO;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
    public static UserDTO fillUserData(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAdmin(user.isAdmin());
        userDTO.setSalt(user.getSalt());
        userDTO.setActive(user.isActive());
        userDTO.setTimeCreated(user.getTimeCreated());
        userDTO.setTeamList(user.getTeamList());
        userDTO.setWorkloadList(user.getWorkloadList());
        userDTO.setDiscussionList(user.getDiscussionList());
        userDTO.setTaskList(user.getTaskList());
        userDTO.setDailyScrumEntryList(user.getDailyScrumEntryList());
        userDTO.setCommentList(user.getCommentList());
        return userDTO;
    }

    private static List<TeamDTO> fillTeamDTOs(List<TeamDTO> teams) {
        List<TeamDTO> teamDTOs = new ArrayList<TeamDTO>();
        for (Team team : teams)
            teamDTOs.add(fillTeamDTO(team));

        return teamDTOs;
    }

    public static EnrollmentStudentDTO fillEnrollmentStudentDTO(EnrollmentStudent es) {
        EnrollmentDTO dto = new EnrollmentStudentDTO();
        return dto;
    }

    public static EnrollmentStudentDTO fillEnrollmentStudentDTO(EnrollmentStudent es) {
        EnrollmentStudentDTO dto = new EnrollmentStudentDTO();
        dto.setId(es.getId());

        if (es.getStudentSpecialNeeds() != null)
            dto.setStudentSpecialNeedsType(es.getStudentSpecialNeeds().getType());

        dto.setStudyMode(es.isStudyMode());
        dto.setCourseList(fillCourseDTOs(es.getCourse()));

        dto.setEnrollmentRemoteDTO(fillEnrollmentRemoteDTO(es.getEnrollmentRemote()));

        dto.setEnrollmentTypeDTO(fillEnrollmentTypeDTO(es.getEnrollmentType()));
        dto.setStudyCurriculumDTO(fillStudyCurriculumDTO(es.getStudyCurriculum()));
        dto.setStudyLevelDTO(fillStudyLevelDTO(es.getStudyLevel()));
        if (dto.getStudyCurriculumDTO() != null)
            dto.setStudyWayDTO(dto.getStudyCurriculumDTO().getStudyProgramDTO());   //to je isto kot StudyProgramDTO
        if (es.getSchoolYear() != null && es.getSchoolYear().getYear() != null)
            dto.setYear(Integer.valueOf(es.getSchoolYear().getYear().split("/")[0]));

        if (es.getSignInExam() != null)
            dto.setSignInExamDTOs(fillSignInExamDTOs(es.getSignInExam()));

        //dto.setPersonDTO();   //TODO if needed ; probably cycle !!!
        long personId = es.getStudent().getPerson().getId();
        dto.setPersonDTO(DTOfiller.fillPersonDTO(ProxyManager.getPersonProxy().findPerson(personId), false));

        dto.setDisplay(es.getSchoolYear().getYear() + ", " +
                es.getStudyCurriculum().getStudyGrade().getDescription() + ", " +
                es.getStudyCurriculum().getStudyProgram().getName() + ", " +
                es.getEnrollmentType().getType() + " (" +
                es.getEnrollmentType().getDescription() + ")");
        return dto;
    }
    /* -------------------------------------------------------------------------------------------------------------- */
}
