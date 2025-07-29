/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Mila Oktaviani
 */
public class formTransaksi extends javax.swing.JFrame {

    private frmPelanggan1 DataPelanggan;
    private int select;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private String idpengguna = Sessi.getIdPengguna();
    private String namapengguna = Sessi.getNamaPengguna();
    java.util.Date tglsekarang = new java.util.Date();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String tanggal = format.format(tglsekarang);
    String idtransaksi, idpelanggan, idmeja,hargatotal, total, bayar, kembali, namamenu;
    String iddetailtransaksi, idmenu, jumlah, harga, totalharga,nomormeja;
    /**
     * Creates new form formTransaksi
     */
    public formTransaksi() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        txtidpengguna.setText(idpengguna);
        lbpengguna.setText(namapengguna);
        setJam();
        this.DataPelanggan = new frmPelanggan1();
        
        model = new DefaultTableModel();
        tbdetailtransaksi.setModel(model);
        model.addColumn("Id Detail Transaksi");
        model.addColumn("Id Transaksi");
        model.addColumn("Id Menu");
        model.addColumn("Nama Menu");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total Harga");
        
        model2 = new DefaultTableModel();
        tbmenu.setModel(model2);
        model2.addColumn("Id Menu");
        model2.addColumn("Nama Menu");
        model2.addColumn("Kategori");
        model2.addColumn("Harga");
        model2.addColumn("Stok");
        
        getData();
        getDataMenu();
        lbtanggal.setText(tanggal);
        combo_meja();
        cari_meja();
        nonaktif();
        idotomatis();
        loaddata();
        selectdata();
    }
    
     public void setDataPelanggan(frmPelanggan1 DataPelanggan) {
        this.DataPelanggan = DataPelanggan;
    }
     
     public final void setJam(){
         ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String nol_jam = "", nol_menit = "", nol_detik ="";
                
                java.util.Date dateTime = new java.util.Date();
                int nilai_jam = dateTime.getHours();
                int nilai_menit = dateTime.getMinutes();
                int nilai_detik = dateTime.getSeconds();
                
                if(nilai_jam <= 9) nol_jam = "0";
                if(nilai_menit <= 9) nol_menit = "0";
                if(nilai_detik <= 9) nol_detik = "0";
                
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                
                lbjam.setText(jam+":"+menit+":"+detik+"");
            }
        };
        new Timer(1000, taskPerformer).start();
    }
     
