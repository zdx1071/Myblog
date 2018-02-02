package com.zdx.blog.service;

import com.zdx.blog.domain.Blog;
import com.zdx.blog.domain.Catalog;
import com.zdx.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog saveBlog(Blog blog);

    void removeBlog(Long id);

    Blog getBlogById(Long id);

    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    Page<Blog> listBlogsByTitleVoteAndSort(User user,String title,Pageable pageable);

    Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 阅读量递增
     */
    void readingIncrease(Long id);

    /**
     * 发表评论
     */
    Blog createComment(Long blogId,String commentContent);

    /**
     * 删除评论
     */
    void removeComment(Long blogId,Long commentId);

    /**
     * 点赞
     */
    Blog createVote(Long blogId);

    /**
     * 取消点赞
     */
    void removeVote(Long blogId,Long voteId);
}
