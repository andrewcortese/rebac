import java.util.LinkedList;
import java.util.List;

public class Authorizer {
	public Authorizer(){
		
	}
	
	/*
	 * This is the entry point method for authorization of an access request
	 */
	public boolean authorize(Graph<Person,Relationship> socialNetwork, int accessor, int owner, String policy)
	{
		//break up the policy into atomic policies
		LinkedList<PolicyAtom> atoms = this.atomize(policy);
		
		//use the atomic authorize method to make an authorization decision for each atomic policy
		for(PolicyAtom a : atoms)
		{
			boolean result = this.atomAuthorize(socialNetwork, accessor, owner, a.getSubPolicy());
			a.setAuthorizationResult(result);
		}
		
		//now we need to apply the AND and OR operators
		
		//grab the decision from the first atomic policy
		boolean decision = true;
		if(atoms.size() > 0)
		{
			decision = atoms.getFirst().getAuthorizationResult();
		}
		
		//apply the operators, one atomic policy at a time
		System.out.print("\nThe policy is: ");
		int i = 0;
		while(i<atoms.size())
		{
			PolicyAtom a = atoms.get(i);
			
			//print this policy atom
			String operatorString = new String();
			if(a.nextOperator != PropositionalOperator.end)
			{
				operatorString = a.getNextOperator().toString();
			}
			System.out.printf("%s %s ", a.getSubPolicy(), operatorString);
			
			//if the operator to the right is an AND, then && the running decision with the next one
			if(a.getNextOperator() == PropositionalOperator.and)
			{
				PolicyAtom b = atoms.get(i+1);
				decision = decision && b.getAuthorizationResult(); 
			}
			
			//if the operator to the right is an OR, then || the running decision with the next one
			else if(a.getNextOperator() == PropositionalOperator.or)
			{
				PolicyAtom b = atoms.get(i+1);
				decision = decision || b.getAuthorizationResult(); 
			}
			
			//if the operator is END, break out of the loop.
			else
			{
				break;
			}
			
			i++;
		}
		System.out.println();
		return decision;
	}
	
	/*
	 * This method breaks a positive policy string into atomic policies by tokenizing around "^" and "v"
	 */
	private LinkedList<PolicyAtom> atomize(String policy)
	{
		LinkedList<PolicyAtom> orAtoms = new LinkedList<PolicyAtom>();
		LinkedList<PolicyAtom> allAtoms = new LinkedList<PolicyAtom>();
		
		String delim = "#";
		String[] tokens = policy.split(delim);
		for(String tok : tokens)
		{
			PolicyAtom a = new PolicyAtom(new String(tok), PropositionalOperator.or);
			orAtoms.add(a);
		}
		
		delim = "@";
		for(PolicyAtom a : orAtoms)
		{
			tokens = a.getSubPolicy().split(delim);
			for(String tok : tokens)
			{
				PolicyAtom b = new PolicyAtom(new String(tok), PropositionalOperator.and);
				allAtoms.add(b);
			}
			allAtoms.getLast().setNextOperator(PropositionalOperator.or);
		}
		
		//allAtoms = orAtoms;
		allAtoms.getLast().setNextOperator(PropositionalOperator.end);
		
		
		return allAtoms;
	}
	
	/*
	 * this is an entry point to the recursive atomic policy authorizer method
	 */
	private boolean atomAuthorize(Graph<Person,Relationship> socialNetwork, int accessor, int owner, String atomicPolicy)
	{
		String[] relationships1 = atomicPolicy.split("<|>|\\s+");

		LinkedList<String> rels = new LinkedList<String>();
		for (String s : relationships1)
		{
			if(s.length() > 0)
			{
				rels.add(s);
			}
		}
		String[] relationships = new String[rels.size()];
		for(int i=0; i< rels.size(); i++)
		{
			relationships[i] = rels.get(i);
		}
		
		return recursiveAtomAuthorize(socialNetwork, accessor, owner, relationships, 0);
	}
	
	/*
	 * this is the recursive method that authorizes an atomic policy by applying the semantic rules and querying the graph
	 */
	private boolean recursiveAtomAuthorize(Graph<Person,Relationship> socialNetwork, int v, int u, String[] relationships, int index)
	{
		boolean decision = false;
		if(index < relationships.length)
		{
			String relationship = relationships[index];
			
			//if the desired relationship between u and v is identity, then check for it
			if(relationship.equals("a"))
			{
//				System.out.printf("same? %d %d\n", u, v);
				if(v == u)
				{
					decision = true;
				}
				else
				{
					decision = false;
				}
			}
			
			//otherwise, query the graph for all vertices that match the specified relationship
			else
			{
				for(Edge<Relationship> e : socialNetwork.getEdges())
				{
//					System.out.printf("u=%d\n", u);
//					System.out.printf("%d %d\n", e.getSource(), e.getDestination());
					//if the relationship is between u and another vertex
					if(e.getSource() == u)
					{
						//...and the relationship type matches
						String identifier = e.getComponent().getIdentifier();
//						System.out.printf("edgerel: %s  targetrel: %s\n", e.getComponent().getIdentifier(), relationship);
						if(relationship.equals(identifier))
						{
//							System.out.printf("Dest %d\n", e.getDestination());
							//then call the algorithm recursively. once a matching path is found, return true
							int x = e.getDestination();
							if(this.recursiveAtomAuthorize(socialNetwork, v, x, relationships, index+1) == true)
							{
								return true;
							}
						}
					}
				}
				//if the set of matching edges was empty or no path was found, return false
				decision = false;
			}
		}
		return decision;
	}
}
