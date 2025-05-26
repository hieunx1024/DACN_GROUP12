<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
        <meta charset="UTF-8" />
        <title>Câu hỏi thường gặp (FAQ)</title>
        <link rel="stylesheet" href="/static/css/bootstrap.min.css" />
        <style>
            .panel-heading .faq-question {
                cursor: pointer;
            }
        </style>
    </head>

    <body>
        <div class="container" style="margin-top:50px; max-width:800px;">
            <h2 class="text-center">Câu hỏi thường gặp (FAQ)</h2>
            <div class="panel-group" id="faqAccordion" role="tablist" aria-multiselectable="true">

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="heading1">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#faqAccordion" href="#collapse1"
                                aria-expanded="true" aria-controls="collapse1" class="faq-question">
                                1. Tôi có thể mua hàng ở đâu?
                            </a>
                        </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="heading1">
                        <div class="panel-body">
                            Bạn có thể mua hàng trực tiếp trên website hoặc tại các cửa hàng của chúng tôi trên toàn
                            quốc.
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="heading2">
                        <h4 class="panel-title">
                            <a class="collapsed faq-question" role="button" data-toggle="collapse"
                                data-parent="#faqAccordion" href="#collapse2" aria-expanded="false"
                                aria-controls="collapse2">
                                2. Chính sách đổi trả hàng như thế nào?
                            </a>
                        </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading2">
                        <div class="panel-body">
                            Quý khách có thể đổi trả trong vòng 7 ngày kể từ ngày nhận hàng nếu sản phẩm còn nguyên tem
                            và chưa qua sử dụng.
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="heading3">
                        <h4 class="panel-title">
                            <a class="collapsed faq-question" role="button" data-toggle="collapse"
                                data-parent="#faqAccordion" href="#collapse3" aria-expanded="false"
                                aria-controls="collapse3">
                                3. Tôi làm thế nào để liên hệ hỗ trợ?
                            </a>
                        </h4>
                    </div>
                    <div id="collapse3" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading3">
                        <div class="panel-body">
                            Bạn có thể gọi điện đến hotline 1900-1234 hoặc gửi email support@example.com để được hỗ trợ
                            nhanh nhất.
                        </div>
                    </div>
                </div>

                <!-- Thêm câu hỏi khác tương tự -->

            </div>
        </div>

        <script src="/static/js/jquery.min.js"></script>
        <script src="/static/js/bootstrap.min.js"></script>
    </body>

    </html>