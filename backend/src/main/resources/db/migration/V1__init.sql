-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd.
CREATE TABLE oms_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(32) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(50) NOT NULL,
  role VARCHAR(20) NOT NULL,
  department VARCHAR(50),
  enabled BIT NOT NULL DEFAULT 1,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE oms_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(40) NOT NULL UNIQUE,
  external_order_no VARCHAR(60),
  channel_name VARCHAR(80) NOT NULL,
  customer_name VARCHAR(80) NOT NULL,
  customer_phone VARCHAR(30),
  item_summary VARCHAR(500) NOT NULL,
  item_count INT NOT NULL,
  total_amount DECIMAL(14,2) NOT NULL,
  paid_amount DECIMAL(14,2) NOT NULL,
  status VARCHAR(24) NOT NULL,
  warehouse_name VARCHAR(80),
  carrier_name VARCHAR(80),
  tracking_no VARCHAR(80),
  ordered_at DATETIME(6) NOT NULL,
  promised_ship_at DATETIME(6),
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_order_status(status),
  INDEX idx_order_time(ordered_at),
  INDEX idx_order_channel(channel_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE oms_shipment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  shipment_no VARCHAR(40) NOT NULL UNIQUE,
  order_no VARCHAR(40) NOT NULL,
  warehouse_name VARCHAR(80) NOT NULL,
  carrier_name VARCHAR(80),
  tracking_no VARCHAR(80),
  status VARCHAR(20) NOT NULL,
  item_count INT NOT NULL,
  shipped_at DATETIME(6),
  expected_delivery_at DATETIME(6),
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_shipment_order(order_no),
  INDEX idx_shipment_status(status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE oms_after_sale (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  request_no VARCHAR(40) NOT NULL UNIQUE,
  order_no VARCHAR(40) NOT NULL,
  customer_name VARCHAR(80) NOT NULL,
  type VARCHAR(24) NOT NULL,
  reason VARCHAR(255) NOT NULL,
  amount DECIMAL(14,2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  requested_at DATETIME(6) NOT NULL,
  handler_name VARCHAR(80),
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_after_sale_status(status),
  INDEX idx_after_sale_order(order_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE oms_sales_channel (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(30) NOT NULL UNIQUE,
  name VARCHAR(80) NOT NULL,
  type VARCHAR(24) NOT NULL,
  status VARCHAR(16) NOT NULL,
  sync_status VARCHAR(16) NOT NULL,
  today_orders BIGINT NOT NULL,
  today_amount DECIMAL(14,2) NOT NULL,
  last_sync_at DATETIME(6),
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE oms_order_event (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(40) NOT NULL,
  event_type VARCHAR(30) NOT NULL,
  title VARCHAR(80) NOT NULL,
  description VARCHAR(255) NOT NULL,
  operator_name VARCHAR(80) NOT NULL,
  occurred_at DATETIME(6) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL,
  INDEX idx_event_order(order_no),
  INDEX idx_event_time(occurred_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
