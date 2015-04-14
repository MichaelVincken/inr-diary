package be.webfactor.inrdiary.domain;

public enum DoseType {

	D000(0f, "0"), D025(.25f, "¼"), D050(.5f, "½"), D075(.75f, "¾"),
	D100(1f, "1"), D125(1.25f, "1¼"), D150(1.5f, "1½"), D175(1.75f, "1¾"),
	D200(2f, "2"), D225(2.25f, "2¼"), D250(2.5f, "2½"), D275(2.75f, "2¾"),
	D300(3f, "3"), D325(3.25f, "3¼"), D350(3.5f, "3½");

	public static final DoseType DEFAULT = D100;
	public static final String[] DISPLAY_VALUES;

	static {
		DISPLAY_VALUES = new String[values().length];
		for (int i = 0; i < DISPLAY_VALUES.length; i++) {
			DISPLAY_VALUES[i] = values()[i].label;
		}
	}

	private String label;
	private float value;

	private DoseType(float value, String label) {
		this.value = value;
		this.label = label;
	}

}
