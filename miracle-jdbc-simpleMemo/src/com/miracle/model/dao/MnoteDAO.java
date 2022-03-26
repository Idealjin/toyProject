package com.miracle.model.dao;

import static com.miracle.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import com.miracle.model.dto.MnoteDTO;
import com.miracle.model.dto.RecordDTO;

public class MnoteDAO {
	
	public MnoteDAO() {
		
		try {
			prop.loadFromXML(new FileInputStream("mapper/Mnote-query.xml"));
			
			
		} catch (InvalidPropertiesFormatException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	

	Properties prop = new Properties();
	
	public int newNote(Connection con, MnoteDTO mnoteDTO) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("newNote");
		
		try {
			pstmt = con.prepareStatement(query);
		
			pstmt.setString(1, mnoteDTO.getTitle());
			pstmt.setString(2, mnoteDTO.getContent());
			pstmt.setString(3, mnoteDTO.getDate());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
		
		
		
		
	}

	public List<MnoteDTO> selNote(Connection con) {
		
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selNote");
		
		MnoteDTO mnoteDTO = new MnoteDTO();
		
		List<MnoteDTO> mnoteDTO2 = new ArrayList<>();
		
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				mnoteDTO.setMemoNo(rset.getInt("NO"));
				mnoteDTO.setTitle(rset.getString("TITLE")); 
				mnoteDTO.setContent(rset.getString("CONTENT"));
				mnoteDTO.setDate(rset.getString("CREATE_DATE"));
				mnoteDTO.setDelYn(rset.getBoolean("NOTE_STATUS"));
				
				mnoteDTO2.add(mnoteDTO);
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return mnoteDTO2;
		
	}

	public int updateNote(Connection con, MnoteDTO mnoteDTO) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateNote");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mnoteDTO.getTitle());
			pstmt.setString(2, mnoteDTO.getContent());
			pstmt.setInt(3, mnoteDTO.getMemoNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}

	public List<MnoteDTO> selNoteNo(Connection con, int selNo) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = prop.getProperty("selNoteNo");
		
		MnoteDTO mnoteDTO = new MnoteDTO();
		
		List<MnoteDTO> mnoteDTO2 = new ArrayList<>();
		
		try {
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, selNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				mnoteDTO.setMemoNo(rset.getInt("NO"));
				mnoteDTO.setTitle(rset.getString("TITLE")); 
				mnoteDTO.setContent(rset.getString("CONTENT"));
				mnoteDTO.setDate(rset.getString("CREATE_DATE"));
				mnoteDTO.setDelYn(rset.getBoolean("NOTE_STATUS"));
				
				mnoteDTO2.add(mnoteDTO);
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return mnoteDTO2;
		
	}

	public int delNote(Connection con, int selNo) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("delNote");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, selNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int newNoteRecord(Connection con, RecordDTO recordDTO) {
	
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("newNoteRecord");
		
		try {
			pstmt = con.prepareStatement(query);
		
			pstmt.setString(1, recordDTO.getAuthor());
			pstmt.setString(2, recordDTO.getNoteDate());
			pstmt.setString(3, recordDTO.getState());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateRecord(Connection con, RecordDTO recordDTO) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateRecord");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, recordDTO.getNoteDate());
			pstmt.setString(2, recordDTO.getState());
			pstmt.setInt(3, recordDTO.getNo());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}

	public int delRecord(Connection con, int selNo, RecordDTO recordDTO) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("delRecord");
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, recordDTO.getNoteDate());
			pstmt.setString(2, recordDTO.getState());
			pstmt.setInt(3, selNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

}
