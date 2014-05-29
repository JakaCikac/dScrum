package si.fri.tpo.gwt.server.impl.discussion;

import si.fri.tpo.gwt.client.components.Pair;
import si.fri.tpo.gwt.client.dto.*;
import si.fri.tpo.gwt.server.jpa.*;
import si.fri.tpo.gwt.server.proxy.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 20/05/14.
 */
public class DiscussionImpl {

    // WARNING: doesnt fill all user and all comment data, but it's not needed just yet..
    public static List<DiscussionDTO> getAllDiscussionOfProject(ProjectDTO projectDTO) {
        List<Discussion> discussionList = ProxyManager.getDiscussionProxy().findDiscussionList();

        List<DiscussionDTO> discussionDTOList = new ArrayList<DiscussionDTO>();
        for (Discussion discussion : discussionList) {
            if (discussion.getDiscussionPK().getProjectProjectId() == projectDTO.getProjectId()) {
                DiscussionDTO dDTO = new DiscussionDTO();

                DiscussionPKDTO discussionPKDTO = new DiscussionPKDTO();
                discussionPKDTO.setDiscussionId(discussion.getDiscussionPK().getDiscussionId());
                discussionPKDTO.setProjectProjectId(discussion.getDiscussionPK().getProjectProjectId());
                discussionPKDTO.setUserUserId(discussion.getDiscussionPK().getUserUserId());

                dDTO.setDiscussionPK(discussionPKDTO);

                dDTO.setCreatetime(discussion.getCreatetime());
                dDTO.setContent(discussion.getContent());
                dDTO.setProject(projectDTO);

                UserDTO uDTO = new UserDTO();
                User user = ProxyManager.getUserProxy().findUserById(discussion.getUser().getUserId());
                uDTO.setUserId(user.getUserId());
                uDTO.setUsername(user.getUsername());

                dDTO.setUser(uDTO);

                List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
                List<Comment> commentList = ProxyManager.getCommentProxy().findCommentEntities();
                for (Comment comment : commentList)
                    if (comment.getCommentPK().getDiscussionDiscussionId() == discussion.getDiscussionPK().getDiscussionId()) {
                        CommentDTO commentDTO = new CommentDTO();
                        commentDTO.setContent(comment.getContent());
                        commentDTOList.add(commentDTO);
                    } else continue;

                dDTO.setCommentList(commentDTOList);

                discussionDTOList.add(dDTO);
            } else continue;
        }
        return discussionDTOList;
    }

    public static Pair<Boolean, String> saveDiscussion(DiscussionDTO discussionDTO, ProjectDTO projectDTO) {
        try {
            Discussion d = new Discussion();
            d.setContent(discussionDTO.getContent());
            d.setCreatetime(discussionDTO.getCreatetime());

            User u = ProxyManager.getUserProxy().findUserById(discussionDTO.getUser().getUserId());
            if (u == null ){
                return Pair.of(false, "User doesnt exist.");
            }
            d.setUser(u);

            Project p = ProxyManager.getProjectProxy().findProjectByName(projectDTO.getName());
            if (p == null ){
                return Pair.of(false, "Project doesnt exist.");
            }
            d.setProject(p);

            DiscussionPK dpk = new DiscussionPK();
            dpk.setProjectProjectId(p.getProjectId());
            dpk.setUserUserId(u.getUserId());

            d.setDiscussionPK(dpk);

            try {
                if (d == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getDiscussionProxy().create(d);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Discussion saved successfully.");
    }

    public static Pair<Boolean, String> updateDiscussion(DiscussionDTO discussionDTO) {
        try {

            DiscussionPK discussionPK = new DiscussionPK();
            discussionPK.setUserUserId(discussionDTO.getDiscussionPK().getUserUserId());
            discussionPK.setDiscussionId(discussionDTO.getDiscussionPK().getDiscussionId());
            discussionPK.setProjectProjectId(discussionDTO.getDiscussionPK().getProjectProjectId());

            Discussion d = ProxyManager.getDiscussionProxy().findDiscussion(discussionPK);
            d.setContent(discussionDTO.getContent());
            d.setCreatetime(discussionDTO.getCreatetime());

            List<Comment> commentList = d.getCommentList();
            for (CommentDTO commentDTO : discussionDTO.getCommentList()) {
                CommentPK commentPK = new CommentPK();
                commentPK.setCommentId(commentDTO.getCommentPK().getCommentId());
                System.out.println("ERRORS");
                System.out.println(commentDTO);
                System.out.println(commentDTO.getCommentPK());
                System.out.println(commentDTO.getCommentPK().getCommentId());
                System.out.println("DISCUSSION "+commentDTO.getCommentPK().getDiscussionDiscussionId());
                commentPK.setDiscussionDiscussionId(commentDTO.getCommentPK().getDiscussionDiscussionId());
                commentPK.setDiscussionProjectProjectId(commentDTO.getCommentPK().getDiscussionProjectProjectId());
                commentPK.setDiscussionUserUserId(commentDTO.getCommentPK().getDiscussionUserUserId());
                commentPK.setUserUserId(commentDTO.getCommentPK().getUserUserId());
                Comment comment = ProxyManager.getCommentProxy().findComment(commentPK);

                commentList.add(comment);
            }
            d.setCommentList(commentList);

            try {
                if (d == null)
                    return Pair.of(false, "Data error!");

                ProxyManager.getDiscussionProxy().edit(d);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, e.getMessage());
        }
        return Pair.of(true, "Discussion updated successfully.");
    }

    public static Pair<Boolean, Integer> saveDiscussionComment(CommentDTO commentDTO) {
        int insertedCommentID = -1;
        try {
            Comment comment = new Comment();
            comment.setContent(commentDTO.getContent());
            comment.setCreatetime(commentDTO.getCreatetime());

            DiscussionPK discussionPK = new DiscussionPK();
            discussionPK.setUserUserId(commentDTO.getDiscussion().getDiscussionPK().getUserUserId());
            discussionPK.setDiscussionId(commentDTO.getDiscussion().getDiscussionPK().getDiscussionId());
            discussionPK.setProjectProjectId(commentDTO.getDiscussion().getDiscussionPK().getProjectProjectId());

            Discussion discussion = ProxyManager.getDiscussionProxy().findDiscussion(discussionPK);
            if (discussion == null) {
                return Pair.of(false, -1);
            }
            comment.setDiscussion(discussion);

            User user = ProxyManager.getUserProxy().findUser(commentDTO.getUser().getUserId());
            if (user == null) {
                return Pair.of(false, -2);
            }
            comment.setUser(user);

            CommentPK commentPK = new CommentPK();
            commentPK.setDiscussionUserUserId(discussion.getUser().getUserId());
            commentPK.setDiscussionProjectProjectId(discussion.getProject().getProjectId());
            commentPK.setUserUserId(user.getUserId());
            commentPK.setDiscussionDiscussionId(discussion.getDiscussionPK().getDiscussionId());
            comment.setCommentPK(commentPK);

            try {
                if (comment == null) {
                    return Pair.of(false, -3);
                }
                insertedCommentID = ProxyManager.getCommentProxy().create(comment);
                if (insertedCommentID == -1) {
                    System.out.println("ob vstavljanju s kontrolerjem je Comment id ... -1 :(");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return Pair.of(false, -4);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Pair.of(false, -5);
        }
        return Pair.of(true, insertedCommentID);
    }
}
