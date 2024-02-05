package DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import MODELS.*;

public class IDAOImpl implements IDAO {
	private final String driver = "org.exist.xmldb.DatabaseImpl";
	private Class cl;
	private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
	private Collection col;

	public IDAOImpl() {
		try {
			String colName="/db/apps/demo/data";
			cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);
			col = DatabaseManager.getCollection(URI + colName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Country getCountry(String name) {
		Country c = new Country();
		String sXQuery = "/mondial/country[name='"+name+"']/name/text() , "
		        + "/mondial/country[name='"+name+"']/government/text() , "
		        + "/mondial/country[name='"+name+"']/population/text() , "
		        + "/mondial/country[name='"+name+"']/indep_date/text()";
		
		try {
			XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
			xqs.setProperty("indent", "yes");
			CompiledExpression compiled = xqs.compile(sXQuery);
			ResourceSet result = xqs.execute(compiled);
			ResourceIterator i = result.getIterator();
			org.xmldb.api.base.Resource res = null;
			res = i.nextResource();
			c.setName(res.getContent().toString());
			res=i.nextResource();
			c.setGovernment(res.getContent().toString());
			res=i.nextResource();
			c.setPopulation(Long.parseLong(res.getContent().toString()));
			res=i.nextResource();
			c.setIndepDate(new SimpleDateFormat("yyyy-mm-dd").parse(res.getContent().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return c;
	}

	@Override
	public int Count(Resource resource) {
		String sXQuery = "";
		switch (resource) {
		case COUNTRIES:
			sXQuery = "count(/mondial/country)";
			break;
		case MOUNTAINS:
			sXQuery = "count(/mondial/mountain)";
			break;
		case LAKES:
			sXQuery = "count(/mondial/lake)";
			break;
		case RIVERS:
			sXQuery = "count(/mondial/river)";
			break;
		}
		
		int countryCount = 0;

	    try {
	        XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
	        xqs.setProperty("indent", "yes");

	        CompiledExpression compiled = xqs.compile(sXQuery);
	        ResourceSet result = xqs.execute(compiled);

	        ResourceIterator i = result.getIterator();
	        org.xmldb.api.base.Resource res = i.nextResource();
	        countryCount = Integer.parseInt(res.getContent().toString());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return countryCount;
	}

	@Override
	public ArrayList<Island> GetIslands(String archipelago) {
		ArrayList<Island> islands = new ArrayList<>();

	    try {
	        XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
	        xqs.setProperty("indent", "yes");

	        String sXQuery = String.format(
	                "for $island in /mondial/island[islands='%s'] " +
	                "return ($island/name/text(), data($island/@sea), $island/area/text(), $island/latitude/text(), $island/longitude/text())", archipelago);

	        ResourceSet result = xqs.query(sXQuery);

	        ResourceIterator i = result.getIterator();
	        
	        while (i.hasMoreResources()) {
	        	org.xmldb.api.base.Resource res = i.nextResource();
	        	Island island = new Island();
	            island.setName(res.getContent().toString());
	            res = i.nextResource();
	            island.setSea(res.getContent().toString());
	            res = i.nextResource();
	            island.setArea(Double.parseDouble(res.getContent().toString()));
	            res = i.nextResource();
	            island.setLongitude(Double.parseDouble(res.getContent().toString()));
	            res = i.nextResource();
	            island.setLatitude(Double.parseDouble(res.getContent().toString()));
	            islands.add(island);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return islands;
	}

	@Override
	public boolean UpdateResourceName(Resource resource, String oldName, String newName) {
		boolean changed = false;
		
		String sXQuery = "";
		switch (resource) {
		case COUNTRIES:
			sXQuery = String.format(
                    "update replace /mondial/country[name='%s']/name with <name>%s</name>",
                    oldName, newName);
			break;
		case MOUNTAINS:
			sXQuery = String.format(
                    "update replace /mondial/mountain[name='%s']/name with <name>%s</name>",
                    oldName, newName);
			break;
		case LAKES:
			sXQuery = String.format(
                    "update replace /mondial/lake[name='%s']/name with <name>%s</name>",
                    oldName, newName);
			break;
		case RIVERS:
			sXQuery = String.format(
                    "update replace /mondial/river[name='%s']/name with <name>%s</name>",
                    oldName, newName);
			break;
		}
		
		try {
	        XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
	        xqs.setProperty("indent", "yes");

	        CompiledExpression compiled = xqs.compile(sXQuery);
			ResourceSet result = xqs.execute(compiled);

	        changed = result.getSize() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return changed;
	}

	@Override
	public void StoreCountries() {
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		contentBuilder.append("<?xml-stylesheet href=\"countries.xsl\" type=\"text/xsl\"?>");
		
		try {
	        XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
	        xqs.setProperty("indent", "yes");

	        String sXQuery = "let $countries := doc(\"/db/apps/demo/data/mondial.xml\")/mondial/country\r\n"
	        		+ "return\r\n"
	        		+ "  <countries>\r\n"
	        		+ "  {\r\n"
	        		+ "    for $country in $countries\r\n"
	        		+ "    return\r\n"
	        		+ "      <country>\r\n"
	        		+ "        <name>{data($country/name)}</name>\r\n"
	        		+ "        <population>{data($country/population)}</population>\r\n"
	        		+ "        <indep_date>{data($country/indep_date)}</indep_date>\r\n"
	        		+ "        <government>{data($country/government)}</government>\r\n"
	        		+ "      </country>\r\n"
	        		+ "  }\r\n"
	        		+ "  </countries>";

	        ResourceSet result = xqs.query(sXQuery);

	        ResourceIterator i = result.getIterator();
	        
	        XMLResource xmlResource = (XMLResource) col.createResource("countries.xml", "XMLResource");
	        

	        while (i.hasMoreResources()) {
	            org.xmldb.api.base.Resource res = i.nextResource();
	            contentBuilder.append(res.getContent().toString());
	        }

	        xmlResource.setContent(contentBuilder.toString());
	        col.storeResource(xmlResource);
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
