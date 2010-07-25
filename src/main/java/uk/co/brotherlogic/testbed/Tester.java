package uk.co.brotherlogic.testbed;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;

public class Tester extends TemplatePage{

	@Override
	public Class generates() {
		return TestObject.class;
	}

	@Override
	public String linkParams(Object o) {
		return Integer.toString(((TestObject)o).number);
	}

	@Override
	protected Map<String, Object> convertParams(List<String> paramList,
			Map<String, String> paramMap) {
		Map<String,Object> objMap = new TreeMap<String, Object>();
		objMap.put("test", new TestObject(paramList.get(0)));
		return objMap;
	}

}
