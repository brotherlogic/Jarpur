package uk.co.brotherlogic.jarpur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Front of house deals with request and routes them to the relevant controller
 * 
 * @author sat
 * 
 */
public class FrontOfHouse extends HttpServlet {

	public FrontOfHouse() {
	}

	public static void main(String[] args) {
		FrontOfHouse foh = new FrontOfHouse();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		// Prepare the properties
		JarpurProperties.set(this.getServletContext());
		LinkTable.getLinkTable(this.getServletContext().getRealPath("/")
				+ "/WEB-INF/");
		Page.setBase(this.getServletContext().getRealPath("/") + "/WEB-INF/");

		// Get the path and drop off the leading / and add a trailing one if not
		// already there
		String path = req.getPathInfo().substring(1);
		if (!path.endsWith("/"))
			path = path + "/";

		String page = "";
		if (path.contains("resources")) {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					this.getServletContext().getRealPath("/WEB-INF/") + "/"
							+ path.substring(path.indexOf("resources")))));
			for (String line = reader.readLine(); line != null; line = reader
					.readLine())
				page += line;
			reader.close();
		} else {
			// Start searching for the appropriate class
			List<String> params = new LinkedList<String>();
			page = search(path, params, convertParams(req.getParameterMap()));
		}
		// Write out the result
		OutputStream os = res.getOutputStream();
		PrintStream ps = new PrintStream(os);
		ps.print(page);
		os.close();
	}

	private String capitalize(String in) {
		return Character.toUpperCase(in.charAt(0)) + in.substring(1);
	}

	private Map<String, String> convertParams(Map pMap) {
		Map<String, String> retMap = new TreeMap<String, String>();
		for (Object eSet : pMap.entrySet()) {
			Entry entr = (Entry) eSet;
			retMap.put((String) entr.getKey(), ((String[]) entr.getValue())[0]);
		}

		System.err.println("RETMAP: " + retMap);
		return retMap;
	}

	private String search(String path, List<String> params,
			Map<String, String> paramMap) {
		String className = JarpurProperties.get("base") + "."
				+ path.replace("/", ".");
		while (className.endsWith("."))
			className = className.substring(0, className.length() - 1);

		// Build on Default
		String defaultClass = className.trim() + ".Default";
		String res = build(defaultClass, params, paramMap);
		if (res != null)
			return res;

		String[] pathElems = path.split("/");
		className = JarpurProperties.get("base")
				+ "."
				+ path.substring(
						0,
						path.length()
								- pathElems[pathElems.length - 1].length() - 1)
						.replace("/", ".");
		String nClass = className.trim()
				+ capitalize(pathElems[pathElems.length - 1]);
		res = build(nClass, params, paramMap);
		if (res != null)
			return res;

		if (pathElems.length > 0) {
			params.add(pathElems[pathElems.length - 1]);
			return search(path.substring(0, path.length()
					- pathElems[pathElems.length - 1].length() - 1), params,
					paramMap);
		}

		// Try down a bit
		return "";
	}

	private String build(String className, List<String> params,
			Map<String, String> paramMap) {
		try {
			System.err.println("BUILDING: " + className);
			Class cls = Class.forName(className);
			Page pg = (Page) cls.getConstructor(new Class[0]).newInstance(
					new Object[0]);
			System.err.println("BUILT");
			return pg.generate(params, paramMap);
		} catch (Exception e) {
			// Pass
			e.printStackTrace();
		}
		return null;
	}
}