//      private void getData() {
//        model.getDataVector().removeAllElements();
//        model.fireTableDataChanged();
//        try {
//            Statement st = koneksi.koneksiDB().createStatement();
//             String sql = "SELECT tbdetailtransaksi.iddetailtransaksi,"
//                    + " tbdetailtransaksi.idtransaksi,"
//                    + "tbmenu.namamenu,"
//                    + "tbdetailtransaksi.jumlah,"
//                    + "tbdetailtransaksi.harga,"
//                    + "tbdetailtransaksi.totalharga"
//                    + " From tbdetailtransaksi INNER JOIN tbmenu ON"
//                    + " tbdetailtransaksi.idmenu = tbmenu.idmenu where tbdetailtransaksi.idtransaksi='"+idtransaksi+"'";
//            ResultSet rs = st.executeQuery(sql);
//            
//            while (rs.next()) {
//                Object[] obj = new Object[6];
//                obj[0] = rs.getString("iddetailtransaksi");
//                obj[1] = rs.getString("idtransaksi");
//                obj[2] = rs.getString("namamenu");
//                obj[3] = rs.getString("jumlah");
//                obj[4] = rs.getString("harga");
//                obj[5] = rs.getString("totalharga");
//                
//                model.addRow(obj);
//            }
//        }catch(SQLException error) {
//            JOptionPane.showMessageDialog(null, error.getMessage());
//        }
//    }
     
     private void getData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "SELECT * FROM tbdetailtransaksi WHERE idtransaksi NOT IN (SELECT idtransaksi FROM tbtransaksi);";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[7];
                obj[0] = rs.getString("iddetailtransaksi");
                obj[1] = rs.getString("idtransaksi");
                obj[2] = rs.getString("idmenu");
                obj[3] = rs.getString("namamenu");
                obj[4] = rs.getString("jumlah");
                obj[5] = rs.getString("harga");
                obj[6] = rs.getString("totalharga");
                
                model.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
      
      private void getDataMenu() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "SELECT * FROM tbmenu where stok > 0";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[5];
                obj[0] = rs.getString("idmenu");
                obj[1] = rs.getString("namamenu");
                obj[2] = rs.getString("kategori");
                obj[3] = rs.getString("harga");
                obj[4] = rs.getString("stok");
                
                
                model2.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
      
      public void combo_meja() {
        try {
         Statement st = koneksi.koneksiDB().createStatement();
         String sql = "select idmeja, nomormeja, status from tbmeja where status = 'kosong' order by idmeja asc";
         ResultSet rs = st.executeQuery(sql);
         
         while (rs.next()) {
             String nama = rs.getString("nomormeja");
             cbnomeja.addItem(nama);
         }
         rs.close();
                
    } catch (Exception e) {
            System.out.println(e.getMessage());
    }
}
      
       
       public void cari_meja() {
           try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "select idmeja from tbmeja where nomormeja='"+cbnomeja.getSelectedItem()+"'and status = 'kosong'";
            ResultSet rs = st.executeQuery(sql);
             
             while (rs.next()) {
               this.txtidmeja.setText(rs.getString("idmeja"));
               
           }
            rs.close(); 
            st.close();
               
           } catch(Exception e) {
               System.out.println(e.getMessage());
           }
           
       }
       
       public void nonaktif() {
        txtidtransaksi.setEnabled(false);
        txtidpengguna.setEnabled(false);
        txtidpengguna.setEnabled(false);
        txtidpelanggan.setEnabled(false);
        txtidmeja.setEnabled(false);
        cbnomeja.setEnabled(false);
        txtnamamenu.setEnabled(false);
        txtjumlah.setEnabled(false);
        txtharga.setEnabled(false);
        txttotal.setEnabled(false);
        txtbayar.setEnabled(false);
        txtkembali.setEnabled(false);
        txthargatotal.setEnabled(false);
        btncaripelanggan.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
        btnproses.setEnabled(true);
        btncetak.setEnabled(false);
        btnselesai.setEnabled(true);
        btnadd.setEnabled(false);
        btnmin.setEnabled(false);
        tbmenu.setEnabled(false);
        
    }
       public void aktif() {
        txtidpengguna.setEnabled(false);
        txtidpengguna.setEnabled(false);
        txtidpelanggan.setEnabled(false);
        txtidmeja.setEnabled(false);
        cbnomeja.setEnabled(true);
        txtnamamenu.setEnabled(false);
        txtharga.setEnabled(false);
        txttotal.setEnabled(false);
        txtkembali.setEnabled(false);
        txtjumlah.setEnabled(true);
        btncaripelanggan.setEnabled(true);
        btnproses.setEnabled(true);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btncetak.setEnabled(true);
        btnselesai.setEnabled(true);
        tbmenu.setEnabled(true);
        txtstok.setEnabled(false);
    }
       
        public void idotomatis() {
        try {
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "select * from tbdetailtransaksi order by idtransaksi desc";
            ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                String id = rs.getString("idtransaksi").substring(3);
                String AN = "" + (Integer.parseInt(id) + 1);
                String nol = "";
                    
                if (AN.length() == 1) {
                    nol = "000";
                } else if (AN.length() == 2) {
                    nol = "00";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                txtidtransaksi.setText("TR" + nol + AN);
            } else {
                txtidtransaksi.setText("TR0001");
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
        
        public void loaddata() {
        idtransaksi = txtidtransaksi.getText();
        idmenu = txtidmenu.getText();
        namamenu = txtnamamenu.getText();
        jumlah = txtjumlah.getText();
        harga = txtharga.getText();
        totalharga = txttotal.getText();
    }
        
        public void loaddatatransaksi() {
            idtransaksi = txtidtransaksi.getText();
            tanggal = tanggal;
            idpengguna = txtidpengguna.getText();
            idpelanggan = txtidpelanggan.getText();
            idmeja = txtidmeja.getText();
            hargatotal = txthargatotal.getText();
            total = txttotal.getText();
            bayar = txtbayar.getText();
            kembali = txtkembali.getText();
        }
         
        public void simpandata() {
        loaddata();
        if (!updatestok()) {
            txtstok.setText("");
            txtjumlah.setText("");
            txtjumlah.setEnabled(false);
            txtnamamenu.setText("");
            txtharga.setText("");
            txttotal.setText("");
            tbmenu.setEnabled(true);
            tbmenu.clearSelection();
            btnselesai.setEnabled(true);
            btnadd.setEnabled(true);
            return;           
        }
        try (Connection conn = koneksi.koneksiDB()) {
           
           String sql = "INSERT INTO tbdetailtransaksi (iddetailtransaksi, idtransaksi, idmenu, namamenu, jumlah, harga, totalharga) VALUES (?, ?, ?, ?, ?, ?, ?)";          
           PreparedStatement p = conn.prepareStatement(sql);
           p.setString(1, iddetailtransaksi);
           p.setString(2, idtransaksi);
           p.setString(3, idmenu);
           p.setString(4, namamenu);
           p.setString(5, jumlah);
           p.setString(6, harga);
           p.setString(7, totalharga);
           p.executeUpdate();
           
           getData();           
           getDataMenu();
           idotomatis();
           nonaktif();
           txtidmenu.setText("");
           txtnamamenu.setText("");
           txtharga.setText("");
           txttotal.setText("");
           txtstok.setText("");
           txtjumlah.setText("");
           JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
           
        } catch (SQLException x){
            JOptionPane.showMessageDialog(null, x.getMessage());
        }
    }
          
        public void simpandatatransaksi() {
            loaddata();
            loaddatatransaksi();
            try (Connection conn = koneksi.koneksiDB()) {
               String sql = "INSERT INTO tbtransaksi (idtransaksi, tanggal, idpengguna, idpelanggan, idmeja, idmenu, namamenu, jumlah, hargatotal, total, bayar, kembali) " +
                             "SELECT idtransaksi, ?, ?, ?, ?, idmenu, namamenu, jumlah, harga, totalharga, ?, ? FROM tbdetailtransaksi WHERE idtransaksi NOT IN (SELECT idtransaksi FROM tbtransaksi)";

                PreparedStatement p = conn.prepareStatement(sql);
                p.setString(1, tanggal);
                p.setString(2, idpengguna);
                p.setString(3, idpelanggan);
                p.setString(4, idmeja);
                p.setString(5, bayar);
                p.setString(6, kembali);
                p.executeUpdate();  
                getData();
                idotomatis();
                resetForm();
                nonaktif();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            } catch (SQLException x) {
                JOptionPane.showMessageDialog(null, x.getMessage());
            }
        }
          
        
            public void selectdata() {
        int i = tbdetailtransaksi.getSelectedRow();
        if (i == -1) {
            //tidak ada baris yang di pilih
            return;
        }
        txtstok.setText(""+model.getValueAt(i, 0));
        txtidpelanggan.setText(""+model.getValueAt(i, 1));
        txtidmeja.setText(""+model.getValueAt(i, 2));
        txtharga.setText(""+model.getValueAt(i, 3));
        
        
    }
     
    public void selectdatamenu() {
         int i = tbmenu.getSelectedRow();
        if (i != -1) {
            txtidmenu.setText(""+model2.getValueAt(i, 0));
            txtnamamenu.setText(""+model2.getValueAt(i, 1));
            txtharga.setText(""+model2.getValueAt(i, 3));
            txtstok.setText(""+model2.getValueAt(i, 4));
            tbmenu.setEnabled(false);
        }

        
    }
    
     
     
     public void hitungpembayaran() {
        try {
            int totalharga = Integer.parseInt(txthargatotal.getText());
            int pembayaran = Integer.parseInt(txtbayar.getText());
            int totalPembayaran = pembayaran - totalharga;

            txtkembali.setText(String.valueOf(totalPembayaran)); 
        }catch (NumberFormatException e) {
            txtkembali.setText("");
        }
        
    }
     
      public void ubahdata() {
        loaddata();
        loaddatatransaksi();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date tanggal = new Date();
        String jual_tgl = dateFormat.format(tanggal);
       
        try {
             Connection conn = koneksi.koneksiDB();
             Statement stat = conn.createStatement();
             String sql="UPDATE tbtransaksi set tanggal='"+jual_tgl+"',"+"idpengguna='"+idpengguna+"',"+"idpelanggan='"+idpelanggan+"',"+"idmeja='"+idmeja+"',"+"total='"+hargatotal+"',"+"bayar='"+bayar+"',"+"kembali='"+kembali+"' where idtransaksi='"+idtransaksi+"'";
             System.out.println(sql);
             PreparedStatement p = (PreparedStatement)koneksi.koneksiDB().prepareStatement(sql);
             p.executeUpdate();
             getData();
             resetForm();
             selectdata();
             JOptionPane.showMessageDialog(null, "Data berhasil diubah");    
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
      
      private void resetForm() {
        txtidtransaksi.setText("");
        txtidpelanggan.setText("");
        txtidmeja.setText("");
        cbnomeja.setSelectedIndex(0);
        txtnamamenu.setText("");
        txtjumlah.setText("");
        txtharga.setText("");
        txttotal.setText("");
        txtbayar.setText("");
        txtkembali.setText("");
        txthargatotal.setText("");
    }
      
      public void selectpelanggan() {
        select = DataPelanggan.select();
        DefaultTableModel pelanggan = DataPelanggan.getTableModel(); 

        txtidpelanggan.setText("" + pelanggan.getValueAt(select, 0));
    }
      
   public boolean updatestok() {
        String jumlahStr = txtjumlah.getText();
        int stok = Integer.parseInt(txtstok.getText());

        if (jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Jumlah Harus Diisi!");
            return false;
        }

        try {
            int jumlah = Integer.parseInt(jumlahStr);

            if (jumlah > stok) {
                JOptionPane.showMessageDialog(null, "Stok Tidak cukup!");
                return false;
            } else {
                Connection conn = koneksi.koneksiDB();
                String sql = "UPDATE tbmenu SET stok = stok - ? WHERE idmenu = ?";
                PreparedStatement p = conn.prepareStatement(sql);
                p.setInt(1, jumlah);
                p.setString(2, idmenu);
                p.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        } catch (NumberFormatException e) {

            return false;
        }
    }

      
      public void update_meja() {
        try{
            Connection conn=koneksi.koneksiDB();
            java.sql.Statement st = conn.createStatement();
            String mj = "UPDATE tblmeja set status = 'isi' where idmeja = '"+idmeja+"'";
            PreparedStatement p=(PreparedStatement) koneksi.koneksiDB(). prepareStatement(mj);
            p.executeUpdate();
            getData();
        } catch (Exception e) {
            System.out.println("Terjadi Kesalahan");
        }
    }
      
      public void cetakstruk() {
         try {
            String file = "/src/struk.jasper";
            
            Connection conn = koneksi.koneksiDB();
            HashMap parameter = new HashMap();
            
            parameter.put("tanggal",tanggal);
            parameter.put("pengguna",lbpengguna.getText());
            parameter.put("pelanggan",txtidpelanggan.getText());
            parameter.put("jam",lbjam.getText());
            parameter.put("hargatotal",txthargatotal.getText());
            parameter.put("bayar",txtbayar.getText());
            parameter.put("kembali",txtkembali.getText());
            
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), parameter, conn);
            JasperViewer.viewReport(print, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
      }
      
      // Method untuk mengosongkan tabel detail transaksi
public void clearDetailTransaksiTable() {
    // Mendapatkan model tabel detail transaksi
    DefaultTableModel model = (DefaultTableModel) tbdetailtransaksi.getModel();

    // Menghapus semua baris dalam tabel
    model.setRowCount(0);

    // Mengatur nilai total harga menjadi 0
    txthargatotal.setText("0");
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbtanggal = new javax.swing.JLabel();
        lbjam = new javax.swing.JLabel();
        txtidpengguna = new javax.swing.JTextField();
        lbpengguna = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbnomeja = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnadd = new javax.swing.JButton();
        btnmin = new javax.swing.JButton();
        btnproses = new javax.swing.JButton();
        btnselesai = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbdetailtransaksi = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbmenu = new javax.swing.JTable();
        txtidpelanggan = new javax.swing.JTextField();
        txtidtransaksi = new javax.swing.JTextField();
        txtidmeja = new javax.swing.JTextField();
        btncaripelanggan = new javax.swing.JButton();
        txtnamamenu = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txttotal = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        txthargatotal = new javax.swing.JTextField();
        txtbayar = new javax.swing.JTextField();
        txtkembali = new javax.swing.JTextField();
        btncetak = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtidmenu = new javax.swing.JTextField();
        txtstok = new javax.swing.JTextField();
        btnsimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 204), 3));

        jLabel1.setFont(new java.awt.Font("Elephant", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Form Transaksi");

        lbtanggal.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbtanggal.setText("Tanggal");

        lbjam.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbjam.setText("Jam");

        lbpengguna.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbpengguna.setText("Pengguna");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(lbpengguna))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(txtidpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(223, 223, 223)
                .addComponent(lbtanggal)
                .addGap(129, 129, 129)
                .addComponent(lbjam)
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(txtidpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbpengguna)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbtanggal)
                    .addComponent(lbjam))
                .addGap(25, 25, 25))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1230, 110));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Id Transaksi");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Id Pelanggan");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("No Meja");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setText("Jumlah");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, -1, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Harga");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 210, -1, -1));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("Nama Menu");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, -1, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel18.setText("Total Bayar");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 460, -1, -1));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel19.setText("Bayar");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 500, -1, -1));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel20.setText("Kembali");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 540, -1, -1));

        cbnomeja.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbnomejaItemStateChanged(evt);
            }
        });
        cbnomeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbnomejaActionPerformed(evt);
            }
        });
        getContentPane().add(cbnomeja, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 316, 170, 30));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("Id Meja");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel21.setText("Total");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 250, -1, -1));

        btnadd.setText("+");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });
        getContentPane().add(btnadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 130, 70, 40));

        btnmin.setText("-");
        btnmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnminActionPerformed(evt);
            }
        });
        getContentPane().add(btnmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 130, 70, 40));

        btnproses.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnproses.setText("Proses");
        btnproses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprosesActionPerformed(evt);
            }
        });
        getContentPane().add(btnproses, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 250, -1, -1));

        btnselesai.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnselesai.setText("Selesai");
        btnselesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnselesaiActionPerformed(evt);
            }
        });
        getContentPane().add(btnselesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 250, -1, -1));

        tbdetailtransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tbdetailtransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbdetailtransaksiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbdetailtransaksi);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 310, 530, 120));

        tbmenu.setModel(new javax.swing.table.DefaultTableModel(
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
        tbmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbmenuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbmenu);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 620, 190));
        getContentPane().add(txtidpelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 170, 40));
        getContentPane().add(txtidtransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 170, 40));

        txtidmeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidmejaActionPerformed(evt);
            }
        });
        getContentPane().add(txtidmeja, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, 170, 40));

        btncaripelanggan.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btncaripelanggan.setText("Cari");
        btncaripelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncaripelangganActionPerformed(evt);
            }
        });
        getContentPane().add(btncaripelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 90, 40));
        getContentPane().add(txtnamamenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 130, 150, -1));

        txtjumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtjumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtjumlahKeyTyped(evt);
            }
        });
        getContentPane().add(txtjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 170, 150, -1));

        txtharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargaActionPerformed(evt);
            }
        });
        getContentPane().add(txtharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 210, 150, -1));
        getContentPane().add(txttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 250, 150, -1));

        btntambah.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btntambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-30.png"))); // NOI18N
        btntambah.setText("Tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });
        getContentPane().add(btntambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, -1, -1));

        btnedit.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-edit-30.png"))); // NOI18N
        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });
        getContentPane().add(btnedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 580, 100, -1));

        btnhapus.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-30.png"))); // NOI18N
        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });
        getContentPane().add(btnhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 580, -1, -1));

        txthargatotal.setText("0");
        txthargatotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargatotalActionPerformed(evt);
            }
        });
        getContentPane().add(txthargatotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 460, 170, -1));

        txtbayar.setText("0");
        txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarActionPerformed(evt);
            }
        });
        txtbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbayarKeyReleased(evt);
            }
        });
        getContentPane().add(txtbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 500, 170, -1));

        txtkembali.setText("0");
        getContentPane().add(txtkembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 540, 170, -1));

        btncetak.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btncetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-print-30.png"))); // NOI18N
        btncetak.setText("Cetak");
        btncetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakActionPerformed(evt);
            }
        });
        getContentPane().add(btncetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 670, 120, 50));

        btnkeluar.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-exit-30.png"))); // NOI18N
        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });
        getContentPane().add(btnkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 670, 110, 50));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("id menu");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, -1, -1));

        txtidmenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidmenuKeyReleased(evt);
            }
        });
        getContentPane().add(txtidmenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, 150, -1));

        txtstok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstokActionPerformed(evt);
            }
        });
        getContentPane().add(txtstok, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 180, 90, -1));

        btnsimpan.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-save-30.png"))); // NOI18N
        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });
        getContentPane().add(btnsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 670, 120, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbnomejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbnomejaActionPerformed
       cari_meja();   // TODO add your handling code here:
    }//GEN-LAST:event_cbnomejaActionPerformed

    private void tbdetailtransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbdetailtransaksiMouseClicked
        selectdata();
        nonaktif();
        btntambah.setEnabled(false);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btncetak.setEnabled(true);
        btnproses.setEnabled(false);
        btnselesai.setEnabled(false);
        tbmenu.setEnabled(false);
        btncaripelanggan.setEnabled(false);
        txthargatotal.setEnabled(false);
        txtbayar.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_tbdetailtransaksiMouseClicked

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        aktif();
        idotomatis();
        selectdata();
        btntambah.setEnabled(false);
        btncetak.setEnabled(false);
        btnhapus.setEnabled(false);
        btnedit.setEnabled(false); 
        btnselesai.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_btntambahActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        ubahdata();
        nonaktif();
        btntambah.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        nonaktif();
        btntambah.setEnabled(true);
        loaddata();
        int pesan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini? " + idtransaksi, "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
            try {
                Connection conn = koneksi.koneksiDB();
                String sql = "DELETE FROM tbdetailtransaksi WHERE idtransaksi = ?";
                PreparedStatement p = conn.prepareStatement(sql);
                p.setString(1, idtransaksi);
                p.executeUpdate();
                getData();
                resetForm();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        int pesan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin keluar? ", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
            new frmMenuUtama().setVisible(true);
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void tbmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbmenuMouseClicked
         selectdatamenu();   
         txtjumlah.setEnabled(true);
         txtjumlah.requestFocus();
         btnproses.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_tbmenuMouseClicked

    private void btncaripelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncaripelangganActionPerformed
        new frmPelanggan1().setVisible(true);
        this.setVisible(false);
        selectpelanggan();        // TODO add your handling code here:
    }//GEN-LAST:event_btncaripelangganActionPerformed

    private void txtbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbayarKeyReleased
        hitungpembayaran();        // TODO add your handling code here:
    }//GEN-LAST:event_txtbayarKeyReleased

    private void btnprosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprosesActionPerformed
