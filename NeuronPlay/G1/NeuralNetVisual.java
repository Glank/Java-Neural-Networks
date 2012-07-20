import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;

public class NeuralNetVisual extends JFrame implements MouseListener, Runnable
{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 250;

	NeuralNet net;
	Vector<Point> neuronLocations = new Vector();

	public NeuralNetVisual(NeuralNet net)
	{
		this.net = net;
		for(int i = 0; i < net.neurons.size(); i++)
		{
			neuronLocations.add(new Point((int)(Math.random()*WIDTH), (int)(Math.random()*HEIGHT)));
		}

		setSize(WIDTH,HEIGHT);
		setTitle("Testing in progress...");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		new Thread(this).start();
	}

	public void setNeuralNet(NeuralNet net)
	{
		neuronLocations = new Vector();
		this.net = net;
		for(int i = 0; i < net.neurons.size(); i++)
		{
			neuronLocations.add(new Point((int)(Math.random()*WIDTH), (int)(Math.random()*HEIGHT)));
		}
	}

	public void run()
	{
		while(true)
		{
			repaint();
			try
			{
				Thread.sleep(25);
			}
			catch(Exception ex)
			{
			}
		}
	}

	public void paint(Graphics g)
	{
		//create blank canvas
		g.setColor(Color.WHITE);
		g.fillRect(0,0,WIDTH, HEIGHT);

		g.setColor(Color.BLACK);
		for(int i = 0; i < net.neurons.size(); i++)
		{
			for(int j = i+1; j < net.neurons.size(); j++)
			{
				if(net.neurons.get(i).isConnected(net.neurons.get(j)))
					g.drawLine((int)neuronLocations.get(i).getX(), (int)neuronLocations.get(i).getY(), (int)neuronLocations.get(j).getX(), (int)neuronLocations.get(j).getY());
			}
		}

		//draw neurons
		g.setColor(Color.BLUE);
		for(int i = 0; i < net.neurons.size(); i++)
		{
			if(net.neurons.get(i).on()>.5)
				g.fillOval((int)neuronLocations.get(i).getX()-4, (int)neuronLocations.get(i).getY()-4, 8, 8);
			else
				g.drawOval((int)neuronLocations.get(i).getX()-4, (int)neuronLocations.get(i).getY()-4, 8, 8);
		}

	}

	public void mouseExited(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}

	public void mouseClicked(MouseEvent me)
	{
		//save - to be writen
	}
}