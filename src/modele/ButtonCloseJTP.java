package modele;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

public class ButtonCloseJTP extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JTabbedPane jpane;
	private int index;
	

	public ButtonCloseJTP(JTabbedPane jpane) {
        super(new FlowLayout(0, 0, 0));
        if(jpane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        else {
            this.jpane = jpane;
            setOpaque(false);
         
            JLabel label = new JLabel(){
			    private static final long serialVersionUID = 1L;

			    public String getText() {
                    int i = jpane.indexOfTabComponent(ButtonCloseJTP.this);
                    if (i != -1) {
                        return jpane.getTitleAt(i);
                    }
                    return null;
                }
         };
         this.add(label);
         label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
         TabButton button = new TabButton();
         this.add(button);
         setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }
    }

	//private static final MouseListener buttonMouseListener = new 1();
	public int getIndex() {
		return this.index;
	}
	
	
	class TabButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		public TabButton() {
			
			byte size = 17;
			this.setPreferredSize(new Dimension(size, size));
			this.setToolTipText("close this tab");
			this.setUI(new BasicButtonUI());
			this.setContentAreaFilled(false);
			this.setFocusable(false);
			this.setBorder(BorderFactory.createEtchedBorder());
			this.setBorderPainted(false);
			this.addMouseListener(buttonMouseListener);
			this.setRolloverEnabled(true);
			this.addActionListener(this);
		}

		
		public void actionPerformed(ActionEvent arg0) {
			int i = jpane.indexOfTabComponent(ButtonCloseJTP.this);
            if (i != -1) {
                jpane.remove(i);                                
    			index -=-1;
            }			
		}
		public void updateUI() {
		}
		protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
	
    }

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}
