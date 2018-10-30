package demo; 
import com.antoiovi.unicig.fluidi.comb.Combustibile;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.Date;

import java.awt.Color;
 import java.awt.Font;


import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;

import java.awt.Rectangle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import javax.swing.SwingUtilities;


public class Caldaia extends JFrame 
{
    JSpinner s;
    SpinnerNumberModel model ;
    JSpinner.NumberEditor editor;
    String[] sinput={"Combustibile","Potenza","Temperatura fumi [°C]"};
	String[] soutput={"Portata massica fumi [g/s]","CostElasticita","CapTermica", "TenoreH2O [%]","Pressione parziale del vapore acqueo[Pa]"};
	JLabel[]     lblInput;
	JTextField[] txtInput;
	
	JLabel[]     lblOutput;
	JTextField[] txtOutput;
    
	JComponent[] jinput;
	
	
	JButton bCalcola;
	
	String[] combustibili;
	JComboBox combList;
		
    public void prepareAndShowGUI()
    {
    
	
	GridBagLayout gridBagLayout = new GridBagLayout();
		JPanel panel=new JPanel(new GridBagLayout());
		combustibili=Combustibile.combustibile;
		combList= new JComboBox(combustibili);
		combList.setSelectedIndex(4);
		jinput=new JComponent[sinput.length];
		
	
		
	    lblInput=new JLabel[sinput.length];
		txtInput=new JTextField[sinput.length-1];
		
		lblOutput=new JLabel[soutput.length];
		txtOutput=new JTextField[soutput.length];
		
		for(int x=0;x<sinput.length;x++){
		if(x==0){
			jinput[x]=combList;
			
		}else{
			txtInput[x-1]=new JTextField();
			txtInput[x-1].setColumns(10);
			jinput[x]=txtInput[x-1];
		}
		
		
	
	}
		
	int X=0;
	int Y=0;
	GridBagConstraints c = new GridBagConstraints();
	//c.fill = GridBagConstraints.HORIZONTAL;
	c.insets = new Insets(0, 0, 5, 5);
	//Titolo input
	X=0;
	c.gridy = Y;
	//c.weighty = 1.0;   //request any extra vertical space
	c.ipady = 40;      //make this component tall
	c.gridwidth = 2;
	JLabel titlinput=new JLabel("Dati caldaia :")	;
	titlinput.setFont(new Font("Courier New", Font.BOLD, 24));
    titlinput.setForeground(Color.GRAY);
	panel.add(titlinput,c);
	
	c.weighty =0;   //reset
	c.ipady = 0;    //reset
	Y+=2;
	
	
	// Input
	c.gridy = Y;
	c.gridwidth = 1;
	for(int x=0;x<sinput.length;x++){
		c.gridx = X;
		lblInput[x]=new JLabel(sinput[x]);
		panel.add(lblInput[x], c);
		X++;
		c.gridx = X;
		panel.add(jinput[x],c);
		X++;
	}
	// Button OK
	Y+=2;
	X=2;
	bCalcola=new JButton("Calcola")	;
	//c.weighty = 1.0;   //request any extra vertical space
	c.gridx = X;
	c.gridy = Y;
	c.gridwidth = 2;
	panel.add(bCalcola,c);
	
	X=0;
	Y+=2;
	c.gridx = X;
	c.gridy = Y;
	c.gridwidth = 2;
	//c.weighty = 1.0;   //request any extra vertical space
	c.ipady = 30;      //make this component tall

	JLabel titleout=new JLabel("Risultati del calcolo :")	;
	titleout.setFont(new Font("Courier New", Font.BOLD, 24));
    titleout.setForeground(Color.GRAY);
	panel.add(titleout,c);
	c.gridwidth = 1;
	
	// Output
	Y+=2;
	c.gridy = Y;
	c.ipady = 0;       //reset to default
	c.weighty =0;		//reset to default
	X=0;
int col=0;
int row=Y;
	for(int x=0;x<soutput.length;x++){
		c.gridx = X;
		c.gridy = Y;
		lblOutput[x]=new JLabel(soutput[x]);
		panel.add(lblOutput[x], c);
		X++;
		c.gridx = X;
		txtOutput[x]=new JTextField();
		txtOutput[x].setColumns(10);
		panel.add(txtOutput[x],c);
		X++;
		col++;
		if(col>2){
			col=0;
			X=0;
			Y+=2;
		}
		
	}			
	

		
		
		Container cont = getContentPane();
        cont.add(panel);
         pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setVisible(true);
    }

    public static void main(String args[]) 
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                Caldaia sd = new Caldaia();
                sd.prepareAndShowGUI();
            }
        });
    }
}