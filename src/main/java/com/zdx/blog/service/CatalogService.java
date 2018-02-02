package com.zdx.blog.service;

import com.zdx.blog.domain.Catalog;
import com.zdx.blog.domain.User;

import java.util.List;

public interface CatalogService {

    Catalog saveCatalog(Catalog catalog);

    void removeCatalog(Long id);

    Catalog getCatalogById(Long id);

    List<Catalog> listCatalogs(User user);
}
