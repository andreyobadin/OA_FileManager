import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import run.RunManager;
 
public class ProgramRun extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final String configPatch = "C:/Users/Ирина/Desktop/Обадин/OA_FileManager/install/Config.xml";
	
	public static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
 
    
    public static void createGUI() {
 
        JFrame frame = new JFrame("OA_FileManager");
        JLabel leble = new JLabel("                      Scaning!");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.add(leble);
        frame.addWindowListener(new WindowListener() {
 
            public void windowActivated(WindowEvent event) {
 
            }
 
            public void windowClosed(WindowEvent event) {
 
            }
 
            public void windowClosing(WindowEvent event) {
                Object[] options = { "Yes", "No!" };
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Close Program?",
                                "", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    event.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
 
            public void windowDeactivated(WindowEvent event) {
 
            }
 
            public void windowDeiconified(WindowEvent event) {
 
            }
 
            public void windowIconified(WindowEvent event) {
 
            }
 
            public void windowOpened(WindowEvent event) {
 
            }
 
        });
 
        frame.setPreferredSize(new Dimension(250, 250));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
     
    public static void main(String[] args) {
      
        UIManager. put("Button.font", FONT);
        UIManager.put("Label.font", FONT);              
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        createGUI();
        RunManager.runP(configPatch);
    }
}