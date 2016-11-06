package editor;

/**Represent one category in memory*/
public class CategoriesNode {
	private int id;
	private int parentId;
	private String name;
	
	public CategoriesNode(int id, int parentId, String name){
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public int getParentId() {
		return parentId;
	}
	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
