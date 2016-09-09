import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Describes Phase Panel.
 * @author ipaluyanava
 *
 */
public class PhasePanel extends JPanel {                     
    private JButton addIngredient;
    private JPanel bottomPanel;
    private JSeparator bottomPanelSeparator;
    
    private JLabel phaseNameLabel;
    private JPanel topPanel;
    private JSeparator topPanelSeparator1;
    private JSeparator topPanelSeparator2;
    
    private JPanel dynamicPanel;
    
    private JPanel titlePanel;
    private JLabel guidelinesLbl;
    private JLabel ingredientLbl;
    private JLabel percentLbl;

	private Phase phase;
	// Holds components that are changeable.
	private ArrayList<ArrayList<Component>> dataComponents = new ArrayList<ArrayList<Component>>();
    
	/**
	 * Sets phase object, gets and sets 
	 * phase ingredients and creates UI.
	 * @param newPhase is the phase to set to.
	 */
    public PhasePanel(Phase newPhase) {
    	phase = newPhase;
    	phase.setPhaseIngredients(
    		Mgr.getPhaseIngredients(phase.getName())
    	);
        initComponents();
    }
    
    /**
     * Builds UI.
     */
    private void initComponents() {
        topPanel = new JPanel();
        topPanelSeparator1 = new JSeparator();
        phaseNameLabel = new JLabel();
        topPanelSeparator2 = new JSeparator();
        
        bottomPanel = new JPanel();
        bottomPanelSeparator = new JSeparator();
        addIngredient = new JButton();
        
        dynamicPanel = new JPanel();
        
        titlePanel = new JPanel();
        ingredientLbl = new JLabel();
        percentLbl = new JLabel();
        guidelinesLbl = new JLabel();       

        setLayout(new BorderLayout());

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(topPanelSeparator1);
        
        phaseNameLabel.setFont(new Font("Georgia", 1, 14));
        phaseNameLabel.setText(phase.getName() + " Phase");
        phaseNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(phaseNameLabel);
        
        topPanel.add(topPanelSeparator2);
        add(topPanel, BorderLayout.PAGE_START);

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        bottomPanel.add(bottomPanelSeparator);

        addIngredient.setFont(new Font("Georgia", 1, 18));
        addIngredient.setText("+");
        addIngredient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addIngredientActionPerformed(evt);
            }
        });
        bottomPanel.add(addIngredient);

        add(bottomPanel, BorderLayout.PAGE_END);
        
        
        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.PAGE_AXIS));

        // Set Titles
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 0));
        
        ingredientLbl.setFont(new Font("Georgia", 1, 14));
        ingredientLbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ingredientLbl.setText("Ingredient");
        ingredientLbl.setPreferredSize(new Dimension(300, 30));
        titlePanel.add(ingredientLbl);

        percentLbl.setFont(new Font("Georgia", 1, 14));
        percentLbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        percentLbl.setText("Percent %");
        percentLbl.setPreferredSize(new Dimension(200, 30));
        titlePanel.add(percentLbl);

        guidelinesLbl.setFont(new java.awt.Font("Georgia", 1, 14));
        guidelinesLbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        guidelinesLbl.setText("Guidelines");
        guidelinesLbl.setPreferredSize(new Dimension(200, 30));
        titlePanel.add(guidelinesLbl);

        dynamicPanel.add(titlePanel);
        
        // Add ingredients based on the phase data
        for(Ingredient i: phase.getIngredients()) {
        	createIngredient(i);
        }
        add(dynamicPanel, BorderLayout.CENTER);
    }  
    
    /**
     * Reinitialize component values for ingredient
     * once ingredient was change with combo box.
     * @param evt
     */
    @SuppressWarnings("unchecked")
	private void ingredientsComboBoxActionPerformed(ActionEvent evt) {                                                    
    	 ArrayList<Component> elements = getRowComponents((Component) evt.getSource());
    	 JLabel lbl = null;
    	 Ingredient ingr = null;
    	 JTextField textFiled = null;
    	 
    	 for(Component e: elements) {
    		 if(e instanceof JTextField) {
    			textFiled = (JTextField) e;
    		 } else if(e instanceof JLabel) {
    			 lbl = (JLabel) e;
    		 } else {
    			 ingr = ((Ingredient) ((JComboBox<Ingredient>) e).getSelectedItem());
    		 }
    	 }

    	 if(!ingr.equals(null)){
    		 if(!lbl.equals(null)) {
    			 lbl.setText(ingr.getGuidelines().split(";")[0]);
    	    	 lbl.setToolTipText(ingr.getGuidelines().split(";")[0]);
    		 }
    		 if(!textFiled.equals(null)) {
    			 textFiled.setText(String.valueOf(ingr.getAmount()));
    		 }
    	 }
    	 
    	 //phase.setModified(true);
    	 ((FormulaFrame) SwingUtilities.getWindowAncestor(this)).getSaveBtn().setEnabled(true);
    }
    
    /**
     * Add additional ingredient to the phase.
     * @param evt
     */
    private void addIngredientActionPerformed(ActionEvent evt) {                                              
    	createIngredient(null);
    	revalidate();
    	repaint();
    	((FormulaFrame) SwingUtilities.getWindowAncestor(this)).getSaveBtn().setEnabled(true);
    }                                             

    /**
     * Enables save button when percent value changed.
     */
    private void percentActionPerformed() {                                        
    	((FormulaFrame) SwingUtilities.getWindowAncestor(this)).getSaveBtn().setEnabled(true);
    }        
    
    /**
     * Searches for a component.
     * @param comp the component to search for.
     * @return found component or null.
     */
    private ArrayList<Component> getRowComponents(Component comp) {
    	for(ArrayList<Component> c: dataComponents) {
    		if(c.contains(comp)){
    			return c;
    		}
    	}
    	return null;
    }
    
    /**
     * Returns list of dynamic components.
     * @return the list of components.
     */
    public ArrayList<ArrayList<Component>> getDynamicComponents() {
    	return dataComponents;
    }
    
    /**
     * Create panel with containers for ingredient 
     * and populate them with data.
     * @param ingredient the ingredient to base UI additions on.
     */
    private void createIngredient(Ingredient ingredient) {
    	ArrayList<Ingredient> ingredients = null;
    	// Creates panel for an actual ingredient.
    	if(ingredient != null) {
    		ingredients = Mgr.getSameTypeIngredients(ingredient);
    		ingredients.add(ingredient);
    	// Creates panel for + button.
    	} else {
    		ingredients = phase.getPhaseIngredients();
    		ingredient = new Ingredient();
    	}

    	// Create panel for ingredient
    	JPanel ingredientPanel = new JPanel();
    	ingredientPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 0));

    	// Create combo box with multiple ingredients of the same type.
    	JComboBox<Ingredient> ingredientsComboBox = new JComboBox<Ingredient>(
    		ingredients.toArray(new Ingredient[ingredients.size()])
    	);

    	ingredientsComboBox.setSelectedItem(ingredient);
    	ingredientsComboBox.setFont(new Font("Georgia", 0, 14));
    	ingredientsComboBox.setPreferredSize(new Dimension(300, 30));
    	ingredientsComboBox.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent evt) {
    			ingredientsComboBoxActionPerformed(evt);
    		}
    	});

    	ingredientPanel.add(ingredientsComboBox);

    	// Create percent editor with default value
    	JTextField percent = new JTextField();
        percent.setFont(new Font("Georgia", 0, 14));
        percent.setPreferredSize(new Dimension(200, 30));
        percent.setHorizontalAlignment(SwingConstants.RIGHT);
        percent.setText(String.valueOf(ingredient.getAmount()));        
        percent.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				percentActionPerformed();
				
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				percentActionPerformed();
				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				percentActionPerformed();
				
			}
        });
        
        ingredientPanel.add(percent);
  
        // Create guidelines section
        JLabel guidelines = new JLabel();
        guidelines.setFont(new Font("Georgia", 0, 14));
        guidelines.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        guidelines.setPreferredSize(new Dimension(200, 30));
        guidelines.setText(ingredient.getGuidelines());
        guidelines.setToolTipText(ingredient.getGuidelines());
        
        ingredientPanel.add(guidelines);
        
        // Save ingredient components to get their values later.
        ArrayList<Component> elements = new ArrayList<Component>();
        elements.add(ingredientsComboBox);
        elements.add(percent);
        elements.add(guidelines);
        dataComponents.add(elements);
        
        // Add ingredient panel to dynamic panel
        dynamicPanel.add(ingredientPanel);
    }
   
}
