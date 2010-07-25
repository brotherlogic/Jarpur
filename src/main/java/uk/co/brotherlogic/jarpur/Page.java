package uk.co.brotherlogic.jarpur;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Page {

	public static String base = "";

	public static void setBase(String in) {
		base = in;
	}

	public abstract String generate(List<String> paramList,
			Map<String, String> paramMap) throws IOException;

	public abstract Class generates();

	public abstract String linkParams(Object o);

}
