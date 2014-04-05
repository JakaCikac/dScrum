/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import si.fri.tpo.jpa.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.Team;

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
        if (team.getUserCollection() == null) {
            team.setUserCollection(new ArrayList<User>());
        }
        if (team.getProjectCollection() == null) {
            team.setProjectCollection(new ArrayList<Project>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<User> attachedUserCollection = new ArrayList<User>();
            for (User userCollectionUserToAttach : team.getUserCollection()) {
                userCollectionUserToAttach = em.getReference(userCollectionUserToAttach.getClass(), userCollectionUserToAttach.getUserId());
                attachedUserCollection.add(userCollectionUserToAttach);
            }
            team.setUserCollection(attachedUserCollection);
            Collection<Project> attachedProjectCollection = new ArrayList<Project>();
            for (Project projectCollectionProjectToAttach : team.getProjectCollection()) {
                projectCollectionProjectToAttach = em.getReference(projectCollectionProjectToAttach.getClass(), projectCollectionProjectToAttach.getProjectId());
                attachedProjectCollection.add(projectCollectionProjectToAttach);
            }
            team.setProjectCollection(attachedProjectCollection);
            em.persist(team);
            for (User userCollectionUser : team.getUserCollection()) {
                userCollectionUser.getTeamCollection().add(team);
                userCollectionUser = em.merge(userCollectionUser);
            }
            for (Project projectCollectionProject : team.getProjectCollection()) {
                Team oldTEAMteamidOfProjectCollectionProject = projectCollectionProject.getTEAMteamid();
                projectCollectionProject.setTEAMteamid(team);
                projectCollectionProject = em.merge(projectCollectionProject);
                if (oldTEAMteamidOfProjectCollectionProject != null) {
                    oldTEAMteamidOfProjectCollectionProject.getProjectCollection().remove(projectCollectionProject);
                    oldTEAMteamidOfProjectCollectionProject = em.merge(oldTEAMteamidOfProjectCollectionProject);
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
            Collection<User> userCollectionOld = persistentTeam.getUserCollection();
            Collection<User> userCollectionNew = team.getUserCollection();
            Collection<Project> projectCollectionOld = persistentTeam.getProjectCollection();
            Collection<Project> projectCollectionNew = team.getProjectCollection();
            Collection<User> attachedUserCollectionNew = new ArrayList<User>();
            for (User userCollectionNewUserToAttach : userCollectionNew) {
                userCollectionNewUserToAttach = em.getReference(userCollectionNewUserToAttach.getClass(), userCollectionNewUserToAttach.getUserId());
                attachedUserCollectionNew.add(userCollectionNewUserToAttach);
            }
            userCollectionNew = attachedUserCollectionNew;
            team.setUserCollection(userCollectionNew);
            Collection<Project> attachedProjectCollectionNew = new ArrayList<Project>();
            for (Project projectCollectionNewProjectToAttach : projectCollectionNew) {
                projectCollectionNewProjectToAttach = em.getReference(projectCollectionNewProjectToAttach.getClass(), projectCollectionNewProjectToAttach.getProjectId());
                attachedProjectCollectionNew.add(projectCollectionNewProjectToAttach);
            }
            projectCollectionNew = attachedProjectCollectionNew;
            team.setProjectCollection(projectCollectionNew);
            team = em.merge(team);
            for (User userCollectionOldUser : userCollectionOld) {
                if (!userCollectionNew.contains(userCollectionOldUser)) {
                    userCollectionOldUser.getTeamCollection().remove(team);
                    userCollectionOldUser = em.merge(userCollectionOldUser);
                }
            }
            for (User userCollectionNewUser : userCollectionNew) {
                if (!userCollectionOld.contains(userCollectionNewUser)) {
                    userCollectionNewUser.getTeamCollection().add(team);
                    userCollectionNewUser = em.merge(userCollectionNewUser);
                }
            }
            for (Project projectCollectionOldProject : projectCollectionOld) {
                if (!projectCollectionNew.contains(projectCollectionOldProject)) {
                    projectCollectionOldProject.setTEAMteamid(null);
                    projectCollectionOldProject = em.merge(projectCollectionOldProject);
                }
            }
            for (Project projectCollectionNewProject : projectCollectionNew) {
                if (!projectCollectionOld.contains(projectCollectionNewProject)) {
                    Team oldTEAMteamidOfProjectCollectionNewProject = projectCollectionNewProject.getTEAMteamid();
                    projectCollectionNewProject.setTEAMteamid(team);
                    projectCollectionNewProject = em.merge(projectCollectionNewProject);
                    if (oldTEAMteamidOfProjectCollectionNewProject != null && !oldTEAMteamidOfProjectCollectionNewProject.equals(team)) {
                        oldTEAMteamidOfProjectCollectionNewProject.getProjectCollection().remove(projectCollectionNewProject);
                        oldTEAMteamidOfProjectCollectionNewProject = em.merge(oldTEAMteamidOfProjectCollectionNewProject);
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
            Collection<User> userCollection = team.getUserCollection();
            for (User userCollectionUser : userCollection) {
                userCollectionUser.getTeamCollection().remove(team);
                userCollectionUser = em.merge(userCollectionUser);
            }
            Collection<Project> projectCollection = team.getProjectCollection();
            for (Project projectCollectionProject : projectCollection) {
                projectCollectionProject.setTEAMteamid(null);
                projectCollectionProject = em.merge(projectCollectionProject);
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
