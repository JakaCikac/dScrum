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
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getDailyScrumEntries() == null) {
            user.setDailyScrumEntries(new ArrayList<DailyScrumEntry>());
        }
        if (user.getDiscussions() == null) {
            user.setDiscussions(new ArrayList<Discussion>());
        }
        if (user.getTasks() == null) {
            user.setTasks(new ArrayList<Task>());
        }
        if (user.getTeams() == null) {
            user.setTeams(new ArrayList<Team>());
        }
        if (user.getWorkloads() == null) {
            user.setWorkloads(new ArrayList<Workload>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DailyScrumEntry> attachedDailyScrumEntries = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntriesDailyScrumEntryToAttach : user.getDailyScrumEntries()) {
                dailyScrumEntriesDailyScrumEntryToAttach = em.getReference(dailyScrumEntriesDailyScrumEntryToAttach.getClass(), dailyScrumEntriesDailyScrumEntryToAttach.getId());
                attachedDailyScrumEntries.add(dailyScrumEntriesDailyScrumEntryToAttach);
            }
            user.setDailyScrumEntries(attachedDailyScrumEntries);
            List<Discussion> attachedDiscussions = new ArrayList<Discussion>();
            for (Discussion discussionsDiscussionToAttach : user.getDiscussions()) {
                discussionsDiscussionToAttach = em.getReference(discussionsDiscussionToAttach.getClass(), discussionsDiscussionToAttach.getId());
                attachedDiscussions.add(discussionsDiscussionToAttach);
            }
            user.setDiscussions(attachedDiscussions);
            List<Task> attachedTasks = new ArrayList<Task>();
            for (Task tasksTaskToAttach : user.getTasks()) {
                tasksTaskToAttach = em.getReference(tasksTaskToAttach.getClass(), tasksTaskToAttach.getId());
                attachedTasks.add(tasksTaskToAttach);
            }
            user.setTasks(attachedTasks);
            List<Team> attachedTeams = new ArrayList<Team>();
            for (Team teamsTeamToAttach : user.getTeams()) {
                teamsTeamToAttach = em.getReference(teamsTeamToAttach.getClass(), teamsTeamToAttach.getTeamId());
                attachedTeams.add(teamsTeamToAttach);
            }
            user.setTeams(attachedTeams);
            List<Workload> attachedWorkloads = new ArrayList<Workload>();
            for (Workload workloadsWorkloadToAttach : user.getWorkloads()) {
                workloadsWorkloadToAttach = em.getReference(workloadsWorkloadToAttach.getClass(), workloadsWorkloadToAttach.getId());
                attachedWorkloads.add(workloadsWorkloadToAttach);
            }
            user.setWorkloads(attachedWorkloads);
            em.persist(user);
            for (DailyScrumEntry dailyScrumEntriesDailyScrumEntry : user.getDailyScrumEntries()) {
                User oldUserOfDailyScrumEntriesDailyScrumEntry = dailyScrumEntriesDailyScrumEntry.getUser();
                dailyScrumEntriesDailyScrumEntry.setUser(user);
                dailyScrumEntriesDailyScrumEntry = em.merge(dailyScrumEntriesDailyScrumEntry);
                if (oldUserOfDailyScrumEntriesDailyScrumEntry != null) {
                    oldUserOfDailyScrumEntriesDailyScrumEntry.getDailyScrumEntries().remove(dailyScrumEntriesDailyScrumEntry);
                    oldUserOfDailyScrumEntriesDailyScrumEntry = em.merge(oldUserOfDailyScrumEntriesDailyScrumEntry);
                }
            }
            for (Discussion discussionsDiscussion : user.getDiscussions()) {
                User oldUserOfDiscussionsDiscussion = discussionsDiscussion.getUser();
                discussionsDiscussion.setUser(user);
                discussionsDiscussion = em.merge(discussionsDiscussion);
                if (oldUserOfDiscussionsDiscussion != null) {
                    oldUserOfDiscussionsDiscussion.getDiscussions().remove(discussionsDiscussion);
                    oldUserOfDiscussionsDiscussion = em.merge(oldUserOfDiscussionsDiscussion);
                }
            }
            for (Task tasksTask : user.getTasks()) {
                User oldUserOfTasksTask = tasksTask.getUser();
                tasksTask.setUser(user);
                tasksTask = em.merge(tasksTask);
                if (oldUserOfTasksTask != null) {
                    oldUserOfTasksTask.getTasks().remove(tasksTask);
                    oldUserOfTasksTask = em.merge(oldUserOfTasksTask);
                }
            }
            for (Team teamsTeam : user.getTeams()) {
                teamsTeam.getUsers().add(user);
                teamsTeam = em.merge(teamsTeam);
            }
            for (Workload workloadsWorkload : user.getWorkloads()) {
                User oldUserOfWorkloadsWorkload = workloadsWorkload.getUser();
                workloadsWorkload.setUser(user);
                workloadsWorkload = em.merge(workloadsWorkload);
                if (oldUserOfWorkloadsWorkload != null) {
                    oldUserOfWorkloadsWorkload.getWorkloads().remove(workloadsWorkload);
                    oldUserOfWorkloadsWorkload = em.merge(oldUserOfWorkloadsWorkload);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            List<DailyScrumEntry> dailyScrumEntriesOld = persistentUser.getDailyScrumEntries();
            List<DailyScrumEntry> dailyScrumEntriesNew = user.getDailyScrumEntries();
            List<Discussion> discussionsOld = persistentUser.getDiscussions();
            List<Discussion> discussionsNew = user.getDiscussions();
            List<Task> tasksOld = persistentUser.getTasks();
            List<Task> tasksNew = user.getTasks();
            List<Team> teamsOld = persistentUser.getTeams();
            List<Team> teamsNew = user.getTeams();
            List<Workload> workloadsOld = persistentUser.getWorkloads();
            List<Workload> workloadsNew = user.getWorkloads();
            List<DailyScrumEntry> attachedDailyScrumEntriesNew = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntriesNewDailyScrumEntryToAttach : dailyScrumEntriesNew) {
                dailyScrumEntriesNewDailyScrumEntryToAttach = em.getReference(dailyScrumEntriesNewDailyScrumEntryToAttach.getClass(), dailyScrumEntriesNewDailyScrumEntryToAttach.getId());
                attachedDailyScrumEntriesNew.add(dailyScrumEntriesNewDailyScrumEntryToAttach);
            }
            dailyScrumEntriesNew = attachedDailyScrumEntriesNew;
            user.setDailyScrumEntries(dailyScrumEntriesNew);
            List<Discussion> attachedDiscussionsNew = new ArrayList<Discussion>();
            for (Discussion discussionsNewDiscussionToAttach : discussionsNew) {
                discussionsNewDiscussionToAttach = em.getReference(discussionsNewDiscussionToAttach.getClass(), discussionsNewDiscussionToAttach.getId());
                attachedDiscussionsNew.add(discussionsNewDiscussionToAttach);
            }
            discussionsNew = attachedDiscussionsNew;
            user.setDiscussions(discussionsNew);
            List<Task> attachedTasksNew = new ArrayList<Task>();
            for (Task tasksNewTaskToAttach : tasksNew) {
                tasksNewTaskToAttach = em.getReference(tasksNewTaskToAttach.getClass(), tasksNewTaskToAttach.getId());
                attachedTasksNew.add(tasksNewTaskToAttach);
            }
            tasksNew = attachedTasksNew;
            user.setTasks(tasksNew);
            List<Team> attachedTeamsNew = new ArrayList<Team>();
            for (Team teamsNewTeamToAttach : teamsNew) {
                teamsNewTeamToAttach = em.getReference(teamsNewTeamToAttach.getClass(), teamsNewTeamToAttach.getTeamId());
                attachedTeamsNew.add(teamsNewTeamToAttach);
            }
            teamsNew = attachedTeamsNew;
            user.setTeams(teamsNew);
            List<Workload> attachedWorkloadsNew = new ArrayList<Workload>();
            for (Workload workloadsNewWorkloadToAttach : workloadsNew) {
                workloadsNewWorkloadToAttach = em.getReference(workloadsNewWorkloadToAttach.getClass(), workloadsNewWorkloadToAttach.getId());
                attachedWorkloadsNew.add(workloadsNewWorkloadToAttach);
            }
            workloadsNew = attachedWorkloadsNew;
            user.setWorkloads(workloadsNew);
            user = em.merge(user);
            for (DailyScrumEntry dailyScrumEntriesOldDailyScrumEntry : dailyScrumEntriesOld) {
                if (!dailyScrumEntriesNew.contains(dailyScrumEntriesOldDailyScrumEntry)) {
                    dailyScrumEntriesOldDailyScrumEntry.setUser(null);
                    dailyScrumEntriesOldDailyScrumEntry = em.merge(dailyScrumEntriesOldDailyScrumEntry);
                }
            }
            for (DailyScrumEntry dailyScrumEntriesNewDailyScrumEntry : dailyScrumEntriesNew) {
                if (!dailyScrumEntriesOld.contains(dailyScrumEntriesNewDailyScrumEntry)) {
                    User oldUserOfDailyScrumEntriesNewDailyScrumEntry = dailyScrumEntriesNewDailyScrumEntry.getUser();
                    dailyScrumEntriesNewDailyScrumEntry.setUser(user);
                    dailyScrumEntriesNewDailyScrumEntry = em.merge(dailyScrumEntriesNewDailyScrumEntry);
                    if (oldUserOfDailyScrumEntriesNewDailyScrumEntry != null && !oldUserOfDailyScrumEntriesNewDailyScrumEntry.equals(user)) {
                        oldUserOfDailyScrumEntriesNewDailyScrumEntry.getDailyScrumEntries().remove(dailyScrumEntriesNewDailyScrumEntry);
                        oldUserOfDailyScrumEntriesNewDailyScrumEntry = em.merge(oldUserOfDailyScrumEntriesNewDailyScrumEntry);
                    }
                }
            }
            for (Discussion discussionsOldDiscussion : discussionsOld) {
                if (!discussionsNew.contains(discussionsOldDiscussion)) {
                    discussionsOldDiscussion.setUser(null);
                    discussionsOldDiscussion = em.merge(discussionsOldDiscussion);
                }
            }
            for (Discussion discussionsNewDiscussion : discussionsNew) {
                if (!discussionsOld.contains(discussionsNewDiscussion)) {
                    User oldUserOfDiscussionsNewDiscussion = discussionsNewDiscussion.getUser();
                    discussionsNewDiscussion.setUser(user);
                    discussionsNewDiscussion = em.merge(discussionsNewDiscussion);
                    if (oldUserOfDiscussionsNewDiscussion != null && !oldUserOfDiscussionsNewDiscussion.equals(user)) {
                        oldUserOfDiscussionsNewDiscussion.getDiscussions().remove(discussionsNewDiscussion);
                        oldUserOfDiscussionsNewDiscussion = em.merge(oldUserOfDiscussionsNewDiscussion);
                    }
                }
            }
            for (Task tasksOldTask : tasksOld) {
                if (!tasksNew.contains(tasksOldTask)) {
                    tasksOldTask.setUser(null);
                    tasksOldTask = em.merge(tasksOldTask);
                }
            }
            for (Task tasksNewTask : tasksNew) {
                if (!tasksOld.contains(tasksNewTask)) {
                    User oldUserOfTasksNewTask = tasksNewTask.getUser();
                    tasksNewTask.setUser(user);
                    tasksNewTask = em.merge(tasksNewTask);
                    if (oldUserOfTasksNewTask != null && !oldUserOfTasksNewTask.equals(user)) {
                        oldUserOfTasksNewTask.getTasks().remove(tasksNewTask);
                        oldUserOfTasksNewTask = em.merge(oldUserOfTasksNewTask);
                    }
                }
            }
            for (Team teamsOldTeam : teamsOld) {
                if (!teamsNew.contains(teamsOldTeam)) {
                    teamsOldTeam.getUsers().remove(user);
                    teamsOldTeam = em.merge(teamsOldTeam);
                }
            }
            for (Team teamsNewTeam : teamsNew) {
                if (!teamsOld.contains(teamsNewTeam)) {
                    teamsNewTeam.getUsers().add(user);
                    teamsNewTeam = em.merge(teamsNewTeam);
                }
            }
            for (Workload workloadsOldWorkload : workloadsOld) {
                if (!workloadsNew.contains(workloadsOldWorkload)) {
                    workloadsOldWorkload.setUser(null);
                    workloadsOldWorkload = em.merge(workloadsOldWorkload);
                }
            }
            for (Workload workloadsNewWorkload : workloadsNew) {
                if (!workloadsOld.contains(workloadsNewWorkload)) {
                    User oldUserOfWorkloadsNewWorkload = workloadsNewWorkload.getUser();
                    workloadsNewWorkload.setUser(user);
                    workloadsNewWorkload = em.merge(workloadsNewWorkload);
                    if (oldUserOfWorkloadsNewWorkload != null && !oldUserOfWorkloadsNewWorkload.equals(user)) {
                        oldUserOfWorkloadsNewWorkload.getWorkloads().remove(workloadsNewWorkload);
                        oldUserOfWorkloadsNewWorkload = em.merge(oldUserOfWorkloadsNewWorkload);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<DailyScrumEntry> dailyScrumEntries = user.getDailyScrumEntries();
            for (DailyScrumEntry dailyScrumEntriesDailyScrumEntry : dailyScrumEntries) {
                dailyScrumEntriesDailyScrumEntry.setUser(null);
                dailyScrumEntriesDailyScrumEntry = em.merge(dailyScrumEntriesDailyScrumEntry);
            }
            List<Discussion> discussions = user.getDiscussions();
            for (Discussion discussionsDiscussion : discussions) {
                discussionsDiscussion.setUser(null);
                discussionsDiscussion = em.merge(discussionsDiscussion);
            }
            List<Task> tasks = user.getTasks();
            for (Task tasksTask : tasks) {
                tasksTask.setUser(null);
                tasksTask = em.merge(tasksTask);
            }
            List<Team> teams = user.getTeams();
            for (Team teamsTeam : teams) {
                teamsTeam.getUsers().remove(user);
                teamsTeam = em.merge(teamsTeam);
            }
            List<Workload> workloads = user.getWorkloads();
            for (Workload workloadsWorkload : workloads) {
                workloadsWorkload.setUser(null);
                workloadsWorkload = em.merge(workloadsWorkload);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
