package com.superdupi;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ShopManagementApplication {

	// Diese Klasse wird benutzt um die Applikation laufen zu lassen

	public static void main(String[] args) {

		// read the CSV Data with products and load the data in the product Repository
		ProductRepository repository = getProductDataFromCSV();

		if (repository != null) {

			// run the ShopManagement

			ShopManagement management = new ShopManagement(repository);
			management.run();

		}
	}

	public static ProductRepository getProductDataFromCSV() {

		ProductRepostitoryImplList repository = new ProductRepostitoryImplList();

		try (CSVParser parser = CSVFormat.DEFAULT.parse(new FileReader("supermarket_test_data.csv"))) {
			for (CSVRecord record : parser) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.parse(record.get(3), formatter);
				Date date = new java.util.Date(localDate.toEpochDay() * 86400000L);

				Product product = new Product(record.get(0), Integer.parseInt(record.get(1)),
						Double.parseDouble(record.get(2)), date, ProductType.valueOf(record.get(4)));

				repository.addProduct(product);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return repository;

	}

}
