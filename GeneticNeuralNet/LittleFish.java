import java.util.*;
import java.awt.*;

public class LittleFish implements WorldCreature, NeuralEvent
{
	public static final int RADIUS = 10;
	public static final int NEURONS = 25;
	public static final int CHROMOSOMES = 4;
	public static final int GENES = 10;
	public static final double MUTATION = .001;
	public int age = 0;
	public double x = 0, y = 0;
	public double direction = 0;
	public GeneticNeuralGrid brain;
	public ArrayList<LittleFish> preditors = new ArrayList();
	public LittleFish(double xL, double yL)
	{
		x = xL;
		y = yL;
		brain = new GeneticNeuralGrid(NEURONS,CHROMOSOMES,GENES);
		brain.addNeuralEvent(0,this);
		brain.addNeuralEvent(1,this);
		brain.addNeuralEvent(2,this);
		brain.addNeuralEvent(3,this);
		brain.addNeuralEvent(4,this);
		brain.addNeuralEvent(5,this);
		brain.addNeuralEvent(6,this);
		brain.addNeuralEvent(7,this);
	}

	public LittleFish(LittleFish p1, LittleFish p2)
	{
		x = (int)(Math.random()*400);
		y = (int)(Math.random()*300);
		brain = new GeneticNeuralGrid(p1.brain, p2.brain, MUTATION);
		brain.addNeuralEvent(0,this);
		brain.addNeuralEvent(1,this);
		brain.addNeuralEvent(2,this);
		brain.addNeuralEvent(3,this);
		brain.addNeuralEvent(4,this);
		brain.addNeuralEvent(5,this);
		brain.addNeuralEvent(6,this);
		brain.addNeuralEvent(7,this);
	}

	public void neuralAction(int s, int n)
	{
		if(n == 0)
			direction += s/10.0;
		if(n == 1)
			direction -= s/10.0;
		if((n < 5) && (n > 1))
		{
			x+=(RADIUS/2.0)*Math.cos(direction);
			y+=(RADIUS/2.0)*Math.sin(direction);
		}
		if(n >= 5)
		{
			x-=(RADIUS/2.0)*Math.cos(direction);
			y-=(RADIUS/2.0)*Math.sin(direction);
		}

		if(x < 0)
			x = 400;
		else if(x > 400)
			x = 0;
		else if(y < 0)
			y = 300;
		else if(y > 300)
			y = 0;
	}

	public Point getPoint()
	{
		return new Point((int)x, (int)y);
	}

	public void update()
	{
		for(int i = 0; i < preditors.size(); i++)
		{
			if(preditors.get(i).contains(new Point((int)(x+(2*RADIUS)*Math.cos(direction)),(int)(y+(2*RADIUS)*Math.sin(direction)))))
				brain.turnOn(24);
			if(preditors.get(i).contains(new Point((int)(x+(2*RADIUS)*Math.cos(direction+Math.PI*.5)),(int)(y+(2*RADIUS)*Math.sin(direction+Math.PI*.5)))))
				brain.turnOn(23);
			if(preditors.get(i).contains(new Point((int)(x+(2*RADIUS)*Math.cos(direction+Math.PI*1)),(int)(y+(2*RADIUS)*Math.sin(direction+Math.PI*1)))))
				brain.turnOn(22);
			if(preditors.get(i).contains(new Point((int)(x+(2*RADIUS)*Math.cos(direction+Math.PI*1.5)),(int)(y+(2*RADIUS)*Math.sin(direction+Math.PI*1.5)))))
				brain.turnOn(21);
		}
		brain.update();
		age++;
	}

	public void draw(Graphics g1)
	{
		Graphics2D g = (Graphics2D)g1;
		g.fillOval((int)(x-RADIUS), (int)(y-RADIUS), 2*RADIUS, 2*RADIUS);
		g.setColor(Color.BLACK);
		g.drawLine((int)(x),(int)(y),(int)(x+(2*RADIUS)*Math.cos(direction)),(int)(y+(2*RADIUS)*Math.sin(direction)));
		g.drawLine((int)(x),(int)(y),(int)(x+(2*RADIUS)*Math.cos(direction+Math.PI*.5)),(int)(y+(2*RADIUS)*Math.sin(direction+Math.PI*.5)));
		g.drawLine((int)(x),(int)(y),(int)(x+(2*RADIUS)*Math.cos(direction+Math.PI*1)),(int)(y+(2*RADIUS)*Math.sin(direction+Math.PI*1)));
		g.drawLine((int)(x),(int)(y),(int)(x+(2*RADIUS)*Math.cos(direction+Math.PI*1.5)),(int)(y+(2*RADIUS)*Math.sin(direction+Math.PI*1.5)));
	}


	public boolean contains(Point p)
	{
		double dx = p.x-x;
		double dy = p.y-y;
		return (dx*dx + dy*dy) <= RADIUS*RADIUS;
	}
}