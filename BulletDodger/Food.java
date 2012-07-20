import java.awt.*;

public class Food
{
	public static final int SPEED = 3;
	public static final int RADIUS = 5;
	int x,y;

	public Food(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Food()
	{
		this((int)(Math.random()*(World.WIDTH-RADIUS*2)),(int)(Math.random()*(World.HEIGHT-RADIUS*2)));
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.drawOval(x,y,RADIUS*2,RADIUS*2);
	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,RADIUS*2,RADIUS*2);
	}
}