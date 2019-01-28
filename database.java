//Program incorporating elements of JDBC and mySQL
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;


class GUI extends JFrame implements WindowListener
{
   private JLabel studentId;
   private JLabel firstName;
   private JLabel lastName;
   private JLabel address;
   private JLabel program;
   private JLabel gender;
   private JLabel country;
   private JButton newButton;
   private JButton deleteButton;
   private JButton updateButton;
   private JButton refreshButton;
   private JButton displayTable;
   private JMenu file;
   private JMenu help;
   private JRadioButton male;
   private JRadioButton female;
   private ButtonGroup genderGroup;
   private JTable table;
   private JTextField sID;
   private JTextField fName;
   private JTextField lName;
   private JTextField add;
   private JComboBox course;
   private JTextField countryName;
   private JPanel buttonArea;
   private JPanel infoArea;
   private JPanel formArea;
   private Connection con;
   private Statement st;
   private ResultSet rs;
   private int counter;
   private String id;
   private String g;
   GUI()
   {
       counter=0;
       con=null;
       st=null;
       rs=null;
       g="";
       String [] courseNames={"Computer Science and Engineering","Mechanical Engineering","Electronics and Telecommuncation Engineering","Civil Engineering","Information Technology"};
       setLayout(new BorderLayout());
       infoArea=new JPanel();
       formArea=new JPanel();
       this.add(infoArea,BorderLayout.CENTER);
       infoArea.setVisible(true);
       infoArea.setSize(500,500);
       buttonArea=new JPanel();
       buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,50,50));
       infoArea.setLayout(new BorderLayout());
       infoArea.add(buttonArea,BorderLayout.SOUTH);
       newButton=new JButton("New");
       newButton.addActionListener(
       new ActionListener()
       {
           public void actionPerformed(ActionEvent e)
           {
               try
               {
                   Class.forName("com.mysql.jdbc.Driver");
                   con=DriverManager.getConnection("jdbc:mysql://localhost/student","root","root");
                   st=con.createStatement();
                   String gender="";
                   if(male.isSelected())
                       gender="Male";
                   else if(female.isSelected())
                       gender="Female";
                   st.executeUpdate("INSERT INTO studentdatabase VALUES"+"('"+sID.getText()+"','"+fName.getText()+"','"+lName.getText()+"','"+add.getText()+"','"+course.getSelectedItem()+"','"+countryName.getText()+"','"+gender+"')");
                   JOptionPane.showMessageDialog(null,"Record succesfully inserted","Insertion Succesful",JOptionPane.INFORMATION_MESSAGE);
                   sID.setText("");
                   fName.setText("");
                   lName.setText("");
                   add.setText("");
                   course.setSelectedIndex(0);
                   countryName.setText("");
                   genderGroup.clearSelection();
               }
               
               catch(SQLException sqlException)
               {
                   sqlException.printStackTrace();
                   System.exit(1);
               }
               catch(ClassNotFoundException classNotFound)
               {
                   classNotFound.printStackTrace();
                   System.exit(1);
               }
               finally
               {
                   try
                   {
                       st.close();
                       con.close();
                   }
                   catch(Exception exception)
                   {
                       exception.printStackTrace();
                       System.exit(1);
                   }
               }
           }   
       });
       
       deleteButton=new JButton("Delete");
       deleteButton.addActionListener(
       new ActionListener()
       {
           int check=0;
           public void actionPerformed(ActionEvent e)
           {
               id=JOptionPane.showInputDialog(null,"Enter the ID of the student whose record you wish to delete");
               try
               {
                   Class.forName("com.mysql.jdbc.Driver");
                   con=DriverManager.getConnection("jdbc:mysql://localhost/student","root","root");
                   st=con.createStatement();
                   rs=st.executeQuery("SELECT * from studentdatabase WHERE ID LIKE"+"'"+id+"'");
                    if(rs.first()==false)
                    {
                    JOptionPane.showMessageDialog(null,"No such student on record","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                       st.executeUpdate("DELETE FROM studentdatabase WHERE ID = "+"'"+id+"'");
                       JOptionPane.showMessageDialog(null,"Record succesfully deleted","Information",JOptionPane.INFORMATION_MESSAGE);
                        sID.setText("");
                        fName.setText("");
                        lName.setText("");
                        add.setText("");
                        course.setSelectedIndex(0);
                        genderGroup.clearSelection();
                        countryName.setText("");
                    }
               }
               catch(SQLException sqlException)
               {
                           sqlException.printStackTrace();
                           System.exit(1);
               }
               catch(ClassNotFoundException classNotFound)
               {
                   classNotFound.printStackTrace();
                   System.exit(1);
               }
               finally
               {
                   try
                   {
                       st.close();
                       con.close();
                   }
                   catch(Exception exception)
                   {
                       exception.printStackTrace();
                       System.exit(1);
                   }
               }
               }  
       });
       updateButton=new JButton("Update");
       updateButton.addActionListener(
       new ActionListener()
       {
           int check=0;
           public void actionPerformed(ActionEvent e)
           {
               counter++;
               if(counter==1)
               {
               id=JOptionPane.showInputDialog(null,"Enter the ID of the student whose record you wish to update");
               try
               {
                   Class.forName("com.mysql.jdbc.Driver");
                   con=DriverManager.getConnection("jdbc:mysql://localhost/student","root","root");
                   st=con.createStatement();
                   rs=st.executeQuery("SELECT * from studentdatabase WHERE ID LIKE"+"'"+id+"'");
                    if(rs.first()==false)
                    {
                    check=JOptionPane.showConfirmDialog(null,"No such student exists on record,would you like to create a new record?","ERROR",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
                    if(check==JOptionPane.YES_OPTION)
                    {
                    JOptionPane.showMessageDialog(null,"Please fill out the rest of the details and click on the new button to add a record","Information",JOptionPane.INFORMATION_MESSAGE);                   
                    sID.setText(id);
                    fName.setText("");
                    lName.setText("");
                    add.setText("");
                    course.setSelectedIndex(0);
                    genderGroup.clearSelection();
                    countryName.setText("");    
                    }
                    else
                    {
                        counter=0;
                    }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Modify information and press update","Information",JOptionPane.INFORMATION_MESSAGE);
                        rs.first();
                        sID.setText(rs.getString(1));
                        fName.setText(rs.getString(2));
                        lName.setText(rs.getString(3));
                        add.setText(rs.getString(4));
                        course.setSelectedItem(rs.getString(5));
                        countryName.setText(rs.getString(6));
                        g=rs.getString(7);
                        if(g.equalsIgnoreCase("Male"))
                        {
                            male.setSelected(true);
                            g="Male";
                        }
                        else if(g.equalsIgnoreCase("Female"))
                        {
                            female.setSelected(true);
                            g="Female";
                        }
                        else
                        {
                            genderGroup.clearSelection();
                            g="";
                        }
                    }
                }
               catch(SQLException sqlException)
               {
                           sqlException.printStackTrace();
                           System.exit(1);
               }
               catch(ClassNotFoundException classNotFound)
               {
                   classNotFound.printStackTrace();
                   System.exit(1);
               }
               finally
               {
                   try
                   {
                       st.close();
                       con.close();
                   }
                   catch(Exception exception)
                   {
                       exception.printStackTrace();
                       System.exit(1);
                   }
               }
               }
               else if(counter==2)
               {
                   try 
                   {
                       Class.forName("com.mysql.jdbc.Driver");
                       con=DriverManager.getConnection("jdbc:mysql://localhost/student","root","root");
                       st=con.createStatement();
                       st.executeUpdate("UPDATE studentdatabase SET "+"ID='"+sID.getText()+"',firstName='"+fName.getText()+"',lastName='"+lName.getText()+"',address='"+add.getText()+"',course='"+course.getSelectedItem()+"',country='"+countryName.getText()+"',gender='"+g+"'"+" WHERE ID = "+"'"+id+"'");
                       JOptionPane.showMessageDialog(null,"Record succesfully updated","Information",JOptionPane.INFORMATION_MESSAGE);
                        sID.setText("");
                        fName.setText("");
                        lName.setText("");
                        add.setText("");
                        course.setSelectedIndex(0);
                        genderGroup.clearSelection();
                        countryName.setText("");
                       counter=0;
                   }
                   catch (SQLException ex) 
                   {
                       Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   catch (ClassNotFoundException ex) 
                   {
                       Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   finally
                   {
                   try
                   {
                       st.close();
                       con.close();
                   }
                   catch(Exception exception)
                   {
                       exception.printStackTrace();
                       System.exit(1);
                   }
                   }
                 }
               }
       });
       refreshButton=new JButton("Refresh");
       refreshButton.addActionListener(
       new ActionListener()
       {
           public void actionPerformed(ActionEvent e)
           {
               sID.setText("");
               fName.setText("");
               lName.setText("");
               add.setText("");
               course.setSelectedIndex(0);
               genderGroup.clearSelection();
               countryName.setText("");
           }   
       });
       displayTable=new JButton("Display Table");
       displayTable.addActionListener(
       new ActionListener()
       {
           int rows;
           @SuppressWarnings("empty-statement")
           public void actionPerformed(ActionEvent e)
           {
               try
               {
               int x=1;
               int y=1;
               int j=0;
               int col;
               Class.forName("com.mysql.jdbc.Driver");
               con=DriverManager.getConnection("jdbc:mysql://localhost/student","root","root");
               st=con.createStatement();
               rs=st.executeQuery("SELECT * FROM studentdatabase");
               ResultSetMetaData metaData=rs.getMetaData();
               col=metaData.getColumnCount();
               String tableData[][];
               tableData=new String[10][col];
               String columnNames[]={"Student Id","First Name","Last Name","Address","Course","Country","Gender"};
               for(int i=0;i<=6;i++)
               {
               tableData[0][i]=columnNames[i];
               }
               while(rs.next())
               {
                   y=0;
                       for(j=1;j<=col;j++)
                       {
                           tableData[x][y]=rs.getString(j);   
                           y++;
                       }
                       x++;
               }
               JFrame frame=new JFrame();
               frame.setSize(800,600);
               frame.setVisible(true);
               frame.requestFocus();
               frame.setLayout(new BorderLayout());
               table=new JTable(tableData,columnNames);
               frame.add(table,BorderLayout.CENTER);
               JPanel backPanel=new JPanel();
               backPanel.setVisible(true);
               backPanel.setSize(500,500);
               frame.add(backPanel,BorderLayout.SOUTH);
               JButton back=new JButton("Back");
               back.addActionListener(
                       new ActionListener()
                       {
                           public void actionPerformed(ActionEvent e)
                           {
                               GUI.this.requestFocus();
                               frame.dispose();
                           }
                       });
               backPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
               backPanel.add(back);
               }
               catch(SQLException sqlException)
               {
                   sqlException.printStackTrace();
                   System.exit(1);
               }
               catch(ClassNotFoundException classNotFound)
               {
                   classNotFound.printStackTrace();
                   System.exit(1);
               }
               finally
               {
                   try
                   {
                       st.close();
                       con.close();
                   }
                   catch(Exception exception)
                   {
                       exception.printStackTrace();
                       System.exit(1);
                   }
               }
           }   
       });
       buttonArea.add(newButton);
       buttonArea.add(deleteButton);
       buttonArea.add(updateButton);
       buttonArea.add(refreshButton);
       buttonArea.add(displayTable);
       infoArea.add(formArea,BorderLayout.CENTER);
       formArea.setLayout(new GridBagLayout());
       formArea.setVisible(true);
       formArea.setSize(300,300);
       GridBagConstraints c=new GridBagConstraints();  
       c.insets=new Insets(10,10,10,10);
       c.gridx=0;
       c.gridy=0;
       studentId=new JLabel("Student ID");
       formArea.add(studentId,c);
       sID = new JTextField(30);
       c.gridx=2;
       formArea.add(sID,c);
       firstName=new JLabel("First Name");
       c.gridx=0;
       c.gridy=1;
       formArea.add(firstName,c);
       c.gridx=2;
       fName=new JTextField(30);
       formArea.add(fName,c);
       c.gridy=2;
       c.gridx=0;
       lastName=new JLabel("Last Name");
       formArea.add(lastName,c);
       c.gridx=2;
       lName= new JTextField(30);
       formArea.add(lName,c);
       c.gridy=3;
       c.gridx=0;
       address=new JLabel("Address");
       formArea.add(address,c);
       c.gridx=2;
       add=new JTextField(30);
       formArea.add(add,c);
       c.gridx=0;
       c.gridy=4;
       program=new JLabel("Course");
       formArea.add(program,c);
       c.gridx=2;
       course=new JComboBox(courseNames);
       formArea.add(course,c);
       c.gridx=0;
       c.gridy=5;
       country=new JLabel("Country");
       countryName=new JTextField(30);
       formArea.add(country,c);
       c.gridx=2;
       formArea.add(countryName,c);
       c.gridx=0;
       c.gridy=6;
       gender=new JLabel("Gender");
       formArea.add(gender,c);
       c.gridx=2;
       male=new JRadioButton("Male");
       female=new JRadioButton("Female");
       genderGroup=new ButtonGroup();
       genderGroup.add(male);
       genderGroup.add(female);
       formArea.add(male,c);
       c.gridx=3;
       formArea.add(female,c);   
   }
      public void windowIconified(WindowEvent e)
        {
        }
        public void windowDeiconified(WindowEvent e)
        {
        }
        public void windowClosing(WindowEvent e)
        {
                System.exit(0);
        }
        public void windowClosed(WindowEvent e)
        {
        }
        public void windowOpened(WindowEvent e)
        {
        }
        public void windowActivated(WindowEvent e)
        {
        }
        public void windowDeactivated(WindowEvent e)
        {
        }
}
/**
 *
 * @author Lenovo
 */
public class Form {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GUI obj=new GUI();
        obj.setSize(800,600);
        obj.setVisible(true);
    }
    
}
