package com.wei.actjson;
import java.sql.*;
import java.util.*;
//import com.act.model.ActVO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ActDAO implements ActDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "insert into act (actID,memID,actCreateDate,actName,actStatID,actTimeID,actPriID,actLocID,actStartDate,actEndDate,actSignStartDate,actSignEndDate,actITVType,actITVCount,actMemMax,actMemMin,actImg,actBN,actContent,actWeather,actWD,actWR,actIsHot,actLong,actLat,actLocName,actAdr) "+
																					"values (actID_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?,?,?)";
	private static final String UPDATE = "update act set actName=?, actStatID=?, actTimeID=?, actPriID=?, "
																					+ "actStartDate=?, actEndDate=?, actSignStartDate=?, actSignEndDate=?,"
																					+ " actITVType=?, actITVCount=?, actMemMax=?, actMemMin=?, "
																					+ "actImg=?, actContent=?, actAdr=? where actID=?";
	private static final String GET_ALL_STMT = "SELECT * FROM act";
//	private static final String GET_ONE_STMT = "select * from act where actID=?";
	private static final String GET_ACT_BY_ACTID="select * from act where actID=?";
	private static final String GET_ACT_BY_DATE="select * from act where actDate=timestamp (\'?\')";
	private static final String GET_ACT_BY_POIID="select * from act join actPOI on act.actID=actPOI.actID where POIID=?";
	private static final String GET_ACT_BY_WKS="SELECT * FROM act WHERE MOD(TO_CHAR(?, 'J'), 7) + 1 IN (6, 7);";
