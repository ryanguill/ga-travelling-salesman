This is a version of a genetic algorithm I have written in a few different languages. The purpose is to solve the travelling salesman problem by breeding and evolving a set of solutions.

To make it harder and more accurate to an evolution simulation, the actual cities are represented as certain genes, but the genes in the individual are random, meaning that the "city" genes that are actual phenotypic must be discovered, as well as then evolved to be in order. 

At a high level, fitness is measured as having discovered the actual locations and minimizing distance, while penalizing visiting the same city more than once.

Mutation, chromosome shuffling and bonuses to reward fitness are options that can be adjusted to achieve very different performance characteristics.

On the first run, a default set of parameters will be written to the home directory, which can then be adjusted for future runs.

to compile:
`javac -classpath ./libs:./src -d ./out/production/ga-travelling-salesman ./src/Main.java`

to run:
`java -Dfile.encoding=UTF-8 -classpath ./out/production/ga-travelling-salesman Main`

This project was originally written using java 8, but has been tested up to Java 15.