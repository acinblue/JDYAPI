package com.shushan.entity;

public class Warehouse {
	private String warehouse;
	private int warehouseno;
	private String warehousetext;
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public int getWarehouseno() {
		return warehouseno;
	}
	public void setWarehouseno(int warehouseno) {
		this.warehouseno = warehouseno;
	}
	public String getWarehousetext() {
		return warehousetext;
	}
	public void setWarehousetext(String warehousetext) {
		this.warehousetext = warehousetext;
	}
	public Warehouse(String warehouse, int warehouseno, String warehousetext) {
		super();
		this.warehouse = warehouse;
		this.warehouseno = warehouseno;
		this.warehousetext = warehousetext;
	}
	public Warehouse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
