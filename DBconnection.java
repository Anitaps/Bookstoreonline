import java.sql.*;


//DBConnection class responsible for establishing a connection to the database.
public class DBconnection {
	public static void main (String[]args){DBconnection db = new DBconnection();}
	     public Connection connection =null;
	     public PreparedStatement pst = null;
	     public ResultSet Rs = null;
	     
	  // the url of the database   
	     private static final String URL = "jdbc:sqlite:book";
     //Constructor. Loads the driver to be used.
	     public DBconnection(){
	     try{
    	 connection=DriverManager.getConnection("jdbc:sqlite:book");
    	 //jdbc:sqlite:Books:sql
	    		System.out.println("Loading driver...");
				// here in the constructor we just load the driver
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver loaded...");
			
			} catch (ClassNotFoundException ex) {
				// handle errors for Class.forName here
				System.out.println("\n" + "Could not load driver..." + "\n");
				System.out.println(ex);
			}
	     catch(SQLException ex){
	    	 System.out.println(ex);
	     }

	     } //end of dbconnection
		public PreparedStatement pst(String query) {
			// TODO Auto-generated method stub
			return null;
		}
		
}
   
   