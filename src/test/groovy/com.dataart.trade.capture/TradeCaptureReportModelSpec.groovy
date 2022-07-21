package com.dataart.trade.capture

import spock.lang.Specification


class TradeCaptureReportModelSpec extends Specification {

    TradeCaptureReportModel baseTrade;
    def expectedLastQty = Double.valueOf(1.1)
    def expectedLastPx = Double.valueOf(2.2)

    def setup() {
        baseTrade = new TradeCaptureReportModel();
        baseTrade.setExecutedSize(expectedLastQty)
        baseTrade.setExecutedPrice(expectedLastPx)
    }

    def when_no_required_fields_then_illegal_argument_exception() {
        given:
        def trade = new TradeCaptureReportModel()

        when:
        trade.toTradeCaptureReport()

        then:
        IllegalArgumentException exception = thrown()
        exception.message == "Not valid arguments for creating TradeCaptureReport"
    }

    def when_passed_required_fields_then_fix_message_created() {
        given:
        def expectedLastQty = Double.valueOf(1.1)
        def expectedLastPx = Double.valueOf(2.2)
        def expectedMessage = "8=FIXT.1.1\u00019=96\u000135=AE\u000131=2.2\u000132=1.1\u0001552=1\u0001453=1\u0001448=\u0001447=N\u0001452=0\u0001802=1\u0001523=\u0001803=0\u00011115= \u000138=0\u0001711=1\u0001311=\u00011045=0\u000110=012\u0001"

        def trade = new TradeCaptureReportModel()
        trade.setExecutedSize(expectedLastQty)
        trade.setExecutedPrice(expectedLastPx)

        when:
        def message = trade.toTradeCaptureReport().toString()

        then:
        message == expectedMessage
    }

    def when_trade_report_Id_then_fix_message_contains_tag_571() {
        given:
        baseTrade.setTradeReportId("TRADE_REPORT_ID")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("571=TRADE_REPORT_ID")
    }

    def when_trade_Id_then_fix_message_contains_tag_1003() {
        given:
        baseTrade.setMatchId("TRADE_ID")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("1003=TRADE_ID")
    }

    def when_trade_link_Id_then_fix_message_contains_tag_820() {
        given:
        baseTrade.setTradeLinkId("TRADE_LINK_ID")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("820=TRADE_LINK_ID")
    }

    def when_trade_report_ref_id_then_fix_message_contains_tag_572() {
        given:
        baseTrade.setTradeReportRefId("TRADE_REPORT_REF_ID")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("572=TRADE_REPORT_REF_ID")
    }

    def when_trade_report_type_then_fix_message_contains_tag_856() {
        given:
        baseTrade.setTradeReportType(4)

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("856=4")
    }

    def when_exec_type_then_fix_message_contains_tag_150() {
        given:
        baseTrade.setExecType('H' as char)

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("150=H")
    }

    def when_trade_report_trans_type_then_fix_message_contains_tag_487() {
        given:
        baseTrade.setTradeReportTransType(1)

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("487=1")
    }

    def when_match_status_then_fix_message_contains_tag_573() {
        given:
        baseTrade.setMatchStatus('1' as char)

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("573=1")
    }

    def when_transact_time_then_fix_message_contains_tag_60_and_1125() {
        given:
        baseTrade.setTransactTime("2022-06-22T11:32:40.526")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("60=20220622-11:32:40.526")
        message.contains("1125=20220622")
    }

    def when_executed_size_then_fix_message_contains_tag_32() {
        given:
        baseTrade.setExecutedSize(20.50)

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("32=20.5")
    }

    def when_price_type_then_fix_message_contains_tag_423() {
        given:
        baseTrade.setPriceType(20)

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("423=20")
    }

    def when_executed_price_then_fix_message_contains_tag_31() {
        given:
        baseTrade.setExecutedPrice(2.50)

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("31=2.5")
    }

    def when_currency_then_fix_message_contains_tag_15() {
        given:
        baseTrade.setCurrency("PLN")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("15=PLN")
    }

    def when_match_type_then_fix_message_contains_tag_574() {
        given:
        baseTrade.setMatchType("4")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("574=4")
    }

