
package com.dataart.trade.capture;

import lombok.Data;
import quickfix.field.*;
import quickfix.fix50sp2.TradeCaptureReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

@Data
public class TradeCaptureReportModel {

    Double executedPrice;
    Double executedSize;
    String transactTime;
    String tradeReportId;
    String matchId;
    String tradeLinkId;
    String tradeReportRefId;
    Integer tradeReportType;
    Character execType;
    Integer tradeReportTransType;
    Character matchStatus;
    Integer priceType;
    String matchType;
    String symbol;
    String securityType;
    String settlementDate;
    Integer noLegs;
    List<Leg> legs;
    String fixingDate;
    String orderId;
    String clientOrderId;
    Character capacity;
    Double lastSpotRate;
    Double calculatedCcyQty;
    String currency;
    Character side;
    String sideExacId;
    Integer sideLiquidityIndicator;
    String settleCurrency;

    public TradeCaptureReport toTradeCaptureReport() {
        TradeCaptureReport result = initTradeCapture();
        putTradeReportId(result);
        putTradeId(result);
        putTradeLinkId(result);
        putTradeReportRefId(result);
        putTradeReportType(result);
        putExecType(result);
        putTradeReportTransType(result);
        putMatchStatus(result);
        putTransactTime(result);
        putPriceType(result);
        putCurrency(result);
        putMatchType(result);
        putSymbol(result);
        putSecurityType(result);
        putSettlementDate(result);
        putLegs(result);
        putTradeMaturityDate(result);
        putNoSides(result);
        putOrigTradeDate(result);
        putLastSpotRate(result);
        putNoUdelying(result);
        putSettleCurrency(result);
        putCalculatedCcyLastQty(result);
//        result.set(new ApplID("1"));
//        result.set(new ApplSeqNum());
//        result.set(new ApplLastSeqNum());
        result.set(new PreviouslyReported(false));
        return result;
    }

    @Data
    public static class Leg {
        Character settlType;
        String settlDate;
        String securityId;
        Double executedPrice;
        Double calculatedCcyQty;
        Double executedSize;

        public TradeCaptureReport.NoLegs toNoLegs() {
            TradeCaptureReport.NoLegs noLegs = new TradeCaptureReport.NoLegs();
            putLegSettlType(noLegs);
            putLegSettlDate(noLegs);
            putLegSecurityID(noLegs);
            putLegSecurityIDSource(noLegs);
            putLegLastPx(noLegs);
            putLegCalculatedCcyLastQty(noLegs);
//            noLegs.set(new LegOrderQty(executedSize));
//            noLegs.set(new NoSides());
            return noLegs;
        }

        private void putLegCalculatedCcyLastQty(TradeCaptureReport.NoLegs noLegs) {
            if (calculatedCcyQty != null) {
                noLegs.set(new LegCalculatedCcyLastQty(calculatedCcyQty));
            }
        }

        private void putLegLastPx(TradeCaptureReport.NoLegs noLegs) {
            if (executedPrice != null) {
                noLegs.set(new LegLastPx(executedPrice));
            }
        }

        private void putLegSecurityIDSource(TradeCaptureReport.NoLegs noLegs) {
            noLegs.set(new LegSecurityIDSource("4"));
        }

        private void putLegSecurityID(TradeCaptureReport.NoLegs noLegs) {
            if (isNoneBlank(securityId)) {
                noLegs.set(new LegSecurityID(securityId)); //Isin1/Isin2
            }
        }

        private void putLegSettlDate(TradeCaptureReport.NoLegs noLegs) {
            if (isNoneBlank(settlDate)) {
                noLegs.set(new LegSettlDate(settlDate));
            }
        }

        private void putLegSettlType(TradeCaptureReport.NoLegs noLegs) {
            if (settlType != null) {
                noLegs.set(new LegSettlType(settlType));
            }
        }

    }

    private TradeCaptureReport initTradeCapture() {
        if (executedSize != null && executedPrice != null) {
            LastQty lastQty = new LastQty(executedSize);
            LastPx lastPx = new LastPx(executedPrice);
            return new TradeCaptureReport(lastQty, lastPx);
        }

        throw new IllegalArgumentException("Not valid arguments for creating TradeCaptureReport");
    }

    private void putSettleCurrency(TradeCaptureReport result) {
        if(isNoneBlank(settleCurrency)) {
            result.set(new SettlCurrency(settleCurrency));
        }
    }

    private void putNoUdelying(TradeCaptureReport result) {
        TradeCaptureReport.NoUnderlyings noUnderlyings = new TradeCaptureReport.NoUnderlyings();
        noUnderlyings.set(new UnderlyingSymbol("1")); //TODO
//        noUnderlyings.set(new UnderlyingFXRate()); //TODO
        result.addGroup(noUnderlyings);
    }

    private void putCurrency(TradeCaptureReport result) {
        if(isNoneBlank(currency)) {
            result.set(new Currency(currency));
        }
    }

