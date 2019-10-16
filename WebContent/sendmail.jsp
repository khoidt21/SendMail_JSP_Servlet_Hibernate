<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.hibernate.been.User"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- Contact Section -->
<section class="page-section" id="contact">
	<div class="container">
		<!-- Contact Section Form -->
		<div class="row">
			<div class="col-lg-8 mx-auto" style="margin-top: 20px">
				<!-- To configure the contact form email address, go to mail/contact_me.php and update the email address in the PHP file on line 19. -->

				<form name="form1" id="form1" action="SendMailServlet" method="POST">
				
					<div class="control-group">
						<h5 style="color: red; font-weight: bold;">Gửi Email</h5>
					</div>
				
					<div class="control-group">
						<label>To:</label> <span style="color: red">(*)</span>
					</div>
					<div class="control-group">
						<div
							class="form-group floating-label-form-group controls mb-0 pb-2"
						>
							<input class="form-control" id="to" type="text"
								name="to" value=""
							>
						</div>
					</div>
					<div class="control-group">
						<label>CC:</label> <span style="color: red">(*)</span>
					</div>
					<div class="control-group">
						<div
							class="form-group floating-label-form-group controls mb-0 pb-2"
						>
							<input class="form-control" id="cc" type="text"
								name="cc" value=""
							>
						</div>
					</div>
					<div class="control-group">
						<label>Tiêu đề</label> <span style="color: red">(*)</span>
					</div>
					<div class="control-group">
						<div
							class="form-group floating-label-form-group controls mb-0 pb-2"
						>
							<input class="form-control" id="title" type="text"
								name="title"
							>
						</div>
					</div>
				<div class="control-group">
					<label>Nội dung <span style="color: red">(*)</span></label>
				</div>
				<div class="control-group">
					<div
							class="form-group floating-label-form-group controls mb-0 pb-2"
						>
							<textarea id="content" name="content"></textarea>
						</div>
				</div>
				
				<div class="control-group">
					<div class="form-group controls mb-0 pb-2"></div>
				</div>
				<div id="success"></div>
				<div class="form-group">
					
						<button type="submit" class="btn btn-primary btn-sm"
							id="sendMessageButton"
						>Submit</button>
				</div>
				</form>
			</div>
		</div>
		
	</div>
</section>
<%@ include file="footer.jsp"%>
</body>
<style>
.floating-label-form-group label {
	opacity: 1 !important;
}

.bg-secondary {
	background-color: #dd3d31 !important;
}

.copyright {
	background-color: #000 !important;
}

.footer {
	background-color: #dd3d31 !important;
}
.mce-notification {display: none !important;}
</style>
<script type="text/javascript">
tinymce.init({
	  selector: 'textarea#content',
	  height: 200,
	  menubar: true,
	  plugins: [
	    'advlist autolink lists link image charmap print preview anchor',
	    'searchreplace visualblocks code fullscreen',
	    'insertdatetime media table paste code help wordcount'
	  ],
	  toolbar: 'undo redo | formatselect | bold italic backcolor | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat | help',
	  content_css: [
	    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
	    '//www.tiny.cloud/css/codepen.min.css'
	  ]
	});
</script>
<script>
	$(document)
			.ready(
					function() {
						$('#form1')
								.submit(
										function(e) {
											var to = $('#to').val();
											var cc = $('#cc').val();
											var title = $('#title').val();
											var content = $('#content').val();
											
											var valid = true;
											$(".error").remove();
											if (to.length < 1) {
												$('#to')
														.after(
																'<span class="error" style="color:red">This field is required</span>');
												valid = false;
											} 
											if (cc.length < 1) {
												$('#cc')
														.after(
																'<span class="error" style="color:red">This field is required</span>');
												valid = false;
											}
											if (title.length < 1) {
												$('#title')
														.after(
																'<span class="error" style="color:red">This field is required</span>');
												valid = false;
											} 
											if (content.length < 1) {
												$('#content')
														.after(
																'<span class="error" style="color:red">This field is required</span>');
												valid = false;
											} 
											
											return valid;
										});
					});
</script>
</html>
