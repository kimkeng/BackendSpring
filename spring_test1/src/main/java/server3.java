//Thread 활용
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class server3 {

	public static void main(String[] args) {
		server3 sv = new server3();
		sv.start();
	}
	//Socket을 open하는 역활 (접속환경)
	public void start() {
		ServerSocket serverSocket = null;		
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(8000);
			System.out.println("[채팅 서버 오픈]");
			while(true) {
				socket = serverSocket.accept();
				//client 접속마다 새로운 Thread를 생성
				chatroom chatroom = new chatroom(socket);
				chatroom.start();
			}
		}catch(Exception e) {
			System.out.println("Thread 오류 발생!!");
		}finally {	//Socket error catch 
			if(serverSocket != null) {
				try {
					serverSocket.close();
					System.out.println("server 강제종료");
				}catch(Exception e) {
					System.out.println("server socket Real Error");
					System.exit(0);
				}
			}
		}
	}
}

//접속자 현황을 setting해서 Thread로 관리


class chatroom extends Thread{
	InputStream is = null;
	OutputStream os = null;
	Socket socket = null;
	BufferedReader in = null;
	PrintWriter out = null;
	//server 메모리에 사용자 list를 저장하는 공간
	static List<PrintWriter> list = new ArrayList<PrintWriter>();
	
	//즉시실행
	public chatroom(Socket socket) {
		this.socket = socket;
		try {
			this.out = new PrintWriter(this.socket.getOutputStream());
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.list.add(out);
		}catch(Exception e) {
			System.out.println("Socket 통신 오류!!");
		}
	}
	
	//Thread 실행
	@Override
	public void run() {
		
		super.run();
	}
}