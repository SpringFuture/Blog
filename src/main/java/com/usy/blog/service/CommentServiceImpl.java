package com.usy.blog.service;

import com.usy.blog.dao.CommentRepository;
import com.usy.blog.model.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<Comment> comments =commentRepository.findByBlogIdAndParentCommentNull(blogId,Sort.by(Sort.Direction.DESC,"createTime"));
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId =comment.getParentComment().getId();
        if (parentCommentId!=-1){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
    /**
     * 循环每一个顶级的评论节点
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView =new ArrayList<>();
        for(Comment comment : comments){
            Comment c=new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    /**s
     * root根节点，blog不为空集合对象
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for(Comment comment : comments){
            List<Comment> replays1=comment.getReplyComments();
            for(Comment replay1 : replays1){
                recursively(replay1);
            }
            comment.setReplyComments(tempReplays);
            tempReplays=new ArrayList<>();
        }
    }

    /**
     * 递归迭代
     * @param comment
     */
    private void recursively(Comment comment) {
        tempReplays.add(comment);//顶点添加到临时存放集合
        if(comment.getReplyComments().size()>0){
            List<Comment> replays=comment.getReplyComments();
            for(Comment replay : replays){
                tempReplays.add(replay);
                if (replay.getReplyComments().size()>0){
                    recursively(replay);
                }
            }
        }
    }

    //存放迭代栈出的所有子代集合
    private List<Comment> tempReplays=new ArrayList<>();
}
