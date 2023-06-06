package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Category;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Данный метод позволяет получить список всех товаров
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public List<Product> getSearchProduct(String search, String ot, String Do, String price, String contract, Model model){

      List<Product> search_products = new ArrayList<>();

      if(!ot.isEmpty() & !Do.isEmpty()){
        if(!price.isEmpty()){
            if(price.equals("sorted_by_ascending_price")) {
                if (!contract.isEmpty()) {
                    if (contract.equals("nutrition")) {
                      search_products = productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1);
                    } else if (contract.equals("appliances")) {
                      search_products = productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3);
                    } else if (contract.equals("clothes")) {
                      search_products = productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2);
                    }
                } else {
                  search_products = productRepository.findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do));
                }
            } else if(price.equals("sorted_by_descending_price")){
                if(!contract.isEmpty()){
                    System.out.println(contract);
                    if(contract.equals("nutrition")){
                      search_products = productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1);
                    }else if (contract.equals("appliances")) {
                      search_products = productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3);
                    } else if (contract.equals("clothes")) {
                      search_products = productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2);
                    }
                }  else {
                  search_products = productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do));
                }
            }
        } else {
          search_products = productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do));
        }
      } else {
        search_products = productRepository.findByTitleContainingIgnoreCase(search);
      }
      return search_products;
    }

    // Данный метод позволяет получить товар по id
    public Product getProductId(int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    // Данный метод позволяет сохранить товар
    @Transactional
    public void saveProduct(Product product, Category category){
        product.setCategory(category);
        productRepository.save(product);
    }

    // Данный метод позволяет обновить данные о товаре
    @Transactional
    public void updateProduct(int id, Product product){
        product.setId(id);
        productRepository.save(product);
    }

    // Данный метод позволяет удалить товар по id
    @Transactional
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }
}
