package com.xiaojian.pick.controller;

import com.xiaojian.pick.entity.Category;
import com.xiaojian.pick.entity.Commodity;
import com.xiaojian.pick.page.AjaxResult;
import com.xiaojian.pick.service.CategoryService;
import com.xiaojian.pick.util.OSSClientUtil;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author 小贱
 * @date 2020/10/14 - 18:18
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

/**
 * 跳转到商品分类列表页面
 */
    @RequestMapping("/toCategory")
    public String toSeek(){
        return "category/category";
    }
/**
 * 返回分页商品分类列表
 */
    @RequestMapping("/categoryList")
    @ResponseBody
    public Map<String,Object> commodityList(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "limit", required = false) Integer pageSize){
        // 返回数据
        Map<String,Object> result = new HashMap<>();
        List<Category> categoryList = categoryService.findByPage(page,pageSize);

        result.put("data",categoryList);
        result.put("count",categoryService.getCount());
        result.put("code",0);

        return result;
    }

/**
 * 删除该分类信息
 */
    @PostMapping("/delCategory")
    @ResponseBody
    public AjaxResult delCategory(Integer id){

        AjaxResult result = null;
        int count = 0;
        try{
            count = categoryService.deleteCategory(id);
            if(count > 0){
                result = new AjaxResult(true,"删除成功!");
            } else{
                result = new AjaxResult(false,"删除失败，请联系站长!");
            }
        } catch(Exception e){
            result = new AjaxResult(false,"删除失败，多半是该分类下还有商品在出售！");
        }


        return result;
    }

/**
 * 跳转到添加分类信息页面
 */
    @RequestMapping("/toAddCategory")
    public String toAddCategory(){
        return "category/addCategory";
    }

/**
 * 添加分类信息
 */
    @PostMapping("/addCategory")
    @ResponseBody
    public AjaxResult addCategory(@RequestParam("cateName") String cateName,@RequestParam("sort") Integer sort,@RequestParam("iconFile") MultipartFile multipartFile){
        Category category = new Category();
        category.setCateName(cateName);
        category.setSort(sort);

        AjaxResult result = null;
        // 上传图片到 阿里云 OSS
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        ossClientUtil.setHomeimagedir("admin/icons/");
        try {
            if(category != null && multipartFile != null){
                String img_src = ossClientUtil.uploadImageAndGetPath(multipartFile,false);
                category.setCateIcon(img_src);
                int count = categoryService.addCategory(category);
                if(count > 0){
                    result = new AjaxResult(true,"添加成功！");
                } else{
                    result = new AjaxResult(false,"上传失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false,"图片上传失败！");
        }

        return result;
    }

/**
 * 跳转到修改分类信息页面
 */
    @RequestMapping("/toUpdateCategory/{categoryId}")
    public ModelAndView toUpdateCategory(@PathVariable("categoryId")Integer categoryId){
        ModelAndView mav = new ModelAndView();
        Category category = categoryService.findById(categoryId);

        if(category == null){
            mav.addObject("errorMsg","数据请求失败！");
        } else{
            mav.addObject("category",category);
        }

        mav.setViewName("category/updateCategory");
        return mav;
    }
/**
 * 修改分类信息
 */
    @PostMapping("/updateCategory")
    @ResponseBody
    public AjaxResult updateCategory(@RequestParam("id")Integer id, @RequestParam("cateName") String cateName,
                                     @RequestParam("sort") Integer sort,@RequestParam("iconFile") MultipartFile multipartFile){
        Category category = new Category();
        category.setId(id);
        category.setCateName(cateName);
        category.setSort(sort);

        AjaxResult result = null;
        // 上传图片到 阿里云 OSS
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        ossClientUtil.setHomeimagedir("admin/icons/");
        try {
            if(category != null && multipartFile != null){
                String img_src = ossClientUtil.uploadImageAndGetPath(multipartFile,false);
                category.setCateIcon(img_src);
                int count = categoryService.updateCategory(category);
                if(count > 0){
                    result = new AjaxResult(true,"修改成功！");
                } else{
                    result = new AjaxResult(false,"修改失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new AjaxResult(false,"图片上传失败！");
        }

        return result;
    }





/**
 * 返回到前端的数据
 */
/**
 * 返回所有分类列表
 */
    @GetMapping("/getAllCategory")
    @ResponseBody
    public List<Category> getAllCategory(){
        return categoryService.findAll();
    }


}
