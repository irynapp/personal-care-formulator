import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * Creates formula frame.
 * @author ipaluyanava
 *
 */
public class FormulaFrame extends JFrame {                   
    private JSplitPane bottomSplitPane;
    private JPanel btnsPanel;
    private JButton calculateTotalPercentBtn;
    private JButton calculateRecipeBtn;
    private JButton closeBtn;
    private JPanel dynamicPanel;
    private JSplitPane mainSplitPane;
    private JButton saveFormulaBtn;
    private JLabel titleLabel;
	
    /*
     * Reference to category panel;
     * Needed to category UI updates when new formula is added.
     */
    private CategoryPanel catPanel;
    private ArrayList<Phase> phases; // Formula phases list
	private Formula formula;
   // List of the phase panels
	private ArrayList<PhasePanel> phasePanels = new ArrayList<PhasePanel>();
    
	/**
	 * Sets formula, formula's category, creates category
	 * phases objects and builds formula UI.
	 * @param newFormula the formula object to set to.
	 * @param panel the category panel to set to.
	 */
    public FormulaFrame(Formula newFormula, CategoryPanel panel) {
    	formula = newFormula;
    	catPanel = panel;
    	phases = Mgr.createPhases(formula);
        initComponents();
    }

    /**
     * Builds UI.
     */
	private void initComponents() {
        mainSplitPane = new JSplitPane();
        titleLabel = new JLabel();
        bottomSplitPane = new JSplitPane();
        dynamicPanel = new JPanel();
        btnsPanel = new JPanel();
        calculateTotalPercentBtn = new JButton();
        calculateRecipeBtn = new JButton();
        saveFormulaBtn = new JButton();
        closeBtn = new JButton();


        mainSplitPane.setDividerLocation(50);
        mainSplitPane.setDividerSize(1);
        mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        titleLabel.setFont(new Font("Georgia", 1, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText(formula.getName());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, 
        		new Color(204, 204, 204), new Color(204, 204, 204), new Color(153, 153, 153), 
        		new Color(153, 153, 153)), BorderFactory.createEtchedBorder(new Color(204, 204, 204), 
        		new Color(153, 153, 153)
        )));
        titleLabel.setBackground(new Color(204, 204, 204));
        titleLabel.setOpaque(true);
 
        mainSplitPane.setTopComponent(titleLabel);

