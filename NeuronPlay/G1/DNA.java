import java.util.*;

public class DNA implements java.io.Serializable
{
	public static final double CHANCE_OF_MUTATION = .01;
	public String data;

	public DNA(String data)
	{
		this.data = data;
	}

	public DNA(int length)
	{
		data = "";
		char[] list = new char[length];
		for(int i = 0; i < length; i++)
		{
			if(Math.random() < .25)
				list[i]='G';
			else if(Math.random() < .333333)
				list[i]= 'T';
			else if(Math.random() < .5)
				list[i]='C';
			else
				list[i]='A';
		}
		data = new String(list);
		//System.out.println(data.length());
	}

	public DNA(DNA p1, DNA p2)
	{
		data = "";
		for(int i = 0; i < Math.min(p1.data.length(), p2.data.length()); i++)
		{
			data = data + ((Math.random() < .5)?p1.data.charAt(i):p2.data.charAt(i));
		}
		if(p1.data.length() > p2.data.length())
			if(Math.random()>.5)
				data = data+p1.data.substring(p2.data.length(), p1.data.length());
		if(p2.data.length() > p1.data.length())
			if(Math.random()>.5)
				data = data+p2.data.substring(p1.data.length(), p2.data.length());

		for(int i = 0; i < data.length(); i++)
		{
			if(Math.random()<CHANCE_OF_MUTATION)
			{
				if(Math.random() < .25)
					data = data.substring(0,i) + 'G' + data.substring(i+1, data.length());
				else if(Math.random() < .333333)
					data = data.substring(0,i) + 'T' + data.substring(i+1, data.length());
				else if(Math.random() < .5)
					data = data.substring(0,i) + 'C' + data.substring(i+1, data.length());
				else
					data = data.substring(0,i) + 'A' + data.substring(i+1, data.length());
			}
		}

		if(Math.random()<CHANCE_OF_MUTATION)
		{
			if(Math.random() < .25)
				data = data + 'G';
			else if(Math.random() < .333333)
				data = data + 'T';
			else if(Math.random() < .5)
				data = data + 'C';
			else
				data = data + 'A';
		}
	}


	public int readUnsignedInt(int location)
	{
		int sum = 0;
		for(int i = 0; i < 15; i++)
		{
			if(data.charAt(location+i) == 'G')
				sum += 0*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'T')
				sum += 1*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'C')
				sum += 2*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'A')
				sum += 3*(int)(Math.pow(4,i));
		}
		return sum;
	}

	public int readSignedInt(int location)
	{
		int sum = 0;
		for(int i = 0; i < 14; i++)
		{
			if(data.charAt(location+i) == 'G')
				sum += 0*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'T')
				sum += 1*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'C')
				sum += 2*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'A')
				sum += 3*(int)(Math.pow(4,i));
		}
		if((data.charAt(location+14) == 'C') || (data.charAt(location+14) == 'A'))
			sum = -sum;

		return sum;
	}

	public double readShortDouble(int location)
	{
		int sumA = 0;
		for(int i = 0; i < 15; i++)
		{
			if(data.charAt(location+i) == 'G')
				sumA += 0*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'T')
				sumA += 1*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'C')
				sumA += 2*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'A')
				sumA += 3*(int)(Math.pow(4,i));
		}
		return sumA/1073741823.0;
	}

	public double readDouble(int location)
	{
		int sumA = 0;
		for(int i = 0; i < 15; i++)
		{
			if(data.charAt(location+i) == 'G')
				sumA += 0*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'T')
				sumA += 1*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'C')
				sumA += 2*(int)(Math.pow(4,i));
			else if(data.charAt(location+i) == 'A')
				sumA += 3*(int)(Math.pow(4,i));
		}
		double value = sumA/1073741823.0;
		int sumB = 0;
		for(int i = 0; i < 2; i++)
		{
			if(data.charAt(location+i+15) == 'G')
				sumB += 0*(int)(Math.pow(4,i));
			else if(data.charAt(location+i+15) == 'T')
				sumB += 1*(int)(Math.pow(4,i));
			else if(data.charAt(location+i+15) == 'C')
				sumB += 2*(int)(Math.pow(4,i));
			else if(data.charAt(location+i+15) == 'A')
				sumB += 3*(int)(Math.pow(4,i));
		}
		value = value*Math.pow(10, sumB);
		if((data.charAt(location+17) == 'C') || (data.charAt(location+17) == 'A'))
			value = -value;
		return value;
	}

	public boolean readBoolean(int location)
	{
		return (data.charAt(location) == 'C') || (data.charAt(location) == 'A');
	}

	public boolean hasInt(int location)
	{
		return (data.length()-location >= 15);
	}

	public boolean hasShortDouble(int location)
	{
		return (data.length()-location >= 15);
	}

	public boolean hasDouble(int location)
	{
		return (data.length()-location >= 18);
	}

	public boolean hasBoolean(int location)
	{
		return (data.length()-location >= 1);
	}

	public boolean hasLength(int location, int length)
	{
		return (data.length()-location >= length);
	}

	public static void main(String[] args)
	{
		DNA data = new DNA("AAAAAAAAAAAAAAAAAA");
		System.out.println(data.hasDouble(0));
		System.out.println(data.readDouble(0));
	}
}