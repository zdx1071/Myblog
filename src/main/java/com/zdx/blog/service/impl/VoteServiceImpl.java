package com.zdx.blog.service.impl;

import com.zdx.blog.domain.Vote;
import com.zdx.blog.repository.VoteRepository;
import com.zdx.blog.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class VoteServiceImpl implements VoteService{
    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findOne(id);
    }

    @Transactional
    @Override
    public void removeVote(Long id) {
        voteRepository.delete(id);
    }
}
