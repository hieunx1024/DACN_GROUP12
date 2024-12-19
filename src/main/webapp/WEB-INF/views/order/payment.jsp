<%@ page pageEncoding="utf-8" %>
<%@ include file="/common/taglib.jsp" %>

<!-- Shopping Cart -->
<div class="panel panel-success">
    <div class="panel-heading">
        <h4 class="panel-title">THÔNG TIN THANH TOÁN TRỰC TUYẾN TẠI VNPAY</h4>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Tên</th>
            <th>Giá trị</th>
            <th>Mô tả</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Mã giao dịch</td>
            <td>${vnpay.transactionId}</td>
            <td>Mã giao dịch tại web WEBSALES</td>
        </tr>

        <tr>
            <td>Thông tin đơn hàng</td>
            <td>${vnpay.transactionInfo}</td>
            <td>Thông tin mô tả từ WEBSALES</td>
        </tr>

        <tr>
            <td>Số tiền</td>
            <td><f:formatNumber value="${vnpay.amount}" pattern="#,###"/>
            <td>Số tiền đã thanh toán</td>
        </tr>

        <tr>
            <td>Ngày giao dịch</td>
            <td>${vnpay.transactionDate}</td>
            <td>Thời gian thực hiện giao dịch</td>
        </tr>

        <tr>
            <td>Tiền tệ</td>
            <td>VND</td>
            <td>Đơn vị tiền tệ được thanh toán</td>
        </tr>


        <tr>
            <td>Mã kết quả</td>
            <td>${vnpay.response}</td>
            <td>Trạng thái giao dịch</td>
        </tr>

        <tr>
            <td>Thông báo</td>
            <td>${vnpay.message}</td>
            <td>Thông báo từ cổng thanh toán VNPay</td>
        </tr>

        <tr>
            <td>Mã giao dịch tại VNPay</td>
            <td>${vnpay.transactionNo}</td>
            <td>Mã GD trên cổng VNPay</td>
        </tr>

        <tr>
            <td>Loại thẻ</td>
            <td>${vnpay.cardType}</td>
            <td>Thẻ thanh toán</td>
        </tr>

        <tr>
            <td>Ngân hàng</td>
            <td>${vnpay.bankCode}</td>
            <td>Ngân hàng GD</td>
        </tr>
        </tbody>
    </table>
    <div class="panel-footer">Tổng giá : ${vnpay.amount}</div>
</div>

