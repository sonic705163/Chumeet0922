package com.ad.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class AdJDBCDAO implements AdDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "project";
	String passwd = "123";
	
	private static final String INSERT_STMT = "INSERT INTO ad (adID,adminID,adName,adImg,adContent,adLink,adDate) VALUES (adID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE ad SET adminID=?, adName=?, adImg=?, adContent=?, adLink=?, adDate=? WHERE adID = ?";
	private static final String DELETE_AD = "DELETE FROM ad WHERE adID = ?";
	private static final String GET_ONE_STMT = "SELECT adID,adminID,adName,adImg,adContent,adLink,adDate FROM ad WHERE adID = ?";
	private static final String GET_ALL_STMT = "SELECT adID,adminID,adName,adImg,adContent,adLink,adDate FROM ad";
	
	@Override
	public void insert(AdVO adVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, adVO.getAdminID());
			pstmt.setString(2, adVO.getAdName());
			pstmt.setBytes(3, adVO.getAdImg());
			pstmt.setString(4, adVO.getAdContent());	
			pstmt.setString(5, adVO.getAdLink());
			pstmt.setTimestamp(6, adVO.getAdDate());

			pstmt.executeUpdate();

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

	}
	@Override
	public void update(AdVO adVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, adVO.getAdminID());
			pstmt.setString(2, adVO.getAdName());
			pstmt.setBytes(3, adVO.getAdImg());
			pstmt.setString(4, adVO.getAdContent());	
			pstmt.setString(5, adVO.getAdLink());
			pstmt.setTimestamp(6, adVO.getAdDate());
			pstmt.setInt(7, adVO.getAdID());

			pstmt.executeUpdate();

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

	}
	@Override
	public void delete(Integer adID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_AD);
			
			pstmt.setInt(1, adID);
			
			pstmt.executeUpdate();
			
				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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

	}
	@Override
	public AdVO findByPrimaryKey(Integer adID) {
		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, adID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// adVO 也稱為 Domain objects
				adVO = new AdVO();
				adVO.setAdID(rs.getInt("adID"));
				adVO.setAdminID(rs.getInt("adminID"));
				adVO.setAdName(rs.getString("adName"));
				adVO.setAdImg(rs.getBytes("adImg"));
				adVO.setAdContent(rs.getString("adContent"));
				adVO.setAdLink(rs.getString("adLink"));
				adVO.setAdDate(rs.getTimestamp("adDate"));
				
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
		return adVO;
	}
	
	@Override
	public List<AdVO> getAll() {
		List<AdVO> list = new ArrayList<AdVO>();
		AdVO adVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adVO = new AdVO();
				adVO.setAdID(rs.getInt("adID"));
				adVO.setAdminID(rs.getInt("adminID"));
				adVO.setAdName(rs.getString("adName"));
				adVO.setAdImg(rs.getBytes("adImg"));
				adVO.setAdContent(rs.getString("adContent"));
				adVO.setAdLink(rs.getString("adLink"));
				adVO.setAdDate(rs.getTimestamp("adDate"));
				list.add(adVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

	public static void main(String[] args) {
		
		AdJDBCDAO dao = new AdJDBCDAO();

		// 新增
//		AdVO adVO1 = new AdVO();
//		adVO1.setAdminID(2);
//		adVO1.setAdName("RO仙境傳說");
//		adVO1.setAdImg(null);
//		adVO1.setAdContent("超好玩");
//		adVO1.setAdLink("https://ro.gnjoy.com.tw/");
//		adVO1.setAdDate(Timestamp.valueOf("2006-05-02 18:05:00.0"));
//		dao.insert(adVO1);

		// 修改
//		AdVO adVO2 = new AdVO();
//		adVO2.setAdminID(2);
//		adVO2.setAdName(" iT 邦幫忙");
//		adVO2.setAdImg(null);
//		adVO2.setAdContent("IT 人的一日，就是在面對問題、解決問題中度過。");
//		adVO2.setAdLink("http://ithelp.ithome.com.tw/");
//		adVO2.setAdDate(Timestamp.valueOf("2015-05-02 18:05:00.0"));
//		adVO2.setAdID(8);
//		dao.update(adVO2);

		// 刪除
//		dao.delete(9);

		// 查詢
//		AdVO adVO3 = dao.findByPrimaryKey(8);
//		System.out.print(adVO3.getAdID() + ",");
//		System.out.print(adVO3.getAdminID() + ",");
//		System.out.print(adVO3.getAdName() + ",");
//		System.out.print(adVO3.getAdImg() + ",");
//		System.out.print(adVO3.getAdContent() + ",");
//		System.out.print(adVO3.getAdLink() + ",");
//		System.out.print(adVO3.getAdDate());
//		System.out.println();
//		System.out.println("---------------------");

		// 查詢部門
		List<AdVO> list = dao.getAll();
		for (AdVO aAd : list) {
			System.out.print(aAd.getAdID() + ",");
			System.out.print(aAd.getAdminID() + ",");
			System.out.print(aAd.getAdName() + ",");
			System.out.print(aAd.getAdImg() + ",");
			System.out.print(aAd.getAdContent() + ",");
			System.out.print(aAd.getAdLink() + ",");
			System.out.print(aAd.getAdDate());
			System.out.println();
		}
		
	
		
	}
}