    private void putTransactTime(TradeCaptureReport result) {
        if(isNoneBlank(transactTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            result.set(new TransactTime(LocalDateTime.parse(transactTime, formatter)));
        }
    }

    private void putLegs(TradeCaptureReport result) {
        if(legs != null && "FXSWAP".equals(securityType)) {
            for (TradeCaptureReportModel.Leg leg : legs) { //2 if SWAP. Otehrwise do not send
                result.addGroup(leg.toNoLegs());
            }
        }
    }

    private void putCalculatedCcyLastQty(TradeCaptureReport result) {
        if (calculatedCcyQty != null) {
            result.set(new CalculatedCcyLastQty(calculatedCcyQty));
        }
    }

    private void putLastSpotRate(TradeCaptureReport result) {
        if (lastSpotRate != null) {
            result.set(new LastSpotRate(lastSpotRate));
        }
    }

    private void putOrigTradeDate(TradeCaptureReport result) {
        if (isNoneBlank(transactTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
            LocalDateTime dateTime = LocalDateTime.parse(transactTime);
//            result.set(new OrigTradeDate(dateTime.format(formatter)));
            result.set(new TradeDate(dateTime.format(formatter)));
        }
    }

    private void putNoSides(TradeCaptureReport result) {
        TradeCaptureReport.NoSides noSides = new TradeCaptureReport.NoSides();
        TradeCaptureReport.NoSides.NoPartyIDs noPartyIDs = new TradeCaptureReport.NoSides.NoPartyIDs();
        noPartyIDs.set(new PartyIDSource('6')); //TODO
        noPartyIDs.set(new PartyID("1")); //TODO
        noPartyIDs.set(new PartyRole(3));//TODO

        TradeCaptureReport.NoSides.NoPartyIDs.NoPartySubIDs noPartySubIDs = new TradeCaptureReport.NoSides.NoPartyIDs.NoPartySubIDs();
        noPartySubIDs.set(new PartySubID("1")); //TODO
        noPartySubIDs.set(new PartySubIDType(4)); //TODO

        noPartyIDs.addGroup(noPartySubIDs);
//        noPartyIDs.set(new PartySubID()); //TODO
        noSides.addGroup(noPartyIDs);
//        noSides.set(new OrderCategory()); //TODO
        noSides.set(new OrderQty()); //TODO
        if(sideLiquidityIndicator != null) {
            noSides.set(new SideLiquidityInd(sideLiquidityIndicator));
        }
        if (isNoneBlank(orderId)) {
            noSides.set(new OrderID(orderId));
        }
        if (isNoneBlank(clientOrderId)) {
            noSides.set(new ClOrdID(clientOrderId));
        }
        if (capacity != null) {
            noSides.set(new OrderCapacity(capacity));
        }

        if (side != null) {
            noSides.set(new Side(side));
        }
        if(isNoneBlank(sideExacId)) {
            noSides.set(new SideExecID(sideExacId));
        }

        result.addGroup(noSides);
    }

    private void putTradeMaturityDate(TradeCaptureReport result) {
        if (isNoneBlank(fixingDate)) {
            result.set(new MaturityDate(fixingDate));
        }
    }

    private void putSettlementDate(TradeCaptureReport result) {
        if (isNoneBlank(settlementDate)) {
            result.set(new SettlDate(settlementDate));
        }
    }

    private void putSecurityType(TradeCaptureReport result) {
        if (isNoneBlank(securityType)) {
            result.set(new SecurityType(securityType));
        }
    }

    private void putSymbol(TradeCaptureReport result) {
        if (isNoneBlank(symbol)) {
            result.set(new Symbol(symbol));
        }
    }

    private void putMatchType(TradeCaptureReport result) {
        if (isNoneBlank(matchType)) {
            result.set(new MatchType(matchType));
        }
    }

    private void putPriceType(TradeCaptureReport result) {
        //TODO add logic
        if (priceType != null) {
            result.set(new PriceType(priceType));
        }
    }

    private void putMatchStatus(TradeCaptureReport result) {
        if (matchStatus != null) {
            result.set(new MatchStatus(matchStatus));
        }
    }

    private void putTradeReportTransType(TradeCaptureReport result) {
        if (tradeReportTransType != null) {
            result.set(new TradeReportTransType(tradeReportTransType));
        }
    }

    private void putExecType(TradeCaptureReport result) {
        if (execType != null) {
            result.set(new ExecType(execType));
        }
    }

    private void putTradeReportType(TradeCaptureReport result) {
        if (tradeReportType != null) {
            result.set(new TradeReportType(tradeReportType));
        }
    }

    private void putTradeReportRefId(TradeCaptureReport result) {
        if (isNoneBlank(tradeReportRefId)) {
            result.set(new TradeReportRefID(tradeReportRefId));
        }
    }

    private void putTradeLinkId(TradeCaptureReport result) {
        if (isNoneBlank(tradeLinkId)) {
            result.set(new TradeLinkID(tradeLinkId));
        }
    }

    private void putTradeId(TradeCaptureReport result) {
        if (isNoneBlank(matchId)) {
            result.set(new TradeID(matchId));
        }
    }

    private void putTradeReportId(TradeCaptureReport result) {
        if (isNoneBlank(tradeReportId)) {
            result.set(new TradeReportID(tradeReportId));
        }
    }
}
