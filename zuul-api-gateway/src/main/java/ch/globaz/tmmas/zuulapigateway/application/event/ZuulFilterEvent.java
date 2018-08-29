package ch.globaz.tmmas.zuulapigateway.application.event;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ZuulFilterEvent {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("DD.MM.YYYY HH:mm:ss");

	private String servicePath;
	private String eventDate;
	private String host;


	public ZuulFilterEvent(String servicePath, String host) {
		this.servicePath = servicePath;
		this.host = host;
		this.eventDate = dateFormat.format(new Date());
	}
}
