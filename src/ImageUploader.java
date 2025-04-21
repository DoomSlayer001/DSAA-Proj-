import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUploader extends JFrame {

    public ImageUploader() {
        // 设置主窗口
        setTitle("图片上传程序");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建上传按钮
        JButton uploadButton = new JButton("上传图片");
        uploadButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        uploadButton.setPreferredSize(new Dimension(150, 60));

        // 添加按钮点击事件
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // 设置文件过滤器（只显示图片文件）
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "图片文件", "jpg", "jpeg", "png", "gif", "bmp");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(ImageUploader.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // 读取图片文件
                        BufferedImage originalImage = ImageIO.read(selectedFile);
                        if (originalImage != null) {
                            // 创建新窗口显示图片
                            showImageWindow(originalImage);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ImageUploader.this,
                                "无法读取文件: " + ex.getMessage(),
                                "错误",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 设置布局
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
        add(uploadButton);
    }

    private void showImageWindow(BufferedImage image) {
        // 创建新窗口
        JFrame imageFrame = new JFrame("图片预览");
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 缩放图片以适应窗口
        Image scaledImage = getScaledImage(image, 800, 600);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        // 添加滚动条
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setPreferredSize(new Dimension(800, 600));

        imageFrame.add(scrollPane);
        imageFrame.pack();
        imageFrame.setLocationRelativeTo(null);
        imageFrame.setVisible(true);
    }

    private Image getScaledImage(BufferedImage original, int maxWidth, int maxHeight) {
        // 计算缩放比例
        double widthRatio = (double) maxWidth / original.getWidth();
        double heightRatio = (double) maxHeight / original.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (original.getWidth() * ratio);
        int newHeight = (int) (original.getHeight() * ratio);

        return original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageUploader().setVisible(true);
            }
        });
    }
}