
public class PolicyAtom {

	private String subPolicy = new String();
	PropositionalOperator nextOperator = PropositionalOperator.end;
	boolean authorizationResult = false;
	
	public PolicyAtom(String subPolicy, PropositionalOperator nextOperator) {
		super();
		this.subPolicy = subPolicy;
		this.nextOperator = nextOperator;
	}

	public String getSubPolicy() {
		return subPolicy;
	}

	public void setSubPolicy(String subPolicy) {
		this.subPolicy = subPolicy;
	}

	public PropositionalOperator getNextOperator() {
		return nextOperator;
	}

	public void setNextOperator(PropositionalOperator nextOperator) {
		this.nextOperator = nextOperator;
	}

	public boolean getAuthorizationResult() {
		return authorizationResult;
	}

	public void setAuthorizationResult(boolean authorizationResult) {
		this.authorizationResult = authorizationResult;
	}
	
	
	
	
	
}
