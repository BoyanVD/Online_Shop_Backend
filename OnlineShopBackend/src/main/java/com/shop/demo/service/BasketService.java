package com.shop.demo.service;


import com.shop.demo.exception.*;
import com.shop.demo.model.entity.Basket;
import com.shop.demo.model.entity.BasketProduct;
import com.shop.demo.model.entity.Product;
import com.shop.demo.model.entity.User;
import com.shop.demo.repository.BasketProductRepository;
import com.shop.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasketService {

	private final BasketProductRepository basketProductRepository;
	private final ProductRepository productRepository;

	@Autowired
	public BasketService(BasketProductRepository basketProductRepository,
											 ProductRepository productRepository) {
		this.basketProductRepository = basketProductRepository;
		this.productRepository = productRepository;
	}


	/**
	 * The method adds product to basket
	 * @param user
	 * @param quantity
	 * @param productId
	 */
	public void addProductToBasket(User user, Integer quantity, Long productId) throws NoSuchProductException, NotEnoughQuantityException {
		Product product = productRepository.findProductById(productId);
		if(product == null){
			throw new NoSuchProductException("There is no such product !!!");
		}
		if(product.getQuantity() < quantity){
			throw new NotEnoughQuantityException("There is not enough quantity from product to add to basket !!!");
		}
		BasketProduct basketProduct = new BasketProduct();
		basketProduct.setBasket(user.getBasket());
		basketProduct.setProduct(product);
		basketProduct.setQuantity(quantity);

		basketProductRepository.save(basketProduct);
	}

	public void removeProductFromUserBasket(Long productId, Basket basket) throws NoSuchProductException {

		BasketProduct basketProduct = basketProductRepository.findBasketProductByBasket_IdAndAndProduct_Id(basket.getId(), productId);
		if(basketProduct == null){
			throw new NoSuchProductException("No such product in user basket !!!");
		}
		basketProductRepository.delete(basketProduct);
	}
	
}
