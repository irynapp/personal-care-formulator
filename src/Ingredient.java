/**
 * Describes ingredient.
 * @author ipaluyanava
 *
 */
public class Ingredient {
	private int id = 0;
	private String name = "";
	private String inciName = "";
	private String type = "";
	private String guidelines = "";
	private String phase = "";
	private int amount = 0;

	/**
	 * Returns ingredient's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets ingredient's name.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns ingredient's type.
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets ingredient's type.
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns ingredients' guidelines.
	 * @return the guidelines
	 */
	public String getGuidelines() {
		return guidelines;
	}

	/**
	 * Sets ingredients' guidelines.
	 * @param guidelines the guidelines to set
	 */
	public void setGuidelines(String guidelines) {
		this.guidelines = guidelines;
	}

	/** 
	 * Returns ingredient's phase.
	 * @return the phase
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * Sets ingredient's phase.
	 * @param phase the phase to set
	 */
	public void setPhase(String phase) {
		this.phase = phase;
	}

	/**
	 * Returns ingredient's unique identifier.
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets ingredient's unique identifier.
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns ingredient's INCI name.
	 * @return the inciName
	 */
	public String getInciName() {
		return inciName;
	}

	/**
	 * Sets ingredient's INCI name.
	 * @param inciName the inciName to set
	 */
	public void setInciName(String inciName) {
		this.inciName = inciName;
	}

	/**
	 * Returns ingredient's amount.
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets ingredient's amount.
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * Displays ingredient.
	 */
	public String toString() {
		return name;
	}
}
