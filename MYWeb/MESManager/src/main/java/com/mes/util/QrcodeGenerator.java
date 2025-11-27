/**
      <dependency>
          <groupId>com.google.zxing</groupId>
          <artifactId>core</artifactId>
          <version>3.4.1</version>
      </dependency>
      <dependency>
          <groupId>com.google.zxing</groupId>
          <artifactId>javase</artifactId>
          <version>3.4.1</version>
      </dependency>
    <dependency>
**/

package com.mes.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @Author xhwang
 * @Time 20204年5月7日 下午7:57:14
 * @Description:带logo的二维码
 */
public class QrcodeGenerator {

    private static final Integer QRCode$width = 200;// 生成二维码图片的宽度
    private static final Integer QRCode$height = 200;// 生成二维码图片的高度

    private static final int QRCOLOR = 0xFF000000; // 默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色白色


    /**
     * 二维码绘制logo
     *
     * @param QRClogCodePath 二维码图片存放路径
     * @param logoImgPath 二维码logo图片路径
     * @param text 二维码附带的信息
     * **/
    public static void qrcodeGenerator(
            String QRClogCodePath,
            String logoImgPath,
            String text)
            throws IOException, WriterException {
        /**format 图片格式 /jpg,png,gif..........**/
        String format="png";
        createLogoQRCode(text,QRClogCodePath,format,logoImgPath);
    }

    /**
     * 带logo的二维码生成方法
     */
    public static void createLogoQRCode(String text, String QRClogCodePath, String format,String logoImgPath )
            throws WriterException, IOException {
        File file=new File(logoImgPath);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, QRCode$width, QRCode$height,
                getDecodeHintType());
        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? QRCOLOR : BGWHITE);
            }
        }
        BufferedImage bufferedImage = null;
        if (file != null) {
            bufferedImage = encodeImgLogo(image, file);// 绘制二维码自定义中心位置图片,注意实际格式，更改图片后缀名为满足的格式无效
        } else {
            // logo图片
            bufferedImage = encodeImgLogo(image, new File(QRClogCodePath));// 绘制二维码中心位置logo图片
        }
        if (bufferedImage == null) {
           System.out.println("获取二维码字节流失败，生成失败！");
           System.exit(-1);//强制退出
        }
        // 重新生成带logo的二维码图片
        ImageIO.write(bufferedImage, format, new File(QRClogCodePath));// 生成带中心位置图标的二维码图片
    }


    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    public static Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);
        return hints;
    }

    /**
     * 二维码绘制logo
     *
     * @param image
     * @param logoImg
     *            logo图片文件 格式：JPG, jpg, tiff, pcx, PCX, bmp, BMP, gif, GIF,
     *            WBMP, png, PNG, raw, RAW, JPEG, pnm, PNM, tif, TIF, TIFF,
     *            wbmp, jpeg
     * @throws IOException
     */
    public static BufferedImage encodeImgLogo(BufferedImage image, File logoImg) throws IOException {
        // String[] names = ImageIO.getReaderFormatNames();
        // System.out.println(Arrays.toString(names));
        // 能读取的图片格式：JPG, jpg, tiff, pcx, PCX, bmp, BMP, gif, GIF, WBMP, png, PNG, raw, RAW, JPEG, pnm, PNM, tif, TIF, TIFF, wbmp, jpeg
        // 读取二维码图片
        // 获取画笔
        Graphics2D g = image.createGraphics();
        // 读取logo图片
        BufferedImage logo = ImageIO.read(logoImg);
        // 设置二维码大小，太大，会覆盖二维码，此处20%
        int logoWidth = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10)
                : logo.getWidth(null);
        int logoHeight = logo.getHeight(null) > image.getHeight() * 2 / 10 ? (image.getHeight() * 2 / 10)
                : logo.getHeight(null);
        // 设置logo图片放置位置
        // 中心
        int x = (image.getWidth() - logoWidth) / 2;
        int y = (image.getHeight() - logoHeight) / 2;
        // 右下角，15为调整值
        // int x = twodimensioncode.getWidth() - logoWidth-15;
        // int y = twodimensioncode.getHeight() - logoHeight-15;
        // 开始合并绘制图片
        g.drawImage(logo, x, y, logoWidth, logoHeight, null);
        g.drawRoundRect(x, y, logoWidth, logoHeight, 15, 15);
        // logo边框大小
        g.setStroke(new BasicStroke(2));
        // logo边框颜色
        g.setColor(Color.WHITE);
        g.drawRect(x, y, logoWidth, logoHeight);
        g.dispose();
        logo.flush();
        image.flush();
        return image;
    }

}
