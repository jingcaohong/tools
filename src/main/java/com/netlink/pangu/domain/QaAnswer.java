package com.netlink.pangu.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "qa_answer")
public class QaAnswer {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 问题ID
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * 回答人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 回答人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 评论数
     */
    private Long comments;

    /**
     * 赞
     */
    private Long likes;

    /**
     * 踩
     */
    private Long dislikes;

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
     * 回答内容
     */
    private String answer;

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
     * 获取问题ID
     *
     * @return question_id - 问题ID
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * 设置问题ID
     *
     * @param questionId 问题ID
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取回答人ID
     *
     * @return user_id - 回答人ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置回答人ID
     *
     * @param userId 回答人ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取回答人姓名
     *
     * @return user_name - 回答人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置回答人姓名
     *
     * @param userName 回答人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取评论数
     *
     * @return comments - 评论数
     */
    public Long getComments() {
        return comments;
    }

    /**
     * 设置评论数
     *
     * @param comments 评论数
     */
    public void setComments(Long comments) {
        this.comments = comments;
    }

    /**
     * 获取赞
     *
     * @return likes - 赞
     */
    public Long getLikes() {
        return likes;
    }

    /**
     * 设置赞
     *
     * @param likes 赞
     */
    public void setLikes(Long likes) {
        this.likes = likes;
    }

    /**
     * 获取踩
     *
     * @return dislikes - 踩
     */
    public Long getDislikes() {
        return dislikes;
    }

    /**
     * 设置踩
     *
     * @param dislikes 踩
     */
    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
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
     * 获取回答内容
     *
     * @return answer - 回答内容
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置回答内容
     *
     * @param answer 回答内容
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }
}