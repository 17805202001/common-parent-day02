<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><c:if test="${param.name=='jack'}">true</c:if><c:if test="${param.name!='jack'}">false</c:if>