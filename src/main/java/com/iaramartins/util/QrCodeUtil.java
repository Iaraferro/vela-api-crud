package com.iaramartins.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import java.nio.file.Path;



public class QrCodeUtil {
    public static void gerarQRCode(String texto, String caminhoArquivo, int largura, int altura) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, largura, altura);

        Path caminho = FileSystems.getDefault().getPath(caminhoArquivo);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", caminho);
    }

    public static byte[] gerarQRCodeEmBytes(String texto, int largura, int altura) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, largura, altura);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", stream);
        return stream.toByteArray();
    }
}
