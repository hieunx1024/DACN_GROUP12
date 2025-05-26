<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8" />
                <title>Phản hồi</title>
                <link rel="stylesheet" href="/static/css/bootstrap.min.css" />
            </head>

            <body>
                <div class="container" style="margin-top:50px; max-width:600px;">
                    <h2 class="text-center">Gửi phản hồi cho chúng tôi</h2>

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">${successMessage}</div>
                    </c:if>

                    <form:form method="post" action="/home/feedback" modelAttribute="feedbackForm">
                        <div class="form-group">
                            <label for="name">Họ tên:</label>
                            <form:input path="name" cssClass="form-control" id="name" placeholder="Nhập họ tên" />
                            <form:errors path="name" cssClass="text-danger" />
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <form:input path="email" cssClass="form-control" id="email" placeholder="Nhập email" />
                            <form:errors path="email" cssClass="text-danger" />
                        </div>
                        <div class="form-group">
                            <label for="message">Nội dung phản hồi:</label>
                            <form:textarea path="message" cssClass="form-control" id="message" rows="5"
                                placeholder="Nội dung phản hồi" />
                            <form:errors path="message" cssClass="text-danger" />
                        </div>
                        <button type="submit" class="btn btn-primary">Gửi phản hồi</button>
                    </form:form>
                </div>

                <script src="/static/js/jquery.min.js"></script>
                <script src="/static/js/bootstrap.min.js"></script>
            </body>

            </html>