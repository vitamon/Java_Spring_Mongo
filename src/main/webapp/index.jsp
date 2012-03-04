<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Just Statistics</title>
</head>
<body>
<p>
    Entries matching your application ID:
</p>

<p>
<ul>
    <c:forEach var="entry" items="${entries}">
        <li>
            <div><c:out value="${entry}"/></div>
        </li>
    </c:forEach>
</ul>
</body>
</html>
