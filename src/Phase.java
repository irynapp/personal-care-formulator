import java.util.ArrayList;

/**
 * Describes phase object.
 * @author ipaluyanava
 *
 */
public class Phase {
	private String name;
	private boolean isModified = false;
	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
	private ArrayList<Ingredient> phaseIngredients = new ArrayList<Ingredient>();

	/**
	 * Gets list of ingredients.
	 * @return the list of ingredients.
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Gets the phase name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets phase name.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Displays Phase.
	 */
	public String toString() {
		return String.format("[%s, %s]", name, ingredients);
	}

	/**
	 * Gets  phase ingredients list.
	 * @return the phaseIngredients
	 */
	public ArrayList<Ingredient> getPhaseIngredients() {
		return phaseIngredients;
	}
	
	/**
	 * Sets phase ingredient list.
	 * @param ingredients the ingredient list to set to.
	 */
	public void setPhaseIngredients(ArrayList<Ingredient> ingredients) {
		phaseIngredients = ingredients;
		
	}

	/**
	 * Returns modified field.
	 * @return the isModified
	 */
	public boolean isModified() {
		return isModified;
	}

	/**
	 * Sets modified field.
	 * @param isModified the isModified to set
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
}
