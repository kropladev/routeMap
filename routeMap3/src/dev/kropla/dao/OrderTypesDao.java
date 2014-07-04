package dev.kropla.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import dev.kropla.dto.DicOrderTypes;
import dev.kropla.exceptions.NotFoundException;
//import static dev.kropla.tools.Configs.log;
//import static dev.kropla.tools.Configs.logDev;

public class OrderTypesDao{
	public static  Logger log= (Logger) LoggerFactory.getLogger("DAO");
		
	    /**
	     * createValueObject-method. This method is used when the Dao class needs
	     * to create new value object instance. The reason why this method exists
	     * is that sometimes the programmer may want to extend also the valueObject
	     * and then this method can be overrided to return extended valueObject.
	     * NOTE: If you extend the valueObject class, make sure to override the
	     * clone() method in it!
	     */
	    public DicOrderTypes createValueObject() {
	          return new DicOrderTypes();
	    }
	 /**
	     * getObject-method. This will create and load valueObject contents from database 
	     * using given Primary-Key as identifier. This method is just a convenience method 
	     * for the real load-method which accepts the valueObject as a parameter. Returned
	     * valueObject will be created using the createValueObject() method.
	     */
	    public DicOrderTypes getObject(Connection conn, int rtId) throws NotFoundException, SQLException {
	    	
	         DicOrderTypes valueObject = createValueObject();
	          valueObject.setRtId(rtId);
	          load(conn, valueObject);
	          return valueObject;
	    }


	    /**
	     * load-method. This will load valueObject contents from database using
	     * Primary-Key as identifier. Upper layer should use this so that valueObject
	     * instance is created and only primary-key should be specified. Then call
	     * this method to complete other persistent information. This method will
	     * overwrite all other fields except primary-key and possible runtime variables.
	     * If load can not find matching row, NotFoundException will be thrown.
	     *
	     * @param conn         This method requires working database connection.
	     * @param valueObject  This parameter contains the class instance to be loaded.
	     *                     Primary-key field must be set for this to work properly.
	     */
	    public void load(Connection conn,DicOrderTypes valueObject) throws NotFoundException, SQLException {

	          String sql = "SELECT * FROM rm_dic_order_types WHERE (rt_id = ? ) "; 
	          java.sql.PreparedStatement stmt = null;

	          try {
	               stmt = conn.prepareStatement(sql);
	               stmt.setInt(1, valueObject.getRtId()); 

	               singleQuery(conn, stmt, valueObject);
	          }catch(Exception e){
	        	  log.error(e.getMessage());
	          } finally {
	              if (stmt != null)
	                  stmt.close();
	          }
	    }


	    /**
	     * LoadAll-method. This will read all contents from database table and
	     * build a List containing valueObjects. Please note, that this method
	     * will consume huge amounts of resources if table has lot's of rows. 
	     * This should only be used when target tables have only small amounts
	     * of data.
	     *
	     * @param conn         This method requires working database connection.
	     */
	    public List<DicOrderTypes> loadAll(Connection conn) {

	          String sql = "SELECT * FROM rm_dic_order_types ORDER BY rt_id ASC ";
	          List<DicOrderTypes> searchResults = null;
			try {
				searchResults = listQuery(conn, conn.prepareStatement(sql));
			} catch (SQLException e) {
				
				log.error(e.getMessage());
			}

	          return searchResults;
	    } 
	    

