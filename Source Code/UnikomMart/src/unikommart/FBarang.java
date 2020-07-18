/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unikommart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Exception
 */
public class FBarang extends javax.swing.JFrame {
    DefaultTableModel tab,tab2;
    Connection conn;
    String kodeS="";
    boolean temp = true,temp1=true;

    /**
     * Creates new form FBarang
     */
    public FBarang() {
        initComponents();
        Scroll.setVisible(false);
        this.setLocationRelativeTo(null);
        after();
        conn=koneksi.getConnection();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.getViewport().setForeground(Color.BLACK);
        setTabel();
        setTabel2();
    }
    FBarang(String kode) {
        initComponents();
        Scroll.setVisible(false);
        vPegawai.setText(kode);
        vPegawai.setEditable(false);
        this.setLocationRelativeTo(null);
        after();
        conn=koneksi.getConnection();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.getViewport().setForeground(Color.BLACK);
        setTabel();
        setTabel2();
    }
    void before(){
        vKode.setEditable(true);
        vNama.setEditable(true);
        vHarga.setEditable(true);
        vStok.setEditable(true);
        vSuplier.setEditable(true);
        kode();
    }
    void after(){
        vKode.setEditable(false);
        vNama.setEditable(false);
        vHarga.setEditable(false);
        vStok.setEditable(false);
        vSuplier.setEditable(false);
        vKode.setText("");
        vNama.setText("");
        vHarga.setText("");
        vStok.setText("");
        vSuplier.setText("");
        Scroll.setVisible(false);
        temp=true;
        temp1=true;
    }
    private void kode(){
    try{
        String sql= "SELECT*FROM barang ORDER BY kode_barang DESC";
        PreparedStatement st=conn.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        if (rs.next()){
           String no= rs.getString("kode_barang").substring(1);
           String temps = "" + (Integer.parseInt(no)+1);
           String nol = "";
           switch (temps.length()) {
            case 1: nol = "00";
                    break;
            case 2: nol = "0";
                    break;
            case 3: nol = "";
                    break;
            }
           vKode.setText("A" + nol + temps);
        }else
           vKode.setText("A001");
        
    vKode.setEditable(false);
    }catch(NumberFormatException | SQLException e){
        JOptionPane.showMessageDialog(null, e);
    }
} 
    private void setTabel(){
    String [] JudulKolom={"No","Kode Barang","Nama Barang","Harga Barang","Stok Barang","Supplier"};
        tab = new DefaultTableModel(null, JudulKolom){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
    tabel.setModel(tab);
    tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tabel.getColumnModel().getColumn(0).setPreferredWidth(30);
    tabel.getColumnModel().getColumn(1).setPreferredWidth(100);
    tabel.getColumnModel().getColumn(2).setPreferredWidth(350);
    tabel.getColumnModel().getColumn(3).setPreferredWidth(103);
    tabel.getColumnModel().getColumn(4).setPreferredWidth(100);
    tabel.getColumnModel().getColumn(5).setPreferredWidth(120);

    getData();
    } 
    private void setTabel2(){
    String [] JudulKolom={"Nama"};
        tab2 = new DefaultTableModel(null, JudulKolom){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
    tSuplier.setModel(tab2);
    tSuplier.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tSuplier.getColumnModel().getColumn(0).setPreferredWidth(150);
    } 
    private void getData(){  
        try{   
        String sql="Select * from barang JOIN supplier ON (barang.kode_supplier=supplier.kode_supplier) ORDER BY kode_barang";
        PreparedStatement st=conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery(); 
        
        String kode,Nama,harga,stok,sup;
        int no=0;
        while(rs.next()){
            no=no+1;
            kode=rs.getString("kode_barang");
            Nama=rs.getString("nama_barang");
            harga=rs.getString("harga_barang");
            stok=rs.getString("stok_barang");
            sup=rs.getString("nama_supplier");

            Object Data[]={no,kode,Nama,harga,stok,sup};
            tab.addRow(Data);
        }
        }catch (SQLException sqle) {                  
           System.out.println("Proses Query Gagal = " + sqle);
           System.exit(0);
        }catch(Exception e){
           System.out.println("Koneksi Access Gagal " +e.getMessage());
           System.exit(0);
        }
    }
    public void hapusIsiJTable() {
        int a = tab.getRowCount();
        for (int i = 0; i < a; i++) {
            tab.removeRow(0);
        }
    }
    void cari(){
        try {
        String sql =  "Select * from barang JOIN supplier ON (barang.kode_supplier=supplier.kode_supplier) WHERE barang.kode_barang Like '%" +vCari.getText() + "%' or barang.nama_barang Like '%" +vCari.getText() + "%'";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs =st.executeQuery();

        hapusIsiJTable();
        int no=0;
        while (rs.next()) {
            no++;
            String kode = rs.getString("kode_barang");
            String nama = rs.getString("nama_barang");
            String harga = rs.getString("harga_barang");
            String stok = rs.getString("stok_barang");
            String supp=rs.getString("nama_supplier");
            
            Object [] data = {no,kode,nama,harga,stok,supp};
            tab.addRow(data);
        }
      }catch (SQLException se) {} 
    }
    void simpanData(){
        try{            
        String sql="Insert into barang values(?,?,?,?,?)";
        PreparedStatement st=conn.prepareStatement(sql);
            st.setString(1, vKode.getText());
            st.setString(2, vNama.getText());
            st.setString(3, vHarga.getText());
            st.setString(4, vStok.getText());
            st.setString(5, kodeS);
        int rs=st.executeUpdate();
        if(rs>0){
            JOptionPane.showMessageDialog(this,"Input Berhasil");
      	    setTabel();
            temp=true;
        }
        }catch (SQLException sqle) {
           JOptionPane.showMessageDialog(this,"Input  Gagal = " + sqle.getMessage());
        }catch(HeadlessException e){
           JOptionPane.showMessageDialog(this,"Koneksi Gagal " +e.getMessage());
        }
    }
    void updateData(){
        
        int pil = tabel.getSelectedRow();
        vKode.setText(tab.getValueAt(pil, 1).toString());
        vNama.setText(tab.getValueAt(pil, 2).toString());
        vHarga.setText(tab.getValueAt(pil, 3).toString());
        vStok.setText(tab.getValueAt(pil, 4).toString());
        
    }
    void updateData2(){
        try{            
        String sql="UPDATE barang SET harga_barang=?,stok_barang=? WHERE kode_barang=?";
        PreparedStatement up=conn.prepareStatement(sql);
            up.setString(1, vHarga.getText());
            up.setString(2, vStok.getText());
            up.setString(3, vKode.getText());
        int rs=up.executeUpdate();
        if(rs>0){
            JOptionPane.showMessageDialog(this,"Edit Berhasil");
      	    setTabel();         
            }
        }catch (SQLException sqle) {
           JOptionPane.showMessageDialog(this,"Input  Gagal = " + sqle.getMessage());
        }catch(HeadlessException e){
           JOptionPane.showMessageDialog(this,"Koneksi Gagal " +e.getMessage());
        }
        after();
    }
    public void hapusIsiJTable2() {
    int a = tab2.getRowCount();
    for (int i = 0; i < a; i++) {
      tab2.removeRow(0);
    }
    }
    void suplier(){
        try {
        String sql =  "Select * from supplier WHERE "
                    + "kode_supplier Like '%" +vSuplier.getText() + "%' or "
                    + "nama_supplier Like '%" +vSuplier.getText() + "%'";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs =st.executeQuery();

        hapusIsiJTable2();
        while (rs.next()) {
            String nama = rs.getString("nama_supplier");
            Object [] data = {nama};
            tab2.addRow(data);
        }
      }catch (SQLException se) {} 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_laporan = new javax.swing.JLabel();
        btn_transaksi = new javax.swing.JLabel();
        btn_logout = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        vCari = new javax.swing.JTextField();
        vKode = new javax.swing.JTextField();
        vNama = new javax.swing.JTextField();
        vHarga = new javax.swing.JTextField();
        vStok = new javax.swing.JTextField();
        vSuplier = new javax.swing.JTextField();
        bTambah = new javax.swing.JLabel();
        bEdit = new javax.swing.JLabel();
        bBatal = new javax.swing.JLabel();
        vPegawai = new javax.swing.JTextField();
        Scroll = new javax.swing.JScrollPane();
        tSuplier = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UnikomMart");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_laporanMouseClicked(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 40, 40));

        btn_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_transaksiMouseClicked(evt);
            }
        });
        getContentPane().add(btn_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 40, 40));

        btn_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_logoutMouseClicked(evt);
            }
        });
        getContentPane().add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 617, 40, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tabel.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabel.setGridColor(new java.awt.Color(0, 0, 0));
        tabel.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tabel.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabel);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 325, 820, 360));

        vCari.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vCari.setBorder(null);
        vCari.setOpaque(false);
        vCari.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                vCariCaretUpdate(evt);
            }
        });
        vCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vCariActionPerformed(evt);
            }
        });
        getContentPane().add(vCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 294, 170, 20));

        vKode.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vKode.setBorder(null);
        vKode.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        vKode.setOpaque(false);
        getContentPane().add(vKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 93, 138, 21));

        vNama.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vNama.setBorder(null);
        vNama.setOpaque(false);
        vNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vNamaActionPerformed(evt);
            }
        });
        getContentPane().add(vNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 93, 138, 21));

        vHarga.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vHarga.setBorder(null);
        vHarga.setOpaque(false);
        getContentPane().add(vHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(768, 93, 138, 21));

        vStok.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vStok.setBorder(null);
        vStok.setOpaque(false);
        vStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vStokKeyReleased(evt);
            }
        });
        getContentPane().add(vStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 156, 138, 21));

        vSuplier.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vSuplier.setBorder(null);
        vSuplier.setOpaque(false);
        vSuplier.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                vSuplierCaretUpdate(evt);
            }
        });
        vSuplier.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vSuplierFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vSuplierFocusLost(evt);
            }
        });
        vSuplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                vSuplierKeyReleased(evt);
            }
        });
        getContentPane().add(vSuplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 156, 138, 21));

        bTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bTambahMouseClicked(evt);
            }
        });
        getContentPane().add(bTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, 110, 29));

        bEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bEditMouseClicked(evt);
            }
        });
        getContentPane().add(bEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 110, 29));

        bBatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bBatalMouseClicked(evt);
            }
        });
        getContentPane().add(bBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 200, 110, 29));

        vPegawai.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vPegawai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vPegawai.setBorder(null);
        vPegawai.setOpaque(false);
        vPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vPegawaiActionPerformed(evt);
            }
        });
        getContentPane().add(vPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 96, 60, -1));

        Scroll.setBackground(new java.awt.Color(255, 255, 255));
        Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tSuplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        tSuplier.setGridColor(new java.awt.Color(204, 204, 204));
        Scroll.setViewportView(tSuplier);

        getContentPane().add(Scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(578, 180, 150, 90));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unikommart/03_Barang.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_transaksiMouseClicked
        // TODO add your handling code here:
        new FTransaksi("",vPegawai.getText()).setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_transaksiMouseClicked

    private void btn_laporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_laporanMouseClicked
        // TODO add your handling code here:
        new FLaporan(vPegawai.getText()).setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_laporanMouseClicked

    private void vCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vCariActionPerformed

    private void vCariCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_vCariCaretUpdate
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_vCariCaretUpdate

    private void vNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vNamaActionPerformed

    private void bTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bTambahMouseClicked
        // TODO add your handling code here:
        
        if(temp==true){
           before();
           vNama.requestFocus();
      
        }if(temp==false){
            simpanData();
            after();
            kodeS="";
        }
    }//GEN-LAST:event_bTambahMouseClicked

    private void vStokKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vStokKeyReleased
        // TODO add your handling code here:
        temp=false;
        temp1=false;
    }//GEN-LAST:event_vStokKeyReleased

    private void bEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bEditMouseClicked
        // TODO add your handling code here:
        int pil = tabel.getSelectedRow();
        if(pil<0) JOptionPane.showMessageDialog(null, "Silahkan Pilih data pada Tabel.");
        else{
          if(temp1==true){
            before();
            vKode.setEditable(false);
            vNama.setEditable(false);
            updateData();
          }
          if(temp1==false){
            updateData2(); 
          }
        }
    }//GEN-LAST:event_bEditMouseClicked

    private void bBatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBatalMouseClicked
        // TODO add your handling code here:
        after();
        vCari.requestFocus();
    }//GEN-LAST:event_bBatalMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
