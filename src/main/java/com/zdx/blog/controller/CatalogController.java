package com.zdx.blog.controller;

import com.zdx.blog.domain.Catalog;
import com.zdx.blog.domain.User;
import com.zdx.blog.service.CatalogService;
import com.zdx.blog.util.ConstraintViolationExceptionHandler;
import com.zdx.blog.vo.CatalogVO;
import com.zdx.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Controller
@RequestMapping("/catalogs")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    //这个是？？
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 获取分类列表
     */
    @GetMapping
    public String listComments(@RequestParam(value = "username",required = true) String username,Model model){
        //看一下这个方法，loadUserByUsername
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);
        //判断操作用户是否是分类的所有者
        boolean isOwner = false;

        //这里好好看一下，判断的条件比较复杂
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }
            model.addAttribute("isCatalogsOwner",isOwner);
            model.addAttribute("catalogs",catalogs);
            return "/userspace/u :: #catalogRepleace";
    }

    /**
     * 发表分类
     */
    @PostMapping
    //这个注解看一下
    @PreAuthorize("authentication.name.equals(#catalogVO.username)")    //指定用户才能操作方法
    public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO){
        String username = catalogVO.getUsername();
        Catalog catalog = catalogVO.getCatalog();

        User user = (User) userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e) {
            //源码看一下
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true,"处理成功",null));
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name.equals(#username)")  //这里怎么就不用
    public ResponseEntity<Response> delete(String username,@PathVariable("id") Long id){
        try {
            catalogService.removeCatalog(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false,ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true,"处理成功",null));
    }

    /**
     * 获取分类编辑页面
     */
    @GetMapping("/edit")
    public String getCatalogEdit(Model model){
        Catalog catalog = new Catalog(null,null);
        model.addAttribute("catalog",catalog);
        return "/userspace/catalogedit";
    }

    /**
     * 根据Id获取分类信息
     */
    @GetMapping("/edit/{id}")
    public String getCatalogById(@PathVariable("id") Long id,Model model){
        Catalog catalog = catalogService.getCatalogById(id);
        model.addAttribute("catalog",catalog);
        return "/userspace/catalogedit";
    }

}
