package dev.kropla.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import dev.kropla.dto.OrdersAndroidObject;

public class OrdersDao extends JdbcDaoSupport implements IOrdersDao{
/*	private SimpleJdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
		this.jdbcTemplate=jdbcTemplate;
	}*/
	
	@Override
	public OrdersAndroidObject dataOfOrderById(int orderId) {
		return getJdbcTemplate().queryForObject(DbQueries.S_V_ORDERS_DATA_ANDROID, new RowMapper<OrdersAndroidObject>() {
			@Override
			public OrdersAndroidObject mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrdersAndroidObject orderObj = new OrdersAndroidObject();
				orderObj.setCustFirstName(rs.getString("custFirstName"));
				orderObj.setCustId(rs.getInt("custId"));
				orderObj.setCustLastName(rs.getString("custLastName"));
				orderObj.setCustName(rs.getString("custName"));
				orderObj.setCustPhone(rs.getString("custPhone"));
				orderObj.setOrderId(rs.getInt("orderId"));
				orderObj.setOrderName(rs.getString("orderName"));
				orderObj.setOrderStreet_from(rs.getString("orderStreet_from"));
				orderObj.setOrderStreet_goal(rs.getString("orderStreet_goal"));
				orderObj.setRtId(rs.getInt("orderTypeId"));
				orderObj.setRtName(rs.getString("orderTypeDesc"));
				orderObj.setTimeOrderStart(rs.getString("timeOrderStart"));
				orderObj.setOrderStatusDesc(rs.getString("orderStatusDesc"));
				orderObj.setOrderStatusId(rs.getInt("orderStatusId"));
				return orderObj;
			}
		},orderId);
	}
	
}