    def when_symbol_then_fix_message_contains_tag_55() {
        given:
        baseTrade.setSymbol("EUR")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("55=EUR")
    }

    def when_security_type_then_fix_message_contains_tag_167() {
        given:
        baseTrade.setSecurityType("FXSPOT")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("167=FXSPOT")
    }

    def when_settlement_date_then_fix_message_contains_tag_64() {
        given:
        baseTrade.setSettlementDate("2022-06-22T11:32:40.526")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("64=2022-06-22T11:32:40.526")
    }

    def when_swap_then_fix_message_contains_tag_555() {
        given:
        TradeCaptureReportModel.Leg leg = new TradeCaptureReportModel.Leg()
        TradeCaptureReportModel.Leg leg2 = new TradeCaptureReportModel.Leg()
        baseTrade.setLegs([leg, leg2])
        baseTrade.setSecurityType("FXSWAP")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("555=2")
    }

    def when_no_swap_then_fix_message_not_contains_tag_555() {
        given:
        baseTrade.setSecurityType("FXSPOT")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        !message.contains("555=")
    }

    def when_leg_settlement_date_then_fix_message_contains_tag_588() {
        given:
        TradeCaptureReportModel.Leg leg = new TradeCaptureReportModel.Leg()
        leg.setSettlDate("2022-06-22")
        baseTrade.setLegs(Collections.singletonList(leg))
        baseTrade.setSecurityType("FXSWAP")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("588=2022-06-22")
    }

    def when_leg_security_id_then_fix_message_contains_tag_602() {
        given:
        TradeCaptureReportModel.Leg leg = new TradeCaptureReportModel.Leg()
        leg.setSecurityId("1")
        baseTrade.setLegs(Collections.singletonList(leg))
        baseTrade.setSecurityType("FXSWAP")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("602=1")
    }

    def when_leg_security_id_source_then_fix_message_contains_tag_603() {
        given:
        TradeCaptureReportModel.Leg leg = new TradeCaptureReportModel.Leg()
        baseTrade.setLegs(Collections.singletonList(leg))
        baseTrade.setSecurityType("FXSWAP")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("603=4")
    }

    def when_leg_last_px_then_fix_message_contains_tag_1074() {
        given:
        TradeCaptureReportModel.Leg leg = new TradeCaptureReportModel.Leg()
        leg.setCalculatedCcyQty(2.25)
        baseTrade.setLegs(Collections.singletonList(leg))
        baseTrade.setSecurityType("FXSWAP")
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("1074=2.25")
    }

    def when_fixing_date_then_fix_message_contains_tag_541() {
        given:
        baseTrade.setFixingDate("20220622")

        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("541=20220622")
    }

    def when_side_then_fix_message_contains_tag_54() {
        given:
        baseTrade.setSide('1' as char)
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("54=1")
    }

    def when_side_liquidity_indicator_then_fix_message_contains_tag_1444() {
        given:
        baseTrade.setSideLiquidityIndicator(1)
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("1444=1")
    }

    def when_order_id_then_fix_message_contains_tag_37() {
        given:
        baseTrade.setOrderId("111-34")
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("37=111-34")
    }


    def when_client_order_id_then_fix_message_contains_tag_11() {
        given:
        baseTrade.setClientOrderId("34-111")
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("11=34-111")
    }

    def when_capacity_then_fix_message_contains_tag_528() {
        given:
        baseTrade.setCapacity('A' as char)
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("528=A")
    }

    def when_last_spot_rate_then_fix_message_contains_tag_194() {
        given:
        baseTrade.setLastSpotRate(2.15)
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("194=2.15")
    }

    def when_settle_currency_then_fix_message_contains_tag_120() {
        given:
        baseTrade.setSettleCurrency("EUR")
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("120=EUR")
    }

    def when_calculated_ccy_qty_then_fix_message_contains_tag_1056() {
        given:
        baseTrade.setCalculatedCcyQty(2.25)
        when:
        def message = baseTrade.toTradeCaptureReport().toString()

        then:
        message.contains("1056=2.25")
    }


}
