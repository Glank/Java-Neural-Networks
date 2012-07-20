import java.util.*;
import java.io.*;

public class GeneticNeuralGrid extends NeuralGrid implements Serializable
{
	public NeuralChromosome[] chromosomes;
	public GeneticNeuralGrid(int neuronCount, int chromosomeCount, int genes)
	{
		super();
		neurons = new ArrayList();
		for(int i = 0; i < neuronCount; i++)
		{
			addNew();
		}

		chromosomes = new NeuralChromosome[chromosomeCount];
		for(int i = 0; i < chromosomeCount; i++)
		{
			chromosomes[i] = new NeuralChromosome(neuronCount, genes);
		}
		readChromosomes();
	}

	public GeneticNeuralGrid(GeneticNeuralGrid p1, GeneticNeuralGrid p2, double mutationProbability)
	{
		super();
		neurons = new ArrayList();
		for(int i = 0; i < p1.neurons.size(); i++)
		{
			addNew();
		}

		chromosomes = new NeuralChromosome[p1.chromosomes.length];
		for(int i = 0; i < chromosomes.length; i++)
		{
			chromosomes[i] = new NeuralChromosome(p1.chromosomes[i], p2.chromosomes[i], mutationProbability);
		}
		readChromosomes();
	}

	public GeneticNeuralGrid(GeneticNeuralGrid gng, double mutationProbability)
	{
		super();
		neurons = new ArrayList();
		for(int i = 0; i < gng.neurons.size(); i++)
		{
			addNew();
		}

		chromosomes = new NeuralChromosome[gng.chromosomes.length];
		for(int i = 0; i < chromosomes.length; i++)
		{
			chromosomes[i] = new NeuralChromosome(gng.chromosomes[i], mutationProbability);
		}
		readChromosomes();
	}

	public void readChromosomes()
	{
		String commands = "";
		for(int i = 0; i < chromosomes.length; i++)
		{
			if(i!=0)
				commands = commands + "\n";
			commands = commands + chromosomes[i].unparse();
		}
		Scanner reader = new Scanner(commands);
		while(reader.hasNext())
		{
			Scanner line = new Scanner(reader.nextLine());
			String command = line.next();
			if(command.equals("set"))
			{
				setResistance(line.nextInt(), line.nextInt());
			}
			else if(command.equals("connect"))
			{
				connect(line.nextInt(),line.nextInt(),line.nextInt());
			}
			else if(command.equals("on"))
			{
				turnOn(line.nextInt());
			}
		}
	}
}