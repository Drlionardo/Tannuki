<#import "../macro/template.ftlh" as tmp>
<@tmp.page>
<div class="text-center flex-box">
    <main class="form-signin">
        <form method="post" action="/registration">
            <img class="mb-4" src="/img/logo.png" alt="" width="72" height="72">
            <h1 class="h3 mb-3 fw-normal">Registration</h1>

            <div class="form-floating">
                <input type="email" name="email" class="form-control" id="floatingInput" placeholder="name@example.com">
                <label for="floatingInput">Email address</label>
            </div>
            <div class="form-floating">
                <input type="text" name="username" class="form-control" id="floatingInput" placeholder="username">
                <label for="floatingInput">Username</label>
            </div>
            <div class="form-floating">
                <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
                <label for="floatingPassword">Password</label>
            </div>
            <input type="hidden" name="_csrf" value=${_csrf.token}>

            <button class="w-100 btn btn-lg btn-primary" type="submit">Register account</button>
            <#if responseMessage??>
                <div class="alert alert-danger mt-2" role="alert">
                    ${responseMessage}
                </div>
            </#if>
            <p class="mt-3 mb-3 text-muted">
                Already have account?
                <a href="/login">Login page</a>
            </p>
        </form>
    </main>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</@tmp.page>