//        for (double i = 0.1; i < 1.0; i=i+0.1) {
//            String val = i+"";
//            float f = Float.valueOf(val);
//            this.setOpacity(f);
//            try{
//                Thread.sleep(50);
//            } catch (InterruptedException ex) {}
//        }
    }//GEN-LAST:event_formWindowOpened

    private void btn_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseClicked
        // TODO add your handling code here:
        new FLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_logoutMouseClicked

    private void vSuplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vSuplierKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==10){
        try {
            String sql =    "Select * from supplier WHERE "
                          + "kode_supplier Like '%" +vSuplier.getText() + "%' or "
                          + "nama_supplier Like '%" +vSuplier.getText() + "%'";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs =st.executeQuery();
        while (rs.next()) {
            String nama = rs.getString("nama_supplier");
            kodeS = rs.getString("kode_supplier");
            vSuplier.setText(nama);
            Scroll.setVisible(false);
            bTambah.requestFocus();
        }
      }catch (SQLException se) {} 
        }
    }//GEN-LAST:event_vSuplierKeyReleased

    private void vSuplierCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_vSuplierCaretUpdate
        // TODO add your handling code here:
        Scroll.setVisible(true);
        suplier();
    }//GEN-LAST:event_vSuplierCaretUpdate

    private void vSuplierFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vSuplierFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_vSuplierFocusGained

    private void vSuplierFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vSuplierFocusLost
        // TODO add your handling code here:
        Scroll.setVisible(false);
    }//GEN-LAST:event_vSuplierFocusLost

    private void vPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vPegawaiActionPerformed

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
            java.util.logging.Logger.getLogger(FBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Scroll;
    private javax.swing.JLabel bBatal;
    private javax.swing.JLabel bEdit;
    private javax.swing.JLabel bTambah;
    private javax.swing.JLabel btn_laporan;
    private javax.swing.JLabel btn_logout;
    private javax.swing.JLabel btn_transaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tSuplier;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField vCari;
    private javax.swing.JTextField vHarga;
    private javax.swing.JTextField vKode;
    private javax.swing.JTextField vNama;
    private javax.swing.JTextField vPegawai;
    private javax.swing.JTextField vStok;
    private javax.swing.JTextField vSuplier;
    // End of variables declaration//GEN-END:variables
}
