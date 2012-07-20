import java.util.*;
import java.awt.*;

public class Bot
{
	public static final int RADIUS = 10;
	double direction;
	int x, y, food;
	DNA dna;
	NeuralNet nn;

	public Bot(DNA dna)
	{
		this.dna = dna;
		nn = new NeuralNet(dna);
		x = (int)(Math.random()*BotEnvironment.WIDTH);
		y = (int)(Math.random()*BotEnvironment.HEIGHT);
		food = 0;
		direction = Math.random()*Math.PI*2;
	}

	public void eat()
	{
		food++;
	}

	public int getFood()
	{
		return food;
	}

	public DNA getDNA()
	{
		return dna;
	}

	public double distanceFrom(Point p)
	{
		return Math.sqrt((p.getX()-x)*(p.getX()-x)+(p.getY()-y)*(p.getY()-y));
	}

	public void turnLeft()
	{
		direction+=.1;
		while(direction >= Math.PI*2)
			direction-=Math.PI*2;
	}

	public void turnRight()
	{
		direction-=.1;
		while(direction < 0)
			direction+=Math.PI*2;
	}

	public void moveForward()
	{
		x+=(int)(Math.cos(direction)*RADIUS/4+.5);
		y+=(int)(Math.sin(direction)*RADIUS/4+.5);
		if(x > BotEnvironment.WIDTH)
			x=BotEnvironment.WIDTH;
		if(y > BotEnvironment.HEIGHT)
			y=BotEnvironment.HEIGHT;
		if(x < 0)
			x = 0;
		if(y < 0)
			y = 0;
	}

	public void senseRight()
	{
		nn.setState(0,1);
	}

	public void senseLeft()
	{
		nn.setState(1,1);
	}

	public void update(Vector<Point> food)
	{
		int shortest = 0;
		double distance = Math.sqrt((food.get(0).getX()-x)*(food.get(0).getX()-x)+(food.get(0).getY()-y)*(food.get(0).getY()-y));
		for(int i = 1; i < food.size(); i++)
		{
			double newDistance = Math.sqrt((food.get(i).getX()-x)*(food.get(i).getX()-x)+(food.get(i).getY()-y)*(food.get(i).getY()-y));
			if(newDistance < distance)
			{
				shortest = i;
				distance = newDistance;
			}
		}

		double ang1 = Math.acos((food.get(shortest).getX()-x)/distance);
		double ang2 = Math.asin((food.get(shortest).getY()-y)/distance);
		if(ang2 < 0)
			ang1 = Math.PI*2-ang1;

		if(direction < ang1)
		{
			if(ang1-direction < Math.PI)
				senseRight();
			else
				senseLeft();
		}
		else
		{
			if(direction-ang1 < Math.PI)
				senseLeft();
			else
				senseRight();
		}

		nn.iterate();

		if(nn.getState(2)>.5)
		{
			//System.out.println("Reached");
			moveForward();
		}
		if(nn.getState(3)>.5)
			turnLeft();
		if(nn.getState(4)>.5)
			turnRight();
	}

	public void draw(Graphics g)
	{
		g.setColor(new Color(dna.readUnsignedInt(0)%256, dna.readUnsignedInt(15)%256, dna.readUnsignedInt(30)%256));
		g.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
		g.setColor(Color.RED);
		g.drawLine(x,y,x+(int)(Math.cos(direction)*RADIUS+.5), y+(int)(Math.sin(direction)*RADIUS+.5));
	}
}