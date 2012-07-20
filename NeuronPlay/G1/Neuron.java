import java.awt.*;
import java.util.*;

public class Neuron
{
	double charge = 0;
	boolean twoState = true;
	double resistance = 0;
	Vector<Neuron> connections;
	Vector<Double> amplifications;

	public Neuron(boolean twoState, double resistance)
	{
		this.twoState = twoState;
		this.resistance = resistance;
		connections = new Vector();
		amplifications = new Vector();
	}

	public void connectTo(Neuron other, double amplification)
	{
		connections.add(other);
		amplifications.add(amplification);
	}

	public boolean isConnected(Neuron other)
	{
		for(int i = 0; i < connections.size(); i++)
		{
			if(connections.get(i) == other)
				return true;
		}
		return false;
	}

	public double on()
	{
		return twoState?charge>resistance?1:0:charge-resistance;
	}

	public void setState(double on)
	{
		//System.out.println("Reached.");
		charge = on+resistance;
	}

	public void recieveCharge(double c)
	{
		charge+=c;
	}

	public void passCharges()
	{
		for(int i = 0; i < connections.size(); i++)
		{
			connections.get(i).recieveCharge(on()*charge*amplifications.get(i));
		}
		charge = 0;
	}
}