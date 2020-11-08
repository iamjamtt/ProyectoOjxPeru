/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VistaCajero;

import Conexion.ConexionSQL;
import static VistaCajero.cdConfirmarRecargaa.total;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author jamt_
 */
public class efConfirmarDevolucionn extends javax.swing.JInternalFrame {

    /**
     * Creates new form efConfirmarDevolucionn
     */
    public efConfirmarDevolucionn() {
        initComponents();
        
        this.getContentPane().setBackground(Color.WHITE);
    }
    
    void estadoTarjeta(){
        String modi="UPDATE Tarjeta SET estadoTarjeta="+1+" WHERE idTarjeta="+aaLogearTarjeta.idTarjeta;
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al descontar el saldo de la tarjeta: " + e);
        }
    }
    
    void ingresarFechaDevolucion(){
        String fecha;
        
        Date fechaActual = new Date();
        int anioactual = fechaActual.getYear()+1900;
        int mesactual = fechaActual.getMonth()+1;
        int diaactual = fechaActual.getDate();

        fecha = anioactual+"-"+mesactual+"-"+diaactual;
                
        String modi="UPDATE VoucherAlquiler SET fechaDevolucion='"+fecha+"', estadoVA="+2+" WHERE codigoVA='"+eeMenuDevolucionn.codVA+"'";
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al descontar el saldo de la tarjetaaaaa: " + e);
        }
    }

    void restaurarStock(int codP){
        int cap = 0;
        int desfinal;
        String consul="SELECT * FROM Pelicula WHERE idPelicula="+codP;
        try {
            Statement st= cn.createStatement();
            ResultSet rs= st.executeQuery(consul);
            while(rs.next())
            {
                cap= rs.getInt("cantidadP");
            }
             
        } catch (Exception e) {
        }
        desfinal=cap+1;
        String modi="UPDATE Pelicula SET cantidadP="+desfinal+" WHERE idPelicula = "+codP;
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    int obtenerIdVA(){
        int idVAA = 0;
        String consul="SELECT * FROM VoucherAlquiler WHERE codigoVA='"+eeMenuDevolucionn.codVA+"'";
        try {
            Statement st= cn.createStatement();
            ResultSet rs= st.executeQuery(consul);
            while(rs.next())
            {
                idVAA= rs.getInt("idVA");
            }
            System.out.println("idVA: " + idVAA);
        } catch (Exception e) {
            System.out.println("Error obtener idVA: " + e);
        }
        return idVAA;
    }
    
    int contadorDeFlias(){
        int idVA = obtenerIdVA();
        int contador = 0;
        String consul="SELECT count(*) AS total FROM DetalleVoucher WHERE idVA="+idVA;
        try {
            Statement st= cn.createStatement();
            ResultSet rs= st.executeQuery(consul);
            if(rs.next())
            {
                contador = rs.getInt("total");
            }
            System.out.println("contador: " + contador);
        } catch (Exception e) {
            System.out.println("Error obtener idVA: " + e);
        }
        return contador;
    }
    
    void confirmarTarjeta(String contra){
        int cont=0;
        String mostrar = "select * from Cliente c INNER JOIN Tarjeta t ON c.idTarjeta = t.idTarjeta WHERE t.contrasenia = '"+contra+"'" + " AND t.idTarjeta = " + aaLogearTarjeta.idTarjeta;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(mostrar);
            
            if(rs.next()){
                cont++;
            }
            
            if(cont==1){
                estadoTarjeta();
                ingresarFechaDevolucion();
                int capcod;
                int idVA = obtenerIdVA();
                int contador = contadorDeFlias();
                String mo = "SELECT * FROM DetalleVoucher where idVA="+idVA;
                try {
                    Statement stt = cn.createStatement();
                    ResultSet rss = stt.executeQuery(mo);
                    
                    for(int i=0;i<contador;i++)
                    {
                        if(rss.next()){
                        capcod=rss.getInt("idPelicula");
                        restaurarStock(capcod); 
                        }
                    }
                    
                } catch (Exception e) {
                    System.out.println("Error al restaurar stok: " + e);
                }
                
                JOptionPane.showMessageDialog(null, "Tramite de devolucion con exito","Mensaje",1);
                
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                txtContraTarjeta.setText("");
            }
                        
        } catch (SQLException ex) {
            System.out.println("Error Logear Cliente -- " + ex);
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
        jLabel1 = new javax.swing.JLabel();
        txtContraTarjeta = new javax.swing.JPasswordField();
        btnConfirmar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Confirmar Devolucion");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel1.setText("Contraseña");

        btnConfirmar.setBackground(new java.awt.Color(51, 255, 51));
        btnConfirmar.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(txtContraTarjeta)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfirmar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
        String contra = new String(txtContraTarjeta.getPassword());

        confirmarTarjeta(contra);
    }//GEN-LAST:event_btnConfirmarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtContraTarjeta;
    // End of variables declaration//GEN-END:variables
Conexion.ConexionSQL cc = new ConexionSQL();
Connection cn= ConexionSQL.conexionn();
}
