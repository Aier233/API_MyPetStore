package org.csu.mypetstore.api;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.csu.mypetstore.api.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("org.csu.mypetstore.api.persistence")
class MyPetStoreApiApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CatalogService catalogService;

    @Test
    void contextLoads() {
    }

    @Test
    void selectAllCategoryListTest(){
        //List<Category> categoryList = categoryMapper.selectList(null);
        //System.out.println(categoryList);//由于Lombok生成了toString方法，才能使用
        //categoryList.forEach(System.out::println);

        CommonResponse<List<Category>> categoryList = catalogService.getCategoryList();
        System.out.println(categoryList.toString());
    }

    @Test
    void selectCategoryByIdTest(){
        Category category = categoryMapper.selectById("CATS");
        System.out.println(category);
    }

}