        bottomSplitPane.setDividerLocation(400);
        bottomSplitPane.setDividerSize(1);
        bottomSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        dynamicPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, 
        		new Color(204, 204, 204), new Color(204, 204, 204), new Color(153, 153, 153), 
        		new Color(153, 153, 153)), BorderFactory.createEtchedBorder(new Color(204, 204, 204),
        		new Color(153, 153, 153)
        )));
        
        JScrollPane scrollPane = new JScrollPane(
        	dynamicPanel, 
        	ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
        	ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.PAGE_AXIS));
        
        // Add phase panels to dynamic panel
        for(Phase p: phases) {
        	PhasePanel phasePnl = new PhasePanel(p);
        	phasePanels.add(phasePnl);
        	dynamicPanel.add(phasePnl);
        }

        bottomSplitPane.setTopComponent(scrollPane);

        btnsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, 
        		new Color(204, 204, 204), new Color(204, 204, 204), new Color(153, 153, 153), new Color(153, 153, 153)), 
        		BorderFactory.createEtchedBorder(new Color(204, 204, 204), new Color(153, 153, 153)
        )));
        btnsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        
        calculateTotalPercentBtn.setFont(new Font("Georgia", 1, 14));
        calculateTotalPercentBtn.setText("Calculate Total Percent");       
        calculateTotalPercentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	calculateTotalPercentBtnActionPerformed(evt);
            }
        });
        btnsPanel.add(calculateTotalPercentBtn);

        calculateRecipeBtn.setFont(new Font("Georgia", 1, 14));
        calculateRecipeBtn.setText("Calculate Recipe");
        if(formula.isBasic()) {
        	calculateRecipeBtn.setEnabled(false);
        }
        
        calculateRecipeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                calculateRecipeBtnActionPerformed(evt);
            }
        });
        btnsPanel.add(calculateRecipeBtn);

        saveFormulaBtn.setFont(new Font("Georgia", 1, 14));
        saveFormulaBtn.setText("Save Formula");
        saveFormulaBtn.setEnabled(false);
        saveFormulaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveFormulaBtnActionPerformed(evt);
            }
        });
        btnsPanel.add(saveFormulaBtn);

        closeBtn.setFont(new Font("Georgia", 1, 14));
        closeBtn.setText("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });
        btnsPanel.add(closeBtn);

        bottomSplitPane.setBottomComponent(btnsPanel);
        mainSplitPane.setBottomComponent(bottomSplitPane);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainSplitPane, GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainSplitPane, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        );

        pack();
    } 
	
	/**
	 * Closes window.
	 * @param evt
	 */
    private void closeBtnActionPerformed(ActionEvent evt) {                                         
    	//this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    	this.dispose();
    }
    
    /**
     * Sums up percent value of each ingredient 
     * and displays total value to user.
     * @param evt
     */
    private void calculateTotalPercentBtnActionPerformed(ActionEvent evt) {
    	int amount = 0;
    	for(PhasePanel panel: phasePanels) {
    		for(ArrayList<Component> comps: panel.getDynamicComponents()){
    			for(Component dataComponent: comps) {    				
    				if(dataComponent instanceof JTextField) {
    					amount += Integer.parseInt(((JTextField) dataComponent).getText());
    				}
    			}
    		}
    	}
    	
    	JOptionPane.showMessageDialog(
    		this,
    		amount + "%",
    		"Current Total Percent",
    		JOptionPane.INFORMATION_MESSAGE
    	);
	}
    
    /**
     * Calls field validation functionality; if no data issues occurred,
     * prompts user to enter number of grams and, if number is a valid integer,
     * displays the recipe form.
     * @param evt
     */
    private void calculateRecipeBtnActionPerformed(ActionEvent evt) {                                                   
    	if(!checkDataValidity()) {
    		JOptionPane.showMessageDialog(
    			this,
    			"Invalid ingredient or percent value (should be > 0) or total percent (should be 100%)",
    			"Invalid Data",
    			JOptionPane.ERROR_MESSAGE
    		);
    		return;
    	}
    	
    	int gramAmount = 0;
    	boolean keepAsking = true;
		while (keepAsking) {
			String strNumber = JOptionPane.showInputDialog(this, "Please enter number of grams");
			// User pressed OK with empty or non-empty entry.
			if(strNumber != null) {
				try {
					gramAmount = Integer.parseInt(strNumber);
				// Catches empty value or non-integer value.
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(
						this,
						"Number of grams should be > 0",
						"Invalid entry",
						JOptionPane.ERROR_MESSAGE
					);
					continue;
				}
				if(gramAmount <= 0) {
					JOptionPane.showMessageDialog(
						this,
						"Number of grams should be > 0",
						"Invalid entry",
						JOptionPane.ERROR_MESSAGE
					);
					continue;
				}
				keepAsking = false;
				Formula f = createFormulaForRecipe();
				// Need this value to be final in order to be able to pass it within run().
				final int amount = gramAmount;
				EventQueue.invokeLater(new Runnable() {
			   		 public void run() {
			   			 RecipeDisplay display = new RecipeDisplay(amount, f);
			   			 display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			   			 display.setLocationRelativeTo(null);
			   			 display.setVisible(true);
			   		 }
			   });
			// User pressed cancel.
			} else {
				int result = JOptionPane.showConfirmDialog(
					this,
					"Are you sure you want to cancel?",
					null,
					JOptionPane.YES_NO_OPTION
				);
				// Stop looping.
				if(result == JOptionPane.YES_OPTION) {
					keepAsking = false;
				} 
			}
		} 
    }                                                  
    
    /**
     * Invokes field validation functionality; if no data issues occurred,
     * asks user to enter a file name; if file name do not exists in app DB,
     * saves data to DB; otherwise, prompts user to try again until user presses cancel.
     * @param evt
     */
    private void saveFormulaBtnActionPerformed(ActionEvent evt) {
    	if(!checkDataValidity()) {
    		JOptionPane.showMessageDialog(
    			this,
    			"Invalid ingredient or percent value (should be > 0) or total percent (should be 100%)",
    			"Invalid Data",
    			JOptionPane.ERROR_MESSAGE
    		);
    		return;
    	}

    	boolean keepAsking = true;
		while (keepAsking) {
			String newName = JOptionPane.showInputDialog(
		    	this, 
		    	"Please enter formula name:",
		    	"New Formula Name",
		    	JOptionPane.PLAIN_MESSAGE
		    );
			
			// User pressed OK with empty or non-empty entry.
			if(newName != null) {
				if(newName.length() > 0) {
					try {
						saveNewFormula(newName);
					// Do not allow duplicate names.
					} catch (Exception e) {
						System.err.println(e.getClass().getName() + ": " + e.getMessage());
						JOptionPane.showMessageDialog(
				    		this,
				    		"Formula with the specified name already exists",
				    		"Invalid formula name",
				    		JOptionPane.ERROR_MESSAGE
				    	);
						continue;
					}
					updateUI();
					keepAsking = false;
				} else {
					JOptionPane.showMessageDialog(
						this,
						"Invalid entry! Please try again.",
						"Invalid entry",
						JOptionPane.ERROR_MESSAGE
					);
				} 
			// User pressed cancel.
			} else {
				int result = JOptionPane.showConfirmDialog(
					this,
					"Are you sure you want to cancel?",
					null,
					JOptionPane.YES_NO_OPTION
				);

				if(result == JOptionPane.YES_OPTION) {
					keepAsking = false;
				} 
			}
		}
		
    }
    
    /**
     * Create new formula and add it to DB.
     * @param name the new formula name.
     * @throws Exception if the formula with the specified name already exists.
     */
	@SuppressWarnings("unchecked")
	private void saveNewFormula(String name) throws Exception {
		// Creating new Formula object.
    	Formula newFormula = new Formula();
    	newFormula.setName(name);
    	newFormula.setCategoryId(formula.getCategoryId());
    	
    	// Iterate over fields and add ingredients.
    	for(PhasePanel panel: phasePanels) {
    		for(ArrayList<Component> comps: panel.getDynamicComponents()){
    			Ingredient ingredient = null;
    			int amount = 0;
    			
    			for(Component dataComponent: comps) {
    				if(dataComponent instanceof JComboBox) {
    					ingredient = (Ingredient) ((JComboBox<Ingredient>) dataComponent).getSelectedItem();
    				} 
    				
    				if(dataComponent instanceof JTextField) {
    					amount = Integer.parseInt(((JTextField) dataComponent).getText());
    				}
    			}
    			
    			if(!ingredient.equals(null)) {
    				ingredient.setAmount(amount);
    				newFormula.getIngredients().add(ingredient);
    			}
    			
    		}
    	}
    	
    	// Try to populating DB
    	Mgr.addNewFormula(newFormula);
    	/*
    	 * If previous step did not fail,
    	 * set current formula to a new one.
    	 */
    	formula = newFormula;   	
    }
    
	/**
	 * Returns save button reference; needed for 
	 * enabling and disabling this button.
	 * @return the saveFormulaBtn
	 */
    public JButton getSaveBtn() {
    	return saveFormulaBtn;
    }
    
    /**
     * Check validity of data in phase containers:
     * - text fields should not contain 0's
     * - ingredients should not be default (carrier oil, butter, etc.)
     * - total percent amount should be 100%
     * @return true, if no issues found; otherwise, false.
     */
    private boolean checkDataValidity() {
    	int amount = 0;
    	for(PhasePanel panel: phasePanels) {
    		for(ArrayList<Component> comps: panel.getDynamicComponents()){
    			for(Component c: comps) {
    				if(c instanceof JTextField) {
    					if(((JTextField) c).getText().equals("0")) {
    						return false;
    					}
    					amount += Integer.parseInt(((JTextField) c).getText());
    				}
    				
    				if(c instanceof JComboBox) {
    					@SuppressWarnings("unchecked")
						Ingredient ingredient = (Ingredient) ((JComboBox<Ingredient>) c).getSelectedItem();
    					if(ingredient.getName().equals(ingredient.getType())) {
    						return false;
    					}
    				} 
    			}
    		}
    	}
    	
    	if(amount != 100) {
    		return false;
    	}
   
    	return true;
    }
    
    /**
     * Update Formula frame and Category panel
     * when new formula is added.
     */
    public void updateUI() {
    	titleLabel.setText(formula.getName());
    	saveFormulaBtn.setEnabled(false);
    	catPanel.updateUI(formula);
    }
    
    /**
     * Create formula object for recipe display form.
     * @return new formula for recipe display form.
     */
    @SuppressWarnings("unchecked")
	private Formula createFormulaForRecipe(){
		//Creating new Formula object
    	Formula newFormula = new Formula();
    	newFormula.setName(formula.getName());
    	
    	for(PhasePanel panel: phasePanels) {
    		for(ArrayList<Component> comps: panel.getDynamicComponents()){
    			Ingredient ingredient = null;
    			int amount = 0;
    			
    			for(Component dataComponent: comps) {
    				if(dataComponent instanceof JComboBox) {
    					ingredient = (Ingredient) ((JComboBox<Ingredient>) dataComponent).getSelectedItem();
    				} 
    				
    				if(dataComponent instanceof JTextField) {
    					amount = Integer.parseInt(((JTextField) dataComponent).getText());
    				}
    			}
    			
    			if(!ingredient.equals(null)) {
    				ingredient.setAmount(amount);
    				newFormula.getIngredients().add(ingredient);
    			}
    			
    		}
    	}
    	return newFormula;
    }            
}
