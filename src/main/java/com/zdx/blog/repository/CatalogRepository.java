package com.zdx.blog.repository;

import com.zdx.blog.domain.Catalog;
import com.zdx.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogRepository extends JpaRepository<Catalog,Long>{

    //根据用户查询
    List<Catalog> findByUser(User user);

    //根据用户和catalog name查询
    List<Catalog> findByUserAndName(User user,String name);
}
