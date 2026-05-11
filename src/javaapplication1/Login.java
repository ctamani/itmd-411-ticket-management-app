package javaapplication1;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//controls-label text fields, button
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Login extends JFrame {

    Dao conn;

    public Login() {

        super("IIT HELP DESK LOGIN");
        conn = new Dao();
        conn.createTables();
        setSize(420, 360);
        setLocationRelativeTo(null); // centers window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Gradient background panel
        JPanel bg = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, UIStyle.BG_TOP, 0, getHeight(), UIStyle.BG_BOT));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bg.setLayout(new GridBagLayout());
        setContentPane(bg);

        // Card panel
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIStyle.CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(UIStyle.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(340, 295));
        card.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 0, 6, 0);
        gc.fill   = GridBagConstraints.HORIZONTAL;
        gc.gridx  = 0; gc.weightx = 1;

        // Title
        JLabel lblTitle = new JLabel("IIT HELP DESK", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Open Sans", Font.BOLD, 22));
        lblTitle.setForeground(UIStyle.TEXT_PRI);
        gc.gridy = 0; gc.insets = new Insets(0, 0, 2, 0);
        card.add(lblTitle, gc);

        // SET UP CONTROLS
        JLabel lblUsername = new JLabel("Username", JLabel.LEFT);
        JLabel lblPassword = new JLabel("Password", JLabel.LEFT);
        JLabel lblStatus   = new JLabel(" ", JLabel.CENTER);

        UIStyle.styleFieldLabel(lblUsername);
        UIStyle.styleFieldLabel(lblPassword);

        JTextField     txtUname    = new JTextField(10);
        JPasswordField txtPassword = new JPasswordField();
        JButton        btn         = new JButton("Sign In");
        JButton        btnExit     = new JButton("Exit");

        UIStyle.styleTextField(txtUname);
        UIStyle.styleTextField(txtPassword);
        UIStyle.stylePrimaryButton(btn);
        UIStyle.styleGhostButton(btnExit);

        // constraints
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblStatus.setForeground(UIStyle.DANGER);

        // ADD OBJECTS TO CARD 
        gc.insets = new Insets(4, 0, 2, 0);
        gc.gridy = 2; card.add(lblUsername, gc);  // 1st row filler
        gc.insets = new Insets(2, 0, 10, 0);
        gc.gridy = 3; card.add(txtUname, gc);
        gc.insets = new Insets(4, 0, 2, 0);
        gc.gridy = 4; card.add(lblPassword, gc);  // 2nd row
        gc.insets = new Insets(2, 0, 14, 0);
        gc.gridy = 5; card.add(txtPassword, gc);
        gc.insets = new Insets(4, 0, 4, 0);
        gc.gridy = 6; card.add(btn, gc);           // 3rd row
        gc.gridy = 7; card.add(btnExit, gc);
        gc.insets = new Insets(8, 0, 0, 0);
        gc.gridy = 8; card.add(lblStatus, gc);     // 4th row

        bg.add(card);

        btn.addActionListener(new ActionListener() {
            int count = 0; // count agent

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean admin = false;
                count = count + 1;
                // verify credentials of user

                String query = "SELECT * FROM achik_users WHERE uname = ? and upass = ?;";
                try (PreparedStatement stmt = conn.getConnection().prepareStatement(query)) {
                    stmt.setString(1, txtUname.getText());
                    stmt.setString(2, new String(txtPassword.getPassword()));
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        admin = rs.getBoolean("admin"); // get table column value
                        new Tickets(txtUname.getText(), admin); //open Tickets file / GUI interface
                        setVisible(false); // HIDE THE FRAME
                        dispose(); // CLOSE OUT THE WINDOW
                    } else {
                        lblStatus.setText("Try again! " + (3 - count) + " / 3 attempt(s) left");

                        if (count >= 3) {
                            lblStatus.setText("0 / 3 attempt(s) left. Contact help desk to unlock password.");
                            btn.setEnabled(false);
                            txtUname.setEnabled(false);
                            txtPassword.setEnabled(false);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            
            }
        });
        btnExit.addActionListener(e -> System.exit(0));

        // Enter key triggers sign-in from password field
        txtPassword.addActionListener(e -> btn.doClick());

        setVisible(true); // SHOW THE FRAME
    }

    public static void main(String[] args) {
        new Login();
    }
}