	    public Map<String,String> loadAllSymbols(Connection conn) {

	          String sql = "SELECT rt_id,rt_name FROM rm_dic_order_types ORDER BY rt_name ASC ";
	         // List<String> searchResults = null;
	          Map<String,String> searchResults = null;
			try {
				searchResults = listQuerySymbols(conn, conn.prepareStatement(sql));
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
			  log.debug("SEARCH result:"+searchResults.toString());
	          return searchResults;
	    }

	    /**
	     * create-method. This will create new row in database according to supplied
	     * valueObject contents. Make sure that values for all NOT NULL columns are
	     * correctly specified. Also, if this table does not use automatic surrogate-keys
	     * the primary-key must be specified. After INSERT command this method will 
	     * read the generated primary-key back to valueObject if automatic surrogate-keys
	     * were used. 
	     *
	     * @param conn         This method requires working database connection.
	     * @param valueObject  This parameter contains the class instance to be created.
	     *                     If automatic surrogate-keys are not used the Primary-key 
	     *                     field must be set for this to work properly.
	     */
	    public synchronized void create(Connection conn,DicOrderTypes valueObject) throws SQLException {

	          String sql = "";
	          PreparedStatement stmt = null;
	     //     ResultSet result = null;

	          try {
	               sql = "INSERT INTO rm_dic_order_types ( rt_id, rt_name, rt_name2, "
	               + "rt_symbol) VALUES (?, ?, ?, ?) ";
	               stmt = conn.prepareStatement(sql);

	               stmt.setInt(1, valueObject.getRtId()); 
	               stmt.setString(2, valueObject.getRtName()); 
	               stmt.setString(3, valueObject.getRtName2()); 
	               stmt.setString(4, valueObject.getRtSymbol()); 

	               int rowcount = databaseUpdate(conn, stmt);
	               if (rowcount != 1) {
	                    //log.debug("PrimaryKey Error when updating DB!");
	                    throw new SQLException("PrimaryKey Error when updating DB!");
	               }
	          }catch(Exception e){
	        	  log.error(e.getMessage());
	          } finally {
	              if (stmt != null)
	                  stmt.close();
	          }


	    }


	    /**
	     * save-method. This method will save the current state of valueObject to database.
	     * Save can not be used to create new instances in database, so upper layer must
	     * make sure that the primary-key is correctly specified. Primary-key will indicate
	     * which instance is going to be updated in database. If save can not find matching 
	     * row, NotFoundException will be thrown.
	     *
	     * @param conn         This method requires working database connection.
	     * @param valueObject  This parameter contains the class instance to be saved.
	     *                     Primary-key field must be set for this to work properly.
	     */
	    public void save(Connection conn,DicOrderTypes valueObject) 
	          throws NotFoundException, SQLException {

	          String sql = "UPDATE rm_dic_order_types SET rt_name = ?, rt_name2 = ?, rt_symbol = ? WHERE (rt_id = ? ) ";
	          PreparedStatement stmt = null;

	          try {
	              stmt = conn.prepareStatement(sql);
	              stmt.setString(1, valueObject.getRtName()); 
	              stmt.setString(2, valueObject.getRtName2()); 
	              stmt.setString(3, valueObject.getRtSymbol()); 

	              stmt.setInt(4, valueObject.getRtId()); 

	              int rowcount = databaseUpdate(conn, stmt);
	              if (rowcount == 0) {
	                   //log.debug("Object could not be saved! (PrimaryKey not found)");
	                   throw new NotFoundException("Object could not be saved! (PrimaryKey not found)");
	              }
	              if (rowcount > 1) {
	                   //log.debug("PrimaryKey Error when updating DB! (Many objects were affected!)");
	                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were affected!)");
	              }
	          }catch(Exception e){
	        	  log.error(e.getMessage());
	          } finally {
	              if (stmt != null)
	                  stmt.close();
	          }
	    }


	    /**
	     * delete-method. This method will remove the information from database as identified by
	     * by primary-key in supplied valueObject. Once valueObject has been deleted it can not 
	     * be restored by calling save. Restoring can only be done using create method but if 
	     * database is using automatic surrogate-keys, the resulting object will have different 
	     * primary-key than what it was in the deleted object. If delete can not find matching row,
	     * NotFoundException will be thrown.
	     *
	     * @param conn         This method requires working database connection.
	     * @param valueObject  This parameter contains the class instance to be deleted.
	     *                     Primary-key field must be set for this to work properly.
	     */
	    public void delete(Connection conn,DicOrderTypes valueObject) 
	          throws NotFoundException, SQLException {

	          String sql = "DELETE FROM rm_dic_order_types WHERE (rt_id = ? ) ";
	          PreparedStatement stmt = null;

	          try {
	              stmt = conn.prepareStatement(sql);
	              stmt.setInt(1, valueObject.getRtId()); 

	              int rowcount = databaseUpdate(conn, stmt);
	              if (rowcount == 0) {
	                   //log.debug("Object could not be deleted (PrimaryKey not found)");
	                   throw new NotFoundException("Object could not be deleted! (PrimaryKey not found)");
	              }
	              if (rowcount > 1) {
	                   //log.debug("PrimaryKey Error when updating DB! (Many objects were deleted!)");
	                   throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
	              }
	          } catch(Exception e){
		        	  log.error(e.getMessage());
	          } finally {
	              if (stmt != null)
	                  stmt.close();
	          }
	    }


