package uk.co.brotherlogic.testbed;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;

public class Default extends TemplatePage {

	@Override
	public Class generates() {
		return null;
	}

	@Override
	public String linkParams(Object o) {
		return null;
	}

	@Override
	protected Map<String, Object> convertParams(List<String> paramList,
			Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		TestObject obj = new TestObject("1234");
		Map<String, Object> mapping = new TreeMap<String, Object>();
		mapping.put("testerobj", obj);
		return mapping;
	}
}
