/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VistaCajero;

import Conexion.ConexionSQL;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.sql.Blob;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author jamt_
 */
public class ddMenuAlquierr extends javax.swing.JInternalFrame {

    /**
     * Creates new form ddMenuAlquierr
     */
    PlaceHolder holder;
    DefaultTableModel model;
    public static int idPelicula;
    public static double precioPelicula;
    public static double precioTotal;
    public static int con;
    public static String prePe;
    
    
    public ddMenuAlquierr(){
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        tablaPelicula.setBackground(Color.WHITE);
        holder = new PlaceHolder(txtBuscarPelicula, "Buscar Pelicula");
        cargarComboDestino();
        cargarPelicula("");
        
    }
    
    void cargarComboDestino(){
        String SQL = "SELECT nombreCategoria FROM Categoria";
        try {
            PreparedStatement pst = cn.prepareStatement(SQL);
            ResultSet rs = pst.executeQuery();
            cboCategoriaPelicula.addItem("Categoria");
            
            while(rs.next()){
                cboCategoriaPelicula.addItem("> "+rs.getString("nombreCategoria"));
            }
            
        } catch (Exception e) {
            System.out.println("Error en combo Categoria Pelicula: " + e);
        }
    }
    
    void cargarPelicula(String valor){
        
        String mostrar="SELECT * FROM Pelicula WHERE nombreP LIKE '%"+valor+"%' AND "+" cantidadP>"+0+ " AND estadoP="+1;
        
        String []titulos={"NRO","CARATULA","NOMBRE","PRECIO","STOCK"};
        Object []Registros=new Object[5];
        model= new DefaultTableModel(null, titulos);
        
        try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(mostrar);
                
                tablaPelicula.setDefaultRenderer(Object.class, new TablaPelicula());
                
                while(rs.next())
                {
                    Registros[0]= rs.getString(1);
                    
                    Blob foto = rs.getBlob(6);
                    byte[] data = foto.getBytes(1, (int) foto.length());
                    BufferedImage img = null;
                    
                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (Exception e) {
                        System.out.println("error imageio pelicula " + e);
                    }

                    Registros[1]= new JLabel(new ImageIcon(img));
                    Registros[2]= rs.getString(2);
                    Registros[3]= rs.getString(3);
                    Registros[4]= rs.getString(5);
                    
                    model.addRow(Registros); 
                }
                tablaPelicula.setModel(model);
                tablaPelicula.setRowHeight(170);
        } catch (SQLException ex) {
            System.out.println("Error en la tabla pelicula: " + ex);
        }
    }
    int idCategoria;
  
    void cargarPeliculaCategoria(String valor){
        idCategoria = cboCategoriaPelicula.getSelectedIndex();
        String mostrar="SELECT * FROM Pelicula WHERE nombreP LIKE '%"+valor+"%' AND idCategoria="+idCategoria+" AND "+" cantidadP>"+0;
        
        String []titulos={"NRO","CARATULA","NOMBRE","PRECIO","STOCK"};
        Object []Registros=new Object[5];
        model= new DefaultTableModel(null, titulos);
        
        try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(mostrar);
                
                tablaPelicula.setDefaultRenderer(Object.class, new TablaPelicula());
                
                while(rs.next())
                {
                    Registros[0]= rs.getString(1);
                    
                    Blob foto = rs.getBlob(6);
                    byte[] data = foto.getBytes(1, (int) foto.length());
                    BufferedImage img = null;
                    
                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (Exception e) {
                        System.out.println("error imageio pelicula " + e);
                    }

                    Registros[1]= new JLabel(new ImageIcon(img));
                    Registros[2]= rs.getString(2);
                    Registros[3]= rs.getString(3);
                    Registros[4]= rs.getString(5);
                    model.addRow(Registros); 
                }
                tablaPelicula.setModel(model);
                tablaPelicula.setRowHeight(170);
        } catch (SQLException ex) {
            System.out.println("Error en la tabla pelicula: " + ex);
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

        jPanel1 = new javax.swing.JPanel();
        cboCategoriaPelicula = new javax.swing.JComboBox<>();
        txtBuscarPelicula = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPelicula = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Peliculas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 153));

        cboCategoriaPelicula.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        cboCategoriaPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoriaPeliculaActionPerformed(evt);
            }
        });

        txtBuscarPelicula.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        txtBuscarPelicula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarPeliculaKeyTyped(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 255, 51));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboCategoriaPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(txtBuscarPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboCategoriaPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBuscarPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaPelicula = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int col){
                for(int i=0; i<tablaPelicula.getRowCount(); i++){
                    if(row==i){
                        return false;
                    }
                }
                return true;
            }
        };
        tablaPelicula.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tablaPelicula.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaPelicula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPeliculaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPelicula);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaPeliculaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPeliculaMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            try {
                DefaultTableModel tabladet = (DefaultTableModel) ddeAñadirAlquiler.tablaPelicula.getModel();
                String[]  dato=new String[4];

                int  fila = tablaPelicula.getSelectedRow();

                if(fila==-1){
                    JOptionPane.showMessageDialog(null, "No  ha seleccionado ningun registro");
                }
                else{
                    String codPe=tablaPelicula.getValueAt(fila, 0).toString();
                    String nomPe=tablaPelicula.getValueAt(fila, 2).toString();
                    prePe=tablaPelicula.getValueAt(fila, 3).toString();
                    System.out.println("cod Pe " + codPe);
                    int c=0;
                    int j=0;
                    int cantPe=1;
                    double pre = Double.parseDouble(prePe);
                
                    for(int i=0;i<ddeAñadirAlquiler.tablaPelicula.getRowCount();i++){
                            Object com=ddeAñadirAlquiler.tablaPelicula.getValueAt(i,0);
                            if(codPe.equals(com)){
                            j=i;
                            JOptionPane.showMessageDialog(null, "Pelicula ya ingresada");
                            c=c+1;
                        }
                    }

                    if(c==0){
                        dato[0]=codPe;
                        dato[1]=nomPe;
                        dato[2]=prePe;
                        dato[3]=String.valueOf(cantPe);
                        
                        con++;
                        precioTotal += pre;
                        System.out.println("con <-> " + con + " || preTotal <-> " + precioTotal);
                        
                        if(con==3){
                            ddeAñadirAlquiler.btnAniadir.setEnabled(false);
                        }
                        
                        tabladet.addRow(dato);

                        ddeAñadirAlquiler.tablaPelicula.setModel(tabladet);
                        
                        this.dispose();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error agregar peliculas a la otra tabla " + e);
            }
            
            //ddeAñadirAlquiler añaAlqui = new ddeAñadirAlquiler();
            //bbPrincipal.escritorio.add(añaAlqui);
            //añaAlqui.toFront();
            //añaAlqui.setVisible(true);
        }
    }//GEN-LAST:event_tablaPeliculaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cboCategoriaPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaPeliculaActionPerformed
        // TODO add your handling code here:
        cargarPeliculaCategoria("");
    }//GEN-LAST:event_cboCategoriaPeliculaActionPerformed

    private void txtBuscarPeliculaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPeliculaKeyTyped
        // TODO add your handling code here:
        cargarPeliculaCategoria(txtBuscarPelicula.getText());
        cargarPelicula(txtBuscarPelicula.getText());
    }//GEN-LAST:event_txtBuscarPeliculaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboCategoriaPelicula;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPelicula;
    private javax.swing.JTextField txtBuscarPelicula;
    // End of variables declaration//GEN-END:variables
Conexion.ConexionSQL cc = new ConexionSQL();
Connection cn= ConexionSQL.conexionn();
}
