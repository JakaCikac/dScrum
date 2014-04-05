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
import si.fri.tpo.jpa.Sprint;
import si.fri.tpo.jpa.Priority;
import si.fri.tpo.jpa.Project;
import si.fri.tpo.jpa.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.AcceptanceTest;
import si.fri.tpo.jpa.UserStory;

/**
 *
 * @author Administrator
 */
public class UserStoryJpaController implements Serializable {

    public UserStoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserStory userStory) {
        if (userStory.getTaskCollection() == null) {
            userStory.setTaskCollection(new ArrayList<Task>());
        }
        if (userStory.getAcceptanceTestCollection() == null) {
            userStory.setAcceptanceTestCollection(new ArrayList<AcceptanceTest>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sprint sprint = userStory.getSprint();
            if (sprint != null) {
                sprint = em.getReference(sprint.getClass(), sprint.getSprintPK());
                userStory.setSprint(sprint);
            }
            Priority PRIORITYpriorityid = userStory.getPRIORITYpriorityid();
            if (PRIORITYpriorityid != null) {
                PRIORITYpriorityid = em.getReference(PRIORITYpriorityid.getClass(), PRIORITYpriorityid.getPriorityId());
                userStory.setPRIORITYpriorityid(PRIORITYpriorityid);
            }
            Project PROJECTprojectid = userStory.getPROJECTprojectid();
            if (PROJECTprojectid != null) {
                PROJECTprojectid = em.getReference(PROJECTprojectid.getClass(), PROJECTprojectid.getProjectId());
                userStory.setPROJECTprojectid(PROJECTprojectid);
            }
            Collection<Task> attachedTaskCollection = new ArrayList<Task>();
            for (Task taskCollectionTaskToAttach : userStory.getTaskCollection()) {
                taskCollectionTaskToAttach = em.getReference(taskCollectionTaskToAttach.getClass(), taskCollectionTaskToAttach.getTaskPK());
                attachedTaskCollection.add(taskCollectionTaskToAttach);
            }
            userStory.setTaskCollection(attachedTaskCollection);
            Collection<AcceptanceTest> attachedAcceptanceTestCollection = new ArrayList<AcceptanceTest>();
            for (AcceptanceTest acceptanceTestCollectionAcceptanceTestToAttach : userStory.getAcceptanceTestCollection()) {
                acceptanceTestCollectionAcceptanceTestToAttach = em.getReference(acceptanceTestCollectionAcceptanceTestToAttach.getClass(), acceptanceTestCollectionAcceptanceTestToAttach.getAcceptanceTestId());
                attachedAcceptanceTestCollection.add(acceptanceTestCollectionAcceptanceTestToAttach);
            }
            userStory.setAcceptanceTestCollection(attachedAcceptanceTestCollection);
            em.persist(userStory);
            if (sprint != null) {
                sprint.getUserStoryCollection().add(userStory);
                sprint = em.merge(sprint);
            }
            if (PRIORITYpriorityid != null) {
                PRIORITYpriorityid.getUserStoryCollection().add(userStory);
                PRIORITYpriorityid = em.merge(PRIORITYpriorityid);
            }
            if (PROJECTprojectid != null) {
                PROJECTprojectid.getUserStoryCollection().add(userStory);
                PROJECTprojectid = em.merge(PROJECTprojectid);
            }
            for (Task taskCollectionTask : userStory.getTaskCollection()) {
                UserStory oldUserStoryOfTaskCollectionTask = taskCollectionTask.getUserStory();
                taskCollectionTask.setUserStory(userStory);
                taskCollectionTask = em.merge(taskCollectionTask);
                if (oldUserStoryOfTaskCollectionTask != null) {
                    oldUserStoryOfTaskCollectionTask.getTaskCollection().remove(taskCollectionTask);
                    oldUserStoryOfTaskCollectionTask = em.merge(oldUserStoryOfTaskCollectionTask);
                }
            }
            for (AcceptanceTest acceptanceTestCollectionAcceptanceTest : userStory.getAcceptanceTestCollection()) {
                UserStory oldUSERSTORYstoryidOfAcceptanceTestCollectionAcceptanceTest = acceptanceTestCollectionAcceptanceTest.getUSERSTORYstoryid();
                acceptanceTestCollectionAcceptanceTest.setUSERSTORYstoryid(userStory);
                acceptanceTestCollectionAcceptanceTest = em.merge(acceptanceTestCollectionAcceptanceTest);
                if (oldUSERSTORYstoryidOfAcceptanceTestCollectionAcceptanceTest != null) {
                    oldUSERSTORYstoryidOfAcceptanceTestCollectionAcceptanceTest.getAcceptanceTestCollection().remove(acceptanceTestCollectionAcceptanceTest);
                    oldUSERSTORYstoryidOfAcceptanceTestCollectionAcceptanceTest = em.merge(oldUSERSTORYstoryidOfAcceptanceTestCollectionAcceptanceTest);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserStory userStory) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserStory persistentUserStory = em.find(UserStory.class, userStory.getStoryId());
            Sprint sprintOld = persistentUserStory.getSprint();
            Sprint sprintNew = userStory.getSprint();
            Priority PRIORITYpriorityidOld = persistentUserStory.getPRIORITYpriorityid();
            Priority PRIORITYpriorityidNew = userStory.getPRIORITYpriorityid();
            Project PROJECTprojectidOld = persistentUserStory.getPROJECTprojectid();
            Project PROJECTprojectidNew = userStory.getPROJECTprojectid();
            Collection<Task> taskCollectionOld = persistentUserStory.getTaskCollection();
            Collection<Task> taskCollectionNew = userStory.getTaskCollection();
            Collection<AcceptanceTest> acceptanceTestCollectionOld = persistentUserStory.getAcceptanceTestCollection();
            Collection<AcceptanceTest> acceptanceTestCollectionNew = userStory.getAcceptanceTestCollection();
            List<String> illegalOrphanMessages = null;
            for (Task taskCollectionOldTask : taskCollectionOld) {
                if (!taskCollectionNew.contains(taskCollectionOldTask)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Task " + taskCollectionOldTask + " since its userStory field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sprintNew != null) {
                sprintNew = em.getReference(sprintNew.getClass(), sprintNew.getSprintPK());
                userStory.setSprint(sprintNew);
            }
            if (PRIORITYpriorityidNew != null) {
                PRIORITYpriorityidNew = em.getReference(PRIORITYpriorityidNew.getClass(), PRIORITYpriorityidNew.getPriorityId());
                userStory.setPRIORITYpriorityid(PRIORITYpriorityidNew);
            }
            if (PROJECTprojectidNew != null) {
                PROJECTprojectidNew = em.getReference(PROJECTprojectidNew.getClass(), PROJECTprojectidNew.getProjectId());
                userStory.setPROJECTprojectid(PROJECTprojectidNew);
            }
            Collection<Task> attachedTaskCollectionNew = new ArrayList<Task>();
            for (Task taskCollectionNewTaskToAttach : taskCollectionNew) {
                taskCollectionNewTaskToAttach = em.getReference(taskCollectionNewTaskToAttach.getClass(), taskCollectionNewTaskToAttach.getTaskPK());
                attachedTaskCollectionNew.add(taskCollectionNewTaskToAttach);
            }
            taskCollectionNew = attachedTaskCollectionNew;
            userStory.setTaskCollection(taskCollectionNew);
            Collection<AcceptanceTest> attachedAcceptanceTestCollectionNew = new ArrayList<AcceptanceTest>();
            for (AcceptanceTest acceptanceTestCollectionNewAcceptanceTestToAttach : acceptanceTestCollectionNew) {
                acceptanceTestCollectionNewAcceptanceTestToAttach = em.getReference(acceptanceTestCollectionNewAcceptanceTestToAttach.getClass(), acceptanceTestCollectionNewAcceptanceTestToAttach.getAcceptanceTestId());
                attachedAcceptanceTestCollectionNew.add(acceptanceTestCollectionNewAcceptanceTestToAttach);
            }
            acceptanceTestCollectionNew = attachedAcceptanceTestCollectionNew;
            userStory.setAcceptanceTestCollection(acceptanceTestCollectionNew);
            userStory = em.merge(userStory);
            if (sprintOld != null && !sprintOld.equals(sprintNew)) {
                sprintOld.getUserStoryCollection().remove(userStory);
                sprintOld = em.merge(sprintOld);
            }
            if (sprintNew != null && !sprintNew.equals(sprintOld)) {
                sprintNew.getUserStoryCollection().add(userStory);
                sprintNew = em.merge(sprintNew);
            }
            if (PRIORITYpriorityidOld != null && !PRIORITYpriorityidOld.equals(PRIORITYpriorityidNew)) {
                PRIORITYpriorityidOld.getUserStoryCollection().remove(userStory);
                PRIORITYpriorityidOld = em.merge(PRIORITYpriorityidOld);
            }
            if (PRIORITYpriorityidNew != null && !PRIORITYpriorityidNew.equals(PRIORITYpriorityidOld)) {
                PRIORITYpriorityidNew.getUserStoryCollection().add(userStory);
                PRIORITYpriorityidNew = em.merge(PRIORITYpriorityidNew);
            }
            if (PROJECTprojectidOld != null && !PROJECTprojectidOld.equals(PROJECTprojectidNew)) {
                PROJECTprojectidOld.getUserStoryCollection().remove(userStory);
                PROJECTprojectidOld = em.merge(PROJECTprojectidOld);
            }
            if (PROJECTprojectidNew != null && !PROJECTprojectidNew.equals(PROJECTprojectidOld)) {
                PROJECTprojectidNew.getUserStoryCollection().add(userStory);
                PROJECTprojectidNew = em.merge(PROJECTprojectidNew);
            }
            for (Task taskCollectionNewTask : taskCollectionNew) {
                if (!taskCollectionOld.contains(taskCollectionNewTask)) {
                    UserStory oldUserStoryOfTaskCollectionNewTask = taskCollectionNewTask.getUserStory();
                    taskCollectionNewTask.setUserStory(userStory);
                    taskCollectionNewTask = em.merge(taskCollectionNewTask);
                    if (oldUserStoryOfTaskCollectionNewTask != null && !oldUserStoryOfTaskCollectionNewTask.equals(userStory)) {
                        oldUserStoryOfTaskCollectionNewTask.getTaskCollection().remove(taskCollectionNewTask);
                        oldUserStoryOfTaskCollectionNewTask = em.merge(oldUserStoryOfTaskCollectionNewTask);
                    }
                }
            }
            for (AcceptanceTest acceptanceTestCollectionOldAcceptanceTest : acceptanceTestCollectionOld) {
                if (!acceptanceTestCollectionNew.contains(acceptanceTestCollectionOldAcceptanceTest)) {
                    acceptanceTestCollectionOldAcceptanceTest.setUSERSTORYstoryid(null);
                    acceptanceTestCollectionOldAcceptanceTest = em.merge(acceptanceTestCollectionOldAcceptanceTest);
                }
            }
            for (AcceptanceTest acceptanceTestCollectionNewAcceptanceTest : acceptanceTestCollectionNew) {
                if (!acceptanceTestCollectionOld.contains(acceptanceTestCollectionNewAcceptanceTest)) {
                    UserStory oldUSERSTORYstoryidOfAcceptanceTestCollectionNewAcceptanceTest = acceptanceTestCollectionNewAcceptanceTest.getUSERSTORYstoryid();
                    acceptanceTestCollectionNewAcceptanceTest.setUSERSTORYstoryid(userStory);
                    acceptanceTestCollectionNewAcceptanceTest = em.merge(acceptanceTestCollectionNewAcceptanceTest);
                    if (oldUSERSTORYstoryidOfAcceptanceTestCollectionNewAcceptanceTest != null && !oldUSERSTORYstoryidOfAcceptanceTestCollectionNewAcceptanceTest.equals(userStory)) {
                        oldUSERSTORYstoryidOfAcceptanceTestCollectionNewAcceptanceTest.getAcceptanceTestCollection().remove(acceptanceTestCollectionNewAcceptanceTest);
                        oldUSERSTORYstoryidOfAcceptanceTestCollectionNewAcceptanceTest = em.merge(oldUSERSTORYstoryidOfAcceptanceTestCollectionNewAcceptanceTest);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userStory.getStoryId();
                if (findUserStory(id) == null) {
                    throw new NonexistentEntityException("The userStory with id " + id + " no longer exists.");
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
            UserStory userStory;
            try {
                userStory = em.getReference(UserStory.class, id);
                userStory.getStoryId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userStory with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Task> taskCollectionOrphanCheck = userStory.getTaskCollection();
            for (Task taskCollectionOrphanCheckTask : taskCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserStory (" + userStory + ") cannot be destroyed since the Task " + taskCollectionOrphanCheckTask + " in its taskCollection field has a non-nullable userStory field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sprint sprint = userStory.getSprint();
            if (sprint != null) {
                sprint.getUserStoryCollection().remove(userStory);
                sprint = em.merge(sprint);
            }
            Priority PRIORITYpriorityid = userStory.getPRIORITYpriorityid();
            if (PRIORITYpriorityid != null) {
                PRIORITYpriorityid.getUserStoryCollection().remove(userStory);
                PRIORITYpriorityid = em.merge(PRIORITYpriorityid);
            }
            Project PROJECTprojectid = userStory.getPROJECTprojectid();
            if (PROJECTprojectid != null) {
                PROJECTprojectid.getUserStoryCollection().remove(userStory);
                PROJECTprojectid = em.merge(PROJECTprojectid);
            }
            Collection<AcceptanceTest> acceptanceTestCollection = userStory.getAcceptanceTestCollection();
            for (AcceptanceTest acceptanceTestCollectionAcceptanceTest : acceptanceTestCollection) {
                acceptanceTestCollectionAcceptanceTest.setUSERSTORYstoryid(null);
                acceptanceTestCollectionAcceptanceTest = em.merge(acceptanceTestCollectionAcceptanceTest);
            }
            em.remove(userStory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserStory> findUserStoryEntities() {
        return findUserStoryEntities(true, -1, -1);
    }

    public List<UserStory> findUserStoryEntities(int maxResults, int firstResult) {
        return findUserStoryEntities(false, maxResults, firstResult);
    }

    private List<UserStory> findUserStoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserStory.class));
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

    public UserStory findUserStory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserStory.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserStoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserStory> rt = cq.from(UserStory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
