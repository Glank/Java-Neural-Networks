import java.io.*;
import java.util.*;

public class NeuralChromosome implements Serializable
{
	public boolean[] bits;
	public int gridSize;
	public int geneLength;

	public NeuralChromosome(int gridS, int genes)
	{
		gridSize = gridS;
		geneLength = (new NeuralGene(gridS)).length();
		bits = new boolean[genes*geneLength];
		for(int i = 0; i < bits.length; i++)
		{
			bits[i] = Math.random() > .5;
		}
	}

	public int length()
	{
		return bits.length;
	}

	public NeuralChromosome(NeuralChromosome p1, NeuralChromosome p2, double mutationProbability)
	{
		int pchange = (int)(Math.random()*p1.length());
		bits = new boolean[p1.length()];
		gridSize = p1.gridSize;
		geneLength = p1.geneLength;
		if(Math.random() > .5)
		{
			for(int i = 0; i < p1.length(); i++)
			{
				if(i < pchange)
				{
					if(Math.random() > mutationProbability)
						bits[i] = p1.bits[i];
					else
						bits[i] = !p1.bits[i];
				}
				else
				{
					if(Math.random() > mutationProbability)
						bits[i] = p2.bits[i];
					else
						bits[i] = !p2.bits[i];
				}
			}
		}
		else
		{
			for(int i = 0; i < p1.length(); i++)
			{
				if(i < pchange)
				{
					if(Math.random() > mutationProbability)
						bits[i] = p2.bits[i];
					else
						bits[i] = !p2.bits[i];
				}
				else
				{
					if(Math.random() > mutationProbability)
						bits[i] = p1.bits[i];
					else
						bits[i] = !p1.bits[i];
				}
			}
		}
	}

	public NeuralChromosome(NeuralChromosome n)
	{
		bits = new boolean[n.length()];
		gridSize = n.gridSize;
		geneLength = n.geneLength;
		for(int i = 0; i < n.length(); i++)
		{
			bits[i] = n.bits[i];
		}
	}

	public NeuralChromosome(NeuralChromosome n, double mutationProbability)
	{
		bits = new boolean[n.length()];
		gridSize = n.gridSize;
		geneLength = n.geneLength;
		for(int i = 0; i < n.length(); i++)
		{
			if(Math.random() > mutationProbability)
				bits[i] = n.bits[i];
			else
				bits[i] = !n.bits[i];
		}
	}

	public String unparse()
	{
		String ret = "";
		for(int i = 0; i < length()/geneLength; i++)
		{
			if(i != 0)
				ret = ret + "\n";
			ret = ret + (new NeuralGene(bits, i*geneLength, (i+1)*geneLength)).unparse();
		}
		return ret;
	}

	public static void main(String[] args)
	{
		NeuralChromosome nc = new NeuralChromosome(8,10);
		System.out.println(nc.unparse());
	}
}