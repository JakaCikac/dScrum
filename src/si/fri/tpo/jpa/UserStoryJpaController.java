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
public class UserStoryJpaController implements Serializable {

    public UserStoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserStory userStory) {
        if (userStory.getAcceptanceTests() == null) {
            userStory.setAcceptanceTests(new ArrayList<AcceptanceTest>());
        }
        if (userStory.getTasks() == null) {
            userStory.setTasks(new ArrayList<Task>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project = userStory.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                userStory.setProject(project);
            }
            Priority priority = userStory.getPriority();
            if (priority != null) {
                priority = em.getReference(priority.getClass(), priority.getPriorityId());
                userStory.setPriority(priority);
            }
            Sprint sprint = userStory.getSprint();
            if (sprint != null) {
                sprint = em.getReference(sprint.getClass(), sprint.getId());
                userStory.setSprint(sprint);
            }
            List<AcceptanceTest> attachedAcceptanceTests = new ArrayList<AcceptanceTest>();
            for (AcceptanceTest acceptanceTestsAcceptanceTestToAttach : userStory.getAcceptanceTests()) {
                acceptanceTestsAcceptanceTestToAttach = em.getReference(acceptanceTestsAcceptanceTestToAttach.getClass(), acceptanceTestsAcceptanceTestToAttach.getAcceptanceTestId());
                attachedAcceptanceTests.add(acceptanceTestsAcceptanceTestToAttach);
            }
            userStory.setAcceptanceTests(attachedAcceptanceTests);
            List<Task> attachedTasks = new ArrayList<Task>();
            for (Task tasksTaskToAttach : userStory.getTasks()) {
                tasksTaskToAttach = em.getReference(tasksTaskToAttach.getClass(), tasksTaskToAttach.getId());
                attachedTasks.add(tasksTaskToAttach);
            }
            userStory.setTasks(attachedTasks);
            em.persist(userStory);
            if (project != null) {
                project.getUserStories().add(userStory);
                project = em.merge(project);
            }
            if (priority != null) {
                priority.getUserStories().add(userStory);
                priority = em.merge(priority);
            }
            if (sprint != null) {
                sprint.getUserStories().add(userStory);
                sprint = em.merge(sprint);
            }
            for (AcceptanceTest acceptanceTestsAcceptanceTest : userStory.getAcceptanceTests()) {
                UserStory oldUserStoryOfAcceptanceTestsAcceptanceTest = acceptanceTestsAcceptanceTest.getUserStory();
                acceptanceTestsAcceptanceTest.setUserStory(userStory);
                acceptanceTestsAcceptanceTest = em.merge(acceptanceTestsAcceptanceTest);
                if (oldUserStoryOfAcceptanceTestsAcceptanceTest != null) {
                    oldUserStoryOfAcceptanceTestsAcceptanceTest.getAcceptanceTests().remove(acceptanceTestsAcceptanceTest);
                    oldUserStoryOfAcceptanceTestsAcceptanceTest = em.merge(oldUserStoryOfAcceptanceTestsAcceptanceTest);
                }
            }
            for (Task tasksTask : userStory.getTasks()) {
                UserStory oldUserStoryOfTasksTask = tasksTask.getUserStory();
                tasksTask.setUserStory(userStory);
                tasksTask = em.merge(tasksTask);
                if (oldUserStoryOfTasksTask != null) {
                    oldUserStoryOfTasksTask.getTasks().remove(tasksTask);
                    oldUserStoryOfTasksTask = em.merge(oldUserStoryOfTasksTask);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserStory userStory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserStory persistentUserStory = em.find(UserStory.class, userStory.getStoryId());
            Project projectOld = persistentUserStory.getProject();
            Project projectNew = userStory.getProject();
            Priority priorityOld = persistentUserStory.getPriority();
            Priority priorityNew = userStory.getPriority();
            Sprint sprintOld = persistentUserStory.getSprint();
            Sprint sprintNew = userStory.getSprint();
            List<AcceptanceTest> acceptanceTestsOld = persistentUserStory.getAcceptanceTests();
            List<AcceptanceTest> acceptanceTestsNew = userStory.getAcceptanceTests();
            List<Task> tasksOld = persistentUserStory.getTasks();
            List<Task> tasksNew = userStory.getTasks();
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                userStory.setProject(projectNew);
            }
            if (priorityNew != null) {
                priorityNew = em.getReference(priorityNew.getClass(), priorityNew.getPriorityId());
                userStory.setPriority(priorityNew);
            }
            if (sprintNew != null) {
                sprintNew = em.getReference(sprintNew.getClass(), sprintNew.getId());
                userStory.setSprint(sprintNew);
            }
            List<AcceptanceTest> attachedAcceptanceTestsNew = new ArrayList<AcceptanceTest>();
            for (AcceptanceTest acceptanceTestsNewAcceptanceTestToAttach : acceptanceTestsNew) {
                acceptanceTestsNewAcceptanceTestToAttach = em.getReference(acceptanceTestsNewAcceptanceTestToAttach.getClass(), acceptanceTestsNewAcceptanceTestToAttach.getAcceptanceTestId());
                attachedAcceptanceTestsNew.add(acceptanceTestsNewAcceptanceTestToAttach);
            }
            acceptanceTestsNew = attachedAcceptanceTestsNew;
            userStory.setAcceptanceTests(acceptanceTestsNew);
            List<Task> attachedTasksNew = new ArrayList<Task>();
            for (Task tasksNewTaskToAttach : tasksNew) {
                tasksNewTaskToAttach = em.getReference(tasksNewTaskToAttach.getClass(), tasksNewTaskToAttach.getId());
                attachedTasksNew.add(tasksNewTaskToAttach);
            }
            tasksNew = attachedTasksNew;
            userStory.setTasks(tasksNew);
            userStory = em.merge(userStory);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getUserStories().remove(userStory);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getUserStories().add(userStory);
                projectNew = em.merge(projectNew);
            }
            if (priorityOld != null && !priorityOld.equals(priorityNew)) {
                priorityOld.getUserStories().remove(userStory);
                priorityOld = em.merge(priorityOld);
            }
            if (priorityNew != null && !priorityNew.equals(priorityOld)) {
                priorityNew.getUserStories().add(userStory);
                priorityNew = em.merge(priorityNew);
            }
            if (sprintOld != null && !sprintOld.equals(sprintNew)) {
                sprintOld.getUserStories().remove(userStory);
                sprintOld = em.merge(sprintOld);
            }
            if (sprintNew != null && !sprintNew.equals(sprintOld)) {
                sprintNew.getUserStories().add(userStory);
                sprintNew = em.merge(sprintNew);
            }
            for (AcceptanceTest acceptanceTestsOldAcceptanceTest : acceptanceTestsOld) {
                if (!acceptanceTestsNew.contains(acceptanceTestsOldAcceptanceTest)) {
                    acceptanceTestsOldAcceptanceTest.setUserStory(null);
                    acceptanceTestsOldAcceptanceTest = em.merge(acceptanceTestsOldAcceptanceTest);
                }
            }
            for (AcceptanceTest acceptanceTestsNewAcceptanceTest : acceptanceTestsNew) {
                if (!acceptanceTestsOld.contains(acceptanceTestsNewAcceptanceTest)) {
                    UserStory oldUserStoryOfAcceptanceTestsNewAcceptanceTest = acceptanceTestsNewAcceptanceTest.getUserStory();
                    acceptanceTestsNewAcceptanceTest.setUserStory(userStory);
                    acceptanceTestsNewAcceptanceTest = em.merge(acceptanceTestsNewAcceptanceTest);
                    if (oldUserStoryOfAcceptanceTestsNewAcceptanceTest != null && !oldUserStoryOfAcceptanceTestsNewAcceptanceTest.equals(userStory)) {
                        oldUserStoryOfAcceptanceTestsNewAcceptanceTest.getAcceptanceTests().remove(acceptanceTestsNewAcceptanceTest);
                        oldUserStoryOfAcceptanceTestsNewAcceptanceTest = em.merge(oldUserStoryOfAcceptanceTestsNewAcceptanceTest);
                    }
                }
            }
            for (Task tasksOldTask : tasksOld) {
                if (!tasksNew.contains(tasksOldTask)) {
                    tasksOldTask.setUserStory(null);
                    tasksOldTask = em.merge(tasksOldTask);
                }
            }
            for (Task tasksNewTask : tasksNew) {
                if (!tasksOld.contains(tasksNewTask)) {
                    UserStory oldUserStoryOfTasksNewTask = tasksNewTask.getUserStory();
                    tasksNewTask.setUserStory(userStory);
                    tasksNewTask = em.merge(tasksNewTask);
                    if (oldUserStoryOfTasksNewTask != null && !oldUserStoryOfTasksNewTask.equals(userStory)) {
                        oldUserStoryOfTasksNewTask.getTasks().remove(tasksNewTask);
                        oldUserStoryOfTasksNewTask = em.merge(oldUserStoryOfTasksNewTask);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = userStory.getStoryId();
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

    public void destroy(int id) throws NonexistentEntityException {
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
            Project project = userStory.getProject();
            if (project != null) {
                project.getUserStories().remove(userStory);
                project = em.merge(project);
            }
            Priority priority = userStory.getPriority();
            if (priority != null) {
                priority.getUserStories().remove(userStory);
                priority = em.merge(priority);
            }
            Sprint sprint = userStory.getSprint();
            if (sprint != null) {
                sprint.getUserStories().remove(userStory);
                sprint = em.merge(sprint);
            }
            List<AcceptanceTest> acceptanceTests = userStory.getAcceptanceTests();
            for (AcceptanceTest acceptanceTestsAcceptanceTest : acceptanceTests) {
                acceptanceTestsAcceptanceTest.setUserStory(null);
                acceptanceTestsAcceptanceTest = em.merge(acceptanceTestsAcceptanceTest);
            }
            List<Task> tasks = userStory.getTasks();
            for (Task tasksTask : tasks) {
                tasksTask.setUserStory(null);
                tasksTask = em.merge(tasksTask);
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

    public UserStory findUserStory(int id) {
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