//	private static final String GET_ACT_BY_DIST="select * from act where ?";
	private static final String GET_ACT_BY_MEMID_CREATE="select * from act where actmem.memID=?";
	private static final String GET_ACT_BY_MEMID_JOIN="select * from act join actmem on act.actID=actmem.actID join member on actmem.memID=member.memID"
																								+"where actmem.memID=?";
	private static final String GET_ACT_BY_MEMID_TRACK="select * from act join actmem on act.actID=actTraMem.actID join member on actTraMem.memID=member.memID"
																								+"where actTraMem.memID=?";
	private static final String GET_ACT_BY_MEMID_FRIEND="select * from act join actmem on act.actID=actmem.actID join Friends on actmem.memID=Friends.frimem1 where Friends.FriendType='好友'";

	private static final String GET_ACT_BY_CLUBID="select * from act join actclub on act.actID=actClub.actID join club on actClub.ClubID=Club.ClubID"
			+"where actClub (actClub.ClubID!=0) AND actClub.ClubID=?";

	@Override
	public byte[] getImage(int id) {
		String sql = "SELECT actImg FROM act WHERE actID = ?";
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

	public int insert(ActVO ActVO, byte[] actImg) {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, ActVO.getMemID());
			pstmt.setTimestamp(2, ActVO.getActCreateDate());
			pstmt.setString(3, ActVO.getActName());
			pstmt.setInt(4, ActVO.getActStatID());
			pstmt.setInt(5, ActVO.getActTimeID());
			pstmt.setInt(6, ActVO.getActPriID());
			pstmt.setInt(7, ActVO.getActLocID());
			pstmt.setTimestamp(8, ActVO.getActStartDate());
			pstmt.setTimestamp(9, ActVO.getActEndDate());
			pstmt.setTimestamp(10, ActVO.getActSignStartDate());
			pstmt.setTimestamp(11, ActVO.getActSignEndDate());
			pstmt.setInt(12, ActVO.getActITVType());
			pstmt.setString(13, ActVO.getActITVCount());
			pstmt.setInt(14, ActVO.getActMemMax());
			pstmt.setInt(15, ActVO.getActMemMin());
			pstmt.setBytes(16, actImg);
			pstmt.setBytes(17, ActVO.getActBN());
			pstmt.setString(18, ActVO.getActContent());
			pstmt.setString(19, ActVO.getActWeather());
			pstmt.setString(20, ActVO.getActWD());
			pstmt.setString(21, ActVO.getActWR());
			pstmt.setDouble(22, ActVO.getActLong());
			pstmt.setDouble(23, ActVO.getActLat());
			pstmt.setString(24, ActVO.getActLocName());
			pstmt.setString(25, ActVO.getActAdr());

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
	public int update(ActVO ActVO, byte[] actImg) {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, ActVO.getActName());
			pstmt.setInt(2, ActVO.getActStatID());
			pstmt.setInt(3, ActVO.getActTimeID());
			pstmt.setInt(4, ActVO.getActPriID());
			pstmt.setTimestamp(5, ActVO.getActStartDate());
			pstmt.setTimestamp(6, ActVO.getActEndDate());
			pstmt.setTimestamp(7, ActVO.getActSignStartDate());
			pstmt.setTimestamp(8, ActVO.getActSignEndDate());
			pstmt.setInt(9, ActVO.getActITVType());
			pstmt.setString(10, ActVO.getActITVCount());
			pstmt.setInt(11, ActVO.getActMemMax());
			pstmt.setInt(12, ActVO.getActMemMin());
			pstmt.setBytes(13, actImg);
			pstmt.setString(14, ActVO.getActContent());
			pstmt.setString(15, ActVO.getActAdr());
			pstmt.setInt(16, ActVO.getActID());
			
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
	public ActVO findByPrimaryKey(Integer actID) {

		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_ACTID);

			pstmt.setInt(1, actID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ActVO 也稱為 Domain objects
				ActVO = new ActVO();
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActStartDate(rs.getTimestamp("actStartDate"));
				ActVO.setActAdr(rs.getString("actAdr"));
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
		return ActVO;
	}

	@Override
	public List<ActVO> getAll() {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActCreateDate(rs.getTimestamp("actCreateDate"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActStatID(rs.getInt("actStatID"));
				ActVO.setActTimeID(rs.getInt("actTimeID"));
				ActVO.setActPriID(rs.getInt("actPriID"));
				ActVO.setActLocID(rs.getInt("actLocID"));
				ActVO.setActStartDate(rs.getTimestamp("actStartDate"));
				ActVO.setActEndDate(rs.getTimestamp("actEndDate"));
				ActVO.setActSignStartDate(rs.getTimestamp("actSignStartDate"));
				ActVO.setActSignEndDate(rs.getTimestamp("actSignEndDate"));
				ActVO.setActITVType(rs.getInt("actITVType"));
				ActVO.setActITVCount(rs.getString("actITVCount"));
				ActVO.setActMemMax(rs.getInt("actMemMax"));
				ActVO.setActMemMin(rs.getInt("actMemMin"));
//				ActVO.setActImg(rs.getBytes("actImg"));
//				ActVO.setActBN(rs.getBytes("actBN"));
				ActVO.setActContent(rs.getString("actContent"));
				ActVO.setActWeather(rs.getString("actWeather"));
				ActVO.setActWD(rs.getString("actWD"));
				ActVO.setActWR(rs.getString("actWR"));
				ActVO.setActIsHot(rs.getInt("actIsHot"));
				ActVO.setActLong(rs.getDouble("actLong"));
				ActVO.setActLat(rs.getDouble("actLat"));
				ActVO.setActLocName(rs.getString("actLocName"));
				ActVO.setActAdr(rs.getString("actAdr"));
				list.add(ActVO); // Store the row in the list
			}

			// Handle any driver errors
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

	@Override
	public List<ActVO> getActByCat(Integer POIID) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_POIID);
			pstmt.setInt(1, POIID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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

@Override
	public List<ActVO> getActByDate(Timestamp actDate) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_DATE);
			pstmt.setTimestamp(1, actDate);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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

	@Override
	public List<ActVO> getActByWks(Timestamp actDate) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_WKS);
			pstmt.setTimestamp(1, actDate);
			pstmt.setTimestamp(2, actDate);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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
//	public List<ActVO> getActByDist(Integer Dist) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<ActVO> getActByMemIDJoin(Integer memID) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_MEMID_JOIN);
			pstmt.setInt(1, memID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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


	@Override
	public List<ActVO> getActByMemIDCreate(Integer memID) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_MEMID_CREATE);
			pstmt.setInt(1, memID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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


	@Override
	public List<ActVO> getActByMemIDFriend(Integer memID) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_MEMID_FRIEND);
			pstmt.setInt(1, memID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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


	@Override
	public List<ActVO> getActByMemIDTrack(Integer memID) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_MEMID_TRACK);
			pstmt.setInt(1, memID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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


	@Override
	public List<ActVO> getActByClub(Integer clubID) {
		List<ActVO> list = new ArrayList<ActVO>();
		ActVO ActVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ACT_BY_CLUBID);
			pstmt.setInt(1, clubID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ActVO = new ActVO();
				ActVO.setActID(rs.getInt("actID"));
				ActVO.setMemID(rs.getInt("memID"));
				ActVO.setActName(rs.getString("actName"));
				ActVO.setActName(rs.getString("actCreateDate"));
				list.add(ActVO); // Store the row in the list
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
}
