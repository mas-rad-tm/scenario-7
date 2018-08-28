package ch.globaz.tmmas.zuulapigateway.application.event;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ServerFilterEvent {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("DD.MM.YYYY HH:mm:ss");

	private String key;
	private String eventDate;
	private String host;



	public ServerFilterEvent(String key, String host) {
		this.key = key;
		this.host = host;
		this.eventDate = dateFormat.format(new Date());
	}
}
