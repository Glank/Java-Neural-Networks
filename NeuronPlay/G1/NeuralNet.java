import java.util.*;

public class NeuralNet
{
	public Vector<Neuron> neurons;

	public NeuralNet()
	{
		neurons = new Vector();
	}

	public NeuralNet(DNA dna)
	{
		neurons = new Vector();
		for(int i = 0; i < (int)(dna.data.length()/4.0); i+=32)
		{
			addNeuron(dna.data.charAt(i)!='G', dna.readShortDouble(i+1)*dna.readShortDouble(i+16)*4*(dna.readBoolean(i+31)?1:-1));
		}
		int connections = 0;
		for(int i = (int)(dna.data.length()/4.0); dna.hasLength(i,61);i+=61)
		{
			connections++;
			makeConnection(dna.readUnsignedInt(i),
			dna.readUnsignedInt(i+15),
			dna.readShortDouble(i+30)*dna.readShortDouble(i+45)*10*(dna.readBoolean(i+60)?1:-1));
		}
		//System.out.println(neurons.size());
		//System.out.println(connections);
	}

	public double getState(int neuron)
	{
		if(neuron >= neurons.size())
			return 0;
		return neurons.get(neuron).on();
	}

	public void setState(int neuron, double on)
	{
		if(neuron < neurons.size())
			neurons.get(neuron).setState(on);
	}

	public void iterate()
	{
		for(int i = 0; i < neurons.size(); i++)
		{
			neurons.get(i).passCharges();
		}
	}

	public void addNeuron(boolean twoState, double resistance)
	{
		neurons.add(new Neuron(twoState, resistance));
	}

	public void makeConnection(int a, int b, double amplification)
	{
		if((neurons.size() != 0) && (a%neurons.size() != b%neurons.size()))
			neurons.get(a%neurons.size()).connectTo(neurons.get(b%neurons.size()), amplification);
	}

	public static void main(String[] args)
	{
		while(true)
		{
			DNA dna = new DNA(1000);
			NeuralNet nn = new NeuralNet(dna);
			nn.setState(0,1);
			nn.setState(1,1);
			nn.iterate();
			//System.out.println(nn.getState(2));
		}
	}
}