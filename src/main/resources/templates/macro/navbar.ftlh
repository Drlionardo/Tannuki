<#assign
authorized = Session.SPRING_SECURITY_CONTEXT??
>
<#if authorized>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    username = user.getUsername()
    isAdmin = user.isAdmin()
    >
<#else >
    <#assign
    username = "unknown"
    isAdmin = false
    >
</#if>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">
            <img src="/img/logo.png" alt="" width="35" height="35">
        </a>
        <a class="navbar-brand" href="/">Reg-Hub</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/main">All vents</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">My events</a>
                </li>
                <#if isAdmin>
                    <!-- Admin only-->
                    <li class="nav-item">
                        <a class="nav-link" href="#">Admin page</a>
                    </li>
                </#if>
            </ul>
            <ul class="navbar-nav">
                <#if authorized>
                    <li class="nav-item">
                        <a class="nav-link" href="profile/${user.getId()}">${username}</a>
                    </li>
                    <form method="post" action="/logout">
                        <input type="hidden" name="_csrf" value=${_csrf.token}>
                        <button class="btn btn-primary ml-2" type="submit">Log out</button>
                    </form>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                </#if>
            </ul>
        </div>

    </div>
</nav>