package ch.globaz.tmmas.zuulapigateway.application.event;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class PrometheusCallEvent {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("DD.MM.YYYY HH:mm:ss");

	private String eventDate;


	public PrometheusCallEvent() {
		this.eventDate = dateFormat.format(new Date());
	}
}
