package editor;

/**Represent one good in memory*/
public class GoodsNode {
	
	private int id;
	private int categoryId;
	private String name;
    private String description;
	private double price;
	private String pathToImage;
		
	public int getId() {
		return id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getPathToImage() {
		return pathToImage;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}

}
