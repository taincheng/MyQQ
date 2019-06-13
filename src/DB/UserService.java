package DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import QQServer.UserHaoyouinfo;
import sun.security.ssl.KerberosClientKeyExchange;

public class UserService {
	/**
	 * email登录
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws UsernameNotFoundException
	 *             用户名错误
	 * @throws PasswordException
	 *             密码错误
	 * @throws StateException
	 *             账户被锁定
	 * @throws SQLexcept
	 *             sql错误
	 */
	public String LoginForEmail(String email,String password) 
			throws UsernameNotFoundException, PasswordException, StateException, SQLException{
		return Login(email, password,"select * from userinfo where email = ?");
	}
	

	/**
	 * 手机号码登录
	 * 
	 * @param phone
	 * @param password
	 * @return
	 * @throws UsernameNotFoundException
	 *             用户名错误
	 * @throws PasswordException
	 *             密码错误
	 * @throws StateException
	 *             账户被锁定
	 * @throws SQLexcept
	 *             sql错误
	 */
	public String LoginForPhone(String phone,String password) 
			throws UsernameNotFoundException, PasswordException, StateException, SQLException{
		return Login(phone, password,"select * from userinfo where phonenumber = ?");
	}
	
	
	private String Login(String key, String password,String sql)
			throws UsernameNotFoundException, PasswordException, StateException, SQLException {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, key);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getInt("state") == 0) {
					if (rs.getString("password").equals(password)) {
						return rs.getString(1);
					} else {
						throw new PasswordException();
					}
				} else {
					throw new StateException();
				}

			} else {
				throw new UsernameNotFoundException();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

	}
	/**
	 * 得到好友列表
	 * @param uid 
	 * @return  返回好友列表集合
	 * @throws Exception 
	 */
	public Vector<UserHaoyouinfo> getHaoyoulist(String uid) throws Exception{
		Connection conn = null;
		
		try {
			conn = DBManager.getConnection();
			String sql = "select u.image, u.uid, u.netname ,u.info from hy h INNER JOIN userinfo u ON u.uid = h.hyuid AND h.uid = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, uid);
			ResultSet rs = pst.executeQuery();
			
			Vector<UserHaoyouinfo> vector = new Vector<UserHaoyouinfo>();
			while (rs.next()) {
				UserHaoyouinfo userHaoyouinfo = new UserHaoyouinfo();
				userHaoyouinfo.setUid(rs.getString(2));
				userHaoyouinfo.setImg(rs.getString(1));
				userHaoyouinfo.setInfo(rs.getString(4));
				userHaoyouinfo.setNetname(rs.getString(3));
				vector.add(userHaoyouinfo);
			}
			return vector;
		} catch (Exception e) {
			throw e;
		}finally {
			conn.close();
		}
	}
	
	
	/**
	 * 得到个人信息
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public UserGereninfo getUserinfo(String uid) throws Exception{
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select * from userinfo where uid = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, uid);
			ResultSet rs = pst.executeQuery();
			UserGereninfo userGereninfo = new UserGereninfo();
			if(rs.next()){
				userGereninfo.setUid(rs.getString("uid"));
				userGereninfo.setBack(rs.getString("back"));
				userGereninfo.setInfo(rs.getString("info"));
				userGereninfo.setNetname(rs.getString("netname"));
				userGereninfo.setImg(rs.getString("image"));
				userGereninfo.setEmail(rs.getString("email"));
				userGereninfo.setPhonenumber(rs.getString("phonenumber"));
				userGereninfo.setSex(rs.getString("sex"));
				userGereninfo.setName(rs.getString("name"));
				userGereninfo.setYy(rs.getInt("yy"));
				userGereninfo.setMm(rs.getInt("mm"));
				userGereninfo.setDd(rs.getInt("dd"));
				
			}
			return userGereninfo;
		} catch (Exception e) {
			throw e;
		}finally {
			conn.close();
		}
	}
	
	public void regUser(String username,String password) throws UsernameException,SQLException{
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select * from userinfo where phonenumber = ? or email = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, username);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				throw new UsernameException();
			}
			if(username.indexOf("@") >= 0){
				sql = "insert into userinfo(uid,email,password,createtime) values(?,?,?,SYSDATE())";
				pst = conn.prepareStatement(sql);
				
			}else if (username.trim().length() == 11 && username.indexOf("@") <= -1) {
				sql = "insert into userinfo(uid,phonenumber,password,createtime) values(?,?,?,SYSDATE())";
				pst = conn.prepareStatement(sql);
			}
			
			pst.setString(1, System.currentTimeMillis()+"R"+(int)(Math.random()*10000));
			pst.setString(2, username);
			pst.setString(3, password);
			if(pst.executeUpdate() <= 0){
				throw new SQLException();
			}
		} catch (SQLException e) {
		}finally {
			conn.close();
		}
	}

}
