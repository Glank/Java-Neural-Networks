import java.awt.*;

public class Sprite implements Comparable<Sprite>
{
	public static final int RADIUS = 10;
	public static final int SPEED = 3;
	public static final double TOLERANCE = .001;
	int x,y,age=0,food=0;
	FeedforwardNeuralNet ffnn;
	boolean dead = false;

	public Sprite(int x, int y, FeedforwardNeuralNet ffnn)
	{
		this.x = x;
		this.y = y;
		this.ffnn = ffnn;
		dead = false;
	}

	public Sprite(int x, int y)
	{
		this(x,y,new FeedforwardNeuralNet(6,4,15,4));
	}

	public Sprite()
	{
		this((int)(Math.random()*(World.WIDTH-RADIUS*2)),(int)(Math.random()*(World.HEIGHT-RADIUS*2)));
	}

	public Sprite(FeedforwardNeuralNet ffnn)
	{
		this((int)(Math.random()*(World.WIDTH-RADIUS*2)),(int)(Math.random()*(World.HEIGHT-RADIUS*2)),ffnn);
	}

	public boolean onEdge()
	{
		return (x+RADIUS*2==World.WIDTH-1)||(x==0)||(y+RADIUS*2==World.HEIGHT-1)||(y==0);
	}

	public void update(World w)
	{
		if(!dead)
		{
			age++;
			double[] out = ffnn.getOut(w.getSpriteIn(this));
			int speed = 3;
			//System.out.println(out[0]);
			if(out[2]>0)
				speed++;
			if(out[3]>0)
				speed++;
			if(out[0]>TOLERANCE)
				x+=speed;
			else if(out[0]<-TOLERANCE)
				x-=speed;
			if(out[1]>TOLERANCE)
				y+=speed;
			else if(out[1]<-TOLERANCE)
				y-=speed;
			if(x >= World.WIDTH-RADIUS*2)
				x = World.WIDTH-RADIUS*2-1;
			else if(x < 0)
				x = 0;
			if(y >= World.HEIGHT-RADIUS*2)
				y = World.HEIGHT-RADIUS*2-1;
			else if(y < 0)
				y = 0;
		}
	}

	public void die()
	{
		dead = true;
	}

	public void eat()
	{
		food++;
	}

	public double getDistance(Bullet b)
	{
		return (b.x+b.RADIUS-x-RADIUS)*(b.x+b.RADIUS-x-RADIUS)+(b.y+b.RADIUS-y-RADIUS)*(b.y+b.RADIUS-y-RADIUS);
	}

	public double getDistance(Food b)
	{
		return (b.x+b.RADIUS-x-RADIUS)*(b.x+b.RADIUS-x-RADIUS)+(b.y+b.RADIUS-y-RADIUS)*(b.y+b.RADIUS-y-RADIUS);
	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,RADIUS*2,RADIUS*2);
	}

	public void draw(Graphics g)
	{
		if(!dead)
		{
			g.setColor(Color.BLUE);
			g.drawOval(x,y,RADIUS*2,RADIUS*2);
		}
	}

	public Sprite getChild()
	{
		return new Sprite(ffnn.getChild());
	}

	public static void main(String[] args)
	{
		System.out.println(new Integer(5).compareTo(6));
	}

	public String toString()
	{
		return ""+food;
	}

	public int compareTo(Sprite s)
	{
		if(s.age!=age)
			return s.age-age;
		return s.food-food;
	}
}