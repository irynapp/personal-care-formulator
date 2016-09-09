import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Creates Category UI.
 * @author ipaluyanava
 */
public class CategoryPanel extends JPanel {
	private Category category; // Category object
	private ArrayList<JButton> formulaBtns = new ArrayList<JButton>(); //list of formula buttons.

	/**
	 * Sets category object, pulls ingredients for each category formula
	 * and initializes category panel components. 
	 * @param newCategory the category object to set to.
	 */
    public CategoryPanel(Category newCategory) {
    	category = newCategory;
    	Mgr.getIngredientsForCategoryFormulas(category.getFormulas());
        initComponents();
    }
    
    /**
     * Builds the panel.
     */
    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        for(Formula formula: category.getFormulas()) {
        	addFormulaBtn(formula);
        } 
    }
    
    /**
     * Creates button for each category formula.
     * @param formula the formula to add as button.
     */
	private void addFormulaBtn(Formula formula) {
		JButton btn = new JButton();
		btn.setFont(new Font("Georgia", 1, 14));
		btn.setText(formula.getName());
		btn.setToolTipText(formula.getName());
		btn.setPreferredSize(new Dimension(160, 54));
		if(formula.isBasic()) {
			btn.setBackground(new Color(204, 204, 204));
			btn.setOpaque(true);
		}
		btn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		        showFormula(evt);
		    }
		});
		
		 add(btn);
		 formulaBtns.add(btn);
	}
    
	/**
	 * Pressing on formula button, creates a formula form.
	 * @param evt
	 */
    protected void showFormula(ActionEvent evt) {
    	CategoryPanel panel = this;
    	EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			FormulaFrame f = new FormulaFrame(
    				Mgr.getSelectedFomula(
    					category.getFormulas(),
    					((JButton) evt.getSource()).getText()
    				),
    				panel
    			);
    			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    			f.setResizable(false);
    			f.setLocationRelativeTo(null);
    			f.setVisible(true);
    		}
    	});
	}
    
    /**
     * Add new formula to category, add formula button to category panel,
     * update formula count within category for main form.
     * @param formula the formula to add.
     */
    public void updateUI(Formula formula) {
    	category.getFormulas().add(formula);
    	addFormulaBtn(formula);
    	((Formulator) SwingUtilities.getWindowAncestor(this)).getCountLbl().setText(
    			String.valueOf(category.getFormulas().size())
    	);
    	revalidate();
    	repaint();
    }
}
