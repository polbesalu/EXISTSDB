package DAO;

import java.util.ArrayList;

import DAO.IDAO.Resource;
import MODELS.*;

public interface IDAO {
	
	public Country getCountry(String name);
	
	public enum Resource {COUNTRIES, MOUNTAINS, LAKES, RIVERS};
	
	public int Count(Resource resource);
	
	public ArrayList<Island> GetIslands(String archipielago);
	
	boolean UpdateResourceName(Resource resource, String oldName, String newName);
	
	public void StoreCountries();
}
