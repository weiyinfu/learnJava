package dbpool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool implements InvocationHandler {
	// 定义连接
	private Connection conn = null;
	// 定义监控连接创建的语句
	private Statement statRef = null;
	private PreparedStatement prestatRef = null;
	// 是否支持事务标志
	private boolean supportTransaction = false;
	// 数据库的忙状态
	private boolean isFree = false;
	// 最后一次访问时间
	long lastAccessTime = 0;
	// 定义要接管的函数的名字
	String CREATESTATE = "createStatement";
	String CLOSE = "close";
	String PREPARESTATEMENT = "prepareStatement";
	String COMMIT = "commit";
	String ROLLBACK = "rollback";

	private ConnectionPool() {
		// 记录日至
		try {
			// 创建连接
			String driver = "";
			String url = "", user = "", password = "";
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, password);
			DatabaseMetaData dm = null;
			dm = conn.getMetaData();
			// 判断是否支持事务
			supportTransaction = dm.supportsTransactions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object obj = null;
		// 判断是否调用了close的方法，如果调用close方法则把连接置为无用状态
		if (CLOSE.equals(method.getName())) {
			// 设置不使用标志
			setIsFree(false);
			// 检查是否有后续工作，清除该连接无用资源
			if (statRef != null)
				statRef.close();
			if (prestatRef != null)
				prestatRef.close();
			return null;
		}
		// 判断是使用了createStatement语句
		if (CREATESTATE.equals(method.getName())) {
			obj = method.invoke(conn, args);
			statRef = (Statement) obj;// 记录语句
			return obj;
		}
		// 判断是使用了prepareStatement语句
		if (PREPARESTATEMENT.equals(method.getName())) {
			obj = method.invoke(conn, args);
			prestatRef = (PreparedStatement) obj;
			return obj;
		}
		// 如果不支持事务，就不执行该事物的代码
		if ((COMMIT.equals(method.getName()) || ROLLBACK.equals(method.getName())) && (!isSupportTransaction()))
			return null;
		obj = method.invoke(conn, args);
		// 设置最后一次访问时间，以便及时清除超时的连接
		lastAccessTime = System.currentTimeMillis();
		return obj;
	}

	public Connection getFreeConnection() {
		// 返回数据库连接conn的接管类，以便截住close方法
		Connection proxyConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(),
				conn.getClass().getInterfaces(), this);
		return proxyConn;
	}

	void close() throws SQLException {
		// 由于类属性conn是没有被接管的连接，因此一旦调用close方法后就直接关闭连接
		conn.close();
	}

	public void setIsFree(boolean value) {
		isFree = value;
	}

	public boolean isFree() {
		return isFree;
	}

	public boolean isSupportTransaction() {
		return supportTransaction;
	}
}