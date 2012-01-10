package com;

import java.util.ArrayList;
import java.util.List;

public class Chromosome implements Comparable<Chromosome> {

	private ArrayList<Gene> arrGenes;
	private ArrayList<Gene> arrPhenotype;
	private int chromosomeLength;
	private double fitnessScore;
	private double invertedScore;
	private double distribution;
	private int locationsFound;
	private double totalDistance;
	
	public Chromosome ( int chromosomeLength)
	{
		this.chromosomeLength = chromosomeLength;
		arrGenes = new ArrayList<Gene>(chromosomeLength);
		fitnessScore = -1D;
		locationsFound = 0;
		totalDistance = -1D;
		arrPhenotype = new ArrayList<Gene>(chromosomeLength);
	}
	
	public String toString () {
		String out = "";
		int i = 0;
		
		for ( i = 0; i < arrGenes.size(); i++ )
		{
			Gene tempGene = arrGenes.get(i);
			
			if ( tempGene == null ) {
				if ( i != arrGenes.size() -1 )
				{
					out += "-";
				}
			}
			else {
				out += tempGene.toString();
				
				if ( i != arrGenes.size() -1 )
				{
					out += "-";
				}
			}
				
		}
		
		return out;
		
	}
	
	public int getLength () {
		return arrGenes.size();
	}
	
	public String stringValue () {
		return toString();
				
	}
	
	public void addGene ( Gene gene ) {
		arrGenes.add(gene);
	}
	
	public void setGeneAtPosition ( Gene gene, int position ) {
		arrGenes.set(position, gene);
	}
	
	public Gene getGeneAtPosition ( int position ) {
		if ( position < 0 || position >= arrGenes.size() ) {
			return null;
		}
		
		return arrGenes.get(position);
	}
	
	public void setFitnessScore ( double value ) {
		fitnessScore = value;
		invertedScore = 1000000D - fitnessScore;
	}
	
	public double getFitnessScore ( ) {
		return fitnessScore;
	}
	
	public double getInvertedFitnessScore () {
		return invertedScore;
	}
	
	public void setDistribution ( double value ) {
		distribution = Math.floor(value);
	}

	public double getDistribution ( ) {
		return distribution;
	}
	
	public void setLocationsFound ( int value ) {
		locationsFound = value;
	}
	
	public int getLocationsFound ( ) {
		return locationsFound;
	}
	
	public void setTotalDistance ( double value ) {
		totalDistance = value;
	}
	
	public double getTotalDistance ( ) {
		return totalDistance;
				
	}
	
	public void setPhenotype ( ArrayList<Gene> value ) {
		arrPhenotype = value;
	}
	
	public ArrayList<Gene> getPhenotype ( ) {
		return arrPhenotype;
	}
	
	public void addGeneToPhenotype ( Gene value ) {
		arrPhenotype.add(value);
	}
	
	public String phenotypeToString ( ) {
		String out = "";
		int i = 0;
		
		for ( i = 0; i < arrPhenotype.size(); i++ )
		{
			Gene tempGene = arrPhenotype.get(i);
			
			if ( tempGene == null ) {
				if ( i != arrPhenotype.size() -1 )
				{
					out += "-";
				}
			}
			else {
				out += tempGene.toString();
				
				if ( i != arrPhenotype.size() -1 )
				{
					out += "-";
				}
			}
				
		}
		
		return out;
	}
	
	public boolean hasNullGene ( )
	{
		
		try {
			for ( int i = 0; i < chromosomeLength; i++ ) {
				if ( arrGenes.get(i) == null) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("something is wrong!");
			System.out.println(toString());
		}
		
		return false;
	}
	
	public String phenotypeStringValue () {
		return phenotypeToString();
	}
	
	public void shuffleChromosome ( ) {
		
		if ( hasNullGene() )
			System.out.println("null elements found!");
		
		int p1 = randRange(0, chromosomeLength - 4);
		int p2 = randRange(p1 + 1, chromosomeLength - 3);
		int p3 = randRange(p2 + 1, chromosomeLength - 2);
		int p4 = chromosomeLength;
		
		//create the temp arrays to hold the genes
		List<Gene> ar1 = arrGenes.subList(0, p1);
		List<Gene> ar2 = arrGenes.subList(p1, p2);
		List<Gene> ar3 = arrGenes.subList(p2, p3);
		List<Gene> ar4 = arrGenes.subList(p3, p4);
		
		//now recombine them randomly
		ArrayList<Integer> arOrder = new ArrayList<Integer>(4);
		ArrayList<Gene> arOutput = new ArrayList<Gene>(chromosomeLength);
		
		arOrder.add(1);
		arOrder.add(2);
		arOrder.add(3);
		arOrder.add(4);
		
		int i = 0;
		
		for ( i = 0; i < 4; i++ ) {
			int randNum = randRange(0,arOrder.size()-1);
			
			switch ( arOrder.get(randNum) ) {
			
				case 1 :
					arOutput.addAll(ar1);
					break;
				case 2 :
					arOutput.addAll(ar2);
					break;
				case 3 :
					arOutput.addAll(ar3);
					break;
				case 4 :
					arOutput.addAll(ar4);
					break;
			}
			
			arOrder.remove(randNum);
		}
	
		arrGenes = arOutput;
		
		if ( hasNullGene() )
			System.out.println("null elements found!");
		
	}
	
	@Override
	public int compareTo(Chromosome o) {
		
		if ( fitnessScore < o.getFitnessScore() )
			return -1;

		if ( fitnessScore > o.getFitnessScore() )
			return 1;
		
		return 0;
	}
	
	
	
	public static final int randRange ( int low, int high ) {
		return (int) ( low + Math.floor(Math.random() * ( high - low + 1)));
	}
	
}
