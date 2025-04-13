package De2;

import java.awt.EventQueue;
import java.awt.Font;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class De2Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtHoTen;
	private JTextField txtDTB;
	private JTable table;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					De2Frame frame = new De2Frame();
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
	public De2Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHTn = new JLabel("Họ tên");
		lblHTn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHTn.setBounds(10, 4, 77, 19);
		contentPane.add(lblHTn);
		
		txtHoTen = new JTextField();
		txtHoTen.setColumns(10);
		txtHoTen.setBounds(81, 3, 343, 20);
		contentPane.add(txtHoTen);
		
		JLabel lblNewLabel_1 = new JLabel("DTB");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 34, 77, 14);
		contentPane.add(lblNewLabel_1);
		
		txtDTB = new JTextField();
		txtDTB.setColumns(10);
		txtDTB.setBounds(81, 33, 343, 20);
		contentPane.add(txtDTB);
		
		JLabel lblNewLabel_1_2 = new JLabel("Lớp");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(10, 59, 77, 20);
		contentPane.add(lblNewLabel_1_2);
		
		JComboBox<Lop> comboBox = new JComboBox<>();
		
		comboBox.setBounds(81, 59, 343, 22);
		contentPane.add(comboBox);
		
		try(Connection conn = Connect.getConnection()){
			String sql = "select * from Lop";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			comboBox.removeAllItems();
			while(rs.next()) {
				int maLop = Integer.parseInt(rs.getString("maLop"));
				String tenLop = rs.getString("tenLop");
				Lop lop = new Lop(maLop, tenLop);
				comboBox.addItem(lop);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		dtm = new DefaultTableModel();
		dtm.addColumn("MSSV");
		dtm.addColumn("Họ tên");
		dtm.addColumn("DTB");
		dtm.addColumn("Lớp");
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        if (row >= 0) {
		            txtHoTen.setText(table.getValueAt(row, 1).toString());
		            txtDTB.setText(table.getValueAt(row, 2).toString());

		            String tenLop = table.getValueAt(row, 3).toString();
		            for (int i = 0; i < comboBox.getItemCount(); i++) {
		                String item = comboBox.getItemAt(i).toString();
		                if (item.equals(tenLop)) {
		                	comboBox.setSelectedIndex(i);
		                    break;
		                }
		            }
		        }
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 149, 414, 111);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		
		loadTable();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lop lop = (Lop) comboBox.getSelectedItem();
				loadTableThemLop(lop.getMaLop());
			}
		});
		JButton btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String hoTen = txtHoTen.getText();
				float DTB;
				try {
					 DTB = Float.parseFloat(txtDTB.getText());
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "DTB phải là số!");
					return;
				}
				Lop lop = (Lop) comboBox.getSelectedItem();
				SinhVien sv = new SinhVien(0, hoTen, DTB);
				sv.setLop(lop);
				
				try(Connection conn = Connect.getConnection()){
					String sql = "insert into SinhVien(hoTen, DTB, maLop) values(?, ?, ?)";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, sv.getHoTen());
					pst.setFloat(2, sv.getDTB());
					pst.setInt(3, lop.getMaLop());
					pst.executeUpdate();
					loadTable();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnThem.setBounds(32, 107, 89, 23);
		contentPane.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnSua.setBounds(131, 109, 89, 23);
		contentPane.add(btnSua);
		
		
		JButton btnXoa_1 = new JButton("Xóa");
		btnXoa_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if(selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xóa!");
					return;
				}
				String MaSV = dtm.getValueAt(selectedRow, 0).toString();
				try(Connection conn = Connect.getConnection()){
					String sql = "delete from SinhVien where MaSV = ?";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, MaSV);
					pst.executeUpdate();
					loadTable();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnXoa_1.setBounds(230, 109, 89, 23);
		contentPane.add(btnXoa_1);
	}
	public void loadTable() {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		
		try(Connection conn = Connect.getConnection()) {
			String sql = "select MaSV, hoTen, DTB, tenLop from SinhVien join Lop on SinhVien.maLop = Lop.maLop";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString("MaSV"),
						rs.getString("hoTen"),
						rs.getString("DTB"),
						rs.getString("TenLop")
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTableThemLop(int MaLop) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		
		try(Connection conn = Connect.getConnection()) {
			String sql = "select MaSV, hoTen, DTB, tenLop from SinhVien join Lop on SinhVien.maLop = Lop.maLop where SinhVien.maLop = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, MaLop);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dtm.addRow(new Object[] {
						rs.getString("MaSV"),
						rs.getString("hoTen"),
						rs.getString("DTB"),
						rs.getString("TenLop")
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
