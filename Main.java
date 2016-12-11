import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class Main {

	public static void main(String[] args) {
				
		//command line argument constants
		final int numArgs = 2;
		final int networkFileArg = 0;
		final int resourceFileArg = 1;
		
		//social network initial graph size constants
		final int nodeCap = 500;
		final int edgeCap = 250000;
		
		String networkFilename = new String();
		String resourceFilename = new String();
		
		//check for command line arguments.
		//if wrong number of args, print usage message and exit
		if(args.length != numArgs)
		{
			System.out.println("Usage: rebac networkfile resourcefile");
			System.exit(1);
		}
		//if correct number, get args
		else
		{
			networkFilename = args[networkFileArg];
			resourceFilename = args[resourceFileArg];
		}

		//initialize the protection system
		Initializer init = new Initializer();
		ProtectionSystem system = init.initialize(networkFilename, resourceFilename, nodeCap, edgeCap);
		
		//get the social network and resource list
		Graph<Person, Relationship> socialNetwork = system.getSocialNetwork();
		List<Resource> resources = system.getResources();
		
		//create authorizer
		Authorizer auth = new Authorizer();
		
		//accept access requests
		Scanner console = new Scanner(System.in);
		boolean validChoice = false;
		int choice = -1;
		while(validChoice == false)
		{
			validChoice = true;
			System.out.println("Type (1) to make an access request or (2) to exit, then hit enter");
			String choiceString = console.nextLine();
			try
			{
				choice = Integer.parseInt(choiceString);
			}
			catch(NumberFormatException nfe)
			{
				System.out.println("Error! Invalid input. Please try again.");
				validChoice = false;
			}
		}
		
		//input choice 1 means access request
		while(choice == 1)
		{
			//prompt for the request
			System.out.println("Enter an access request of the form accessorID resourceID, and then hit enter.");
			
			//read it
			String request = console.nextLine();
			
			//split the string (should be two ints delimited by a space)
			String delim = "\\s+";
			String[] tokens = request.split(delim);
			
			//if there isn't enough input, try again
			if(tokens.length < 2)
			{
				System.out.println("Error! Invalid input. Please try again.");
				continue;
			}
			
			//try to parse the input
			int accessor = -1;
			int resourceId = -1;
			try
			{
				accessor = Integer.parseInt(tokens[0]);
				resourceId = Integer.parseInt(tokens[1]);
			}
			//catch invalid input
			catch(NumberFormatException nfe2)
			{
				System.out.println("Error! Invalid input. Please try again.");
			}
			//if input was invalid, try again
			if(accessor == -1 || resourceId == -1)
			{
				continue;
			}
			
			if(accessor >= socialNetwork.numNodes() || accessor < 0)
			{
				System.out.println("Error! No such user exists. Please try again.");
				continue;
			}
			
			if(resourceId >= resources.size() || resourceId < 0)
			{
				System.out.println("Error! No such resource exists. Please try again.");
			}
				
			
			//if we made it this far, input was valid
			
			//get the resource, owner, and policy
			Resource resource = resources.get(resourceId);
			int owner = resource.getOwner();
			String policy = resource.getPolicy();
			
			//make an authorization decision
			boolean decision = auth.authorize(socialNetwork, accessor, owner, policy);
			
			//print the result
			System.out.printf("Accessor %d -- Resource: %d -- Owner: %d \n\n ****Decision: %s****\n\n", accessor, resourceId, owner, Boolean.toString(decision));
		
			//prompt for another choice
			validChoice = false;
			choice = -1;
			while(validChoice == false)
			{
				validChoice = true;
				System.out.println("Type (1) to make an access request or (2) to exit, then hit enter");
				String choiceString = console.nextLine();
				try
				{
					choice = Integer.parseInt(choiceString);
				}
				catch(NumberFormatException nfe)
				{
					System.out.println("Error! Invalid input. Please try again.");
					validChoice = false;
				}
			}
		}
		
		console.close();
	}

}
