import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Describes recipe UI.
 * @author ipaluyanava
 *
 */
public class RecipeDisplay extends JFrame {
    private JButton closeBtn;
    private JLabel ingredientLbl;
    private JLabel jLabel1;
    private JPanel bottomPanel;
    private JPanel dynamicPanel;
    private JLabel percentLbl;
    private JLabel recipeNameLbl;
    private JButton saveAsBtn;
    
    private int amount;
    private int totalPercent = 0;
    private int totalGrams = 0;
	private Formula formula;
	
	// Storage for easy iteration later. 
	private ArrayList<ArrayList<Component>> cmps = new ArrayList<ArrayList<Component>>();
	
	/**
	 * Creates recipe UI and sets some data.
	 * @param amount the amount to create recipe for.
	 * @param formula the formula to create recipe for.
	 */
    public RecipeDisplay(int amount, Formula formula) {
    	this.amount = amount;
    	this.formula = formula;
        initComponents();
    }
    
    /**
     * Builds UI.
     */
    private void initComponents() {
        recipeNameLbl = new JLabel();
        bottomPanel = new JPanel();
        saveAsBtn = new JButton();
        closeBtn = new JButton();
        dynamicPanel = new JPanel();
        ingredientLbl = new JLabel();
        percentLbl = new JLabel();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Setup top part of the form
        recipeNameLbl.setFont(new Font("Georgia", 1, 16));
        recipeNameLbl.setText(formula.getName());
        recipeNameLbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        recipeNameLbl.setBackground(new Color(204, 204, 204));
        recipeNameLbl.setOpaque(true);
        getContentPane().add(recipeNameLbl, BorderLayout.PAGE_START);

        //Setup bottom part of the form
        bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        bottomPanel.setBackground(new Color(204, 204, 204));
        bottomPanel.setOpaque(true);

        saveAsBtn.setFont(new Font("Georgia", 1, 14));
        saveAsBtn.setText("Save As...");
        saveAsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveAsBtnActionPerformed(evt);
            }
        });
        bottomPanel.add(saveAsBtn);

        closeBtn.setFont(new Font("Georgia", 1, 14));
        closeBtn.setText("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });
        bottomPanel.add(closeBtn);

        getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
        
        // Setup dynamic/middle part of the form.
        dynamicPanel.setLayout(new GridLayout(0, 3, 10, 2));
        dynamicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ingredientLbl.setFont(new Font("Georgia", 1, 14));
        ingredientLbl.setText("Ingredient");
        dynamicPanel.add(ingredientLbl);

        percentLbl.setFont(new Font("Georgia", 1, 14));
        percentLbl.setText("Percent %");
        dynamicPanel.add(percentLbl);

        jLabel1.setFont(new Font("Georgia", 1, 14));
        jLabel1.setText("Amount(grams)");
        dynamicPanel.add(jLabel1);

        addToRecipe();
        
        getContentPane().add(dynamicPanel, BorderLayout.CENTER);

        pack();
    }                       
    
    /**
     * Saves recipe to a plain text file.
     * @param evt
     */
    private void saveAsBtnActionPerformed(ActionEvent evt){   
    	BufferedWriter writer = null;
    	try {
    		String userDir = System.getProperty("user.home");
    		JFileChooser chooser = new JFileChooser(userDir +"/Desktop");
    		int returnVal = chooser.showSaveDialog(this);
    		if(returnVal == JFileChooser.APPROVE_OPTION) {   			
    			File file = new File(chooser.getSelectedFile().toString());
    			if (file.exists()) {
    				int response = JOptionPane.showConfirmDialog(this,
    						"Do you want to replace the existing file?",
    						"Confirm", JOptionPane.YES_NO_OPTION,
    						JOptionPane.QUESTION_MESSAGE);
    				if (response != JOptionPane.YES_OPTION) {
    					return;
    				} 
    			}
    			
    			writer = new BufferedWriter(new FileWriter(file));
    	        writeDataToFile(writer);
    	        saveAsBtn.setEnabled(false);
    		}
    	} catch (Exception e) { 
    		System.out.println("Error occured while writing to file: " + e.toString());
    	} finally {
    		try{
    			writer.close();
    		} catch (Exception e) {
    			System.out.println("Issues closing writer: " + e.toString());
    		}
        }	 
    }                                         
    
    /**
     * Closes recipe form.
     * @param evt
     */
    private void closeBtnActionPerformed(ActionEvent evt) {
    	this.dispose();
    }                                            
    
    /**
     * Add dynamic fields to UI.
     */
    private void addToRecipe() {
    	// Add titles and first phase
		ArrayList<Ingredient> processLater = processPhase(formula.getIngredients());
		// Add rest of phases 
		while(processLater.size() != 0) {
			processLater = processPhase(processLater);
		}
    	//Add totals
		ArrayList<Component> subComps = new ArrayList<Component>();
		JLabel lbl = createLabel("Total");
		lbl.setFont(new Font("Georgia", 1, 14));
		subComps.add(lbl);
		subComps.add(
			createLabel(String.valueOf(totalPercent))
	    );
		subComps.add(
			createLabel(String.valueOf(totalGrams))
	    );
		cmps.add(subComps);
    	
    }

    /**
     * Process phases of ingredients by combining them in logical groups
     * and displaying them in UI.
     * @param ingredients the ingredients to process.
     * @return ingredients that still need to be processed.
     */
	private ArrayList<Ingredient> processPhase(ArrayList<Ingredient> ingredients) {
		String phase = "";
		ArrayList<Component> subComps;
		ArrayList<Ingredient> processLater = new ArrayList<Ingredient>();

    	for(Ingredient i: ingredients) {
    		if(phase.equals("")) {
    			subComps = new ArrayList<Component>();
    			phase = i.getPhase();
    			JLabel lbl = createLabel(phase + " Phase");
    			lbl.setFont(new Font("Georgia", 1, 14));
    			subComps.add(lbl);
    			subComps.add(
    				createLabel("")
        	    );
    			subComps.add(
    				createLabel("")
        	    );
    			cmps.add(subComps);
    		}
    		
    		if(!i.getPhase().equals(phase)) {
    			processLater.add(i);
    			continue;
    		}
    		
    		subComps = new ArrayList<Component>();
    		int percentAmount = i.getAmount();
    		int gramAmount = (percentAmount * amount)/100;
    		totalPercent += percentAmount;
    		totalGrams += gramAmount;
    				
    		subComps.add(
    			createLabel(i.getName())
    		);
    		subComps.add(
    			createLabel(String.valueOf(percentAmount))
    		);
    		subComps.add(
    			createLabel(String.valueOf(gramAmount))
    		);
    		cmps.add(subComps);
    	}
    	
    	return processLater;
	}    
    
	/**
	 * Create label field.
	 * @param value is the value to set label's text to.
	 * @return label.
	 */
    private JLabel createLabel(String value) {
    	JLabel lbl = new JLabel();
		lbl.setFont(new Font("Georgia", 0, 14));
        lbl.setText(value);
        dynamicPanel.add(lbl);
        return lbl;
    }
    
    /**
     * Write data to a file.
     * @param writer BufferedWriter object.
     * @throws IOException when error occurred during writing.
     */
    private void writeDataToFile(BufferedWriter writer) throws IOException {
    	writer.write(
    			formula.getName() + 
    			System.getProperty("line.separator") + 
    			System.getProperty("line.separator")
    	);
    	writer.write(
    		"Ingredient\t\t\tPercent %\t\t\tAmount(Gram)" + 
    		System.getProperty("line.separator") + 
    		System.getProperty("line.separator")
    	);  
    	for(ArrayList<Component> subComps: cmps) {
    		String str = "";
    		for(Component cmp: subComps) {
    			str += ((JLabel) cmp).getText() + "\t\t\t";
    		}
    		writer.write(str + System.getProperty("line.separator"));
    	}
    }       
}
