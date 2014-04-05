/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.gwt.server.jpa.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.Team;

/**
 *
 * @author Administrator
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
        if (team.getUserList() == null) {
            team.setUserList(new ArrayList<User>());
        }
        if (team.getProjectList() == null) {
            team.setProjectList(new ArrayList<Project>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<User> attachedUserList = new ArrayList<User>();
            for (User userListUserToAttach : team.getUserList()) {
                userListUserToAttach = em.getReference(userListUserToAttach.getClass(), userListUserToAttach.getUserId());
                attachedUserList.add(userListUserToAttach);
            }
            team.setUserList(attachedUserList);
            List<Project> attachedProjectList = new ArrayList<Project>();
            for (Project projectListProjectToAttach : team.getProjectList()) {
                projectListProjectToAttach = em.getReference(projectListProjectToAttach.getClass(), projectListProjectToAttach.getProjectId());
                attachedProjectList.add(projectListProjectToAttach);
            }
            team.setProjectList(attachedProjectList);
            em.persist(team);
            for (User userListUser : team.getUserList()) {
                userListUser.getTeamList().add(team);
                userListUser = em.merge(userListUser);
            }
            for (Project projectListProject : team.getProjectList()) {
                Team oldTeamTeamIdOfProjectListProject = projectListProject.getTeamTeamId();
                projectListProject.setTeamTeamId(team);
                projectListProject = em.merge(projectListProject);
                if (oldTeamTeamIdOfProjectListProject != null) {
                    oldTeamTeamIdOfProjectListProject.getProjectList().remove(projectListProject);
                    oldTeamTeamIdOfProjectListProject = em.merge(oldTeamTeamIdOfProjectListProject);
                }
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
            List<User> userListOld = persistentTeam.getUserList();
            List<User> userListNew = team.getUserList();
            List<Project> projectListOld = persistentTeam.getProjectList();
            List<Project> projectListNew = team.getProjectList();
            List<User> attachedUserListNew = new ArrayList<User>();
            for (User userListNewUserToAttach : userListNew) {
                userListNewUserToAttach = em.getReference(userListNewUserToAttach.getClass(), userListNewUserToAttach.getUserId());
                attachedUserListNew.add(userListNewUserToAttach);
            }
            userListNew = attachedUserListNew;
            team.setUserList(userListNew);
            List<Project> attachedProjectListNew = new ArrayList<Project>();
            for (Project projectListNewProjectToAttach : projectListNew) {
                projectListNewProjectToAttach = em.getReference(projectListNewProjectToAttach.getClass(), projectListNewProjectToAttach.getProjectId());
                attachedProjectListNew.add(projectListNewProjectToAttach);
            }
            projectListNew = attachedProjectListNew;
            team.setProjectList(projectListNew);
            team = em.merge(team);
            for (User userListOldUser : userListOld) {
                if (!userListNew.contains(userListOldUser)) {
                    userListOldUser.getTeamList().remove(team);
                    userListOldUser = em.merge(userListOldUser);
                }
            }
            for (User userListNewUser : userListNew) {
                if (!userListOld.contains(userListNewUser)) {
                    userListNewUser.getTeamList().add(team);
                    userListNewUser = em.merge(userListNewUser);
                }
            }
            for (Project projectListOldProject : projectListOld) {
                if (!projectListNew.contains(projectListOldProject)) {
                    projectListOldProject.setTeamTeamId(null);
                    projectListOldProject = em.merge(projectListOldProject);
                }
            }
            for (Project projectListNewProject : projectListNew) {
                if (!projectListOld.contains(projectListNewProject)) {
                    Team oldTeamTeamIdOfProjectListNewProject = projectListNewProject.getTeamTeamId();
                    projectListNewProject.setTeamTeamId(team);
                    projectListNewProject = em.merge(projectListNewProject);
                    if (oldTeamTeamIdOfProjectListNewProject != null && !oldTeamTeamIdOfProjectListNewProject.equals(team)) {
                        oldTeamTeamIdOfProjectListNewProject.getProjectList().remove(projectListNewProject);
                        oldTeamTeamIdOfProjectListNewProject = em.merge(oldTeamTeamIdOfProjectListNewProject);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = team.getTeamId();
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            List<User> userList = team.getUserList();
            for (User userListUser : userList) {
                userListUser.getTeamList().remove(team);
                userListUser = em.merge(userListUser);
            }
            List<Project> projectList = team.getProjectList();
            for (Project projectListProject : projectList) {
                projectListProject.setTeamTeamId(null);
                projectListProject = em.merge(projectListProject);
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

    public Team findTeam(Integer id) {
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
