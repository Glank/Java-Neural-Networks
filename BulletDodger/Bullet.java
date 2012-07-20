import java.awt.*;

public class Bullet
{
	public static final int SPEED = 3;
	public static final int RADIUS = 5;
	int x,y;
	boolean horizontal, positive;

	public Bullet(int x, int y, boolean horizontal)
	{
		this.x = x;
		this.y = y;
		this.horizontal = horizontal;
		positive = Math.random()>.5;
	}

	public Bullet()
	{
		this((int)(Math.random()*(World.WIDTH-RADIUS*2)),(int)(Math.random()*(World.HEIGHT-RADIUS*2)),Math.random()>.5);
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawOval(x,y,RADIUS*2,RADIUS*2);
	}

	public void update()
	{
		if(horizontal)
		{
			x+=SPEED*(positive?1:-1);
			if(x >= World.WIDTH-RADIUS*2)
			{
				x = World.WIDTH-RADIUS*2-1;
				positive = false;
			}
			else if(x < 0)
			{
				x = 0;
				positive = true;
			}
		}
		else
		{
			y+=SPEED*(positive?1:-1);
			if(y >= World.HEIGHT-RADIUS*2)
			{
				y = World.HEIGHT-RADIUS*2-1;
				positive = false;
			}
			else if(y < 0)
			{
				y = 0;
				positive = true;
			}
		}
	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,RADIUS*2,RADIUS*2);
	}
}