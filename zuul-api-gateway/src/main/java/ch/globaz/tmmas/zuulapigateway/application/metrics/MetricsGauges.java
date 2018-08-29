package ch.globaz.tmmas.zuulapigateway.application.metrics;

public enum MetricsGauges {

	ZUUL_REQUEST_FILTER("gateway.zuul.request"),
	SERVER_REQUEST_FILTER("gateway.server.request"),
	RIBBON_API_ROUTING("gateway.ribbon.routing");

	private final String gaugeName;

	MetricsGauges(String gaugeName){
		this.gaugeName = gaugeName;
	}

	public String gaugeName(){
		return gaugeName;
	}
}
