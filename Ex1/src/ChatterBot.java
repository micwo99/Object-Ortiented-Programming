import java.util.*;
;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {
	static final String REQUEST_PREFIX = "say ";
	static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
	static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";
	String name;
	Random rand = new Random();
	String[] repliesToIllegalRequest;
	String[] repliesToLegalRequests;

	/**
	 * Constructor
	 */
	ChatterBot(String name ,String[] repliesToLegalRequests,String[] repliesToIllegalRequest) {
		this.name= name;
		this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
		this.repliesToLegalRequests= new  String[repliesToLegalRequests.length];
		for(int i = 0 ; i < repliesToLegalRequests.length ; i++) {
			this.repliesToLegalRequests[i] = repliesToLegalRequests[i];
		}
		for(int i = 0 ; i < repliesToIllegalRequest.length ; i = i+1) {
			this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
		}
	}

	/**
	 * the function returns what the bot will respond depending on the statment that the user enters
	 * @param statement statement that the user enters
	 * @return the answer of the bot
	 */
	String replyTo(String statement) {
		if(statement.startsWith(REQUEST_PREFIX)) {
			//we don’t repeat the request prefix, so delete it from the reply
			return respondToLegalRequest(statement);
		}
		return  respondToIllegalRequest(statement);
	}

	/**
	 * the function returns the respond of the bot after the user enters a legal request
	 * @param statement statement that the user enters
	 */
	String respondToLegalRequest(String statement){
		String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
		return replacePlaceholderInARandomPattern(phrase,repliesToLegalRequests,REQUESTED_PHRASE_PLACEHOLDER);
	}

	/**
	 * the function returns the respond of the bot after the user enters a illegal request
	 * @param statement statement that the user enters
	 */
	String respondToIllegalRequest(String statement) {
		return replacePlaceholderInARandomPattern(statement,repliesToIllegalRequest,ILLEGAL_REQUEST_PLACEHOLDER);
	}

	/**
	 * Choose randomly a sentence from replies and replace  the parameter change with statement
	 * @return the answer of the bot
	 */
	String replacePlaceholderInARandomPattern(String statement,String[] replies, String  change ){
		int randomIndex = rand.nextInt(replies.length);
		String reply = replies[randomIndex];
		return reply.replaceAll(change, statement);

	}

	/**
	 * th function returns the name of the bot
	 */
	String getName(){
		return name;
	}
}
