package org.example;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

/**
 * Central visual language & component factory for Aarogya Hospital Management System.
 */
public final class Theme {

    private Theme() { }

    // ------------------------------------------------------------------
    // Strict Palette Enforcement
    // ------------------------------------------------------------------
    public static final Color DEEP_NAVY       = new Color(0x2F, 0x41, 0x56); // #2F4156 - Headers, Table Headers, Primary Buttons
    public static final Color SOFT_TEAL       = new Color(0x1B, 0x6B, 0x93); // #1B6B93 - Vibrant Teal Blue Accent
    public static final Color SKY_BLUE        = new Color(0xC8, 0xD9, 0xE6); // #C8D9E6 - Selection, Card Borders, Secondary Buttons
    public static final Color WARM_BEIGE      = new Color(0xF5, 0xEF, 0xEB); // #F5EFEB - Window/Form Background
    public static final Color CLEAN_WHITE     = new Color(0xFF, 0xFF, 0xFF); // #FFFFFF - Cards, Inputs, Row alternate

    // Aliases
    public static final Color BG              = WARM_BEIGE;
    public static final Color BG_DARK         = DEEP_NAVY;
    public static final Color CARD_BG         = CLEAN_WHITE;
    public static final Color BORDER          = SKY_BLUE;
    public static final Color BORDER_STRONG   = DEEP_NAVY;

    public static final Color ACCENT          = DEEP_NAVY;
    public static final Color ACCENT_DARK     = new Color(0x1E, 0x2C, 0x3D);
    public static final Color ACCENT_LIGHT    = new Color(0xE8, 0xF0, 0xF8);

    public static final Color TEAL            = SOFT_TEAL;
    public static final Color TEAL_LIGHT      = SKY_BLUE;

    public static final Color DANGER          = new Color(0xC0, 0x39, 0x2B);
    public static final Color DANGER_LIGHT    = new Color(0xFD, 0xED, 0xEC);

    public static final Color TEXT_PRIMARY    = DEEP_NAVY;
    public static final Color TEXT_SECONDARY  = new Color(0x4A, 0x5D, 0x6E);
    public static final Color TEXT_ON_ACCENT  = CLEAN_WHITE;

    public static final Color TABLE_ROW_ALT   = new Color(0xFA, 0xF7, 0xF5);
    public static final Color TABLE_GRID      = new Color(0xE0, 0xD9, 0xD4);
    public static final Color TABLE_SELECTION = SKY_BLUE;

    // ------------------------------------------------------------------
    // Typography
    // ------------------------------------------------------------------
    private static final String FONT_FAMILY = "Segoe UI";

    public static final Font FONT_TITLE     = new Font(FONT_FAMILY, Font.BOLD, 20);
    public static final Font FONT_HEADER    = new Font(FONT_FAMILY, Font.BOLD, 17);
    public static final Font FONT_SUBTITLE  = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_LABEL     = new Font(FONT_FAMILY, Font.BOLD, 12);
    public static final Font FONT_BODY      = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font FONT_CAPTION   = new Font(FONT_FAMILY, Font.ITALIC, 12);
    public static final Font FONT_BUTTON    = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font FONT_STAT      = new Font(FONT_FAMILY, Font.BOLD, 26);

    // ------------------------------------------------------------------
    // Spacing
    // ------------------------------------------------------------------
    public static final int PAD_XS = 4;
    public static final int PAD_SM = 8;
    public static final int PAD_MD = 14;
    public static final int PAD_LG = 24;

    // ------------------------------------------------------------------
    // Input Parameter & Character Filter Restrictions
    // ------------------------------------------------------------------

    public static void addAlphabetOnlyFilter(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("[a-zA-Z\\s]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && (text.isEmpty() || text.matches("[a-zA-Z\\s]+"))) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public static void addNumberOnlyFilter(JTextField field, int maxLength) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("\\d+")) {
                    if (maxLength <= 0 || (fb.getDocument().getLength() + string.length() <= maxLength)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && (text.isEmpty() || text.matches("\\d+"))) {
                    if (maxLength <= 0 || (fb.getDocument().getLength() - length + text.length() <= maxLength)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }
        });
    }

