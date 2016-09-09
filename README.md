# Personal Care Formulator

It is an application that assist user in development of personal care products (body lotions, lip balms, body balms, etc.) by providing information on ingredients, basics of formulation and formulas.

## Quick Summary:
- The tool has access to a database with information on various ingredients.
- It contains a set of predefine basic categories.
- Each category contains at least one basic/starter formula/template and one real formula.
- It allows user to add new formula into an existing category.
- It allows user to create an actual recipe based on the existing formula and save it into a plain text file.

## Current Prototype Functionalities:
- The tool has access to PFDB.db sqlite database that composed of 4 tables:
  - Categories – contains information for each category (examples: lip balm, body balm, etc.).
  - Formulas – contains information for each formula within category (examples: Coconut Oil Lip Balm, After Sun Body Balm, etc.).
  - Ingredients – contains information on variety of ingredients (examples: coca butter, extra virgin olive oil, vitamin E, etc.).
  - FormulaDetails – contains information about composition of formula.
- It contains two predefined categories with the few formulas in each (including Basic Formula).
- It allows user to perform the following operations:
  - View category and track number of formulas in it.
  - Change between categories.
  - Modify any existing formula or create a new formula based on the Basic formula (**_Note:_** the idea is to always use a template for any brand new formula).
  - Add new ingredient to an ingredient phase during formula creation/modification.
  - Check total percent while formulating (**_Note:_** final formula should always sum up to 100%, otherwise formula cannot be saved to DB).
  - Save any changes made to a formula.
  - Calculate a recipe based on a formula by specifying number of grams (**_Note:_** in order to use this functionality, formula should sum up to 100%).
  - Save recipe to a plain text file for printing or later use.

## Implementation Details:
**Data Classes** – describe metadata and act as _Model_
  - Category.java
  - Formula.java
  - Ingredient.java
  - Phase.java – describes a formula phase (example: Oil, Water or Cool Down).

**UI Classes** – act as _View_
- CategoryPanel.java – category that holds formula buttons.
- FormulaFrame.java – describes formula (name, phases), allows to calculate total running percent, calculate recipe, add new ingredients into phase and save formula to DB.
- PhasePanel.java- describes formula phase with name, ingredient titles, ingredient containers and addition button; it is used by FormulaFrame.
- RecipeDisplay.java – displays recipe that calculated based on the specified number of grams.
- Formulator.java- main form with category selector, formula count and category panels.
- Simulator.java – starting point of an app – it creates main application form.

**Manager** – acts as _Controller_
- Mgr.java - it handles all the manipulations with DB data based on UI requests.

### Known Issues and Limitations
- Due to a poor choice of a layout some formulas can be invisible or half visible on the Category panel when the number of formula buttons increases.
- During real life formulations, percent and amount can contain decimal points, current prototype handles integers only.
- Poor text formatting of the recipe file.
- Checks and validations are not present everywhere.

###Future Functionalities and Improvements
- Fix Category/Formulator layout to display all the formulas in a category.
- Develop functionality to add category (and required basic formula for it).
- Add functionality to print recipe.
- Add functionality to save recipe as PDF document.
- Figure out how to add text formatting while saving recipe to a file.
- Add additional checks and validations.
- Create a UI Manager similar to the existing data manager.
