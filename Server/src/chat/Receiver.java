/*
 * Receiver.java
 * 
 * Author : taehee Kwon
 * Date : 2019. 10. 31
 * Contact : dhstoalfh9509@gmail.com
 */
package chat;

import java.io.DataInputStream;
import java.net.Socket;
import chat.User;

/**
 * 채팅방 내에서 메시지 받으면 처리
 * @author Taehee Kwon, dhstoalfh9509@gmail.com, 2019
 * @since  2019. 10. 31.
 */
public class Receiver implements Runnable{
	Socket socket;
	// DataInputStream = 기본 데이터로 입력받기 위한 클래스
	DataInputStream in;
	String name;
	User user = new User();
	
	/**
	 * 사용자 추가
	 * 2019. 11. 1., dhstoalfh9509@gmail.com
	 * @param user 사용자
	 * @param socket 소켓
	 * @throws Exception readUTF로부터의 IOException
	 */
	public Receiver(User user, Socket socket) throws Exception{
		this.user = user;
		this.socket = socket;
		
		// 접속한 client로부터 데이터를 읽어들이기 위해 dataInputStream 생성
		in = new DataInputStream(socket.getInputStream());
		// 최초 사용자로부터 닉네임 읽어들임
		this.name = in.readUTF();
		// 사용자 추가
		user.AddClient(name, socket);
	}
	
	public void run(){
		try{
			// 계속해서 사용자가 메시지 쓰는 것을 받아 출력함
			while(true){
				String msg = in.readUTF();
				user.sendMsg(msg, name);
			}
		} catch(Exception e){
			user.RemoveClient(this.name);
			e.printStackTrace();
		}
	}
}
