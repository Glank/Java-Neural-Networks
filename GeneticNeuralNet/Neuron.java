import java.util.*;
public class Neuron
{
	public ArrayList<Neuron> connections = new ArrayList();
	public ArrayList<Integer> strengths = new ArrayList();
	public int resistance = 0;
	public int recieved = 0;
	public boolean on = false;
	public NeuralEvent event = null;
	public int number = 0;

	public Neuron(int r)
	{
		resistance = r;
	}

	public void connect(Neuron n, int s)
	{
		connections.add(n);
		strengths.add(s);
	}

	public void setNumber(int num)
	{
		number = num;
	}

	public void recieve(int r)
	{
		recieved+=r;
	}

	public void send()
	{
		for(int i = 0; i < connections.size(); i++)
		{
			connections.get(i).recieve(strengths.get(i));
		}
	}

	public void recieve()
	{
		on = recieved > resistance;
		if((event != null) && on)
			event.neuralAction(recieved-resistance, number);
		recieved = 0;
	}
}