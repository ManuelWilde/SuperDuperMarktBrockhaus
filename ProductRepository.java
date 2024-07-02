package com.superdupi;

import java.util.List;

// Dieses Interface wird bentutzt um die initialen Daten aufzunehmen, und dann um das Regal abzubilden

public interface ProductRepository {

	List<Product> getAllProducts();

	Product getProductById(int id);

	void addProduct(Product product);

	void updateProduct(Product product);

	void removeProduct(int id);

	public void printAllPRoducts();

}