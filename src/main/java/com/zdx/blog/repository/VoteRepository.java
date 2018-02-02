package com.zdx.blog.repository;

import com.zdx.blog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Long>{
}
