package multiclients;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String args[]) {
		String host = "127.0.0.1";
		int port = 8081;
		new Client(host, port);
	}

	public Client(String host, int port) {
		try {
			String serverHostname = new String("127.0.0.1");

			System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");

			Socket echoSocket = null;
			DataOutputStream out = null;
			DataInputStream in = null;

			try {
				echoSocket = new Socket(serverHostname, 8081);
				out = new DataOutputStream(echoSocket.getOutputStream());
				in = new DataInputStream(new BufferedInputStream(echoSocket.getInputStream()));
			} catch (UnknownHostException e) {
				System.err.println("Unknown host: " + serverHostname);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Unable to get streams from server");
				System.exit(1);
			}


			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.print("client: ");
				String userInput = stdIn.readLine();

				if ("q".equals(userInput)) {
					break;
				}
				out.writeUTF(userInput);
				System.out.println("server: " + in.readLine());
			}

			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}