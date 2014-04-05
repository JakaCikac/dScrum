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
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.UserStory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.jpa.Sprint;
import si.fri.tpo.jpa.SprintPK;

/**
 *
 * @author Administrator
 */
public class SprintJpaController implements Serializable {

    public SprintJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sprint sprint) throws PreexistingEntityException, Exception {
        if (sprint.getSprintPK() == null) {
            sprint.setSprintPK(new SprintPK());
        }
        if (sprint.getUserStoryCollection() == null) {
            sprint.setUserStoryCollection(new ArrayList<UserStory>());
        }
        sprint.getSprintPK().setPROJECTprojectid(sprint.getProject().getProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project = sprint.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                sprint.setProject(project);
            }
            Collection<UserStory> attachedUserStoryCollection = new ArrayList<UserStory>();
            for (UserStory userStoryCollectionUserStoryToAttach : sprint.getUserStoryCollection()) {
                userStoryCollectionUserStoryToAttach = em.getReference(userStoryCollectionUserStoryToAttach.getClass(), userStoryCollectionUserStoryToAttach.getStoryId());
                attachedUserStoryCollection.add(userStoryCollectionUserStoryToAttach);
            }
            sprint.setUserStoryCollection(attachedUserStoryCollection);
            em.persist(sprint);
            if (project != null) {
                project.getSprintCollection().add(sprint);
                project = em.merge(project);
            }
            for (UserStory userStoryCollectionUserStory : sprint.getUserStoryCollection()) {
                Sprint oldSprintOfUserStoryCollectionUserStory = userStoryCollectionUserStory.getSprint();
                userStoryCollectionUserStory.setSprint(sprint);
                userStoryCollectionUserStory = em.merge(userStoryCollectionUserStory);
                if (oldSprintOfUserStoryCollectionUserStory != null) {
                    oldSprintOfUserStoryCollectionUserStory.getUserStoryCollection().remove(userStoryCollectionUserStory);
                    oldSprintOfUserStoryCollectionUserStory = em.merge(oldSprintOfUserStoryCollectionUserStory);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSprint(sprint.getSprintPK()) != null) {
                throw new PreexistingEntityException("Sprint " + sprint + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sprint sprint) throws NonexistentEntityException, Exception {
        sprint.getSprintPK().setPROJECTprojectid(sprint.getProject().getProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sprint persistentSprint = em.find(Sprint.class, sprint.getSprintPK());
            Project projectOld = persistentSprint.getProject();
            Project projectNew = sprint.getProject();
            Collection<UserStory> userStoryCollectionOld = persistentSprint.getUserStoryCollection();
            Collection<UserStory> userStoryCollectionNew = sprint.getUserStoryCollection();
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                sprint.setProject(projectNew);
            }
            Collection<UserStory> attachedUserStoryCollectionNew = new ArrayList<UserStory>();
            for (UserStory userStoryCollectionNewUserStoryToAttach : userStoryCollectionNew) {
                userStoryCollectionNewUserStoryToAttach = em.getReference(userStoryCollectionNewUserStoryToAttach.getClass(), userStoryCollectionNewUserStoryToAttach.getStoryId());
                attachedUserStoryCollectionNew.add(userStoryCollectionNewUserStoryToAttach);
            }
            userStoryCollectionNew = attachedUserStoryCollectionNew;
            sprint.setUserStoryCollection(userStoryCollectionNew);
            sprint = em.merge(sprint);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getSprintCollection().remove(sprint);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getSprintCollection().add(sprint);
                projectNew = em.merge(projectNew);
            }
            for (UserStory userStoryCollectionOldUserStory : userStoryCollectionOld) {
                if (!userStoryCollectionNew.contains(userStoryCollectionOldUserStory)) {
                    userStoryCollectionOldUserStory.setSprint(null);
                    userStoryCollectionOldUserStory = em.merge(userStoryCollectionOldUserStory);
                }
            }
            for (UserStory userStoryCollectionNewUserStory : userStoryCollectionNew) {
                if (!userStoryCollectionOld.contains(userStoryCollectionNewUserStory)) {
                    Sprint oldSprintOfUserStoryCollectionNewUserStory = userStoryCollectionNewUserStory.getSprint();
                    userStoryCollectionNewUserStory.setSprint(sprint);
                    userStoryCollectionNewUserStory = em.merge(userStoryCollectionNewUserStory);
                    if (oldSprintOfUserStoryCollectionNewUserStory != null && !oldSprintOfUserStoryCollectionNewUserStory.equals(sprint)) {
                        oldSprintOfUserStoryCollectionNewUserStory.getUserStoryCollection().remove(userStoryCollectionNewUserStory);
                        oldSprintOfUserStoryCollectionNewUserStory = em.merge(oldSprintOfUserStoryCollectionNewUserStory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SprintPK id = sprint.getSprintPK();
                if (findSprint(id) == null) {
                    throw new NonexistentEntityException("The sprint with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SprintPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sprint sprint;
            try {
                sprint = em.getReference(Sprint.class, id);
                sprint.getSprintPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sprint with id " + id + " no longer exists.", enfe);
            }
            Project project = sprint.getProject();
            if (project != null) {
                project.getSprintCollection().remove(sprint);
                project = em.merge(project);
            }
            Collection<UserStory> userStoryCollection = sprint.getUserStoryCollection();
            for (UserStory userStoryCollectionUserStory : userStoryCollection) {
                userStoryCollectionUserStory.setSprint(null);
                userStoryCollectionUserStory = em.merge(userStoryCollectionUserStory);
            }
            em.remove(sprint);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sprint> findSprintEntities() {
        return findSprintEntities(true, -1, -1);
    }

    public List<Sprint> findSprintEntities(int maxResults, int firstResult) {
        return findSprintEntities(false, maxResults, firstResult);
    }

    private List<Sprint> findSprintEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sprint.class));
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

    public Sprint findSprint(SprintPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sprint.class, id);
        } finally {
            em.close();
        }
    }

    public int getSprintCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sprint> rt = cq.from(Sprint.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
