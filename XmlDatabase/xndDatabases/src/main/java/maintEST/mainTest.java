package maintEST;

import java.util.ArrayList;
import java.util.List;

import DAO.IDAO;
import DAO.IDAO.Resource;
import DAO.IDAOFactory;
import MODELS.*;

public class mainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IDAOFactory factory = new IDAOFactory();
		IDAO dao = factory.createIDAOManager();
		Country c = dao.getCountry("Albania");
		System.out.println(c);
		System.out.println();
		
		System.out.println("Number of countries: " + dao.Count(Resource.COUNTRIES));
		System.out.println("Number of mountains: " + dao.Count(Resource.MOUNTAINS));
		System.out.println("Number of lakes: " + dao.Count(Resource.LAKES));
		System.out.println("Number of rivers: " + dao.Count(Resource.RIVERS));
		System.out.println();
		
		String archipelagoName = "Outer Hebrides";
		ArrayList<Island> islandList = dao.GetIslands(archipelagoName);
		
		System.out.println(String.format("Island of the archipelago %s:", archipelagoName));
		for(Island island : islandList)System.out.println(island);
		System.out.println();
		
		if(dao.UpdateResourceName(Resource.COUNTRIES, "Spain", "spain")) System.out.println("Country updated!");
		else System.out.println("Country not upaded!");
		if(dao.UpdateResourceName(Resource.LAKES, "Graciosa", "graciosa")) System.out.println("Lake updated!");
		else System.out.println("Lake not upaded!");
		if(dao.UpdateResourceName(Resource.MOUNTAINS, "Elbrus", "elbrus")) System.out.println("Mountain updated!");
		else System.out.println("Mountain not upaded!");
		if(dao.UpdateResourceName(Resource.RIVERS, "Hekla", "hekla")) System.out.println("River updated!");
		else System.out.println("River not upaded!");
		System.out.println();
		
		try {
			dao.StoreCountries();
			System.out.println("Countries stored!");
		} catch (Exception e) {
			System.out.println("Countries NOT stored!");
			e.printStackTrace();
		}
	}
}
