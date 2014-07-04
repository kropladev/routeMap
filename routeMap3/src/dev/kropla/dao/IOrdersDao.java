package dev.kropla.dao;

import dev.kropla.dto.OrdersAndroidObject;

public interface IOrdersDao {
	public abstract OrdersAndroidObject dataOfOrderById(int orderId);
}
