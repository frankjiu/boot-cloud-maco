
<%
	String path = request.getContextPath();
	response.sendRedirect(path + "/login");
%>
<script type="text/javascript">
	window.location.replace("<%=path%>/login");
</script>
