package snow;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import objectdraw.*;

public class Snow extends WindowController implements MouseListener ,MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8897285325847428332L;
	private SnowArray s;
	private Timer tr = new Timer();
	private TimerTask tt;
	private Text txt,txt2,txt3;
	private Color backColor,color,color2;
	private int timing=0,n=0,time=0;;
	private boolean con=true,conti= true, click;
	private int count=100;
	private Point start,stop;	
	private FilledRect fr;
	private Line l1,l2,l3,l4;
	FilledRect back_ground, stickTop, signTop,stick, sign;
	FilledOval hill,sun,sun2;

	public void begin() 
	{
		color= new Color(164,91,38);
		color2= new Color(210,128,66);
		start = new Point(0,0);
		stop = new Point(0,0);
		
		back_ground = new FilledRect(0,0,1024,680,canvas);   //background
		back_ground.setColor(Color.black);
		 
		hill = new FilledOval(0,350,680,300,canvas);  //hill
		hill.setColor(Color.gray);
		
		sun = new FilledOval(-60,350,50,50,canvas);  //sun
		sun.setColor(Color.orange);
		sun2 = new FilledOval(-62,348,54,54,canvas);//sun
		sun2.setColor(Color.yellow);
		sun.sendToFront();
		
		stickTop= new FilledRect(315,200,15,25,canvas); //sticktop that hold snow
		stickTop.sendToBack();
		
		signTop = new FilledRect(330,215,140,10,canvas);  // sign top that hold snow
		stickTop.sendToBack();
		
		l1= new Line(0,0,0,0,canvas);  //4 line for the rectangle
		l2= new Line(0,0,0,0,canvas);
		l3= new Line(0,0,0,0,canvas);
		l4= new Line(0,0,0,0,canvas);
		
		s= new SnowArray(canvas);   //snow object
	
		tt = new TimerTask()
		{
			public void run()
			{
				runs();
			} 
		};
		
		tr.schedule(tt,0,50);//timer
		
		stick = new FilledRect(315,200,15,152,canvas);  //sign and stick
		sign = new FilledRect(330.5,215,140,65,canvas);
		stick.setColor(color);		
		sign.setColor(color2);
		
		txt= new Text("0 second",0,0,canvas);   //Time
		txt2 = new Text("Christmas",340,220,canvas);  //text on sign
		txt3= new Text("Village",392,252,canvas);
		
		txt.setColor(Color.green);
		txt.setBold(true);
		txt.setFontSize(20);txt2.setFontSize(20);
		txt2.setBold(true);
		txt2.setItalic(true);
		txt3.setFontSize(20);
		txt3.setBold(true);
		txt3.setItalic(true);
		
		s.sentToFront();  //sent snow to front
	}
	public void mouseEntered(MouseEvent e) //begin applet when mouse is in the screen
	{
		//s.change();
	//	con=true;
	}
	
	public void mouseExited(MouseEvent e)  // pause the applet when mouse move out window
	{
	//	s.change();    
	//	con=false;
	}
	
	public void mouseClicked(MouseEvent e)
	{
	}

	//invoke when mouse first click, and is the begin point for the rectangle
	public void mousePressed(MouseEvent e)   
	{	if(n==0)
		start.setLocation(e.getPoint());
		n++;
	}
	
	//hide the selected box, and change snow color in side the region
	public void mouseReleased(MouseEvent e)
	{
		//hide the lines
		l1.hide();	l2.hide();	l3.hide();	l4.hide();
		Location loc1 = new Location(start);
		Location loc2 = new Location(stop);
		//create a rectangle bound by the four lines
		fr= new FilledRect(loc1,loc2,canvas);

		s.changeColor(fr);
		n=0;// reset start point when next time mouse pressed
		fr.removeFromCanvas();  // remove rectangle
	}
	public void mouseMoved(MouseEvent e)
	{
	}
	
	//make the select box
	public void mouseDragged(MouseEvent e)
	{
		//show the line and get the stop point
		l1.show();l2.show();l3.show();l4.show();	
		stop.setLocation(e.getX(),e.getY());
		
		//reset the four lines
		Location loc1 = new Location(stop.getX(),start.getY());
		Location loc2 = new Location(start.getX(),stop.getY());
		Location loc3 = new Location(stop);
		Location loc4= new Location(start);
		
		l1.setStart(loc4);l1.setEnd(loc1);
		l2.setStart(loc4);l2.setEnd(loc2);
		l3.setStart(loc3);l3.setEnd(loc1);
		l4.setStart(loc3);l4.setEnd(loc2);

		l1.setColor(Color.cyan);
		l2.setColor(Color.cyan);
		l3.setColor(Color.cyan);
		l4.setColor(Color.cyan);
	}
	private void runs()
	{
		if(con)
		{
			s.fall();   //snow falling
			
			s.stopAt(hill);  //snow stop at the hill, stick top and sign top
			s.stopAt(stickTop);
			s.stopAt(signTop);
	
			timing++;  //counting
			time++;
			if(time%20==0)
			{ 
				if(time<1200)
					txt.setText(time/20+" second"); //timer
				if(time>=1200)
					txt.setText((time/1200)+" minute "+(time%1200)/20+" second");
			}	
			
					
			if(timing>500&&timing%15==0&&timing<=695)  // change background color
			{
				backColor=back_ground.getColor();
				backColor=backColor.brighter();
				back_ground.setColor(backColor);
				back_ground.sendToBack();
			}
			if(timing>=665)
			{			
				count++;
				s.melt();   //snow melt
							
				if(!conti)  //sun fall
				{
					count-=2;
					sun.move(2,400/(count));
					sun2.move(2,400/(count));						
				}
				if(conti)  //sun rise
				{
					sun.move(2,-400/count);
					sun2.move(2,-400/count);
				}
				if(count>280)  // indicate when sun begin to fall
					conti=false;
							
				if(count==1)   // divide by 0 error.
					count--;
				if(count==0)  //  sent sun back to left
				{
					conti=true;
					count=100;
					sun.moveTo(-60,350);
					sun2.moveTo(-62,348);
					sun.sendToFront();
					timing=0;
	//				s.reSet();
				}					
			}
		}
	}
}
