import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Initializer {

	public Initializer()
	{
		
	}
	
	public ProtectionSystem initialize(String networkFilename, String resourceFilename, int nodeCap, int edgeCap)
	{
		//open the two files
		FileLineReader networkFile = new FileLineReader(networkFilename);
		FileLineReader resourceFile = new FileLineReader(resourceFilename);
		
		//create the social network
		Graph<Person,Relationship> socialNetwork = new Graph<Person,Relationship>(nodeCap, edgeCap);
		
		//node supplier for the node/edge creation method (see Graph#addEdgeAndNodes())
		Supplier<Person> nodeSupplier = () -> new Person();
		
		//read in the social network
		for(String line : networkFile.readLines())
		{
			//split the line around spaces
			String delim = "\\s+";
			String[] tokens = line.split(delim);
			
			//collect the three tokens
			int person1 = Integer.parseInt(tokens[0]);
			int person2 = Integer.parseInt(tokens[1]);
			String relationshipIdentifier = tokens[2];
			
			//create relationship object (edge attribute)
			Relationship rel = new Relationship(relationshipIdentifier);
			
			//create the edge and nodes
			socialNetwork.addEdgeAndNodes(person1, person2, rel, nodeSupplier);
		}
		
		//read in the resources
		List<Resource> resources = new ArrayList<Resource>();
		for(String line : resourceFile.readLines())
		{
			//split the line around spaces
			String delim = "\\s+";
			String[] tokens = line.split(delim);
			
			//collect the three tokens
			int id = Integer.parseInt(tokens[0]);
			int owner = Integer.parseInt(tokens[1]);
			String policy = tokens[2];
			
			//add the resource
			Resource resource = new Resource(id, owner, policy);
			resources.add(resource);
		}
		
		return new ProtectionSystem(socialNetwork, resources);
	}
}
