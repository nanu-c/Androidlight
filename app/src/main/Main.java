package main;
import java.net.SocketException;

import artnet4j.ArtNet;
public class Main {
	public static void main(){
		ArtNet artnet = new ArtNet();
        try {
            artnet.start();
            artnet.setBroadCastAddress("192.168.0.20");
        }
        catch (Exception e){
        	
        }
		
	}
}
