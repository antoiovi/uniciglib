package it.iovino.utilita;

public class SystemLogger implements Mylogger {
int level;
	@Override
	public void appendMessage(int level, String message) {
		if(level>=this.level){
			System.out.println(message);
		}
	}
	@Override
	public void setLevel(int level) {
		this.level=level;
		
	}
	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}
