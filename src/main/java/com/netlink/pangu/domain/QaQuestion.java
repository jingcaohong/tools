package com.netlink.pangu.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "qa_question")
public class QaQuestion {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 问题分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 分类名称
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 提问人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 提问人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 浏览数
     */
    private Long views;

    /**
     * 回答数
     */
    private Long answers;

    /**
     * 点赞数
     */
    @Column(name = "thumb_up")
    private Long thumbUp;

    /**
     * 踩数
     */
    @Column(name = "thumb_down")
    private Long thumbDown;

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
     * 问题内容
     */
    private String question;

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
     * 获取问题分类ID
     *
     * @return category_id - 问题分类ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置问题分类ID
     *
     * @param categoryId 问题分类ID
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取分类名称
     *
     * @return category_name - 分类名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置分类名称
     *
     * @param categoryName 分类名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * 获取问题标题
     *
     * @return title - 问题标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置问题标题
     *
     * @param title 问题标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取提问人ID
     *
     * @return user_id - 提问人ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置提问人ID
     *
     * @param userId 提问人ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取提问人姓名
     *
     * @return user_name - 提问人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置提问人姓名
     *
     * @param userName 提问人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取浏览数
     *
     * @return views - 浏览数
     */
    public Long getViews() {
        return views;
    }

    /**
     * 设置浏览数
     *
     * @param views 浏览数
     */
    public void setViews(Long views) {
        this.views = views;
    }

    /**
     * 获取回答数
     *
     * @return answers - 回答数
     */
    public Long getAnswers() {
        return answers;
    }

    /**
     * 设置回答数
     *
     * @param answers 回答数
     */
    public void setAnswers(Long answers) {
        this.answers = answers;
    }

    /**
     * 获取点赞数
     *
     * @return thumb_up - 点赞数
     */
    public Long getThumbUp() {
        return thumbUp;
    }

    /**
     * 设置点赞数
     *
     * @param thumbUp 点赞数
     */
    public void setThumbUp(Long thumbUp) {
        this.thumbUp = thumbUp;
    }

    /**
     * 获取踩数
     *
     * @return thumb_down - 踩数
     */
    public Long getThumbDown() {
        return thumbDown;
    }

    /**
     * 设置踩数
     *
     * @param thumbDown 踩数
     */
    public void setThumbDown(Long thumbDown) {
        this.thumbDown = thumbDown;
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
     * 获取问题内容
     *
     * @return question - 问题内容
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 设置问题内容
     *
     * @param question 问题内容
     */
    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }
}