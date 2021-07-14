<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <h2>Event page</h2>
    <a href="/main">Main page</a>

    <div>Id:${event.id}  <a href="/event/${event.id}/edit">Edit event</a> </div>
    <div>Name:${event.name}</div>
    <div>
        Admins:
        <#list event.admins as admin>
            ${admin.email}
        </#list>
    </div>
    <div>Description:${event.description}</div>

    <div> All users:

    <#list requestList as req>
        <div>${req.owner.username}  ${req.registrationTime}</div>
    </#list>
    </div>

    <#if isRegistered>
        Cancel Registration:
        <form method="post" action="/event/${event.id}/cancel">
            <input type="hidden" name="_csrf" value=${_csrf.token}>
            <button type="submit">Cancel register</button>
        </form>
        <#else>
            Register:
            <form method="post" action="/event/${event.id}/register">
                <input type="hidden" name="_csrf" value=${_csrf.token}>
                <button type="submit">Register</button>
            </form>
    </#if>
</@tmpl.page>
