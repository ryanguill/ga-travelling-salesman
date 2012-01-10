package com;

import java.util.Properties;

public class Config {

	private int NUMBER_OF_CHROMOSOMES_PER_GENERATION = 5000; // population size
	private int NUMBER_OF_GENERATIONS_TO_RUN = 5000;
	private int DUPLICATE_LOCATION_SCORE_PENALTY = 5000;
	private int CHROMOSOME_LENGTH_MULTIPLIER = 6;
	private double CHROMOSOME_SHUFFLE_ODDS_MULTIPLIER = 2.0D;
	private int SUPER_INDIVIDUAL_BONUS_FIRST = 10000;
	private int SUPER_INDIVIDUAL_BONUS_SECOND = 5000;
	private int SUPER_INDIVIDUAL_BONUS_THIRD = 1000;
	private double FIRST_MUTATION_PERCENTAGE = 10.0D; // 10.0
	private double SECOND_MUTATION_PERCENTAGE = 7.0D; // 7.0
	private double THIRD_MUTATION_PERCENTAGE = 5.0D; // 5.0

	public Config(Properties propertyFile) {

		if (propertyFile != null) {
			Integer tempInt;
			Double tempDouble;

			// attempt to parse the property file.
			for (String key : propertyFile.stringPropertyNames()) {
				String value = propertyFile.getProperty(key);

				switch (ConfigPropertyKeys.valueOf(key)) {

				case NUMBER_OF_CHROMOSOMES_PER_GENERATION:

					try {

						tempInt = Integer.parseInt(value);

						if (tempInt <= 10000 && tempInt >= 50) {
							NUMBER_OF_CHROMOSOMES_PER_GENERATION = tempInt;
						}

					} catch (NumberFormatException e) {

					}

					break;

				case NUMBER_OF_GENERATIONS_TO_RUN:

					try {
						tempInt = Integer.parseInt(value);

						if (tempInt <= 1000000 && tempInt >= 50) {
							NUMBER_OF_GENERATIONS_TO_RUN = tempInt;
						}

					} catch (NumberFormatException e) {

					}

					break;
				case DUPLICATE_LOCATION_SCORE_PENALTY:

					try {
						tempInt = Integer.parseInt(value);

						if (tempInt <= 100000 && tempInt >= 0) {
							DUPLICATE_LOCATION_SCORE_PENALTY = tempInt;
						}
					} catch (NumberFormatException e) {

					}

					break;
				case CHROMOSOME_LENGTH_MULTIPLIER:

					try {
						tempInt = Integer.parseInt(value);

						if (tempInt <= 20 && tempInt >= 2) {
							CHROMOSOME_LENGTH_MULTIPLIER = tempInt;
						}
					} catch (NumberFormatException e) {

					}

					break;
				case SUPER_INDIVIDUAL_BONUS_FIRST:

					try {
						tempInt = Integer.parseInt(value);

						if (tempInt <= 100000 && tempInt >= 0) {
							SUPER_INDIVIDUAL_BONUS_FIRST = tempInt;
						}
					} catch (NumberFormatException e) {

					}

					break;
				case SUPER_INDIVIDUAL_BONUS_SECOND:

					try {
						tempInt = Integer.parseInt(value);

						if (tempInt <= 100000 && tempInt >= 0) {
							SUPER_INDIVIDUAL_BONUS_SECOND = tempInt;
						}
					} catch (NumberFormatException e) {

					}

					break;
				case SUPER_INDIVIDUAL_BONUS_THIRD:

					try {
						tempInt = Integer.parseInt(value);

						if (tempInt <= 100000 && tempInt >= 0) {
							SUPER_INDIVIDUAL_BONUS_THIRD = tempInt;
						}
					} catch (NumberFormatException e) {

					}

					break;

				case CHROMOSOME_SHUFFLE_ODDS_MULTIPLIER:

					try {
						tempDouble = Double.parseDouble(value);

						if (tempDouble <= 99.9D && tempDouble >= 0D) {
							CHROMOSOME_SHUFFLE_ODDS_MULTIPLIER = tempDouble;
						}
					} catch (NumberFormatException e) {

					}

					break;
				case FIRST_MUTATION_PERCENTAGE:

					try {
						tempDouble = Double.parseDouble(value);

						if (tempDouble <= 99.9D && tempDouble >= 0D) {
							FIRST_MUTATION_PERCENTAGE = tempDouble;
						}
					} catch (NumberFormatException e) {

					}

					break;
				case SECOND_MUTATION_PERCENTAGE:

					try {
						tempDouble = Double.parseDouble(value);

						if (tempDouble <= 99.9D && tempDouble >= 0D) {
							SECOND_MUTATION_PERCENTAGE = tempDouble;
						}
					} catch (NumberFormatException e) {

					}

					break;
				case THIRD_MUTATION_PERCENTAGE:

					try {
						tempDouble = Double.parseDouble(value);

						if (tempDouble <= 99.9D && tempDouble >= 0D) {
							THIRD_MUTATION_PERCENTAGE = tempDouble;
						}
					} catch (NumberFormatException e) {

					}

					break;
				}

			}
		}

	}

