/*
 * User.java
 * 
 * Author : taehee Kwon
 * Date : 2019. 10. 31
 * Contact : dhstoalfh9509@gmail.com
 */
package chat;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 채팅시 사용자와 관련된 함수들 모임
 * @author Taehee Kwon, dhstoalfh9509@gmail.com, 2019
 * @since  2019. 10. 31.
 */
public class User {
	HashMap<String, DataOutputStream> clientMap = new HashMap<String, DataOutputStream>();
	
	// synchronized = 멀티 thread로 동기화 되는 것을 막기 위함
	public synchronized void AddClient(String name, Socket socket){
		try {
			sendMsg(name + "님이 입장하셨습니다.", "Server");
			clientMap.put(name, new DataOutputStream(socket.getOutputStream()));
			System.out.println("채팅 참여 인원 : " + clientMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized void RemoveClient(String name){
		try {
			clientMap.remove(name);
			sendMsg(name + "님이 퇴장하셨습니다.", "Server");
			System.out.println("채팅 참여 인원 : " + clientMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized void sendMsg(String msg, String name) throws Exception{
		Iterator iter = clientMap.keySet().iterator();
		while(iter.hasNext()){
			String clientName = (String)iter.next();
			clientMap.get(clientName).writeUTF(name + " : " + msg);
		}
	}
}
