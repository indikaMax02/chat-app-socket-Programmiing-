package server.dao;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CrudDAO<T, ID> extends SuperDAO {

    boolean add(T t) throws SQLException, ClassNotFoundException;


}
