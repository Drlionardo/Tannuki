<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <h2>Login page:</h2>
    <a href="/registration">Registration page</a>
    <div>
        <form method="post" action="/login">
            <div>
                <label>Email:</label> <input type="text" name="username">
            </div>
            <div>
                <label>Password:</label> <input type="password" name="password">
            </div>
            <input type="hidden" name="_csrf" value=${_csrf.token}>
            <div>
                <button type="submit">Login</button>
            </div>
        </form>
        ${responseMessage!}
    </div>
</@tmpl.page>
