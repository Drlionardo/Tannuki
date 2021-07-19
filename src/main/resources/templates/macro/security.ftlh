<#assign
isAuthorized = Session.SPRING_SECURITY_CONTEXT??
>
<#if isAuthorized>
    <#assign
    activeUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    username = activeUser.getUsername()
    isAdmin = activeUser.isAdmin()
    >
<#else >
    <#assign
    activeUser = "unknown"
    username = "-"
    isAdmin = false
    >
</#if>