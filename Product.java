package com.superdupi;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

// Diese Klasse wird verwendet um die einzelnen Produkte mit ihren Eigenschaften abzubilden

public class Product {

	private String name;
	private int quality;
	private double basePrice;
	private double currentPrice;
	private ProductType type;
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	private int id;
	private Date expirationDate;
	private boolean sortArticleOut = false;
	private int discount;
	private boolean productOtTheShelf = false;
	private boolean setOnTheShelf = false;
	private Date dateProductIsSetOnTheSHelf;

	public Product(String name, int quality, double price, Date expirationDate, ProductType type)

	{
		this.setName(name);
		this.setBasePrice(price);
		this.setExpirationDate(expirationDate);
		this.setType(type);
		this.setQuality(quality);
		setId(ID_GENERATOR.getAndIncrement());

	}

	public void setQuality(int quality) {

		this.quality = quality;

	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;

		Date today = new Date();

		if (today.after(expirationDate)) {
			setSortArticleOut(true);

		} else {
			// setSortArticleOut(false);

		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getDateProductIsSetOnTheSHelf() {
		return dateProductIsSetOnTheSHelf;
	}

	public void setDateProductIsSetOnTheSHelf(Date dateProductIsSetOnTheSHelf) {
		this.dateProductIsSetOnTheSHelf = dateProductIsSetOnTheSHelf;
	}

	public boolean isProductOtTheShelf() {
		return productOtTheShelf;
	}

	public void setProductOtTheShelf(boolean productOtTheShelf) {
		this.productOtTheShelf = productOtTheShelf;
	}

	public boolean isSetOnTheShelf() {
		return setOnTheShelf;
	}

	public void setSetOnTheShelf(boolean setOnTheShelf) {
		this.setOnTheShelf = setOnTheShelf;
	}

	public boolean isSortArticleOut() {
		return sortArticleOut;
	}

	public void setSortArticleOut(boolean sortArticleOut) {
		this.sortArticleOut = sortArticleOut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuality() {
		return quality;
	}

	public double getBasePrice() {
		return basePrice;

	}

	public void setBasePrice(double price) {
		this.basePrice = price;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

}
