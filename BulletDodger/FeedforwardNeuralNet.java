import java.util.*;

public class FeedforwardNeuralNet
{
	public static final double MUTATION_RATE = 0.05;
	NeuronLayer input;
	public FeedforwardNeuralNet(int inputs, int outputs, int hiddenLayers, int hiddenLayerSizes)
	{
		input = new NeuronLayer(inputs, NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION);
		for(int i = 0; i < hiddenLayers; i++)
		{
			input.getOutputLayer().connectTo(new NeuronLayer(hiddenLayerSizes,NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION), null);
		}
		input.getOutputLayer().connectTo(new NeuronLayer(outputs,NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION), null);
	}

	public FeedforwardNeuralNet(NeuronLayer layer)
	{
		input = layer;
	}

	public double[] getOut(double... in)
	{
		input.getOutputLayer().clear();
		for(int i = 0; i < in.length; i++)
		{
			input.set(i,in[i]);
		}
		input.iterate();
		return input.getOutputLayer().getActivations();
	}


	public FeedforwardNeuralNet getChild()
	{
		return new FeedforwardNeuralNet(input.mutate(MUTATION_RATE));
	}
}