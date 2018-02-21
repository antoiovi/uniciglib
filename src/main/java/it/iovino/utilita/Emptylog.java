package it.iovino.utilita;

public class Emptylog implements Mylogger {
static Emptylog emptylog=null;

private   Emptylog(){
	
}

public static Emptylog getInstance(){
	if(emptylog==null){
		emptylog=new Emptylog();
	}
	return emptylog;
}
	@Override
	public void appendMessage(int level, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLevel(int level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
