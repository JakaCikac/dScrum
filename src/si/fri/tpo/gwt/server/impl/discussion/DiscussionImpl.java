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
        return Pair.of(true, "");
    }

    public static Pair<Boolean, String> updateDiscussion(DiscussionDTO discussionDTO) {
        try {
            Discussion d = ProxyManager.getDiscussionProxy().findDiscussion(discussionDTO.getDiscussionPK().getDiscussionId());
            d.setContent(discussionDTO.getContent());
            d.setCreatetime(discussionDTO.getCreatetime());

            List<Comment> commentList = d.getCommentList();
            for(CommentDTO commentDTO : discussionDTO.getCommentList()){
                CommentPK commentPK = new CommentPK();
                commentPK.setCommentId(commentDTO.getCommentPK().getCommentId());
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
        return Pair.of(true, "");
    }
}
