/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unikommart;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Exception
 */
public class FTransaksi extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tab,tab2;
    String kodbar;
    Integer tot=0;
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in","ID"));

    /**
     * Creates new form FTransaksi
     */
    public FTransaksi() {
        initComponents();
        this.setLocationRelativeTo(null);
        conn=koneksi.getConnection();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        setTabel();
        setTabel2();
        kode();
        date();
        after();
        
    }
    FTransaksi(String pass,String puss) {
        initComponents();
        String temp=pass;
        String temp1=puss;
        vPegawai.setText(temp1);
        vPegawai.setEditable(false);
        if(temp.equals("kasir")){
            btn_barang.setVisible(false);
            btn_laporan.setVisible(false);
        }
        conn=koneksi.getConnection();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        setTabel();
        setTabel2();
        kode();
        date();
        after();
    }
private void after(){
    vNama.setEditable(false);
    vHarga.setEditable(false);
    totHar.setEditable(false);
    vKembali.setEditable(false);
    vCari.setText("");
    vNama.setText("");
    vHarga.setText("");
    vQty.setText("");
    vSub.setText("");
}
void refresh(){
    vCari.setText("");
    vNama.setText("");
    vHarga.setText("");
    vQty.setText("");
    vSub.setText("");
    totHar.setText("");
    vBayar.setText("");
    vKembali.setText("");
    hapusTable2();
}
private void date(){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.now();
    vDate.setText(dtf.format(localDate));
    vDate.setEditable(false);
}
private void kode(){
    try{
        String sql= "SELECT*FROM transaksi ORDER BY kode_nota DESC";
        PreparedStatement st=conn.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        if (rs.next()){
           String no= rs.getString("kode_nota").substring(4);
           String temp = "" + (Integer.parseInt(no)+1);
           String nol = "";
           switch (temp.length()) {
            case 1: nol = "00";
                    break;
            case 2: nol = "0";
                    break;
            case 3: nol = "";
                    break;
            }
           vNota.setText("UNC-" + nol + temp);
        }else
           vNota.setText("UNC-001");
        
    vNota.setEditable(false);
    }catch(NumberFormatException | SQLException e){
        JOptionPane.showMessageDialog(null, e);
    }
} 
private void setTabel(){
    String [] JudulKolom={"Nama Barang","Harga Barang"};
        tab = new DefaultTableModel(null, JudulKolom){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
    tabel.setModel(tab);
    tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tabel.getColumnModel().getColumn(0).setPreferredWidth(140);
    tabel.getColumnModel().getColumn(1).setPreferredWidth(90);

    getData();
}
private void setTabel2(){
    String [] JudulKolom={"Nama Barang","Harga Barang","Qty","Sub-Total"};
        tab2 = new DefaultTableModel(null, JudulKolom){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
    tabel2.setModel(tab2);
    tabel2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tabel2.getColumnModel().getColumn(0).setPreferredWidth(170);
    tabel2.getColumnModel().getColumn(1).setPreferredWidth(90);
    tabel2.getColumnModel().getColumn(2).setPreferredWidth(80);
    tabel2.getColumnModel().getColumn(3).setPreferredWidth(80);
    }
private void getData(){  
    try{   
    String sql="Select * from barang";
    PreparedStatement st=conn.prepareStatement(sql);
    ResultSet rs = st.executeQuery();
        
    String Nama,harga;
    int no=0;
    while(rs.next()){
        no=no+1;
        Nama=rs.getString("nama_barang");
        harga=rs.getString("harga_barang");
         
        Object Data[]={Nama,harga};
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
public void hapusTable1() {
    int a = tab.getRowCount();
    for (int i = 0; i < a; i++) {
      tab.removeRow(0);
    }
}
public void hapusTable2() {
    int a = tab2.getRowCount();
    for (int i = 0; i < a; i++) {
      tab2.removeRow(0);
    }
}
void cari(){
    try {
    String sql =  "Select * from barang WHERE "
                + "kode_barang Like '%" +vCari.getText() + "%' or "
                + "nama_barang Like '%" +vCari.getText() + "%'    ";
    PreparedStatement st = conn.prepareStatement(sql);
    ResultSet rs =st.executeQuery();

    hapusTable1();
    while (rs.next()) {
        String kode = rs.getString("kode_barang");
        String nama = rs.getString("nama_barang");
        String harga = rs.getString("harga_barang");
        Object [] data = {nama,harga};
        tab.addRow(data);
        kodbar=kode;
        }
    }catch (SQLException se) {} 
}
void tambah(){
    String nama,harga,qty,sub,nama1;
    Integer har,qty1,qty2,temp,temp1;
    nama=vNama.getText();
    harga=vHarga.getText();
    qty=String.valueOf(vQty.getText());
    sub=vSub.getText();
    qty2=Integer.valueOf(qty);
    har=Integer.valueOf(sub);
    boolean ketemu=false;
        
    int jum=tab2.getRowCount();
    for(int i=0;i<jum;i++)
    {
        nama1 = tab2.getValueAt(i, 0).toString();
        if(nama1.equalsIgnoreCase(vNama.getText()))
        {
            qty1 = Integer.valueOf(tab2.getValueAt(i, 2).toString());
            temp=qty2+qty1;
            temp1=temp*Integer.valueOf(harga);
            tab2.setValueAt(temp, i, 2);
            tab2.setValueAt(temp1, i, 3);
                   
            ketemu=true;
            break;
        }
    }
    if(ketemu==false)
    {
        Object Data[]={nama,harga,qty,sub};
        tab2.addRow(Data);
    }
    tot=tot+har;
    totHar.setText(String.valueOf(tot));
    after();
    vCari.requestFocus();
}
void selesai(){
    try{   
    Integer qty1=0,qty=0,temp=0,i,jum;    
    String sql="Select * from barang where nama_barang=?";
    
    jum=tab2.getRowCount();
    for(i=0;i<jum;i++)
    {
        String nama = tab2.getValueAt(i, 0).toString();
        qty = Integer.valueOf(tab2.getValueAt(i, 2).toString());
        
        PreparedStatement st=conn.prepareStatement(sql);
        st.setString(1,nama);
        ResultSet rs = st.executeQuery();
        
        while(rs.next())
        {
            qty1 = rs.getInt("stok_barang");
            temp = qty1-qty;          
        }
        String sql1="update barang set stok_barang=? where nama_barang=?";
        PreparedStatement up=conn.prepareStatement(sql1);
        up.setString(1,temp.toString());
        up.setString(2,nama);
        up.executeUpdate(); 
    }
    if(Objects.equals(i, jum)){
        refresh();
        kode();
    }
    }catch (SQLException sqle) {                  
        System.out.println("Proses Query Gagal = " + sqle);
        System.exit(0);
    } 
}
void simpan(){
    try{
    String sql="INSERT INTO transaksi VALUES(?,?,?,?)";
    PreparedStatement st=conn.prepareStatement(sql);
    st.setString(1,vNota.getText());
    st.setString(2,vDate.getText());
    st.setString(3,vPegawai.getText());
    st.setString(4,totHar.getText());
    int rs= st.executeUpdate();

    int rs2=0;
    int jum=tab2.getRowCount();
    for(int i=0;i<jum;i++){
        String sql2="INSERT INTO detailtransaksi VALUES(?,?,?,?,?)";
        PreparedStatement st2=conn.prepareStatement(sql2);
        st2.setString(1,vNota.getText());
        st2.setString(2,tab2.getValueAt(i, 0).toString());
        st2.setString(3,tab2.getValueAt(i, 1).toString());
        st2.setString(4,tab2.getValueAt(i, 2).toString());
        st2.setString(5,tab2.getValueAt(i, 3).toString());
        rs2= st2.executeUpdate();
    }
    if((rs>0) && (rs2>0)){
        JOptionPane.showMessageDialog(this,"Transaksi Berhasil");
        cetak();
        selesai();
    } else
        JOptionPane.showMessageDialog(this,"Transaksi Gagal");   
    }catch(  HeadlessException | SQLException e){
            System.out.println("Exception " +e );
    }
}
void cetak(){
        String reportSource;
        String reportDest;
        Map<String,Object> param=new HashMap<>();
        param.put("Bayar", vBayar.getText());
        param.put("KodNot",vNota.getText());
        try{
            reportSource= System.getProperty("user.dir")+"\\Laporan\\Nota.jrxml";
            reportDest= System.getProperty("user.dir")+"\\Laporan\\Nota.html";


            JasperReport jasperReport=JasperCompileManager.compileReport(reportSource);

            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,param,conn);

            JasperExportManager.exportReportToHtmlFile(jasperPrint,reportDest);

            JasperViewer.viewReport(jasperPrint,false);

        }catch(JRException e){
            System.out.println(e);
        }
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
        btn_barang = new javax.swing.JLabel();
        btn_logout = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel2 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        vNota = new javax.swing.JTextField();
        vDate = new javax.swing.JTextField();
        vNama = new javax.swing.JTextField();
        vHarga = new javax.swing.JTextField();
        vQty = new javax.swing.JTextField();
        vSub = new javax.swing.JTextField();
        vCari = new javax.swing.JTextField();
        totHar = new javax.swing.JTextField();
        vKembali = new javax.swing.JTextField();
        vBayar = new javax.swing.JTextField();
        bTambah = new javax.swing.JLabel();
        bHapus = new javax.swing.JLabel();
        bCetak = new javax.swing.JLabel();
        vPegawai = new javax.swing.JTextField();
        Background = new javax.swing.JLabel();

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
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 322, 40, 40));
        getContentPane().add(btn_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 40, 40));

        btn_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_barangMouseClicked(evt);
            }
        });
        getContentPane().add(btn_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 235, 40, 40));

        btn_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_logoutMouseClicked(evt);
            }
        });
        getContentPane().add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 617, 40, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tabel2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabel2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 188, 420, 492));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 87, 230, 219));

        vNota.setBackground(new java.awt.Color(226, 226, 226));
        vNota.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vNota.setForeground(new java.awt.Color(153, 153, 153));
        vNota.setBorder(null);
        vNota.setOpaque(false);
        getContentPane().add(vNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 165, 140, 21));

        vDate.setBackground(new java.awt.Color(226, 226, 226));
        vDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vDate.setForeground(new java.awt.Color(153, 153, 153));
        vDate.setBorder(null);
        vDate.setOpaque(false);
        getContentPane().add(vDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 221, 140, 21));

        vNama.setBackground(new java.awt.Color(226, 226, 226));
        vNama.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vNama.setForeground(new java.awt.Color(153, 153, 153));
        vNama.setBorder(null);
        vNama.setOpaque(false);
        getContentPane().add(vNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 380, 140, 21));

        vHarga.setBackground(new java.awt.Color(226, 226, 226));
        vHarga.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vHarga.setForeground(new java.awt.Color(153, 153, 153));
        vHarga.setBorder(null);
        vHarga.setOpaque(false);
        getContentPane().add(vHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 442, 140, 21));

        vQty.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vQty.setBorder(null);
        vQty.setOpaque(false);
        vQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vQtyKeyPressed(evt);
            }
        });
        getContentPane().add(vQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 504, 140, 21));

        vSub.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vSub.setBorder(null);
        vSub.setOpaque(false);
        getContentPane().add(vSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 566, 140, 21));

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
        vCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vCariKeyPressed(evt);
            }
        });
        getContentPane().add(vCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 55, 170, 22));

        totHar.setBackground(new java.awt.Color(226, 226, 226));
        totHar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        totHar.setForeground(new java.awt.Color(153, 153, 153));
        totHar.setBorder(null);
        totHar.setOpaque(false);
        getContentPane().add(totHar, new org.netbeans.lib.awtextra.AbsoluteConstraints(805, 373, 137, 21));

        vKembali.setBackground(new java.awt.Color(226, 226, 226));
        vKembali.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vKembali.setForeground(new java.awt.Color(153, 153, 153));
        vKembali.setBorder(null);
        vKembali.setOpaque(false);
        getContentPane().add(vKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(805, 499, 137, 21));

        vBayar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vBayar.setBorder(null);
        vBayar.setOpaque(false);
        vBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vBayarKeyPressed(evt);
            }
        });
        getContentPane().add(vBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(805, 436, 137, 21));

        bTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bTambahMouseClicked(evt);
            }
        });
        getContentPane().add(bTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 610, 74, 30));

        bHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bHapusMouseClicked(evt);
            }
        });
        getContentPane().add(bHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 610, 74, 30));

        bCetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCetakMouseClicked(evt);
            }
        });
        getContentPane().add(bCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(828, 546, 93, 24));

        vPegawai.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vPegawai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        vPegawai.setBorder(null);
        vPegawai.setOpaque(false);
        getContentPane().add(vPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 96, 60, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/unikommart/02_Kasir.jpg"))); // NOI18N
        getContentPane().add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_barangMouseClicked
        // TODO add your handling code here:
        new FBarang(vPegawai.getText()).setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_barangMouseClicked

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

    private void vCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vCariKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==10){
        try {                
            String sql="select * from barang Where kode_barang=?";
            PreparedStatement st=conn.prepareStatement(sql);
            st.setString(1,kodbar);
            ResultSet rs= st.executeQuery();
            if( rs.next()){
                String Nama = rs.getString("nama_barang");
                String Harga = rs.getString("harga_barang");
                vNama.setText(Nama);
                vHarga.setText(Harga);
                vQty.requestFocus();
            } 
        }catch(SQLException e){
                System.out.println("Koneksi Gagal " +e.getMessage());
        }
    }
    }//GEN-LAST:event_vCariKeyPressed

    private void vQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vQtyKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==10){
        Integer qty,har,sub;
        qty=Integer.valueOf(vQty.getText());
        har=Integer.valueOf(vHarga.getText());
        sub=har*qty;
        vSub.setText(String.valueOf(sub));
        tambah();
        }
    }//GEN-LAST:event_vQtyKeyPressed

    private void vBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vBayarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==10){
            Integer total,bayar,kembali;
            total=Integer.valueOf(totHar.getText());
            bayar=Integer.valueOf(vBayar.getText());
            kembali=bayar-total;
            vKembali.setText(String.valueOf(format.format(kembali)));
        }
    }//GEN-LAST:event_vBayarKeyPressed

    private void bTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bTambahMouseClicked
        // TODO add your handling code here:
        tambah();
    }//GEN-LAST:event_bTambahMouseClicked

    private void bHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bHapusMouseClicked
        // TODO add your handling code here:
        int pil = tabel2.getSelectedRow();
        tab2.removeRow(pil);
    }//GEN-LAST:event_bHapusMouseClicked

    private void bCetakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCetakMouseClicked
        // TODO add your handling code here:
        simpan();
        
    }//GEN-LAST:event_bCetakMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
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

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        // TODO add your handling code here:
        try{
            int row = tabel.getSelectedRow();
            String nama = tabel.getValueAt(row, 0).toString();
            String harga = tabel.getValueAt(row, 1).toString();
            
            vNama.setText(nama);
            vHarga.setText(harga);
            vQty.requestFocus();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Error!");
        }
    }//GEN-LAST:event_tabelMouseClicked

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
            java.util.logging.Logger.getLogger(FTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel bCetak;
    private javax.swing.JLabel bHapus;
    private javax.swing.JLabel bTambah;
    private javax.swing.JLabel btn_barang;
    private javax.swing.JLabel btn_laporan;
    private javax.swing.JLabel btn_logout;
    private javax.swing.JLabel btn_transaksi;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabel;
    private javax.swing.JTable tabel2;
    private javax.swing.JTextField totHar;
    private javax.swing.JTextField vBayar;
    private javax.swing.JTextField vCari;
    private javax.swing.JTextField vDate;
    private javax.swing.JTextField vHarga;
    private javax.swing.JTextField vKembali;
    private javax.swing.JTextField vNama;
    private javax.swing.JTextField vNota;
    private javax.swing.JTextField vPegawai;
    private javax.swing.JTextField vQty;
    private javax.swing.JTextField vSub;
    // End of variables declaration//GEN-END:variables
}
