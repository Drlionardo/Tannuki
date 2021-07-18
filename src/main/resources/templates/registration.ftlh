<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <h2>Registration page:</h2>
    <a href="/login">Login page</a>

    <div>
        <form method="post" action="/registration">
            <div>
                <label>Email:</label> <input type="email" name="email">
            </div>
            <div>
                <label>Password:</label> <input type="password" name="password">
            </div>
            <input type="hidden" name="_csrf" value=${_csrf.token}>
            <div>
                <button type="submit">Register</button>
                ${responseMessage!}
            </div>
        </form>
    </div>
</@tmpl.page>
