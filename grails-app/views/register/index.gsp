<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Home</title>
<style>
	.errors {
		display: inline-block;
		color: red;
	}
	label {
		width: 140px;
	}
</style>
</head>
<body>
	<g:if test='${flash.message}'>
		<div class="message">${flash.message}</div>
	</g:if>
	<div id='content' class='form' style='width: 380px;'>
		<h1>Registration</h1>
		<div class='form-body'>
			<form action='register' method='POST' id='loginForm' class='cssform' autocomplete='off'>
				<p>
					<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
					<input type='text' class='text_' name='j_username' id='username'/><div id='validUser' class='errors'>Usernames must be 3 alpha-numeric characters or more.</div>
				</p>
	
				<p>
					<label for='email'>Email:</label>
					<input type='email' class='text_' name='email' id='email'/><div id='validEmail' class='errors'>Please enter a valid email.</div>
				</p>
	
				<p>
					<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
					<input type='password' class='text_' name='j_password' id='password' style="display: inline-block;"/><div id='invalid' class='errors'>Passwords must be at least 7 characters</div>
				</p>
	
				<p>
					<label for='password2'><g:message code="springSecurity.login.password.label"/> (Again):</label>
					<input type='password2' class='text_' name='j_password2' id='password2' style="display: inline-block;"/><div id='noMatch' class='errors'>Passwords must match.</div>
				</p>
	
				<p>
					<label for='checkbox'>I accept the <a href='/home/terms'>Terms & Conditions.</a></label>
					<input type='checkbox' class='text_' name='terms' id='terms' style="display: inline-block;"/><div id='noTerms' class='errors'>Please accept the Terms & Conditions.</div>
				</p>
			</form>
			<p>
				<button class='buttons' id="submit2" onclick='validate()'>Register</button>
			</p>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('#invalid').hide();
			$('#noMatch').hide();
			$('#validEmail').hide();
			$('#validUser').hide();
			$('#noTerms').hide();
		});
		function validate() {
			$('#invalid').hide();
			$('#noMatch').hide();
			$('#validEmail').hide();
			$('#validUser').hide();
			$('#noTerms').hide();
			var valid = true;
			if ($('#password').val() != $('#password2').val()) {
				$('#noMatch').show();
				valid = false;
			}
			if ($('#password').val().length < 7) {
				$('#invalid').show();
				valid = false;
			}
			if ($('#username').val().length < 3) {
				$('#validUser').show();
				valid = false;
			}
			if (!validEmail($('#email').val())) {
				$('#validEmail').show();
				valid = false;
			}
			if (!$('#terms').prop('checked')) {
				$('#noTerms').show();
				valid = false;
			}
			if (valid) {
				$('#loginForm').submit();
			}
		}
		function validEmail(emailAddress) {
		    var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
		    return pattern.test(emailAddress);
		};
	</script>
</body>
</html>
