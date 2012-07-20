import java.util.*;
import java.io.*;

public class NeuralGrid implements Serializable
{
	public ArrayList<Neuron> neurons = new ArrayList();

	public NeuralGrid()
	{
	}

	public void addNew(int r)
	{
		neurons.add(new Neuron(r));
		neurons.get(neurons.size()-1).setNumber(neurons.size()-1);
	}

	public void addNew()
	{
		neurons.add(new Neuron(0));
		neurons.get(neurons.size()-1).setNumber(neurons.size()-1);
	}

	public void setResistance(int n, int r)
	{
		if(n < neurons.size())
			neurons.get(n).resistance = r;
	}

	public void connect(int n1, int n2, int s)
	{
		if((n1 < neurons.size()) && (n2 < neurons.size()))
			neurons.get(n1).connect(neurons.get(n2),s);
	}

	public void turnOn(int n)
	{
		if(n < neurons.size())
			neurons.get(n).on = true;
	}

	public void addNeuralEvent(int n, NeuralEvent ne)
	{
		neurons.get(n).event = ne;
	}

	public String toString()
	{
		String r = "";
		for(int i = 0; i < neurons.size(); i++)
		{
			if(neurons.get(i).on)
				r = r + "1";
			else
				r = r + "0";
		}
		return r;
	}

	public void update()
	{
		//System.out.println(this);
		for(int i = 0; i < neurons.size(); i++)
		{
			neurons.get(i).send();
		}
		for(int i = 0; i < neurons.size(); i++)
		{
			neurons.get(i).recieve();
		}
	}
}