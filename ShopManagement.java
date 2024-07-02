package com.superdupi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Diese Klasse enthält die Geschäftslogik

public class ShopManagement {
	ProductRepository repository = null;

	public ShopManagement(ProductRepository repository) {
		this.repository = repository;
	}

	public void run() {

		repository.printAllPRoducts();

		// Am Tag des Wareneingangs

		// Gehe alle Produkte im Wareneingang durch

		List<Product> allProductsInTheWarehouse = repository.getAllProducts();

		Date today = new Date();

		for (Product product : allProductsInTheWarehouse) {

			// Prüfe Haltbarkeitsdatum und entferne Produkte die das Datum überschritten
			// haben
			checkExpirationDateAndRemoveExpiredGoods(today, product, true);

			if (product.getType().equals(ProductType.CHEESE)) {

				// Es ist noch zu prüfen ob bei Käse das Haltbarkeitsdatum in dem versprochenen
				// Bereich liegt, falls nicht ist der Käse auch nicht einzuräumen

				// Käse hat ein Verfallsdatum das beim Einräumen 50 bis 100 Tage in der Zukunft
				// liegt -> Sonst Warnung rausgeben

				// Es wird davon ausgegangen, dass die Qualität immer nur als ganzzahliger Wert
				// angegeben wird

				if (product.getQuality() >= 29) {

					product.setProductOtTheShelf(true);

				} else {
					repository.removeProduct(product.getId());

				}
			}
			// Berechnung des initialen Preises

			calculateAndSetCurrentPrice(product);

		}
		// Immer ein Tag weiter simulieren solange Produkte im Regal sind
		simulateNextDaysUntilRepositoryIsEmpty(getTheNextDay(today), repository.getAllProducts());

	}

	private void simulateNextDaysUntilRepositoryIsEmpty(Date day, List<Product> list) {

		if (!list.isEmpty()) {

			for (Product product : list) {

				// 1. Prüfe Haltbarkeitsdatum und entferne Produkte die das Datum überschritten
				// haben

				checkExpirationDateAndRemoveExpiredGoods(day, product, false);

				// 2.Berechne die Qualität für Wein und Käse

				calulateQualityForWineAndCheese(day, product);

				if (product.getType().equals(ProductType.CHEESE)) {

					checkQualityForCheeseAndRemoveIfQualityIsUnder30(product);

				}

				// 3. Bestimme für die im Regal bleibenden Produkten den Tagespreis
				// Dies wird zur Zeit nur für Käse gemacht

				if (product.getType().equals(ProductType.CHEESE)) {

					// Neuberechnung des Preises für Käse
					if (product.getQuality() > 29) {
						calculateAndSetCurrentPrice(product);
					}

				}

			}

			// Call the function recursively with the next day and the updated list
			simulateNextDaysUntilRepositoryIsEmpty(getTheNextDay(day), list);

		} else {
			return; // Base case / stop recursing
		}
	}

	private void calculateAndSetCurrentPrice(Product product) {

		double basePrice = product.getBasePrice();

		product.setCurrentPrice(basePrice + (product.getQuality() * 0.1));

		String output = String.format("Der Preis für %s mit ID %s wurde auf %s festgelegt", product.getName(),
				String.valueOf(product.getId()), String.valueOf(product.getCurrentPrice()));
		System.out.println(output);

	}

	private void checkQualityForCheeseAndRemoveIfQualityIsUnder30(Product product) {

		if (product.getQuality() < 30)

		{
			repository.removeProduct(product.getId());
		}

	}

