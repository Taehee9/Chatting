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
 * 		- 추가, 나가기, 메시지 보내기
 * @author Taehee Kwon, dhstoalfh9509@gmail.com, 2019
 * @since  2019. 10. 31.
 */
public class User {
	HashMap<String, DataOutputStream> clientMap = new HashMap<String, DataOutputStream>();
	
	/**
	 * 사용자 추가
	 * 2019. 11. 1., dhstoalfh9509@gmail.com
	 * @param name 사용자
	 * @param socket 소켓
	 */
	public synchronized void AddClient(String name, Socket socket){
		// synchronized = 멀티 thread로 동기화 되는 것을 막기 위함
		try {
			sendMsg(name + "님이 입장하셨습니다.", "Server");
			clientMap.put(name, new DataOutputStream(socket.getOutputStream()));
			System.out.println("채팅 참여 인원 : " + clientMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 사용자 퇴장
	 * 2019. 11. 1., dhstoalfh9509@gmail.com
	 * @param name 사용자
	 */
	public synchronized void RemoveClient(String name){
		try {
			clientMap.remove(name);
			sendMsg(name + "님이 퇴장하셨습니다.", "Server");
			System.out.println("채팅 참여 인원 : " + clientMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 사용자가 입력한 메시지 보내기
	 * 2019. 11. 1., dhstoalfh9509@gmail.com
	 * @param msg 입력 할 메시지
	 * @param name 사용자
	 */
	public synchronized void sendMsg(String msg, String name) throws Exception{
		// clientMap의 key를 이용해 iterator 객체 반환
		Iterator<String> iter = clientMap.keySet().iterator();
		// hasNext() = true : 다음 값이 있을 때
		//			   false : 다음 값이 없을 때
		while(iter.hasNext()){
			// next() = iterator의 다음 요소를 반환
			String clientName = (String)iter.next();
			// wirteUTF = string 문자열을 출력
			clientMap.get(clientName).writeUTF(name + " : " + msg);
		}
	}
}
