package com.example.myinterface;

import java.net.Socket;

public interface MysocketInterface {
	public void onsuccess(Socket socket);

	public void onfailued(Socket socket);
}
