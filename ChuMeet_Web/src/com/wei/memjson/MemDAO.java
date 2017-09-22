package com.wei.memjson;


import java.util.*;
import java.sql.*;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemDAO implements MemDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
		"INSERT INTO member VALUES (MEMBER_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
		"SELECT * FROM member order by memID";
	private static final String GET_ONE_STMT = 
		"SELECT * FROM member where memID = ?";
	private static final String DELETE = 
		"DELETE FROM member where memID = ?";
	private static final String UPDATE = 
		"UPDATE member set memID= ?,memEmail= ?,memPw= ?,memberType= ?,memLv= ?,memExp= ?,memPt= ?,memName= ?,memGender= ?,memBD= ?,memPhone= ?,memAvatar= ?,memJoinDate= ?,memLoginNum= ?,memLocBorn= ?,memLocLive= ?,memInt= ?,memLong= ?,memLat= ?,memPriv= ?,memStatus= ?";
	//登入用
//	private static final String GET_ONE_STMT_BY_MEMEMAIL = 
//			"SELECT memID,memEmail,memPw,memberType,memLv,memExp,memPt,memName,memGender,memBD,memPhone,memAvatar,memJoinDate,memLoginNum,memLocBorn,memLocLive,memInt,memLong,memLat,memPriv,memStatus FROM member where memEmail = ?";
	private static final String GET_ONE_STMT_BY_MEMPW = 
			"SELECT memID,memEmail,memPw,memberType,memLv,memExp,memPt,memName,memGender,memBD,memPhone,memAvatar,memJoinDate,memLoginNum,memLocBorn,memLocLive,memInt,memLong,memLat,memPriv,memStatus FROM member where memPw = ?";

	
	@Override
	public MemVO findByMemEmail(String email){
		String sql = "SELECT * FROM member WHERE mememail = ? ";
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setString(1, email);
		
			
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
		}  finally{
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
	public byte[] getImage(int id) {
		String sql = "SELECT memAvatar FROM member WHERE memID = ?";
		Connection con = null;
		PreparedStatement ps = null;
		byte[] image = null;
		
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memID);

			count = pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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

//	@Override
//	public MemVO findByMemEmail(String memEmail) {
//		MemVO memVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ONE_STMT_BY_MEMEMAIL);
//
//			pstmt.setString(1, memEmail);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				memVO = new MemVO();
//				memVO.setMemID(rs.getInt("memID"));
//				memVO.setMemEmail(rs.getString("memEmail"));
//				memVO.setMemPw(rs.getString("memPw"));
//				memVO.setMemberType(rs.getInt("memberType"));
//				memVO.setMemLv(rs.getInt("memLv"));
//				memVO.setMemExp(rs.getInt("memExp"));
//				memVO.setMemPt(rs.getInt("memPt"));
//				memVO.setMemName(rs.getString("memName"));
//				memVO.setMemGender(rs.getInt("memGender"));
//				memVO.setMemBD(rs.getTimestamp("memBD"));
//				memVO.setMemPhone(rs.getInt("memPhone"));
//				memVO.setMemAvatar(rs.getBytes("memAvatar"));
//				memVO.setMemJoinDate(rs.getTimestamp("memJoinDate"));
//				memVO.setMemLoginNum(rs.getInt("memLoginNum"));
//				memVO.setMemLocBorn(rs.getString("memLocBorn"));
//				memVO.setMemLocLive(rs.getString("memLocLive"));
//				memVO.setMemInt(rs.getString("memInt"));
//				memVO.setMemLong(rs.getDouble("memLong"));
//				memVO.setMemLat(rs.getDouble("memLat"));
//				memVO.setMemPriv(rs.getInt("memPriv"));
//				memVO.setMemStatus(rs.getInt("memStatus"));
//			}
//
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return memVO;
//	}

	@Override
	public MemVO findByMemPw(String memPw) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_MEMPW);

			pstmt.setString(1, memPw);

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




	
}

