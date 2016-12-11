import java.util.List;

public class ProtectionSystem {

	private Graph<Person,Relationship> socialNetwork;
	private List<Resource> resources;
	
	
	public ProtectionSystem(Graph<Person, Relationship> socialNetwork, List<Resource> resources) {
		super();
		this.socialNetwork = socialNetwork;
		this.resources = resources;
	}


	public Graph<Person, Relationship> getSocialNetwork() {
		return socialNetwork;
	}


	public void setSocialNetwork(Graph<Person, Relationship> socialNetwork) {
		this.socialNetwork = socialNetwork;
	}


	public List<Resource> getResources() {
		return resources;
	}


	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	
	
}
