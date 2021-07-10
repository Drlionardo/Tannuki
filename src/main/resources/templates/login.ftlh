<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <h2>Login page:</h2>
    <div>
        <form method="post" action="/login">
            <div>
                <label>Email:</label> <input type="text" name="email">
            </div>
            <div>
                <label>Password:</label> <input type="password" name="password">
            </div>
            <input type="hidden" name="_csrf" value=${_csrf.token}>
            <div>
                <button type="submit">Login</button>
                ${responseMessage!}
            </div>
        </form>
    </div>
</@tmpl.page>
