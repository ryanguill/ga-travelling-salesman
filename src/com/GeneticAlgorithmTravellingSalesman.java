package com;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

import static com.Chromosome.randRange;
import static com.ZipLocation.getLatLongDistance;

public class GeneticAlgorithmTravellingSalesman {

	private Config config;

	private ArrayList<ZipLocation> zipLocations = new ArrayList<ZipLocation>();
	private ArrayList<ZipLocation> allZipLocations = new ArrayList<ZipLocation>();
	private ArrayList<Chromosome> population = new ArrayList<Chromosome>();

	private int currentGeneration = 0;
	private int mutatedCount = 0;
	private int notMutatedCount = 0;
	private double bestScore = 1000000000D;
	private double bestAverageScore = 1000000000D;
	private int mostLocationsFound = 0;
	private double leastTotalDistance = 1000000000D;

	private double mutationRate = 0D;
	private double currentAverageScore = 0D;
	private double currentAverageLocationsFound = 0;
	private Chromosome bestIndividual;
	private int bestIndividualGeneration = 0;
	// private Chromosome generationBestIndividual;
	private boolean isRunning = true;
	// private int skyFoundCount = 0;
	// private int netFoundCount = 0;
	private int skynetFoundCount = 0;

	double averageGenTimeMS = 0D;

	private NumberFormat nf = NumberFormat.getInstance();

	public GeneticAlgorithmTravellingSalesman(Config config) {
		allZipLocations.add(new ZipLocation("37501", "Memphis", "TN", 35.15, -90.05, "AAA"));
		allZipLocations.add(new ZipLocation("90210", "Beverly Hills", "CA", 34.088808, -118.40612, "BBB"));
		allZipLocations.add(new ZipLocation("89101", "Las Vegas", "NV", 36.17208, -115.12237, "CCC"));
		allZipLocations.add(new ZipLocation("98102", "Seattle", "WA", 47.63287, -122.32253, "DDD"));
		allZipLocations.add(new ZipLocation("80201", "Denver", "CO", 39.75, -104.99, "EEE"));
		allZipLocations.add(new ZipLocation("60652", "Chicago", "IL", 41.7446, -87.71188, "FFF"));
		allZipLocations.add(new ZipLocation("04101", "Portland", "ME", 43.6606, -70.2589, "GGG"));
		allZipLocations.add(new ZipLocation("11256", "Brooklyn", "NY", 40.645099, -73.945032, "HHH"));
		allZipLocations.add(new ZipLocation("23294", "Richmond", "VA", 37.633316, -77.54775, "III"));
		allZipLocations.add(new ZipLocation("32801", "Orlando", "FL", 28.53988, -81.37267, "JJJ"));
		allZipLocations.add(new ZipLocation("83701", "Boise", "ID", 43.622, -116.322, "KKK"));
		allZipLocations.add(new ZipLocation("55199", "Minneapolis", "MN", 44.943, -93.093, "LLL"));
		allZipLocations.add(new ZipLocation("78201", "San Antonio", "TX", 29.4685, -98.5264, "MMM"));
		allZipLocations.add(new ZipLocation("70112", "New Orleans", "LA", 29.956, -90.079, "NNN"));
		allZipLocations.add(new ZipLocation("15122", "Pittsburgh", "PA", 40.366, -79.897, "OOO"));
		allZipLocations.add(new ZipLocation("59101", "Billings", "MT", 45.774, -108.500, "PPP"));
		allZipLocations.add(new ZipLocation("58501", "Bismark", "ND", 46.823, -100.774, "QQQ"));
		allZipLocations.add(new ZipLocation("87500", "Santa Fe", "NM", 35.791, -105.993, "RRR"));
		allZipLocations.add(new ZipLocation("29401", "Charleston", "SC", 32.7795, -79.937, "SSS"));
		allZipLocations.add(new ZipLocation("94101", "San Francisco", "CA", 37.78, -122.42, "TTT"));

		zipLocations = allZipLocations;

		nf.setMaximumFractionDigits(4);
		nf.setGroupingUsed(false);

		this.config = config;
	}

