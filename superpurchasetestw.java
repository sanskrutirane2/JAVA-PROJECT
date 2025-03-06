import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class super1 extends JFrame implements ActionListener
{
JMenuBar mb;
JMenu mnu1,mnu2;
JMenuItem item1[],item2[];
JLabel lbl[];
JList lst;
JTextField txt[],txtno,txtqty;
JPanel userpnl,adminpnl,mainpnl;
String label1[]={"User","Admin","About","Exit"},label2[]={"Create Product","Display Products","Search Product","About","Back to main view","Exit"};
String label3[]={"Product no","Name","Qty","Price"};
JButton btn1,btn2,btnpurchase;
Container c;
JTextArea ta;
CardLayout cardl;

public super1()
	{
	c=getContentPane();
	cardl=new CardLayout(5,5);
	mainpnl=new JPanel(cardl);
	c.add(mainpnl,"Center");

	mb=new JMenuBar();
	c.add(mb,"North");
		
	mnu1=new JMenu("Tasks");
	mb.add(mnu1);
	
	item1=new JMenuItem[4];
	for(int i=0;i<item1.length;i++)
		{
		item1[i]=new JMenuItem(label1[i]);
		item1[i].addActionListener(this);
		mnu1.add(item1[i]);
		}
	
	mnu2=new JMenu("Tasks");	
	item2=new JMenuItem[6];
	for(int i=0;i<item2.length;i++)
		{
		item2[i]=new JMenuItem(label2[i]);
		item2[i].addActionListener(this);
		mnu2.add(item2[i]);
		}
	adminpnl=new JPanel(new BorderLayout());		
	JPanel t1=new JPanel(new GridLayout(4,2));
	JPanel t2=new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	txt=new JTextField[4];
	lbl=new JLabel[4];
	Font f=new Font("sans-serif",Font.BOLD,15);
	for(int i=0;i<lbl.length;i++)
		{
		lbl[i]=new JLabel(label3[i],JLabel.CENTER);
		lbl[i].setFont(f);
		t1.add(lbl[i]);

		txt[i]=new JTextField();
		txt[i].setFont(f);
		t1.add(txt[i]);
		txt[i].setDisabledTextColor(Color.BLACK);
		}

	btn1=new JButton("Add");
	btn1.addActionListener(this);
	
	btn2=new JButton("Exit");
	btn2.addActionListener(this);
		
	t2.add(btn1);t2.add(btn2);
	adminpnl.add(t1,"Center");
	adminpnl.add(t2,"South");
	
	userpnl=new JPanel(new BorderLayout());
	ta=new JTextArea();
	ta.setFont(new Font("Monospaced",Font.BOLD,15));
	userpnl.add(ta,"Center");
	
	String s=displayall('o');
	ta.setText(s);
		
	JPanel bpnl=new JPanel(new FlowLayout(FlowLayout.RIGHT));
	bpnl.add(new JLabel("Enter Product no. :"));
	txtno=new JTextField(10);
	bpnl.add(txtno);
	bpnl.add(new JLabel("Enter Quantity :"));
	txtqty=new JTextField(10);
	bpnl.add(txtqty);
	btnpurchase=new JButton("Purchase");
	btnpurchase.addActionListener(this);
	bpnl.add(btnpurchase);
	
	userpnl.add(bpnl,"South");

	mainpnl.add(userpnl);
	mainpnl.add(adminpnl);
	}

public void actionPerformed(ActionEvent e)
	{
	String cmd=e.getActionCommand();
		
	if(cmd.equalsIgnoreCase("exit"))
		{
		JOptionPane.showMessageDialog(null,"Thank you for using super market purchase ...");
		System.exit(0);
		}

	if(cmd.equalsIgnoreCase("admin"))
		{
		cardl.next(mainpnl);
		mb.remove(mnu1);
		mb.add(mnu2);	
		}
	if(cmd.equalsIgnoreCase("add"))
		add();
	if(cmd.equalsIgnoreCase("create product"))
		{
		txt[0].setText("");
		for(int i=1;i<txt.length;i++)
			{
			txt[i].setEnabled(true);
			txt[i].setText("");
			}
		btn1.setText("Add");
		}
	if(cmd.equalsIgnoreCase("display products"))
		displayall('d');

	if(cmd.equalsIgnoreCase("about"))
		JOptionPane.showMessageDialog(this,"This mini project is developed by\n1 : \n2 : \n3 : \n4 : \n for college submission.");

	if(cmd.equalsIgnoreCase("back to main view"))
		{
		mb.remove(mnu2);
		mb.add(mnu1);
		cardl.previous(mainpnl);
		}

	if(cmd.equalsIgnoreCase("search product"))
		{
		txt[0].setText("");
		for(int i=1;i<txt.length;i++)
			{
			txt[i].setEnabled(false);
			txt[i].setText("");
			}
		btn1.setText("Search");
		}
	if(cmd.equalsIgnoreCase("search"))
		search(txt[0].getText(),'o');
	
	if(cmd.equalsIgnoreCase("purchase"))
		{
		if(txtno.getText().equals("")||txtqty.getText().equals(""))
			JOptionPane.showMessageDialog(this,"Fields cannot be blank...");
		else
			purchase();
		}
	String s=displayall('o');
	ta.setText(s);
	}

public String width(String s,int n)
	{
	int i=1;

	while(s.length()<=n)
		s=s+" ";
		
	return s;
	}

public void add()
	{
	try{
	String no,nm,qty,price;
	
	no=txt[0].getText();	
	nm=txt[1].getText();
	qty=txt[2].getText();	
	price=txt[3].getText();

	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection con=DriverManager.getConnection("jdbc:odbc:ccit");
	PreparedStatement psmt=con.prepareStatement("insert into market values(?,?,?,?)");
	psmt.setString(1,no);
	psmt.setString(2,nm);
	psmt.setString(3,price);
	psmt.setString(4,qty);
	
	int status=psmt.executeUpdate();
	if(status==1)
		JOptionPane.showMessageDialog(this,"Product Added ...");
	else	
		JOptionPane.showMessageDialog(this,"Product not Added ...");
	for(int i=0;i<txt.length;i++)
		txt[i].setText("");
	con.close();
	}
	catch(Exception ex)
		{
		JOptionPane.showMessageDialog(this,"! Error : "+ex);
		}
	}	

public String displayall(char status)
	{
	String s="";
	try{
	int ch;

	s=width("Product no.",10)+width("Name of Product",30)+width("Price",10)+width("Quantity",10)+"\n";
	for(int i=0;i<65;i++)	
		s+="-";
	s+="\n";
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	
	Connection con=DriverManager.getConnection("jdbc:odbc:ccit");
	Statement smt=con.createStatement();	
	ResultSet rs=smt.executeQuery("select * from market");
	while(rs.next())
		{
		s+=width(rs.getString(1),10);
		s+=width(rs.getString(2),30);
		s+=width(rs.getString(3),10);
		s+=width(rs.getString(4),10)+"\n";
		}
	con.close();
	if(status=='d')
		{
		display d=new display();
		d.disp(s);
		d.setSize(600,400);
		d.setLocation(300,200);
		d.setVisible(true);
		}
	}catch(Exception ex)
		{
		JOptionPane.showMessageDialog(null,"! ERROR : "+ex);
		}
	return s;
	}

public boolean search(String data,char ch)
	{
	String s,s1="";
	boolean recfound=false;
	try{
	int pno=Integer.parseInt(data),f=0;
	
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	
	Connection con=DriverManager.getConnection("jdbc:odbc:ccit");
	Statement smt=con.createStatement();	
	ResultSet rs=smt.executeQuery("select * from market where productid="+pno);
	if(rs.next())
		{
		recfound=true;
		if(ch=='o')
			{
			String prno=rs.getString(1);
			String name=rs.getString(2);
			String price=rs.getString(3);
			String qty=rs.getString(4);
		                       
			txt[0].setText(prno);	
			txt[1].setText(name);
			txt[2].setText(price);
			txt[3].setText(qty);
			}
		else
			System.out.println();	
		}
	else if(ch=='o')
		{
		JOptionPane.showMessageDialog(this,"Product not found ...");
		for(int i=0;i<txt.length;i++)
			txt[i].setText("");
		}
	con.close();
	}catch(Exception ex)
		{
		JOptionPane.showMessageDialog(this,ex+"");
		}
	
	return recfound;		
	}

public void purchase()
	{
	try{
	String s=txtno.getText();
			
	boolean status=search(s,'p');	
	if(!status)
		JOptionPane.showMessageDialog(this,"Product not found ...");
	else
		{
		String q=txtqty.getText();
		int tqty=Integer.parseInt(q);
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:ccit");
		Statement smt=con.createStatement();
		ResultSet rs=smt.executeQuery("select productqty from market where productid="+s);
		int qty=0;
		if(rs.next())	
			qty=Integer.parseInt(rs.getString(1));
		if(qty<tqty)
			JOptionPane.showMessageDialog(this,"Invalid Quantity ..");
		else
			{
			int n=smt.executeUpdate("update market set productqty=productqty-"+tqty+" where productid="+s);						
			if(n==1)
				JOptionPane.showMessageDialog(this,"Product purchased ...");
			else
				JOptionPane.showMessageDialog(this,"Product not purchased ...");
		
			ta.setText(displayall('o'));
			}
		}
	}catch(Exception ex)
		{
		JOptionPane.showMessageDialog(this,ex+"");
		}
	txtno.setText("");
	txtqty.setText("");
	}
}
class display extends JDialog
{
JTextArea ta;

public void disp(String s)
	{
	setModal(true);
	Container c=this.getContentPane();	
	ta=new JTextArea(s);
	ta.setFont(new Font("Monospaced",Font.BOLD,15));
	c.add(new JScrollPane(ta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),"Center");
	}
}


class superpurchasetestw
{
public static void main(String args[])
	{
	super1 s=new super1();
	s.setSize(600,400);
	s.setLocation(400,200);
	s.setVisible(true);
	}
}

