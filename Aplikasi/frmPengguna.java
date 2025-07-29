/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class frmPengguna extends javax.swing.JFrame {

    private DefaultTableModel model;
    private DefaultTableModel model2;
    String idpengguna, idpegawai, namapengguna, username, password, level;
    
    public frmPengguna() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        model = new DefaultTableModel();
        tblpengguna.setModel(model);
        model.addColumn("Id Pengguna");
        model.addColumn("Id Pegawai");
        model.addColumn("Nama Pengguna");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Level");
        
        model2 = new DefaultTableModel();
        tblpegawai.setModel(model2);
        model2.addColumn("Id Pegawai");
        model2.addColumn("Nama Pegawai");
        model2.addColumn("Jenis Pekerjaan");
        
        GetData();
        getDataPegawai();
        nonaktif();
        idotomatis();
        loaddata();
        selectdata();

    }

    //Langkah Kedua Menampilkan data kedalam tabel
    public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "SELECT * FROM tbpengguna";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("idpengguna");
                obj[1] = rs.getString("idpegawai");
                obj[2] = rs.getString("namapengguna");
                obj[3] = rs.getString("username");
                obj[4] = rs.getString("password");
                obj[5] = rs.getString("level");
                
                model.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
      private void getDataPegawai() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try {
            Statement st = (Statement)koneksi.koneksiDB().createStatement();
            String sql ="select * from tbpegawai";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[3];
                obj[0] = rs.getString("idpegawai");
                obj[1] = rs.getString("namapegawai");
                obj[2] = rs.getString("jenispekerjaan");
                
                model2.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void nonaktif() {
        txtidpengguna.setEnabled(false);
        txtidpegawai.setEnabled(false);
        txtnamapengguna.setEnabled(false);
        txtusername.setEnabled(false);
        txtpassword.setEnabled(false);
        cblevel.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
        btnsimpan.setEnabled(false);
        btnkeluar.setEnabled(true);
        tblpegawai.setEnabled(false);
        txtcari.setEnabled(false);
    }
    
    public void aktif() {
        txtidpengguna.setEnabled(false);
        txtidpegawai.setEnabled(false);
        txtnamapengguna.setEnabled(false);
        txtusername.setEnabled(true);
        txtpassword.setEnabled(true);
        cblevel.setEnabled(true);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btnsimpan.setEnabled(true);
        btnkeluar.setEnabled(true);
        txtcari.setEnabled(true);
    }
    
    public void idotomatis() {
        try {
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM tbpengguna order by idpengguna desc";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()) {
                String id = rs.getString("idpengguna");
                if (id.length() >= 4) {
                    String AN = "" + (Integer.parseInt(id.substring(3))+1);
                    String nol = "";
                    
                    if (AN.length()==1)
                        {nol="000";}
                    else if (AN.length()==2)
                        {nol="00";}
                    else if (AN.length() ==3) 
                        {nol="";}
                    txtidpengguna.setText("PG"+nol+AN);
                }else {
                txtidpengguna.setText("PG0001");
            }                    
            }
        }catch (Exception error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
   
    
    public void loaddata() {
        idpengguna = txtidpengguna.getText();
        idpegawai = txtidpegawai.getText();
        namapengguna = txtnamapengguna.getText();
        username = txtusername.getText();
        password = txtpassword.getText();
        level = (String) cblevel.getSelectedItem();
    }
    
    public void simpandata() {
        loaddata();
        try {
           Connection conn = koneksi.koneksiDB();
           Statement stat = conn.createStatement();
           String sql="INSERT INTO tbpengguna(idpengguna,idpegawai,namapengguna,username,password,level)"+"values"+"('"+idpengguna+"',"
                       + "'"+idpegawai+"','"+namapengguna+"','"+username+"','"+password+"','"+level+"')";
           PreparedStatement p = (PreparedStatement)koneksi.koneksiDB().prepareStatement(sql);
           p.executeUpdate();
           GetData();
           nonaktif();
           kosongkan();
           JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
           
        } catch (SQLException x){
            JOptionPane.showMessageDialog(null, x.getMessage());
        }
    }
    
    public void selectdata(){
        int i = tblpengguna.getSelectedRow();
        if (i== -1){
            return;
        }
        txtidpengguna.setText(""+model.getValueAt(i, 0));
        txtidpegawai.setText(""+model.getValueAt(i, 1));
        txtnamapengguna.setText(""+model.getValueAt(i, 2));
        txtusername.setText(""+model.getValueAt(i, 3));
        txtpassword.setText(""+model.getValueAt(i, 4));
        cblevel.setSelectedItem(""+model.getValueAt(i, 5));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
    }
    
    public void selectdataPegawai() {
        int i = tblpegawai.getSelectedRow();
        if (i == -1) {
            return;
        }
        txtidpegawai.setText(""+model2.getValueAt(i, 0));
        txtnamapengguna.setText(""+model2.getValueAt(i, 1));
    }
    
    public void ubahdata(){
        loaddata();
        try{
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "Update tbpengguna set idpegawai='"+idpegawai+"',"+"namapengguna='"+namapengguna+"',"+"username='"+username+"',"+"password='"+password+"',"+"level='"+level+"' WHERE idpengguna='"+idpengguna+"'";
            PreparedStatement p=(PreparedStatement) koneksi.koneksiDB().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                selectdata();
                 
                JOptionPane.showMessageDialog(null, "Data Berhasil Dirubah");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
     

     
    public void kosongkan(){
        txtidpengguna.setText("");
        txtidpegawai.setText("");
        txtnamapengguna.setText("");
        txtusername.setText("");
        txtpassword.setText("");
        cblevel.setSelectedItem("");
    }
    
     private void cari() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "SELECT * FROM tbpegawai where namapegawai like '%" + txtcari.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[3];
                obj[0] = rs.getString("idpegawai");
                obj[1] = rs.getString("namapegawai");
                obj[2] = rs.getString("jenispekerjaan");
                
                model2.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtidpengguna = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtidpegawai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btntambah = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblpengguna = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtnamapengguna = new javax.swing.JTextField();
        cblevel = new javax.swing.JComboBox<>();
        txtpassword = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpegawai = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 204), 3));

        jLabel1.setFont(new java.awt.Font("Elephant", 0, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Form Pengguna");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(395, 395, 395))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Id Pengguna");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Id Pegawai");

        txtidpegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpegawaiActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Nama Pengguna");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Username");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Password");

        btntambah.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btntambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-30.png"))); // NOI18N
        btntambah.setText("Tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        btnsimpan.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-save-30.png"))); // NOI18N
        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnedit.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-edit-30.png"))); // NOI18N
        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        btnhapus.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-30.png"))); // NOI18N
        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-exit-30.png"))); // NOI18N
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        tblpengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblpengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpenggunaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblpengguna);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Level");

        cblevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Manager", "Admin", "Waiters", "Kasir" }));

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 204)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );

        jLabel8.setText("Cari Pegawai:");

        tblpegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpegawaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpegawai);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btntambah)
                        .addGap(54, 54, 54)
                        .addComponent(btnsimpan)
                        .addGap(43, 43, 43)
                        .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnhapus)
                        .addGap(27, 422, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtidpengguna, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                                    .addComponent(txtidpegawai)
                                    .addComponent(txtusername)
                                    .addComponent(txtnamapengguna)
                                    .addComponent(txtpassword)
                                    .addComponent(cblevel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(143, 143, 143)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtidpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtidpegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtnamapengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(cblevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btnsimpan)
                    .addComponent(btnedit)
                    .addComponent(btnhapus))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        aktif();
        idotomatis();  
        btnhapus.setEnabled(false);
        btnedit.setEnabled(false);
        btntambah.setEnabled(false);
        selectdata();
        tblpegawai.setEnabled(true);
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        simpandata(); 
        nonaktif();
        btntambah.setEnabled(true);
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        ubahdata();
        nonaktif();
        btntambah.setEnabled(true);
    }//GEN-LAST:event_btneditActionPerformed

    private void tblpenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpenggunaMouseClicked
        selectdata();
        aktif();
        btntambah.setEnabled(false);
        btnsimpan.setEnabled(false);
    }//GEN-LAST:event_tblpenggunaMouseClicked

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        nonaktif();
        btntambah.setEnabled(true);
        loaddata();
        int pesan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini? " + idpengguna, "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
            try {
                Connection conn = koneksi.koneksiDB();
                String sql = "DELETE FROM tbpengguna WHERE idpengguna = ?";
                PreparedStatement p = conn.prepareStatement(sql);
                p.setString(1, idpengguna);
                p.executeUpdate();
                GetData();

                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
        kosongkan();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        new frmMenuUtama().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void txtidpegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidpegawaiActionPerformed

    private void tblpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpegawaiMouseClicked
        // TODO add your handling code here:
        selectdataPegawai();
    }//GEN-LAST:event_tblpegawaiMouseClicked

    /** 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPengguna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JComboBox<String> cblevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblpegawai;
    private javax.swing.JTable tblpengguna;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtidpegawai;
    private javax.swing.JTextField txtidpengguna;
    private javax.swing.JTextField txtnamapengguna;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