	private void calulateQualityForWineAndCheese(Date day, Product product) {

		if (product.getType().equals(ProductType.CHEESE))

		{
			product.setQuality(product.getQuality() - 1);
			String output = String.format(
					"Die Qualität des Käse mit dem Namen %s und der ID %s ist um 1 gesunken und ist jetzt %s",
					product.getName(), String.valueOf(product.getId()), String.valueOf(product.getQuality()));
			System.out.println(output);

		}

		if (product.getType().equals(ProductType.WINE)) {

			long diff = day.getTime() - product.getDateProductIsSetOnTheSHelf().getTime();

			long milliSecondsTenDays = 10 * 24 * 60 * 60 * 1000;

			if (diff % milliSecondsTenDays == 0) { // 10 days in milliseconds = 864 000 000

				int quality = product.getQuality();

				if (quality < 50) {
					product.setQuality(quality + 1);
					String output = String.format(
							"Die Qualität des Weins mit dem Namen %s und der ID %s war %s und ist jetzt um 1 gestiegen und ist jetzt %s",
							product.getName(), String.valueOf(product.getId()), String.valueOf(quality),
							String.valueOf(product.getQuality()));
					System.out.println(output);

				}
			}
		}

	}

	private Date getTheNextDay(Date today) {
		Instant instant = today.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate plusOneDayLocalDate = localDate.plusDays(1);
		Instant instant2 = plusOneDayLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date plusOneDay = Date.from(instant2);

		System.out.println("********************************************************");
		String output = String.format("In unser Simulation haben wir jetzt den %s", plusOneDay.toString());
		System.out.println(output);

		return plusOneDay;
	}

	private Date getTheDayBefore(Date today) {
		Instant instant = today.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate plusOneDayLocalDate = localDate.minusDays(1);
		Instant instant2 = plusOneDayLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date plusOneDay = Date.from(instant2);

		return plusOneDay;
	}

	private void checkExpirationDateAndRemoveExpiredGoods(Date date, Product product, boolean initialRun) {
		Date expirationDate = product.getExpirationDate();
		String name = product.getName();
		int id = product.getId();

		if (expirationDate.before(date) || expirationDate.equals(date)) {
			// Produkt nicht weiter haltbar
			if (initialRun) {
				System.out.println("**************************************************************");

				String output = String.format(
						"Das Produkt %s mit dem Namen und der ID %s wird nicht ins Regal eingeräumt und wird aus dem Repository entfernt",
						name, String.valueOf(id));
				System.out.println(output);
			} else {
				String stringDate = date.toString();

				String output = String.format(
						"Das Produkt %s mit dem Namen und der ID %s muss am %s aus dem Regal entfernt werden und wird aus dem Repository entfernt",
						name, String.valueOf(id), stringDate);
				System.out.println(output);
			}
			repository.removeProduct(product.getId());
		} else {
			// Alles Ok Produkt weiter haltbar
			if (initialRun) {
				// kleine Korrektur um den Abstand von zwei Tagen gerade zu berechen
				// TODO Alternative suchen
				Date theNextDay = getTheNextDay(date);
				Date thisDay = getTheDayBefore(theNextDay);

				product.setDateProductIsSetOnTheSHelf(thisDay);

				// Wenn das Haltbarkeitsdatum für Käse nicht 50 - 100 Tage weiter ist als
				// das Datum heute, tue den Käse nicht ins Regal

				if (product.getType().equals(ProductType.CHEESE)) {

					Date today = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(today);

					// Add 50 days
					cal.add(Calendar.DAY_OF_YEAR, 50);
					Date fiftyDaysAhead = cal.getTime();

					// Add 100 days
					cal.add(Calendar.DAY_OF_YEAR, 50); // Add another 50 days to get 100 days ahead
					Date hundredDaysAhead = cal.getTime();

					if (expirationDate.after(fiftyDaysAhead) && expirationDate.before(hundredDaysAhead)) {

						// Alles ok

					} else {
						String output = String.format(
								"Der Käse mit dem Namen %s und der ID %s und dem Haltbarkeitsdatum %s wird nicht ins Regal gestellt. Der Grund hierfür ist, dass das Haltbarkeitsdatum nicht im vorgegebenen Rahmen zwischen 50 und 100 Tagen in der Zunkunft liegt..",
								name, String.valueOf(id), product.getExpirationDate().toString());
						System.out.println(output);

						repository.removeProduct(product.getId());
					}

				}
			}
		}
	}

}