	    /**
	     * deleteAll-method. This method will remove all information from the table that matches
	     * this Dao and ValueObject couple. This should be the most efficient way to clear table.
	     * Once deleteAll has been called, no valueObject that has been created before can be 
	     * restored by calling save. Restoring can only be done using create method but if database 
	     * is using automatic surrogate-keys, the resulting object will have different primary-key 
	     * than what it was in the deleted object. (Note, the implementation of this method should
	     * be different with different DB backends.)
	     *
	     * @param conn         This method requires working database connection.
	     */
	    public void deleteAll(Connection conn) throws SQLException {

	          String sql = "DELETE FROM rm_dic_order_types";
	          PreparedStatement stmt = null;

	          try {
	              stmt = conn.prepareStatement(sql);
	            //  int rowcount = databaseUpdate(conn, stmt);
	          } catch(Exception e){
	        	  log.error(e.getMessage());
	          } finally {
	              if (stmt != null)
	                  stmt.close();
	          }
	    }


	    /**
	     * coutAll-method. This method will return the number of all rows from table that matches
	     * this Dao. The implementation will simply execute "select count(primarykey) from table".
	     * If table is empty, the return value is 0. This method should be used before calling
	     * loadAll, to make sure table has not too many rows.
	     *
	     * @param conn         This method requires working database connection.
	     */
	    public int countAll(Connection conn) throws SQLException {

	          String sql = "SELECT count(*) FROM rm_dic_order_types";
	          PreparedStatement stmt = null;
	          ResultSet result = null;
	          int allRows = 0;

	          try {
	              stmt = conn.prepareStatement(sql);
	              result = stmt.executeQuery();

	              if (result.next())
	                  allRows = result.getInt(1);
	          } catch(Exception e){
	        	  log.error(e.getMessage());
	          } finally {
	              if (result != null)
	                  result.close();
	              if (stmt != null)
	                  stmt.close();
	          }
	          return allRows;
	    }


	    /** 
	     * searchMatching-Method. This method provides searching capability to 
	     * get matching valueObjects from database. It works by searching all 
	     * objects that match permanent instance variables of given object.
	     * Upper layer should use this by setting some parameters in valueObject
	     * and then  call searchMatching. The result will be 0-N objects in a List, 
	     * all matching those criteria you specified. Those instance-variables that
	     * have NULL values are excluded in search-criteria.
	     *
	     * @param conn         This method requires working database connection.
	     * @param valueObject  This parameter contains the class instance where search will be based.
	     *                     Primary-key field should not be set.
	     */
	    public List<DicOrderTypes> searchMatching(Connection conn,DicOrderTypes valueObject) throws SQLException {
	    	 List<DicOrderTypes> searchResults=null;
	    	try{
	          boolean first = true;
	          StringBuffer sql = new StringBuffer("SELECT * FROM rm_dic_order_types WHERE 1=1 ");

	          if (valueObject.getRtId() != 0) {
	              if (first) { first = false; }
	              sql.append("AND rt_id = ").append(valueObject.getRtId()).append(" ");
	          }

	          if (valueObject.getRtName() != null) {
	              if (first) { first = false; }
	              sql.append("AND rt_name LIKE '").append(valueObject.getRtName()).append("%' ");
	          }

	          if (valueObject.getRtName2() != null) {
	              if (first) { first = false; }
	              sql.append("AND rt_name2 LIKE '").append(valueObject.getRtName2()).append("%' ");
	          }

	          if (valueObject.getRtSymbol() != null) {
	              if (first) { first = false; }
	              sql.append("AND rt_symbol LIKE '").append(valueObject.getRtSymbol()).append("%' ");
	          }


	          sql.append("ORDER BY rt_id ASC ");

	          // Prevent accidential full table results.
	          // Use loadAll if all rows must be returned.
	          if (first)
	               searchResults = new ArrayList<DicOrderTypes>();
	          else
	               searchResults = listQuery(conn, conn.prepareStatement(sql.toString()));
	    	} catch(Exception e){
	        	  log.error(e.getMessage());
	    	}
	          return searchResults;
	    }


