package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChromosomeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//Chromosome c = new Chromosome(50);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChromosome() {
		Chromosome c = new Chromosome(50);
		
		assertEquals(50, c.getLength());
	}

	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetLength() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStringValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddGene() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetGeneAtPosition() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetGeneAtPosition() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetFitnessScore() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetFitnessScore() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetInvertedFitnessScore() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetDistribution() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDistribution() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetLocationsFound() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetLocationsFound() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetTotalDistance() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetTotalDistance() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetPhenotype() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetPhenotype() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddGeneToPhenotype() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testPhenotypeToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testHasNullGene() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testPhenotypeStringValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testShuffleChromosome() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCompareTo() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRandRange() {
		fail("Not yet implemented"); // TODO
	}

}
