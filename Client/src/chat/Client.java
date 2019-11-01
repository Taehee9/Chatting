package chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String...args){
		Socket socket = null;
		DataInputStream in = null;
		BufferedReader br = null;
		
		DataOutputStream out = null;
		
		try {
			// 서버로 접속
			socket = new Socket("10.10.10.163", 5678);
			
			in = new DataInputStream(socket.getInputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new DataOutputStream(socket.getOutputStream());
			
			// 채팅방에서 사용 할 닉네임 입력
			System.out.print("닉네임을 입력해주세요 : ");
			String data = br.readLine();
			
			// 서버로 닉네임 전송
			out.writeUTF(data);
			
			// 사용자가 채팅 내용을 입력 및 서버로 전송하기 위한 쓰레드 생성 및 시작
			Thread th = new Thread(new Send(out));
			th.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			// 클라이언트의 메인 쓰레드는 서버로부터 데이터 읽어들이는 것만 반복
			while(true){
				String str = in.readUTF();
				System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
