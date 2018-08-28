package ch.globaz.tmmas.zuulapigateway.application.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ZuulFilterEvent {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("DD.MM.YYYY HH:mm:ss");

	private String key;
	private String eventDate;
	private String host;


	public ZuulFilterEvent(String key, String host) {
		this.key = key;
		this.host = host;
		this.eventDate = dateFormat.format(new Date());
	}
}
