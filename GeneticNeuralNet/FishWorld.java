import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;

public class FishWorld extends ListeningGameComponent
{
	public ArrayList<LittleFish> littleFish;
	public ArrayList<LittleFish> bigFish;
	public static final int MAX_AGE = 200;
	public boolean start = false;

	public FishWorld()
	{
		super(400, 300);
		littleFish = new ArrayList();
		bigFish = new ArrayList();
		for(int i = 0; i < 20; i++)
			littleFish.add(new LittleFish((int)(Math.random()*400),(int)(Math.random()*300)));

		for(int i = 0; i < 10; i++)
			bigFish.add(new LittleFish((int)(Math.random()*400),(int)(Math.random()*300)));

		for(int i = 0; i < littleFish.size(); i++)
			littleFish.get(i).preditors = bigFish;

		for(int i = 0; i < bigFish.size(); i++)
			bigFish.get(i).preditors = littleFish;

		delay = 50;
		start = true;
	}

	public void update()
	{
		if(start)
		{
			for(int i = 0; i < littleFish.size(); i++)
			{
				littleFish.get(i).update();
			}
			for(int i = 0; i < bigFish.size(); i++)
			{
				bigFish.get(i).update();
				for(int a= 0; a < littleFish.size(); a++)
				{
					if(bigFish.get(i).contains(littleFish.get(a).getPoint()))
					{
						bigFish.get(i).age-=50;
						littleFish.remove(a);
						littleFish.add(a,new LittleFish(littleFish.get((int)(Math.random()*littleFish.size())),littleFish.get((int)(Math.random()*littleFish.size()))));
					}
				}
			}

			for(int i = 0; i < littleFish.size(); i++)
			{
				if(littleFish.get(i).age > MAX_AGE)
				{
					littleFish.remove(i);
					littleFish.add(i,new LittleFish(littleFish.get((int)(Math.random()*littleFish.size())),littleFish.get((int)(Math.random()*littleFish.size()))));
				}
			}

			for(int i = 0; i < bigFish.size(); i++)
			{
				if((bigFish.get(i).age > MAX_AGE) || (Math.random() < .0008))
				{
					bigFish.remove(i);
					bigFish.add(i,new LittleFish(bigFish.get((int)(Math.random()*bigFish.size())),bigFish.get((int)(Math.random()*bigFish.size()))));
				}
			}
		}
	}

	public void draw(Graphics g)
	{
		if(start)
		{

			for(int i = 0; i < littleFish.size(); i++)
			{
				g.setColor(Color.BLUE);
				littleFish.get(i).draw(g);
			}
			for(int i = 0; i < bigFish.size(); i++)
			{
				g.setColor(Color.RED);
				bigFish.get(i).draw(g);
			}
		}
	}

	public static void main(String[] args)
	{
		(new FishWorld()).makeTestWindow();
	}
}