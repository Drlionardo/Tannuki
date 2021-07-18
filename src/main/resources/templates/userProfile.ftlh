<#import "macro/template.ftlh" as tmpl>
<#include "macro/security.ftlh">
<@tmpl.page>
    <a href="/main">Main page</a>
    <h2>Profile of ${user.username}:</h2>
    <div>Name: ${user.username}</div>
    <div>
        Roles:
        <#list user.roles as role>
            <label>${role}</label>
        </#list>
    </div>
    <#if activeUser.getId() == user.id || isAdmin>
        <div>
            Current email: ${user.email}
            Статус:
            <#if user.emailValidated>
                Подтвержден
            <#else>
                Ожидает подтвреждения
                <form method="post" action="/profile/${user.id}/repeatEmailActivation">
                    <input type="hidden" name="_csrf" value=${_csrf.token}>
                    <button type="submit">Send email with code again</button>
                </form>
            </#if>
        </div>

        <div>
            Change email:
            <form method="post" action="/profile/${user.id}/changeEmail">
                New email <input type="email" name="newEmail" value="${user.email}">
                <input type="hidden" name="_csrf" value=${_csrf.token}>
                <button type="submit">Change email</button>
            </form>
        </div>
        <div>
            Change password:
            <form method="post" action="/profile/${user.id}/changePassword">
                Old password <input type="password" name="oldPassword">
                New password <input type="password" name="newPassword">
                <input type="hidden" name="_csrf" value=${_csrf.token}>
                <button type="submit">Change password</button>
            </form>
        </div>
    </#if>
    <#if isAdmin>
        <h3>Admin section:</h3>
        <form method="post" action="/profile/${user.id}/updateRoles">
            <#list roles! as role>
                <div>
                    <input type="checkbox" name="${role}"${user.roles ? seq_contains(role) ? string("checked", "")}>
                    <label>${role}</label>
                </div>
            </#list>
            <input type="hidden" name="_csrf" value=${_csrf.token}>
            <button type="submit">Update roles</button>
        </form>
    </#if>
    <h1>${responseMessage!}</h1>
</@tmpl.page>
