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
public class PriorityJpaController implements Serializable {

    public PriorityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Priority priority) {
        if (priority.getUserStories() == null) {
            priority.setUserStories(new ArrayList<UserStory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UserStory> attachedUserStories = new ArrayList<UserStory>();
            for (UserStory userStoriesUserStoryToAttach : priority.getUserStories()) {
                userStoriesUserStoryToAttach = em.getReference(userStoriesUserStoryToAttach.getClass(), userStoriesUserStoryToAttach.getStoryId());
                attachedUserStories.add(userStoriesUserStoryToAttach);
            }
            priority.setUserStories(attachedUserStories);
            em.persist(priority);
            for (UserStory userStoriesUserStory : priority.getUserStories()) {
                Priority oldPriorityOfUserStoriesUserStory = userStoriesUserStory.getPriority();
                userStoriesUserStory.setPriority(priority);
                userStoriesUserStory = em.merge(userStoriesUserStory);
                if (oldPriorityOfUserStoriesUserStory != null) {
                    oldPriorityOfUserStoriesUserStory.getUserStories().remove(userStoriesUserStory);
                    oldPriorityOfUserStoriesUserStory = em.merge(oldPriorityOfUserStoriesUserStory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Priority priority) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Priority persistentPriority = em.find(Priority.class, priority.getPriorityId());
            List<UserStory> userStoriesOld = persistentPriority.getUserStories();
            List<UserStory> userStoriesNew = priority.getUserStories();
            List<UserStory> attachedUserStoriesNew = new ArrayList<UserStory>();
            for (UserStory userStoriesNewUserStoryToAttach : userStoriesNew) {
                userStoriesNewUserStoryToAttach = em.getReference(userStoriesNewUserStoryToAttach.getClass(), userStoriesNewUserStoryToAttach.getStoryId());
                attachedUserStoriesNew.add(userStoriesNewUserStoryToAttach);
            }
            userStoriesNew = attachedUserStoriesNew;
            priority.setUserStories(userStoriesNew);
            priority = em.merge(priority);
            for (UserStory userStoriesOldUserStory : userStoriesOld) {
                if (!userStoriesNew.contains(userStoriesOldUserStory)) {
                    userStoriesOldUserStory.setPriority(null);
                    userStoriesOldUserStory = em.merge(userStoriesOldUserStory);
                }
            }
            for (UserStory userStoriesNewUserStory : userStoriesNew) {
                if (!userStoriesOld.contains(userStoriesNewUserStory)) {
                    Priority oldPriorityOfUserStoriesNewUserStory = userStoriesNewUserStory.getPriority();
                    userStoriesNewUserStory.setPriority(priority);
                    userStoriesNewUserStory = em.merge(userStoriesNewUserStory);
                    if (oldPriorityOfUserStoriesNewUserStory != null && !oldPriorityOfUserStoriesNewUserStory.equals(priority)) {
                        oldPriorityOfUserStoriesNewUserStory.getUserStories().remove(userStoriesNewUserStory);
                        oldPriorityOfUserStoriesNewUserStory = em.merge(oldPriorityOfUserStoriesNewUserStory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = priority.getPriorityId();
                if (findPriority(id) == null) {
                    throw new NonexistentEntityException("The priority with id " + id + " no longer exists.");
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
            Priority priority;
            try {
                priority = em.getReference(Priority.class, id);
                priority.getPriorityId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The priority with id " + id + " no longer exists.", enfe);
            }
            List<UserStory> userStories = priority.getUserStories();
            for (UserStory userStoriesUserStory : userStories) {
                userStoriesUserStory.setPriority(null);
                userStoriesUserStory = em.merge(userStoriesUserStory);
            }
            em.remove(priority);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Priority> findPriorityEntities() {
        return findPriorityEntities(true, -1, -1);
    }

    public List<Priority> findPriorityEntities(int maxResults, int firstResult) {
        return findPriorityEntities(false, maxResults, firstResult);
    }

    private List<Priority> findPriorityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Priority.class));
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

    public Priority findPriority(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Priority.class, id);
        } finally {
            em.close();
        }
    }

    public int getPriorityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Priority> rt = cq.from(Priority.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
