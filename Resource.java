
public class Resource {
	private int id;
	private int owner;
	private String policy;
	
	public Resource(int id, int owner, String policy) {
		super();
		this.id = id;
		this.owner = owner;
		this.policy = policy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
	
	
	
}