	public void commenceAlgorithm() {

		// get the first generation to get us started.
		generateFirstGenerationChromosomes();
		currentGeneration++;

		// score the generation
		for (Chromosome c : population) {
			scoreChromosomeFitness(c);
		}

		long totalTime = 0L;

		while (currentGeneration < config.getNUMBER_OF_GENERATIONS_TO_RUN() && isRunning) {
			long startTime = System.nanoTime();
			runGeneration();
			long endTime = System.nanoTime();
			long generationTime = endTime - startTime;
			totalTime += generationTime;
			averageGenTimeMS = (double) (totalTime / currentGeneration) / 1000000;
			long totalTimeSeconds = totalTime / 1000000000;

			out("Gen's time: " + nf.format((double) generationTime / 1000000) + "ms. / Avg Gen Time: " + nf.format(averageGenTimeMS) + "ms. / Total Time: "
					+ timeFormat(totalTimeSeconds));
		}

		// print out the best individual
		out(bestIndividual.phenotypeToString());

		ArrayList<Gene> phenotype = bestIndividual.getPhenotype();

		String solution = "";
		boolean firstCity = true;

		for (Gene g : phenotype) {

			if (!firstCity)
				solution += " - \n";

			locsLoop: for (ZipLocation z : zipLocations) {
				if (g.getValue().equals(z.getId())) {
					solution += z.getCity() + " " + z.getState();
					break locsLoop;
				}
			}

			firstCity = false;
		}

		out("best solution found: " + nf.format(bestIndividual.getLocationsFound()) + " locations / " + nf.format(bestIndividual.getTotalDistance())
				+ " Total Distance / " + nf.format(bestIndividual.getFitnessScore()) + " fitness score. / Gen Found: " + bestIndividualGeneration);
		out(solution);
		if (skynetFoundCount > 0) {
			out("Skynet found " + skynetFoundCount + " times.");
		}

	}

	public void runGeneration() {

		currentGeneration++;

		if (mutatedCount + notMutatedCount > 0)
			mutationRate = (double) mutatedCount / (mutatedCount + notMutatedCount);
		else
			mutationRate = 0;

		ArrayList<Chromosome> matingPopulation = selectChromosomesForMating(population);

		population = mateTopChromosomes(matingPopulation);

		double totalScoreForGeneration = 0D;
		int totalLocationsFound = 0;
		double genBestScore = 0;
		int mostGenerationLocationsFound = 0;

		boolean bestEverIndividual = false;

		for (Chromosome c : population) {
			scoreChromosomeFitness(c);
			totalScoreForGeneration += c.getFitnessScore();
			totalLocationsFound += c.getLocationsFound();

			if (c.getFitnessScore() < bestScore) {
				bestScore = c.getFitnessScore();
				bestIndividual = c;

				bestEverIndividual = true;
				bestIndividualGeneration = currentGeneration;
			}

			if (c.getFitnessScore() < genBestScore) {
				genBestScore = c.getFitnessScore();
				// generationBestIndividual = c;
			}

			if (c.getLocationsFound() > mostLocationsFound) {
				mostLocationsFound = c.getLocationsFound();
			}

			if (c.getLocationsFound() > mostGenerationLocationsFound) {
				mostGenerationLocationsFound = c.getLocationsFound();
			}

			if (c.getTotalDistance() < leastTotalDistance) {
				leastTotalDistance = c.getTotalDistance();
			}

		}

		currentAverageScore = (double) totalScoreForGeneration / population.size();

		if (currentAverageScore < bestAverageScore) {
			bestAverageScore = currentAverageScore;
		}

		currentAverageLocationsFound = (double) totalLocationsFound / population.size();

		out("---------------------------------------------------------------");
		long timeRemainingSeconds = (long) ((config.getNUMBER_OF_GENERATIONS_TO_RUN() - currentGeneration) * averageGenTimeMS) / 1000;

		out("Gen: " + currentGeneration + "/" + config.getNUMBER_OF_GENERATIONS_TO_RUN() + " - "
				+ nf.format(((double) currentGeneration / config.getNUMBER_OF_GENERATIONS_TO_RUN()) * 100) + "%" + " - Est. Time Remaining: "
				+ timeFormat(timeRemainingSeconds));
		if (bestEverIndividual) {
			out("Best Ever Individual Found! Score: " + bestScore);
			out("Best Individual: " + bestIndividual.toString());
		}
		out("Mutation Rate: " + nf.format(mutationRate * 100) + "%");
		// out("Most Locations Found: " + mostLocationsFound);

		// out("Least Total Distance: " + leastTotalDistance);

		double currentPeformance = (currentAverageScore / bestAverageScore) - 1;
		double currentPeformance2 = 100 - currentPeformance;

		out("Current Performance: " + nf.format(currentPeformance2) + "%");
		out("Gen's Avg Score: " + nf.format(currentAverageScore) + " / Best Avg Score: " + nf.format(bestAverageScore) + " / Best Score: "
				+ nf.format(bestIndividual.getFitnessScore()));
		out("Gen's Avg Locations found: " + currentAverageLocationsFound + " / Gen's Most Locations Found: " + mostGenerationLocationsFound);

	}

