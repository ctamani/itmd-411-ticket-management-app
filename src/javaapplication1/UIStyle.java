package javaapplication1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class UIStyle {

    // Color palette
    public static final Color BG_TOP = new Color(13, 17, 28);
    public static final Color BG_BOT = new Color(22, 30, 50);
    public static final Color CARD_BG = new Color(28, 36, 58);
    public static final Color CARD_BORDER = new Color(52, 68, 110);
    public static final Color ACCENT = new Color(82, 140, 255);
    public static final Color ACCENT_HOV = new Color(110, 165, 255);
    public static final Color TEXT_PRI = new Color(220, 228, 255);
    public static final Color TEXT_SEC = new Color(120, 140, 190);
    public static final Color FIELD_BG = new Color(18, 24, 42);
    public static final Color DANGER = new Color(255, 90, 90);
    public static final Color SUCCESS = new Color(60, 200, 120);

    public static void styleFieldLabel(JLabel lbl) {
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(TEXT_SEC);
        lbl.setHorizontalAlignment(JLabel.LEFT);
    }

    public static void styleTextField(JTextField tf) {
        tf.setBackground(FIELD_BG);
        tf.setForeground(TEXT_PRI);
        tf.setCaretColor(ACCENT);
        tf.setFont(new Font("Monospaced", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_BORDER, 1, true),
            BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
        tf.setPreferredSize(new Dimension(tf.getPreferredSize().width, 34));

        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT, 1, true),
                    BorderFactory.createEmptyBorder(7, 10, 7, 10)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(CARD_BORDER, 1, true),
                    BorderFactory.createEmptyBorder(7, 10, 7, 10)
                ));
            }
        });
    }

    public static void stylePrimaryButton(JButton b) {
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(b.getPreferredSize().width, 36));

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(ACCENT_HOV);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(ACCENT);
            }
        });
    }

    public static void styleGhostButton(JButton b) {
        b.setBackground(new Color(0, 0, 0, 0));
        b.setForeground(TEXT_SEC);
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(b.getPreferredSize().width, 30));

        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setForeground(TEXT_PRI);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setForeground(TEXT_SEC);
            }
        });
    }

    public static void styleMenu(JMenu m) {
        m.setForeground(TEXT_SEC);
        m.setFont(new Font("SansSerif", Font.BOLD, 12));
        m.getPopupMenu().setBackground(CARD_BG);
        m.getPopupMenu().setBorder(BorderFactory.createLineBorder(CARD_BORDER));

        m.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                m.setForeground(TEXT_PRI);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                m.setForeground(TEXT_SEC);
            }
        });
    }

    public static void styleMenuItem(JMenuItem item) {
        item.setBackground(CARD_BG);
        item.setForeground(TEXT_PRI);
        item.setFont(new Font("SansSerif", Font.PLAIN, 12));
        item.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(42, 54, 85));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                item.setBackground(CARD_BG);
            }
        });
    }

    public static void styleTable(JTable jt) {
        jt.setBackground(CARD_BG);
        jt.setForeground(TEXT_PRI);
        jt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        jt.setRowHeight(26);
        jt.setGridColor(CARD_BORDER);
        jt.setSelectionBackground(ACCENT);
        jt.setSelectionForeground(Color.WHITE);
        jt.setShowHorizontalLines(true);
        jt.setShowVerticalLines(false);

        JTableHeader header = jt.getTableHeader();
        header.setBackground(BG_TOP);
        header.setForeground(TEXT_SEC);
        header.setFont(new Font("SansSerif", Font.BOLD, 11));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CARD_BORDER));

        jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                if (!isSelected) {
                    setBackground(row % 2 == 0 ? CARD_BG : new Color(33, 43, 68));
                    setForeground(TEXT_PRI);
                }

                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return this;
            }
        });
    }

    public static void styleScrollPane(JScrollPane sp) {
        sp.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, CARD_BORDER));
        sp.getViewport().setBackground(CARD_BG);
    }
}