	    /** 
	     * getDaogenVersion will return information about
	     * generator which created these sources.
	     */
	    public String getDaogenVersion() {
	        return "DaoGen version 2.4.1";
	    }


	    /**
	     * databaseUpdate-method. This method is a helper method for internal use. It will execute
	     * all database handling that will change the information in tables. SELECT queries will
	     * not be executed here however. The return value indicates how many rows were affected.
	     * This method will also make sure that if cache is used, it will reset when data changes.
	     *
	     * @param conn         This method requires working database connection.
	     * @param stmt         This parameter contains the SQL statement to be excuted.
	     */
	    protected int databaseUpdate(Connection conn, PreparedStatement stmt) throws SQLException {

	          int result = stmt.executeUpdate();

	          return result;
	    }



	    /**
	     * databaseQuery-method. This method is a helper method for internal use. It will execute
	     * all database queries that will return only one row. The resultset will be converted
	     * to valueObject. If no rows were found, NotFoundException will be thrown.
	     *
	     * @param conn         This method requires working database connection.
	     * @param stmt         This parameter contains the SQL statement to be excuted.
	     * @param valueObject  Class-instance where resulting data will be stored.
	     */
	    protected void singleQuery(Connection conn, PreparedStatement stmt,DicOrderTypes valueObject) 
	          throws NotFoundException, SQLException {

	          ResultSet result = null;

	          try {
	              result = stmt.executeQuery();

	              if (result.next()) {

	                   valueObject.setRtId(result.getInt("rt_id")); 
	                   valueObject.setRtName(result.getString("rt_name")); 
	                   valueObject.setRtName2(result.getString("rt_name2")); 
	                   valueObject.setRtSymbol(result.getString("rt_symbol")); 

	              } else {
	                    //log.debug("OrderTypes Object Not Found!");
	                    throw new NotFoundException("OrderTypes Object Not Found!");
	              }
	              } catch(Exception e){
		        	  log.error(e.getMessage());
	              
	          } finally {
	              if (result != null)
	                  result.close();
	              if (stmt != null)
	                  stmt.close();
	          }
	    }


	    /**
	     * databaseQuery-method. This method is a helper method for internal use. It will execute
	     * all database queries that will return multiple rows. The resultset will be converted
	     * to the List of valueObjects. If no rows were found, an empty List will be returned.
	     *
	     * @param conn         This method requires working database connection.
	     * @param stmt         This parameter contains the SQL statement to be excuted.
	     */
	    protected List<DicOrderTypes> listQuery(Connection conn, PreparedStatement stmt) throws SQLException {

	          ArrayList<DicOrderTypes> searchResults = new ArrayList<DicOrderTypes>();
	          ResultSet result = null;

	          try {
	              result = stmt.executeQuery();

	              while (result.next()) {
	                  DicOrderTypes temp = createValueObject();

	                   temp.setRtId(result.getInt("rt_id")); 
	                   temp.setRtName(result.getString("rt_name")); 
	                   temp.setRtName2(result.getString("rt_name2")); 
	                   temp.setRtSymbol(result.getString("rt_symbol")); 

	                   searchResults.add(temp);
	              }
	          } catch(Exception e){
	        	  log.error(e.getMessage());
	          } finally {
	              if (result != null)
	                  result.close();
	              if (stmt != null)
	                  stmt.close();
	          }

	          return (List<DicOrderTypes>)searchResults;
	    }
	    
	    protected Map<String,String> listQuerySymbols(Connection conn, PreparedStatement stmt) throws SQLException {

	          //ArrayList<String> searchResults = new ArrayList<String>();
	    	HashMap<String,String> searchResults = new HashMap<String, String>();  
	    	ResultSet result = null;

	          try {
	              result = stmt.executeQuery();

	              while (result.next()) {
	                  String temp,temp2;
	                  //int tempInt=result.getInt("rt_id"); 
	                   temp=result.getString("rt_id"); 
	                   	log.debug(temp);
	                   temp2=result.getString("rt_name");
	                   searchResults.put(temp,temp2);
	                   log.debug("SEARCH result:"+searchResults.toString());
	              }
	          } catch(Exception e){
	        	  log.error(e.getMessage());
	          } finally {
	              if (result != null)
	                  result.close();
	              if (stmt != null)
	                  stmt.close();
	          }

	          return (Map<String,String>)searchResults;
	    }
	 
}
