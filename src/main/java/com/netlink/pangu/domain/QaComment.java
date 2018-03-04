package com.netlink.pangu.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`qa_comment`")
public class QaComment {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 回答ID
     */
    @Column(name = "answer_id")
    private Long answerId;

    /**
     * 评论人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 评论人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 被回复评论ID
     */
    @Column(name = "reply_to_comment_id")
    private Long replyToCommentId;

    /**
     * 被回复用户ID
     */
    @Column(name = "reply_to_user_id")
    private String replyToUserId;

    /**
     * 被回复用户名
     */
    @Column(name = "reply_to_user_name")
    private String replyToUserName;

    /**
     * 是否逻辑删除
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 创建时间
     */
    @Column(name = "gmt_created")
    private Date gmtCreated;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 评论
     */
    private String comment;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取回答ID
     *
     * @return answer_id - 回答ID
     */
    public Long getAnswerId() {
        return answerId;
    }

    /**
     * 设置回答ID
     *
     * @param answerId 回答ID
     */
    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    /**
     * 获取评论人ID
     *
     * @return user_id - 评论人ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置评论人ID
     *
     * @param userId 评论人ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取评论人姓名
     *
     * @return user_name - 评论人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置评论人姓名
     *
     * @param userName 评论人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取被回复评论ID
     *
     * @return reply_to_comment_id - 被回复评论ID
     */
    public Long getReplyToCommentId() {
        return replyToCommentId;
    }

    /**
     * 设置被回复评论ID
     *
     * @param replyToCommentId 被回复评论ID
     */
    public void setReplyToCommentId(Long replyToCommentId) {
        this.replyToCommentId = replyToCommentId;
    }

    /**
     * 获取被回复用户ID
     *
     * @return reply_to_user_id - 被回复用户ID
     */
    public String getReplyToUserId() {
        return replyToUserId;
    }

    /**
     * 设置被回复用户ID
     *
     * @param replyToUserId 被回复用户ID
     */
    public void setReplyToUserId(String replyToUserId) {
        this.replyToUserId = replyToUserId == null ? null : replyToUserId.trim();
    }

    /**
     * 获取被回复用户名
     *
     * @return reply_to_user_name - 被回复用户名
     */
    public String getReplyToUserName() {
        return replyToUserName;
    }

    /**
     * 设置被回复用户名
     *
     * @param replyToUserName 被回复用户名
     */
    public void setReplyToUserName(String replyToUserName) {
        this.replyToUserName = replyToUserName == null ? null : replyToUserName.trim();
    }

    /**
     * 获取是否逻辑删除
     *
     * @return is_delete - 是否逻辑删除
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否逻辑删除
     *
     * @param isDelete 是否逻辑删除
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    /**
     * 获取创建时间
     *
     * @return gmt_created - 创建时间
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreated 创建时间
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * 获取修改时间
     *
     * @return gmt_modified - 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置修改时间
     *
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取评论
     *
     * @return comment - 评论
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置评论
     *
     * @param comment 评论
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}