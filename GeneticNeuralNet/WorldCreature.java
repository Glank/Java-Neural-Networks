import java.awt.*;
public interface WorldCreature
{
	public boolean contains(Point p);
	public void draw(Graphics g);
	public void update();
}