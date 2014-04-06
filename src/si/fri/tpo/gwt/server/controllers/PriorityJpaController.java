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
import si.fri.tpo.gwt.server.jpa.UserStory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.jpa.Priority;

/**
 *
 * @author Administrator
 */
public class PriorityJpaController implements Serializable {

    public PriorityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public PriorityJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Priority priority) {
        if (priority.getUserStoryList() == null) {
            priority.setUserStoryList(new ArrayList<UserStory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UserStory> attachedUserStoryList = new ArrayList<UserStory>();
            for (UserStory userStoryListUserStoryToAttach : priority.getUserStoryList()) {
                userStoryListUserStoryToAttach = em.getReference(userStoryListUserStoryToAttach.getClass(), userStoryListUserStoryToAttach.getStoryId());
                attachedUserStoryList.add(userStoryListUserStoryToAttach);
            }
            priority.setUserStoryList(attachedUserStoryList);
            em.persist(priority);
            for (UserStory userStoryListUserStory : priority.getUserStoryList()) {
                Priority oldPriorityPriorityIdOfUserStoryListUserStory = userStoryListUserStory.getPriorityPriorityId();
                userStoryListUserStory.setPriorityPriorityId(priority);
                userStoryListUserStory = em.merge(userStoryListUserStory);
                if (oldPriorityPriorityIdOfUserStoryListUserStory != null) {
                    oldPriorityPriorityIdOfUserStoryListUserStory.getUserStoryList().remove(userStoryListUserStory);
                    oldPriorityPriorityIdOfUserStoryListUserStory = em.merge(oldPriorityPriorityIdOfUserStoryListUserStory);
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
            List<UserStory> userStoryListOld = persistentPriority.getUserStoryList();
            List<UserStory> userStoryListNew = priority.getUserStoryList();
            List<String> illegalOrphanMessages = null;
            for (UserStory userStoryListOldUserStory : userStoryListOld) {
                if (!userStoryListNew.contains(userStoryListOldUserStory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserStory " + userStoryListOldUserStory + " since its priorityPriorityId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<UserStory> attachedUserStoryListNew = new ArrayList<UserStory>();
            for (UserStory userStoryListNewUserStoryToAttach : userStoryListNew) {
                userStoryListNewUserStoryToAttach = em.getReference(userStoryListNewUserStoryToAttach.getClass(), userStoryListNewUserStoryToAttach.getStoryId());
                attachedUserStoryListNew.add(userStoryListNewUserStoryToAttach);
            }
            userStoryListNew = attachedUserStoryListNew;
            priority.setUserStoryList(userStoryListNew);
            priority = em.merge(priority);
            for (UserStory userStoryListNewUserStory : userStoryListNew) {
                if (!userStoryListOld.contains(userStoryListNewUserStory)) {
                    Priority oldPriorityPriorityIdOfUserStoryListNewUserStory = userStoryListNewUserStory.getPriorityPriorityId();
                    userStoryListNewUserStory.setPriorityPriorityId(priority);
                    userStoryListNewUserStory = em.merge(userStoryListNewUserStory);
                    if (oldPriorityPriorityIdOfUserStoryListNewUserStory != null && !oldPriorityPriorityIdOfUserStoryListNewUserStory.equals(priority)) {
                        oldPriorityPriorityIdOfUserStoryListNewUserStory.getUserStoryList().remove(userStoryListNewUserStory);
                        oldPriorityPriorityIdOfUserStoryListNewUserStory = em.merge(oldPriorityPriorityIdOfUserStoryListNewUserStory);
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
            List<UserStory> userStoryListOrphanCheck = priority.getUserStoryList();
            for (UserStory userStoryListOrphanCheckUserStory : userStoryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Priority (" + priority + ") cannot be destroyed since the UserStory " + userStoryListOrphanCheckUserStory + " in its userStoryList field has a non-nullable priorityPriorityId field.");
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
