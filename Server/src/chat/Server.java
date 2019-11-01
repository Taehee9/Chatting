/*
 * Server.java
 * 
 * Author : taehee Kwon
 * Date : 2019. 10. 31
 * Contact : dhstoalfh9509@gmail.com
 */
package chat;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Taehee Kwon, dhstoalfh9509@gmail.com, 2019
 * @since  2019. 10. 31.
 */
public class Server {
	public static void main(String...args){
		// 사용할 port 번호
		final int SERVER_PORT = 5000;
		
		// Client와 통신하기 위한 Socket
		Socket socket = null;
		// 채팅방에 접속해 있는 client 관리 객체
		User user = new User();
		// client 접속을 받기 위한 serverSocket
		ServerSocket server_socket = null;
		
		int count = 0;
		Thread thread[] = new Thread[10];
		
		try {
			server_socket = new ServerSocket();
			
			// 소켓을 호스트의 포트와 바인딩
			String localAddress = InetAddress.getLocalHost().getHostAddress();
			server_socket.bind(new InetSocketAddress(localAddress, SERVER_PORT));
			System.out.println("[server] start!! \naddress : " + localAddress + " , port : " + SERVER_PORT);
			
			// Server의 메인쓰레드는 계속해서 사용자의 접속을 받음
			while(true){
				// client로부터 연결 요청 올 때까지 접속 대기
				// 연결 요청 오기 전에 서버는 BLOCK 상태, 연결이 되면 통신을 위한 Socket 객체 반환
				socket = server_socket.accept();
				
				thread[count] = new Thread(new Receiver(user, socket));
				thread[count].start();
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
