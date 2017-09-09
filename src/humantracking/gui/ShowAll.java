/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package humantracking.gui;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import com.googlecode.javacv.cpp.opencv_highgui;
import humantracking.dbadapter.VideoAccessDBAdapter;
import static humantracking.gui.FrontView.resize;
import humantracking.model.SurvelianceVideo;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG;

/**
 *
 * @author Darshana Priyasad
 */
public class ShowAll extends javax.swing.JFrame {

    private DefaultTableModel model;
    private opencv_highgui.CvCapture capture;
    private boolean pause;
    private BackgroundSubtractor backgroundSubtractor;

    final HOGDescriptor hog;
    final MatOfFloat descriptors;

    private CascadeClassifier rightEarDetector;
    private CascadeClassifier leftEarDetector;
    private CascadeClassifier eyeDetector;
    private CascadeClassifier faceDetector;
    private CascadeClassifier upperbodyDetector;
    private CascadeClassifier lowerbodyDetector;
    private CascadeClassifier pedestrainDetector;

    final MatOfRect foundLocations;
    final MatOfDouble foundWeights;
    final Size winStride;
    final Size padding;
    final Point rectPoint1;
    final Point rectPoint2;
    final Point fontPoint;
    int frames = 0;
    int framesWithPeople = 0;
    final Scalar rectColor;
    final Scalar fontColor;

    /**
     * Creates new form ShowAll
     */
    public ShowAll() {
        initComponents();
        pack();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        foundLocations = new MatOfRect();
        foundWeights = new MatOfDouble();
        winStride = new Size(8, 8);
        padding = new Size(32, 32);
        rectPoint1 = new Point();
        rectPoint2 = new Point();
        fontPoint = new Point();

        rectColor = new Scalar(0, 255, 0);
        fontColor = new Scalar(255, 255, 255);

        eyeDetector = new CascadeClassifier();
        eyeDetector.load("./src/humantracking/support/haarcascade_eye.xml");

        rightEarDetector = new CascadeClassifier();
        rightEarDetector.load("./src/humantracking/support/haarcascade_mcs_rightear.xml");

        leftEarDetector = new CascadeClassifier();
        leftEarDetector.load("./src/humantracking/support/haarcascade_mcs_leftear.xml");

        faceDetector = new CascadeClassifier();
        faceDetector.load("./src/humantracking/support/lbpcascade_frontalface.xml");

        upperbodyDetector = new CascadeClassifier();
        upperbodyDetector.load("./src/humantracking/support/haarcascade_upperbody.xml");

        lowerbodyDetector = new CascadeClassifier();
        lowerbodyDetector.load("./src/humantracking/support/haarcascade_lowerbody.xml");

        pedestrainDetector = new CascadeClassifier();
        pedestrainDetector.load("./src/humantracking/support/hogcascade_pedestrians.xml");

        hog = new HOGDescriptor();
        descriptors = HOGDescriptor.getDefaultPeopleDetector();
        hog.setSVMDetector(descriptors);

        setLocationRelativeTo(null);
        setTitle("Full Feeds");
        pause = false;
        model = (DefaultTableModel) jTable1.getModel();
        try {
            loadVideos();
        } catch (SQLException ex) {
            Logger.getLogger(ShowAll.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ShowAll.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/humantracking/images/all.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 3, true));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 3, true), "Select Recorded Camera Feed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(153, 0, 153))); // NOI18N

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setOpaque(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Camera ID", "Video Name", "Location", "Date", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
        }

