/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.fri.tpo.gwt.server.controllers;

import si.fri.tpo.gwt.server.controllers.exceptions.NonexistentEntityException;
import si.fri.tpo.gwt.server.controllers.exceptions.PreexistingEntityException;
import si.fri.tpo.gwt.server.jpa.Comment;
import si.fri.tpo.gwt.server.jpa.CommentPK;
import si.fri.tpo.gwt.server.jpa.Discussion;
import si.fri.tpo.gwt.server.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CommentJpaController implements Serializable {

    public CommentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Integer create(Comment comment) throws PreexistingEntityException, Exception {
        int insertedCommentID = -1;
        if (comment.getCommentPK() == null) {
            comment.setCommentPK(new CommentPK());
        }
        comment.getCommentPK().setDiscussionDiscussionId(comment.getDiscussion().getDiscussionPK().getDiscussionId());
        comment.getCommentPK().setDiscussionUserUserId(comment.getDiscussion().getDiscussionPK().getUserUserId());
        comment.getCommentPK().setUserUserId(comment.getUser().getUserId());
        comment.getCommentPK().setDiscussionProjectProjectId(comment.getDiscussion().getDiscussionPK().getProjectProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Discussion discussion = comment.getDiscussion();
            if (discussion != null) {
                discussion = em.getReference(discussion.getClass(), discussion.getDiscussionPK());
                comment.setDiscussion(discussion);
            }
            User user = comment.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                comment.setUser(user);
            }
            em.persist(comment);
            if (discussion != null) {
                discussion.getCommentList().add(comment);
                discussion = em.merge(discussion);
            }
            if (user != null) {
                user.getCommentList().add(comment);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComment(comment.getCommentPK()) != null) {
                throw new PreexistingEntityException("Comment " + comment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                insertedCommentID = comment.getCommentPK().getCommentId();
                em.close();
            }
        }
        return insertedCommentID;
    }

    public void edit(Comment comment) throws NonexistentEntityException, Exception {
        comment.getCommentPK().setDiscussionDiscussionId(comment.getDiscussion().getDiscussionPK().getDiscussionId());
        comment.getCommentPK().setDiscussionUserUserId(comment.getDiscussion().getDiscussionPK().getUserUserId());
        comment.getCommentPK().setUserUserId(comment.getUser().getUserId());
        comment.getCommentPK().setDiscussionProjectProjectId(comment.getDiscussion().getDiscussionPK().getProjectProjectId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comment persistentComment = em.find(Comment.class, comment.getCommentPK());
            Discussion discussionOld = persistentComment.getDiscussion();
            Discussion discussionNew = comment.getDiscussion();
            User userOld = persistentComment.getUser();
            User userNew = comment.getUser();
            if (discussionNew != null) {
                discussionNew = em.getReference(discussionNew.getClass(), discussionNew.getDiscussionPK());
                comment.setDiscussion(discussionNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                comment.setUser(userNew);
            }
            comment = em.merge(comment);
            if (discussionOld != null && !discussionOld.equals(discussionNew)) {
                discussionOld.getCommentList().remove(comment);
                discussionOld = em.merge(discussionOld);
            }
            if (discussionNew != null && !discussionNew.equals(discussionOld)) {
                discussionNew.getCommentList().add(comment);
                discussionNew = em.merge(discussionNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getCommentList().remove(comment);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getCommentList().add(comment);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CommentPK id = comment.getCommentPK();
                if (findComment(id) == null) {
                    throw new NonexistentEntityException("The comment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CommentPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comment comment;
            try {
                comment = em.getReference(Comment.class, id);
                comment.getCommentPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comment with id " + id + " no longer exists.", enfe);
            }
            Discussion discussion = comment.getDiscussion();
            if (discussion != null) {
                discussion.getCommentList().remove(comment);
                discussion = em.merge(discussion);
            }
            User user = comment.getUser();
            if (user != null) {
                user.getCommentList().remove(comment);
                user = em.merge(user);
            }
            em.remove(comment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comment> findCommentEntities() {
        return findCommentEntities(true, -1, -1);
    }

    public List<Comment> findCommentEntities(int maxResults, int firstResult) {
        return findCommentEntities(false, maxResults, firstResult);
    }

    private List<Comment> findCommentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comment.class));
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

    public Comment findComment(CommentPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comment.class, id);
        } finally {
            em.close();
        }
    }

    public int getCommentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comment> rt = cq.from(Comment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
