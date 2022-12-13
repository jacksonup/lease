package com.hdu.lease.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 * 二维码工具类
 *
 * @author chenyb46701
 * @date 2022/12/10
 */
public class QrCodeUtil {

    private static final String CHARSET = "utf-8";

    // 二维码尺寸
    private static final int QRCODE_SIZE = 400;

    public static BufferedImage createImage(String content, String bottomContent, Boolean needCompress) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;

        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                    hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int tempHeight = height;
        if (needCompress) {
            tempHeight += 30;
        }

        BufferedImage image = new BufferedImage(width, tempHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        //添加底部文字
        if (needCompress) {
            addFontImage(image, bottomContent);
        }

        return image;
    }

    /**
     * 将文明说明增加到二维码上
     *
     * @param str      文字
     * @param width    宽
     * @param height   高
     * @param fontSize 字体大小
     * @return
     */
    public static BufferedImage textToImage(String str, int width, int height, int fontSize) {
        BufferedImage textImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) textImage.getGraphics();
        //开启文字抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.BLACK);
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
        g2.setFont(font);
        LineMetrics lineMetrics = font.getLineMetrics(str, context);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        float offset = (width - fontMetrics.stringWidth(str)) / 2;
        float y = (height + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;
        g2.drawString(str, (int) offset, (int) y);
        return textImage;
    }

    /**
     * 添加 底部图片文字
     *
     * @param source      图片源
     * @param declareText 文字本文
     */
    private static void addFontImage(BufferedImage source, String declareText) {
        BufferedImage textImage = textToImage(declareText, QRCODE_SIZE, 50, 16);
        Graphics2D graph = source.createGraphics();
        //开启文字抗锯齿
        graph.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = textImage.getWidth(null);
        int height = textImage.getHeight(null);

        Image src = textImage;
        graph.drawImage(src, 0, QRCODE_SIZE - 20, width, height, null);
        graph.dispose();
    }

}
