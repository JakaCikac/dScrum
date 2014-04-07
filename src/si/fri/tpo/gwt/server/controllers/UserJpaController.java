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
import si.fri.tpo.gwt.server.jpa.Team;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.jpa.Workload;
import si.fri.tpo.gwt.server.jpa.Discussion;
import si.fri.tpo.gwt.server.jpa.Task;
import si.fri.tpo.gwt.server.jpa.DailyScrumEntry;
import si.fri.tpo.gwt.server.jpa.Comment;
import si.fri.tpo.gwt.server.jpa.User;

/**
 *
 * @author Administrator
 */
public class UserJpaController  {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public UserJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getTeamList() == null) {
            user.setTeamList(new ArrayList<Team>());
        }
        if (user.getWorkloadList() == null) {
            user.setWorkloadList(new ArrayList<Workload>());
        }
        if (user.getDiscussionList() == null) {
            user.setDiscussionList(new ArrayList<Discussion>());
        }
        if (user.getTaskList() == null) {
            user.setTaskList(new ArrayList<Task>());
        }
        if (user.getDailyScrumEntryList() == null) {
            user.setDailyScrumEntryList(new ArrayList<DailyScrumEntry>());
        }
        if (user.getCommentList() == null) {
            user.setCommentList(new ArrayList<Comment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Team> attachedTeamList = new ArrayList<Team>();
            for (Team teamListTeamToAttach : user.getTeamList()) {
                teamListTeamToAttach = em.getReference(teamListTeamToAttach.getClass(), teamListTeamToAttach.getTeamId());
                attachedTeamList.add(teamListTeamToAttach);
            }
            user.setTeamList(attachedTeamList);
            List<Workload> attachedWorkloadList = new ArrayList<Workload>();
            for (Workload workloadListWorkloadToAttach : user.getWorkloadList()) {
                workloadListWorkloadToAttach = em.getReference(workloadListWorkloadToAttach.getClass(), workloadListWorkloadToAttach.getWorkloadPK());
                attachedWorkloadList.add(workloadListWorkloadToAttach);
            }
            user.setWorkloadList(attachedWorkloadList);
            List<Discussion> attachedDiscussionList = new ArrayList<Discussion>();
            for (Discussion discussionListDiscussionToAttach : user.getDiscussionList()) {
                discussionListDiscussionToAttach = em.getReference(discussionListDiscussionToAttach.getClass(), discussionListDiscussionToAttach.getDiscussionPK());
                attachedDiscussionList.add(discussionListDiscussionToAttach);
            }
            user.setDiscussionList(attachedDiscussionList);
            List<Task> attachedTaskList = new ArrayList<Task>();
            for (Task taskListTaskToAttach : user.getTaskList()) {
                taskListTaskToAttach = em.getReference(taskListTaskToAttach.getClass(), taskListTaskToAttach.getTaskPK());
                attachedTaskList.add(taskListTaskToAttach);
            }
            user.setTaskList(attachedTaskList);
            List<DailyScrumEntry> attachedDailyScrumEntryList = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryListDailyScrumEntryToAttach : user.getDailyScrumEntryList()) {
                dailyScrumEntryListDailyScrumEntryToAttach = em.getReference(dailyScrumEntryListDailyScrumEntryToAttach.getClass(), dailyScrumEntryListDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryList.add(dailyScrumEntryListDailyScrumEntryToAttach);
            }
            user.setDailyScrumEntryList(attachedDailyScrumEntryList);
            List<Comment> attachedCommentList = new ArrayList<Comment>();
            for (Comment commentListCommentToAttach : user.getCommentList()) {
                commentListCommentToAttach = em.getReference(commentListCommentToAttach.getClass(), commentListCommentToAttach.getCommentPK());
                attachedCommentList.add(commentListCommentToAttach);
            }
            user.setCommentList(attachedCommentList);
            em.persist(user);
            for (Team teamListTeam : user.getTeamList()) {
                teamListTeam.getUserList().add(user);
                teamListTeam = em.merge(teamListTeam);
            }
            for (Workload workloadListWorkload : user.getWorkloadList()) {
                User oldUserOfWorkloadListWorkload = workloadListWorkload.getUser();
                workloadListWorkload.setUser(user);
                workloadListWorkload = em.merge(workloadListWorkload);
                if (oldUserOfWorkloadListWorkload != null) {
                    oldUserOfWorkloadListWorkload.getWorkloadList().remove(workloadListWorkload);
                    oldUserOfWorkloadListWorkload = em.merge(oldUserOfWorkloadListWorkload);
                }
            }
            for (Discussion discussionListDiscussion : user.getDiscussionList()) {
                User oldUserOfDiscussionListDiscussion = discussionListDiscussion.getUser();
                discussionListDiscussion.setUser(user);
                discussionListDiscussion = em.merge(discussionListDiscussion);
                if (oldUserOfDiscussionListDiscussion != null) {
                    oldUserOfDiscussionListDiscussion.getDiscussionList().remove(discussionListDiscussion);
                    oldUserOfDiscussionListDiscussion = em.merge(oldUserOfDiscussionListDiscussion);
                }
            }
            for (Task taskListTask : user.getTaskList()) {
                User oldUserUserIdOfTaskListTask = taskListTask.getUserUserId();
                taskListTask.setUserUserId(user);
                taskListTask = em.merge(taskListTask);
                if (oldUserUserIdOfTaskListTask != null) {
                    oldUserUserIdOfTaskListTask.getTaskList().remove(taskListTask);
                    oldUserUserIdOfTaskListTask = em.merge(oldUserUserIdOfTaskListTask);
                }
            }
            for (DailyScrumEntry dailyScrumEntryListDailyScrumEntry : user.getDailyScrumEntryList()) {
                User oldUserOfDailyScrumEntryListDailyScrumEntry = dailyScrumEntryListDailyScrumEntry.getUser();
                dailyScrumEntryListDailyScrumEntry.setUser(user);
                dailyScrumEntryListDailyScrumEntry = em.merge(dailyScrumEntryListDailyScrumEntry);
                if (oldUserOfDailyScrumEntryListDailyScrumEntry != null) {
                    oldUserOfDailyScrumEntryListDailyScrumEntry.getDailyScrumEntryList().remove(dailyScrumEntryListDailyScrumEntry);
                    oldUserOfDailyScrumEntryListDailyScrumEntry = em.merge(oldUserOfDailyScrumEntryListDailyScrumEntry);
                }
            }
            for (Comment commentListComment : user.getCommentList()) {
                User oldUserOfCommentListComment = commentListComment.getUser();
                commentListComment.setUser(user);
                commentListComment = em.merge(commentListComment);
                if (oldUserOfCommentListComment != null) {
                    oldUserOfCommentListComment.getCommentList().remove(commentListComment);
                    oldUserOfCommentListComment = em.merge(oldUserOfCommentListComment);
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
            List<Team> teamListOld = persistentUser.getTeamList();
            List<Team> teamListNew = user.getTeamList();
            List<Workload> workloadListOld = persistentUser.getWorkloadList();
            List<Workload> workloadListNew = user.getWorkloadList();
            List<Discussion> discussionListOld = persistentUser.getDiscussionList();
            List<Discussion> discussionListNew = user.getDiscussionList();
            List<Task> taskListOld = persistentUser.getTaskList();
            List<Task> taskListNew = user.getTaskList();
            List<DailyScrumEntry> dailyScrumEntryListOld = persistentUser.getDailyScrumEntryList();
            List<DailyScrumEntry> dailyScrumEntryListNew = user.getDailyScrumEntryList();
            List<Comment> commentListOld = persistentUser.getCommentList();
            List<Comment> commentListNew = user.getCommentList();
            List<String> illegalOrphanMessages = null;
            for (Workload workloadListOldWorkload : workloadListOld) {
                if (!workloadListNew.contains(workloadListOldWorkload)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workload " + workloadListOldWorkload + " since its user field is not nullable.");
                }
            }
            for (Discussion discussionListOldDiscussion : discussionListOld) {
                if (!discussionListNew.contains(discussionListOldDiscussion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Discussion " + discussionListOldDiscussion + " since its user field is not nullable.");
                }
            }
            for (DailyScrumEntry dailyScrumEntryListOldDailyScrumEntry : dailyScrumEntryListOld) {
                if (!dailyScrumEntryListNew.contains(dailyScrumEntryListOldDailyScrumEntry)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DailyScrumEntry " + dailyScrumEntryListOldDailyScrumEntry + " since its user field is not nullable.");
                }
            }
            for (Comment commentListOldComment : commentListOld) {
                if (!commentListNew.contains(commentListOldComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comment " + commentListOldComment + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Team> attachedTeamListNew = new ArrayList<Team>();
            for (Team teamListNewTeamToAttach : teamListNew) {
                teamListNewTeamToAttach = em.getReference(teamListNewTeamToAttach.getClass(), teamListNewTeamToAttach.getTeamId());
                attachedTeamListNew.add(teamListNewTeamToAttach);
            }
            teamListNew = attachedTeamListNew;
            user.setTeamList(teamListNew);
            List<Workload> attachedWorkloadListNew = new ArrayList<Workload>();
            for (Workload workloadListNewWorkloadToAttach : workloadListNew) {
                workloadListNewWorkloadToAttach = em.getReference(workloadListNewWorkloadToAttach.getClass(), workloadListNewWorkloadToAttach.getWorkloadPK());
                attachedWorkloadListNew.add(workloadListNewWorkloadToAttach);
            }
            workloadListNew = attachedWorkloadListNew;
            user.setWorkloadList(workloadListNew);
            List<Discussion> attachedDiscussionListNew = new ArrayList<Discussion>();
            for (Discussion discussionListNewDiscussionToAttach : discussionListNew) {
                discussionListNewDiscussionToAttach = em.getReference(discussionListNewDiscussionToAttach.getClass(), discussionListNewDiscussionToAttach.getDiscussionPK());
                attachedDiscussionListNew.add(discussionListNewDiscussionToAttach);
            }
            discussionListNew = attachedDiscussionListNew;
            user.setDiscussionList(discussionListNew);
            List<Task> attachedTaskListNew = new ArrayList<Task>();
            for (Task taskListNewTaskToAttach : taskListNew) {
                taskListNewTaskToAttach = em.getReference(taskListNewTaskToAttach.getClass(), taskListNewTaskToAttach.getTaskPK());
                attachedTaskListNew.add(taskListNewTaskToAttach);
            }
            taskListNew = attachedTaskListNew;
            user.setTaskList(taskListNew);
            List<DailyScrumEntry> attachedDailyScrumEntryListNew = new ArrayList<DailyScrumEntry>();
            for (DailyScrumEntry dailyScrumEntryListNewDailyScrumEntryToAttach : dailyScrumEntryListNew) {
                dailyScrumEntryListNewDailyScrumEntryToAttach = em.getReference(dailyScrumEntryListNewDailyScrumEntryToAttach.getClass(), dailyScrumEntryListNewDailyScrumEntryToAttach.getDailyScrumEntryPK());
                attachedDailyScrumEntryListNew.add(dailyScrumEntryListNewDailyScrumEntryToAttach);
            }
            dailyScrumEntryListNew = attachedDailyScrumEntryListNew;
            user.setDailyScrumEntryList(dailyScrumEntryListNew);
            List<Comment> attachedCommentListNew = new ArrayList<Comment>();
            for (Comment commentListNewCommentToAttach : commentListNew) {
                commentListNewCommentToAttach = em.getReference(commentListNewCommentToAttach.getClass(), commentListNewCommentToAttach.getCommentPK());
                attachedCommentListNew.add(commentListNewCommentToAttach);
            }
            commentListNew = attachedCommentListNew;
            user.setCommentList(commentListNew);
            user = em.merge(user);
            for (Team teamListOldTeam : teamListOld) {
                if (!teamListNew.contains(teamListOldTeam)) {
                    teamListOldTeam.getUserList().remove(user);
                    teamListOldTeam = em.merge(teamListOldTeam);
                }
            }
            for (Team teamListNewTeam : teamListNew) {
                if (!teamListOld.contains(teamListNewTeam)) {
                    teamListNewTeam.getUserList().add(user);
                    teamListNewTeam = em.merge(teamListNewTeam);
                }
            }
            for (Workload workloadListNewWorkload : workloadListNew) {
                if (!workloadListOld.contains(workloadListNewWorkload)) {
                    User oldUserOfWorkloadListNewWorkload = workloadListNewWorkload.getUser();
                    workloadListNewWorkload.setUser(user);
                    workloadListNewWorkload = em.merge(workloadListNewWorkload);
                    if (oldUserOfWorkloadListNewWorkload != null && !oldUserOfWorkloadListNewWorkload.equals(user)) {
                        oldUserOfWorkloadListNewWorkload.getWorkloadList().remove(workloadListNewWorkload);
                        oldUserOfWorkloadListNewWorkload = em.merge(oldUserOfWorkloadListNewWorkload);
                    }
                }
            }
            for (Discussion discussionListNewDiscussion : discussionListNew) {
                if (!discussionListOld.contains(discussionListNewDiscussion)) {
                    User oldUserOfDiscussionListNewDiscussion = discussionListNewDiscussion.getUser();
                    discussionListNewDiscussion.setUser(user);
                    discussionListNewDiscussion = em.merge(discussionListNewDiscussion);
                    if (oldUserOfDiscussionListNewDiscussion != null && !oldUserOfDiscussionListNewDiscussion.equals(user)) {
                        oldUserOfDiscussionListNewDiscussion.getDiscussionList().remove(discussionListNewDiscussion);
                        oldUserOfDiscussionListNewDiscussion = em.merge(oldUserOfDiscussionListNewDiscussion);
                    }
                }
            }
            for (Task taskListOldTask : taskListOld) {
                if (!taskListNew.contains(taskListOldTask)) {
                    taskListOldTask.setUserUserId(null);
                    taskListOldTask = em.merge(taskListOldTask);
                }
            }
            for (Task taskListNewTask : taskListNew) {
                if (!taskListOld.contains(taskListNewTask)) {
                    User oldUserUserIdOfTaskListNewTask = taskListNewTask.getUserUserId();
                    taskListNewTask.setUserUserId(user);
                    taskListNewTask = em.merge(taskListNewTask);
                    if (oldUserUserIdOfTaskListNewTask != null && !oldUserUserIdOfTaskListNewTask.equals(user)) {
                        oldUserUserIdOfTaskListNewTask.getTaskList().remove(taskListNewTask);
                        oldUserUserIdOfTaskListNewTask = em.merge(oldUserUserIdOfTaskListNewTask);
                    }
                }
            }
            for (DailyScrumEntry dailyScrumEntryListNewDailyScrumEntry : dailyScrumEntryListNew) {
                if (!dailyScrumEntryListOld.contains(dailyScrumEntryListNewDailyScrumEntry)) {
                    User oldUserOfDailyScrumEntryListNewDailyScrumEntry = dailyScrumEntryListNewDailyScrumEntry.getUser();
                    dailyScrumEntryListNewDailyScrumEntry.setUser(user);
                    dailyScrumEntryListNewDailyScrumEntry = em.merge(dailyScrumEntryListNewDailyScrumEntry);
                    if (oldUserOfDailyScrumEntryListNewDailyScrumEntry != null && !oldUserOfDailyScrumEntryListNewDailyScrumEntry.equals(user)) {
                        oldUserOfDailyScrumEntryListNewDailyScrumEntry.getDailyScrumEntryList().remove(dailyScrumEntryListNewDailyScrumEntry);
                        oldUserOfDailyScrumEntryListNewDailyScrumEntry = em.merge(oldUserOfDailyScrumEntryListNewDailyScrumEntry);
                    }
                }
            }
            for (Comment commentListNewComment : commentListNew) {
                if (!commentListOld.contains(commentListNewComment)) {
                    User oldUserOfCommentListNewComment = commentListNewComment.getUser();
                    commentListNewComment.setUser(user);
                    commentListNewComment = em.merge(commentListNewComment);
                    if (oldUserOfCommentListNewComment != null && !oldUserOfCommentListNewComment.equals(user)) {
                        oldUserOfCommentListNewComment.getCommentList().remove(commentListNewComment);
                        oldUserOfCommentListNewComment = em.merge(oldUserOfCommentListNewComment);
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
            List<Workload> workloadListOrphanCheck = user.getWorkloadList();
            for (Workload workloadListOrphanCheckWorkload : workloadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Workload " + workloadListOrphanCheckWorkload + " in its workloadList field has a non-nullable user field.");
            }
            List<Discussion> discussionListOrphanCheck = user.getDiscussionList();
            for (Discussion discussionListOrphanCheckDiscussion : discussionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Discussion " + discussionListOrphanCheckDiscussion + " in its discussionList field has a non-nullable user field.");
            }
            List<DailyScrumEntry> dailyScrumEntryListOrphanCheck = user.getDailyScrumEntryList();
            for (DailyScrumEntry dailyScrumEntryListOrphanCheckDailyScrumEntry : dailyScrumEntryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the DailyScrumEntry " + dailyScrumEntryListOrphanCheckDailyScrumEntry + " in its dailyScrumEntryList field has a non-nullable user field.");
            }
            List<Comment> commentListOrphanCheck = user.getCommentList();
            for (Comment commentListOrphanCheckComment : commentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Comment " + commentListOrphanCheckComment + " in its commentList field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Team> teamList = user.getTeamList();
            for (Team teamListTeam : teamList) {
                teamListTeam.getUserList().remove(user);
                teamListTeam = em.merge(teamListTeam);
            }
            List<Task> taskList = user.getTaskList();
            for (Task taskListTask : taskList) {
                taskListTask.setUserUserId(null);
                taskListTask = em.merge(taskListTask);
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
