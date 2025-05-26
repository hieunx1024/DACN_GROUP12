<%@ page pageEncoding="utf-8" %>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<div class="panel panel-info">
			<div class="panel-heading">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-search"></span> Search
				</h3>
			</div>
			<div class="panel-body">
				<form:form action="/product/list-by-keywords" method="get">
					<div class="input-group">
						<input name="keywords" placeholder="Keywords?" class="form-control" />
						<span class="input-group-btn">
							<button type="submit" class="btn btn-default">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</span>
					</div>
				</form:form>
			</div>
		</div>