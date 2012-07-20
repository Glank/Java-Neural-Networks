import java.awt.*;
import java.util.*;

public class World extends GameComponent
{
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final int BULLETS = 10;
	public static final int SPRITES = 15;
	public static final int FOODS = 50;
	public static final int MAX_UPDATES = 1000;
	Vector<Bullet> bullets;
	Vector<Sprite> sprites;
	Vector<Food> foods;
	int updates = 0;
	int generation = 0;
	boolean go = false;

	public World()
	{
		super(WIDTH,HEIGHT);
		bullets = new Vector();
		sprites = new Vector();
		foods = new Vector();

		for(int i = 0; i < BULLETS; i++)
			bullets.add(new Bullet());

		for(int i = 0; i < SPRITES; i++)
			sprites.add(new Sprite());

		for(int i = 0; i < FOODS; i++)
			foods.add(new Food());

		go = true;
		System.out.println("0\n--------------------");
	}

	public void update()
	{
		if(go)
		{
			if(updates%(MAX_UPDATES/20)==(MAX_UPDATES/20)-1)
				System.out.print('-');
			updates++;
			for(int i = 0; i < bullets.size(); i++)
				bullets.get(i).update();
			for(int i = 0; i < sprites.size(); i++)
				sprites.get(i).update(this);

			for(int i = 0; i < foods.size(); i++)
			{
				for(int j = 0; j < sprites.size(); j++)
				{
					if(sprites.get(j).getRectangle().intersects(foods.get(i).getRectangle()))
					{
						foods.remove(i);
						i--;
						sprites.get(j).eat();
						j = sprites.size();
					}
				}
			}

			for(int i = 0; i < bullets.size(); i++)
			{
				for(int j = 0; j < sprites.size(); j++)
				{
					if(sprites.get(j).getRectangle().intersects(bullets.get(i).getRectangle()))
					{
						sprites.get(j).die();
					}
				}
			}

			boolean finished = true;
			for(int i = 0; i < sprites.size(); i++)
			{
				if(!sprites.get(i).dead)
				{
					finished = false;
					break;
				}
			}
			if(finished)
				nextGeneration();

			if(updates >= MAX_UPDATES)
				nextGeneration();

			if(foods.size()==0)
				nextGeneration();
		}
	}

	public void draw(Graphics g)
	{
		if(go)
		{
			for(int i = 0; i < foods.size(); i++)
				foods.get(i).draw(g);
			for(int i = 0; i < bullets.size(); i++)
				bullets.get(i).draw(g);
			for(int i = 0; i < sprites.size(); i++)
				sprites.get(i).draw(g);
		}
	}

	public void nextGeneration()
	{
		System.out.println("\n"+(++generation));
		bullets = new Vector();
		Vector<Sprite> nsprites = new Vector();
		foods = new Vector();

		for(int i = 0; i < BULLETS; i++)
			bullets.add(new Bullet());

		for(int i = 0; i < FOODS; i++)
			foods.add(new Food());

		Collections.sort(sprites);
		System.out.println(sprites.get(0));
		System.out.println(sprites.get(1));
		for(int i = 0; i < SPRITES; i++)
			nsprites.add(sprites.get((int)(Math.random()*5)).getChild());

		updates = 0;
		sprites = nsprites;
		System.out.println("--------------------");
	}

	public double[] getSpriteIn(Sprite s)
	{
		//Sort bullets by distance
		boolean sorted = false;
		int[] minRefs = new int[bullets.size()];
		for(int i = 0; i < minRefs.length; i++)
			minRefs[i] = i;
		while(!sorted)
		{
			sorted = true;
			for(int i = 1; i < bullets.size(); i++)
			{
				if(s.getDistance(bullets.get(minRefs[i-1])) > s.getDistance(bullets.get(minRefs[i])))
				{
					sorted = false;
					int t = minRefs[i-1];
					minRefs[i-1] = minRefs[i];
					minRefs[i] = t;
				}
			}
		}

		//get closest food
		int closest = 0;
		double d = s.getDistance(foods.get(0));
		for(int i = 1; i < foods.size(); i++)
		{
			double d2 = s.getDistance(foods.get(i));
			if(d2<d)
			{
				d=d2;
				closest = i;
			}
		}

		double[] in = new double[6];
		for(int i = 0; i < 1; i++)
		{
			Bullet b = bullets.get(minRefs[i]);
			in[i*3] = (b.x-s.x>0)?2:-2;
			in[i*3+1] = (b.y-s.y>0)?2:-2;
			//in[i*5+2] = b.horizontal?2:-2;
			//in[i*5+3] = b.positive?2:-2;
			in[i*3+2] = (s.getDistance(b)<s.RADIUS*5)?2:0;
		}
		Food f = foods.get(closest);
		in[3] = (f.x-s.x>0)?2:-2;
		in[4] = (f.y-s.y>0)?2:-2;
		in[5] = s.onEdge()?2:0;
		return in;
	}

	public static void main(String[] args)
	{
		new World().makeTestWindow();
	}
}