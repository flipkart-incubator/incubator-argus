<!DOCTYPE html>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7"><![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8"><![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
	<!--<![endif]-->
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title> Argus - Sign In</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width">
		
		<link rel="shortcut icon" href="images/favicon.ico">
		<link href="styles/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" id="bootstrap-css">
		<link href="styles/xa.css" media="all" rel="stylesheet" type="text/css" >

		<script src="libs/bower/jquery/js/jquery.js" ></script>
		
		<script src="scripts/prelogin/XAPrelogin.js" ></script>

		<script type="text/javascript">
			$(document).ready(function() {
				/*  $('#signin-container').submit(function() {
					document.location = 'dashboard.html';
					return false;
				}); */ 

				var updateBoxPosition = function() {
					$('#signin-container').css({
						'margin-top' : ($(window).height() - $('#signin-container').height()) / 2
					});
				};
				$(window).resize(updateBoxPosition);
				setTimeout(updateBoxPosition, 50);
			});
		</script>

	</head>
	<body class="login" style="">

		<!-- Page content
		================================================== -->
		<section id="signin-container" style="margin-top: 4.5px;">
			<div class="l-logo">
				<img src="images/logo.png" alt="Argus logo">
			</div>
			<form action="" method="get" accept-charset="utf-8">
				<fieldset>
					<div class="fields">
						<label><i class="icon-user"></i> Username:</label>
						<input type="text" name="username" id="username" tabindex="1" autofocus>
						<label><i class="icon-lock"></i> Password:</label>	
						<input type="password" name="password" id="password" tabindex="2" autocomplete="off">
					</div>
					<span id="errorBox" class="help-inline" style="color:white;display:none;">The username or password you entered is incorrect..
						<i class="icon-warning-sign" style="color:#ae2817;"></i>
					</span>
					<span id="errorBoxUnsynced" class="help-inline" style="color:white;display:none;">User is not available in HDP Admin Tool. Please contact your Administrator.
						<i class="icon-warning-sign" style="color:#ae2817;"></i>
					</span>
					<button type="submit" class="btn btn-primary btn-block" id="signIn" tabindex="4" >
						Sign In
						<!--<img id="signInLoading" src="images/loading.gif" style="visibility: hidden;" /> -->
						<i id="signInLoading" class="icon-spinner icon-spin pull-right icon-sign-in"></i>
					</button>
				</fieldset>
			</form>
			<!-- <div class="social">
				<p>
					...or sign in with
				</p>

				<a href="javascript:void(0);" title="" tabindex="5" class="twitter"> <i class="icon-twitter"></i> </a>

				<a href="javascript:void(0);" title="" tabindex="6" class="facebook"> <i class="icon-facebook"></i> </a>

				<a href="javascript:void(0);" title="" tabindex="7" class="google"> <i class="icon-google-plus"></i> </a>
			</div> -->
		</section>

	</body>
</html>