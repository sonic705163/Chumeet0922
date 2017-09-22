package com.wei.memjson;


import java.util.*;
import java.sql.*;

import java.text.ParseException;


public class MemDAO_JDBC implements MemDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BA103G2";
	String passwd = "a123";

	private static final String INSERT_STMT = 
			"INSERT INTO member VALUES (MEMBER_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM member order by memID";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM member where memID = ?";
		private static final String DELETE = 
			"DELETE FROM member where memID = ?";
		private static final String UPDATE = 
			"UPDATE member set memEmail= ?,memPw= ?,memberType= ?,memLv= ?,memExp= ?,memPt= ?,memName= ?,memGender= ?,memBD= ?,memPhone= ?,memAvatar= ?,memJoinDate= ?,memLoginNum= ?,memLocBorn= ?,memLocLive= ?,memInt= ?,memLong= ?,memLat= ?,memPriv= ?,memStatus= ? where memID= ?";

		
		
		
		
		@Override
		public byte[] getImage(int id) {
			String sql = "SELECT memAvatar FROM member WHERE memID = ?";
			Connection con = null;
			PreparedStatement ps = null;
			byte[] image = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					image = rs.getBytes(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (ps != null) {
						// When a Statement object is closed,
						// its current ResultSet object is also closed
						ps.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return image;
		}	
		
		
	@Override
	public int insert(MemVO memVO, byte[] image) {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memVO.getMemEmail());
			pstmt.setString(2, memVO.getMemPw());
			pstmt.setInt(3, memVO.getMemberType());
			pstmt.setInt(4, memVO.getMemLv());
			pstmt.setInt(5, memVO.getMemExp());
			pstmt.setInt(6, memVO.getMemPt());
			pstmt.setString(7, memVO.getMemName());
			pstmt.setInt(8, memVO.getMemGender());
			pstmt.setTimestamp(9, memVO.getMemBD());
			pstmt.setInt(10, memVO.getMemPhone());
			pstmt.setBytes(11, memVO.getMemAvatar());
			pstmt.setTimestamp(12, memVO.getMemJoinDate());
			pstmt.setInt(13, memVO.getMemLoginNum());
			pstmt.setString(14, memVO.getMemLocBorn());
			pstmt.setString(15, memVO.getMemLocLive());
			pstmt.setString(16, memVO.getMemInt());
			pstmt.setDouble(17, memVO.getMemLong());
			pstmt.setDouble(18, memVO.getMemLat());
			pstmt.setDouble(19, memVO.getMemPriv());
			pstmt.setInt(20, memVO.getMemStatus());

			count = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return count;
	}

	@Override
	public int update(MemVO memVO, byte[] image) {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memVO.getMemEmail());
			pstmt.setString(2, memVO.getMemPw());
			pstmt.setInt(3, memVO.getMemberType());
			pstmt.setInt(4, memVO.getMemLv());
			pstmt.setInt(5, memVO.getMemExp());
			pstmt.setInt(6, memVO.getMemPt());
			pstmt.setString(7, memVO.getMemName());
			pstmt.setInt(8, memVO.getMemGender());
			pstmt.setTimestamp(9, memVO.getMemBD());
			pstmt.setInt(10, memVO.getMemPhone());
			pstmt.setBytes(11, memVO.getMemAvatar());
			pstmt.setTimestamp(12, memVO.getMemJoinDate());
			pstmt.setInt(13, memVO.getMemLoginNum());
			pstmt.setString(14, memVO.getMemLocBorn());
			pstmt.setString(15, memVO.getMemLocLive());
			pstmt.setString(16, memVO.getMemInt());
			pstmt.setDouble(17, memVO.getMemLong());
			pstmt.setDouble(18, memVO.getMemLat());
			pstmt.setDouble(19, memVO.getMemPriv());
			pstmt.setInt(20, memVO.getMemStatus());

			count = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return count;
	}

	@Override
	public int delete(Integer memID) {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memID);

			count = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return count;

	}

	@Override
	public MemVO findByPrimaryKey(Integer memID) {

		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMemID(rs.getInt("memID"));
				memVO.setMemEmail(rs.getString("memEmail"));
				memVO.setMemPw(rs.getString("memPw"));
				memVO.setMemberType(rs.getInt("memberType"));
				memVO.setMemLv(rs.getInt("memLv"));
				memVO.setMemExp(rs.getInt("memExp"));
				memVO.setMemPt(rs.getInt("memPt"));
				memVO.setMemName(rs.getString("memName"));
				memVO.setMemGender(rs.getInt("memGender"));
				memVO.setMemBD(rs.getTimestamp("memBD"));
				memVO.setMemPhone(rs.getInt("memPhone"));
				memVO.setMemAvatar(rs.getBytes("memAvatar"));
				memVO.setMemJoinDate(rs.getTimestamp("memJoinDate"));
				memVO.setMemLoginNum(rs.getInt("memLoginNum"));
				memVO.setMemLocBorn(rs.getString("memLocBorn"));
				memVO.setMemLocLive(rs.getString("memLocLive"));
				memVO.setMemInt(rs.getString("memInt"));
				memVO.setMemLong(rs.getDouble("memLong"));	
				memVO.setMemLat(rs.getDouble("memLat"));	
				memVO.setMemPriv(rs.getInt("memPriv"));	
				memVO.setMemStatus(rs.getInt("memStatus"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return memVO;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMemID(rs.getInt("memID"));
				memVO.setMemEmail(rs.getString("memEmail"));
				memVO.setMemPw(rs.getString("memPw"));
				memVO.setMemberType(rs.getInt("memberType"));
				memVO.setMemLv(rs.getInt("memLv"));
				memVO.setMemExp(rs.getInt("memExp"));
				memVO.setMemPt(rs.getInt("memPt"));
				memVO.setMemName(rs.getString("memName"));
				memVO.setMemGender(rs.getInt("memGender"));
				memVO.setMemBD(rs.getTimestamp("memBD"));
				memVO.setMemPhone(rs.getInt("memPhone"));
				memVO.setMemAvatar(rs.getBytes("memAvatar"));
				memVO.setMemJoinDate(rs.getTimestamp("memJoinDate"));
				memVO.setMemLoginNum(rs.getInt("memLoginNum"));
				memVO.setMemLocBorn(rs.getString("memLocBorn"));
				memVO.setMemLocLive(rs.getString("memLocLive"));
				memVO.setMemInt(rs.getString("memInt"));
				memVO.setMemLong(rs.getDouble("memLong"));	
				memVO.setMemLat(rs.getDouble("memLat"));	
				memVO.setMemPriv(rs.getInt("memPriv"));	
				memVO.setMemStatus(rs.getInt("memStatus"));
				list.add(memVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) throws ParseException {

		MemDAO_JDBC dao = new MemDAO_JDBC();

		// 新增
//		MemVO memVO2 = new MemVO();
//		memVO2.setMemEmail("ggpower@hotmail.com");
//		memVO2.setMemPw("PPww");
//		memVO2.setMemberType(1);
//		memVO2.setMemLv(10);
//		memVO2.setMemExp(300);
//		memVO2.setMemPt(5900);
//		memVO2.setMemName("GodGG");
//		memVO2.setMemGender(1);
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        Date parsed = format.parse("20110210");
//        java.sql.Date sql = new java.sql.Date(parsed.getTime());
//		memVO2.setMemBD(sql);
//		memVO2.setMemPhone(12345678);
//		memVO2.setMemAvatar(null);
//		memVO2.setMemJoinDate(sql);
//		memVO2.setMemLoginNum(5);
//		memVO2.setMemLocBorn(null);
//		memVO2.setMemLocLive(null);
//		memVO2.setMemInt("歡迎歡迎");
//		memVO2.setMemLong(1.2);
//		memVO2.setMemLat(3.4);
//		memVO2.setMemPriv(1);
//		memVO2.setMemStatus(1);
//		dao.insert(memVO2);

//		// 更新
//		MemVO memVO2 = new memVO();
//		memVO2.setEmpno(7001);
//		memVO2.setEname("鍚虫案蹇�2");
//		memVO2.setJob("MANAGER2");
//		memVO2.setHiredate(java.sql.Date.valueOf("2002-01-01"));
//		memVO2.setSal(new Double(20000));
//		memVO2.setComm(new Double(200));
//		memVO2.setDeptno(20);
//		dao.update(memVO2);

		// 刪除
//		dao.delete(7014);

		// 查詢特定一筆
//		MemVO memVO3 = dao.findByPrimaryKey(1);
//		System.out.print(memVO3.getMemID() + ",");
//		System.out.print(memVO3.getMemEmail() + ",");
//		System.out.print(memVO3.getMemPw() + ",");
//		System.out.print(memVO3.getMemberType() + ",");
//		System.out.print(memVO3.getMemLv() + ",");
//		System.out.print(memVO3.getMemExp() + ",");
//		System.out.println(memVO3.getMemPt());
//		System.out.print(memVO3.getMemName() + ",");
//		System.out.print(memVO3.getMemGender() + ",");
//		System.out.print(memVO3.getMemBD() + ",");
//		System.out.print(memVO3.getMemPhone() + ",");
//		System.out.print(memVO3.getMemAvatar() + ",");
//		System.out.print(memVO3.getMemJoinDate() + ",");
//		System.out.print(memVO3.getMemLoginNum());
//		System.out.print(memVO3.getMemLocBorn() + ",");
//		System.out.print(memVO3.getMemLocLive() + ",");
//		System.out.print(memVO3.getMemInt() + ",");
//		System.out.print(memVO3.getMemLong() + ",");
//		System.out.print(memVO3.getMemLat() + ",");
//		System.out.print(memVO3.getMemPriv() + ",");
//		System.out.print(memVO3.getMemStatus());
//		System.out.println("---------------------");

		//查詢全部
//		List<MemVO> list = dao.getAll();
//		for (MemVO member : list) {
//			System.out.print(member.getMemID() + ",");
//			System.out.print(member.getMemEmail() + ",");
//			System.out.print(member.getMemPw() + ",");
//			System.out.print(member.getMemberType() + ",");
//			System.out.print(member.getMemLv() + ",");
//			System.out.print(member.getMemExp() + ",");
//			System.out.print(member.getMemPt());
//			System.out.print(member.getMemName() + ",");
//			System.out.print(member.getMemGender() + ",");
//			System.out.print(member.getMemBD() + ",");
//			System.out.print(member.getMemPhone() + ",");
//			System.out.print(member.getMemAvatar() + ",");
//			System.out.print(member.getMemJoinDate() + ",");
//			System.out.print(member.getMemLoginNum());
//			System.out.print(member.getMemLocBorn() + ",");
//			System.out.print(member.getMemLocLive() + ",");
//			System.out.print(member.getMemInt() + ",");
//			System.out.print(member.getMemLong() + ",");
//			System.out.print(member.getMemLat() + ",");
//			System.out.print(member.getMemPriv() + ",");
//			System.out.print(member.getMemStatus());
//			System.out.println("---------------------");
//			System.out.println();
//		}
		
		
		//查詢帳密
		MemVO memVO4 = dao.findByMemEmail("member01@gmail");
		System.out.print(memVO4.getMemID() + ",");
		System.out.print(memVO4.getMemEmail() + ",");
		System.out.print(memVO4.getMemPw() + ",");
		System.out.print(memVO4.getMemberType() + ",");
		System.out.print(memVO4.getMemLv() + ",");
		System.out.print(memVO4.getMemExp() + ",");
		System.out.print(memVO4.getMemPt());
		System.out.print(memVO4.getMemName() + ",");
		System.out.print(memVO4.getMemGender() + ",");
		System.out.print(memVO4.getMemBD() + ",");
		System.out.print(memVO4.getMemPhone() + ",");
		System.out.print(memVO4.getMemAvatar() + ",");
		System.out.print(memVO4.getMemJoinDate() + ",");
		System.out.print(memVO4.getMemLoginNum());
		System.out.print(memVO4.getMemLocBorn() + ",");
		System.out.print(memVO4.getMemLocLive() + ",");
		System.out.print(memVO4.getMemInt() + ",");
		System.out.print(memVO4.getMemLong() + ",");
		System.out.print(memVO4.getMemLat() + ",");
		System.out.print(memVO4.getMemPriv() + ",");
		System.out.print(memVO4.getMemStatus());

	}

	@Override
	public MemVO findByMemEmail(String memEmail) {
		String sql = "SELECT * FROM member WHERE mememail = ? ";
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			ps = con.prepareStatement(sql);
			
			ps.setString(1, memEmail);
			
			
			rs = ps.executeQuery();

			while (rs.next()) {
				
				memVO = new MemVO();
				memVO.setMemID(rs.getInt("memID"));
				memVO.setMemEmail(rs.getString("memEmail"));
				memVO.setMemPw(rs.getString("memPw"));
				memVO.setMemberType(rs.getInt("memberType"));
				memVO.setMemLv(rs.getInt("memLv"));
				memVO.setMemExp(rs.getInt("memExp"));
				memVO.setMemPt(rs.getInt("memPt"));
				memVO.setMemName(rs.getString("memName"));
				memVO.setMemGender(rs.getInt("memGender"));
				memVO.setMemBD(rs.getTimestamp("memBD"));
				memVO.setMemPhone(rs.getInt("memPhone"));
				memVO.setMemAvatar(rs.getBytes("memAvatar"));
				memVO.setMemJoinDate(rs.getTimestamp("memJoinDate"));
				memVO.setMemLoginNum(rs.getInt("memLoginNum"));
				memVO.setMemLocBorn(rs.getString("memLocBorn"));
				memVO.setMemLocLive(rs.getString("memLocLive"));
				memVO.setMemInt(rs.getString("memInt"));
				memVO.setMemLong(rs.getDouble("memLong"));	
				memVO.setMemLat(rs.getDouble("memLat"));	
				memVO.setMemPriv(rs.getInt("memPriv"));	
				memVO.setMemStatus(rs.getInt("memStatus"));
			
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					// When a Statement object is closed,
					// its current ResultSet object is also closed
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return memVO;

	}
	
	
	@Override
	public MemVO findByMemPw(String memPw) {
		// TODO Auto-generated method stub
		return null;
	}
}