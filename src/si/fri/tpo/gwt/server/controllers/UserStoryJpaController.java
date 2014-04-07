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
import si.fri.tpo.gwt.server.jpa.Sprint;
import si.fri.tpo.gwt.server.jpa.Priority;
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.Task;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.jpa.AcceptanceTest;
import si.fri.tpo.gwt.server.jpa.UserStory;

/**
 *
 * @author Administrator
 */
public class UserStoryJpaController implements Serializable {

    public UserStoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public UserStoryJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserStory userStory) {
        if (userStory.getTaskList() == null) {
            userStory.setTaskList(new ArrayList<Task>());
        }
        if (userStory.getAcceptanceTestList() == null) {
            userStory.setAcceptanceTestList(new ArrayList<AcceptanceTest>());
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
            Priority priorityPriorityId = userStory.getPriorityPriorityId();
            if (priorityPriorityId != null) {
                priorityPriorityId = em.getReference(priorityPriorityId.getClass(), priorityPriorityId.getPriorityId());
                userStory.setPriorityPriorityId(priorityPriorityId);
            }
            Project projectProjectId = userStory.getProjectProjectId();
            if (projectProjectId != null) {
                projectProjectId = em.getReference(projectProjectId.getClass(), projectProjectId.getProjectId());
                userStory.setProjectProjectId(projectProjectId);
            }
            List<Task> attachedTaskList = new ArrayList<Task>();
            for (Task taskListTaskToAttach : userStory.getTaskList()) {
                taskListTaskToAttach = em.getReference(taskListTaskToAttach.getClass(), taskListTaskToAttach.getTaskPK());
                attachedTaskList.add(taskListTaskToAttach);
            }
            userStory.setTaskList(attachedTaskList);
            List<AcceptanceTest> attachedAcceptanceTestList = new ArrayList<AcceptanceTest>();
            for (AcceptanceTest acceptanceTestListAcceptanceTestToAttach : userStory.getAcceptanceTestList()) {
                acceptanceTestListAcceptanceTestToAttach = em.getReference(acceptanceTestListAcceptanceTestToAttach.getClass(), acceptanceTestListAcceptanceTestToAttach.getAcceptanceTestId());
                attachedAcceptanceTestList.add(acceptanceTestListAcceptanceTestToAttach);
            }
            userStory.setAcceptanceTestList(attachedAcceptanceTestList);
            em.persist(userStory);
            if (sprint != null) {
                sprint.getUserStoryList().add(userStory);
                sprint = em.merge(sprint);
            }
            if (priorityPriorityId != null) {
                priorityPriorityId.getUserStoryList().add(userStory);
                priorityPriorityId = em.merge(priorityPriorityId);
            }
            if (projectProjectId != null) {
                projectProjectId.getUserStoryList().add(userStory);
                projectProjectId = em.merge(projectProjectId);
            }
            for (Task taskListTask : userStory.getTaskList()) {
                UserStory oldUserStoryOfTaskListTask = taskListTask.getUserStory();
                taskListTask.setUserStory(userStory);
                taskListTask = em.merge(taskListTask);
                if (oldUserStoryOfTaskListTask != null) {
                    oldUserStoryOfTaskListTask.getTaskList().remove(taskListTask);
                    oldUserStoryOfTaskListTask = em.merge(oldUserStoryOfTaskListTask);
                }
            }
            for (AcceptanceTest acceptanceTestListAcceptanceTest : userStory.getAcceptanceTestList()) {
                UserStory oldUserStoryStoryIdOfAcceptanceTestListAcceptanceTest = acceptanceTestListAcceptanceTest.getUserStoryStoryId();
                acceptanceTestListAcceptanceTest.setUserStoryStoryId(userStory);
                acceptanceTestListAcceptanceTest = em.merge(acceptanceTestListAcceptanceTest);
                if (oldUserStoryStoryIdOfAcceptanceTestListAcceptanceTest != null) {
                    oldUserStoryStoryIdOfAcceptanceTestListAcceptanceTest.getAcceptanceTestList().remove(acceptanceTestListAcceptanceTest);
                    oldUserStoryStoryIdOfAcceptanceTestListAcceptanceTest = em.merge(oldUserStoryStoryIdOfAcceptanceTestListAcceptanceTest);
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
            Priority priorityPriorityIdOld = persistentUserStory.getPriorityPriorityId();
            Priority priorityPriorityIdNew = userStory.getPriorityPriorityId();
            Project projectProjectIdOld = persistentUserStory.getProjectProjectId();
            Project projectProjectIdNew = userStory.getProjectProjectId();
            List<Task> taskListOld = persistentUserStory.getTaskList();
            List<Task> taskListNew = userStory.getTaskList();
            List<AcceptanceTest> acceptanceTestListOld = persistentUserStory.getAcceptanceTestList();
            List<AcceptanceTest> acceptanceTestListNew = userStory.getAcceptanceTestList();
            List<String> illegalOrphanMessages = null;
            for (Task taskListOldTask : taskListOld) {
                if (!taskListNew.contains(taskListOldTask)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Task " + taskListOldTask + " since its userStory field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sprintNew != null) {
                sprintNew = em.getReference(sprintNew.getClass(), sprintNew.getSprintPK());
                userStory.setSprint(sprintNew);
            }
            if (priorityPriorityIdNew != null) {
                priorityPriorityIdNew = em.getReference(priorityPriorityIdNew.getClass(), priorityPriorityIdNew.getPriorityId());
                userStory.setPriorityPriorityId(priorityPriorityIdNew);
            }
            if (projectProjectIdNew != null) {
                projectProjectIdNew = em.getReference(projectProjectIdNew.getClass(), projectProjectIdNew.getProjectId());
                userStory.setProjectProjectId(projectProjectIdNew);
            }
            List<Task> attachedTaskListNew = new ArrayList<Task>();
            for (Task taskListNewTaskToAttach : taskListNew) {
                taskListNewTaskToAttach = em.getReference(taskListNewTaskToAttach.getClass(), taskListNewTaskToAttach.getTaskPK());
                attachedTaskListNew.add(taskListNewTaskToAttach);
            }
            taskListNew = attachedTaskListNew;
            userStory.setTaskList(taskListNew);
            List<AcceptanceTest> attachedAcceptanceTestListNew = new ArrayList<AcceptanceTest>();
            for (AcceptanceTest acceptanceTestListNewAcceptanceTestToAttach : acceptanceTestListNew) {
                acceptanceTestListNewAcceptanceTestToAttach = em.getReference(acceptanceTestListNewAcceptanceTestToAttach.getClass(), acceptanceTestListNewAcceptanceTestToAttach.getAcceptanceTestId());
                attachedAcceptanceTestListNew.add(acceptanceTestListNewAcceptanceTestToAttach);
            }
            acceptanceTestListNew = attachedAcceptanceTestListNew;
            userStory.setAcceptanceTestList(acceptanceTestListNew);
            userStory = em.merge(userStory);
            if (sprintOld != null && !sprintOld.equals(sprintNew)) {
                sprintOld.getUserStoryList().remove(userStory);
                sprintOld = em.merge(sprintOld);
            }
            if (sprintNew != null && !sprintNew.equals(sprintOld)) {
                sprintNew.getUserStoryList().add(userStory);
                sprintNew = em.merge(sprintNew);
            }
            if (priorityPriorityIdOld != null && !priorityPriorityIdOld.equals(priorityPriorityIdNew)) {
                priorityPriorityIdOld.getUserStoryList().remove(userStory);
                priorityPriorityIdOld = em.merge(priorityPriorityIdOld);
            }
            if (priorityPriorityIdNew != null && !priorityPriorityIdNew.equals(priorityPriorityIdOld)) {
                priorityPriorityIdNew.getUserStoryList().add(userStory);
                priorityPriorityIdNew = em.merge(priorityPriorityIdNew);
            }
            if (projectProjectIdOld != null && !projectProjectIdOld.equals(projectProjectIdNew)) {
                projectProjectIdOld.getUserStoryList().remove(userStory);
                projectProjectIdOld = em.merge(projectProjectIdOld);
            }
            if (projectProjectIdNew != null && !projectProjectIdNew.equals(projectProjectIdOld)) {
                projectProjectIdNew.getUserStoryList().add(userStory);
                projectProjectIdNew = em.merge(projectProjectIdNew);
            }
            for (Task taskListNewTask : taskListNew) {
                if (!taskListOld.contains(taskListNewTask)) {
                    UserStory oldUserStoryOfTaskListNewTask = taskListNewTask.getUserStory();
                    taskListNewTask.setUserStory(userStory);
                    taskListNewTask = em.merge(taskListNewTask);
                    if (oldUserStoryOfTaskListNewTask != null && !oldUserStoryOfTaskListNewTask.equals(userStory)) {
                        oldUserStoryOfTaskListNewTask.getTaskList().remove(taskListNewTask);
                        oldUserStoryOfTaskListNewTask = em.merge(oldUserStoryOfTaskListNewTask);
                    }
                }
            }
            for (AcceptanceTest acceptanceTestListOldAcceptanceTest : acceptanceTestListOld) {
                if (!acceptanceTestListNew.contains(acceptanceTestListOldAcceptanceTest)) {
                    acceptanceTestListOldAcceptanceTest.setUserStoryStoryId(null);
                    acceptanceTestListOldAcceptanceTest = em.merge(acceptanceTestListOldAcceptanceTest);
                }
            }
            for (AcceptanceTest acceptanceTestListNewAcceptanceTest : acceptanceTestListNew) {
                if (!acceptanceTestListOld.contains(acceptanceTestListNewAcceptanceTest)) {
                    UserStory oldUserStoryStoryIdOfAcceptanceTestListNewAcceptanceTest = acceptanceTestListNewAcceptanceTest.getUserStoryStoryId();
                    acceptanceTestListNewAcceptanceTest.setUserStoryStoryId(userStory);
                    acceptanceTestListNewAcceptanceTest = em.merge(acceptanceTestListNewAcceptanceTest);
                    if (oldUserStoryStoryIdOfAcceptanceTestListNewAcceptanceTest != null && !oldUserStoryStoryIdOfAcceptanceTestListNewAcceptanceTest.equals(userStory)) {
                        oldUserStoryStoryIdOfAcceptanceTestListNewAcceptanceTest.getAcceptanceTestList().remove(acceptanceTestListNewAcceptanceTest);
                        oldUserStoryStoryIdOfAcceptanceTestListNewAcceptanceTest = em.merge(oldUserStoryStoryIdOfAcceptanceTestListNewAcceptanceTest);
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
            List<Task> taskListOrphanCheck = userStory.getTaskList();
            for (Task taskListOrphanCheckTask : taskListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserStory (" + userStory + ") cannot be destroyed since the Task " + taskListOrphanCheckTask + " in its taskList field has a non-nullable userStory field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sprint sprint = userStory.getSprint();
            if (sprint != null) {
                sprint.getUserStoryList().remove(userStory);
                sprint = em.merge(sprint);
            }
            Priority priorityPriorityId = userStory.getPriorityPriorityId();
            if (priorityPriorityId != null) {
                priorityPriorityId.getUserStoryList().remove(userStory);
                priorityPriorityId = em.merge(priorityPriorityId);
            }
            Project projectProjectId = userStory.getProjectProjectId();
            if (projectProjectId != null) {
                projectProjectId.getUserStoryList().remove(userStory);
                projectProjectId = em.merge(projectProjectId);
            }
            List<AcceptanceTest> acceptanceTestList = userStory.getAcceptanceTestList();
            for (AcceptanceTest acceptanceTestListAcceptanceTest : acceptanceTestList) {
                acceptanceTestListAcceptanceTest.setUserStoryStoryId(null);
                acceptanceTestListAcceptanceTest = em.merge(acceptanceTestListAcceptanceTest);
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
