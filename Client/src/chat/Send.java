/*
 * Send.java
 * 
 * Author : taehee Kwon
 * Date : 2019. 10. 31
 * Contact : dhstoalfh9509@gmail.com
 */
package chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

/**
 * 키보드로 입력받은 내용 보내기
 * @author Taehee Kwon, dhstoalfh9509@gmail.com, 2019
 * @since  2019. 11. 1.
 */
public class Send implements Runnable{
	DataOutputStream out;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public Send(DataOutputStream out){
		this.out = out;
	}
	
	public void run(){
		// 반복해서 사용자가 키보드로 입력한 내용을 서버로 전송
		while(true){
			try{
				String msg = br.readLine();
				out.writeUTF(msg);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

}
