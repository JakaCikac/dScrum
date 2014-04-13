package si.fri.tpo.gwt.server.impl.project;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.ProjectDTO;
import si.fri.tpo.gwt.client.dto.UserDTO;
import si.fri.tpo.gwt.server.impl.fill.FillDTO;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 13/04/14.
 */
public class ProjectImpl {


    public static List<ProjectDTO> getAllProject() {
        //Get project list
        List<Project> projectList;
        // retrieve project list from JPA controller
        projectList = ProxyManager.getProjectProxy().getProjectsList();

        ArrayList<ProjectDTO> resList = new ArrayList<ProjectDTO>();
        ProjectDTO projectDTO;
        for (Project p : projectList) {
            projectDTO = new ProjectDTO();


            resList.add(projectDTO);
        }
        return resList;
    }

    public static Pair<Boolean, String> saveNewProject(ProjectDTO projectDTO) {

        // check for project in database
        System.out.println("Project to check in base: " + projectDTO.getName());
        Project existingProject = ProxyManager.getProjectProxy().findProjectByName(projectDTO.getName());

        // TODO: complete project
        if (existingProject != null) {
            System.out.println("Existing user exists!");
            return Pair.of(false, "User with this username already exists!");
        } else System.out.println("User check passed, no existing user");

        return null;
    }

}



   /*             User u = new User();
                System.out.println("DTO username = " + dto.getUsername());
                u.setLastName(dto.getLastName());
                u.setFirstName(dto.getFirstName());
                u.setEmail(dto.getEmail());
                u.setTimeCreated(dto.getTimeCreated());
                u.setUsername(dto.getUsername());
                u.setSalt(dto.getSalt());
                u.setIsActive(dto.isActive());
                u.setIsAdmin(dto.isAdmin());
                u.setPassword(dto.getPassword());
                try {
                    if (u == null)
                        return Pair.of(false, "Data error!");

                    ProxyManager.getUserProxy().create(u);

                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    return Pair.of(false, e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "");
    }
}

*/
