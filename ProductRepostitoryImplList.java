package com.superdupi;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//Die KLasse wird verwendet um das Interface ProductRepository mit Hilfe einer Liste umzusetzen

public class ProductRepostitoryImplList implements ProductRepository {

	CopyOnWriteArrayList<Product> listOfProduct = new CopyOnWriteArrayList<Product>();

	@Override
	public List<Product> getAllProducts() {

		return listOfProduct;
	}

	@Override
	public Product getProductById(int id) {
		for (Product product : listOfProduct) {
			if (product.getId() == id) {
				return product;
			}
		}
		return null; // or throw an exception if not found
	}

	@Override
	public void addProduct(Product product) {
		listOfProduct.add(product);
	}

	@Override
	public void updateProduct(Product product) {
		// TODO
	}

	@Override
	public void removeProduct(int id) {
		for (Product product : listOfProduct) {
			if (product.getId() == id) {

				String name = product.getName();

				String output = String.format("Produkt %s mit der ID %s und dem wird aus dem Lagerbestand entfernt",
						name, id);
				System.out.println(output);

				listOfProduct.remove(product);
			} else {

				// Es muss nichts getan werden

			}
		}
	}

	public void printAllPRoducts() {

		System.out.println("Die folgenden Produkte wurden angeliefert und sind im Lager.");

		for (Product product : listOfProduct) {

			String name = product.getName();
			ProductType type = product.getType();
			int quality = product.getQuality();
			double basePrice = product.getBasePrice();
			Date expirationDate = product.getExpirationDate();
			int id = product.getId();

			String output = String.format(
					"ProduktName = %s ist vom Produkttyp %s und hat die ID %s sowie Qualität =%s, grundPreis = %s, Haltbarkeitsdatum = %s",
					type.toString(), name, String.valueOf(id), String.valueOf(quality), String.valueOf(basePrice),
					expirationDate.toString());

			System.out.println(output);

		}
		System.out.println("Die oben genannten Produkte wurden angeliefert und sind im Lager.");

	}
}
