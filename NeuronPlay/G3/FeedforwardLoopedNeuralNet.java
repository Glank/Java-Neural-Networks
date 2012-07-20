import java.util.*;

public class FeedforwardLoopedNeuralNet
{
	public static final double MUTATION_RATE = 0.05;
	NeuronLayer input, inputMirror;

	public FeedforwardLoopedNeuralNet(int inputs, int outputs, int hiddenLayers, int hiddenLayerSizes)
	{
		input = new NeuronLayer(inputs, NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION);
		inputMirror = new NeuronLayer(inputs, NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION);
		for(int i = 0; i < hiddenLayers; i++)
		{
			input.getOutputLayer().connectTo(new NeuronLayer(hiddenLayerSizes,NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION), null);
			inputMirror.getOutputLayer().connectTo(new NeuronLayer(hiddenLayerSizes,NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION), null);
		}
		input.getOutputLayer().connectTo(new NeuronLayer(outputs,NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION), null);
		inputMirror.getOutputLayer().connectTo(new NeuronLayer(inputs*outputs*(int)Math.pow(hiddenLayerSizes, hiddenLayers),NeuronLayer.HYPERBOLIC_TANGENT_ACTIVATION), null);
	}

	public FeedforwardLoopedNeuralNet(NeuronLayer layer, NeuronLayer layerMirror)
	{
		input = layer;
		inputMirror = layerMirror;
	}

	public double[] getOut(double... in)
	{
		input.getOutputLayer().clear();
		for(int i = 0; i < in.length; i++)
		{
			input.set(i,in[i]);
		}
		inputMirror.getOutputLayer().clear();
		for(int i = 0; i < in.length; i++)
		{
			inputMirror.set(i,in[i]);
		}
		input.iterate();
		inputMirror.iterate();
		input.recieveWeightModification(input.getOutputLayer().getActivations());
		return input.getOutputLayer().getActivations();
	}


	public FeedforwardLoopedNeuralNet getChild()
	{
		return new FeedforwardLoopedNeuralNet(input.mutate(MUTATION_RATE), inputMirror.mutate(MUTATION_RATE));
	}
}