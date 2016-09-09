
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * Final Project
 * CS565 - Advanced Java Programming
 * Due Date: 04/26/2016
 * @author ipaluyanava
 *
 * Creates main form of the application.
 * @author ipaluyanava
 */
public class Formulator extends JFrame {                   
    private JPanel bottomPanel;
    private JComboBox<Category> categorySelector;
    private JLabel formulasCount;
    private JLabel formulasNumLabel;
    private JLabel jLabel1;
    private JSplitPane mainSplit;
    private JPanel topPanel;
    private ArrayList<Category> categories;
    private ArrayList<CategoryPanel> catPanels;
    
    /**
     * Gets categories from DB and builds the app UI.
     */
    public Formulator() {
    	categories = Mgr.getCategories();
    	catPanels = new ArrayList<CategoryPanel>();
        initComponents();
    }
              
    private void initComponents() {
        mainSplit = new JSplitPane();
        topPanel = new JPanel();
       
        jLabel1 = new JLabel();
        formulasNumLabel = new JLabel();
        formulasCount = new JLabel();
        bottomPanel = new JPanel();
 
        // TODO: check if nicer title is possible.
        setTitle("Personal Care Formulator");

        mainSplit.setDividerLocation(60);
        mainSplit.setDividerSize(2);
        mainSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);

        topPanel.setBackground(new Color(204, 204, 204));
        topPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, 
        		new Color(204, 204, 204), new Color(204, 204, 204), new Color(153, 153, 153), new Color(153, 153, 153)), 
        		BorderFactory.createEtchedBorder(new Color(204, 204, 204), new Color(153, 153, 153)
        )));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        // Creating combo box with categories
        categorySelector = new JComboBox<Category>(categories.toArray(new Category[categories.size()]));
        categorySelector.setFont(new Font("Georgia", 0, 14));
        categorySelector.setAutoscrolls(true);
        categorySelector.setPreferredSize(new Dimension(420, 30));
        categorySelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                categorySelectorActionPerformed(evt);
            }
        });
        topPanel.add(categorySelector);

        jLabel1.setPreferredSize(new Dimension(65, 15));
        topPanel.add(jLabel1);

        formulasNumLabel.setFont(new Font("Georgia", 1, 14));
        formulasNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formulasNumLabel.setText("Number of Formulas:");
        topPanel.add(formulasNumLabel);

        formulasCount.setFont(new Font("Georgia", 0, 14));
        formulasCount.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(formulasCount);

        mainSplit.setTopComponent(topPanel);

        bottomPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, 
        		new Color(204, 204, 204), new Color(204, 204, 204), Color.gray, Color.gray), 
        		BorderFactory.createEtchedBorder(new Color(204, 204, 204), new Color(204, 204, 204)
        )));
        
        // Add individual category panels with formulas
        bottomPanel.setLayout(new CardLayout());
        for(Category category: categories) {
    		CategoryPanel catPanel = new CategoryPanel(category);
    		catPanels.add(catPanel);
    		bottomPanel.add(catPanel, category.getName());
    	}
		
        // Finally set count of formulas in the currently selected category
        formulasCount.setText(String.valueOf(getSelectedCategory().getFormulas().size()));
        
        mainSplit.setRightComponent(bottomPanel);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainSplit, GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainSplit, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
        );

        pack();
    }                   
    
    /**
     * Change category content when category changed.
     * @param evt
     */
    private void categorySelectorActionPerformed(ActionEvent evt) {                                                 
    	CardLayout cl = (CardLayout) bottomPanel.getLayout();
    	Category selectedCategory = getSelectedCategory();
    	cl.show(bottomPanel, selectedCategory.getName());
    	formulasCount.setText(String.valueOf(selectedCategory.getFormulas().size()));
    }
    
    /**
     * Returns currently selected category.
     * @return the currently selected category.
     */
    public Category getSelectedCategory() {
    	return (Category) categorySelector.getSelectedItem();
    }
    
    /**
     * Returns count label reference;
     * needed for reseting count when new formula is added.
     * @return the formulasCount
     */
    public JLabel getCountLbl() {
    	return formulasCount;
    }
}
