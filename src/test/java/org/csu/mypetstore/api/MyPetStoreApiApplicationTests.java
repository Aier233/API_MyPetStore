package org.csu.mypetstore.api;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.service.OrderService;
import org.csu.mypetstore.api.service.UserService;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.OrderVO;
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

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

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

    @Test
    void loginTest(){
         CommonResponse<User> userCommonResponse = userService.getAccountByUsernameAndPassword("123", "68aee686fd46d0dd10c32250ed63ffa2");
        System.out.println(userCommonResponse.getData().toString());
    }


    @Test
    void selectItemByUsernameTest(){
        CommonResponse<List<CartItemVO>> listCommonResponse = cartService.selectItemByUsername("123");
        System.out.println(listCommonResponse.getData());
    }

    @Test
    void getOrderVOTest(){
        OrderVO orderVO = orderService.getOrderVO("1001");
        System.out.println(orderVO.toString());

    }

}
