import java.util.ArrayList;

/**
 * Describes formula.
 * @author ipaluyanava
 */
public class Formula {
	private int id;
	private String name;
	private boolean isBasic = false; //indicates if formula is basic or not.
	private int categoryId;
	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	/**
	 * Returns formula unique identifier.
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets formula unique identifier.
	 * @param id the id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns formula name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets formula name and basic identifier.
	 * @param name the name to set.
	 */
	public void setName(String name) {
		if(name.equals("Basic Formula")) {
			isBasic = true;
		}
		this.name = name;
	}
	
	/**
	 * Returns list of formula ingredients.
	 * @return the ingredients.
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Returns basic identifier.
	 * @return the true if formula is basic; false, otherwise.
	 */
	public boolean isBasic() {
		return isBasic;
	}
	
	/**
	 * String to display formula.
	 */
	public String toString() {
		return String.format("[%d, %s, %s]", id, name, ingredients);
	}
	
	/**
	 * Returns formula's category id.
	 * @return the category_id.
	 */
	public int getCategoryId() {
		return categoryId;
	}
	
	/**
	 * Sets formula's category id.
	 * @param category_id the category id to set.
	 */
	public void setCategoryId(int category_id) {
		this.categoryId = category_id;
	}
}
