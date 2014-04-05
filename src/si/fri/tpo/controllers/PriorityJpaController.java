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
import si.fri.tpo.jpa.UserStory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.Priority;

/**
 *
 * @author Administrator
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
        if (priority.getUserStoryCollection() == null) {
            priority.setUserStoryCollection(new ArrayList<UserStory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UserStory> attachedUserStoryCollection = new ArrayList<UserStory>();
            for (UserStory userStoryCollectionUserStoryToAttach : priority.getUserStoryCollection()) {
                userStoryCollectionUserStoryToAttach = em.getReference(userStoryCollectionUserStoryToAttach.getClass(), userStoryCollectionUserStoryToAttach.getStoryId());
                attachedUserStoryCollection.add(userStoryCollectionUserStoryToAttach);
            }
            priority.setUserStoryCollection(attachedUserStoryCollection);
            em.persist(priority);
            for (UserStory userStoryCollectionUserStory : priority.getUserStoryCollection()) {
                Priority oldPRIORITYpriorityidOfUserStoryCollectionUserStory = userStoryCollectionUserStory.getPRIORITYpriorityid();
                userStoryCollectionUserStory.setPRIORITYpriorityid(priority);
                userStoryCollectionUserStory = em.merge(userStoryCollectionUserStory);
                if (oldPRIORITYpriorityidOfUserStoryCollectionUserStory != null) {
                    oldPRIORITYpriorityidOfUserStoryCollectionUserStory.getUserStoryCollection().remove(userStoryCollectionUserStory);
                    oldPRIORITYpriorityidOfUserStoryCollectionUserStory = em.merge(oldPRIORITYpriorityidOfUserStoryCollectionUserStory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Priority priority) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Priority persistentPriority = em.find(Priority.class, priority.getPriorityId());
            Collection<UserStory> userStoryCollectionOld = persistentPriority.getUserStoryCollection();
            Collection<UserStory> userStoryCollectionNew = priority.getUserStoryCollection();
            List<String> illegalOrphanMessages = null;
            for (UserStory userStoryCollectionOldUserStory : userStoryCollectionOld) {
                if (!userStoryCollectionNew.contains(userStoryCollectionOldUserStory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserStory " + userStoryCollectionOldUserStory + " since its PRIORITYpriorityid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UserStory> attachedUserStoryCollectionNew = new ArrayList<UserStory>();
            for (UserStory userStoryCollectionNewUserStoryToAttach : userStoryCollectionNew) {
                userStoryCollectionNewUserStoryToAttach = em.getReference(userStoryCollectionNewUserStoryToAttach.getClass(), userStoryCollectionNewUserStoryToAttach.getStoryId());
                attachedUserStoryCollectionNew.add(userStoryCollectionNewUserStoryToAttach);
            }
            userStoryCollectionNew = attachedUserStoryCollectionNew;
            priority.setUserStoryCollection(userStoryCollectionNew);
            priority = em.merge(priority);
            for (UserStory userStoryCollectionNewUserStory : userStoryCollectionNew) {
                if (!userStoryCollectionOld.contains(userStoryCollectionNewUserStory)) {
                    Priority oldPRIORITYpriorityidOfUserStoryCollectionNewUserStory = userStoryCollectionNewUserStory.getPRIORITYpriorityid();
                    userStoryCollectionNewUserStory.setPRIORITYpriorityid(priority);
                    userStoryCollectionNewUserStory = em.merge(userStoryCollectionNewUserStory);
                    if (oldPRIORITYpriorityidOfUserStoryCollectionNewUserStory != null && !oldPRIORITYpriorityidOfUserStoryCollectionNewUserStory.equals(priority)) {
                        oldPRIORITYpriorityidOfUserStoryCollectionNewUserStory.getUserStoryCollection().remove(userStoryCollectionNewUserStory);
                        oldPRIORITYpriorityidOfUserStoryCollectionNewUserStory = em.merge(oldPRIORITYpriorityidOfUserStoryCollectionNewUserStory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = priority.getPriorityId();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<UserStory> userStoryCollectionOrphanCheck = priority.getUserStoryCollection();
            for (UserStory userStoryCollectionOrphanCheckUserStory : userStoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Priority (" + priority + ") cannot be destroyed since the UserStory " + userStoryCollectionOrphanCheckUserStory + " in its userStoryCollection field has a non-nullable PRIORITYpriorityid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

    public Priority findPriority(Integer id) {
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