    // ------------------------------------------------------------------
    // Look & Feel bootstrap
    // ------------------------------------------------------------------
    public static void initLookAndFeel() {
        try {
            Class<?> flatLaf = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            UIManager.setLookAndFeel((LookAndFeel) flatLaf.getDeclaredConstructor().newInstance());
        } catch (Exception flatLafUnavailable) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
        }
        UIManager.put("ToolTip.background", CARD_BG);
        UIManager.put("ToolTip.foreground", TEXT_PRIMARY);
        UIManager.put("OptionPane.background", BG);
        UIManager.put("Panel.background", BG);
    }

    // ------------------------------------------------------------------
    // Logo Renderer (Aarogya Hospital Four-Leaf Vector Badge)
    // ------------------------------------------------------------------
    public static ImageIcon createLogoIcon(int size) {
        Image image = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(SOFT_TEAL);
        g2.setStroke(new BasicStroke(size * 0.08f));
        int pad = (int) (size * 0.08f);
        g2.drawOval(pad, pad, size - 2 * pad, size - 2 * pad);

        int cx = size / 2;
        int cy = size / 2;
        int r = (int) (size * 0.28f);

        g2.setColor(CLEAN_WHITE);
        g2.fillOval(cx - r/2, cy - r, r, r);
        g2.fillOval(cx - r/2, cy, r, r);
        g2.fillOval(cx - r, cy - r/2, r, r);
        g2.fillOval(cx, cy - r/2, r, r);

        g2.setColor(DEEP_NAVY);
        int armW = (int) (size * 0.12f);
        int armL = (int) (size * 0.40f);
        g2.fillRect(cx - armW/2, cy - armL/2, armW, armL);
        g2.fillRect(cx - armL/2, cy - armW/2, armL, armW);

        g2.setColor(CLEAN_WHITE);
        int innerW = (int) (size * 0.06f);
        int innerL = (int) (size * 0.28f);
        g2.fillRect(cx - innerW/2, cy - innerL/2, innerW, innerL);
        g2.fillRect(cx - innerL/2, cy - innerW/2, innerL, innerW);

        g2.dispose();
        return new ImageIcon(image);
    }

    public static JPanel createHospitalImagePanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                GradientPaint bgGradient = new GradientPaint(
                        0, 0, new Color(0x1B, 0x29, 0x36),
                        w, h, new Color(0x2F, 0x41, 0x56)
                );
                g2.setPaint(bgGradient);
                g2.fillRect(0, 0, w, h);

                g2.setColor(new Color(255, 255, 255, 12));
                g2.fillRect(30, 40, w - 60, h - 80);
                g2.fillRect(60, 20, w - 120, h - 40);

                g2.setColor(new Color(200, 217, 230, 25));
                for (int y = 80; y < h - 100; y += 40) {
                    for (int x = 70; x < w - 70; x += 35) {
                        g2.fillRect(x, y, 22, 28);
                    }
                }

                int logoSize = Math.min(w, h) / 3;
                int lx = (w - logoSize) / 2;
                int ly = (h - logoSize) / 2 - 30;

                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillOval(lx - 10, ly - 10, logoSize + 20, logoSize + 20);

                ImageIcon logo = createLogoIcon(logoSize);
                g2.drawImage(logo.getImage(), lx, ly, null);

                g2.setColor(CLEAN_WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                FontMetrics fm1 = g2.getFontMetrics();
                String t1 = "AAROGYA HOSPITAL";
                g2.drawString(t1, (w - fm1.stringWidth(t1)) / 2, ly + logoSize + 40);

                g2.setColor(SKY_BLUE);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                FontMetrics fm2 = g2.getFontMetrics();
                String t2 = "Excellence in Healthcare & Patient Care";
                g2.drawString(t2, (w - fm2.stringWidth(t2)) / 2, ly + logoSize + 65);

                g2.dispose();
            }
        };
    }

    // ------------------------------------------------------------------
    // Buttons
    // ------------------------------------------------------------------
    public static class CustomFlatButton extends JButton {
        private final Color bgNormal;
        private final Color fgNormal;
        private final Color borderColor;

        public CustomFlatButton(String text, Color bgNormal, Color fgNormal, Color borderColor) {
            super(text);
            this.bgNormal = bgNormal;
            this.fgNormal = fgNormal;
            this.borderColor = borderColor;
            setFont(FONT_BUTTON);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2.setColor(isEnabled() ? bgNormal : new Color(0xD5, 0xDF, 0xE6));
            g2.fillRoundRect(0, 0, w - 1, h - 1, 8, 8);

            if (borderColor != null) {
                g2.setColor(isEnabled() ? borderColor : new Color(0xAE, 0xB9, 0xC4));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, w - 1, h - 1, 8, 8);
            }

            g2.setColor(isEnabled() ? fgNormal : new Color(0x7F, 0x8C, 0x8D));
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int textX = (w - fm.stringWidth(getText())) / 2;
            int textY = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(getText(), textX, textY);

            g2.dispose();
        }
    }

    public static JButton primaryButton(String text) {
        return new CustomFlatButton(text, DEEP_NAVY, CLEAN_WHITE, DEEP_NAVY);
    }

    public static JButton secondaryButton(String text) {
        return new CustomFlatButton(text, SKY_BLUE, DEEP_NAVY, DEEP_NAVY);
    }

    public static JButton dangerButton(String text) {
        return new CustomFlatButton(text, DANGER, CLEAN_WHITE, DANGER);
    }

    public static JButton backButton() {
        return new CustomFlatButton("\u2190 Back to Dashboard", SKY_BLUE, DEEP_NAVY, DEEP_NAVY);
    }

    public static void installHover(JButton button, Color resting, Color hover) {
    }

    // ------------------------------------------------------------------
    // Borders
    // ------------------------------------------------------------------

    public static Border rounded(Color lineColor, int radius) {
        return new RoundedLineBorder(lineColor, radius, 1);
    }

    public static Border elevatedCard() {
        return new ElevatedBorder(12, 6);
    }

    private static class RoundedLineBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        private final int thickness;

        RoundedLineBorder(Color color, int radius, int thickness) {
            this.color = color;
            this.radius = radius;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness + 2, thickness + 6, thickness + 2, thickness + 6);
        }
    }

    private static class ElevatedBorder extends AbstractBorder {
        private final int radius;
        private final int shadowSize;

        ElevatedBorder(int radius, int shadowSize) {
            this.radius = radius;
            this.shadowSize = shadowSize;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int i = shadowSize; i > 0; i--) {
                int alpha = (int) (8 * ((double) (shadowSize - i + 1) / shadowSize));
                g2.setColor(new Color(47, 65, 86, alpha));
                g2.drawRoundRect(x + i, y + i, w - (2 * i) - 1, h - (2 * i) - 1, radius, radius);
            }

            g2.setColor(SKY_BLUE);
            g2.drawRoundRect(x, y, w - shadowSize - 1, h - shadowSize - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(PAD_MD, PAD_MD, PAD_MD + shadowSize, PAD_MD + shadowSize);
        }
    }

    // ------------------------------------------------------------------
    // Cards / Panels
    // ------------------------------------------------------------------

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setBorder(elevatedCard());
        return panel;
    }

    public static JPanel sectionPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                rounded(SKY_BLUE, 10),
                BorderFactory.createEmptyBorder(PAD_MD, PAD_MD, PAD_MD, PAD_MD)));
        return panel;
    }

    public static JPanel buildHeaderBar(String titleText, String subtitleText, JButton rightButton) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DEEP_NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JPanel leftBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        leftBox.setOpaque(false);

        JLabel lblLogo = new JLabel(createLogoIcon(38));
        leftBox.add(lblLogo);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);

        JLabel title = new JLabel(titleText);
        title.setFont(FONT_TITLE);
        title.setForeground(CLEAN_WHITE);

        titleBox.add(title);
        if (subtitleText != null && !subtitleText.isEmpty()) {
            JLabel subtitle = new JLabel(subtitleText);
            subtitle.setFont(FONT_SUBTITLE);
            subtitle.setForeground(SKY_BLUE);
            titleBox.add(Box.createRigidArea(new Dimension(0, 2)));
            titleBox.add(subtitle);
        }

        leftBox.add(titleBox);
        header.add(leftBox, BorderLayout.WEST);

        if (rightButton != null) {
            JPanel rightWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rightWrap.setOpaque(false);
            rightWrap.add(rightButton);
            header.add(rightWrap, BorderLayout.EAST);
        }

        return header;
    }

    // ------------------------------------------------------------------
    // Text Fields (Min 34px Height + Padded Compound Borders)
    // ------------------------------------------------------------------

    public static void styleField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setForeground(DEEP_NAVY);
        field.setBackground(CLEAN_WHITE);
        field.setCaretColor(DEEP_NAVY);
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 34));
        field.setMinimumSize(new Dimension(field.getMinimumSize().width, 34));
        field.setBorder(fieldBorder(DEEP_NAVY));
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(fieldBorder(SOFT_TEAL));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(fieldBorder(DEEP_NAVY));
            }
        });
    }

    private static Border fieldBorder(Color color) {
        return BorderFactory.createCompoundBorder(
                rounded(color, 6),
                BorderFactory.createEmptyBorder(6, 10, 6, 10));
    }

    public static class PlaceholderField extends JTextField {
        private final String placeholder;

        public PlaceholderField(String placeholder) {
            super();
            this.placeholder = placeholder;
            styleField(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TEXT_SECONDARY);
                g2.setFont(getFont());
                Insets insets = getInsets();
                FontMetrics fm = g2.getFontMetrics();
                int y = insets.top + (getHeight() - insets.top - insets.bottom - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, insets.left, y);
                g2.dispose();
            }
        }
    }

    public static PlaceholderField placeholderField(String placeholder) {
        return new PlaceholderField(placeholder);
    }

    // Radio Button Styling Helper
    public static void styleRadioButton(JRadioButton rdo) {
        rdo.setFont(FONT_BODY);
        rdo.setForeground(DEEP_NAVY);
        rdo.setOpaque(false);
        rdo.setFocusPainted(false);
        rdo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // ------------------------------------------------------------------
    // Combo Boxes
    // ------------------------------------------------------------------

    public static <T> void styleCombo(JComboBox<T> combo) {
        combo.setFont(FONT_BODY);
        combo.setBackground(CLEAN_WHITE);
        combo.setForeground(DEEP_NAVY);
        combo.setPreferredSize(new Dimension(combo.getPreferredSize().width, 34));
        combo.setMinimumSize(new Dimension(combo.getMinimumSize().width, 34));
        combo.setBorder(fieldBorder(DEEP_NAVY));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                            boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                label.setFont(FONT_BODY);
                if (isSelected) {
                    label.setBackground(SKY_BLUE);
                    label.setForeground(DEEP_NAVY);
                } else {
                    label.setBackground(CLEAN_WHITE);
                    label.setForeground(DEEP_NAVY);
                }
                return label;
            }
        });
    }

    // ------------------------------------------------------------------
    // Tables
    // ------------------------------------------------------------------

    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(TABLE_GRID);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(SKY_BLUE);
        table.setSelectionForeground(DEEP_NAVY);
        table.setFillsViewportHeight(true);
        table.setBackground(CLEAN_WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_LABEL);
        header.setBackground(DEEP_NAVY);
        header.setForeground(CLEAN_WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 36));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                                                            boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                label.setBackground(DEEP_NAVY);
                label.setForeground(CLEAN_WHITE);
                label.setFont(FONT_LABEL);
                label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setOpaque(true);
                return label;
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                                                            boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                label.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                if (!isSelected) {
                    label.setBackground(row % 2 == 0 ? CLEAN_WHITE : TABLE_ROW_ALT);
                    label.setForeground(DEEP_NAVY);
                } else {
                    label.setBackground(SKY_BLUE);
                    label.setForeground(DEEP_NAVY);
                }
                return label;
            }
        });
    }

    // ------------------------------------------------------------------
    // Empty / Status States
    // ------------------------------------------------------------------

    public static JLabel emptyStateLabel(String message) {
        JLabel label = new JLabel(message, SwingConstants.LEFT);
        label.setFont(FONT_CAPTION);
        label.setForeground(TEXT_SECONDARY);
        label.setBorder(BorderFactory.createEmptyBorder(PAD_SM, PAD_SM, PAD_SM, PAD_SM));
        return label;
    }

    public static JLabel statusLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_CAPTION);
        label.setForeground(TEXT_SECONDARY);
        return label;
    }
}
