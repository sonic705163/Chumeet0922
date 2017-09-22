package com.wei.clubjson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





public class ClubJDBCDAO implements ClubDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "BA103G2";
	String passwd = "a123";
	

	private static final String INSERT_STMT = 
			"INSERT INTO club (clubID,clubmemID,clubName,clubTypeID,clubContent,clubPhoto,clubStartDate,clubStatus,clubLong,clubLat) VALUES (club_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
				"SELECT * FROM club ORDER BY clubID";
//			"SELECT clubID,clubmemID,clubName,clubTypeID,clubContent, clubStartDate,clubStatus,clubLong,clubLat FROM club order by clubID";
		private static final String GET_ONE_STMT = 
			"SELECT clubID,clubmemID,clubName,clubTypeID,clubContent, clubStartDate,clubStatus,clubLong,clubLat FROM club where clubID = ?";
		private static final String DELETE = 
			"DELETE FROM club where clubID = ?";
		private static final String UPDATE = 
			"UPDATE club set clubmemID=?, clubName=?, clubTypeID=?, clubContent=?,clubPhoto=?, clubStartDate=?, clubStatus=?, clubLong=?, clubLat=? where clubID = ?";

		
		@Override
		public byte[] getImage(int id) {
			String sql = "SELECT clubPhoto FROM club WHERE clubID = ?";
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
		public int insert(ClubVO clubVO, byte[] clubPhoto) {
			int count = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setInt(1, clubVO.getClubMemId());
				pstmt.setString(2, clubVO.getClubName());
				pstmt.setInt(3, clubVO.getClubTypeId());
				pstmt.setString(4, clubVO.getClubContent());
				pstmt.setBytes(5, clubPhoto);
				pstmt.setTimestamp(6, clubVO.getClubStartDate());
				pstmt.setInt(7, clubVO.getClubStatus());
				pstmt.setDouble(8, clubVO.getClubLong());
				pstmt.setDouble(9, clubVO.getClubLat());

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
		public int update(ClubVO clubVO, byte[] clubPhoto) {
			int count = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setInt(1, clubVO.getClubMemId());
				pstmt.setString(2, clubVO.getClubName());
				pstmt.setInt(3, clubVO.getClubTypeId());
				pstmt.setString(4, clubVO.getClubContent());
				pstmt.setBytes(5, clubPhoto);
				pstmt.setTimestamp(6, clubVO.getClubStartDate());
				pstmt.setInt(7, clubVO.getClubStatus());
				pstmt.setDouble(8, clubVO.getClubLong());
				pstmt.setDouble(9, clubVO.getClubLat());
				pstmt.setInt(10, clubVO.getClubId());

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
		public int delete(Integer clubID) {
			int count = 0;
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, clubID);

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
		public ClubVO findByPrimaryKey(Integer clubID) {

			ClubVO clubVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, clubID);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo �]�٬� Domain objects
					clubVO = new ClubVO();
					clubVO.setClubId(rs.getInt("clubID"));
					clubVO.setClubMemId(rs.getInt("clubmemID"));			
					clubVO.setClubName(rs.getString("clubName"));
					clubVO.setClubTypeId(rs.getInt("clubTypeID"));
					clubVO.setClubContent(rs.getString("clubContent"));
//					clubVO.setClubPhoto(rs.getBytes("clubPhoto"));
					clubVO.setClubStartDate(rs.getTimestamp("clubStartDate"));
					clubVO.setClubStatus(rs.getInt("clubStatus"));
					clubVO.setClubLong(rs.getDouble("clubLong"));
					clubVO.setClubLat(rs.getDouble("clubLat"));
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
			return clubVO;
		}

		@Override
		public List<ClubVO> getAll() {
			List<ClubVO> list = new ArrayList<ClubVO>();
			ClubVO clubVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
				
					clubVO = new ClubVO();
					clubVO.setClubId(rs.getInt("clubID"));
					clubVO.setClubMemId(rs.getInt("clubMemID"));
					clubVO.setClubName(rs.getString("clubName"));
					clubVO.setClubTypeId(rs.getInt("clubTypeID"));
					clubVO.setClubContent(rs.getString("clubContent"));
//					clubVO.setClubPhoto(rs.getBytes("clubPhoto"));
					clubVO.setClubStartDate(rs.getTimestamp("clubStartDate"));
					clubVO.setClubStatus(rs.getInt("clubStatus"));
					clubVO.setClubLong(rs.getDouble("clubLong"));
					clubVO.setClubLat(rs.getDouble("clubLat"));
					list.add(clubVO); // Store the row in the list
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

		public static void main(String[] args) {

//			ClubJDBCDAO dao = new ClubJDBCDAO();

			// �s�WOK
//			ClubVO clubVO1 = new ClubVO();
//			clubVO1.setClubmemID(7);
//			clubVO1.setClubName("�i���L");
//			clubVO1.setClubTypeID(1);
//			clubVO1.setClubContent("�w�w�z�n");
////			clubVO1.setClubPhoto(new Double(500));
//			clubVO1.setClubStartDate(java.sql.Date.valueOf("2017-09-09"));
//			clubVO1.setClubStatus(1);
//			clubVO1.setClubLong(1.00006);
//			clubVO1.setClubLat(1.000007);
//			dao.insert(clubVO1);

			// �ק�OK
//			ClubVO clubVO2 = new ClubVO();
//			clubVO2.setClubID(42);
//			clubVO2.setClubmemID(9);
//			clubVO2.setClubName("�d��JAVA");
//			clubVO2.setClubTypeID(1);
//			clubVO2.setClubContent("�˦۱½�");
//			clubVO2.setClubStartDate(java.sql.Date.valueOf("2017-09-09"));
//			clubVO2.setClubStatus(1);
//			clubVO2.setClubLong(19.1131131);
//			clubVO2.setClubLat(20.121416);
//			dao.update(clubVO2);
	//
			// �R��
//			dao.delete(43);
	//
//			// �d��OK
//			ClubVO clubVO3 = dao.findByPrimaryKey(2);
//			System.out.print(clubVO3.getClubID() + ",");
//			System.out.print(clubVO3.getClubmemID() + ",");
//			System.out.print(clubVO3.getClubName() + ",");
//			System.out.print(clubVO3.getClubTypeID() + ",");
//			System.out.print(clubVO3.getClubContent() + ",");
//			System.out.print(clubVO3.getClubStartDate() + ",");
//			System.out.print(clubVO3.getClubStatus() + ",");
//			System.out.print(clubVO3.getClubLong() + ",");
//			System.out.println(clubVO3.getClubLat());
//			System.out.println("---------------------");
	//
			// �d��OK
//			List<ClubVO> list = dao.getAll();
//			for (ClubVO aClub : list) {
//				System.out.print(aClub.getClubID() + ",");
//				System.out.print(aClub.getClubmemID() + ",");
//				System.out.print(aClub.getClubName() + ",");
//				System.out.print(aClub.getClubTypeID() + ",");
//				System.out.print(aClub.getClubContent() + ",");
//				System.out.print(aClub.getClubStartDate() + ",");
//				System.out.print(aClub.getClubStatus() + ",");
//				System.out.print(aClub.getClubLong() + ",");
//				System.out.print(aClub.getClubLat());
//				System.out.println();
//			}
		}



}