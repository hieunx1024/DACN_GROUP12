<%@ page pageEncoding="utf-8" %>
	<%@ include file="/common/taglib.jsp" %>
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button class="navbar-toggle" data-toggle="collapse" data-target="#menu">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/home/index">
						<span class="glyphicon glyphicon-home"></span>Trang chủ
					</a>
				</div>
				<div class="collapse navbar-collapse" id="menu">
					<ul class="nav navbar-nav">
						<li><a href="/home/about"><span class="glyphicon glyphicon-info-sign"></span> Về chúng tôi</a>
						</li>
						<li><a href="/home/contact"><span class="glyphicon glyphicon-phone-alt"></span> Liên hệ</a></li>
						<li><a href="/home/feedback"><span class="glyphicon glyphicon-envelope"></span> Phản hồi</a>
						</li>
						<li><a href="/home/faq"><span class="glyphicon glyphicon-question-sign"></span> Câu hỏi</a></li>
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">
								<span class="glyphicon glyphicon-user"></span> Tài Khoản
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<c:choose>
									<c:when test="${empty sessionScope.user}">
										<li><a href="/account/login">Đăng nhập</a></li>
										<li><a href="/account/forgot">Quên mật khẩu</a></li>
										<li><a href="/account/register">Đăng kí</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="/account/logoff">Đăng xuất</a></li>
										<li><a href="/account/change">Thay đổi mật khẩu</a></li>
										<li><a href="/account/edit">Chỉnh sửa thông tin</a></li>
										<li class="divider"></li>
										<li><a href="/order/list">Đơn hàng</a></li>
										<li><a href="/order/items">Mặt hàng đã mua</a></li>
										<c:if test="${sessionScope.user.admin}">
											<li class="divider"></li>
											<li><a href="/admin/home/index">Quản trị</a></li>
										</c:if>
									</c:otherwise>
								</c:choose>
							</ul>
						</li>
					</ul>
					<ul class="nav navbar-nav navbar-right nn-lang">
						<li><a href="#" title="English"><img src="/static/images/en.png" alt="English"
									class="language-flag"></a></li>
						<li><a href="#" title="Tiếng Việt"><img src="/static/images/vi.png" alt="Tiếng Việt"
									class="language-flag"></a></li>
					</ul>
				</div>
			</div>
		</nav>

		<style>
			.navbar-inverse {
				background-color: #2c3e50;
				border-color: #2c3e50;
			}

			.navbar-inverse .navbar-brand,
			.navbar-inverse .navbar-nav>li>a {
				color: #ecf0f1;
			}

			.navbar-inverse .navbar-brand:hover,
			.navbar-inverse .navbar-nav>li>a:hover {
				color: #f39c12;
			}

			.navbar-inverse .navbar-nav>.active>a {
				color: #f39c12;
				background-color: transparent;
			}

			.navbar-inverse .navbar-nav>li>a {
				padding: 15px 20px;
			}

			.navbar-inverse .dropdown-menu {
				background-color: #34495e;
				border-radius: 5px;
			}

			.navbar-inverse .dropdown-menu>li>a {
				color: #ecf0f1;
				padding: 10px 20px;
				text-align: center;
				/* Căn giữa item dropdown */
			}

			.navbar-inverse .dropdown-menu>li>a:hover {
				background-color: #f39c12;
				color: #fff;
			}

			.language-flag {
				width: 24px;
				height: 16px;
				border-radius: 4px;
				transition: transform 0.3s ease;
			}

			.language-flag:hover {
				transform: scale(1.1);
			}


			body {
				padding-top: 70px;
				/
			}


			.navbar-nav {
				float: none !important;
				display: inline-block;
			}

			.navbar-collapse {
				text-align: center;
			}
		</style>


		<!-- jQuery and Bootstrap JS -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>