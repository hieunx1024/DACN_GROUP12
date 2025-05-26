<%@ page pageEncoding="utf-8" %>
	<style>
		.custom-footer {
			background-color: #355a7e;
			/* màu nền tối */
			color: #f8f9fa;
			/* màu chữ sáng */
			padding: 20px 0;
			font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		}

		.custom-footer a {
			color: #adb5bd;
			text-decoration: none;
			margin: 0 10px;
			transition: color 0.3s ease;
		}

		.custom-footer a:hover {
			color: #ffc107;
			/* màu vàng khi hover */
			text-decoration: underline;
		}

		.custom-footer .glyphicon {
			margin-right: 5px;
		}

		.custom-footer .panel-footer {
			background-color: #355a7e;
			/* nền tối hơn */
			font-size: 0.9em;
			padding: 10px 0;
			margin-top: 15px;
		}
	</style>

	<div class="panel custom-footer text-center">
		<div class="panel-body">
			<a href="#"><span class="glyphicon glyphicon-home"></span> Home</a> |
			<a href="#"><span class="glyphicon glyphicon-info-sign"></span> About Us</a> |
			<a href="#"><span class="glyphicon glyphicon-phone-alt"></span> Contact Us</a> |
			<a href="#"><span class="glyphicon glyphicon-envelope"></span> Feedback</a> |
			<a href="#"><span class="glyphicon glyphicon-question-sign"></span> FAQs</a>
		</div>
		<div class="panel-footer">
			Spring Boot &copy;2025. All rights reserved.
		</div>
	</div>