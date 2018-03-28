package myrestserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TassonomiaServiceController {

	private ArrayList<String> order_ = new ArrayList();
	private ArrayList<String> family = new ArrayList();
	private ArrayList<String> genus = new ArrayList();
	private ArrayList<String> specie = new ArrayList();

	@RequestMapping(value = {"/tassonomia"}, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String _get(
			@RequestParam(value = "name", required = false) String name,
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jret = new JSONObject();

		try {
			System.out.println("name=" + name);

			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/thedb");
			if (ds == null) {
				throw new Exception("Data source not found!");
			}

			if (name == null || name.length() == 0) {
				jret.put("order_", new JSONArray());
				jret.put("family", new JSONArray());
				jret.put("genus", new JSONArray());
				jret.put("nome_scientifico", new JSONArray());
				return jret.toString();
			}

			if (order_.isEmpty()) {
				try {
					String thequery = getQuery("order_");
					System.out.println(thequery);
					Connection c = ds.getConnection();
					c.setAutoCommit(false);
					Statement stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery(thequery);
					while (rs.next()) {
						order_.add(rs.getString("order_"));
					}
					rs.close();
					stmt.close();
					c.close();
					System.out.println(thequery + ", nrec = " + order_.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (family.isEmpty()) {
				try {
					String thequery = getQuery("family");
					System.out.println(thequery);
					Connection c = ds.getConnection();
					c.setAutoCommit(false);
					Statement stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery(thequery);
					while (rs.next()) {
						family.add(rs.getString("family"));
					}
					rs.close();
					stmt.close();
					c.close();
					System.out.println(thequery + ", nrec = " + family.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (genus.isEmpty()) {
				try {
					String thequery = getQuery("genus");
					System.out.println(thequery);
					Connection c = ds.getConnection();
					c.setAutoCommit(false);
					Statement stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery(thequery);
					while (rs.next()) {
						genus.add(rs.getString("genus"));
					}
					rs.close();
					stmt.close();
					c.close();
					System.out.println(thequery + ", nrec = " + genus.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (specie.isEmpty()) {
				try {
					String thequery = getQuery("nome_scientifico");
					System.out.println(thequery);
					Connection c = ds.getConnection();
					c.setAutoCommit(false);
					Statement stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery(thequery);
					while (rs.next()) {
						specie.add(rs.getString("nome_scientifico"));
					}
					rs.close();
					stmt.close();
					c.close();
					System.out.println(thequery + ", nrec = " + specie.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			String[] order_arr = new String[order_.size()];
			String[] familyarr = new String[family.size()];
			String[] genusarr = new String[genus.size()];
			String[] speciesarr = new String[specie.size()];

			order_arr = order_.toArray(order_arr);
			familyarr = family.toArray(familyarr);
			genusarr = genus.toArray(genusarr);
			speciesarr = specie.toArray(speciesarr);

			jret.put("order_", getjarray(order_arr, name));
			jret.put("family", getjarray(familyarr, name));
			jret.put("genus", getjarray(genusarr, name));
			jret.put("nome_scientifico", getjarray(speciesarr, name));
			System.out.println(jret.toString(3));
			return jret.toString();

		} catch (Exception e) {
			e.printStackTrace();
			jret.put("order_", new JSONArray());
			jret.put("family", new JSONArray());
			jret.put("genus", new JSONArray());
			jret.put("nome_scientifico", new JSONArray());
			return jret.toString();
		}
	}

	private JSONArray getjarray(String _array[], String name) {
		HashSet<String> myuniquedata = new HashSet();
		JSONArray jret = new JSONArray();
		int nrec = 1;
		for (String rec : _array) {
			if (rec.toLowerCase().contains(name.toLowerCase())) {
				if (myuniquedata.add(rec)) {
					jret.put(rec);
					if (nrec++ >= 10) {
						return jret;
					}
				}
			}
		}
		return jret;
	}

	private String getQuery(String field) {
		String thequery = "select " + field + ", sum(thecount) "
				+ "from ( "
				+ "select " + field + ", count(*) as thecount "
				+ "from osservazioni_point4 "
				+ "where " + field + " is not null "
				+ "group by " + field + " "
				+ "union select " + field + ", count(*) as thecount "
				+ "from osservazioni_polig4 "
				+ "where " + field + " is not null "
				+ "group by " + field + " "
				+ "union select " + field + ", count(*) as thecount "
				+ "from distribuzioni_geom4 "
				+ "where " + field + " is not null "
				+ "group by " + field + " "
				+ ") xxx group by " + field + " order by 2 desc";
		return thequery;
	}
}