//        nonaktif();
        simpandata();
//        updatestok();
//        update_meja();
        btnadd.setEnabled(true);
        btnhapus.setEnabled(true);
        btnselesai.setEnabled(true);
        
       
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnprosesActionPerformed

    private void btnselesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnselesaiActionPerformed
//       int total = 0;
//        for (int i =0; i< tbdetailtransaksi.getRowCount(); i++){
//        int amount = Integer.parseInt((String)tbdetailtransaksi.getValueAt(i, 5));
//        total += amount;
//    }
//        txthargatotal.setText(""+total);
//        txtbayar.setEnabled(true);
//        txtbayar.setText("");
//        txtbayar.requestFocus();
//        btncetak.setEnabled(true);

        nonaktif();
        txtbayar.setEnabled(true);
        try {
             Connection conn = koneksi.koneksiDB();
             Statement stat = conn.createStatement();
             String sql = "SELECT SUM(harga * jumlah) AS totalharga FROM tbdetailtransaksi WHERE idtransaksi NOT IN (SELECT idtransaksi FROM tbtransaksi)";
             ResultSet rs = stat.executeQuery(sql);
             
             if (rs.next()) {
                  totalharga = rs.getString("totalharga");
                  txthargatotal.setText(String.valueOf(totalharga)); 
             }
             
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
           
// TODO add your handling code here:
    }//GEN-LAST:event_btnselesaiActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed

        getData();
        idotomatis();
        tbmenu.setEnabled(true);
        txtjumlah.setEnabled(true);
        btnselesai.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnminActionPerformed
         tbmenu.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnminActionPerformed

    private void txtjumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahKeyReleased
        try {
            int harga, jumlah,totalharga;
            harga = Integer.parseInt(txtharga.getText());
            jumlah = Integer.parseInt(txtjumlah.getText());
            totalharga = jumlah * harga;
            txttotal.setText("" + totalharga);
        } catch (Exception e){
            
        }
        
            // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahKeyReleased

    private void txtidmenuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidmenuKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidmenuKeyReleased

    private void txtjumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahKeyTyped
            String input=txtjumlah.getText();
            if(input.length()>6){
                txthargatotal.requestFocus();
            }
            
            char karakter=evt.getKeyChar();
            if(!(((karakter>='0')&&(karakter<'9')||(karakter==KeyEvent.VK_BACKSPACE)||(karakter==KeyEvent.VK_DELETE)))){
                getToolkit().beep();
                evt.consume();
            }
            // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahKeyTyped

    private void txthargatotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargatotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargatotalActionPerformed

    private void txtidmejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidmejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidmejaActionPerformed

    private void txtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbayarActionPerformed

    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        ubahdata();
        nonaktif();
        cetakstruk();
        
        cbnomeja.setEnabled(false);
        cbnomeja.setSelectedIndex(0);
        resetForm();// TODO add your handling code here:
    }//GEN-LAST:event_btncetakActionPerformed

    private void txtstokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstokActionPerformed

    private void txthargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargaActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
