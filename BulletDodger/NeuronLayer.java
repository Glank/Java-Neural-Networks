import java.util.*;

//Omnia sine una

public class NeuronLayer
{
	public static final int BOOLEAN_ACTIVATION = -1;
	public static final int LINIAR_ACTIVATION = 0;
	public static final int SIGMOID_ACTIVATION = 1;
	public static final int HYPERBOLIC_TANGENT_ACTIVATION = 2;

	double[] activations;
	double[] weights = null;

	int activationType;

	NeuronLayer connection = null;

	public NeuronLayer(int size, int activationType)
	{
		this.activationType = activationType;
		activations = new double[size];
	}

	public double[] getActivations()
	{
		double[] ret = new double[activations.length];
		for(int i = 0; i < ret.length; i++)
		{
			switch(activationType)
			{
				case BOOLEAN_ACTIVATION:
					ret[i] = booleanActivation(activations[i]);
				case LINIAR_ACTIVATION:
					ret[i] = liniarActivation(activations[i]);
				case SIGMOID_ACTIVATION:
					ret[i] = sigmoidActivation(activations[i]);
				case HYPERBOLIC_TANGENT_ACTIVATION:
					ret[i] = hyperbolicTangentActivation(activations[i]);
			}
		}
		return ret;
	}

	public void connectTo(NeuronLayer connection, double[] weights)
	{
		this.connection = connection;
		if(weights == null)
		{
			Random r = new Random();
			this.weights = new double[connection.size()*size()];
			for(int i = 0; i < this.weights.length; i++)
			{
				this.weights[i] = r.nextGaussian();
			}
		}
		else
			this.weights = weights;
	}

	public NeuronLayer getConnection()
	{
		return connection;
	}

	public NeuronLayer getOutputLayer()
	{
		NeuronLayer end = this;
		while(end.getConnection()!=null)
		{
			end = end.getConnection();
		}
		return end;
	}

	public NeuronLayer mutate(double changeRate)
	{
		NeuronLayer ret = new NeuronLayer(size(),activationType);
		if(connection != null)
		{
			Random r = new Random();
			double[] newWeights = new double[weights.length];
			for(int i = 0; i < weights.length; i++)
			{
				newWeights[i] = weights[i];
				if(Math.random()<changeRate)
				{
					newWeights[i] = newWeights[i] + r.nextGaussian()/5;
				}
			}
			ret.connectTo(connection.mutate(changeRate),newWeights);
		}
		return ret;
	}

	public double sigmoidActivation(double activation)
	{
		return 1.0/(1+Math.pow(Math.E,-activation));
	}

	public double hyperbolicTangentActivation(double activation)
	{
		return (Math.pow(Math.E, 2*activation)-1)/(Math.pow(Math.E,2*activation)+1);
	}

	public double liniarActivation(double activation)
	{
		return activation;
	}

	public double booleanActivation(double activation)
	{
		return (activation>0)?1.0:0.0;
	}

	public int size()
	{
		return activations.length;
	}

	public void set(int num, double amount)
	{
		activations[num] = amount;
	}

	public void add(int num, double amount)
	{
		activations[num] = amount+activations[num];
	}

	public void clear()
	{
		activations = new double[activations.length];
	}

	public void iterate()
	{
		if(connection != null)
		{
			for(int i = 0; i < weights.length; i++)
			{
				switch(activationType)
				{
					case BOOLEAN_ACTIVATION:
						connection.add(i%connection.size(), weights[i]*booleanActivation(activations[i/connection.size()]));
						break;
					case LINIAR_ACTIVATION:
						connection.add(i%connection.size(), weights[i]*liniarActivation(activations[i/connection.size()]));
						break;
					case SIGMOID_ACTIVATION:
						connection.add(i%connection.size(), weights[i]*sigmoidActivation(activations[i/connection.size()]));
						break;
					case HYPERBOLIC_TANGENT_ACTIVATION:
						connection.add(i%connection.size(), weights[i]*hyperbolicTangentActivation(activations[i/connection.size()]));
						break;
				}
			}
			clear();
			connection.iterate();
		}
	}

	public double getActivation(int neuron)
	{
		switch(activationType)
		{
			case BOOLEAN_ACTIVATION:
				return booleanActivation(activations[neuron]);
			case LINIAR_ACTIVATION:
				return liniarActivation(activations[neuron]);
			case SIGMOID_ACTIVATION:
				return sigmoidActivation(activations[neuron]);
			case HYPERBOLIC_TANGENT_ACTIVATION:
				return hyperbolicTangentActivation(activations[neuron]);
		}
		System.out.println("Error in getActivation()");
		return 0;
	}
}