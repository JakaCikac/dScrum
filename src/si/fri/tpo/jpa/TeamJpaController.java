/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author nanorax
 */
public class TeamJpaController implements Serializable {

    public TeamJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Team team) {
        if (team.getProjects() == null) {
            team.setProjects(new ArrayList<Project>());
        }
        if (team.getUsers() == null) {
            team.setUsers(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Project> attachedProjects = new ArrayList<Project>();
            for (Project projectsProjectToAttach : team.getProjects()) {
                projectsProjectToAttach = em.getReference(projectsProjectToAttach.getClass(), projectsProjectToAttach.getProjectId());
                attachedProjects.add(projectsProjectToAttach);
            }
            team.setProjects(attachedProjects);
            List<User> attachedUsers = new ArrayList<User>();
            for (User usersUserToAttach : team.getUsers()) {
                usersUserToAttach = em.getReference(usersUserToAttach.getClass(), usersUserToAttach.getUserId());
                attachedUsers.add(usersUserToAttach);
            }
            team.setUsers(attachedUsers);
            em.persist(team);
            for (Project projectsProject : team.getProjects()) {
                Team oldTeamOfProjectsProject = projectsProject.getTeam();
                projectsProject.setTeam(team);
                projectsProject = em.merge(projectsProject);
                if (oldTeamOfProjectsProject != null) {
                    oldTeamOfProjectsProject.getProjects().remove(projectsProject);
                    oldTeamOfProjectsProject = em.merge(oldTeamOfProjectsProject);
                }
            }
            for (User usersUser : team.getUsers()) {
                usersUser.getTeams().add(team);
                usersUser = em.merge(usersUser);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Team team) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team persistentTeam = em.find(Team.class, team.getTeamId());
            List<Project> projectsOld = persistentTeam.getProjects();
            List<Project> projectsNew = team.getProjects();
            List<User> usersOld = persistentTeam.getUsers();
            List<User> usersNew = team.getUsers();
            List<Project> attachedProjectsNew = new ArrayList<Project>();
            for (Project projectsNewProjectToAttach : projectsNew) {
                projectsNewProjectToAttach = em.getReference(projectsNewProjectToAttach.getClass(), projectsNewProjectToAttach.getProjectId());
                attachedProjectsNew.add(projectsNewProjectToAttach);
            }
            projectsNew = attachedProjectsNew;
            team.setProjects(projectsNew);
            List<User> attachedUsersNew = new ArrayList<User>();
            for (User usersNewUserToAttach : usersNew) {
                usersNewUserToAttach = em.getReference(usersNewUserToAttach.getClass(), usersNewUserToAttach.getUserId());
                attachedUsersNew.add(usersNewUserToAttach);
            }
            usersNew = attachedUsersNew;
            team.setUsers(usersNew);
            team = em.merge(team);
            for (Project projectsOldProject : projectsOld) {
                if (!projectsNew.contains(projectsOldProject)) {
                    projectsOldProject.setTeam(null);
                    projectsOldProject = em.merge(projectsOldProject);
                }
            }
            for (Project projectsNewProject : projectsNew) {
                if (!projectsOld.contains(projectsNewProject)) {
                    Team oldTeamOfProjectsNewProject = projectsNewProject.getTeam();
                    projectsNewProject.setTeam(team);
                    projectsNewProject = em.merge(projectsNewProject);
                    if (oldTeamOfProjectsNewProject != null && !oldTeamOfProjectsNewProject.equals(team)) {
                        oldTeamOfProjectsNewProject.getProjects().remove(projectsNewProject);
                        oldTeamOfProjectsNewProject = em.merge(oldTeamOfProjectsNewProject);
                    }
                }
            }
            for (User usersOldUser : usersOld) {
                if (!usersNew.contains(usersOldUser)) {
                    usersOldUser.getTeams().remove(team);
                    usersOldUser = em.merge(usersOldUser);
                }
            }
            for (User usersNewUser : usersNew) {
                if (!usersOld.contains(usersNewUser)) {
                    usersNewUser.getTeams().add(team);
                    usersNewUser = em.merge(usersNewUser);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = team.getTeamId();
                if (findTeam(id) == null) {
                    throw new NonexistentEntityException("The team with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Team team;
            try {
                team = em.getReference(Team.class, id);
                team.getTeamId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The team with id " + id + " no longer exists.", enfe);
            }
            List<Project> projects = team.getProjects();
            for (Project projectsProject : projects) {
                projectsProject.setTeam(null);
                projectsProject = em.merge(projectsProject);
            }
            List<User> users = team.getUsers();
            for (User usersUser : users) {
                usersUser.getTeams().remove(team);
                usersUser = em.merge(usersUser);
            }
            em.remove(team);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Team> findTeamEntities() {
        return findTeamEntities(true, -1, -1);
    }

    public List<Team> findTeamEntities(int maxResults, int firstResult) {
        return findTeamEntities(false, maxResults, firstResult);
    }

    private List<Team> findTeamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Team.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Team findTeam(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Team.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Team> rt = cq.from(Team.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
