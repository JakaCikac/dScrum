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
import si.fri.tpo.gwt.server.jpa.Project;
import si.fri.tpo.gwt.server.jpa.User;
import si.fri.tpo.gwt.server.jpa.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import si.fri.tpo.gwt.server.controllers.exceptions.IllegalOrphanException;
import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.gwt.server.jpa.Discussion;
import si.fri.tpo.gwt.server.jpa.DiscussionPK;

/**
 *
 * @author Administrator
 */
public class DiscussionJpaController implements Serializable {

    public DiscussionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public DiscussionJpaController() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Discussion discussion) throws PreexistingEntityException, Exception {
        if (discussion.getDiscussionPK() == null) {
            discussion.setDiscussionPK(new DiscussionPK());
        }
        if (discussion.getCommentList() == null) {
            discussion.setCommentList(new ArrayList<Comment>());
        }
        discussion.getDiscussionPK().setUserUserId(discussion.getUser().getUserId());
        discussion.getDiscussionPK().setProjectProjectId(discussion.getProject().getProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project = discussion.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectId());
                discussion.setProject(project);
            }
            User user = discussion.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                discussion.setUser(user);
            }
            List<Comment> attachedCommentList = new ArrayList<Comment>();
            for (Comment commentListCommentToAttach : discussion.getCommentList()) {
                commentListCommentToAttach = em.getReference(commentListCommentToAttach.getClass(), commentListCommentToAttach.getCommentPK());
                attachedCommentList.add(commentListCommentToAttach);
            }
            discussion.setCommentList(attachedCommentList);
            em.persist(discussion);
            if (project != null) {
                project.getDiscussionList().add(discussion);
                project = em.merge(project);
            }
            if (user != null) {
                user.getDiscussionList().add(discussion);
                user = em.merge(user);
            }
            for (Comment commentListComment : discussion.getCommentList()) {
                Discussion oldDiscussionOfCommentListComment = commentListComment.getDiscussion();
                commentListComment.setDiscussion(discussion);
                commentListComment = em.merge(commentListComment);
                if (oldDiscussionOfCommentListComment != null) {
                    oldDiscussionOfCommentListComment.getCommentList().remove(commentListComment);
                    oldDiscussionOfCommentListComment = em.merge(oldDiscussionOfCommentListComment);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDiscussion(discussion.getDiscussionPK()) != null) {
                throw new PreexistingEntityException("Discussion " + discussion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Discussion discussion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        discussion.getDiscussionPK().setUserUserId(discussion.getUser().getUserId());
        discussion.getDiscussionPK().setProjectProjectId(discussion.getProject().getProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion persistentDiscussion = em.find(Discussion.class, discussion.getDiscussionPK());
            Project projectOld = persistentDiscussion.getProject();
            Project projectNew = discussion.getProject();
            User userOld = persistentDiscussion.getUser();
            User userNew = discussion.getUser();
            List<Comment> commentListOld = persistentDiscussion.getCommentList();
            List<Comment> commentListNew = discussion.getCommentList();
            List<String> illegalOrphanMessages = null;
            for (Comment commentListOldComment : commentListOld) {
                if (!commentListNew.contains(commentListOldComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comment " + commentListOldComment + " since its discussion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectId());
                discussion.setProject(projectNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                discussion.setUser(userNew);
            }
            List<Comment> attachedCommentListNew = new ArrayList<Comment>();
            for (Comment commentListNewCommentToAttach : commentListNew) {
                commentListNewCommentToAttach = em.getReference(commentListNewCommentToAttach.getClass(), commentListNewCommentToAttach.getCommentPK());
                attachedCommentListNew.add(commentListNewCommentToAttach);
            }
            commentListNew = attachedCommentListNew;
            discussion.setCommentList(commentListNew);
            discussion = em.merge(discussion);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getDiscussionList().remove(discussion);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getDiscussionList().add(discussion);
                projectNew = em.merge(projectNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getDiscussionList().remove(discussion);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getDiscussionList().add(discussion);
                userNew = em.merge(userNew);
            }
            for (Comment commentListNewComment : commentListNew) {
                if (!commentListOld.contains(commentListNewComment)) {
                    Discussion oldDiscussionOfCommentListNewComment = commentListNewComment.getDiscussion();
                    commentListNewComment.setDiscussion(discussion);
                    commentListNewComment = em.merge(commentListNewComment);
                    if (oldDiscussionOfCommentListNewComment != null && !oldDiscussionOfCommentListNewComment.equals(discussion)) {
                        oldDiscussionOfCommentListNewComment.getCommentList().remove(commentListNewComment);
                        oldDiscussionOfCommentListNewComment = em.merge(oldDiscussionOfCommentListNewComment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DiscussionPK id = discussion.getDiscussionPK();
                if (findDiscussion(id) == null) {
                    throw new NonexistentEntityException("The discussion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DiscussionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion discussion;
            try {
                discussion = em.getReference(Discussion.class, id);
                discussion.getDiscussionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discussion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Comment> commentListOrphanCheck = discussion.getCommentList();
            for (Comment commentListOrphanCheckComment : commentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Discussion (" + discussion + ") cannot be destroyed since the Comment " + commentListOrphanCheckComment + " in its commentList field has a non-nullable discussion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Project project = discussion.getProject();
            if (project != null) {
                project.getDiscussionList().remove(discussion);
                project = em.merge(project);
            }
            User user = discussion.getUser();
            if (user != null) {
                user.getDiscussionList().remove(discussion);
                user = em.merge(user);
            }
            em.remove(discussion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Discussion> findDiscussionEntities() {
        return findDiscussionEntities(true, -1, -1);
    }

    public List<Discussion> findDiscussionEntities(int maxResults, int firstResult) {
        return findDiscussionEntities(false, maxResults, firstResult);
    }

    private List<Discussion> findDiscussionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Discussion.class));
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

    public Discussion findDiscussion(DiscussionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Discussion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiscussionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Discussion> rt = cq.from(Discussion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
