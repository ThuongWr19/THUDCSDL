package De1;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class De1_Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMaSach;
	private JTextField txtTenSach;
	private JTextField txtGia;
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					De1_Frame frame = new De1_Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public De1_Frame() {
		setTitle("De 1 - Sach Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mã Sách");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 77, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên Sách");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 36, 77, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Giá");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 61, 77, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Loại");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(10, 86, 77, 20);
		contentPane.add(lblNewLabel_1_2);
		
		txtMaSach = new JTextField();
		txtMaSach.setBounds(81, 10, 343, 20);
		contentPane.add(txtMaSach);
		txtMaSach.setColumns(10);
		
		txtTenSach = new JTextField();
		txtTenSach.setColumns(10);
		txtTenSach.setBounds(81, 35, 343, 20);
		contentPane.add(txtTenSach);
		
		txtGia = new JTextField();
		txtGia.setColumns(10);
		txtGia.setBounds(81, 60, 343, 20);
		contentPane.add(txtGia);
		
//		/////////////////////////////////////////////////////
		
		JComboBox<LoaiSach> comboBox = new JComboBox<>();
		comboBox.setBounds(81, 86, 343, 22);
		contentPane.add(comboBox);
		
		
		String sql = "select * from loaiSach";
		try (Connection conn = Connect.getConnection()) {
		    PreparedStatement pst = conn.prepareStatement(sql);
		    ResultSet rs = pst.executeQuery();
		    comboBox.removeAllItems();
		    while(rs.next()) {
		        String maLoai = rs.getString("maLoai");
		        String tenLoai = rs.getString("tenLoai");
		        LoaiSach ls = new LoaiSach(maLoai, tenLoai);
		        comboBox.addItem(ls);
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}

		
//		/////////////////////////////////////////////
		
//		/////////////////////////////////////////////
		
		//Thêm cột
		model = new DefaultTableModel();
		model.addColumn("Mã Sách");
		model.addColumn("Tên Sách");
		model.addColumn("Giá");
		model.addColumn("Loại");
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 159, 414, 111);
		contentPane.add(scrollPane);
		
//		/////////////////////////////////////////////
		
		scrollPane.setViewportView(table);

		loadTable();
		
		JButton btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String maSach = txtMaSach.getText();
				String tenSach = txtTenSach.getText();
				float gia;
		        try {
		            gia = Float.parseFloat(txtGia.getText());
		        } catch (NumberFormatException ex) {
		        	JOptionPane.showMessageDialog(null, "Giá phải là số!");
		            return;
		        }
				LoaiSach loai = (LoaiSach) comboBox.getSelectedItem();
				
				Sach sach = new Sach(maSach, tenSach, gia);
				sach.setLs(loai);
				
				String sql = "insert into Sach values(?, ?, ?, ?)";
				
				try (Connection conn = Connect.getConnection()) {
					
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, sach.getMaSach());
					pst.setString(2, sach.getTenSach());
					pst.setFloat(3, sach.getGia());
					pst.setString(4, loai.getMaLoai());
					pst.executeUpdate();
					loadTable();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnThem.setBounds(32, 117, 89, 23);
		contentPane.add(btnThem);
		
		JButton btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				 if (selectedRow == -1) {
				     JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa.");
				      return;
				  }
				 String maSach = model.getValueAt(selectedRow, 0).toString();
				 try(Connection conn = Connect.getConnection()){
					 String sql = "delete from sach where maSach = ?";
					 PreparedStatement pst = conn.prepareStatement(sql);
					 pst.setString(1, maSach);
					 pst.executeUpdate();
					 loadTable();
					 
				 } catch(SQLException ex) {
					 ex.printStackTrace();
				 }

			}
		});
		btnXoa.setBounds(131, 119, 89, 23);
		contentPane.add(btnXoa);
		
	}
	
	public void loadTable() {
	    DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	    dtm.setRowCount(0);

	    try (Connection conn = Connect.getConnection()) {
	        String sql = "SELECT sach.maSach, tenSach, gia, tenLoai FROM sach JOIN loaiSach ON sach.maLoai = loaiSach.maLoai";
	        PreparedStatement pst = conn.prepareStatement(sql);
	        ResultSet rs = pst.executeQuery();
	        while(rs.next()) {
	            model.addRow(new Object[] {
	                rs.getString("maSach"),
	                rs.getString("tenSach"),
	                rs.getString("Gia"),
	                rs.getString("TenLoai")
	            });
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
