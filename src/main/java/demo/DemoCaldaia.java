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
import javax.swing.JScrollPane;

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


public class DemoCaldaia extends JFrame 
{
	void prepareAndShowGUI(){
    APCombCaldaia panel=new APCombCaldaia();
	
	JScrollPane scrollpane=new JScrollPane(panel);
		
		
		Container cont = getContentPane();
        cont.add(scrollpane);
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
                DemoCaldaia sd = new DemoCaldaia();
                sd.prepareAndShowGUI();
            }
        });
    }
}