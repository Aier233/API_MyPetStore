package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.vo.ItemVO;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Product;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CatalogService {
    CommonResponse<List<Category>> getCategoryList();
    CommonResponse<Category> getCategoryById(@PathVariable("id")String categoryId);
    CommonResponse<List<Product>> getProductListByCategoryId(@PathVariable("id")String categoryId);
    CommonResponse<Product> getProductById(@PathVariable("id")String productId);
    CommonResponse<List<ItemVO>> getItemListByProductId(@PathVariable("id")String productId);
    CommonResponse<ItemVO> getItemById(@PathVariable("id")String itemId);

    boolean isItemInStock(String itemId);
    ItemVO getItem(String itemId);
}
