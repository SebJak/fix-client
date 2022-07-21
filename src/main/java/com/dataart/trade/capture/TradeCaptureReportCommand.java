package com.dataart.trade.capture;

import com.dataart.client.Client;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.fix50sp2.TradeCaptureReport;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class TradeCaptureReportCommand {

    Client client;

    public void execute(TradeCaptureReportModel model) {
        log.debug("Starting processing Trade Capture Report for {}", model);
        TradeCaptureReport tradeCaptureReport = model.toTradeCaptureReport();
        client.sendMessage(tradeCaptureReport);
    }
}
