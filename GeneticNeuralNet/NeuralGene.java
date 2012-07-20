import java.io.*;
import java.util.*;
public class NeuralGene implements Serializable
{
	public boolean[] bits;
	public int size;

	public NeuralGene(int gridSize)
	{
		bits = new boolean[10+2*bitsNeeded(gridSize)];
		size = bitsNeeded(gridSize);
		for(int i = 0; i < bits.length; i++)
		{
			bits[i] = (Math.random() > .5);
		}
	}

	public NeuralGene(boolean[] chromosome, int start, int end)
	{
		bits = new boolean[end-start];
		size = (end-start-10)/2;
		for(int i = start; i < end; i++)
		{
			bits[i-start] = chromosome[i];
		}
	}

	public int length()
	{
		return bits.length;
	}

	private int max(double d)
	{
		int i = (int)d;
		if(i != d)
			i++;
		return i;
	}

	private int bitsNeeded(int size)
	{
		return max(Math.log(size)/Math.log(2));
	}

	private int getInt(int start, int end)
	{
		int num = 0;
		for(int i = start; i < end; i++)
		{
			if(bits[i])
				num+= (int)Math.pow(2,i-start);
		}
		return num;
	}

	private byte getByte(int start)
	{
		return (byte)getInt(start,start+8);
	}

	public String unparse()
	{
		if(bits[0] && bits[1])
			return "?";

		if(!bits[0] && !bits[1])
		{
			return "set " + getInt(2,2+size) + " " + getByte(2+size);
		}

		if(!bits[0] && bits[1])
		{
			return "connect " + getInt(2,2+size) + " " + getInt(2+size,2+2*size) + " " + getByte(2+2*size);
		}

		if(bits[0] && !bits[1])
		{
			return "on " + getInt(2,2+size);
		}

		return "?";
	}

	public static void main(String[] args)
	{
		for(int i = 0; i < 10; i++)
		{
			System.out.println((new NeuralGene(8)).unparse());
		}
	}
}