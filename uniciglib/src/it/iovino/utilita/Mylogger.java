package it.iovino.utilita;

public interface Mylogger {
public final int OFF=0;
public final int SUPERFINE=1;
 public final int FINEST=3;
public final int FINE=4;
public final int INFO=5;
public final int MESSAGE=6;
public final int WARNING=7;
public final int GRAVE=8;

	public void appendMessage(int level,String message);
	public void setLevel(int level);
	public void clean();
}
