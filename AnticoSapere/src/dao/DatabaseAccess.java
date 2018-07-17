package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Post;

public class DatabaseAccess {
	
	private Connection connection;
	
	public DatabaseAccess () throws SQLException {
		
		 try {
		     Class.forName("org.h2.Driver" );
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		     return;
		 }
		 connection = DriverManager.getConnection("jdbc:h2:file:./DB/AnticoSapereDB","Netrill","Netrill1986");
		 creaUtenze();
	}
	void creaUtenze () {
		Statement st=null;
		boolean db_gia_presente=false;
		ArrayList <Post> lista = new ArrayList<>();
			try {
			st=connection.createStatement();
			String sql="CREATE TABLE PUBLIC.POST (ID BIGINT NOT NULL IDENTITY,TESTO VARCHAR(500000) NOT NULL,ID_AUTORE INTEGER NOT NULL,TITOLO VARCHAR(100) NOT NULL,DATA_CREAZIONE VARCHAR(100) NOT NULL,	CONSTRAINT SYS_PK_10096 PRIMARY KEY (ID))" ; 
			st.executeUpdate(sql);
			sql="CREATE TABLE PUBLIC.UTENZE (ID INTEGER NOT NULL IDENTITY,USERNAME VARCHAR(100),PASSWORD VARCHAR(100),CONSTRAINT SYS_PK_10092 PRIMARY KEY (ID)) ";
			st.executeUpdate(sql);
			sql=("INSERT INTO UTENZE (USERNAME,PASSWORD) VALUES ('Netrill','scachri86')");
			st.executeUpdate(sql);
			st.close();
		
		}
		catch(Exception e) {
			System.out.print(e.getMessage());
			try {
 
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}

		}
	}
	
	public ArrayList<Post> recuperaTuttiPost () {
		 
		PreparedStatement st=null;
		ResultSet rs = null;
		ArrayList <Post> lista = new ArrayList<>();
		try {
			st=connection.prepareStatement("SELECT * FROM POST,UTENZE where POST.ID_AUTORE = UTENZE.id ");

			rs =st.executeQuery();
			 
			
			while (rs.next()) {
				lista.add(new Post( "TESTO_NON_CARICATO", rs.getString("TITOLO"), rs.getString("USERNAME"), rs.getString("DATA_CREAZIONE"),rs.getLong("POST.ID")));
			}
			
			
			rs.close();
			st.close();
			return lista;
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			
			return null;
		}
	}
	 
	
	
	public long recuperaUtenza(String username, String password)  {
		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			if (password!=null) {
				st=connection.prepareStatement("SELECT * FROM PUBLIC.UTENZE WHERE UTENZE.USERNAME= ? and UTENZE.PASSWORD= ?");
				st.setString(1, username);
				st.setString(2, password);
			}
			else {
				st=connection.prepareStatement("SELECT * FROM PUBLIC.UTENZE WHERE UTENZE.USERNAME= ? ");
				st.setString(1, username);
			}
			rs =st.executeQuery();
			rs.next();
			Long id=rs.getLong("ID");
			rs.close();
			st.close();
			return id;
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			
			return -1;
		}
	}
	

	public void deleteAllPost () {
		
		PreparedStatement st=null;
		ResultSet rs = null;
		 
		try {
			st=connection.prepareStatement("DELETE * FROM PUBLIC.POST");
 
			st.close();
			 
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			
		 
		}
		
	}
	
	
	public ArrayList<Post> SearchByTitle(String title) {
		PreparedStatement st=null;
		ResultSet rs = null;
		ArrayList <Post> lista = new ArrayList<>();
		try {
			st=connection.prepareStatement("SELECT * FROM POST,UTENZE where LOWER(POST.TITOLO) LIKE LOWER('%"+title+"%') and POST.ID_AUTORE = UTENZE.id");
			//st.setString(1,title);
			rs =st.executeQuery();
		 
			
			while (rs.next()) {
				lista.add(new Post( "TESTO_NON_CARICATO", rs.getString("TITOLO"), rs.getString("USERNAME"), rs.getString("DATA_CREAZIONE"),rs.getLong("POST.ID")));
			}
			rs.close();
			st.close();
			return lista;
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			
			return null;
		}
		
	}
	
	
	public void close () throws SQLException {
		connection.close();
	}

	public boolean inserisciFileInDB(String testoFileCaricato, String titolo, String autore, String data) {
		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			Long id = 50L;
			id=recuperaUtenza(autore,null);
			if (id<0)
				return false;
			st=connection.prepareStatement("INSERT INTO PUBLIC.POST (TESTO,ID_AUTORE,TITOLO,DATA_CREAZIONE) VALUES (?,?,?,?)  ");
			st.setString(1, testoFileCaricato);
			st.setLong(2, id);
			st.setString(3, titolo);
			st.setString(4, data);
			
			st.executeUpdate();
			 
			st.close();
			
			return true;
			 
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			return false;

		}
		
	}

	public Post SearchById(Long id) {
		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			Post p = null;
			st=connection.prepareStatement("SELECT * FROM PUBLIC.POST WHERE POST.ID= ? ");
			st.setLong(1, id);
			rs =st.executeQuery();
			
			while (rs.next()) {
				
				p=new Post(rs.getString("TESTO"),rs.getString("TITOLO"),"",rs.getString("DATA_CREAZIONE"),id);
			}
			
			rs.close();
			st.close();
			return p;
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			
			return null;
		}
		
	}

	public void ElimnaById(Long id) {
		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			Post p = null;
			st=connection.prepareStatement("DELETE FROM PUBLIC.POST WHERE POST.ID= ? ");
			st.setLong(1, id);
			st.executeUpdate();
			 
			st.close();
			 
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			
			 
		}
		
	}

	public ArrayList<Post> SearchByText(String text) {
		PreparedStatement st=null;
		ResultSet rs = null;
		ArrayList <Post> lista = new ArrayList<>();
		try {
			st=connection.prepareStatement("SELECT * FROM POST,UTENZE where LOWER(POST.TESTO) LIKE LOWER('%"+text+"%') and POST.ID_AUTORE = UTENZE.id");
			//st.setString(1,title);
			rs =st.executeQuery();
		 
			
			while (rs.next()) {
				lista.add(new Post( "TESTO_NON_CARICATO", rs.getString("TITOLO"), rs.getString("USERNAME"), rs.getString("DATA_CREAZIONE"),rs.getLong("POST.ID")));
			}
			rs.close();
			st.close();
			return lista;
		}
		catch(SQLException e) {
			System.out.print(e.getMessage());
			try {
				if(rs!=null)
					rs.close();
				if(st!=null)
					st.close();
			} catch (SQLException e1) {
				 
				e1.printStackTrace();
			}
			
			return null;
		}
	}

	

	
	
}
