package com.evertonsimoes.qrcode.generator.controller;

import com.evertonsimoes.qrcode.generator.dtos.QrCodeGenerateRequest;
import com.evertonsimoes.qrcode.generator.dtos.QrCodeGenerateResponse;
import com.evertonsimoes.qrcode.generator.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeService service;

    public QrCodeController(QrCodeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generateQrCode(@RequestBody QrCodeGenerateRequest request){
        try {
            QrCodeGenerateResponse response = this.service.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }

    }
}
