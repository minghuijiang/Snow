package snow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestJFrame  extends JFrame implements ActionListener
{
	private final int NUM=200;
	private JButton[] buttons=new JButton[NUM];
	private JTextField tf;
	
	public TestJFrame()
	{
		JPanel p= new JPanel();

		for(int n=0;n<NUM;n++)
		{
			buttons[n]= new JButton("Button");
			buttons[n].setActionCommand(""+n);
			p.add(buttons[n]);
			buttons[n].addActionListener(this);
		}
		
		
		tf = new JTextField(20);
		tf.setEditable(false);
		
		getContentPane().add(p, BorderLayout.CENTER);
		getContentPane().add(tf, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(0,0,800,700);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		int n=Integer.parseInt(ae.getActionCommand());
		int a=0,b=0,c=0,d=0;
		
		if(n%10==0) 	a=1;
		if(n<10) 		b=1;
		if(n%10==9) 	c=1;
		if(n>=190)		d=1;
		
		if(a==0)
			if(buttons[n-1].isEnabled())
				buttons[n-1].setEnabled(false);
			else
				buttons[n-1].setEnabled(true);
		if(b==0)
			if(buttons[n-10].isEnabled())
				buttons[n-10].setEnabled(false);
			else
				buttons[n-10].setEnabled(true);
		if(c==0)
			if(buttons[n+1].isEnabled())
				buttons[n+1].setEnabled(false);
			else
				buttons[n+1].setEnabled(true);
		if(d==0)
			if(buttons[n+10].isEnabled())
				buttons[n+10].setEnabled(false);
			else
				buttons[n+10].setEnabled(true);
		
		tf.setText(n+ " button is clicked, "+(n-1)+ " button should be disable");
	}
	public static void main(String[] b)
	{
		TestJFrame a= new TestJFrame();
	}
}