//        try{
//            HashMap parameters = new HashMap();
//            String a = txtidtransaksi.getText();
//            parameters.put("idtransaksi", a);
//            
//            String namafile = "src/Aplikasi/struk.jasper";
//            File Report = new File(namafile);
//            JasperReport jreprt;
//            jreprt = (JasperReport) JRLoader.loadObject(Report);
//            JasperPrint jprint =JasperFillManager.fillReport(jreprt, parameters,koneksi.koneksiDB());
//            
//            JasperViewer.viewReport(jprint,false);
//        }catch (Exception e) {
//            JOptionPane.showMessageDialog(this,"Laporan Gagal"+ e);
//        }
        
        try {
            String file = "/Aplikasi/struk.jasper";
            
            Connection conn = koneksi.koneksiDB();
            HashMap parameter = new HashMap();
            
            parameter.put("tanggal",tanggal);
            parameter.put("pengguna",lbpengguna.getText());
            parameter.put("pelanggan",txtidpelanggan.getText());
            parameter.put("jam",lbjam.getText());
            parameter.put("hargatotal",txthargatotal.getText());
            parameter.put("bayar",txtbayar.getText());
            parameter.put("kembali",txtkembali.getText());
            
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), parameter, conn);
            JasperViewer.viewReport(print, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

        simpandatatransaksi();
        nonaktif();
        btntambah.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void cbnomejaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbnomejaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbnomejaItemStateChanged

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
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    public javax.swing.JButton btncaripelanggan;
    private javax.swing.JButton btncetak;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnmin;
    private javax.swing.JButton btnproses;
    private javax.swing.JButton btnselesai;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    public javax.swing.JComboBox<String> cbnomeja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbjam;
    private javax.swing.JLabel lbpengguna;
    private javax.swing.JLabel lbtanggal;
    private javax.swing.JTable tbdetailtransaksi;
    public javax.swing.JTable tbmenu;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txthargatotal;
    private javax.swing.JTextField txtidmeja;
    private javax.swing.JTextField txtidmenu;
    public javax.swing.JTextField txtidpelanggan;
    private javax.swing.JTextField txtidpengguna;
    private javax.swing.JTextField txtidtransaksi;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtkembali;
    private javax.swing.JTextField txtnamamenu;
    private javax.swing.JTextField txtstok;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
