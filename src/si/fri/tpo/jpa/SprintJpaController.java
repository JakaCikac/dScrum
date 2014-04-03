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
import si.fri.tpo.jpa.exceptions.PreexistingEntityException;

/**
 *
 * @author nanorax
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
        if (sprint.getId() == null) {
            sprint.setId(new SprintPK());
        }
        if (sprint.getUserStories() == null) {
            sprint.setUserStories(new ArrayList<UserStory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project = sprint.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                sprint.setProject(project);
            }
            List<UserStory> attachedUserStories = new ArrayList<UserStory>();
            for (UserStory userStoriesUserStoryToAttach : sprint.getUserStories()) {
                userStoriesUserStoryToAttach = em.getReference(userStoriesUserStoryToAttach.getClass(), userStoriesUserStoryToAttach.getStoryId());
                attachedUserStories.add(userStoriesUserStoryToAttach);
            }
            sprint.setUserStories(attachedUserStories);
            em.persist(sprint);
            if (project != null) {
                project.getSprints().add(sprint);
                project = em.merge(project);
            }
            for (UserStory userStoriesUserStory : sprint.getUserStories()) {
                Sprint oldSprintOfUserStoriesUserStory = userStoriesUserStory.getSprint();
                userStoriesUserStory.setSprint(sprint);
                userStoriesUserStory = em.merge(userStoriesUserStory);
                if (oldSprintOfUserStoriesUserStory != null) {
                    oldSprintOfUserStoriesUserStory.getUserStories().remove(userStoriesUserStory);
                    oldSprintOfUserStoriesUserStory = em.merge(oldSprintOfUserStoriesUserStory);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSprint(sprint.getId()) != null) {
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sprint persistentSprint = em.find(Sprint.class, sprint.getId());
            Project projectOld = persistentSprint.getProject();
            Project projectNew = sprint.getProject();
            List<UserStory> userStoriesOld = persistentSprint.getUserStories();
            List<UserStory> userStoriesNew = sprint.getUserStories();
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                sprint.setProject(projectNew);
            }
            List<UserStory> attachedUserStoriesNew = new ArrayList<UserStory>();
            for (UserStory userStoriesNewUserStoryToAttach : userStoriesNew) {
                userStoriesNewUserStoryToAttach = em.getReference(userStoriesNewUserStoryToAttach.getClass(), userStoriesNewUserStoryToAttach.getStoryId());
                attachedUserStoriesNew.add(userStoriesNewUserStoryToAttach);
            }
            userStoriesNew = attachedUserStoriesNew;
            sprint.setUserStories(userStoriesNew);
            sprint = em.merge(sprint);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getSprints().remove(sprint);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getSprints().add(sprint);
                projectNew = em.merge(projectNew);
            }
            for (UserStory userStoriesOldUserStory : userStoriesOld) {
                if (!userStoriesNew.contains(userStoriesOldUserStory)) {
                    userStoriesOldUserStory.setSprint(null);
                    userStoriesOldUserStory = em.merge(userStoriesOldUserStory);
                }
            }
            for (UserStory userStoriesNewUserStory : userStoriesNew) {
                if (!userStoriesOld.contains(userStoriesNewUserStory)) {
                    Sprint oldSprintOfUserStoriesNewUserStory = userStoriesNewUserStory.getSprint();
                    userStoriesNewUserStory.setSprint(sprint);
                    userStoriesNewUserStory = em.merge(userStoriesNewUserStory);
                    if (oldSprintOfUserStoriesNewUserStory != null && !oldSprintOfUserStoriesNewUserStory.equals(sprint)) {
                        oldSprintOfUserStoriesNewUserStory.getUserStories().remove(userStoriesNewUserStory);
                        oldSprintOfUserStoriesNewUserStory = em.merge(oldSprintOfUserStoriesNewUserStory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SprintPK id = sprint.getId();
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
                sprint.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sprint with id " + id + " no longer exists.", enfe);
            }
            Project project = sprint.getProject();
            if (project != null) {
                project.getSprints().remove(sprint);
                project = em.merge(project);
            }
            List<UserStory> userStories = sprint.getUserStories();
            for (UserStory userStoriesUserStory : userStories) {
                userStoriesUserStory.setSprint(null);
                userStoriesUserStory = em.merge(userStoriesUserStory);
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
