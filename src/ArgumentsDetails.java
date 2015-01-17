
public class ArgumentsDetails {
	
	public final static char ENTIRE_FILE = 'f';
	public final static char LINE_BY_LINE = 'r';
	public final static char INPUT_TEXT = 't';
	public final static int CLIENT = 1;
	public final static int SERVER =2;
	
	private String loggingFileName;
	private String inputFileName;
	private String inputTextValue;
	private String outputFileName;
	private char mode;
	private boolean client;
	private boolean server;
	private int portNumber;
	private String hostName;
	

	
	
	public ArgumentsDetails(String [] args)
	{
		
		
	}
	
	public ArgumentsDetails(){}

	
	

}
