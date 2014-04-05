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
import si.fri.tpo.jpa.Team;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.jpa.Discussion;
import si.fri.tpo.jpa.Task;
import si.fri.tpo.jpa.Comment;
import si.fri.tpo.jpa.Workload;
import si.fri.tpo.jpa.DailyScrumEntry;
import si.fri.tpo.jpa.User;

/**
 *
 * @author Administrator
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
        if (user.getTeamCollection() == null) {
            user.setTeamCollection(new ArrayList<Team>());
        }
        if (user.getDiscussionCollection() == null) {
            user.setDiscussionCollection(new ArrayList<Discussion>());
        }
        if (user.getTaskCollection() == null) {
            user.setTaskCollection(new ArrayList<Task>());
        }
        if (user.getCommentCollection() == null) {
            user.setCommentCollection(new ArrayList<Comment>());
        }
        if (user.getWorkloadCollection() == null) {
            user.setWorkloadCollection(new ArrayList<Workload>());
        }
        if (user.getDailyScrumEntryCollection() == null) {
            user.setDailyScrumEntryCollection(new ArrayList<DailyScrumEntry>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Team> attachedTeamCollection = new ArrayList<Team>();
            for (Team teamCollectionTeamToAttach : user.getTeamCollection()) {
                teamCollectionTeamToAttach = em.getReference(teamCollectionTeamToAttach.getClass(), teamCollectionTeamToAttach.getTeamId());
                attachedTeamCollection.add(teamCollectionTeamToAttach);
            }
            user.setTeamCollection(attachedTeamCollection);
            Collection<Discussion> attachedDiscussionCollection = new ArrayList<Discussion>();
            for (Discussion discussionCollectionDiscussionToAttach : user.getDiscussionCollection()) {
                discussionCollectionDiscussionToAttach = em.getReference(discussionCollectionDiscussionToAttach.getClass(), discussionCollectionDiscussionToAttach.getDiscussionPK());
                attachedDiscussionCollection.add(discussionCollectionDiscussionToAttach);
            }
            user.setDiscussionCollection(attachedDiscussionCollection);
            Collection<Task> attachedTaskCollection = new ArrayList<Task>();
            for (Task taskCollectionTaskToAttach : user.getTaskCollection()) {
                taskCollectionTaskToAttach = em.getReference(taskCollectionTaskToAttach.getClass(), taskCollectionTaskToAttach.getTaskPK());
                attachedTaskCollection.add(taskCollectionTaskToAttach);
            }
            user.setTaskCollection(attachedTaskCollection);
            Collection<Comment> attachedCommentCollection = new ArrayList<Comment>();
            for (Comment commentCollectionCommentToAttach : user.getCommentCollection()) {
                commentCollectionCommentToAttach = em.getReference(commentCollectionCommentToAttach.getClass(), commentCollectionCommentToAttach.getCommentPK());
                attachedCommentCollection.add(commentCollectionCommentToAttach);
            }
            user.setCommentCollection(attachedCommentCollection);
            Collection<Workload> attachedWorkloadCollection = new ArrayList<Workload>();
            for (Workload workloadCollectionWorkloadToAttach : user.getWorkloadCollection()) {
                workloadCollectionWorkloadToAttach = em.getReference(workloadCollectionWorkloadToAttach.getClass(), workloadCollectionWorkloadToAttach.getWorkloadPK());
                attachedWorkloadCollection.add(workloadCollectionWorkloadToAttach);
            }
            user.setWorkloadCollection(attachedWorkloadCollection);
            Collection<DailyScrumEntry> attachedDailyScrumEntryCollection = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryCollectionDailyScrumEntryToAttach : user.getDailyScrumEntryCollection()) {
                dailyScrumEntryCollectionDailyScrumEntryToAttach = em.getReference(dailyScrumEntryCollectionDailyScrumEntryToAttach.getClass(), dailyScrumEntryCollectionDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryCollection.add(dailyScrumEntryCollectionDailyScrumEntryToAttach);
            }
            user.setDailyScrumEntryCollection(attachedDailyScrumEntryCollection);
            em.persist(user);
            for (Team teamCollectionTeam : user.getTeamCollection()) {
                teamCollectionTeam.getUserCollection().add(user);
                teamCollectionTeam = em.merge(teamCollectionTeam);
            }
            for (Discussion discussionCollectionDiscussion : user.getDiscussionCollection()) {
                User oldUserOfDiscussionCollectionDiscussion = discussionCollectionDiscussion.getUser();
                discussionCollectionDiscussion.setUser(user);
                discussionCollectionDiscussion = em.merge(discussionCollectionDiscussion);
                if (oldUserOfDiscussionCollectionDiscussion != null) {
                    oldUserOfDiscussionCollectionDiscussion.getDiscussionCollection().remove(discussionCollectionDiscussion);
                    oldUserOfDiscussionCollectionDiscussion = em.merge(oldUserOfDiscussionCollectionDiscussion);
                }
            }
            for (Task taskCollectionTask : user.getTaskCollection()) {
                User oldUSERuseridOfTaskCollectionTask = taskCollectionTask.getUSERuserid();
                taskCollectionTask.setUSERuserid(user);
                taskCollectionTask = em.merge(taskCollectionTask);
                if (oldUSERuseridOfTaskCollectionTask != null) {
                    oldUSERuseridOfTaskCollectionTask.getTaskCollection().remove(taskCollectionTask);
                    oldUSERuseridOfTaskCollectionTask = em.merge(oldUSERuseridOfTaskCollectionTask);
                }
            }
            for (Comment commentCollectionComment : user.getCommentCollection()) {
                User oldUserOfCommentCollectionComment = commentCollectionComment.getUser();
                commentCollectionComment.setUser(user);
                commentCollectionComment = em.merge(commentCollectionComment);
                if (oldUserOfCommentCollectionComment != null) {
                    oldUserOfCommentCollectionComment.getCommentCollection().remove(commentCollectionComment);
                    oldUserOfCommentCollectionComment = em.merge(oldUserOfCommentCollectionComment);
                }
            }
            for (Workload workloadCollectionWorkload : user.getWorkloadCollection()) {
                User oldUserOfWorkloadCollectionWorkload = workloadCollectionWorkload.getUser();
                workloadCollectionWorkload.setUser(user);
                workloadCollectionWorkload = em.merge(workloadCollectionWorkload);
                if (oldUserOfWorkloadCollectionWorkload != null) {
                    oldUserOfWorkloadCollectionWorkload.getWorkloadCollection().remove(workloadCollectionWorkload);
                    oldUserOfWorkloadCollectionWorkload = em.merge(oldUserOfWorkloadCollectionWorkload);
                }
            }
            for (DailyScrumEntry dailyScrumEntryCollectionDailyScrumEntry : user.getDailyScrumEntryCollection()) {
                User oldUserOfDailyScrumEntryCollectionDailyScrumEntry = dailyScrumEntryCollectionDailyScrumEntry.getUser();
                dailyScrumEntryCollectionDailyScrumEntry.setUser(user);
                dailyScrumEntryCollectionDailyScrumEntry = em.merge(dailyScrumEntryCollectionDailyScrumEntry);
                if (oldUserOfDailyScrumEntryCollectionDailyScrumEntry != null) {
                    oldUserOfDailyScrumEntryCollectionDailyScrumEntry.getDailyScrumEntryCollection().remove(dailyScrumEntryCollectionDailyScrumEntry);
                    oldUserOfDailyScrumEntryCollectionDailyScrumEntry = em.merge(oldUserOfDailyScrumEntryCollectionDailyScrumEntry);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            Collection<Team> teamCollectionOld = persistentUser.getTeamCollection();
            Collection<Team> teamCollectionNew = user.getTeamCollection();
            Collection<Discussion> discussionCollectionOld = persistentUser.getDiscussionCollection();
            Collection<Discussion> discussionCollectionNew = user.getDiscussionCollection();
            Collection<Task> taskCollectionOld = persistentUser.getTaskCollection();
            Collection<Task> taskCollectionNew = user.getTaskCollection();
            Collection<Comment> commentCollectionOld = persistentUser.getCommentCollection();
            Collection<Comment> commentCollectionNew = user.getCommentCollection();
            Collection<Workload> workloadCollectionOld = persistentUser.getWorkloadCollection();
            Collection<Workload> workloadCollectionNew = user.getWorkloadCollection();
            Collection<DailyScrumEntry> dailyScrumEntryCollectionOld = persistentUser.getDailyScrumEntryCollection();
            Collection<DailyScrumEntry> dailyScrumEntryCollectionNew = user.getDailyScrumEntryCollection();
            List<String> illegalOrphanMessages = null;
            for (Discussion discussionCollectionOldDiscussion : discussionCollectionOld) {
                if (!discussionCollectionNew.contains(discussionCollectionOldDiscussion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Discussion " + discussionCollectionOldDiscussion + " since its user field is not nullable.");
                }
            }
            for (Comment commentCollectionOldComment : commentCollectionOld) {
                if (!commentCollectionNew.contains(commentCollectionOldComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comment " + commentCollectionOldComment + " since its user field is not nullable.");
                }
            }
            for (Workload workloadCollectionOldWorkload : workloadCollectionOld) {
                if (!workloadCollectionNew.contains(workloadCollectionOldWorkload)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workload " + workloadCollectionOldWorkload + " since its user field is not nullable.");
                }
            }
            for (DailyScrumEntry dailyScrumEntryCollectionOldDailyScrumEntry : dailyScrumEntryCollectionOld) {
                if (!dailyScrumEntryCollectionNew.contains(dailyScrumEntryCollectionOldDailyScrumEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DailyScrumEntry " + dailyScrumEntryCollectionOldDailyScrumEntry + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Team> attachedTeamCollectionNew = new ArrayList<Team>();
            for (Team teamCollectionNewTeamToAttach : teamCollectionNew) {
                teamCollectionNewTeamToAttach = em.getReference(teamCollectionNewTeamToAttach.getClass(), teamCollectionNewTeamToAttach.getTeamId());
                attachedTeamCollectionNew.add(teamCollectionNewTeamToAttach);
            }
            teamCollectionNew = attachedTeamCollectionNew;
            user.setTeamCollection(teamCollectionNew);
            Collection<Discussion> attachedDiscussionCollectionNew = new ArrayList<Discussion>();
            for (Discussion discussionCollectionNewDiscussionToAttach : discussionCollectionNew) {
                discussionCollectionNewDiscussionToAttach = em.getReference(discussionCollectionNewDiscussionToAttach.getClass(), discussionCollectionNewDiscussionToAttach.getDiscussionPK());
                attachedDiscussionCollectionNew.add(discussionCollectionNewDiscussionToAttach);
            }
            discussionCollectionNew = attachedDiscussionCollectionNew;
            user.setDiscussionCollection(discussionCollectionNew);
            Collection<Task> attachedTaskCollectionNew = new ArrayList<Task>();
            for (Task taskCollectionNewTaskToAttach : taskCollectionNew) {
                taskCollectionNewTaskToAttach = em.getReference(taskCollectionNewTaskToAttach.getClass(), taskCollectionNewTaskToAttach.getTaskPK());
                attachedTaskCollectionNew.add(taskCollectionNewTaskToAttach);
            }
            taskCollectionNew = attachedTaskCollectionNew;
            user.setTaskCollection(taskCollectionNew);
            Collection<Comment> attachedCommentCollectionNew = new ArrayList<Comment>();
            for (Comment commentCollectionNewCommentToAttach : commentCollectionNew) {
                commentCollectionNewCommentToAttach = em.getReference(commentCollectionNewCommentToAttach.getClass(), commentCollectionNewCommentToAttach.getCommentPK());
                attachedCommentCollectionNew.add(commentCollectionNewCommentToAttach);
            }
            commentCollectionNew = attachedCommentCollectionNew;
            user.setCommentCollection(commentCollectionNew);
            Collection<Workload> attachedWorkloadCollectionNew = new ArrayList<Workload>();
            for (Workload workloadCollectionNewWorkloadToAttach : workloadCollectionNew) {
                workloadCollectionNewWorkloadToAttach = em.getReference(workloadCollectionNewWorkloadToAttach.getClass(), workloadCollectionNewWorkloadToAttach.getWorkloadPK());
                attachedWorkloadCollectionNew.add(workloadCollectionNewWorkloadToAttach);
            }
            workloadCollectionNew = attachedWorkloadCollectionNew;
            user.setWorkloadCollection(workloadCollectionNew);
            Collection<DailyScrumEntry> attachedDailyScrumEntryCollectionNew = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryCollectionNewDailyScrumEntryToAttach : dailyScrumEntryCollectionNew) {
                dailyScrumEntryCollectionNewDailyScrumEntryToAttach = em.getReference(dailyScrumEntryCollectionNewDailyScrumEntryToAttach.getClass(), dailyScrumEntryCollectionNewDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryCollectionNew.add(dailyScrumEntryCollectionNewDailyScrumEntryToAttach);
            }
            dailyScrumEntryCollectionNew = attachedDailyScrumEntryCollectionNew;
            user.setDailyScrumEntryCollection(dailyScrumEntryCollectionNew);
            user = em.merge(user);
            for (Team teamCollectionOldTeam : teamCollectionOld) {
                if (!teamCollectionNew.contains(teamCollectionOldTeam)) {
                    teamCollectionOldTeam.getUserCollection().remove(user);
                    teamCollectionOldTeam = em.merge(teamCollectionOldTeam);
                }
            }
            for (Team teamCollectionNewTeam : teamCollectionNew) {
                if (!teamCollectionOld.contains(teamCollectionNewTeam)) {
                    teamCollectionNewTeam.getUserCollection().add(user);
                    teamCollectionNewTeam = em.merge(teamCollectionNewTeam);
                }
            }
            for (Discussion discussionCollectionNewDiscussion : discussionCollectionNew) {
                if (!discussionCollectionOld.contains(discussionCollectionNewDiscussion)) {
                    User oldUserOfDiscussionCollectionNewDiscussion = discussionCollectionNewDiscussion.getUser();
                    discussionCollectionNewDiscussion.setUser(user);
                    discussionCollectionNewDiscussion = em.merge(discussionCollectionNewDiscussion);
                    if (oldUserOfDiscussionCollectionNewDiscussion != null && !oldUserOfDiscussionCollectionNewDiscussion.equals(user)) {
                        oldUserOfDiscussionCollectionNewDiscussion.getDiscussionCollection().remove(discussionCollectionNewDiscussion);
                        oldUserOfDiscussionCollectionNewDiscussion = em.merge(oldUserOfDiscussionCollectionNewDiscussion);
                    }
                }
            }
            for (Task taskCollectionOldTask : taskCollectionOld) {
                if (!taskCollectionNew.contains(taskCollectionOldTask)) {
                    taskCollectionOldTask.setUSERuserid(null);
                    taskCollectionOldTask = em.merge(taskCollectionOldTask);
                }
            }
            for (Task taskCollectionNewTask : taskCollectionNew) {
                if (!taskCollectionOld.contains(taskCollectionNewTask)) {
                    User oldUSERuseridOfTaskCollectionNewTask = taskCollectionNewTask.getUSERuserid();
                    taskCollectionNewTask.setUSERuserid(user);
                    taskCollectionNewTask = em.merge(taskCollectionNewTask);
                    if (oldUSERuseridOfTaskCollectionNewTask != null && !oldUSERuseridOfTaskCollectionNewTask.equals(user)) {
                        oldUSERuseridOfTaskCollectionNewTask.getTaskCollection().remove(taskCollectionNewTask);
                        oldUSERuseridOfTaskCollectionNewTask = em.merge(oldUSERuseridOfTaskCollectionNewTask);
                    }
                }
            }
            for (Comment commentCollectionNewComment : commentCollectionNew) {
                if (!commentCollectionOld.contains(commentCollectionNewComment)) {
                    User oldUserOfCommentCollectionNewComment = commentCollectionNewComment.getUser();
                    commentCollectionNewComment.setUser(user);
                    commentCollectionNewComment = em.merge(commentCollectionNewComment);
                    if (oldUserOfCommentCollectionNewComment != null && !oldUserOfCommentCollectionNewComment.equals(user)) {
                        oldUserOfCommentCollectionNewComment.getCommentCollection().remove(commentCollectionNewComment);
                        oldUserOfCommentCollectionNewComment = em.merge(oldUserOfCommentCollectionNewComment);
                    }
                }
            }
            for (Workload workloadCollectionNewWorkload : workloadCollectionNew) {
                if (!workloadCollectionOld.contains(workloadCollectionNewWorkload)) {
                    User oldUserOfWorkloadCollectionNewWorkload = workloadCollectionNewWorkload.getUser();
                    workloadCollectionNewWorkload.setUser(user);
                    workloadCollectionNewWorkload = em.merge(workloadCollectionNewWorkload);
                    if (oldUserOfWorkloadCollectionNewWorkload != null && !oldUserOfWorkloadCollectionNewWorkload.equals(user)) {
                        oldUserOfWorkloadCollectionNewWorkload.getWorkloadCollection().remove(workloadCollectionNewWorkload);
                        oldUserOfWorkloadCollectionNewWorkload = em.merge(oldUserOfWorkloadCollectionNewWorkload);
                    }
                }
            }
            for (DailyScrumEntry dailyScrumEntryCollectionNewDailyScrumEntry : dailyScrumEntryCollectionNew) {
                if (!dailyScrumEntryCollectionOld.contains(dailyScrumEntryCollectionNewDailyScrumEntry)) {
                    User oldUserOfDailyScrumEntryCollectionNewDailyScrumEntry = dailyScrumEntryCollectionNewDailyScrumEntry.getUser();
                    dailyScrumEntryCollectionNewDailyScrumEntry.setUser(user);
                    dailyScrumEntryCollectionNewDailyScrumEntry = em.merge(dailyScrumEntryCollectionNewDailyScrumEntry);
                    if (oldUserOfDailyScrumEntryCollectionNewDailyScrumEntry != null && !oldUserOfDailyScrumEntryCollectionNewDailyScrumEntry.equals(user)) {
                        oldUserOfDailyScrumEntryCollectionNewDailyScrumEntry.getDailyScrumEntryCollection().remove(dailyScrumEntryCollectionNewDailyScrumEntry);
                        oldUserOfDailyScrumEntryCollectionNewDailyScrumEntry = em.merge(oldUserOfDailyScrumEntryCollectionNewDailyScrumEntry);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getUserId();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Discussion> discussionCollectionOrphanCheck = user.getDiscussionCollection();
            for (Discussion discussionCollectionOrphanCheckDiscussion : discussionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Discussion " + discussionCollectionOrphanCheckDiscussion + " in its discussionCollection field has a non-nullable user field.");
            }
            Collection<Comment> commentCollectionOrphanCheck = user.getCommentCollection();
            for (Comment commentCollectionOrphanCheckComment : commentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Comment " + commentCollectionOrphanCheckComment + " in its commentCollection field has a non-nullable user field.");
            }
            Collection<Workload> workloadCollectionOrphanCheck = user.getWorkloadCollection();
            for (Workload workloadCollectionOrphanCheckWorkload : workloadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Workload " + workloadCollectionOrphanCheckWorkload + " in its workloadCollection field has a non-nullable user field.");
            }
            Collection<DailyScrumEntry> dailyScrumEntryCollectionOrphanCheck = user.getDailyScrumEntryCollection();
            for (DailyScrumEntry dailyScrumEntryCollectionOrphanCheckDailyScrumEntry : dailyScrumEntryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the DailyScrumEntry " + dailyScrumEntryCollectionOrphanCheckDailyScrumEntry + " in its dailyScrumEntryCollection field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Team> teamCollection = user.getTeamCollection();
            for (Team teamCollectionTeam : teamCollection) {
                teamCollectionTeam.getUserCollection().remove(user);
                teamCollectionTeam = em.merge(teamCollectionTeam);
            }
            Collection<Task> taskCollection = user.getTaskCollection();
            for (Task taskCollectionTask : taskCollection) {
                taskCollectionTask.setUSERuserid(null);
                taskCollectionTask = em.merge(taskCollectionTask);
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

    public User findUser(Integer id) {
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
