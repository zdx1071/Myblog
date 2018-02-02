package com.zdx.blog.repository;

import com.zdx.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>{

    /**
     * 根据名称/账号查找
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名分页查询列表
     * @param name
     * @param pageable
     * @return
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据名称列表查找
     * @param usernames
     * @return
     */
    List<User> findByUsernameIn(Collection<String> usernames);
}
