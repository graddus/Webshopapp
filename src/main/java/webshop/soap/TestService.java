package webshop.soap;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import hello.HelloWorld;
import hello.HelloWorldService;

public class TestService {
	public int getUniekGetal(String name, int bedrag, int adresid) throws MalformedURLException {
		QName qName = new QName("http://Hello/", "HelloWorldService");
		URL url = new URL("http://localhost:9999/java-ws/hello");
		Service service = HelloWorldService.create(url, qName);
		HelloWorld port = (HelloWorld) service.getPort(HelloWorld.class);
		
		int returnMsg = port.getUniekGetal(adresid, name, bedrag);
		return returnMsg;
	}
}
