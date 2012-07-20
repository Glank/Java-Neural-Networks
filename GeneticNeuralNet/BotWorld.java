import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;

public class BotWorld extends ListeningGameComponent
{
	public ArrayList<MoveBot> bots;
	public ArrayList<Point> food;
	public boolean start = false;

	public BotWorld()
	{
		super(400, 300);
		bots = new ArrayList();
		bots.add(new MoveBot(100,100));
		for(int i = 1; i < 25; i++)
		{
			bots.add(new MoveBot(100,100));
		}
		food = new ArrayList();
		for(int i = 0; i < 50; i++)
			food.add(new Point((int)(Math.random()*400),(int)(Math.random()*300)));
		delay = 50;
		start = true;
	}

	public void update()
	{
		if(start)
		{
			ArrayList<Rectangle> bounds = new ArrayList();
			for(int i = 0; i < bots.size(); i++)
			{
				bounds.add(bots.get(i).getBounds());
				for(int f = 0; f < food.size(); f++)
				{
					if(bots.get(i).getBounds().contains(food.get(f)))
					{
						bots.get(i).touches--;
						food.remove(f);
						food.add(f,new Point((int)(Math.random()*400),(int)(Math.random()*300)));
					}
				}
			}

			for(int i = 0; i < bots.size(); i++)
			{
				bots.get(i).update(bounds);
			}

			for(int i = 0; i < bots.size(); i++)
			{
				if((bots.get(i).touches > 30) || (Math.random() < .0005))
				{
					bots.remove(i);
					bots.add(i,new MoveBot(bots.get((int)(Math.random()*bots.size())),bots.get((int)(Math.random()*bots.size()))));
				}
			}
		}
	}

	public void draw(Graphics g)
	{
		if(start)
		{
			if(bots != null)
			{
				for(int i = 0; i < bots.size(); i++)
				{
					bots.get(i).draw(g);
				}
			}
			g.setColor(Color.GREEN);
			for(int i = 0; i < food.size(); i++)
			{
				g.drawOval(food.get(i).x, food.get(i).y, 1, 1);
			}
		}
	}

	public static void main(String[] args)
	{
		(new BotWorld()).makeTestWindow();
	}
}