	private void out(String output) {
		System.out.println(output);
	}

	public void generateFirstGenerationChromosomes() {

		// figure out what the length of the chromosome needs to be
		// number of locations * CHROMOSOME_LENGTH_MULTIPLIER
		int chromosomeLength = allZipLocations.size() * config.getCHROMOSOME_LENGTH_MULTIPLIER();

		for (int i = 0; i < config.getNUMBER_OF_CHROMOSOMES_PER_GENERATION(); i++) {
			Chromosome c = new Chromosome(chromosomeLength);

			for (int k = 0; k < chromosomeLength; k++) {
				c.addGene(generateRandomGene());
			}

			population.add(c);
			out(i + ":" + c.toString());
		}

	}

	public Gene generateRandomGene() {

		String out = "";

		for (int i = 0; i < 3; i++) {
			// out += (char) randRange(65,90);
			out += (char) randRange(65, 90);
		}

		return new Gene(out);
	}

	public Chromosome scoreChromosomeFitness(Chromosome c) {

		double output = 0D;

		// weight for the worst case scenario for distance
		double totalDist = (zipLocations.size() - 1) * 5000;

		// weight for the worst case scenario for the number of locations found
		output = zipLocations.size() * 1000;

		// find the genes in the chromosome that are actual locations
		ArrayList<ZipLocation> actualLocations = new ArrayList<ZipLocation>();

		int actualLocationsLength;

		int chromosomeLength = c.getLength();
		int numberOfLocations = zipLocations.size();

		// boolean skyFound = false;
		// boolean netFound = false;

		cLoop: for (int gIndex = 0; gIndex < chromosomeLength; gIndex++) {

			Gene g = c.getGeneAtPosition(gIndex);

			/*
			 * if ( g.equals(new Gene("SKY")) ) { skyFound = true;
			 * //skyFoundCount++; }
			 * 
			 * if ( g.equals(new Gene("NET")) ) { netFound = true;
			 * //netFoundCount++; }
			 * 
			 * if ( skyFound ) { if ( g.equals(new Gene("NET")) ) { output +=
			 * 9999999999D; skynetFoundCount++; } }
			 * 
			 * if ( netFound ) { if ( g.equals(new Gene("SKY")) ) { output +=
			 * 9999999999D; skynetFoundCount++; } }
			 */

			for (int locIndex = 0; locIndex < numberOfLocations; locIndex++) {

				ZipLocation l = zipLocations.get(locIndex);

				if (g.toString().equals(l.getId())) {

					boolean locationAlreadyFound = false;
					actualLocationsLength = actualLocations.size();

					falLoop: for (int falIndex = 0; falIndex < actualLocationsLength; falIndex++) {

						ZipLocation al = actualLocations.get(falIndex);

						if (al.getId().equals(l.getId())) {
							// dock the score for this
							output += config.getDUPLICATE_LOCATION_SCORE_PENALTY();
							locationAlreadyFound = true;
							break falLoop;
						}
					}

					if (!locationAlreadyFound) {
						actualLocations.add(l);
						c.addGeneToPhenotype(g);
					}

					continue cLoop;
				}
			}

		}

		actualLocationsLength = actualLocations.size();
		output -= (double) (actualLocationsLength * 1000);

		if (actualLocationsLength > 1) {
			// skip the first one
			for (int alIndex = 0; alIndex < actualLocationsLength; alIndex++) {
				ZipLocation currentLocation = actualLocations.get(alIndex);

				int previousLocationIndex = alIndex - 1;

				if (alIndex == 0)
					previousLocationIndex = actualLocationsLength - 1;

				ZipLocation previousLocation = actualLocations.get(previousLocationIndex);

				double dist = getLatLongDistance(currentLocation.getLattitude(), currentLocation.getLongitude(), previousLocation.getLattitude(),
						previousLocation.getLongitude());

				totalDist -= 5000;
				totalDist += dist;
			}
		}

		c.setFitnessScore(output + totalDist);
		c.setLocationsFound(actualLocationsLength);
		c.setTotalDistance(totalDist);

		/*
		 * if ( skyFound ) { System.out.println(
		 * "SKYNET found in chromosome! It should be selected out - but you might want to stop to be sure."
		 * ); }
		 */

		return c;
	}

