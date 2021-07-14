<#import "macro/template.ftlh" as tmpl>
<#import "macro/template.ftlh" as tmpl>
<@tmpl.page>
    <a href="/main">Main page</a>
    <div>Id:${event.id} </div>
    <div><h2>Event editor</h2></div>
    <form method="post" action="/event/${event.id}/edit">
        <input type="text" name="eventName" value="${event.name}">
        <input type="text" name="eventDescription" value="${event.description}">
        <input type="hidden" name="_csrf" value=${_csrf.token}>
        <button type="submit">Update event</button>
    </form>
    <div>
        Admins:
        <#list event.admins as admin>
            <div>${admin.email}
                <form method="post" action="/event/${event.id}/deleteAdmin">
                    <button type="submit">Delete</button>
                    <input type="hidden" name="adminId" value="${admin.id}">
                    <input type="hidden" name="_csrf" value=${_csrf.token}>
                </form>
            </div>
        </#list>
        Add admin
        <form method="post" action="/event/${event.id}/addAdmin">
            Admin username:
            <input type="text" name="adminEmail" placeholder="Email">
            <input type="hidden" name="_csrf" value=${_csrf.token}>
            <button type="submit">Add new admin</button>
        </form>
    </div>
</@tmpl.page>

