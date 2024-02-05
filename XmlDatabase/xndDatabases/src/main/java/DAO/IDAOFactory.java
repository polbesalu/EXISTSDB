package DAO;


public class IDAOFactory {
	public IDAO createIDAOManager() {
		return new IDAOImpl();
	}
}
