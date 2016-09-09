import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Manages UI access to DB data.
 * @author ipaluyanava
 *
 */
public class Mgr {
	/**
	 * Returns connection to DB.
	 * @return the app DB connection.
	 */
	private static Connection connectToDB() {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:PFDB.db";
			conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
               //System.out.println("Connected to the database:" + dbURL);
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * Get list of formula unique identifiers.
	 * @param formulas the formula object to use to get ids.
	 * @return list of ids.
	 */
	private static ArrayList<Integer> getFormulaIds(ArrayList<Formula> formulas) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(Formula formula: formulas) {
			ids.add(formula.getId());
		}
		return ids;
	}
	
	/**
	 * Returns formula object by the specified formula id.
	 * @param formulas the list of formulas to search.
	 * @param id the id to look for.
	 * @return the formula object.
	 */
	private static Formula getFormulaById(ArrayList<Formula> formulas, int id) {
		for(Formula formula: formulas) {
			if(formula.getId() == id) {
				return formula;
			}
		}
		return null;
	}
	
	/**
	 * Returns list of categories and their formulas. 
	 * Used in Formulator class.
	 * @return the list of category objects.
	 */
	public static ArrayList<Category> getCategories() {
		int timeout = 30;
		ArrayList<Category> categories = new ArrayList<Category>();
		String stmtStr = "SELECT Categories.category_id, Categories.name, Formulas.formula_id, Formulas.name AS formula_name " +
				"FROM Categories JOIN Formulas ON Categories.category_id = Formulas.category_id ORDER BY Categories.category_id";
		Connection conn = connectToDB();

		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(timeout);
			ResultSet rs = stmt.executeQuery(stmtStr);
			int currCatId = 0;
			Category cat = null;
			while(rs.next()) {
				int catId = rs.getInt("category_id");
				if(catId != currCatId) {
					cat = new Category();
					categories.add(cat);
					cat.setId(catId);
					cat.setName(rs.getString("name"));	
					currCatId = catId;
					
				}
				Formula formula = new Formula();
				formula.setId(rs.getInt("formula_id"));
				formula.setName(rs.getString("formula_name"));
				formula.setCategoryId(catId);
				cat.getFormulas().add(formula);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return categories;
    }
	
	/**
	 * Get ingredients for each formula in category.
	 * @param formulas the list of formulas to get ingredients for.
	 */
	public static void getIngredientsForCategoryFormulas(ArrayList<Formula> formulas) {	
		String formulaIds = getFormulaIds(formulas).stream()
			.map(Object::toString)
        	.collect(Collectors.joining(", "));
		
		int timeout = 30;
		String stmtStr = "SELECT formula_id, Ingredients.ingredient_id, name, inci_name, type, guidelines, phase, amount FROM FormulaDetails JOIN Ingredients " +
			"WHERE FormulaDetails.ingredient_id = Ingredients.ingredient_id AND formula_id in (" + formulaIds + ") ORDER BY formula_id";
		Connection conn = connectToDB();
		
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(timeout);
			ResultSet rs = stmt.executeQuery(stmtStr);
			int currFormulaId = 0;
			Formula currFormula = null;
			while(rs.next()) {
				int formulaId = rs.getInt("formula_id");
				if(currFormulaId != formulaId) {
					currFormula = getFormulaById(formulas, formulaId);
					currFormulaId = formulaId;
				}
				
				Ingredient ingredient = new Ingredient();
				ingredient.setId(rs.getInt("ingredient_id"));
				ingredient.setName(rs.getString("name"));
				ingredient.setInciName(rs.getString("inci_name"));
				ingredient.setType(rs.getString("type"));
				ingredient.setGuidelines(rs.getString("guidelines"));
				ingredient.setPhase(rs.getString("phase"));
				ingredient.setAmount(rs.getInt("amount"));
				
				currFormula.getIngredients().add(ingredient);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
   }
	
	/**
	 * Returns formula object based on the formula name
	 * @param formulas the list of formulas to search.
	 * @param btnName the name to search for.
	 * @return the formula object.
	 */
	public static Formula getSelectedFomula(ArrayList<Formula> formulas, String btnName) {
		for(Formula formula: formulas) {
			if(formula.getName() == btnName) {
				return formula;
			}
		}
		return null;
	}

	/**
	 * Gets all ingredients of the same type to use within combo box.
	 * @param ingr the ingredient object to base on.
	 * @return list of ingredients of the same type.
	 */
	public static ArrayList<Ingredient> getSameTypeIngredients(Ingredient ingr) {
		ArrayList<Ingredient> sameTypeIngredients = new ArrayList<Ingredient>();
		int timeout = 30;
		String stmtStr = "SELECT * FROM Ingredients WHERE type =='" + 
				ingr.getType() + "' AND name != '" + ingr.getName() +"'";
		Connection conn = connectToDB();
		
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(timeout);
			ResultSet rs = stmt.executeQuery(stmtStr);
			while(rs.next()) {		
				Ingredient ingredient = new Ingredient();
				ingredient.setId(rs.getInt("ingredient_id"));
				ingredient.setName(rs.getString("name"));
				ingredient.setInciName(rs.getString("inci_name"));
				ingredient.setType(rs.getString("type"));
				ingredient.setGuidelines(rs.getString("guidelines"));
				ingredient.setPhase(rs.getString("phase"));
				
				sameTypeIngredients.add(ingredient);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return sameTypeIngredients;
	}

	/**
	 * Gets all the ingredients for the specified phase to use with "plus" button.
	 * @param phaseName is the phase to pull ingredients for.
	 * @return list of ingredients from the same phase.
	 */
	public static ArrayList<Ingredient> getPhaseIngredients(String phaseName) {
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		int timeout = 30;
		String stmtStr = "SELECT * FROM Ingredients WHERE phase =='" + phaseName + "'";
		Connection conn = connectToDB();
		
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(timeout);
			ResultSet rs = stmt.executeQuery(stmtStr);
			while(rs.next()) {		
				Ingredient ingredient = new Ingredient();
				ingredient.setId(rs.getInt("ingredient_id"));
				ingredient.setName(rs.getString("name"));
				ingredient.setInciName(rs.getString("inci_name"));
				ingredient.setType(rs.getString("type"));
				ingredient.setGuidelines(rs.getString("guidelines"));
				ingredient.setPhase(rs.getString("phase"));
				
				ingredients.add(ingredient);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return ingredients;
	}
	
	/**
	 * Create phase objects that will be used in FormulaFrame
	 * for phase panels creation.
	 * @param formula the formula object to base phases on. 
	 * @return list of phases.
	 */
	public static ArrayList<Phase> createPhases(Formula formula) {
		ArrayList<Phase> phases = new ArrayList<Phase>();
    	for(Ingredient ingredient: formula.getIngredients()) {
    		String phaseName = ingredient.getPhase();
    		Phase p = getPhaseObject(phases, phaseName);
    		p.getIngredients().add(ingredient);
    	}
    	return phases;
    }
    
	/**
	 * Gets phase object if already exists or creates new one..
	 * @param phases the list of phases to search for.
	 * @param phaseName the phase name to search for.
	 * @return existing or totally brand new phase object.
	 */
    private static Phase getPhaseObject(ArrayList<Phase> phases, String phaseName) {
    	Phase phase = null;
    	for(Phase p: phases) {
    		if(Objects.equals(p.getName(), phaseName)) {
    			phase = p;
    			break;
    		}
    	}
    	
    	if(phase == null) {
    		phase = new Phase();
    		phase.setName(phaseName);
    		phases.add(phase);
    	}

    	return phase;
	}	

    /**
     * Add new formula to Formulas and FormulaDetails tables.
     * @param formula the formula to add to DB.
     * @throws Exception when key constraint is not met. 
     */
    public static void addNewFormula(Formula formula) throws Exception {
    	formula.setId(getFormulaId());
    	Connection conn = connectToDB();
		conn.setAutoCommit(false);
		Statement stmt = conn.createStatement();
		stmt.setQueryTimeout(30);
		
		try {
			//Insert new formula into Formulas table
			String stmtStr = "INSERT INTO Formulas (formula_id, name, category_id) VALUES ("
				+ formula.getId() + ", '" + formula.getName() + "', " + formula.getCategoryId() + ")";
			stmt.executeUpdate(stmtStr);

			// Add new formula details
			for(Ingredient i: formula.getIngredients()) {
				stmtStr = "INSERT INTO FormulaDetails (formula_id, ingredient_id, amount) VALUES ("
					+ formula.getId() + ", " + i.getId() + ", " + i.getAmount() + ")";
				System.out.println(stmtStr);
				stmt.executeUpdate(stmtStr);
			}

			conn.commit();

		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			throw e;
    	} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		} finally {
			stmt.close();
			conn.close();
		}
    }
    
    /**
     * Get id for new formula.
     * @return unique identifier for a formula.
     */
    private static int getFormulaId() {
    	int max = 0;
		try {
			Connection conn = connectToDB();
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30);
			String stmtStr = "SELECT MAX(formula_id) FROM Formulas";
			ResultSet rs = stmt.executeQuery(stmtStr);
			rs.next(); // exactly one result
			max = rs.getInt(1) + 1;
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return max;
    }
}
		