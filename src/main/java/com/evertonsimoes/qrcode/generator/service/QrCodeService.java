package com.evertonsimoes.qrcode.generator.service;

import com.evertonsimoes.qrcode.generator.dtos.QrCodeGenerateResponse;
import com.evertonsimoes.qrcode.generator.ports.StoragePort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeService {
    private final StoragePort storage;

    public QrCodeService (StoragePort storage ){
        this.storage = storage;
    }

    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {

        byte[] pngQrCodeData;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutPutStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutPutStream);

        pngQrCodeData = pngOutPutStream.toByteArray();

        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");

        return new QrCodeGenerateResponse(url);
    }
}