	public Properties createPropertiesFile() {

		Properties prop = new Properties();

		prop.setProperty(
				ConfigPropertyKeys.NUMBER_OF_CHROMOSOMES_PER_GENERATION
						.toString(), Integer
						.toString(NUMBER_OF_CHROMOSOMES_PER_GENERATION));
		prop.setProperty(
				ConfigPropertyKeys.NUMBER_OF_GENERATIONS_TO_RUN.toString(),
				Integer.toString(NUMBER_OF_GENERATIONS_TO_RUN));
		prop.setProperty(
				ConfigPropertyKeys.DUPLICATE_LOCATION_SCORE_PENALTY.toString(),
				Integer.toString(DUPLICATE_LOCATION_SCORE_PENALTY));
		prop.setProperty(
				ConfigPropertyKeys.CHROMOSOME_LENGTH_MULTIPLIER.toString(),
				Integer.toString(CHROMOSOME_LENGTH_MULTIPLIER));
		prop.setProperty(ConfigPropertyKeys.CHROMOSOME_SHUFFLE_ODDS_MULTIPLIER
				.toString(), Double
				.toString(CHROMOSOME_SHUFFLE_ODDS_MULTIPLIER));
		prop.setProperty(
				ConfigPropertyKeys.SUPER_INDIVIDUAL_BONUS_FIRST.toString(),
				Integer.toString(SUPER_INDIVIDUAL_BONUS_FIRST));
		prop.setProperty(
				ConfigPropertyKeys.SUPER_INDIVIDUAL_BONUS_SECOND.toString(),
				Integer.toString(SUPER_INDIVIDUAL_BONUS_SECOND));
		prop.setProperty(
				ConfigPropertyKeys.SUPER_INDIVIDUAL_BONUS_THIRD.toString(),
				Integer.toString(SUPER_INDIVIDUAL_BONUS_THIRD));
		prop.setProperty(
				ConfigPropertyKeys.FIRST_MUTATION_PERCENTAGE.toString(),
				Double.toString(FIRST_MUTATION_PERCENTAGE));
		prop.setProperty(
				ConfigPropertyKeys.SECOND_MUTATION_PERCENTAGE.toString(),
				Double.toString(SECOND_MUTATION_PERCENTAGE));
		prop.setProperty(
				ConfigPropertyKeys.THIRD_MUTATION_PERCENTAGE.toString(),
				Double.toString(THIRD_MUTATION_PERCENTAGE));

		return prop;
	}

	public int getNUMBER_OF_CHROMOSOMES_PER_GENERATION() {
		return NUMBER_OF_CHROMOSOMES_PER_GENERATION;
	}

	public int getNUMBER_OF_GENERATIONS_TO_RUN() {
		return NUMBER_OF_GENERATIONS_TO_RUN;
	}

	public int getDUPLICATE_LOCATION_SCORE_PENALTY() {
		return DUPLICATE_LOCATION_SCORE_PENALTY;
	}

	public int getCHROMOSOME_LENGTH_MULTIPLIER() {
		return CHROMOSOME_LENGTH_MULTIPLIER;
	}

	public double getCHROMOSOME_SHUFFLE_ODDS_MULTIPLIER() {
		return CHROMOSOME_SHUFFLE_ODDS_MULTIPLIER;
	}

	public int getSUPER_INDIVIDUAL_BONUS_FIRST() {
		return SUPER_INDIVIDUAL_BONUS_FIRST;
	}

	public int getSUPER_INDIVIDUAL_BONUS_SECOND() {
		return SUPER_INDIVIDUAL_BONUS_SECOND;
	}

	public int getSUPER_INDIVIDUAL_BONUS_THIRD() {
		return SUPER_INDIVIDUAL_BONUS_THIRD;
	}

	public double getFIRST_MUTATION_PERCENTAGE() {
		return FIRST_MUTATION_PERCENTAGE;
	}

	public double getSECOND_MUTATION_PERCENTAGE() {
		return SECOND_MUTATION_PERCENTAGE;
	}

	public double getTHIRD_MUTATION_PERCENTAGE() {
		return THIRD_MUTATION_PERCENTAGE;
	}

}
