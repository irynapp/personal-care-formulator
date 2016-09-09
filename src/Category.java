import java.util.ArrayList;

/**
 * Describes category.
 * @author ipaluyanava
 */
public class Category {
	private int id;
	private String name;
	private ArrayList<Formula> formulas = new ArrayList<Formula>();

	/**
	 * Returns category unique identifier.
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set category unique identifier.
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns category name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets category name.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns list of category formulas.
	 * @return the category formulas
	 */
	public ArrayList<Formula> getFormulas() {
		return formulas;
	}
	
	/**
	 * Defines a string to display a category.
	 */
	public String toString() {
		return name;
	}
}
