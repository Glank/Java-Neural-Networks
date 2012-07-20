import java.util.*;
import java.awt.*;

public class Bot
{
	public static final int RADIUS = 10;
	double direction;
	int x, y, food;
	FeedforwardNeuralNet nn;
	double foodAngle = 0;

	public Bot(FeedforwardNeuralNet nn)
	{
		this.nn = nn;
		x = (int)(Math.random()*BotEnvironment.WIDTH);
		y = (int)(Math.random()*BotEnvironment.HEIGHT);
		food = 0;
		direction = Math.random()*Math.PI*2;
	}

	public FeedforwardNeuralNet getBrain()
	{
		return nn;
	}

	public void eat()
	{
		food++;
	}

	public int getFood()
	{
		return food;
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

	public void update(Vector<Point> food)
	{
		if(food.size()>0)
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

			double left = 0, right = 0;
			if(direction < ang1)
			{
				if(ang1-direction < Math.PI)
					right = 2;
				else
					left = 2;
			}
			else
			{
				if(direction-ang1 < Math.PI)
					left = 2;
				else
					right = 2;
			}
			foodAngle = ang1;

			double[] out = nn.getOut(left,right);

			//System.out.println(out[0]);
			if(out[0]>0)
			{
				//System.out.println("Reached");
				moveForward();
			}
			if(out[1]>0)
				turnLeft();
			if(out[2]>0)
				turnRight();
		}
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillOval(x-RADIUS, y-RADIUS, RADIUS*2, RADIUS*2);
		g.setColor(Color.RED);
		g.drawLine(x,y,x+(int)(Math.cos(direction)*RADIUS+.5), y+(int)(Math.sin(direction)*RADIUS+.5));
		g.setColor(Color.WHITE);
		g.drawLine(x,y,x+(int)(Math.cos(foodAngle)*RADIUS+.5), y+(int)(Math.sin(foodAngle)*RADIUS+.5));
	}
}