import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

	ArgumentsDetails argumentsDetails;

	public Server(String[] args) {
		argumentsDetails = new ArgumentsDetails(args);
		
//		recieveText(9999);
		
		recieveFile(9999, "newFile.txt");
		
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendTheText("localhost", 9999, "xxxxxxx");
	
		
		

	}

	private void recieveFile(int port, String outputFileName) {
		ServerSocket serverSocket = null;
		DataInputStream in = null;
		Socket server = null;
		PrintWriter printWtiter = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			new CustomException("not able to occupy recieving port", true)
					.displayException();

		}
		
		while (true) {
			try {
			
				server = serverSocket.accept();
				in = new DataInputStream(server.getInputStream());
				
				 printWtiter = new PrintWriter(outputFileName);
				 printWtiter.append(in.readUTF());
				
				
				break;
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} finally {
				try {
					in.close();
					server.close();
					serverSocket.close();
					printWtiter.close(); 

				} catch (Exception ex) {

				}
			}
		}
		
	}

	public void listenOnPort(int portNumber) {

	}

	// as client sending file to the server
	public void sendEntireFileIntoServer(String hostName, int portNumber,
			String inputFileName, String outputFileName, char director) {
		File f = new File(inputFileName);

		if (f.exists()) {
			sendEntireFile(hostName, portNumber, f);
			recieveEntireFileFromServer(portNumber, outputFileName);
			exit();
		} else {
			new CustomException("File not exist", true).displayException();
		}

	}

	public void sendText(String serverName, int port, String textToSend) {

		sendTheText(serverName, port, textToSend);
		recieveText(port);
		exit();
	}
	
	
	public void sendWholeFile(String serverName, int port, File fileToSend ) {

		String textToSend = fileToText(fileToSend);
		if(textToSend !=null)
		{
			sendTheText(serverName, port, textToSend);
			recieveText(port);
			exit();
		}
	}

	

	private String fileToText(File fileToSend) {

		BufferedReader br = null;
		 String result = null;
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(fileToSend));
			
			while ((sCurrentLine = br.readLine()) != null) {
				result +=sCurrentLine+"\n";
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return result;
		}
	}

	private void sendTheText(String serverName, int port,
			String textToSend) {

		OutputStream outToServer = null;
		DataOutputStream out = null;
		Socket client = null;

		try {
			
			 client = new Socket(serverName, port);
			
			outToServer = client.getOutputStream();
			out = new DataOutputStream(outToServer);

			out.writeUTF(textToSend + client.getLocalSocketAddress());

		} catch (IOException e) {
			new CustomException("not able to recieve Text from server", true)
					.displayException();
		} finally {
			try {
				outToServer.close();
				out.close();
				client.close();
			} catch (Exception ex) {

			}
		}

	}

	private void recieveText(int port) {
		ServerSocket serverSocket = null;
		DataInputStream in = null;
		Socket server = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			new CustomException("not able to occupy recieving port", true)
					.displayException();

		}
		
		while (true) {
			try {
			
				server = serverSocket.accept();
				in = new DataInputStream(server.getInputStream());
				System.out.println(in.readUTF());
				System.out.println("Message has been successfully recieved");
				
				break;
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} finally {
				try {
					in.close();
					server.close();
					serverSocket.close();

				} catch (Exception ex) {

				}
			}
		}
	}

	private void recieveEntireFileFromServer(int portNumber,
			String outputFileName) {

	}

	private void sendEntireFile(String serverName, int port, File f) {
		DataOutputStream out = null;
		OutputStream outToServer = null;
		InputStream is = null;
		Socket client = null;

		try

		{
			System.out.println("Connecting to " + serverName + " on port "
					+ port);
			client = new Socket(serverName, port);
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());
			outToServer = client.getOutputStream();
			out = new DataOutputStream(outToServer);

			is = new FileInputStream(f);

			byte[] bytes = new byte[(int) f.length()];

			is.read(bytes);

			out.write(bytes);

			client.close();
		} catch (IOException e) {
			new CustomException("File not sent", true).displayException();

		} finally {
			try {
				out.close();
				outToServer.close();
				is.close();

			} catch (Exception ex) {

			}
		}

	}

	private void exit() {
		System.exit(0);

	}

	public static byte[] inputStreamToByteArray(InputStream is)
			throws IOException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();
	}
	
	
	
	public static void main(String [] args)
	{
		new Server(args);
	}

}