        jScrollPane2.setViewportView(jScrollPane1);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/humantracking/images/play.png"))); // NOI18N
        jButton1.setText("Play");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 153, 204));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/humantracking/images/video_pause.png"))); // NOI18N
        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 153, 204));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/humantracking/images/no.png"))); // NOI18N
        jButton3.setText("Stop");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(350, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(jLabel1)
                .addContainerGap(282, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jTable1.isRowSelected(jTable1.getSelectedRow())) {
            String filePath = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString();
            //System.out.println(filePath);
            String fileName = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
            String id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
            playVideo(id, filePath + "/" + fileName);
        } else {
            JOptionPane.showMessageDialog(this, "Select A Video First", "Warning", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        pauseVideo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        stopVideo();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ShowAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowAll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowAll().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void loadVideos() throws SQLException, ClassNotFoundException {
        VideoAccessDBAdapter dBAdapter = new VideoAccessDBAdapter();
        List<SurvelianceVideo> details = dBAdapter.getDetails();
        for (SurvelianceVideo detail : details) {
            Object[] data = {detail.getId(), detail.getVideo_name(), detail.getVideo_location(), detail.getDate(), detail.getTime()};
            model.addRow(data);
        }

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {

        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void playVideo(final String id, final String filePath) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        new Thread() {

            @Override
            public void run() {

                capture = opencv_highgui.cvCreateFileCapture(filePath);
                opencv_core.IplImage frame;
                VideoAccessDBAdapter videoAccessDBAdapter = new VideoAccessDBAdapter();

                //pause = false;
                while (true) {
                    System.out.println(pause);
                    if (!pause && capture != null) {
                        frame = opencv_highgui.cvQueryFrame(capture);
                        if (frame != null) {

                            BufferedImage orgImage = resize(frame.getBufferedImage(), 640, 480);
                            byte[] data = ((DataBufferByte) orgImage.getRaster().getDataBuffer()).getData();
                            Mat camImage = new Mat(orgImage.getHeight(), orgImage.getWidth(), CvType.CV_8UC3);

                            camImage.put(0, 0, data);

                            boolean detected = false;
                            //int faceDetactor = faceDetactor(camImage);
                            //int eyeDetactor = eyeDetactor(camImage);
                            //pedestrianDetactor(camImage);
                            //eyeDetactor(camImage);
                            int HogDetection = HogDetection(camImage);
                            if (HogDetection == 0) {
                                int faceDetactor = faceDetactor(camImage);
                                if (faceDetactor == 0) {
                                    int eyeDetactor = eyeDetactor(camImage);
                                    if (eyeDetactor == 0) {
                                        int upperBodyDetactor = upperBodyDetactor(camImage);
                                        if (upperBodyDetactor == 0) {
                                            int lowerBodyDetactor = lowerBodyDetactor(camImage);
                                            if (lowerBodyDetactor == 0) {

                                            } else {
                                                Core.putText(camImage,
                                                        "Human Detected",
                                                        fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                                        2, Core.LINE_AA, true);
                                                detected = true;
                                            }
                                        } else {
                                            Core.putText(camImage,
                                                    "Human Detected",
                                                    fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                                    2, Core.LINE_AA, true);
                                            detected = true;
                                        }
                                    } else {
                                        Core.putText(camImage,
                                                "Human Detected",
                                                fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                                2, Core.LINE_AA, true);
                                        detected = true;
                                    }
                                } else {
                                    //System.out.println(",d.m.mdn,dmndmf");
                                    
                                    Core.putText(camImage,
                                            "Human Detected",
                                            fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                                            2, Core.LINE_AA, true);
                                    
                                    //Core.pu
                                    detected = true;
                                }
                            }
                            /*
                             int faceDetactor = faceDetactor(camImage);
                             if (faceDetactor == 0) {
                             int eyeDetactor = eyeDetactor(camImage);
                             if (eyeDetactor == 0) {
                             int upperBodyDetactor = upperBodyDetactor(camImage);
                             if (upperBodyDetactor == 0) {
                             int lowerBodyDetactor = lowerBodyDetactor(camImage);
                             if (lowerBodyDetactor == 0) {
                                            
                             } else {
                             Core.putText(camImage,
                             "Human Detected",
                             fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                             2, Core.LINE_AA, false);
                             detected = true;
                             }
                             } else {
                             Core.putText(camImage,
                             "Human Detected",
                             fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                             2, Core.LINE_AA, false);
                             detected = true;
                             }
                             } else {
                             Core.putText(camImage,
                             "Human Detected",
                             fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                             2, Core.LINE_AA, false);
                             detected = true;
                             }
                             } else {
                             //System.out.println(",d.m.mdn,dmndmf");
                             Core.putText(camImage,
                             "Human Detected",
                             fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                             2, Core.LINE_AA, false);
                             detected = true;
                             }
                             //HogDetection(camImage);

                            
                             BufferedImage orgImage = frame.getBufferedImage();
                             orgImage = resize(orgImage, 640, 480);

                             */
                            //ImageIcon icon = new ImageIcon(orgImage);
                            BufferedImage image = MatToBufferedImage(camImage);
                            ImageIcon icon = new ImageIcon(image);
                            jLabel3.setIcon(icon);

                            jLabel3.repaint();
                            jLabel3.revalidate();
                            jPanel3.repaint();
                            jLabel3.revalidate();

                            if (detected) {
                                Object[] datas = {id};
                                try {
                                    videoAccessDBAdapter.updateDetails(datas);
                                } catch (SQLException ex) {
                                    Logger.getLogger(ShowAll.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(ShowAll.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                            detected = false;
                        } else {
                            capture = opencv_highgui.cvCreateFileCapture(filePath);
                        }
                    } else {
                        //
                    }
                }

            }
        }.start();
    }

    public void pauseVideo() {
        if (jButton2.getText().equals("Pause")) {
            jButton2.setText("Start");
        } else {
            jButton2.setText("Pause");
        }
        pause = !pause;
        System.out.println(pause);
    }

    public void stopVideo() {
        capture = null;
        jLabel3.setIcon(null);
    }

    public int HogDetection(Mat mat) {
        // CHECKSTYLE:OFF MagicNumber - Magic numbers here for illustration
        hog.detectMultiScale(mat, foundLocations, foundWeights, 0.0,
                winStride, padding, 1.05, 2.0, false);
        // CHECKSTYLE:ON MagicNumber
        if (foundLocations.rows() > 0) {
            framesWithPeople++;
            List<Double> weightList = foundWeights.toList();
            List<Rect> rectList = foundLocations.toList();
            int i = 0;
            for (Rect rect : rectList) {
                rectPoint1.x = rect.x;
                rectPoint1.y = rect.y;
                rectPoint2.x = rect.x + rect.width;
                rectPoint2.y = rect.y + rect.height;
                // Draw rectangle around fond object
                Core.rectangle(mat, rectPoint1, rectPoint2, rectColor, 2);

                fontPoint.x = rect.x;
                // CHECKSTYLE:OFF MagicNumber - Magic numbers here for
                // illustration
                fontPoint.y = rect.y - 4;
                // CHECKSTYLE:ON MagicNumber
                // Print weight
                // CHECKSTYLE:OFF MagicNumber - Magic numbers here for
                // illustration
                Core.putText(mat,
                        String.format("%1.2f", weightList.get(i)),
                        fontPoint, Core.FONT_HERSHEY_PLAIN, 1.5, fontColor,
                        2, Core.LINE_AA, false);
                // CHECKSTYLE:ON MagicNumber
                i++;
            }

        }

        frames++;
        return foundLocations.rows();
    }

    public BufferedImage MatToBufferedImage(Mat frame) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

    public Mat substractBackground(Mat mat) {

        Mat mask = new Mat();

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
        backgroundSubtractor.apply(mat, mask, 1);
        Mat output = new Mat();
        //mask.copyTo(output, mask);

        mat.copyTo(output, mask);
        //loadClassifier(mat);

        return output;
    }

    public int HogDetector(Mat mat) {

        final MatOfRect foundLocations = new MatOfRect();
        final MatOfDouble foundWeights = new MatOfDouble();
        final Size winStride = new Size(8, 8);
        final Size padding = new Size(32, 32);
        //System.out.println("dsdsdd");
        if (mat != null) {

            hog.detectMultiScale(mat, foundLocations, foundWeights, 0.0, winStride, padding, 1.05, 2.0, false);

            double[] toArray = foundWeights.toArray();
            int good_humans = 0;
            double[] weights_array = foundWeights.toArray();

            for (int i = 0; i < weights_array.length; i++) {
                if (weights_array[i] < 0.5) {
                    good_humans++;
                }
            }

            System.out.println(good_humans);
            return good_humans;
        }
        return 0;

    }

    public int pedestrianDetactor(Mat image) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect eyeDetections = new MatOfRect();
        pedestrainDetector.detectMultiScale(image, eyeDetections);
        for (Rect rect : eyeDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 2);
        }
        return eyeDetections.toArray().length;
    }

    public int eyeDetactor(Mat image) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect eyeDetections = new MatOfRect();
        eyeDetector.detectMultiScale(image, eyeDetections);
        for (Rect rect : eyeDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return eyeDetections.toArray().length;
    }

    public int faceDetactor(Mat image) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect eyeDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, eyeDetections);
        for (Rect rect : eyeDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255, 0, 0), 2);
        }
        return eyeDetections.toArray().length;
    }

    public int rightEarDetactor(Mat image) {
        // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        rightEarDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }

    public int leftEarDetactor(Mat image) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        leftEarDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }

    public int upperBodyDetactor(Mat image) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        upperbodyDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }

    public int lowerBodyDetactor(Mat image) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MatOfRect earDetections = new MatOfRect();
        lowerbodyDetector.detectMultiScale(image, earDetections);
        for (Rect rect : earDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return earDetections.toArray().length;
    }
}