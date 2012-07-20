import java.util.*;
import java.awt.*;

public class MoveBot implements NeuralEvent
{
	public int x = 0, y = 0;
	public GeneticNeuralGrid gng;
	public static final int NEURONS = 25;
	public static final double MUTATION = .001;
	public static final int RADIUS = 5;
	public int touches = 0;
	public int xVect = 0, yVect = 0;

	public MoveBot(int xl, int yl)
	{
		x = xl;
		y = yl;
		gng = new GeneticNeuralGrid(NEURONS,1,NEURONS*4);
		gng.addNeuralEvent(4,this);
		gng.addNeuralEvent(5,this);
		gng.addNeuralEvent(6,this);
		gng.addNeuralEvent(7,this);
	}

	public MoveBot(MoveBot p1, MoveBot p2)
	{
		x = (p1.x+p2.x)/2;
		y = (p1.y+p2.y)/2;

		gng = new GeneticNeuralGrid(p1.gng, p2.gng, MUTATION);
		gng.addNeuralEvent(4,this);
		gng.addNeuralEvent(5,this);
		gng.addNeuralEvent(6,this);
		gng.addNeuralEvent(7,this);
		moveRandom();
	}

	public MoveBot(MoveBot mb)
	{
		x = mb.x;
		y = mb.y;

		gng = new GeneticNeuralGrid(mb.gng, MUTATION);
		gng.addNeuralEvent(4,this);
		gng.addNeuralEvent(5,this);
		gng.addNeuralEvent(6,this);
		gng.addNeuralEvent(7,this);
		moveRandom();
	}

	public void moveTo(int xl, int yl)
	{
		x = xl;
		y = yl;
	}

	public void moveRandom()
	{
		moveTo((int)(Math.random()*400), (int)(Math.random()*300));
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawRect(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
		g.setColor(Color.BLACK);
		g.drawLine(x-RADIUS*2, y, x+RADIUS*2, y);
		g.drawLine(x, y-RADIUS*2, x, y+RADIUS*2);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
	}

	public void update(ArrayList<Rectangle> bounds)
	{
		for(int i = 0; i < bounds.size(); i++)
		{
			if(bounds.get(i).contains(new Point(x-RADIUS*2, y)))
				gng.turnOn(0);
			if(bounds.get(i).contains(new Point(x+RADIUS*2, y)))
				gng.turnOn(1);
			if(bounds.get(i).contains(new Point(x, y-RADIUS*2)))
				gng.turnOn(2);
			if(bounds.get(i).contains(new Point(x, y+RADIUS*2)))
				gng.turnOn(3);
		}
		gng.update();
		for(int i = 0; i < bounds.size(); i++)
		{
			if(getBounds().intersects(bounds.get(i)) && !(getBounds().equals(bounds.get(i))))
			{
				touches++;
				i = bounds.size();
			}
		}
	}

	public void neuralAction(int s, int n)
	{
		if(n == 4)
			x+=s/30;
		else if(n == 5)
			x-=s/30;
		else if(n == 6)
			y+=s/30;
		else if(n == 7)
			y-=s/30;

		if(x < 0)
			x = 400;
		else if(x > 400)
			x = 0;
		else if(y < 0)
			y = 300;
		else if(y > 300)
			y = 0;
	}
}