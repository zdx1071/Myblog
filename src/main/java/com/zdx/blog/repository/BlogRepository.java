package com.zdx.blog.repository;

import com.zdx.blog.domain.Blog;
import com.zdx.blog.domain.Catalog;
import com.zdx.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Blog,Long>{

    /**
     * 根据用户名分页查询用户列表（最新）
     * 由 findByUserAndTitleLikeOrTagsLikeOrderByCreateTimeDesc 代替，可以根据标签进行查询
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    //@Deprecated
    //Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    Page<Blog> findByUserAndTitleLike(User user,String title,Pageable pageable);

    //这里不懂
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title,User user,String tags,User user2,Pageable pageable);

    Page<Blog> findByCatalog(Catalog catalog,Pageable pageable);


}
