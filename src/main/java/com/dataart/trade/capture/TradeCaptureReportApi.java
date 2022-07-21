package com.dataart.trade.capture;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class TradeCaptureReportApi {

    TradeCaptureReportCommand command;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody TradeCaptureReportModel model) {
        log.info("Trade Capture Report API received message");
        command.execute(model);
        return ResponseEntity.ok("OK");
    }
}
