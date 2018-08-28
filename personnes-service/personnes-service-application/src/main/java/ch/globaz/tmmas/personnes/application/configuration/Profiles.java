package ch.globaz.tmmas.personnes.application.configuration;

public enum Profiles {

	DEV("dev"),
	PRODUCTION("prod");

	private String profileValue;

	Profiles(String profileValue){
		this.profileValue = profileValue;
	}

	public String value() {
		return this.profileValue;
	}
}
