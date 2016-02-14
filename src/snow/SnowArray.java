package snow;

import objectdraw.*;
import java.awt.*;
import java.util.*;

public class SnowArray extends WindowController 
{
	private final double HIGH=480.0,WIDTH=670.0;   //window size
	private final int SIZE=2000; //# of snow
	private int[] dir=new int[SIZE];  // snow's direction, 1 for left, 2 for right, 0 for stop
	private int[] colorN= new int[SIZE];  // snow's color
	private double[] high=new double[SIZE];  //snow's original high, use for reset snow
	private double[] left=new double[SIZE];   // snow's left border
	private double[] right= new double[SIZE];  // right border
	private Location[] loc= new Location[SIZE];  // snow's location, set in the center of each piece
	private FilledOval[] flake=new FilledOval[SIZE];  // snow
	
	private int num,m=1,resume=2;   // m used for melt, and resume to stop the snow when mouse move out of window
	
	public SnowArray(DrawingCanvas canvas)
	{
		for(num=0;num<=(SIZE-1);num++)  //set snow
		{
			for(int x=0;x<=25;x++)   //26 level of snow.
			{
				if(num%26==x)
				flake[num]= new FilledOval((WIDTH/SIZE*num),-30*x,8,8,canvas);   
				//create the snow at diferent high
			}

			flake[num].setColor(Color.white);   // set color
			flake[num].hide();    // hide the snow
			
			high[num]=flake[num].getY();   //get the high
			left[num]=0;   // initialize a space in left array
			right[num]=0;
			
			if(num%2==0)  //half snow move left and half move right
				dir[num]=1;
			if(num%2==1)
				dir[num]=2;
			
			loc[num]=new Location (flake[num].getX()+4,flake[num].getY()+4);  // set snow's location in center
			colorN[num]=7;  // set color
		}
	}
					
	public void fall() 
	{
		// resume use to stop snow fall when applet stop
		for(num=0;num<=(SIZE-1)&&resume%4==2;num++)
		{
			if(high[num]==flake[num].getY())   // set the border only when snow is begin to fall
			{
				left[num]= flake[num].getX()-15; // snow will move between the border of 15
				right[num] = flake[num].getX()+15;
				flake[num].show();  // show the snow
			}
			
			if(dir[num]==1) // indicate if snow move to left or to the right
			{
				flake[num].move(-(int)(Math.random()*4),(int)(Math.random()*5));			
				if(flake[num].getX()<=left[num]) // check if now go out of border
					dir[num]++;
			}
			else
			if(dir[num]==2) // move to right
			{
				flake[num].move((int)(Math.random()*4),(int)(Math.random()*5));
				if(flake[num].getX()>=right[num])
					dir[num]--;
			}
			
		//	high[num]=flake[num].getY();
		
			// sent snow to the other side when snow move out of window
			if(flake[num].getX()>WIDTH)
			{			
				flake[num].move(-WIDTH,0);
				left[num]=flake[num].getX()-15;  // reset border
				right[num]=flake[num].getX()+15;
			}
			if(flake[num].getX()<0)
			{
				flake[num].move(WIDTH,0);
				left[num]=flake[num].getX()-15;
				right[num]=flake[num].getX()+15;
			}
			//get new location
			loc[num]=new Location(flake[num].getX()+4,flake[num].getY()+4);
		}				
	}
	
	// change the color of snow in a closed region
	public void changeColor(FilledRect fr)
	{
		for(int n=0; n<=(SIZE-1);n++)  
		{
			if(fr.contains(loc[n])&&dir[n]!=0)  // indicate if snow inside the region
				colorN[n]++;
				
			colorN[n]=colorN[n]%8;
			if(colorN[n]%8==0)
				flake[n].setColor(Color.red);
			if(colorN[n]%8==1)
				flake[n].setColor(Color.orange);
			if(colorN[n]%8==2)
				flake[n].setColor(Color.yellow);
			if(colorN[n]%8==3)
				flake[n].setColor(Color.green);
			if(colorN[n]%8==4)
				flake[n].setColor(Color.cyan);		
			if(colorN[n]%8==5)
				flake[n].setColor(Color.blue);
			if(colorN[n]%8==6)
				flake[n].setColor(Color.magenta);
			if(colorN[n]%8==7)
				flake[n].setColor(Color.white);
		}
	}
	public void change()   // indicate if applet stop
	{
		resume+=2;
		if(resume==8)
			resume=0;
	}
	
	public void stopAt(FilledOval fo)  // snow will stop if inside this region fo.
	{
		for(int n=0;n<=(SIZE-1);n++)
		{	
			int cou=0;
			if(fo.contains(loc[n])&&dir[n]!=0)
			{
				for(int m=0; m<=(SIZE-1);m++)
				{
					if(flake[n].contains(loc[m])&&m!=n)
					{ 
						cou++;
						m=SIZE-1; 
					}
				}
				if(cou==0)
					dir[n]=0;	
			}
		}
	}
	public void stopAt(FilledRect fo)
	{
		for(int n=0;n<=(SIZE-1);n++)
		{	
			int cou=0;
			if(fo.contains(loc[n])&&dir[n]!=0)
			{
				for(int m=0; m<=(SIZE-1);m++)
				{
					if(flake[n].contains(loc[m])&&m!=n)
					{ 
						cou++;
						m=SIZE-1; 
					}	
				}
				if(cou==0)
					dir[n]=0;	
			}
		}
	}
	
	public void sentToFront()  // sent snow to the front
	{
		for(int n=0;n<=(SIZE-1);n++)
		{
			FilledOval s =flake[n];
			s.sendToFront();
			flake[n]=s;
		}
	}
	
	public void melt()  // snow melt
	{
		if(m<=1500)
		for(int k=1; k<=5;k++)
		{
			if(k*m<SIZE)
				flake[m*k].hide();
		}
		m++;
		if(m>390)
		{	
			for(num=1900;num<SIZE;num++)
			{	
				flake[num].hide();
			}
		}
	}
}
