package com.zdx.blog.service;

import com.zdx.blog.domain.Vote;

public interface VoteService {

    Vote getVoteById(Long id);

    void removeVote(Long id);
}
