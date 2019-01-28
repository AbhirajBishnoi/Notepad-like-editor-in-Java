//A simple notepad like text editor program in Java incorporating elements of Swing,AWT and file handling
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicstest;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.FILES_ONLY;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GraphicsTest {

    public static void main(String[] args) {
        GUI obj = new GUI();
        obj.setVisible(true);
        obj.setSize(500, 600);
        //obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

class GUI extends JFrame implements WindowListener
{
    public static int Window_count=1;
    private JTextArea txt;
    private JPanel MenuPanel;
    private JMenuBar Menu;
    private JMenu File;
    private JMenuItem New;
    private JMenu Edit;
    private JMenu Format;
    private JMenuItem Open;
    private JMenuItem Save;
    private JMenuItem SaveAs;
    private JMenuItem Exit;
    private JMenuItem Cut;
    private JMenuItem Copy;
    private JMenuItem Paste;
    private JMenuItem SelectAll;
    private JMenuItem Color;
    private JMenuItem BackgroundColor;
    private JMenuItem TextColor;
    private JMenu FontMenu;
    private JMenuItem FontSize;
    private JMenu FontStyle;
    private JMenuItem FontName;
    private Color Bg_color;
    private Color Txt_color;
    private String SelectedText;
    private JMenuItem Find;
    private JMenuItem FindandReplace;
    private JMenu Help;
    private JMenuItem About;
    private JPanel txtPanel;
    private Font CurrentFont;
    private JMenuItem Italics;
    private JMenuItem Bold;
    private JMenuItem Plain;
    private JMenuItem BandI;
    private String FileName;
    int DefaultFontSize;
    private JScrollPane ScrollPane;
    private boolean Savecheck;
    GUI() {
        super("Simple Text Editor");
        SelectedText = " ";
        Savecheck=false;
        DefaultFontSize = 14;
        FileName="C:\\Users\\Lenovo\\Desktop\\Default_"+Window_count+".txt";
        CurrentFont = new Font("Times New Roman", Font.PLAIN, DefaultFontSize);
        txtPanel = new JPanel();
        Bg_color = new Color(192, 192, 192);
        Txt_color = new Color(255, 255, 255);
        setBackground(Bg_color);
        MenuPanel = new JPanel();
        MenuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Menu = new JMenuBar();
        File = new JMenu("File");
        Menu.add(File);
        New = new JMenuItem("New");
        File.add(New);
        New.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ++Window_count;
                        GUI obj2 = new GUI();
                        obj2.setVisible(true);
                        obj2.setSize(700,700);
                        obj2.requestFocus();
                    }
                }
        );
        Open = new JMenuItem("Open");
        File.add(Open);
        Open.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser chooser = new JFileChooser();
                        Scanner input = null;
                        chooser.setFileSelectionMode(FILES_ONLY);
                        FileFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                        chooser.setFileFilter(filter);
                        chooser.setAcceptAllFileFilterUsed(false);
                        int result = chooser.showOpenDialog(GUI.this);
                        if (result != JFileChooser.CANCEL_OPTION) {
                            File fileName = chooser.getSelectedFile();
                            if ((fileName == null) || (fileName.getName().equals(""))) {
                                JOptionPane.showMessageDialog(GUI.this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            try {
                                input = new Scanner(fileName);
                                String s = new String();
                                while (input.hasNextLine() || input.hasNextByte()) {
                                    s = s + input.nextLine();
                                    s = s + '\n';
                                }
                                txt.setText(s);
                            } catch (SecurityException securityException) {
                                JOptionPane.showMessageDialog(GUI.this, "Access denied");
                            } catch (FileNotFoundException event) {
                                JOptionPane.showMessageDialog(GUI.this, "File does not exist");
                                return;
                            } finally {
                                if (input != null) {
                                    input.close();
                                }
                            }
                        }
                    }
                }
        );
        Save= new JMenuItem("Save");
        Save.addActionListener(
        new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        Savecheck=true;
        PrintStream f=null;
            try {
            f = new PrintStream(FileName);
            f.print(txt.getText());
            } catch (IOException ioexception)
            {
            JOptionPane.showMessageDialog(GUI.this, "An error occurred while saving the file", "Error", JOptionPane.ERROR_MESSAGE);
            Savecheck=false;
            }
            finally 
            {
            if (f != null) 
            {
            f.close();
            }
            }
        }
        });  
        File.add(Save);
        SaveAs= new JMenuItem("Save As");
        SaveAs.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser chooser = new JFileChooser();
                        Scanner input = null;
                        PrintStream f=null;
                        Savecheck=true;
                        chooser.setFileSelectionMode(FILES_ONLY);
                        FileFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                        chooser.setFileFilter(filter);
                        chooser.setAcceptAllFileFilterUsed(false);
                        int result = chooser.showSaveDialog(GUI.this);
                        if (result != JFileChooser.CANCEL_OPTION) {
                            File fileName = chooser.getSelectedFile();
                            if ((fileName == null) || (fileName.getName().equals(""))) {
                                JOptionPane.showMessageDialog(GUI.this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            try {
                                String s=fileName.getAbsolutePath();
                                f = new PrintStream(s);
                                f.print(txt.getText());
                                FileName=fileName.getName();
                            } catch (IOException ioexception) {
                                JOptionPane.showMessageDialog(GUI.this, "An error occurred while opening the file", "Error", JOptionPane.ERROR_MESSAGE);
                                Savecheck=false;
                            } finally {
                                if (f != null) {
                                    f.close();
                                }
                            }
                        }
                    }
                }
        );
        File.add(SaveAs);
        Exit = new JMenuItem("Exit");
        File.add(Exit);
        ExitHandler HE = new ExitHandler();
        Exit.addActionListener(HE);
        Edit = new JMenu("Edit");
        Cut = new JMenuItem("Cut");
        CutHandler HC = new CutHandler();
        Cut.addActionListener(HC);
        Edit.add(Cut);
        Copy = new JMenuItem("Copy");
        CopyHandler HCO = new CopyHandler();
        Copy.addActionListener(HCO);
        Edit.add(Copy);
        Paste = new JMenuItem("Paste");
        PasteHandler HP = new PasteHandler();
        Paste.addActionListener(HP);
        Edit.add(Paste);
        SelectAll = new JMenuItem("Select All");
        SelectAllHandler S = new SelectAllHandler();
        SelectAll.addActionListener(S);
        Edit.add(SelectAll);
        Edit.add(SelectAll);
        FindandReplace = new JMenuItem("Find and Replace");
        FindandReplaceHandler FR = new FindandReplaceHandler();
        FindandReplace.addActionListener(FR);
        Edit.add(SelectAll);
        Find = new JMenuItem("Find");
        Edit.add(FindandReplace);
        Menu.add(Edit);
        Format = new JMenu("Format");
        Color = new JMenu("Color");
        Format.add(Color);
        Bold = new JMenuItem("Bold");
        Italics = new JMenuItem("Italicized");
        Plain = new JMenuItem("Plain");
        BandI = new JMenuItem("Bold and Italicized");
        FontMenu = new JMenu("Font");
        FontName = new JMenuItem("Font");
        FontName.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int check = 0;
                        int check1 = 0;
                        String name = JOptionPane.showInputDialog(GUI.this, "", "Please enter the name of the font", JOptionPane.PLAIN_MESSAGE);
                        Font f;
                        if (CurrentFont.isBold()) {
                            check = 1;
                        }
                        if (CurrentFont.isItalic()) {
                            check1 = 1;
                        }
                        if (check == 0 && check1 == 0) {
                            f = new Font(name, Font.PLAIN, DefaultFontSize);
                        } else if (check == 1 && check1 == 0) {
                            f = new Font(name, Font.BOLD, DefaultFontSize);
                        } else if (check == 0 && check1 == 1) {
                            f = new Font(name, Font.ITALIC, DefaultFontSize);
                        } else {
                            f = new Font(name, Font.ITALIC + Font.BOLD, DefaultFontSize);
                        }
                        try {
                            txt.setFont(f);
                        } catch (Exception event) {
                            JOptionPane.showMessageDialog(GUI.this, "Invalid Font", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        FontStyle = new JMenu("Style");
        FontStyle.add(Bold);
        Bold.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = CurrentFont.getFontName();
                        Font f = new Font(name, Font.BOLD, DefaultFontSize);
                        txt.setFont(f);
                    }
                });
        FontStyle.add(Italics);
        Italics.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = CurrentFont.getFontName();
                        Font f = new Font(name, Font.ITALIC, DefaultFontSize);
                        txt.setFont(f);
                    }
                });
        FontStyle.add(BandI);
        BandI.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = CurrentFont.getFontName();
                        Font f = new Font(name, Font.BOLD + Font.ITALIC, DefaultFontSize);
                        txt.setFont(f);
                    }
                });
        FontStyle.add(Plain);
        Plain.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = CurrentFont.getFontName();
                        Font f = new Font(name, Font.PLAIN, DefaultFontSize);
                        txt.setFont(f);
                    }
                });
        FontSize = new JMenuItem("Font size");
        FontSize.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String s = JOptionPane.showInputDialog(GUI.this, "", "Please enter the size of the font", JOptionPane.PLAIN_MESSAGE);
                        int size = Integer.parseInt(s);
                        if (size > 0 && size < 100) {
                            int check = 0;
                            int check1 = 0;
                            Font f;
                            String name = CurrentFont.getFontName();
                            if (CurrentFont.isBold()) {
                                check = 1;
                            }
                            if (CurrentFont.isItalic()) {
                                check1 = 1;
                            }
                            if (check == 0 && check1 == 0) {
                                f = new Font(name, Font.PLAIN, size);
                            } else if (check == 1 && check1 == 0) {
                                f = new Font(name, Font.BOLD, size);
                            } else if (check == 0 && check1 == 1) {
                                f = new Font(name, Font.ITALIC, size);
                            } else {
                                f = new Font(name, Font.ITALIC + Font.BOLD, size);
                            }
                            txt.setFont(f);
                        } else {
                            JOptionPane.showMessageDialog(GUI.this, "Invalid size,Please enter a size between 1 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        FontMenu.add(FontName);
        FontMenu.add(FontStyle);
        FontMenu.add(FontSize);
        Format.add(FontMenu);
        BackgroundColor = new JMenuItem("Background color");
        TextColor = new JMenuItem("Text color");
        Color.add(BackgroundColor);
        Color.add(TextColor);
        ColorHandler C = new ColorHandler();
        BackgroundColor.addActionListener(C);
        TextColor.addActionListener(C);
        Menu.add(Format);
        Help = new JMenu("Help");
        About = new JMenuItem("About");
        Help.add(About);
        Menu.add(Help);
        About.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "This Application has been developed to study the basics of AWT,Swing and Event handling in the Java language.\nIt is a simple text editor which supports basic editing(cut,copy,paste,select,opening,saving).\nAdditional features include Find and Replace and basic formatting options(font size,font type,background color,text color).\n"
                                + "It supports the following shortcuts to aid the user:\n1.Ctrl+C=COPY SELECTED TEXT\n2.Ctrl+V=PASTE SELECTED TEXT\n3.Ctrl+X=CUT SELECTED TEXT\n4.Ctrl+A=SELECT ALL\n5.Ctrl+S=SAVE CURRENT FILE\nNOTE: If no explicit name has been previously provided,the the file is saved by the name Default_CurrentWindowCount (Path: C:\\Lenovo:\\Users:\\Desktop)\n\t\t\t\t\t\nAuthor:Abhiraj\n\t\t\t\t\tLines of Code(LOC):587\n\t\t\t\t\tDevelopment time:15 Hours");
                    }
                }
        );
        MenuPanel.add(Menu);
        setLayout(new BorderLayout());
        txt = new JTextArea(400,400);
        txt.setVisible(true);
        KeyHandler K = new KeyHandler();
        txt.addKeyListener(K);
        txtPanel.setVisible(true);
        txtPanel.setSize(500, 500);
        txtPanel.setLayout(new BorderLayout());
        ScrollPane = new JScrollPane(txt);
        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        txtPanel.add(ScrollPane, BorderLayout.CENTER);
        txt.setFont(CurrentFont);
        add(MenuPanel, BorderLayout.NORTH);
        add(txtPanel, BorderLayout.CENTER);
        Color color = GUI.this.txt.getBackground();
        MenuPanel.setBackground(color);
        addWindowListener(this);
    }       
        public void windowIconified(WindowEvent e)
        {
        }
        public void windowDeiconified(WindowEvent e)
        {
        }
        public void windowClosing(WindowEvent e)
        {
            Window_count=Window_count-1;
            String s="Save file "+FileName+"?";
            int option=JOptionPane.showConfirmDialog(this,s,"Save",YES_NO_OPTION);
            if(option==JOptionPane.YES_OPTION)
            {
            PrintStream f=null;
            try {
            f = new PrintStream(FileName);
            f.print(txt.getText());
            } catch (IOException ioexception)
            {
            JOptionPane.showMessageDialog(GUI.this, "An error occurred while saving the file", "Error", JOptionPane.ERROR_MESSAGE);
            }
            finally 
            {
            if (f != null) 
            {
            f.close();
            }
            }
            }
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            if(Window_count==0)
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
    class ExitHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class FindandReplaceHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String find = JOptionPane.showInputDialog(null, "Please enter you wish to find");
            String replace = JOptionPane.showInputDialog(null, "Please enter text you wish to replace found text with");
            String str = txt.getText().replaceAll(find, replace);
            txt.setText(str);
        }
    }

    class KeyHandler extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() == true && (e.getKeyChar() == 'C' || e.getKeyChar() == 'c')) {
                SelectedText = txt.getSelectedText();
            } else if (e.isControlDown() == true && (e.getKeyChar() == 'V' || e.getKeyChar() == 'v')) {
                String s = txt.getText() + SelectedText;
                txt.setText(s);
            } else if (e.isControlDown() == true && (e.getKeyChar() == 'X' || e.getKeyChar() == 'x')) {
                SelectedText = txt.getSelectedText();
                String text = txt.getText();
                int start = txt.getSelectionStart();
                int end = txt.getSelectionEnd();
                int length = text.length();
                int l = end - start;
                int a = 0;
                String s = new String();
                while (a != length) {
                    if (a == start) {
                        a = a + l;
                        continue;
                    } else {
                        s = s + text.charAt(a);
                        a++;
                    }
                }
                txt.setText(s);
            } else if (e.isControlDown() == true && (e.getKeyChar() == 'A' || e.getKeyChar() == 'a')) {
                txt.setSelectionStart(0);
                txt.setSelectionEnd(txt.getText().length());
            }
            else if (e.isControlDown() == true && (e.getKeyChar() == 'S' || e.getKeyChar() == 's'))
            {
             String str=new String();
            str=String.valueOf(Savecheck);
            JOptionPane.showMessageDialog(GUI.this,"hi");
            PrintStream f=null;
            Savecheck=true;
            try {
            f = new PrintStream(FileName);
            f.print(txt.getText());
            } catch (IOException ioexception)
            {
            JOptionPane.showMessageDialog(GUI.this, "An error occurred while saving the file", "Error", JOptionPane.ERROR_MESSAGE);
            Savecheck=false;
            }
            finally 
            {
            if (f != null) 
            {
            f.close();
            }
            }
            }
        }
    }

    class PasteHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String s = txt.getText() + SelectedText;
            txt.setText(s);
        }
    }

    class ColorHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == BackgroundColor) {
                Bg_color = JColorChooser.showDialog(GUI.this, "Choose a color", Bg_color);
                if (Bg_color == null) {
                    Bg_color = new Color(192, 192, 192);
                }
                GUI.this.txt.setBackground(Bg_color);
            } else if (e.getSource() == TextColor) {
                Txt_color = JColorChooser.showDialog(GUI.this, "Choose a color", Txt_color);
                if (Txt_color == null) {
                    Txt_color = new Color(255, 255, 255);
                }
                txt.setForeground(Txt_color);
            }
        }
    }

    class CopyHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            SelectedText = txt.getSelectedText();
        }
    }

    class SelectAllHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            txt.setSelectionStart(0);
            txt.setSelectionEnd(txt.getText().length());
        }
    }

    class CutHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            SelectedText = txt.getSelectedText();
            String text = txt.getText();
            int start = txt.getSelectionStart();
            int end = txt.getSelectionEnd();
            int length = text.length();
            int l = end - start;
            int a = 0;
            String s = new String();
            while (a != length) {
                if (a == start) {
                    a = a + l;
                } else {
                    s = s + text.charAt(a);
                    a++;
                }
            }
            txt.setText(s);
        }
    }
}
