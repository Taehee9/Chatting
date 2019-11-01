package chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

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
