package com.zdx.blog.service;

import com.zdx.blog.domain.Comment;

public interface CommentService {

    Comment getCommentById(Long id);

    void removeComment(Long id);
}
