package com.digitale.connex;



public class Inventory
{
	private int itemUid;
	private int slot_id;
	private int inventoryid;
	private String category;
	private String subcat;
	private String item;
	private int mass;
	private int stacks;
	private String description;
	private int contraband;
	private int recipe;
	private int capacity;
	private int level;
	private String icon;
	private int quality;
	private int value;
	private String effect;
	private int bind;
	private int count;
	
	
	public Inventory(
			int itemUid,
			 int slot_id,
			 int inventoryid,
			 String category,
			 String subcat,
			 String item,
			 int mass,
			 int stacks,
			 String description,
			 int contraband,
			 int recipe,
			 int capacity,
			 int level,
			 String icon,
			 int quality,
			 int value,
			 String effect,
			 int bind,
			 int count) {
		this.itemUid=itemUid;
		this.slot_id=slot_id;
		this.inventoryid=inventoryid;
		this.category=category;
		this.subcat=subcat;
		this.item=item;
		this.mass=mass;
		this.stacks=stacks;
		this.description=description;
		this.contraband=contraband;
		this.recipe=recipe;
		this.capacity=capacity;
		this.level=level;
		this.icon=icon;
		this.quality=quality;
		this.setValue(value);
		this.setEffect(effect);
		this.setBind(bind);
		this.setCount(count);
		}
	
	public Inventory() {
		// TODO Auto-generated constructor stub
	}

	public void setitemUid(int itemUid) {
		this.itemUid = itemUid;
	}

	public int getitemUid() {
		return itemUid;
	}
	public void setSlot_id(int slot_id) {
		this.slot_id = slot_id;
	}

	public int getSlot_id() {
		return slot_id;
	}

	public void setInventoryid(int inventoryid) {
		this.inventoryid = inventoryid;
	}

	public int getInventoryid() {
		return inventoryid;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setSubcat(String subcat) {
		this.subcat = subcat;
	}

	public String getSubcat() {
		return subcat;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItem() {
		return item;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public int getMass() {
		return mass;
	}

	public void setStacks(int stacks) {
		this.stacks = stacks;
	}

	public int getStacks() {
		return stacks;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setContraband(int contraband) {
		this.contraband = contraband;
	}

	public int getContraband() {
		return contraband;
	}

	public void setRecipe(int recipe) {
		this.recipe = recipe;
	}

	public int getRecipe() {
		return recipe;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}


	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getQuality() {
		return quality;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	/**
	 * @return the bind
	 */
	public int getBind() {
		return bind;
	}

	/**
	 * @param bind the bind to set
	 */
	public void setBind(int bind) {
		this.bind = bind;
	}

	public int getCount(){
		return count;
	}
	public void setCount(int count) {
	
		this.count=count;
	}

	
	}

	




		
