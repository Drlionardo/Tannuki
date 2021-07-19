<#import "../macro/template.ftlh" as tmp>
<@tmp.page>
<div class="text-center flex-box">
    <main class="form-signin">
        <form method="post" action="/login">
            <img class="mb-4" src="img/logo.png" alt="" width="72" height="72">
            <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

            <div class="form-floating">
                <input type="email" name="username" class="form-control" id="floatingInput" placeholder="name@example.com">
                <label for="floatingInput">Email address</label>
            </div>
            <div class="form-floating">
                <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
                <label for="floatingPassword">Password</label>
            </div>
            <input type="hidden" name="_csrf" value=${_csrf.token}>
            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" name="remember" value="remember-me"> Remember me
                </label>
            </div>
            <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
            <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
                <div class="alert alert-danger mt-2" role="alert">
                    ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
                </div>
            </#if>
            <p class="mt-3 mb-3 text-muted">
                Do not have account?
                <a href="/registration">Registration page</a>
            </p>
        </form>
    </main>
</div>
</@tmp.page>