	public ArrayList<Chromosome> selectChromosomesForMating(ArrayList<Chromosome> population) {

		Collections.sort(population);

		ArrayList<Chromosome> output = new ArrayList<Chromosome>();

		int survivors = (int) Math.floor(config.getNUMBER_OF_CHROMOSOMES_PER_GENERATION() / 2);

		for (int cIndex = 0; cIndex < survivors; cIndex++) {
			Chromosome c = population.get(cIndex);

			output.add(c);
		}

		return output;
	}

	public ArrayList<Chromosome> mateTopChromosomes(ArrayList<Chromosome> chromosomesForMating) {

		ArrayList<Chromosome> output = new ArrayList<Chromosome>();

		// we are trying to create a new population of
		// NUMBER_OF_CHROMOSOMES_PER_GENERATION

		// distribute the chromosomes for mating evenly determined by score
		// this should effectively make the stronger individuals more likely to
		// be picked

		ArrayList<Chromosome> distributedChromosomesForMating = new ArrayList<Chromosome>();

		// first we need to get the total score of the chromosomes selected for
		// mating
		double totalScore = 0;
		Chromosome c;
		int i;
		int chromosomesForMatingLength = chromosomesForMating.size();

		for (i = 0; i < chromosomesForMatingLength; i++) {
			c = chromosomesForMating.get(i);

			totalScore += c.getInvertedFitnessScore();
		}

		for (i = 0; i < chromosomesForMatingLength; i++) {
			c = chromosomesForMating.get(i);

			switch (i) {

			case 0:

				c.setDistribution(((c.getInvertedFitnessScore() + config.getSUPER_INDIVIDUAL_BONUS_FIRST()) / totalScore) * 1000);

				break;
			case 1:

				c.setDistribution(((c.getInvertedFitnessScore() + config.getSUPER_INDIVIDUAL_BONUS_SECOND()) / totalScore) * 1000);

				break;
			case 2:

				c.setDistribution(((c.getInvertedFitnessScore() + config.getSUPER_INDIVIDUAL_BONUS_THIRD()) / totalScore) * 1000);

				break;
			default:

				c.setDistribution((c.getInvertedFitnessScore() / totalScore) * 1000);

				break;

			}

			// double d = c.getDistribution();

			for (int distIndex = 0; distIndex < c.getDistribution(); distIndex++) {
				distributedChromosomesForMating.add(c);
			}

		}

		while (output.size() < config.getNUMBER_OF_CHROMOSOMES_PER_GENERATION()) {

			// pick a random number between 0 and length of chromosomesForMating
			// - 1 for the first individual
			int randomPick1 = randRange(0, chromosomesForMatingLength - 1);

			// now pick another random number between 0 and length of
			// chromosomesForMating - 1 for the second individual
			// but we have to make sure it isnt the same as the first pick
			int randomPick2 = -1;

			while (randomPick2 == -1) {
				int randomPick3 = randRange(0, chromosomesForMatingLength - 1);

				if (randomPick3 != randomPick1)
					randomPick2 = randomPick3;
			}

			// now get those chromosomes
			Chromosome c1 = chromosomesForMating.get(randomPick1);
			Chromosome c2 = chromosomesForMating.get(randomPick2);

			// out("");
			// out(c1.toString());
			// out(c2.toString());

			if (c1.hasNullGene()) {
				out("c1 has null gene!");
				out(c1.toString());
			}

			if (c2.hasNullGene()) {
				out("c2 has null gene!");
				out(c2.toString());
			}

			int chromosomeLength = zipLocations.size() * config.getCHROMOSOME_LENGTH_MULTIPLIER();

			int crossoverPoint = randRange(0, chromosomeLength);

			Chromosome childC = new Chromosome(chromosomeLength);

			boolean c1GoesFirst = true;

			if (randRange(0, 99) >= 50)
				c1GoesFirst = false;

			// now choose up to the crossoverpoint from c1 and the rest from c2
			for (i = 0; i < chromosomeLength; i++) {

				// randomly choose if the father or mother goes first...
				if (c1GoesFirst) {
					if (i < crossoverPoint) {
						childC.addGene(c1.getGeneAtPosition(i));
					} else {
						childC.addGene(c2.getGeneAtPosition(i));
					}
				} else {
					if (i < crossoverPoint) {
						childC.addGene(c2.getGeneAtPosition(i));
					} else {
						childC.addGene(c1.getGeneAtPosition(i));
					}
				}

			}

			// out(childC.toString());

			boolean didMutate = false;

			// now randomly, if chosen to mutate, pick a gene to mutate
			if (randRange(0, 99) < config.getFIRST_MUTATION_PERCENTAGE()) {
				// chosen to mutate. randomly choose a gene and replace it.
				childC.setGeneAtPosition(generateRandomGene(), randRange(0, chromosomeLength - 1));
				mutatedCount++;
				didMutate = true;
			} else {
				notMutatedCount++;
			}

			// now randomly, if chosen to mutate, pick a gene to mutate
			if (randRange(0, 99) < config.getSECOND_MUTATION_PERCENTAGE()) {
				// chosen to mutate. randomly choose a gene and replace it.
				childC.setGeneAtPosition(generateRandomGene(), randRange(0, chromosomeLength - 1));
				mutatedCount++;
				didMutate = true;
			} else {
				notMutatedCount++;
			}

			// now randomly, if chosen to mutate, pick a gene to mutate
			if (randRange(0, 99) < config.getTHIRD_MUTATION_PERCENTAGE()) {
				// chosen to mutate. randomly choose a gene and replace it.
				childC.setGeneAtPosition(generateRandomGene(), randRange(0, chromosomeLength - 1));
				mutatedCount++;
				didMutate = true;
			} else {
				notMutatedCount++;
			}

			if (!didMutate) {
				// now randomly, switch the chromosome around in 4 sections
				if (randRange(0, 99) < currentAverageLocationsFound * config.getCHROMOSOME_SHUFFLE_ODDS_MULTIPLIER()) {
					childC.shuffleChromosome();
				}
			}

			// add the child to the new pool, rinse and repeat
			output.add(childC);

		}

		return output;

	}

	private String timeFormat(long seconds) {
		return String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
	}

}
